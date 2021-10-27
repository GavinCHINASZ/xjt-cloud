package com.xjt.cloud.safety.core.dao.project;

import com.xjt.cloud.safety.core.entity.project.Project;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName ProjectDao 项目Dao
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Repository
public interface ProjectDao {


    /***@MethodName: get  按ID查询项目
     * @Description:
     * @Param: [id]
     * @Return: com.xjt.cloud.project.core.entity.Project
     * @Author: zhangZaiFa
     * @Date: 13:48
     **/
    Project get(@Param("id") Long id);

    /***@MethodName: save  新增项目
     * @Description:
     * @Param: [entity]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date: 13:50
     **/
    Long save(Project entity);

    /***@MethodName: deleteProject  按id删除项目
     * @Description:
     * @Param: [ids]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date: 13:47
     **/
    Integer deleteProject(Project entity);


    /***@MethodName: updateProject  修改项目信息
     * @Description:
     * @Param: [project]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date: 13:47
     **/
    Integer updateProject(Project entity);


    /**
     * @MethodName: findByProjectList   按条件查询
     * @Description:
     * @Param: [search]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.Project>
     * @Author: zhangZaiFa
     * @Date: 13:46
     **/
    List<Project> findByProjectList(Project entity);

    /**
     *
     * 功能描述:查询所有项目信息列表
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/8/9 9:42
     */
    List<Project> findByProjectAllList();


    /**@MethodName: findByProjectName 按名称查询项目
     * @Description:
     * @Param: [projectName]
     * @Return: com.xjt.cloud.project.core.entity.Project
     * @Author: zhangZaiFa
     * @Date:2019/9/10 16:34
     **/
    Project findByProjectName(Project entity);

    /**@MethodName: projectTransfer 项目转让
     * @Description: 
     * @Param: [entity]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/10/12 14:48
     **/
    void projectTransfer(Project entity);

    /**@MethodName: projectReview 项目审核
     * @Description: 
     * @Param: [project]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/10/24 15:52 
     **/
    void projectReview(Project project);
}

