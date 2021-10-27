package com.xjt.cloud.admin.manage.entity.sys;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:49
 * @Description: 问题帮助管理实体
 */
public class Faqs extends BaseEntity {
    //问题类型 1 消检通介绍  2功能介绍 3使用帮助
    private Integer faqsType;
    //问题标题
    private String contentTitle;
    //问题内容
    private String content;

    public Integer getFaqsType() {
        return faqsType;
    }

    public void setFaqsType(Integer faqsType) {
        this.faqsType = faqsType;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
