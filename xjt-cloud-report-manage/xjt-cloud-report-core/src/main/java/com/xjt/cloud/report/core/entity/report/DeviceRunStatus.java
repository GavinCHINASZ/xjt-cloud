package com.xjt.cloud.report.core.entity.report;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @Auther: huanggc
 * @Date: 2019/11/01
 * @Description: 设备运行 实体类
 */
public class DeviceRunStatus extends BaseEntity {
    // 对应旧系统的实体EquipmentRun
    private Long detailId;// detail 一 对  DeviceRunStatus 多
    private Integer sortNo;// 1 2 3 ......
    private Long deviceId;// 设备id  materialId
    private Integer controlStatus;// 控制状态 1正常  2故障
    private Integer runStatus;// 运行状态 1正常  2故障
    private String faultPlace = "";// 故障部位 troublePlace
    private String faultDetails = "";// 故障详情 troubleDetail


    // 以下表中没有的字段, 用与页面显示
    private String deviceName;// 设备名称
}
