package com.xjt.cloud.admin.manage.entity.device;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

/**
 * @ClassName DeviceQrNo
 * @Author dwt
 * @Date 2020-10-26 13:56
 * @Description 添加设备二维码实体
 * @Version 1.0
 */
public class DeviceQrNo{
    private String qrNo;
    private Long projectId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }
}
