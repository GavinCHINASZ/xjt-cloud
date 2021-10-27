package com.xjt.cloud.report.core.entity.report;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.ArabicToChineseUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.List;

/**
 * 日报报表 标题 实体类
 *
 * @author huanggc
 * @date 2020/07/06
 */
public class ReportFromTitle extends BaseEntity {

    // 标题名称
    private String titleName;
    // 排序
    private Integer sort;

    private String content;

    private List<ReportFromRecord> reportFromRecordList;

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getContent() {
        if (StringUtils.isEmpty(content)){
            return "";
        }
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ReportFromRecord> getReportFromRecordList() {
        return reportFromRecordList;
    }

    public void setReportFromRecordList(List<ReportFromRecord> reportFromRecordList) {
        this.reportFromRecordList = reportFromRecordList;
    }

    public String getSortDesc() {
        if (sort != null){
            return ArabicToChineseUtils.int2chineseNum(sort);
        }
        return "";
    }
}
