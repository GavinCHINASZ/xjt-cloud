package com.xjt.cloud.project.core.service.service.project;

import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName ProjectService 项目
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public interface ProjectService extends BaseService {

    /**
     * @MethodName: findById 按ID查询
     * @Description:
     * @Param: [id]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date: 13:37
     **/
    Data findById(String id);

    /**
     * @MethodName: addProject 添加项目信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date: 13:37
     **/
    Data addProject(String json);

    /**
     * @MethodName: updateProject  修改项目信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date: 13:37
     **/
    Data updateProject(String json);

    /**
     * @MethodName: deleteProject  删除项目
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date: 13:37
     **/
    Data deleteProject(String json);

    /**
     * @MethodName: findByProjectList 按项目属性搜索项目
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date: 13:37
     **/
    Data findByProjectList(String json);


    /**
     * @MethodName: projectMenu 项目菜单
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/8/1 9:57
     **/
    Data projectMenu(String json);

    /**
     *
     * 功能描述: 项目信息缓存初使化
     *
     * @param
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/8/9 9:38
     */
    void projectCacheInit();

    /**@MethodName: projectTransfer 项目转让
     * @Description: 
     * @Param: [json]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/9/23 9:55 
     **/
    Data projectTransfer(String json);


    /**@MethodName: projectReview 项目审核
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/24 13:56
     **/
    Data projectReview(String json);

    /**
     * @MethodName: findByPassProjectList 查询已通过项目列表
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019-11-06 13:45
     **/
    Data findByPassProjectList(String json);

    /**@MethodName: projectHome 项目首页
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/11 9:42 
     **/
    Data projectHome(String json);

    /**@MethodName: findIsPhotoPermission
     * @Description: 查询是否有拍照权限
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/1/15 11:03
     **/
    Data findIsPhotoPermission(String json);

    /**@MethodName: findProjectStatistics
     * @Description: 查询项目统计信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/3/10 15:44
     **/
    Data findProjectStatistics(String json);

    /**@MethodName: findProjectHomeData
     * @Description: 查询项目首页数据
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/3/18 9:31
     **/
    Data findProjectHomeData(String json);

    Data getHtml(String json);
}
