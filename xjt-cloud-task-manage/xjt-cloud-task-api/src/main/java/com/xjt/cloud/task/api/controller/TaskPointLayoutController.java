package com.xjt.cloud.task.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.service.service.layout.TaskPointLayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TaskPointLayoutController
 * @Description 任务平面图布点
 * @Author wangzhiwen
 * @Date 2021/3/9 16:07
 **/
@RestController
@RequestMapping("/task/point/layout/")
public class TaskPointLayoutController extends AbstractController {
    @Autowired
    private TaskPointLayoutService taskPointLayoutService;

    /**
     *
     * 功能描述:查询任务巡检点点位列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:12
     */
    @RequestMapping(value = "findAppTaskPointPositionList/{projectId}")
    public Data findAppTaskPointPositionList(String json){
        return taskPointLayoutService.findAppTaskPointPositionList(json);
    }

    /**
     *
     * 功能描述:保存任务巡检点点位列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:12
     */
    @RequestMapping(value = "saveTaskPointPosition/{projectId}")
    public Data saveTaskPointPosition(String json){
        return taskPointLayoutService.saveTaskPointPosition(json);
    }

    /**
     *
     * 功能描述:删除任务巡检点点位列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:12
     */
    @RequestMapping(value = "delTaskPointPosition/{projectId}")
    public Data delTaskPointPosition(String json){
        return taskPointLayoutService.delTaskPointPosition(json);
    }

}
