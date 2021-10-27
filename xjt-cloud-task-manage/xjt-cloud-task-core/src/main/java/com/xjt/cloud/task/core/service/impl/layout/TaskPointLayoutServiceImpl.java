package com.xjt.cloud.task.core.service.impl.layout;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.task.core.dao.task.TaskPointLayoutDao;
import com.xjt.cloud.task.core.entity.task.Task;
import com.xjt.cloud.task.core.entity.task.TaskPointLayout;
import com.xjt.cloud.task.core.service.service.TaskService;
import com.xjt.cloud.task.core.service.service.layout.TaskPointLayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务平面图布点
 *
 * @author wangzhiwen
 * @date 2021/3/9 16:09
 **/
@Service
public class TaskPointLayoutServiceImpl extends AbstractService implements TaskPointLayoutService {
    @Autowired
    private TaskPointLayoutDao taskPointLayoutDao;
    @Autowired
    private TaskService taskService;

    /**
     * 任务点位布署初使化方法
     *
     * @param taskPointLayout TaskPointLayout
     * @return boolean
     * @author wangzhiwen
     * @date 2021/3/11 10:37
     */
    @Override
    public boolean taskPointPositionInit(TaskPointLayout taskPointLayout) {
        // 判断布点信息是否已经存在
        int count = taskPointLayoutDao.findTaskPointPositionCount(taskPointLayout);
        if (count > 0) {
            taskPointLayoutDao.delTaskPointPosition(taskPointLayout);
        }
        // 查询巡检点对应的布点信息列表
        List<TaskPointLayout> checkPointLayoutList = taskPointLayoutDao.findFloorPointPositionByCheckPointList(taskPointLayout);

        if (CollectionUtils.isNotEmpty(checkPointLayoutList)) {
            int num = taskPointLayoutDao.saveTaskPointPositionList(checkPointLayoutList);
            if (num > 0) {
                List<TaskPointLayout> saveList = new ArrayList<>();
                // 一个点不用修改
                if (checkPointLayoutList.size() > 1) {
                    TaskPointLayout nextTpL;
                    TaskPointLayout temTpl;
                    int size = checkPointLayoutList.size();
                    for (; ; ) {
                        if (CollectionUtils.isEmpty(checkPointLayoutList)){
                            break;
                        }
                        temTpl = getFirstPoint(checkPointLayoutList);
                        if (temTpl == null){
                            SysLog.info("平面布点开始temTpl为空：");
                            break;
                        }
                        SysLog.info("平面布点开始id：" + temTpl.getPointLayoutId());
                        checkPointLayoutList.remove(temTpl);
                        size--;
                        SysLog.info("平面布点开始id：" + temTpl.getPointLayoutId());
                        temTpl.setPoints("[]");
                        saveList.add(temTpl);
                        if (temTpl.getNextId() != null && temTpl.getNextId() != 0) {
                            for (int n = 0; n < size; ) {
                                nextTpL = checkPointLayoutList.get(n);
                                if (temTpl.getNextId().equals(nextTpL.getPointLayoutId())){
                                    nextTpL.setPoints("[{\"x\":" + temTpl.getAxisX() + ",\"y\":" + temTpl.getAxisY() + "},{\"x\":" + nextTpL.getAxisX() + ",\"y\":" + nextTpL.getAxisY() + "}]");
                                    saveList.add(nextTpL);
                                    temTpl = nextTpL;
                                    checkPointLayoutList.remove(n);
                                    size--;
                                    if (temTpl.getNextId() != null && temTpl.getNextId() != 0) {
                                        n = 0;
                                    }else{
                                        break;
                                    }
                                }else{
                                    n++;
                                }
                            }
                        }
                    }
                }else if(checkPointLayoutList.size() > 1){
                    TaskPointLayout temTpl = checkPointLayoutList.get(0);
                    temTpl.setPoints("[]");
                }

                if (CollectionUtils.isNotEmpty(saveList)) {
                    num = taskPointLayoutDao.modifyTaskPointPositionList(saveList);
                }
                //num = taskPointLayoutDao.modifyTaskPointPositionList(checkPointLayoutList);

                if (num > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @Description 得到一段划线的开始点V
     *
     * @param checkPointLayoutList
     * @author wangzhiwen
     * @date 2021/4/16 9:35
     * @return com.xjt.cloud.task.core.entity.task.TaskPointLayout
     */
    private TaskPointLayout getFirstPoint(List<TaskPointLayout> checkPointLayoutList){
        boolean b;
        for (TaskPointLayout tem:checkPointLayoutList){
            b = false;
            for (TaskPointLayout tem2:checkPointLayoutList) {
                if (tem.getPointLayoutId().equals(tem2.getNextId())) {
                    b = true;
                    break;
                }
            }
            if (!b){
                return tem;
            }
        }
        return null;
    }

    /**
     * 功能描述:查询任务巡检点点位列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2020/4/30 15:12
     */
    @Override
    public Data findTaskPointPositionList(String json) {
        TaskPointLayout taskPointLayout = JSONObject.parseObject(json, TaskPointLayout.class);
        return asseData(taskPointLayoutDao.findTaskPointPositionList(taskPointLayout));
    }

    /**
     * 功能描述:查询任务巡检点点位列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2020/4/30 15:12
     */
    @Override
    public Data findAppTaskPointPositionList(String json) {
        TaskPointLayout taskPointLayout = JSONObject.parseObject(json, TaskPointLayout.class);
        return asseData(taskPointLayoutDao.findAppTaskPointPositionList(taskPointLayout));
    }

    /**
     * 功能描述:保存任务巡检点点位列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2020/4/30 15:12
     */
    @Override
    public Data saveTaskPointPosition(String json) {
        TaskPointLayout taskPointLayout = JSONObject.parseObject(json, TaskPointLayout.class);
        int num;
        if (taskPointLayout.getId() == null) {
            num = taskPointLayoutDao.saveTaskPointPosition(taskPointLayout);
            TaskPointLayout fpp = new TaskPointLayout();
            fpp.setNextId(taskPointLayout.getId());
            fpp.setId(taskPointLayout.getUpperId());
            // 修改上一个点的下一个节点id
            taskPointLayoutDao.modifyTaskPointPosition(fpp);
        } else {
            num = taskPointLayoutDao.modifyTaskPointPosition(taskPointLayout);
        }
        if (num > 0) {
            return asseData(taskPointLayout);
        }
        return Data.isFail();
    }

    /**
     * 功能描述:删除任务巡检点点位列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2020/4/30 15:12
     */
    @Override
    public Data delTaskPointPosition(String json) {
        TaskPointLayout taskPointLayout = JSONObject.parseObject(json, TaskPointLayout.class);
        modifyTaskPointPosition(taskPointLayout);
        int num = taskPointLayoutDao.delTaskPointPosition(taskPointLayout);
        if (num > 0) {
            Long[] checkPointIds = taskPointLayoutDao.findTaskPointPositionCheckPointList(taskPointLayout);
            Task task = new Task();
            task.setProjectId(taskPointLayout.getProjectId());
            task.setId(taskPointLayout.getTaskId());
            task.setCheckPointIds(checkPointIds);
            taskService.modifyTaskCheckPoint(task);
        }
        return asseData(num);
    }

    /**
     * 功能描述:删除任务巡检点点位列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2020/4/30 15:12
     */
    @Override
    public Data delTaskPointPositionByPoint(String json) {
        TaskPointLayout taskPointLayout = JSONObject.parseObject(json, TaskPointLayout.class);
        taskPointLayout = taskPointLayoutDao.findPointPositionIds(taskPointLayout);
        if (taskPointLayout != null && StringUtils.isNotEmpty(taskPointLayout.getIds())) {
            // 判断是否存在布点信息
            modifyTaskPointPosition(taskPointLayout);
            int num = taskPointLayoutDao.delTaskPointPosition(taskPointLayout);
            return asseData(num);
        }
        return Data.isSuccess();
    }

    /**
     * 功能描述: 修改上一节点与下一节点信息
     *
     * @param taskPointLayout TaskPointLayout
     * @return int
     * @author wangzhiwen
     * @date 2020/6/28 11:17
     */
    private int modifyTaskPointPosition(TaskPointLayout taskPointLayout) {
        TaskPointLayout fpp = new TaskPointLayout();
        //if (taskPointLayout.getNextId() != null) {
        fpp.setNextId(0L);
        fpp.setOldNextId(taskPointLayout.getId());
        fpp.setOldNextIds(taskPointLayout.getIds());
        // 修改上一个点的下一个节点id
        taskPointLayoutDao.modifyTaskPointPosition(fpp);
        //}
        //if (taskPointLayout.getUpperId() != null) {
        fpp = new TaskPointLayout();
        fpp.setUpperId(0L);
        fpp.setPoints("");
        fpp.setOldUpperId(taskPointLayout.getId());
        fpp.setOldUpperIds(taskPointLayout.getIds());
        // 修改上一个点的下一个节点id
        return taskPointLayoutDao.modifyTaskPointPosition(fpp);
    }
}
