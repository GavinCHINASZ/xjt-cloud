package com.xjt.cloud.task.core.service.service.layout;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.entity.task.TaskPointLayout;

/**
 * @ClassName TaskPointLayoutService
 * @Description 任务平面图布点
 * @Author wangzhiwen
 * @Date 2021/3/9 16:09
 **/
public interface TaskPointLayoutService {

    /**
     * @param taskPointLayout
     * @return boolean
     * @Description 任务点位布署初使化方法
     * @author wangzhiwen
     * @date 2021/3/11 10:37
     */
   boolean taskPointPositionInit(TaskPointLayout taskPointLayout);

    /**
     *
     * 功能描述:查询任务巡检点点位列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:12
     */
    Data findTaskPointPositionList(String json);

    /**
     *
     * 功能描述:查询任务巡检点点位列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:12
     */
    Data findAppTaskPointPositionList(String json);

    /**
     *
     * 功能描述:保存任务巡检点点位列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:12
     */
    Data saveTaskPointPosition(String json);

    /**
     *
     * 功能描述:删除任务巡检点点位列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:12
     */
    Data delTaskPointPosition(String json);

    /**
     *
     * 功能描述:删除任务巡检点点位列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:12
     */
    Data delTaskPointPositionByPoint(String json);
}
