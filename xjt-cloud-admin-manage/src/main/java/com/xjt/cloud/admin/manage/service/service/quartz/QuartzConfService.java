package com.xjt.cloud.admin.manage.service.service.quartz;


import com.xjt.cloud.admin.manage.entity.QuartzConfig;
import com.xjt.cloud.commons.utils.Data;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.util.List;


/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:56
 * @Description: quartz定时任务逻辑处理接口
 */
public interface QuartzConfService {

    /**
     *
     * 功能描述:查找任务列表
     *
     * @param
     * @return: java.util.List<com.xjt.cloud.sys.manage.entity.QuartzConfig>
     * @auther: wangzhiwen
     * @date: 2019/5/22 0022 17:16
     */
    List<QuartzConfig> getJobList();

    /**
     *
     * 功能描述: 以id查找任务对象
     *
     * @param id
     * @return: com.xjt.cloud.sys.manage.entity.QuartzConfig
     * @auther: wangzhiwen
     * @date: 2019/5/22 0022 17:16
     */
    QuartzConfig findById(long id);

    /**
     *
     * 功能描述:修改任务的描述与时间
     *
     * @param id
     * @param cron
     * @param msg
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/5/22 0022 17:15
     */
    Data updateJob(long id, String cron, String msg);

    /**
     *
     * 功能描述: 修改任务状态
     *
     * @param id
     * @param status
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/5/22 0022 17:06
     */
    Data updateJobStatus(long id, int status);

    /**
     *
     * 功能描述:添加新任务
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/5/22 0022 17:17
     */
    Data saveJob(String json);

    /**
     *
     * 功能描述:删除任务
     *
     * @param id
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/5/22 0022 17:17
     */
    Data delJob(long id);

    /**
     *
     * 功能描述: 保存任务运行日志
     *
     * @param cronTrigger
     * @param exceptionMsg
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/12/2 10:59
     */
    void saveTaskLog(CronTriggerImpl cronTrigger, String exceptionMsg);
}
