package com.xjt.cloud.admin.manage.task;

import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.service.service.quartz.QuartzConfService;
import com.xjt.cloud.commons.utils.HttpUtils;
import com.xjt.cloud.commons.utils.SpringBeanUtil;
import com.xjt.cloud.commons.utils.SysLog;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.impl.JobExecutionContextImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @ClassName VideoDevice
 * @Author dwt
 * @Date 2019-09-11 14:44
 * @Description 视频设备状态周期巡检
 * @Version 1.0
 */
@Configuration
@Component
@EnableScheduling
public class VideoDevice implements Job, Serializable {
    private static Logger logger = LoggerFactory.getLogger(VideoDevice.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        logger.info("ScheduleTask ====> 开启!" );
        String exceptionMsg = "任务执行失败";
        try {
            HttpUtils.sendGet(ConstantsClient.VIDEO_STATUS_HANDLE_URL,null);
        } catch (Exception e) {
            exceptionMsg = SysLog.getExceptionInfo(e);
            logger.error("ScheduleTask ====>异常!", e);
        } finally {
            QuartzConfService quartzConfService = SpringBeanUtil.getBean(QuartzConfService.class);
            quartzConfService.saveTaskLog(((CronTriggerImpl) ((JobExecutionContextImpl) jobExecutionContext).getTrigger()), exceptionMsg);
            logger.info("ScheduleTask ====> 结束!" );
        }
    }
}
