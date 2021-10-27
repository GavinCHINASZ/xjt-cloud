package com.xjt.cloud.task.core.entity.project;

import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;

import java.util.List;

/**
 * @ClassName Building
 * @Author dwt
 * @Date 2019-08-07 19:02
 * @Description 建筑设备
 * @Version 1.0
 */
public class TaskBuilding {
    private Long id;
    private String buildingName;
    private List<TaskFloor> taskFloorList;
    private Integer sel;
    private Integer notSel;

    private Long floorId;
    private String floorName;

    /**
     * 位置
     */
    private String pointLocation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public List<TaskFloor> getTaskFloorList() {
        return taskFloorList;
    }

    public void setTaskFloorList(List<TaskFloor> taskFloorList) {
        this.taskFloorList = taskFloorList;
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

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    /**
     *
     * 功能描述:得到建筑物楼层名称
     *
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/10/30 10:12
     */
    public String getFloorName(){
        if(getFloorId() != null && getFloorId() != 0){
            floorName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, getFloorId(), "floorName");
        }
        return floorName;
    }
}
