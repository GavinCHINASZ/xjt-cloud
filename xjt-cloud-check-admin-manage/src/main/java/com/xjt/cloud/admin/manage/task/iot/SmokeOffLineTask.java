package com.xjt.cloud.admin.manage.task.iot;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.service.service.quartz.QuartzConfService;
import com.xjt.cloud.commons.utils.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.JobExecutionContextImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 烟感设备过期定时任务
 *
 * @author huanggc
 * @date 2020/08/04
 */
public class SmokeOffLineTask implements Job, Serializable {

    private static Logger logger = LoggerFactory.getLogger(SmokeOffLineTask.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("SmokeOffLineTask ====> 开启!" );
        String exceptionMsg = "烟感任务执行失败";
        try {
            String res = HttpUtils.sendGet(ConstantsClient.SMOKE_OFF_LINE_TASK_URL,null);
            if (StringUtils.isNotEmpty(res)){
                Data data = JSONObject.parseObject(res, Data.class);
                if (200 == data.getStatus()){
                    exceptionMsg = "烟感任务执行成功";
                }
            }
        } catch (Exception e) {
            exceptionMsg = SysLog.getExceptionInfo(e);
            logger.error("SmokeOffLineTask ====>异常!", e);
        } finally {
            // sys_manage.quartz_config 表中配置定时任务参数
            QuartzConfService quartzConfService = SpringBeanUtil.getBean(QuartzConfService.class);
            quartzConfService.saveTaskLog(((CronTriggerImpl) ((JobExecutionContextImpl) jobExecutionContext).getTrigger()), exceptionMsg);
            logger.info("SmokeOffLineTask ====> 结束!" );
        }
    }

}
