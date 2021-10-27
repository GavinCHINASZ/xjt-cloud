package com.xjt.cloud.task.core.dao.sys;

import com.xjt.cloud.task.core.entity.TaskExecutor;
import com.xjt.cloud.task.core.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserDao
 *
 * @author dwt
 * @date 2019-08-09 9:37
 * 用户Dao层接口
 */
public interface UserDao {

    /**
     * 根据角色id查询
     *
     * @param roleId   角色ID
     * @param userName 用户名称
     * @param orgIds   orgId
     * @return 部门用户列表
     * @author dwt
     * @date 2019-08-09 9:48
     */
    List<User> findUserByRoleIdCV5(@Param("roleId") Long roleId, @Param("userName") String userName, @Param("orgIds") Long[] orgIds);
    /**
     * 根据角色id查询
     *
     * @param roleId   角色ID
     * @param userName 用户名称
     * @param orgIds   orgId
     * @return 部门用户列表
     * @author dwt
     * @date 2019-08-09 9:48
     */
    List<User> findUserByRoleId(@Param("roleId") Long roleId, @Param("userName") String userName, @Param("orgIds") Long[] orgIds,@Param("projectId") Long projectId);

    /**
     * 根据部门用户角色id查询
     *
     * @param orgUserRoleId orgUserRoleId
     * @param userId        用户ID
     * @return 部门用户名称
     * @author dwt
     * @date 2019-08-09 9:50
     */
    String findUserNameByOrgUserRoleId(Long orgUserRoleId, Long userId);

    /**
     * 根据角色id,任务id,项目id查询
     *
     * @param taskExecutor TaskExecutor
     * @return 执行者名称列表
     * @author dwt
     * @date 2019-08-12 14:12
     */
    List<String> findUserNameByTaskId(TaskExecutor taskExecutor);

    /**
     * 查询用户信息
     *
     * @param userId        用户ID
     * @param orgUserRoleId orgUserRoleId
     * @return 用户实体
     * @author dwt
     * @date 2019-08-13 15:28
     */
    User findUser(Long userId, Long orgUserRoleId);

    /**
     * 根据项目id查询部门用户列表
     *
     * @param projectId 项目ID
     * @return 部门用户列表
     * @author dwt
     * @date 2019-10-12 15:55
     */
    List<User> findUserByProjectId(Long projectId);

    /**
     *@author dwt
     *@date 2019-12-13 16:30
     *@param java.lang.Long
     *@return java.lang.String
     *
     */
    //String findOrgUserNameByLoginId(@Param("userId") Long userId, @Param("projectId") Long projectId);

    /**
     * 查询消息推送人列表
     *
     * @param executorType executorType
     * @param taskId       任务ID
     * @return java.lang.Long
     * @author dwt
     * @date 2019-12-16 15:56
     */
    List<Long> findSendMessageUserIds(@Param("executorType") Integer executorType, @Param("taskId") Long taskId);

    /**
     * 地铁平面图查询用户信息
     *
     * @param user User
     * @return User
     * @author dwt
     * @date 2020-07-02 17:35
     */
    User findUserMessageByUserId(User user);

    /**
     *
     * @param taskExecutor TaskExecutor
     * @return 查询任务执行人
     * @author dwt
     * @date 2020-09-04 10:40
     */
    List<User> findUserByTaskId(TaskExecutor taskExecutor);

    /**
     * 查询部门名称
     *
     * @param projectId 项目ID
     * @param userId 用户ID
     * @return String 部门名称
     * @author huanggc
     * @date 2021/03/11
     */
    String getOrgNameString(@Param("projectId") Long projectId, @Param("userId") Long userId);
}
