package com.xjt.cloud.quartz.manage;

import com.xjt.cloud.commons.utils.SysLog;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/5/22 0022 15:59
 * @Description: 定时任务运行工厂类
 */

public class StartSchedulerListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    public MySchedulerFactory mySchedulerFactory;
    @Autowired
    private MyJobFactory myJobFactory;
    // springboot 启动监听
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            //mySchedulerFactory.scheduleJobs();
            initScheduleJobs();
        } catch (SchedulerException e) {
            SysLog.error(e);
        }
    }

    public void initScheduleJobs()throws SchedulerException{

    }

    //注入SchedulerFactoryBean
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(myJobFactory);
        return schedulerFactoryBean;
    }

}
