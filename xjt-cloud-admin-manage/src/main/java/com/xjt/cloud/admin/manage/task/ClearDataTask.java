package com.xjt.cloud.admin.manage.task;

import com.xjt.cloud.admin.manage.service.service.quartz.QuartzConfService;
import com.xjt.cloud.admin.manage.service.service.sys.UserService;
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

import java.io.Serializable;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/2 14:29
 * @Description:
 */
public class ClearDataTask implements Job, Serializable {

    private static Logger logger = LoggerFactory.getLogger(WaterOffLineTask.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("WaterOffLineTask ====> 开启!" );
        String exceptionMsg = "任务执行失败";
        try {
            UserService userService = SpringBeanUtil.getBean(UserService.class);
            userService.clearData();
            exceptionMsg = "任务执行成功";
        } catch (Exception e) {
            exceptionMsg = SysLog.getExceptionInfo(e);
            logger.error("WaterOffLineTask ====>异常!", e);
        } finally {
            QuartzConfService quartzConfService = SpringBeanUtil.getBean(QuartzConfService.class);
            quartzConfService.saveTaskLog(((CronTriggerImpl) ((JobExecutionContextImpl) jobExecutionContext).getTrigger()), exceptionMsg);
            logger.info("WaterOffLineTask ====> 结束!" );
        }
    }
}