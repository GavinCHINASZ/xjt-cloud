package com.xjt.cloud.iot.core.entity.fire;

/**
 * @ClassName FireAlarmAreaType
 * @Author dwt
 * @Date 2020-07-15 11:13
 * @Version 1.0
 */
public class FireAlarmAreaType {
    //回来名称
    private String loopName;
    //承包商
    private String contractor;
    //区域类型 0默认没有，1外围，2洁净区
    private Integer areaType;
    //项目id
    private Long projectId;
    //责任单位
    private String unit;

    public String getLoopName() {
        return loopName;
    }

    public void setLoopName(String loopName) {
        this.loopName = loopName;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public Integer getAreaType() {
        return areaType;
    }

    public void setAreaType(Integer areaType) {
        this.areaType = areaType;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
