package com.xjt.cloud.admin.manage.task.project;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.dao.log.ProjectRunLogDao;
import com.xjt.cloud.admin.manage.entity.log.ProjectRunLog;
import com.xjt.cloud.admin.manage.service.service.quartz.QuartzConfService;
import com.xjt.cloud.commons.dict.Dict;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.message.manage.service.service.MessageService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.impl.JobExecutionContextImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * MonitorProjectRunStateTask
 * 监听所有模块运行状态
 *
 * @author huanggc
 * @date 2020/09/07
 */
public class MonitorProjectRunStateTask implements Job, Serializable {
    @Autowired
    private MessageService messageService;
    @Autowired
    private ProjectRunLogDao projectRunLogDao;

    private static Logger logger = LoggerFactory.getLogger(MonitorProjectRunStateTask.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        logger.info("MonitorProjectRunStateTask ====> 开启!" );
        String exceptionMsg = "MonitorProjectRunStateTask 任务执行失败";
        try {
            List<Dict> dictList = DictUtils.getDictListByDictCode(ConstantsClient.MONITOR_PROJECT_RUN_STATE_DICT_CODE);
            if (CollectionUtils.isNotEmpty(dictList)){
                StringBuilder sb = new StringBuilder();

                ProjectRunLog projectRunLog = new ProjectRunLog();
                String res;
                for (Dict dict : dictList) {
                    try {
                        res = HttpUtils.httpGetString(dict.getMemo());
                    }catch (Exception e){
                        res = "";
                    }
                    // 访问失败
                    if(StringUtils.isEmpty(res)){
                        sb.append(" " + dict.getItemDescription());

                        projectRunLog.setItemValue(dict.getItemCode());
                        projectRunLogDao.saveProjectRunLog(projectRunLog);
                    }
                }

                // 查询 数量
                ProjectRunLog entity = new ProjectRunLog();
                int projectRunLogCount = projectRunLogDao.findProjectRunLogCount(entity);
                if (projectRunLogCount >= Integer.valueOf(DictUtils.getDictItemValueByDictAndItemCode(
                        ConstantsClient.MONITOR_PROJECT_CONFIG, ConstantsClient.FAIL_NUM_SEND_MAIL))
                && projectRunLogCount < Integer.valueOf(DictUtils.getDictItemValueByDictAndItemCode(
                        ConstantsClient.MONITOR_PROJECT_CONFIG, ConstantsClient.FAIL_NUM_SEND_SMS))){

                    SysLog.info("MonitorProjectRunStateTask 发送邮件------->>");
                    // 多次失败 发邮件
                    String mailStr = DictUtils.getDictPropertyByDictItemCodePropertyName(ConstantsClient.MONITOR_PROJECT_CONFIG,
                            ConstantsClient.MONITOR_PROJECT_MAIL_RECEIVE,"itemDescription");
                    if (StringUtils.isNotEmpty(mailStr)){
                        MailUtils.send("消检通系统", sb.toString(), "系统停止运行错误", mailStr.split(";"));
                    }

                    entity.setDeleted(false);
                    projectRunLogDao.updateProjectRunLogDeleted(entity);
                }else if (projectRunLogCount >= Integer.valueOf(DictUtils.getDictItemValueByDictAndItemCode(
                        ConstantsClient.MONITOR_PROJECT_CONFIG, ConstantsClient.FAIL_NUM_SEND_SMS))){

                    SysLog.info("MonitorProjectRunStateTask 发送短信------->>");
                    // 发短信  SMS_48320070   ${ProName}项目${position}，请您及时处理！   event == 91
                    String phoneStr = DictUtils.getDictPropertyByDictItemCodePropertyName(ConstantsClient.MONITOR_PROJECT_CONFIG,
                            ConstantsClient.MONITOR_PROJECT_PHONE_RECEIVE,"itemDescription");
                    if (StringUtils.isNotEmpty(phoneStr)){
                        String[] split = phoneStr.split(";");

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("ProName", "停止运行的");
                        jsonObject.put("position", sb.toString());
                        Integer event = Integer.valueOf(ConstantsClient.MONITOR_PROJECT_EVENT);
                        for (String i : split) {
                            messageService.sendSMS(event, i, jsonObject);
                        }
                    }

                    entity.setDeleted(false);
                    projectRunLogDao.updateProjectRunLogDeleted(entity);
                }
            }
            exceptionMsg = "MonitorProjectRunStateTask 任务执行成功";
        } catch (Exception e) {
            exceptionMsg = SysLog.getExceptionInfo(e);
            logger.error("MonitorProjectRunStateTask ====>异常!");
            SysLog.error(e);
        } finally {
            // sys_manage.quartz_config 表中配置定时任务参数
            QuartzConfService quartzConfService = SpringBeanUtil.getBean(QuartzConfService.class);
            quartzConfService.saveTaskLog(((CronTriggerImpl) ((JobExecutionContextImpl) jobExecutionContext).getTrigger()), exceptionMsg);
            logger.info("MonitorProjectRunStateTask ====> 结束!" );
        }
    }

}
