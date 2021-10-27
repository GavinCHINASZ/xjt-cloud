package com.xjt.cloud.fault.core.entity.fault;

import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;

/**
 * 故障报修 实体类
 *
 * @author huanggc
 * @date 2019/09/02
 **/
public class FaultRepairRecords {
    private Date createTime;
    private String deviceName;
    /**
     * 故障描述(故障原因)
     */
    private String faultDescription;
    /**
     * 工单状态:  所有待办(1待指派,  2维修中,  3审核中); 所有完成: 4已完成  5不予处理
     */
    private Integer workOrderStatus;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getFaultDescription() {
        if (StringUtils.isNotEmpty(faultDescription)) {
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
