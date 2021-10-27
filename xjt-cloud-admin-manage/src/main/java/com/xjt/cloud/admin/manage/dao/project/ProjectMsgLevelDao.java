package com.xjt.cloud.admin.manage.dao.project;

import com.xjt.cloud.admin.manage.entity.project.ProjectMsgLevel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目信息级别配置
 *
 * @author huanggc
 * @date 2021/01/15
 */
@Repository
public interface ProjectMsgLevelDao {
    /**
     * 功能描述: 查询 消息等级列表
     *
     * @param projectMsgLevel ProjectMsgLevel
     * @return List<ProjectMsgLevel>
     * @author huanggc
     * @date 2019/5/23
     */
    List<ProjectMsgLevel> findProjectMsgLevelList(ProjectMsgLevel projectMsgLevel);

    /**
     * 功能描述: 更新 消息等级
     *
     * @param projectMsgLevel ProjectMsgLevel
     * @author huanggc
     * @date 2019/5/23
     * @return int
     */
    Integer updateProjectMsgLevelList(ProjectMsgLevel projectMsgLevel);

    /**
     * 功能描述: 保存 消息等级
     *
     * @param projectMsgLevel ProjectMsgLevel
     * @author huanggc
     * @date 2019/5/23
     * @return int
     */
    Integer saveProjectMsgLevelList(ProjectMsgLevel projectMsgLevel);

}
