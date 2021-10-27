package com.xjt.cloud.admin.manage.task.task;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.service.service.quartz.QuartzConfService;
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
 * @ClassName TaskDay
 * @Author dwt
 * @Date 2019-08-06 9:59
 * @Description 每天周期任务处理
 * @Version 1.0
 */
@Configuration
@Component
public class TaskDay implements Job, Serializable {
    private static Logger logger = LoggerFactory.getLogger(TaskOneWeek.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        logger.info("ScheduleTask ====> 开启!");
        String exceptionMsg = "任务执行失败";
        try {
            //类型  8：每日 (每小时：0 0 */1 * * ?)
            String res = HttpUtils.sendGet(ConstantsClient.PERIOD_TASK_HANDLE_URL, "json={\"type\":8}");
            if(StringUtils.isNotEmpty(res)){
                Data data = JSONObject.parseObject(res, Data.class);
                if(200 == data.getStatus()){
                    exceptionMsg = "任务执行成功";
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
