package com.xjt.cloud.report.core.entity.report;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @Auther: huanggc
 * @Date: 2019/11/01
 * @Description: 报表系统 实体类
 */
public class ReportSystem extends BaseEntity {
    private String reportName;// 报表名称(如:GA587)
    private String reportNo; // 报表编号(如:A1)

    private Integer type;// 类型(1检测、2维修、3保养) retype
    private String itemName;// 巡查项目名称, 巡查内容名称  如:自然排烟窗外观
    private Integer sortNo;// @NotNull   1 2 3 4 5 ......
    private Long parentId;// ReportSystemId, 巡查项目ID, 巡查项目 下有多个 巡查内容





    // private Long hierarchyId;// ? 是 ReportSystemId
    private String itemIds;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }
}
