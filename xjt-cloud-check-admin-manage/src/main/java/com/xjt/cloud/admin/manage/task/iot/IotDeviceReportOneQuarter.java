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
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 物联设备故障统计
 * 季度任务
 *
 * @author huanggc
 * @date 2021/01/27
 */
@Configuration
@Component
public class IotDeviceReportOneQuarter implements Job, Serializable {
    private static Logger logger = LoggerFactory.getLogger(IotDeviceReportOneQuarter.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        logger.info("IotDeviceReportOneQuarter ====> 开启!");
        String exceptionMsg = "任务执行失败";
        try {
            //类型
            String res = HttpUtils.sendGet(ConstantsClient.IOT_DEVICE_REPORT_ONE_WEEK_URL, "json={\"timeChinese\":\"季度\"}");
            if(StringUtils.isNotEmpty(res)){
                Data data = JSONObject.parseObject(res, Data.class);
                if(200 == data.getStatus()){
                    exceptionMsg = "任务执行成功";
                }
            }
        } catch (Exception e) {
            exceptionMsg = SysLog.getExceptionInfo(e);
            logger.error("IotDeviceReportOneQuarter ====>异常!", e);
        } finally {
            QuartzConfService quartzConfService = SpringBeanUtil.getBean(QuartzConfService.class);
            quartzConfService.saveTaskLog(((CronTriggerImpl) ((JobExecutionContextImpl) jobExecutionContext).getTrigger()), exceptionMsg);
            logger.info("IotDeviceReportOneQuarter ====> 结束!");
        }
    }
}
