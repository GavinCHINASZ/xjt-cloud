package com.xjt.cloud.safety.core.service.service.project;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.safety.core.entity.project.Building;

import java.util.List;

/**
 * @ClassName BuildingService 建筑物
 * @Author zhangZaiFa
 * @Date 2019-07-31 15:15
 * @Description
 */
public interface BuildingService {
    /**
     * @MethodName: findByProjectList 查询建筑物列表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 13:49
     **/
    Data findByProjectList(String json);

    /**
     * @MethodName: deleteBuilding 删除建筑物
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 13:49
     **/
    Data deleteBuilding(String json);

    /**
     * @MethodName: updateBuilding 修改建筑物
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 13:49
     **/
    Data updateBuilding(String json);

    /**
     * @MethodName: addBuilding 添加建筑物
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 13:49
     **/
    Data addBuilding(String json);

    /**
     * @MethodName: 查询建造物楼层
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/5 13:54
     **/
    Data findBuildingFloor(String json);

    /**
     * @MethodName: findByBuildingId 查询建筑物
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/16 13:42
     **/
    Data findByBuilding(String json);

    /**
     * @MethodName: buildingCacheInit 添加初始化缓存
     * @Description:
     * @Param: []
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/10/30 9:53
     **/
    void buildingCacheInit();

    /**
     * @MethodName: buildingCacheInit 添加初始化缓存
     * @Description:
     * @Param: []
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/10/30 9:53
     **/
    void projectCacheBuildingFloorInit();

    /**
     * 功能描述:以sql文,查询建筑物列表
     *
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/11/26 10:53
     */
    Data findBuildingListBySql(String json);

    /**
     * @MethodName: findCheckProjectBuilding
     * @Description:
     * @Param: [checkProjectId]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.project.Building>
     * @Author: zhangZaiFa
     * @Date:2020/4/14 18:34
     **/
    List<Building> findCheckProjectBuilding(Long checkProjectId);
}
