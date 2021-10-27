package com.xjt.cloud.project.core.service.service;

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

    /**@MethodName: findProjectFunSubscript
     * @Description: 查询项目功能角标
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/6/1 13:44
     **/
    Data findProjectFunSubscript(String json);

    /**
     * 查询是否有权限
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/1/15 11:03
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findIsPermission(String json);

    /**
     * @Description 项目信息级别缓存初使化
     *
     * @author wangzhiwen
     * @date 2021/1/11 10:46
     * @return void
     */
    Data projectMsgLevelCacheInit();

    /**
     * @Description 查询app首页用户项目数据
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 10:41
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findAppHomeUserProjectData(String json);
}
