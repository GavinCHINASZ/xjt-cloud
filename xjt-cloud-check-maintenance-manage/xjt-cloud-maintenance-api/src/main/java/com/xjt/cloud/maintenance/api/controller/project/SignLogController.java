package com.xjt.cloud.maintenance.api.controller.project;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.service.service.project.SignLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SignLogController
 * @Author dwt
 * @Date 2020-04-12 15:05
 * @Version 1.0
 */
@RestController
@RequestMapping("/signLog")
public class SignLogController {

    @Autowired
    private SignLogService signLogService;

    /**
     *@Author: dwt
     *@Date: 2020-04-12 14:08
     *@Param: json
     *@Return: Data
     *@Description 保存检测员签到日志
     */
    @RequestMapping(value = "/saveSignLog/{projectId}")
    public Data saveSignLog(String json){
        return signLogService.saveSignLog(json);
    }
    /**
     *@Author: dwt
     *@Date: 2020-04-12 14:09
     *@Param: json
     *@Return: Data
     *@Description 查询项目检测签到日志
     */
    @RequestMapping(value = "/findSignLogByProjectId/{projectId}")
    public Data findSignLogByProjectId(String json){
        return signLogService.findSignLogByProjectId(json);
    }
    /**
     *@Author: dwt
     *@Date: 2020-04-12 14:11
     *@Param: json
     *@Return: Data
     *@Description 查询检测员签到日志
     */
    @RequestMapping(value = "/findCheckUserSignLog/{projectId}")
    public Data findCheckUserSignLog(String json){
        return signLogService.findCheckUserSignLog(json);
    }

}
