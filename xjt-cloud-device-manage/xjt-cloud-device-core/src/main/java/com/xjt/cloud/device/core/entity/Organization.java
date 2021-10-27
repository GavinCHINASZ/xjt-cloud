package com.xjt.cloud.device.core.entity;


/**
 * @ClassName Organization 公司、部门实体
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public class Organization {
    private Long id;//公司id
    //公司名称
    private String orgName;

    private Long depId;//部门id
    private String orgDepName;//部门名称

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public String getOrgDepName() {
        return orgDepName;
    }

    public void setOrgDepName(String orgDepName) {
        this.orgDepName = orgDepName;
    }
}
