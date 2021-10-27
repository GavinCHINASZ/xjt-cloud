package com.xjt.cloud.admin.manage.dao.log;

import com.xjt.cloud.admin.manage.entity.log.ProjectRunLog;
import org.springframework.stereotype.Repository;

/**
 * 监听项目运行状态日志 DAO
 *
 * @author huanggc
 * @date 2020/09/08
 */
@Repository
public interface ProjectRunLogDao {

    /**
     * 保存 监听项目运行状态日志
     *
     * @param projectRunLog 监听项目运行状态日志实体
     * @author huanggc
     * @date 2020/09/08
     * @return Integer
     */
    Integer saveProjectRunLog(ProjectRunLog projectRunLog);

    /**
     * 查询 监听项目运行状态日志 数量
     *
     * @param projectRunLog 监听项目运行状态日志实体
     * @author huanggc
     * @date 2020/09/08
     * @return Integer
     */
    Integer findProjectRunLogCount(ProjectRunLog projectRunLog);

    /**
     * 更新 监听项目运行状态日志 数量
     *
     * @param projectRunLog 监听项目运行状态日志实体
     * @author huanggc
     * @date 2020/09/08
     * @return Integer
     */
    Integer updateProjectRunLog(ProjectRunLog projectRunLog);

    /**
     * 更新 监听项目运行状态日志 数量
     *
     * @param projectRunLog 监听项目运行状态日志实体
     * @author huanggc
     * @date 2020/09/08
     * @return Integer
     */
    Integer updateProjectRunLogDeleted(ProjectRunLog projectRunLog);
}
