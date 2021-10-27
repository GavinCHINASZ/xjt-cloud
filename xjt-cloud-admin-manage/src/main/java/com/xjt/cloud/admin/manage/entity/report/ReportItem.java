package com.xjt.cloud.admin.manage.entity.report;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @Auther: huanggc
 * @Date: 2019/11/01
 * @Description: 报表项 实体类
 */
public class ReportItem extends BaseEntity {// ?
    private Long reportSystemId;// 报表系统项 多对一reportItem
    private Long deviceCheckItemId;// 设备巡检项ID 多对一reportItem (旧 lk_mt_check_item)

    private String reportItemName;// 报表项 名称

    public Long getReportSystemId() {
        return reportSystemId;
    }

    public void setReportSystemId(Long reportSystemId) {
        this.reportSystemId = reportSystemId;
    }

    public Long getDeviceCheckItemId() {
        return deviceCheckItemId;
    }

    public void setDeviceCheckItemId(Long deviceCheckItemId) {
        this.deviceCheckItemId = deviceCheckItemId;
    }

    public String getReportItemName() {
        return reportItemName;
    }

    public void setReportItemName(String reportItemName) {
        this.reportItemName = reportItemName;
    }
}
