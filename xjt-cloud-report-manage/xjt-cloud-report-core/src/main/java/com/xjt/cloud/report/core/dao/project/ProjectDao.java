package com.xjt.cloud.report.core.dao.project;

import com.xjt.cloud.report.core.entity.project.Project;

/**
 * @ClassName ProjectDao 项目Dao
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public interface ProjectDao {

    /**
     * 功能描述: 根据 id 查询实体
     *
     * @param id
     * @auther: huanggc
     * @date: 2019/11/04
     * @return: Project 项目实体
     */
    Project findById(Long id);
}

