package com.xjt.cloud.quartz.manage;

import java.io.Serializable;


/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/29 0029 13:45
 * @Description:任务配置信息
 */
public class BaseQuartzConfig implements Serializable {
    private Long id;//主键id

    private String group;//分组

    private String name;//任务名称

    private String cron;//任务定时表达式*/1 * * * * ?

    private Integer status;//任务状态 0 为正常 1暂停

    private String msg;//任务描述

    private String quartzClass;//任务入口类路径如com.xjt.cloud.sys.manage.task.ScheduleTask2

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group == null ? null : group.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron == null ? null : cron.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public String getQuartzClass() {
        return quartzClass;
    }

    public void setQuartzClass(String quartzClass) {
        this.quartzClass = quartzClass == null ? null : quartzClass.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", group=").append(group);
        sb.append(", name=").append(name);
        sb.append(", cron=").append(cron);
        sb.append(", status=").append(status);
        sb.append(", msg=").append(msg);
        sb.append(", quartzClass=").append(quartzClass);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}