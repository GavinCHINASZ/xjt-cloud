package com.xjt.cloud.admin.manage.dao.project;

import com.xjt.cloud.admin.manage.entity.project.Building;
import com.xjt.cloud.admin.manage.entity.project.BuildingFloor;
import com.xjt.cloud.admin.manage.entity.project.Project;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDao {
    /**
     * 功能描述:查询所有项目列表
     * @Author zhangZaiFa
     * @Date 2019/5/23
     * @return com.xjt.cloud.commons.utils.Data
     */
    List<Project> findAllProjectList(Project project);

    /**
     * 功能描述:查询所有项目數量
     * @Author zhangZaiFa
     * @Date 2019/5/23
     * @return com.xjt.cloud.commons.utils.Data
     */
    Integer findAllProjectListPageCount(Project project);

    Project findProject(Project project);

    int modifyProject(Project project);
    /**
     * @Description 查询建筑物楼层列表
     *
     * @param buildingFloor
     * @author wangzhiwen
     * @date 2020/8/27 15:43
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.project.BuildingFloor>
     */
    List<BuildingFloor> findBuildIngFloorList(BuildingFloor buildingFloor);

    /**
     * @Description 查询建筑物列表
     *
     * @param building
     * @author wangzhiwen
     * @date 2020/8/27 15:43
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.project.Building>
     */
    List<Building> findBuildingList(Building building);
}
