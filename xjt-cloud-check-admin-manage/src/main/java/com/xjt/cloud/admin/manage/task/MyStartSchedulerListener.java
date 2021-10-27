package com.xjt.cloud.admin.manage.task;

import com.xjt.cloud.admin.manage.entity.QuartzConfig;
import com.xjt.cloud.admin.manage.service.service.quartz.QuartzConfService;
import com.xjt.cloud.quartz.manage.StartSchedulerListener;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/5/23 0023 13:49
 * @Description:
 */
@Configuration
public class MyStartSchedulerListener extends StartSchedulerListener {
    // 任务配置读取服务
    @Autowired
    private QuartzConfService asyncQuartzConfService;

    // 项目启动 开启任务
    @Override
    public void initScheduleJobs()throws SchedulerException {
        List<QuartzConfig> jobList = asyncQuartzConfService.getJobList();
        for (QuartzConfig config : jobList) {
            // 1-暂停的任务 0-正常运行任务
            if (1l==config.getStatus()){
                continue;
            }
            mySchedulerFactory.addJob(config);
        }
    }
}
