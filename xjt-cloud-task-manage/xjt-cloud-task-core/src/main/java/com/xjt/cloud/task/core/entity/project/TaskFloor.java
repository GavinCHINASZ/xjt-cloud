package com.xjt.cloud.task.core.entity.project;

import com.xjt.cloud.task.core.entity.TaskDeviceCheckPoint;

import java.util.List;

/**
 * @ClassName Floor
 * @Author dwt
 * @Date 2019-08-07 19:04
 * @Description 建筑物楼层
 * @Version 1.0
 */
public class TaskFloor {
    private Long id;
    private String floorName;
    private Integer totalCount;
    private String buildingName;
    private List<TaskDeviceCheckPoint> checkPointList;
    private Integer sel;
    private Integer notSel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<TaskDeviceCheckPoint> getCheckPointList() {
        return checkPointList;
    }

    public void setCheckPointList(List<TaskDeviceCheckPoint> checkPointList) {
        this.checkPointList = checkPointList;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Integer getCheckPointCount(){
        Integer checkPointCount = 0;
        if(totalCount != null){
            checkPointCount = totalCount;
        }
        return checkPointCount;
    }

    public Integer getSel() {
        return sel;
    }

    public void setSel(Integer sel) {
        this.sel = sel;
    }

    public Integer getNotSel() {
        return notSel;
    }

    public void setNotSel(Integer notSel) {
        this.notSel = notSel;
    }
}
