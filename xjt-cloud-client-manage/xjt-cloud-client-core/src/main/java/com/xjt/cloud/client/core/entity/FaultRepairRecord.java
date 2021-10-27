package com.xjt.cloud.client.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 故障报修 实体类
 *
 * @Author huanggc
 * @Date 2019/09/02
 **/
public class FaultRepairRecord {
    private String appId;
    private Date createTime;
    private Integer totalCount;
    private String deviceName;// 设备名称
    private String faultDescription;// 故障描述(故障原因)
    private Integer workOrderStatus;// 工单状态:  所有待办(1待指派,  2维修中,  3审核中 )       所有完成: 4已完成  5不予处理

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getFaultDescription() {
        if (StringUtils.isNotEmpty(faultDescription)){
            String[] split = faultDescription.split("xjtgzbx;");
            return split[0];
        }
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    public Integer getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(Integer workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }
}
