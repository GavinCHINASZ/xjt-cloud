package com.xjt.cloud.task.core.dao.task;

import com.xjt.cloud.task.core.entity.task.TaskModelManage;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * TaskModelManageDao 模板管理
 *
 * @author huanggc
 * @date 2021/03/09
 */
@Repository
public interface TaskModelManageDao {
    /**
     * 查询 模板管理数量
     *
     * @param taskModelManage TaskModelManage
     * @return Integer
     * @author huanggc
     * @date 2021/03/09
     */
    Integer findTaskModelManageTotalCount(TaskModelManage taskModelManage);

    /**
     * 查询 模板管理list
     *
     * @param taskModelManage TaskModelManage
     * @return List<TaskModelManage>
     * @author huanggc
     * @date 2021/03/09
     */
    List<TaskModelManage> findTaskModelManageList(TaskModelManage taskModelManage);

    /**
     * 删除 模板管理list
     *
     * @param taskModelManage TaskModelManage
     * @return int
     * @author huanggc
     * @date 2021/03/09
     */
    int updateTaskModelManage(TaskModelManage taskModelManage);

    /**
     * 保存 模板管理
     *
     * @param taskModelManage TaskModelManage
     * @return int
     * @author huanggc
     * @date 2021/03/09
     */
    int saveTaskModelManage(TaskModelManage taskModelManage);

    /**
     * 查询 模板管理
     *
     * @param taskModelManage TaskModelManage
     * @return int
     * @author huanggc
     * @date 2021/03/09
     */
    TaskModelManage findTaskModelManage(TaskModelManage taskModelManage);
}
