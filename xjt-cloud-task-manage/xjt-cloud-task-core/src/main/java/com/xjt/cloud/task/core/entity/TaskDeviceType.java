package com.xjt.cloud.task.core.entity;

import java.util.List;

/**
 * @ClassName DeviceType
 * @Author dwt
 * @Date 2019-08-08 14:11
 * @Description 系统设备类型
 * @Version 1.0
 */
public class TaskDeviceType {
    private Long id;
    private String deviceSysName;
    private Integer totalCount;
    //0:系统，1:设备类型
    private Integer type;
    private List<TaskDeviceType> taskDeviceTypeList;
    private List<TaskDeviceCheckPoint> checkPointList;
    private String deviceQrNo;
    private Integer sel;
    private Integer notSel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceSysName() {
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<TaskDeviceType> getTaskDeviceTypeList() {
        return taskDeviceTypeList;
    }



    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<TaskDeviceCheckPoint> getCheckPointList() {
        return checkPointList;
    }

    public void setCheckPointList(List<TaskDeviceCheckPoint> checkPointList) {
        this.checkPointList = checkPointList;
    }

    public void setTaskDeviceTypeList(List<TaskDeviceType> taskDeviceTypeList) {
        this.taskDeviceTypeList = taskDeviceTypeList;
    }

    public String getDeviceQrNo() {
        return deviceQrNo;
    }

    public void setDeviceQrNo(String deviceQrNo) {
        this.deviceQrNo = deviceQrNo;
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
