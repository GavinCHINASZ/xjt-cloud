package com.xjt.cloud.iot.core.entity.fire;

/**
 * @ClassName FireAlarmCode
 * @Author dwt
 * @Date 2019-10-11 15:06
 * @Description 火警主机编码表实体
 * @Version 1.0
 */
public class FireAlarmCode {
    private Long id;
    //项目id
    private Long projectId;
    //设备名称
    private String deviceName;
    //用户编码
    private String userCode;
    //主机号
    private Integer controllerNumber;
    //回路
    private String controllerLoop;
    //设备地址
    private String deviceAddress;
    //原始码
    private String rawCode;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getControllerNumber() {
        return controllerNumber;
    }

    public void setControllerNumber(Integer controllerNumber) {
        this.controllerNumber = controllerNumber;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getRawCode() {
        return rawCode;
    }

    public void setRawCode(String rawCode) {
        this.rawCode = rawCode;
    }

    public String getControllerLoop() {
        return controllerLoop;
    }

    public void setControllerLoop(String controllerLoop) {
        this.controllerLoop = controllerLoop;
    }
}
