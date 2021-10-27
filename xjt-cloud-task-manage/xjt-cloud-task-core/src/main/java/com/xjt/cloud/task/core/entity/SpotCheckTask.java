package com.xjt.cloud.task.core.entity;

/**
 * @Author dwt
 * @Date 2020-06-23 10:07
 * @Version 1.0
 */
public class SpotCheckTask {
    private Long versionNo;
    private Integer deviceNum;
    private Integer checkedNum;
    private Integer faultNum;

    public Long getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }

    public Integer getCheckedNum() {
        return checkedNum;
    }

    public void setCheckedNum(Integer checkedNum) {
        this.checkedNum = checkedNum;
    }

    public Integer getFaultNum() {
        return faultNum;
    }

    public void setFaultNum(Integer faultNum) {
        this.faultNum = faultNum;
    }
}
