package com.xjt.cloud.admin.manage.service.service.project;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.project.Building;
import com.xjt.cloud.admin.manage.entity.project.BuildingFloor;
import com.xjt.cloud.admin.manage.entity.project.Project;
import com.xjt.cloud.admin.manage.entity.project.ProjectReviewRecord;
import com.xjt.cloud.commons.utils.Data;

import java.util.List;

public interface ProjectService {

    /**
     * 功能描述:查询所有项目列表
     * @Author zhangZaiFa
     * @Date 2019/5/23
     * @return com.xjt.cloud.commons.utils.Data
     */
    ScriptPage<Project> findAllProjectList(AjaxPage ajaxPage, Project project);

    /**
     * 功能描述:查询所有项目列表
     * @Author zhangZaiFa
     * @Date 2019/5/23
     * @return com.xjt.cloud.commons.utils.Data
     */
    List<Project> findAllProject(Project project);

    /**
     * 功能描述:查询项目审核记录
     * @Author zhangZaiFa
     * @Date 2019/5/23
     * @return com.xjt.cloud.commons.utils.Data
     */
    List<ProjectReviewRecord> findProjectReviewRecordList(ProjectReviewRecord projectReviewRecord);

    Project findProject(Project project);

    /**@MethodName: projectReview 项目审核
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/24 13:56
     **/
    Data projectReview(String json);

    /**@MethodName: projectReview 修改项目
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/24 13:56
     **/
    Data modifyProject(Project project);

    /**
     *
     * 功能描述: 示例项目任务
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/7/23 16:29
     */
    void examplesProjectTask();

    /**
     * @Description 查询建筑物列表
     *
     * @param building
     * @author wangzhiwen
     * @date 2020/8/27 15:43
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.project.Building>
     */
    List<Building> findBuildingList(Building building);

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
     * 保存 报警等级
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/01/19
     **/
    Data saveProjectMsgLevel(String json);
}
