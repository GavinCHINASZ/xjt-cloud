package com.xjt.cloud.task.core.dao.task;

import com.xjt.cloud.task.core.entity.task.TaskPointLayout;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName TaskPointLayoutDao
 * @Description 任务平面图布点
 * @Author wangzhiwen
 * @Date 2021/3/9 16:08
 **/
@Repository
public interface TaskPointLayoutDao {

    /**
     * @param taskPointLayout
     * @return List<TaskPointLayout>
     * @Description 以巡检点id查询巡检点所在楼层的所有布点信息
     * @author wangzhiwen
     * @date 2021/3/11 13:50
     */
   List<TaskPointLayout> findFloorPointPositionByFloorAllList(TaskPointLayout taskPointLayout);

    /**
     * @param taskPointLayout
     * @return List<TaskPointLayout>
     * @Description 以巡检点id查询巡检点布点信息
     * @author wangzhiwen
     * @date 2021/3/11 13:50
     */
    List<TaskPointLayout> findFloorPointPositionByCheckPointList(TaskPointLayout taskPointLayout);

    /**
     * @param taskPointLayout
     * @return int
     * @Description 查询任务的设备布点数
     * @author wangzhiwen
     * @date 2021/3/11 13:50
     */
    int findTaskPointPositionCount(TaskPointLayout taskPointLayout);

   /**
    * @param taskPointLayout
    * @return int
    * @Description 查询任务布点里的巡检点列表
    * @author wangzhiwen
    * @date 2021/3/11 13:50
    */
   Long[] findTaskPointPositionCheckPointList(TaskPointLayout taskPointLayout);

    /**
     * 功能描述:保存任务巡检点点位
     *
     * @param taskPointLayout
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:22
     */
    int saveTaskPointPosition(TaskPointLayout taskPointLayout);

    /**
     * 功能描述:保存任务巡检点点位
     *
     * @param list
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:22
     */
    int saveTaskPointPositionList(List<TaskPointLayout> list);

    /**
     *
     * 功能描述:list
     *
     * @param list
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:22
     */
    int modifyTaskPointPositionList(List<TaskPointLayout> list);
    /**
     *
     * 功能描述:修改任务巡检点点位
     *
     * @param taskPointLayout
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:22
     */
    int modifyTaskPointPosition(TaskPointLayout taskPointLayout);

    /**
     *
     * 功能描述:删除任务巡检点点位
     *
     * @param taskPointLayout
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:22
     */
    int delTaskPointPosition(TaskPointLayout taskPointLayout);

    /**
     *
     * 功能描述:查询任务巡检点点位信息列表
     *
     * @param taskPointLayout
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:22
     */
    List<TaskPointLayout> findTaskPointPositionList(TaskPointLayout taskPointLayout);

    /**
     *
     * 功能描述:查询任务巡检点点位信息列表
     *
     * @param taskPointLayout
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:22
     */
    List<TaskPointLayout> findAppTaskPointPositionList(TaskPointLayout taskPointLayout);

    /**
     *
     * 功能描述:以巡检点id查询布点信息
     *
     * @param taskPointLayout
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:22
     */
    TaskPointLayout findPointPositionIds(TaskPointLayout taskPointLayout);
}
