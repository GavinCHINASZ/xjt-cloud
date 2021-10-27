package com.xjt.cloud.iot.core.entity.linkage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xjt.cloud.commons.base.BaseEntity;

/**
 * 联动设备(无数据库表)
 *
 * @author huanggc
 * @date 2020/08/18
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LinkageDeviceReport extends BaseEntity {
    private Integer totalCount;
    // 总设备数
    private Integer deviceTotal;
    // 异常设备数
    private Integer failDevice;
    // 离线数
    private Integer offLine;

    @Override
    public Integer getTotalCount() {
        return totalCount;
    }

    @Override
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getDeviceTotal() {
        return deviceTotal;
    }

    public void setDeviceTotal(Integer deviceTotal) {
        this.deviceTotal = deviceTotal;
    }

    public Integer getFailDevice() {
        return failDevice;
    }

    public void setFailDevice(Integer failDevice) {
        this.failDevice = failDevice;
    }

    public Integer getOffLine() {
        return offLine;
    }

    public void setOffLine(Integer offLine) {
        this.offLine = offLine;
    }
}
