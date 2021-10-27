package com.xjt.cloud.project.core.dao.project;

import com.xjt.cloud.project.core.entity.project.Building;
import com.xjt.cloud.project.core.entity.project.Project;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: xjt-cloud-project-manage
 * @description:
 * @author: zxb
 * @create: 2019-07-31 14:05
 **/
@Repository
public interface BuildingDao {
    /**
     * @MethodName: findByProjectList 查询项目列表
     * @Description:
     * @Param: [building]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.Building>
     * @Author: zhangZaiFa
     * @Date:2019/7/31 14:24
     **/
    List<Building> findByProjectList(Building building);

    /**
     * @MethodName: findByProjectListCount 查询项目列表总数量
     * @Description:
     * @Param: [building]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2019/7/31 14:24
     **/
    Integer findByProjectListCount(Building building);

    /**
     * @MethodName: deleteBuilding 删除建筑物
     * @Description:
     * @Param: [list]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/7/31 14:25
     **/
    Integer deleteBuilding(List<Long> list);

    /**
     * @MethodName: addBuilding 添加建筑物
     * @Description:
     * @Param: [building]
     * @Return: Long
     * @Author: zhangZaiFa
     * @Date:2019/7/31 15:44
     **/
    Long addBuilding(Building building);

    /**
     * @MethodName: updateBuilding 修改建造物信息
     * @Description:
     * @Param: [building]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/7/31 16:18
     **/
    void updateBuilding(Building building);

    /**@MethodName: findByProjectAcreage 面积查询
     * @Description: 
     * @Param: [projectId]
     * @Return: java.lang.Long
     * @Author: zhangZaiFa
     * @Date:2019/10/15 11:09
     **/
    Long findByProjectAcreage(@Param("projectId")Long projectId,@Param("checkProjectId") Long checkProjectId);

    /**@MethodName: findByBuilding 查询建筑物
     * @Description: 
     * @Param: [building]
     * @Return: com.xjt.cloud.project.core.entity.Building
     * @Author: zhangZaiFa
     * @Date:2019/10/16 14:11
     **/
    Building findByBuilding(Building building);

    /**@MethodName: findByBuildingAllList 查询所有建筑物信息
     * @Description: 
     * @Param: []
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.Building>
     * @Author: zhangZaiFa
     * @Date:2019/10/30 10:05
     **/
    List<Building> findByBuildingAllList();

    /**@MethodName: findByBuildingName 查询名称是否存在
     * @Description: 
     * @Param: [building]
     * @Return: com.xjt.cloud.project.core.entity.Building
     * @Author: zhangZaiFa
     * @Date:2019/11/1 10:02
     **/
    Building findByBuildingName(Building building);




    /**
     *
     * 功能描述:以sql文,查询建筑物列表
     *
     * @param sql
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/11/26 10:53
     */
    List<Building> findBuildingListBySql(@Param("sql") String sql);

    /**@MethodName: findByProjectBuildInfo
     * @Description: 查询项目建筑物信息
     * @Param: [project]
     * @Return: com.xjt.cloud.project.core.entity.Project
     * @Author: zhangZaiFa
     * @Date:2020/3/10 15:48
     **/
    Project findByProjectBuildInfo(Project project);

    /**@MethodName: findScreenProjectMapData
     * @Description: 查询大屏项目地图数据
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/9 9:45
     **/
    List<Building> findScreenProjectMapData(Building building);
}
