package com.xjt.cloud.maintenance.web.controller.project;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.service.service.project.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName BuildingController 建筑物
 * @Author zhangZaiFa
 * @Date 2019-07-31 15:15
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
     * @Date:2019/7/31 13:45
     **/
    @RequestMapping(value = "/findByProjectList/{projectId}")
    public Data findByProjectList(@PathVariable Long projectId, String json) {
        return buildingService.findByProjectList(json);
    }

    @RequestMapping(value = "/findByBuilding/{projectId}")
    public Data findByBuilding(@PathVariable Long projectId, String json) {
        return buildingService.findByBuilding(json);
    }



    /**
     * @MethodName: addBuilding 添加建筑物
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 13:48
     **/
    @RequestMapping(value = "/addBuilding/{projectId}")
    public Data addBuilding(String json) {
        return buildingService.addBuilding(json);
    }

    /**
     * @MethodName: updateBuilding 修改建筑物
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 13:48
     **/
    @RequestMapping(value = "/updateBuilding/{projectId}")
    public Data updateBuilding( String json) {
        return buildingService.updateBuilding(json);
    }

    /**
     * @MethodName: deleteBuilding 删除建筑物
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 13:48
     **/
    @RequestMapping(value = "/deleteBuilding/{projectId}")
    public Data deleteBuilding(String json) {
        return buildingService.deleteBuilding(json);
    }



    @RequestMapping(value = "/findBuildingFloor/{projectId}")
    public Data findBuildingFloor( String json) {
        return buildingService.findBuildingFloor(json);
    }

    /**@MethodName: projectCacheInit 添加建筑物初始化缓存
     * @Description:
     * @Param: []
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/10/30 9:51
     **/
    @RequestMapping(value = "/buildingCacheInit")
    public void projectCacheInit() {
        buildingService.buildingCacheInit();
    }
    /**@MethodName: projectCacheInit 添加建筑物初始化缓存
     * @Description:
     * @Param: []
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/10/30 9:51
     **/
    @RequestMapping(value = "/projectCacheBuildingFloorInit")
    public void projectCacheBuildingFloorInit() {
        buildingService.projectCacheBuildingFloorInit();
    }

    /**
     *
     * 功能描述:以sql文,查询建筑物列表
     *
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/11/26 10:53
     */
    @RequestMapping(value = "/findBuildingListBySql")
    public Data findBuildingListBySql(String json) {
        return buildingService.findBuildingListBySql(json);
    }
}
