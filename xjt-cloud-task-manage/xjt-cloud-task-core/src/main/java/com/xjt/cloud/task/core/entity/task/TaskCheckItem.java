package com.xjt.cloud.task.core.entity.task;

import java.util.List;

/**
 * @ClassName CheckItem
 * @Author dwt
 * @Date 2019-08-14 17:11
 * @Description 任务巡检项
 * @Version 1.0
 */
public class TaskCheckItem {
    private Long checkPointId;
    private List<TaskCheckItem> checkItemList;

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public List<TaskCheckItem> getCheckItemList() {
        return checkItemList;
    }

    public void setCheckItemList(List<TaskCheckItem> checkItemList) {
        this.checkItemList = checkItemList;
    }
}
