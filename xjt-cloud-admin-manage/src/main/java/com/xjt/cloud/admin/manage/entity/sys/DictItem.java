package com.xjt.cloud.admin.manage.entity.sys;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.dict.Dict;

import java.util.List;

/**
 * 字典项信息实体
 *
 * @author huanggc
 * @date 2021/1/15
 */
public class DictItem extends BaseEntity {
    /**
     * 字典信息ID
     */
    private Long dictId;

    /**
     * 字典编码
     */
    private String dictCode;


    /**
     * 字典项名字
     */
    private String itemName;

    /**
     * 字典项值
     */
    private String itemValue;

    /**
     * 字典项描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 状态（1启用，2不启用， 99删除）
     */
    private Integer status;

    /**
     * 字典项编码
     */
    private String itemCode;

    /**
     * 备注
     */
    private String memo;

    /**
     * 方案名称
     */
    private String modelName;

    /**
     * 事件级别 类型名称，与事件类型对应
     */
    private String eventTypeNameLevel;

    private List<DictItem> children;

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getText() {
        return this.itemName + "----->";
    }

    public List<DictItem> getChildren() {
        return children;
    }

    public void setChildren(List<DictItem> children) {
        this.children = children;
    }

    public String getEventTypeNameLevel() {
        return eventTypeNameLevel;
    }

    public void setEventTypeNameLevel(String eventTypeNameLevel) {
        this.eventTypeNameLevel = eventTypeNameLevel;
    }
}
