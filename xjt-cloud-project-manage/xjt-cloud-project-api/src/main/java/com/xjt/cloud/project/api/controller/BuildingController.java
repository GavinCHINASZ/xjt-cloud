package com.xjt.cloud.project.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName BuildingController 建筑物
 * @Author zhangZaiFa
 * @Date 2019-11-06 15:15
 * @Description
 */
@RestController
@RequestMapping("/project/building")
public class BuildingController extends AbstractController {

    @Autowired
    private BuildingService buildingService;


    /**
     * @MethodName: findByProjectList 项目建筑物列表
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019-11-06 13:45
     **/
    @RequestMapping(value = "/findByProjectList/{projectId}")
    public Data findByProjectList(@PathVariable Long projectId, String json) {
        return buildingService.findByProjectList(json);
    }
    
    /**@MethodName: findByBuilding 查询建筑物详情
     * @Description: 
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/6 16:12
     **/
    @RequestMapping(value = "/findByBuilding/{projectId}")
    public Data findByBuilding(@PathVariable Long projectId, String json) {
        return buildingService.findByBuilding(json);
    }


    /**@MethodName: findBuildingFloor 查询建筑物楼层信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/6 16:11
     **/
    @RequestMapping(value = "/findBuildingFloor/{projectId}")
    public Data findBuildingFloor( String json) {
        return buildingService.findBuildingFloor(json);
    }



}
