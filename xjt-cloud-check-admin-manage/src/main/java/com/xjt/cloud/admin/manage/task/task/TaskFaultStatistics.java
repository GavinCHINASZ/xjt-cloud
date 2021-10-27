package com.xjt.cloud.admin.manage.task.task;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.service.service.quartz.QuartzConfService;
import com.xjt.cloud.commons.utils.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.impl.JobExecutionContextImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 定时任务发送故障统计信息(月报)
 *
 * @author huanggc
 * @date 2020/11/30
 */
@Configuration
@Component
public class TaskFaultStatistics implements Job, Serializable {
    private static Logger logger = LoggerFactory.getLogger(TaskFaultStatistics.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        logger.info("TaskFaultStatistics ====> 开启!");
        String exceptionMsg = "任务执行失败";
        try {
            String res = HttpUtils.sendGet(ConstantsClient.TASK_FAULT_STATISTICS,null);
            if(StringUtils.isNotEmpty(res)){
                Data data = JSONObject.parseObject(res, Data.class);
                if(200 == data.getStatus()){
                    exceptionMsg = "任务执行成功";
                }
            }
        } catch (Exception e) {
            exceptionMsg = SysLog.getExceptionInfo(e);
            logger.error("TaskFaultStatistics ====>异常!", e);
        } finally {
            QuartzConfService quartzConfService = SpringBeanUtil.getBean(QuartzConfService.class);
            quartzConfService.saveTaskLog(((CronTriggerImpl) ((JobExecutionContextImpl) jobExecutionContext).getTrigger()), exceptionMsg);
            logger.info("TaskFaultStatistics ====> 结束!");
        }
    }
}
