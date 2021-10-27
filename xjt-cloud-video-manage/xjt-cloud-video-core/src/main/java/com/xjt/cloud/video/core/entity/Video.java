package com.xjt.cloud.video.core.entity;


import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName Video
 * @Author dwt
 * @Date 2019-09-09 15:08
 * @Description video实体类
 * @Version 1.0
 */
public class Video extends BaseEntity {

    private Long parentId;//
    private Integer deviceType;// '设备类型  0：NVR设备，1：IPCamera设备',
    private String deviceIp;// '设备ip',
    private Integer devicePort;// '设备端口',
    private String userName;// '用户名',
    private String password;// '密码',
    private Long checkPointId;// '设备巡更点id',
    private Long deviceId;// '设备id',
    private Integer deviceStatus;// '设备状态  0：在线；1：离线',
    private Integer channel;//  '通道',
    private String deviceName;//设备名称

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public Integer getDevicePort() {
        return devicePort;
    }

    public void setDevicePort(Integer devicePort) {
        this.devicePort = devicePort;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
