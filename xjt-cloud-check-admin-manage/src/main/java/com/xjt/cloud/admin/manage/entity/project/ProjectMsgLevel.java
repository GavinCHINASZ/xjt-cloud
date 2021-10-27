package com.xjt.cloud.admin.manage.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * 项目信息级别配置表
 *
 * @author huanggc
 * @date 2021/01/15
 */
public class ProjectMsgLevel extends BaseEntity {
    /**
     * 项目id为0时，有模板名称
     */
    private String modelName;
    /**
     * 模板类型 1一般模板 2默认配置 3默认模板
     */
    private Integer type;
    /**
     * 状态0未激活 1已激活 99 删除
     */
    private Integer status;
    /**
     * 一级信息事件类型以;分组如:1;2;3
     */
    private String eventTypeLevel1;
    private String eventTypeLevel2;
    private String eventTypeLevel3;
    private String eventTypeLevel4;

    private String eventTypeNameLevel;
    /**
     * 事件级别1类型名称，与事件类型对应
     */
    private String eventTypeNameLevel1;
    private String eventTypeNameLevel2;
    private String eventTypeNameLevel3;
    private String eventTypeNameLevel4;

    /**
     * 字典项ID
     */
    private Long dictItemId;

    /**
     * 字典项名字
     */
    private String itemName;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEventTypeLevel1() {
        return eventTypeLevel1;
    }

    public void setEventTypeLevel1(String eventTypeLevel1) {
        this.eventTypeLevel1 = eventTypeLevel1;
    }

    public String getEventTypeLevel2() {
        return eventTypeLevel2;
    }

    public void setEventTypeLevel2(String eventTypeLevel2) {
        this.eventTypeLevel2 = eventTypeLevel2;
    }

    public String getEventTypeLevel3() {
        return eventTypeLevel3;
    }

    public void setEventTypeLevel3(String eventTypeLevel3) {
        this.eventTypeLevel3 = eventTypeLevel3;
    }

    public String getEventTypeLevel4() {
        return eventTypeLevel4;
    }

    public void setEventTypeLevel4(String eventTypeLevel4) {
        this.eventTypeLevel4 = eventTypeLevel4;
    }

    public String getEventTypeNameLevel() {
        return eventTypeNameLevel;
    }

    public void setEventTypeNameLevel(String eventTypeNameLevel) {
        this.eventTypeNameLevel = eventTypeNameLevel;
    }

    public String getEventTypeNameLevel1() {
        return eventTypeNameLevel1;
    }

    public void setEventTypeNameLevel1(String eventTypeNameLevel1) {
        this.eventTypeNameLevel1 = eventTypeNameLevel1;
    }

    public String getEventTypeNameLevel2() {
        return eventTypeNameLevel2;
    }

    public void setEventTypeNameLevel2(String eventTypeNameLevel2) {
        this.eventTypeNameLevel2 = eventTypeNameLevel2;
    }

    public String getEventTypeNameLevel3() {
        return eventTypeNameLevel3;
    }

    public void setEventTypeNameLevel3(String eventTypeNameLevel3) {
        this.eventTypeNameLevel3 = eventTypeNameLevel3;
    }

    public String getEventTypeNameLevel4() {
        return eventTypeNameLevel4;
    }

    public void setEventTypeNameLevel4(String eventTypeNameLevel4) {
        this.eventTypeNameLevel4 = eventTypeNameLevel4;
    }

    public Long getDictItemId() {
        return dictItemId;
    }

    public void setDictItemId(Long dictItemId) {
        this.dictItemId = dictItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
