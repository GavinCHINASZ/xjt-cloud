package com.xjt.cloud.report.core.entity.report;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * 日报报表 内容 实体类
 *
 * @author huanggc
 * @date 2020/07/06
 */
public class ReportFromRecord extends BaseEntity {
    /**
     * 内容
     */
    private String content;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 日报ID
     */
    private Long reportFromId;
    /**
     * 标题ID
     */
    private Long reportFromTitleId;
    private String titleName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getReportFromId() {
        return reportFromId;
    }

    public void setReportFromId(Long reportFromId) {
        this.reportFromId = reportFromId;
    }

    public Long getReportFromTitleId() {
        return reportFromTitleId;
    }

    public void setReportFromTitleId(Long reportFromTitleId) {
        this.reportFromTitleId = reportFromTitleId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
}
