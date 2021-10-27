package com.xjt.cloud.maintenance.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

/**
 * @ClassName AppModule APP模块管理
 * @Author zhangZaiFa
 * @Date 2019-12-30 15:15
 * @Description
 */
public class AppModule extends BaseEntity {
    private Long userId;//用户ID
    private Integer type;//1、app  2、web
    private String appModuleType;//APP模块类型
    private String webModuleType;//Web模块类型
    private String moduleType;//Web模块类型

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAppModuleType() {
        return appModuleType;
    }

    public void setAppModuleType(String appModuleType) {
        this.appModuleType = appModuleType;
    }

    public String getWebModuleType() {
        return webModuleType;
    }

    public void setWebModuleType(String webModuleType) {
        this.webModuleType = webModuleType;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
