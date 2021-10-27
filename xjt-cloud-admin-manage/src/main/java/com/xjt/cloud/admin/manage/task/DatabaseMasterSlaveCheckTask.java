package com.xjt.cloud.admin.manage.task;

import com.xjt.cloud.admin.manage.service.service.quartz.QuartzConfService;
import com.xjt.cloud.admin.manage.service.service.sys.SysService;
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
 * @ClassName DatabaseMasterSlaveTask
 * @Description 数据库主从监听任务
 * @Author wangzhiwen
 * @Date 2020/10/26 9:33
 **/
public class DatabaseMasterSlaveCheckTask implements Job, Serializable {

    private static Logger logger = LoggerFactory.getLogger(WaterOffLineTask.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("DatabaseMasterSlaveCheckTask ====> 开启!" );
        String exceptionMsg = "任务执行失败";
        try {
            SysService sysService = SpringBeanUtil.getBean(SysService.class);
            sysService.databaseMasterSlaveCheckTask();
            exceptionMsg = "任务执行成功";
        } catch (Exception e) {
            exceptionMsg = SysLog.getExceptionInfo(e);
            logger.error("DatabaseMasterSlaveCheckTask ====>异常!", e);
        } finally {
            QuartzConfService quartzConfService = SpringBeanUtil.getBean(QuartzConfService.class);
            quartzConfService.saveTaskLog(((CronTriggerImpl) ((JobExecutionContextImpl) jobExecutionContext).getTrigger()), exceptionMsg);
            logger.info("DatabaseMasterSlaveCheckTask ====> 结束!" );
        }
    }
}