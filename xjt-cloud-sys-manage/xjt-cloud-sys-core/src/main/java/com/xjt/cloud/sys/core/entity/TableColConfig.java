package com.xjt.cloud.sys.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/10/31 10:15
 * @Description: 页面表格显示配置实体类
 */
public class TableColConfig extends BaseEntity {

    private Long userId;//用户id
    private String type;//用户类型
    private String colsName;//全部列属性名
    private String colsTitle; //全部列标题

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColsName() {
        return colsName;
    }

    public void setColsName(String colsName) {
        this.colsName = colsName;
    }

    public String getColsTitle() {
        return colsTitle;
    }

    public void setColsTitle(String colsTitle) {
        this.colsTitle = colsTitle;
    }
}
