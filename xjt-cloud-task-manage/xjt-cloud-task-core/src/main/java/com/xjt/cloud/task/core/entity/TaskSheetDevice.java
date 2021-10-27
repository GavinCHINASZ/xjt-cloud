package com.xjt.cloud.task.core.entity;

/**
 * @Author dwt
 * @Date 2020-05-08 11:12
 * @Version 1.0
 */
public class TaskSheetDevice {

    private Integer rowNum;
    private String deviceName;
    private String pointQrNo;
    private String pointLocation;

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPointQrNo() {
        return pointQrNo;
    }

    public void setPointQrNo(String pointQrNo) {
        this.pointQrNo = pointQrNo;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }
}
