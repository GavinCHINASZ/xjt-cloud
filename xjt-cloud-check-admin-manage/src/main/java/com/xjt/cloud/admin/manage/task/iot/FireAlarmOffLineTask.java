package com.xjt.cloud.admin.manage.task.iot;

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

import java.io.Serializable;

/**
 * @ClassName FireAlarmOffLineTask
 * @Author Administrator
 * @Date 2019-11-25 10:06
 * @Version 1.0
 */
public class FireAlarmOffLineTask implements Job, Serializable {

    private static Logger logger = LoggerFactory.getLogger(FireAlarmOffLineTask.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        logger.info("FireAlarmOffLineTask ====> 开启!" );
        String exceptionMsg = "任务执行失败";
        try {
            String res = HttpUtils.sendGet(ConstantsClient.FIRE_ALARM_OFF_LINE_TASK_URL,null);
            if(StringUtils.isNotEmpty(res)){
                Data data = JSONObject.parseObject(res, Data.class);
                if(200 == data.getStatus()){
                    exceptionMsg = "任务执行成功";
                }
            }
        } catch (Exception e) {
            exceptionMsg = SysLog.getExceptionInfo(e);
            logger.error("FireAlarmOffLineTask ====>异常!", e);
        } finally {
            QuartzConfService quartzConfService = SpringBeanUtil.getBean(QuartzConfService.class);
            quartzConfService.saveTaskLog(((CronTriggerImpl) ((JobExecutionContextImpl) jobExecutionContext).getTrigger()), exceptionMsg);
            logger.info("FireAlarmOffLineTask ====> 结束!" );
        }

    }
}
