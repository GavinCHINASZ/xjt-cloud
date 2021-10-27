package com.xjt.cloud.iot.core.entity.linkage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 联动设备表(真实设备) 实体类
 *
 * @author huanggc
 * @date 2020/08/25
 **/
public class LinkageDeviceTable extends BaseEntity {
    /**
     * 实体描述: 对应声光报警设备(真实设备)
     *
     */
    private Long[] ids;
    private Long[] projectIds;
    // 传感器ID
    private String sensorId;
    // 设备ID
    private Long deviceId;

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Long[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Long[] projectIds) {
        this.projectIds = projectIds;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}