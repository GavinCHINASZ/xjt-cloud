package com.xjt.cloud.project.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.BackgroundMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName BackgroundMapController
 * @Author dwt
 * @Date 2020-09-03 9:28
 * @Version 1.0
 */
@RestController
@RequestMapping("/background/map")
public class BackgroundMapController extends AbstractController {

    @Autowired
    private BackgroundMapService backgroundMapService;
    /**
     *@Author: dwt
     *@Date: 2020-09-03 9:30
     *@Param: json
     *@Return: Data
     *@Description 背景图保存
     */
    @RequestMapping(value = "/saveBackgroundMap/{projectId}")
    public Data saveBackgroundMap(String json) {
        return backgroundMapService.saveBackgroundMap(json);
    }
    /**
     *@Author: dwt
     *@Date: 2020-09-03 9:31
     *@Param: json
     *@Return: Data
     *@Description 背景图查询
     */
    @RequestMapping(value = "/findBackgroundMap/{projectId}")
    public Data findBackgroundMap(String json) {
        return backgroundMapService.findBackgroundMap(json);
    }
}
