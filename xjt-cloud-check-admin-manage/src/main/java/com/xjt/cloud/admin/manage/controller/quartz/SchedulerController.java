package com.xjt.cloud.admin.manage.controller.quartz;

import com.xjt.cloud.admin.manage.service.service.quartz.QuartzConfService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:50
 * @Description: 任务控制类
 */
@RestController
@RequestMapping("/quartz/")
public class SchedulerController extends AbstractController {
    @Autowired
    private QuartzConfService quartzConfService;

    /**
     *
     * 功能描述:查询所有任务
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/5/22 0022 17:10
     */
    @RequestMapping(value = "list")
    public Data list(HttpServletRequest http) {
        return new Data(Constants.SUCCESS_CODE,quartzConfService.getJobList());
    }

    /**
     *
     * 功能描述:新增任务
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/5/22 0022 17:09
     */
    @RequestMapping(value = "saveJob")
    public Data saveJob(String json) {
        return quartzConfService.saveJob(json);
    }

    /**
     *
     * 功能描述:修改任务描述或运行时间
     *
     * @param id
     * @param cron
     * @param msg
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/5/22 0022 17:09
     */
    @RequestMapping(value = "edit")
    public Data editJob(@RequestParam(value = "id", required = true) long id,
                        @RequestParam(value = "cron", required = true) String cron,
                        @RequestParam(value = "msg", required = true) String msg) {
        return quartzConfService.updateJob(id, cron, msg);
    }

    /**
     *
     * 功能描述: 暂停某个任务
     *
     * @param id
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/5/22 0022 17:04
     */
    @RequestMapping(value = "pauseJob")
    @Transactional
    public Data pauseJob(@RequestParam(value = "id", required = true) long id) {
        return quartzConfService.updateJobStatus(id,1);
    }

    /**
     *
     * 功能描述: 恢复某个任务
     *
     * @param id
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/5/22 0022 17:04
     */
    @RequestMapping(value = "resumeJob")
    public Data resumeJob(@RequestParam(value = "id", required = true) long id) {
        return quartzConfService.updateJobStatus(id,0);
    }
}
