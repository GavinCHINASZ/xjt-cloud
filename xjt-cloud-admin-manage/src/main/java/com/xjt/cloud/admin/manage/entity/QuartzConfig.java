package com.xjt.cloud.admin.manage.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.quartz.manage.BaseQuartzConfig;

import java.util.Date;

/**
 * 任务配置信息
 *
 * @author wangzhiwen
 * @date 2019/4/29 0029 13:45
 */
public class QuartzConfig extends BaseQuartzConfig {
    // BaseQuartzConfig
    private static final long serialVersionUID = 1L;
    //分组
    //private String group;
    //任务名称
    //private String name;

    //任务定时表达式*/1 * * * * ?
    //private String cron;

    // 0启用, 1停用, 99已删除
    //private Integer status;

    //任务描述
    //private String msg;

    // 任务入口类路径如com.xjt.cloud.sys.manage.task.ScheduleTask2
    //private String quartzClass;
    //最后处理时间
    private Date lastHandleTime;
    //任务运行日志
    private String taskLog;

    @JsonIgnore
    @JSONField(
            serialize = false
    )
    private Integer pageIndex = 0;

    @JsonIgnore
    @JSONField(
            serialize = false
    )
    private Integer pageSize = 15;

    @JsonIgnore
    @JSONField(
            serialize = false
    )
    private Integer totalCount;

    @JsonIgnore
    @JSONField(
            serialize = false
    )
    private String[] orderCols;
    @JsonIgnore
    @JSONField(
            serialize = false
    )
    private boolean orderDesc;

    private String orderStr;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(getId());
        sb.append(", group=").append(getGroup());
        sb.append(", name=").append(getName());
        sb.append(", cron=").append(getCron());
        sb.append(", status=").append(getStatus());
        sb.append(", msg=").append(getMsg());
        sb.append(", quartzClass=").append(getQuartzClass());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public Date getLastHandleTime() {
        return lastHandleTime;
    }

    public void setLastHandleTime(Date lastHandleTime) {
        this.lastHandleTime = lastHandleTime;
    }

    public String getTaskLog() {
        return taskLog;
    }

    public void setTaskLog(String taskLog) {
        this.taskLog = taskLog;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String[] getOrderCols() {
        return orderCols;
    }

    public void setOrderCols(String[] orderCols) {
        this.orderCols = orderCols;
    }

    public boolean isOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(boolean orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    @JsonIgnore
    @JSONField(
            serialize = false
    )
    public Integer getBeginIndex() {
        return (this.pageIndex - 1) * this.pageSize;
    }
}