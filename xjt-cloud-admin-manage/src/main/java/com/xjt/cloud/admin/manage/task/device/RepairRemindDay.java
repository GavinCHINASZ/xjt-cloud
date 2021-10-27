package com.xjt.cloud.admin.manage.task.device;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.service.service.quartz.QuartzConfService;
import com.xjt.cloud.admin.manage.task.task.TaskOneWeek;
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
 * 设备送修提醒 每天周期任务处理
 *
 * @ClassName RepairRemindDay
 * @Author huanggc
 * @Date 2020-03-30
 * @Version 1.0
 */
@Configuration
@Component
public class RepairRemindDay implements Job, Serializable {
    private static Logger logger = LoggerFactory.getLogger(TaskOneWeek.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        logger.info("ScheduleTask ====> 开启!");
        String exceptionMsg = "送修提醒定时任务执行失败";
        try {
            String res = HttpUtils.sendGet(ConstantsClient.REPAIR_REMIND_URL, null);
            if(StringUtils.isNotEmpty(res)){
                Data data = JSONObject.parseObject(res, Data.class);
                if(200 == data.getStatus()){
                    exceptionMsg = "送修提醒定时任务执行成功";
                }
            }
        } catch (Exception e) {
            exceptionMsg = SysLog.getExceptionInfo(e);
            logger.error("ScheduleTask ====>异常!", e);
        } finally {
            QuartzConfService quartzConfService = SpringBeanUtil.getBean(QuartzConfService.class);
            quartzConfService.saveTaskLog(((CronTriggerImpl) ((JobExecutionContextImpl) jobExecutionContext).getTrigger()), exceptionMsg);
            logger.info("ScheduleTask ====> 结束!");
        }
    }
}
