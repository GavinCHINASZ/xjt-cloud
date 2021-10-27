package com.xjt.cloud.admin.manage.controller.project;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.project.*;
import com.xjt.cloud.admin.manage.service.service.project.ProjectService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 权限管理 Controller
 *
 * @author wangzhiwen
 * @date 2019/12/10 10:17
 */
@Controller
@RequestMapping("/project/")
public class ProjectController extends AbstractController {
    @Autowired
    private ProjectService projectService;

    /**
     * 功能描述:查询所有项目列表
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/5/23
     */
    @RequestMapping(value = "findAllProjectList")
    @ResponseBody
    public ScriptPage<Project> findAllProjectList(AjaxPage ajaxPage, Project project) {
        return projectService.findAllProjectList(ajaxPage, project);
    }

    /**
     * 功能描述:查询所有项目列表
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/5/23
     */
    @RequestMapping(value = "findAllProject")
    @ResponseBody
    public List<Project> findAllProject(Project project) {
        return projectService.findAllProject(project);
    }

    @RequestMapping(value = "findProject")
    @ResponseBody
    public Project findProject(Project project) {
        return projectService.findProject(project);
    }

    /**
     * 功能描述:查询项目审核记录
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/5/23
     */
    @RequestMapping(value = "findProjectReviewRecordList")
    @ResponseBody
    public List<ProjectReviewRecord> findProjectReviewRecordList(ProjectReviewRecord projectReviewRecord) {
        return projectService.findProjectReviewRecordList(projectReviewRecord);
    }


    /**
     * 跳转项目列表
     *
     * @return org.springframework.web.servlet.ModelAndView
     * @author zhangZaiFa
     * @date 2019/12/10 14:14
     **/
    @RequestMapping("toProjectListPage")
    public ModelAndView toProjectListPage() {
        return new ModelAndView("project/projectList");
    }

    /**
     * projectReview 项目审核
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @param json 参数
     * @author zhangZaiFa
     * @date 2019/10/24 13:56
     **/
    @RequestMapping(value = "/projectReview")
    @ResponseBody
    public Data projectReview(String json) {
        return projectService.projectReview(json);
    }

    /**
     * projectReview 修改项目
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @param project 项目
     * @author zhangZaiFa
     * @date 2019/10/24 13:56
     **/
    @RequestMapping(value = "/modifyProject")
    @ResponseBody
    public Data modifyProject(Project project) {
        return projectService.modifyProject(project);
    }

    /**
     * @param building 建筑物
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.project.Building>
     * 查询建筑物列表
     * @author wangzhiwen
     * @date 2020/8/27 15:43
     */
    @RequestMapping(value = "findBuildingList")
    @ResponseBody
    public List<Building> findBuildingList(Building building) {
        return projectService.findBuildingList(building);
    }

    /**
     * 查询建筑物楼层列表
     *
     * @param buildingFloor 楼层
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.project.BuildingFloor>
     * @author wangzhiwen
     * @date 2020/8/27 15:43
     */
    @RequestMapping(value = "findBuildIngFloorList")
    @ResponseBody
    public List<BuildingFloor> findBuildIngFloorList(BuildingFloor buildingFloor) {
        return projectService.findBuildIngFloorList(buildingFloor);
    }

    /**
     * 保存 报警等级
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/01/19
     **/
    @RequestMapping("saveProjectMsgLevel")
    @ResponseBody
    public Data saveProjectMsgLevel(String  json){
        return projectService.saveProjectMsgLevel(json);
    }
}
