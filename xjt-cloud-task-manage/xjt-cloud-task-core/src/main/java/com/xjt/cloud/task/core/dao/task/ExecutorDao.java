package com.xjt.cloud.task.core.dao.task;/**
 * @ClassName ExecutorDao
 * @Author Administrator
 * @Date 2019-07-25 16:19
 * @Description TODO
 * @Version 1.0
 */

import com.xjt.cloud.task.core.entity.Executor;
import com.xjt.cloud.task.core.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@ClassName ExecutorDao
 *@Author dwt
 *@Date 2019-07-25 16:19
 *@Description: 执行者Dao
 *@Version 1.0
 */
@Repository
public interface ExecutorDao {

    /**
     *@Author: dwt
     *@Date: 2019-07-25 16:20
     *@Param: 执行者对象
     *@Return: 执行者列表
     *@Description: 筛选执行者对象
     */
   List<Executor> findExecutorList(Executor executor);

   /**
    *@Author: dwt
    *@Date: 2019-07-25 16:22
    *@Param:  执行者对象
    *@Return: 主键id
    *@Description: 保存执行者对象
    */
   Integer saveExecutor(Executor executor);
    /**
     *@Author: dwt
     *@Date: 2019-08-13 10:59
     *@Param: java.lang.Long
     *@Return: void
     *@Description 删除执行人
     */
    void deleteExecutorByTaskId(@Param("taskId") Long taskId, @Param("executorType") Integer executorType, @Param("taskIds") List<Long> taskIds);
    /**
     *@Author: dwt
     *@Date: 2019-10-12 16:05
     *@Param: java.lang.Long
     *@Return: 部门用户列表
     *@Description 根据任务id查询对应的执行者（部门用户列表）
     */
    List<User> findExecutorListByTaskId(@Param("executorType") int executorType,@Param("taskId") long taskId);
    /**
     *@Author: dwt
     *@Date: 2019-11-21 18:50
     *@Param: java.lang.Long
     *@Return: java.lang.Long
     *@Description 查询任务审核权限
     */
    Long findReviewAuthority(Executor executor);
}
