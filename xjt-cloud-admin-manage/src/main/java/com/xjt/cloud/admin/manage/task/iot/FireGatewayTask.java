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
 * @ClassName FireGatewayTask
 * @Author dwt
 * @Date 2019-12-03 17:19
 * @Version 1.0
 */
public class FireGatewayTask implements Job, Serializable {

    private static Logger logger = LoggerFactory.getLogger(FireGatewayTask.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("FireGatewayTask ====> 开启!" );
        String exceptionMsg = "任务执行失败";
        try {
            String res = HttpUtils.sendGet(ConstantsClient.FIRE_GATEWAY_READ_ADDRSS_URL,null);
            if(StringUtils.isNotEmpty(res)){
                Data data = JSONObject.parseObject(res, Data.class);
                if(200 == data.getStatus()){
                    exceptionMsg = "任务执行成功";
                }
            }
        } catch (Exception e) {
            exceptionMsg = SysLog.getExceptionInfo(e);
            logger.error("FireGatewayTask ====>异常!", e);
        } finally {
            QuartzConfService quartzConfService = SpringBeanUtil.getBean(QuartzConfService.class);
            quartzConfService.saveTaskLog(((CronTriggerImpl) ((JobExecutionContextImpl) jobExecutionContext).getTrigger()), exceptionMsg);
            logger.info("FireGatewayTask ====> 结束!" );
        }
    }
}
