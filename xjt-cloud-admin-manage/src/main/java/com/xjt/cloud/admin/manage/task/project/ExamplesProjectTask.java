package com.xjt.cloud.admin.manage.task.project;

import com.xjt.cloud.admin.manage.service.service.project.ProjectService;
import com.xjt.cloud.admin.manage.service.service.quartz.QuartzConfService;
import com.xjt.cloud.admin.manage.task.iot.WaterOffLineTask;
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
 * @Auther: wangzhiwen
 * @Date: 2020/7/23 16:24
 * @Description:
 */
@Configuration
@Component
public class ExamplesProjectTask  implements Job, Serializable {

    private static Logger logger = LoggerFactory.getLogger(WaterOffLineTask.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("ExamplesProjectTask ====> 开启!");
        String exceptionMsg = "任务执行失败";
        try {
            ProjectService projectService = SpringBeanUtil.getBean(ProjectService.class);
            projectService.examplesProjectTask();
            exceptionMsg = "任务执行成功";
        } catch (Exception e) {
            exceptionMsg = SysLog.getExceptionInfo(e);
            logger.error("ExamplesProjectTask ====>异常!", e);
        } finally {
            QuartzConfService quartzConfService = SpringBeanUtil.getBean(QuartzConfService.class);
            quartzConfService.saveTaskLog(((CronTriggerImpl) ((JobExecutionContextImpl) jobExecutionContext).getTrigger()), exceptionMsg);
            logger.info("ExamplesProjectTask ====> 结束!");
        }
    }
}