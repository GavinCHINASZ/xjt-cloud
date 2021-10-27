package com.xjt.cloud.task.core.entity;

import java.util.List;

/**
 * @ClassName TaskPointUpload
 * @Author dwt
 * @Date 2020-03-24 14:18
 * @Description 任务巡查点导入查询设备
 * @Version 1.0
 */
public class TaskPointUpload {
    private Long projectId;
    private List<PointUpload> pointUploadList;
    private List<TaskSheetDevice> deviceList;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public List<PointUpload> getPointUploadList() {
        return pointUploadList;
    }

    public void setPointUploadList(List<PointUpload> pointUploadList) {
        this.pointUploadList = pointUploadList;
    }

    public List<TaskSheetDevice> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<TaskSheetDevice> deviceList) {
        this.deviceList = deviceList;
    }
}
