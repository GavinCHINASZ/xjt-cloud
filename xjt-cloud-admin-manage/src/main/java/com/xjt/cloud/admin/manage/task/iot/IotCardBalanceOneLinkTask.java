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
 * @ClassName IotCardBalanceOneLinkTask
 * @Description
 * @Author wangzhiwen
 * @Date 2020/9/9 15:53
 **/
@Configuration
@Component
public class IotCardBalanceOneLinkTask implements Job, Serializable {
    private static Logger logger = LoggerFactory.getLogger(IotCardStatusOneLinkTask.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("IotCardBalanceOneLinkTask ====> 开启!" );
        String exceptionMsg = "任务执行失败";
        try {
            IotCardService iotCardService = SpringBeanUtil.getBean(IotCardService.class);
            if (iotCardService != null){
                iotCardService.iotCardBalanceOneLinkTask();
                exceptionMsg = "任务执行成功";
            }
        } catch (Exception e) {
            exceptionMsg = SysLog.getCodeLocation();

            logger.error("IotCardBalanceOneLinkTask ====>异常!", e);
        } finally {
            QuartzConfService quartzConfService = SpringBeanUtil.getBean(QuartzConfService.class);
            quartzConfService.saveTaskLog(((CronTriggerImpl) ((JobExecutionContextImpl) jobExecutionContext).getTrigger()), exceptionMsg);
            logger.info("IotCardBalanceOneLinkTask ====> 结束!" );
        }
    }
}
