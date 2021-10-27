package com.xjt.cloud.admin.manage.task.message;

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
 * PageViewReportTask
 * PV统计定时任务
 *
 * @author huanggc
 * @date 2020/11/03
 */
public class PageViewReportTask implements Job, Serializable {
    private static Logger logger = LoggerFactory.getLogger(PageViewReportTask.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        logger.info("PageViewReportTask ====> 开启!");
        String exceptionMsg = "任务执行失败";
        try {
            String res = HttpUtils.sendGet(ConstantsClient.PAGE_VIEW_REPORT_TASK_URL, null);
            if (StringUtils.isNotEmpty(res)) {
                Data data = JSONObject.parseObject(res, Data.class);
                if (200 == data.getStatus()) {
                    exceptionMsg = "PageViewReportTask任务执行成功";
                }
            }
        } catch (Exception e) {
            exceptionMsg = SysLog.getExceptionInfo(e);
            logger.error("PageViewReportTask ====>异常!", e);
        } finally {
            // sys_manage.quartz_config 表中配置定时任务参数
            QuartzConfService quartzConfService = SpringBeanUtil.getBean(QuartzConfService.class);
            quartzConfService.saveTaskLog(((CronTriggerImpl) ((JobExecutionContextImpl) jobExecutionContext).getTrigger()), exceptionMsg);
            logger.info("PageViewReportTask ====> 结束!");
        }
    }
}
