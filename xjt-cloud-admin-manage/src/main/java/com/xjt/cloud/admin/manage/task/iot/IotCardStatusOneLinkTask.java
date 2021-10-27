package com.xjt.cloud.admin.manage.task.iot;

import com.xjt.cloud.admin.manage.service.service.iot.IotCardService;
import com.xjt.cloud.admin.manage.service.service.quartz.QuartzConfService;
import com.xjt.cloud.commons.utils.SpringBeanUtil;
import com.xjt.cloud.commons.utils.SysLog;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.JobExecutionContextImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @ClassName IotCardOneLinkTask
 * @Description 物联卡基本信息任务
 * @Author wangzhiwen
 * @Date 2020/9/8 14:49
 **/
@Configuration
@Component
public class IotCardStatusOneLinkTask implements Job, Serializable {
    private static Logger logger = LoggerFactory.getLogger(IotCardStatusOneLinkTask.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("IotCardStatusOneLinkTask ====> 开启!" );
        String exceptionMsg = "任务执行失败";
        try {
            IotCardService iotCardService = SpringBeanUtil.getBean(IotCardService.class);
            if (iotCardService != null){
                iotCardService.iotCardStatusOneLinkTask();
                exceptionMsg = "任务执行成功";
            }
        } catch (Exception e) {
            exceptionMsg = SysLog.getExceptionInfo(e);
            logger.error("IotCardStatusOneLinkTask ====>异常!", e);
        } finally {
            QuartzConfService quartzConfService = SpringBeanUtil.getBean(QuartzConfService.class);
            quartzConfService.saveTaskLog(((CronTriggerImpl) ((JobExecutionContextImpl) jobExecutionContext).getTrigger()), exceptionMsg);
            logger.info("IotCardStatusOneLinkTask ====> 结束!" );
        }
    }
}
