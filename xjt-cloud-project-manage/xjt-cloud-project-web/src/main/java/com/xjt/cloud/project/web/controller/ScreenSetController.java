package com.xjt.cloud.project.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.ScreenSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ScreenSetController 大屏设置
 * @Author zhangZaiFa
 * @Date 2019-07-31 15:15
 * @Description
 */
@RestController
@RequestMapping("/project/screen")
public class ScreenSetController extends AbstractController {
    @Autowired
    private ScreenSetService screenSetService;

    /***@MethodName: findScreenSet
     * @Description: findScreenSet 查看项目大屏设置
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/7 17:01
     **/
    @RequestMapping(value = "/findScreenSet/{projectId}")
    public Data findScreenSet(String json) {
        return screenSetService.findScreenSet(json);
    }

    /***@MethodName: save
     * @Description: save 保存大屏设置
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/7 17:01
     **/
    @RequestMapping(value = "/save/{projectId}")
    public Data save(String json) {
        return screenSetService.save(json);
    }

    /**@MethodName: findScreenProjectList
     * @Description: 查询大屏项目列表
     * @Param: []
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/9 9:41
     **/
    @RequestMapping(value = "/findScreenProjectList")
    public Data findScreenProjectList(String json) {
        return screenSetService.findScreenProjectList(json);
    }

    /**@MethodName: findScreenProjectMapData
     * @Description: 查询大屏项目地图数据
     * @Param: []
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/9 9:43
     **/
    @RequestMapping(value = "/findScreenProjectMapData")
    public Data findScreenProjectMapData(String json) {
        return screenSetService.findScreenProjectMapData(json);
    }


    /**@MethodName: findUserScreenPermission
     * @Description: 查询项目是否有大屏功能
     * @Param: []
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/27 11:15
     **/
    @RequestMapping(value = "/findUserScreenPermission")
    public Data findUserScreenPermission() {
        return screenSetService.findUserScreenPermission();
    }

    /**
     * 查询 地铁大屏
     *
     * @MethodName: findMetroScreen
     * @param json
     * @Author hunaggc
     * @Date 2020-05-09
     * @Return com.xjt.cloud.commons.utils.Data
     **/
    @RequestMapping(value = "/findMetroScreen/{projectId}")
    public Data findMetroScreen(String json) {
        return screenSetService.findMetroScreen(json);
    }

    /**
     * 地铁大屏--跳转
     *
     * @MethodName: findMetroJump
     * @param json
     * @Author hunaggc
     * @Date 2020-05-09
     * @Return com.xjt.cloud.commons.utils.Data
     **/
    @RequestMapping(value = "/findMetroJump/{projectId}")
    public Data findMetroJump(String json) {
        return screenSetService.findMetroJump(json);
    }
}
