package com.xjt.cloud.admin.manage.task.log;

import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.dao.log.SecurityLogDao;
import com.xjt.cloud.admin.manage.entity.log.SecurityLog;
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
 * SecurityLogTask
 * 日志
 *
 * @author huanggc
 * @date 2020/10/21
 */
public class SecurityLogTask implements Job, Serializable {
    @Autowired
    private SecurityLogDao securityLogDao;

    private static Logger logger = LoggerFactory.getLogger(SecurityLogTask.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        logger.info("SecurityLogTask ====> 开启!" );
        String exceptionMsg = "任务执行失败";
        try {
            List<Dict> dictList = DictUtils.getDictListByDictCode(ConstantsClient.SECURITY_LOG_TASK_DICT_CODE);
            if (CollectionUtils.isNotEmpty(dictList)){
                StringBuffer descriptionSb = new StringBuffer();
                StringBuffer memoSb = new StringBuffer();

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
                SecurityLog securityLog = new SecurityLog();
                for (String description : descriptionArr) {
                    // 表个数不超过10个
                    // 删除 日志数据
                    securityLog.setTableName(description);
                    securityLog.setCreateTime(createTime);
                    securityLogDao.deletedSecurityLog(securityLog);
                }
            }
        } catch (Exception e) {
            exceptionMsg = SysLog.getExceptionInfo(e);
            logger.error("SecurityLogTask ====>异常!", e);
        } finally {
            // sys_manage.quartz_config 表中配置定时任务参数
            QuartzConfService quartzConfService = SpringBeanUtil.getBean(QuartzConfService.class);
            quartzConfService.saveTaskLog(((CronTriggerImpl) ((JobExecutionContextImpl) jobExecutionContext).getTrigger()), exceptionMsg);
            logger.info("SecurityLogTask ====> 结束!" );
        }
    }

}
