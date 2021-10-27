package com.xjt.cloud.project.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProjectController 项目
 *
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@RestController
@RequestMapping("/project")
public class ProjectController extends AbstractController {

    @Autowired
    private ProjectService projectService;

    /**
     *  findByPassProjectList 查询已通过项目列表
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019-11-06 13:45
     **/
    @RequestMapping(value = "/findByPassProjectList")
    public Data findByPassProjectList(String json) {
        return projectService.findByPassProjectList(json);
    }

    /** projectHome 项目首页
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/11 9:42
     **/
    @RequestMapping(value = "/projectHome")
    public Data projectHome(String json) {
        return projectService.projectHome(json);
    }

    /** findIsPhotoPermission
     * 查询是否有拍照权限
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/1/15 11:03
     **/
    @RequestMapping(value = "/findIsPhotoPermission")
    public Data findIsPhotoPermission(String json) {
        return projectService.findIsPhotoPermission(json);
    }

    /**
     * @MethodName: get 按Id查询
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date: 14:18
     **/
    @RequestMapping(value = "/findById/{projectId}")
    public Data findById(String json) {
        return projectService.findById(json);
    }

    /**
     * 查询是否有权限
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/1/15 11:03
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "/findIsPermission")
    public Data findIsPermission(String json) {
        return projectService.findIsPermission(json);
    }

    /** 
     * findProjectStatistics
     * 查询项目统计信息
     *
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/3/10 15:44
     **/
    @RequestMapping(value = "/findProjectStatistics")
    public Data findProjectStatistics(String json) {
        return projectService.findProjectStatistics(json);
    }

    /** findProjectFunSubscript
     * 查询项目功能角标
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/6/1 13:43
     **/
    @RequestMapping(value = "/findProjectFunSubscript")
    public Data findProjectFunSubscript(String json) {
        return projectService.findProjectFunSubscript(json);
    }

    /**
     * @Description 查询app首页用户项目数据
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 10:41
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping("/findAppHomeUserProjectData")
    public Data findAppHomeUserProjectData(String json){
        return projectService.findAppHomeUserProjectData(json);
    }


}
