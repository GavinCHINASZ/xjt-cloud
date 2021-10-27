package com.xjt.cloud.iot.core.entity.gateway;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName Gateway
 * @Author dwt
 * @Date 2019-11-28 16:59
 * @Version 1.0
 */
public class FireGateway extends BaseEntity {
    private Long id;
    private Long projectId;
    //注册码
    private  String registerCode;

    //从机id
    private Integer slaveId;

    //开始位置
    private Integer startAddress;

    //位置数量
    private Integer addressAccount;

    //网关
    private String gateway;

    private Integer fireAlarmNo;


    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public Integer getSlaveId() {
        return slaveId;
    }

    public void setSlaveId(Integer slaveId) {
        this.slaveId = slaveId;
    }

    public Integer getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(Integer startAddress) {
        this.startAddress = startAddress;
    }

    public Integer getAddressAccount() {
        return addressAccount;
    }

    public void setAddressAccount(Integer addressAccount) {
        this.addressAccount = addressAccount;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public Integer getFireAlarmNo() {
        return fireAlarmNo;
    }

    public void setFireAlarmNo(Integer fireAlarmNo) {
        this.fireAlarmNo = fireAlarmNo;
    }
}
