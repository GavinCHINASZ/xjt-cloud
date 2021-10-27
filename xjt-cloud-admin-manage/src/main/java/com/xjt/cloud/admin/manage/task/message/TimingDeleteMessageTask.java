package com.xjt.cloud.admin.manage.task.message;

import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.dao.message.MessagesDao;
import com.xjt.cloud.admin.manage.entity.message.Messages;
import com.xjt.cloud.admin.manage.service.service.quartz.QuartzConfService;
import com.xjt.cloud.commons.dict.Dict;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.impl.JobExecutionContextImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 定时删除消息任务
 *
 * @author huanggc
 * @date 2020/11/03
 */
public class TimingDeleteMessageTask implements Job, Serializable {
    @Autowired
    private MessagesDao messageDao;

    private static Logger logger = LoggerFactory.getLogger(TimingDeleteMessageTask.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        logger.info("TimingDeleteMessageTask ====> 开启!");
        String exceptionMsg = "任务执行失败";
        try {
            // 消息模块 MESSAGE_MANAGE_CONFIG


            List<Dict> dictList = DictUtils.getDictListByDictCode(ConstantsClient.MESSAGE_MANAGE_CONFIG);
            if (CollectionUtils.isNotEmpty(dictList)){
                StringBuilder descriptionSb = new StringBuilder();
                StringBuilder memoSb = new StringBuilder();

                // 要删除消息的表名 DELETE_MESSAGE_TABLE_NAME    ;号分隔
                String deleteMessageTableName = ConstantsClient.DELETE_MESSAGE_TABLE_NAME;

                for (Dict dict : dictList) {
                    descriptionSb.append(dict.getItemDescription());
                    memoSb.append(dict.getMemo());
                }

                String[] descriptionArr = descriptionSb.toString().split(";");
                String[] memo = memoSb.toString().split(";");

                Calendar c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.add(Calendar.MONTH, -(Integer.parseInt(memo[0])));
                Date createTime = c.getTime();

                Messages message = new Messages();
                for (String description : descriptionArr) {
                    // 表个数不超过10个
                    // 删除 消息数据
                    message.setTableName(description);
                    message.setCreateTime(createTime);
                    messageDao.deleteMessage(message);
                }
            }
        } catch (Exception e) {
            exceptionMsg = SysLog.getExceptionInfo(e);
            logger.error("TimingDeleteMessageTask ====>异常!", e);
        } finally {
            // sys_manage.quartz_config 表中配置定时任务参数
            QuartzConfService quartzConfService = SpringBeanUtil.getBean(QuartzConfService.class);
            quartzConfService.saveTaskLog(((CronTriggerImpl) ((JobExecutionContextImpl) jobExecutionContext).getTrigger()), exceptionMsg);
            logger.info("TimingDeleteMessageTask ====> 结束!");
        }
    }
}
