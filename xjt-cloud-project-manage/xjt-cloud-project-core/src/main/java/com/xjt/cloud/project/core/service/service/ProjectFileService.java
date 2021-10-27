package com.xjt.cloud.project.core.service.service;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: xjt-cloud-project-manage
 * @description:zhangZaiFa
 * @author: zxb
 * @create: 2020-05-18 14:25
 **/
public interface ProjectFileService {

    /**@MethodName: findProjectFileList
     * @Description: 查询项目文件列表
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/18 14:27
     **/
    Data findProjectFileList(String json);

    /**@MethodName: delProjectFile
     * @Description: 删除项目文件
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/18 14:27
     **/
    Data delProjectFile(String json);

    /**@MethodName: saveProjectFile
     * @Description: 保存项目文件
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/18 14:27
     **/
    Data saveProjectFile(String json);

    /**@MethodName: updateProjectFile
     * @Description: 修改项目文件
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/28 13:48
     **/
    Data updateProjectFile(String json);

    /**@MethodName: downProjectFile
     * @Description: 下载项目文件
     * @Param: [response, request, json]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/6/16 9:08
     **/
    void downProjectFile(HttpServletResponse response, HttpServletRequest request, String json);
}
