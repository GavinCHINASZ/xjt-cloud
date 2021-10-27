package com.xjt.cloud.project.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.OrganizationService;
import com.xjt.cloud.project.core.service.service.ProjectFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @ClassName ProjectFileController 组织架构
 * @Author zhangZaiFa
 * @Date 2020-05-18 15:15
 * @Description
 */
@RestController
@RequestMapping("/project/file")
public class ProjectFileController extends AbstractController {

    @Autowired
    private ProjectFileService projectFileService;

    /**
     * @MethodName: findByDepartmentList
     * @Description: 查询项目文件列表
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/18 14:24
     **/
    @RequestMapping(value = "findProjectFileList/{projectId}")
    public Data findProjectFileList(String json) {
        return projectFileService.findProjectFileList(json);
    }


    /**
     * @MethodName: delProjectFile
     * @Description: 删除项目文件
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/18 14:26
     **/
    @RequestMapping(value = "delProjectFile/{projectId}")
    public Data delProjectFile(String json) {
        return projectFileService.delProjectFile(json);
    }

    /**
     * @MethodName: updateProjectFile
     * @Description: 修改项目文件
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/18 14:26
     **/
    @RequestMapping(value = "updateProjectFile/{projectId}")
    public Data updateProjectFile(String json) {
        return projectFileService.updateProjectFile(json);
    }

    /**
     * @MethodName: saveProjectFile
     * @Description: 保存项目文件
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/18 14:26
     **/
    @RequestMapping(value = "saveProjectFile/{projectId}")
    public Data saveProjectFile(String json) {
        return projectFileService.saveProjectFile(json);
    }


    /**@MethodName: downloadProjectLog
     * @Description:  下载项目文件
     * @Param: [response, request, json]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/6/16 9:08
     **/
    @RequestMapping(value = "downProjectFile/{projectId}")
    public void downProjectFile(HttpServletResponse response, HttpServletRequest request, String json) {
        projectFileService.downProjectFile(response, request, json);
    }

}
