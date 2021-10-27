package com.xjt.cloud.quartz.manage;

import com.xjt.cloud.commons.utils.SysLog;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 调度工厂类
 * Created by jinyu on 2018/4/14/014.
 */
@Service
@Component
public class MySchedulerFactory {
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    // 任务调度
    @Autowired
    private Scheduler scheduler;

    // 添加一个任务
    public void addJob(BaseQuartzConfig config){
        try {
            @SuppressWarnings("unchecked")
            Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(config.getQuartzClass());
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(config.getName(), config.getGroup()).build();
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(config.getCron());
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(config.getName(), config.getGroup()) .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (ClassNotFoundException e) {
            SysLog.error(e);
        }catch (SchedulerException e) {
            SysLog.error(e);
        }
    }

    // 新增一个任务
    public void saveJob(BaseQuartzConfig quartzConfig)  {
        addJob(quartzConfig);
    }

    // 任务暂停
    public void pauseJob(BaseQuartzConfig quartzConfig) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(quartzConfig.getName(), quartzConfig.getGroup());
        scheduler.pauseJob(jobKey);
    }

    // 删除任务
    public void delJob(BaseQuartzConfig quartzConfig) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(quartzConfig.getName(), quartzConfig.getGroup());
        scheduler.deleteJob(jobKey);
    }

    // 任务恢复
    public void resumeJob(BaseQuartzConfig quartzConfig) throws SchedulerException, ClassNotFoundException {
        JobKey jobKey = JobKey.jobKey(quartzConfig.getName(), quartzConfig.getGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 暂停所有任务
     *
     * @throws SchedulerException
     */
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 修改某个任务的执行时间
     *
     * @param quartzConfig
     * @return
     * @throws SchedulerException
     */
    public boolean modifyJob(BaseQuartzConfig quartzConfig) throws SchedulerException {
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(quartzConfig.getName(), quartzConfig.getGroup());
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        String oldTime = cronTrigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(quartzConfig.getCron())) {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartzConfig.getCron());
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(quartzConfig.getName(), quartzConfig.getGroup())
                    .withSchedule(cronScheduleBuilder).build();
            date = scheduler.rescheduleJob(triggerKey, trigger);
        }
        return date != null;
    }

    /**
     * 恢复所有任务
     *
     * @throws SchedulerException
     */
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }
}
