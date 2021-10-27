package com.xjt.cloud.maintenance.core.dao.project;

import com.xjt.cloud.maintenance.core.entity.project.Project;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目Dao
 *
 * @author zhangZaiFa
 * @date 2019-07-29 15:15
 */
@Repository
public interface ProjectDao {

    /***
     * 按ID查询项目
     *
     * @param id id
     * @return com.xjt.cloud.project.core.entity.Project
     * @author zhangZaiFa
     * @date 13:48
     **/
    Project get(@Param("id") Long id);

    /***
     * save  新增项目
     *
     * @param project Project
     * @return java.lang.Integer
     * @author zhangZaiFa
     * @date 13:50
     **/
    Long save(Project project);

    /***
     * deleteProject  按id删除项目
     *
     * @param entity Project
     * @return java.lang.Integer
     * @author zhangZaiFa
     * @date 13:47
     **/
    Integer deleteProject(Project entity);


    /***
     * updateProject  修改项目信息
     *
     * @param entity  Project
     * @return java.lang.Integer
     * @author zhangZaiFa
     * @date 13:47
     **/
    Integer updateProject(Project entity);


    /**
     * findByProjectList   按条件查询
     *
     * @param entity Project
     * @return List<Project>
     * @author zhangZaiFa
     * @date 13:46
     **/
    List<Project> findByProjectList(Project entity);

    /**
     * 功能描述:查询所有项目信息列表
     *
     * @return List<Project>
     * @author wangzhiwen
     * @date 2019/8/9 9:42
     */
    List<Project> findByProjectAllList();

    /**
     * findByProjectName 按名称查询项目
     *
     * @param entity Project
     * @return com.xjt.cloud.project.core.entity.Project
     * @author zhangZaiFa
     * @date 2019/9/10 16:34
     **/
    Project findByProjectName(Project entity);

    /**
     * projectTransfer 项目转让
     *
     * @param entity Project
     * @author zhangZaiFa
     * @date 2019/10/12 14:48
     **/
    void projectTransfer(Project entity);

    /**
     * projectReview 项目审核
     *
     * @param project Project
     * @author zhangZaiFa
     * @date 2019/10/24 15:52
     **/
    void projectReview(Project project);

    /**
     * 根据 checkProjectId 查询项目
     *
     * @param checkProjectId checkProjectId
     * @return Project
     * @author huanggc
     * @date 2021/05/07
     **/
    Project findByCheckProjectId(@Param(value="checkProjectId") Long checkProjectId);
}

