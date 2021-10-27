package com.xjt.cloud.project.core.entity;

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
    private Integer type;//1、app  2、web 3app首页模版
    private String appModuleType;//APP模块类型
    private String webModuleType;//Web模块类型
    //模块类型  CCCF查询 0, 巡查 1, 保养 2, 检查 3, 火警主机 4, 火眼警报 5, 电气火灾 6, 可燃气 7, 智能烟感 8, 消火栓 9,
    // 水压监测 10, 水浸监测 11, 设备登记 12, 设备管理 13, 维保记录 14, 故障报修 15, 值班记录 16, 离线巡检 17, 审核 18, 报表 19,
    // 极早期预警 20, 月度工单 21, 平面图 22, 消防维保日报 23 扫码巡检 24, NFC巡检 25, 更多 26, 项目管理 27, 声光报警 28,
    // 任务管理 29, 电气火灾 30, 类脑火眼 31,
    private String moduleType;

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
