package com.xjt.cloud.project.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.ProjectFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName ProjectFileController 项目档案
 * @Author zhangZaiFa
 * @Date 2020-05-18 15:15
 * @Description
 */
@RestController
@RequestMapping("/project/file")
public class ProjectFileController extends AbstractController {

   @Autowired
   private ProjectFileService projectFileService;

    /**@MethodName: findByDepartmentList
     * @Description: 查询项目文件列表
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/18 14:24
     **/
    @RequestMapping(value = "findProjectFileList/{projectId}")
    public Data findProjectFileList(String json){
        return projectFileService.findProjectFileList(json);
    }



}
