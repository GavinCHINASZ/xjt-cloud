package com.xjt.cloud.project.core.dao.project;

import com.xjt.cloud.project.core.entity.BuildingFloor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: xjt-cloud-project-manage
 * @description:
 * @author: zxb
 * @create: 2019-07-31 16:28
 **/
@Repository
public interface BuildingFloorDao {

    /**
     * @MethodName: deleteBuildingIdBuildingFloor 按建筑物ID删除楼层
     * @Description:
     * @Param: [buildingId]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 16:32
     **/
    void deleteBuildingIdBuildingFloor(@Param("buildingId") Long buildingId);

    /**
     * @MethodName: deleteBuildingIdBuildingFloor 按建筑物ID删除楼层
     * @Description:
     * @Param: [buildingId]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 16:32
     **/
    void saveBuildingFloor(List<BuildingFloor> list);

    /**@MethodName: findByBuildIngFloors 查询建筑物楼层
     * @Description: 
     * @Param: [buildingId]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.BuildingFloor>
     * @Author: zhangZaiFa
     * @Date:2019/9/5 13:59 
     **/
    List<BuildingFloor> findByBuildIngFloors(@Param("buildingId") Long buildingId);

    /**@MethodName: updateBuildingFloor 更新楼层信息
     * @Description:
     * @Param: [buildingFloor]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/10/16 9:42
     **/
    void updateBuildingFloor(BuildingFloor buildingFloor);

    /**@MethodName: findByBuildingAllList 添加所有建筑物楼层信息
     * @Description: 
     * @Param: []
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.BuildingFloor>
     * @Author: zhangZaiFa
     * @Date:2019/10/30 9:56 
     **/
    List<BuildingFloor> findByBuildingAllList();
}
