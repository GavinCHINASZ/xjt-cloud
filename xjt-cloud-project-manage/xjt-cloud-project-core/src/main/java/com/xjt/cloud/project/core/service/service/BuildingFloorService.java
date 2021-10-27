package com.xjt.cloud.project.core.service.service;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.entity.BuildingFloor;

import java.util.Date;
import java.util.List;

/**
 * @ClassName BuildingFloorService 建筑物楼层
 * @Author zhangZaiFa
 * @Date 2019-07-31 15:15
 * @Description
 */
public interface BuildingFloorService {

    /**
     * @MethodName: saveBuildingFloor
     * @Description:
     * @Param: [buildingFloors, buildingId]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/7/31 16:26
     **/
    Data saveBuildingFloor(List<BuildingFloor> buildingFloors, Long buildingId);

    /**
     * @MethodName: deleteBuildingFloor  按建筑物ID删除楼层
     * @Description:
     * @Param: [buildingId]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 16:31
     **/
    Data deleteBuildingIdBuildingFloor(Long buildingId);

    /**@MethodName: findByBuildIngFloors 查询建造物楼层
     * @Description: 
     * @Param: [buildingId]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.BuildingFloor>
     * @Author: zhangZaiFa
     * @Date:2019/9/5 13:56 
     **/
    List<BuildingFloor> findByBuildIngFloors(Long buildingId);

    /**@MethodName: updateBuildingFloor 更新建筑物楼层信息
     * @Description:
     * @Param: [oldBuildingFloors, id]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/10/16 9:35
     **/
    Data updateBuildingFloor(List<BuildingFloor> oldBuildingFloors, Long id,Long projectId);

    /**
     *
     * 功能描述: 修改楼层信息
     *
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/5/8 11:28
     */
    Data modifyBuildingFloor(String json);

    /**@MethodName: findByBuildingAllList 查询所有建筑物楼层信息
     * @Description: 
     * @Param: []
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.BuildingFloor>
     * @Author: zhangZaiFa
     * @Date:2019/10/30 9:56
     **/
    List<BuildingFloor> findByBuildingAllList();
}
