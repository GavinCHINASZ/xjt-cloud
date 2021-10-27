package com.xjt.cloud.project.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/29 0029 13:45
 * @Description:
 */
public class User extends BaseEntity {
    //用户名
    private String loginName;
    //密码
    private String password;
    //员工姓名(成员姓名)
    private String userName;
    //手机号码
    private String phone;
    /**
     * 证书类型
     * 1消防工程师一级，2消防工程师二级，3消防设施操作员五级/初级技能，4消防设施操作员四级/中级技能，5消防设施操作员三级/高级技能，
     * 6消防设施操作员二级/技师，7消防设施操作员一级/高级技师
     */
    private Integer certificateLevel;
    // 证书编号
    private String certificateNumber;
    // 资质证书
    private String certificate;
    // 所属项目角色ID
    private Long projectRoleId;
    //所属项目名称
    private String projectName;
    //角色ID
    private Long roleId;
    //角色ID
    private Long[] roleIds;
    //角色名称
    private String roleName;
    //公司名称
    private String companyName;
    //部门名称
    private String departmentName;
    private boolean orgIdNull;//设置部门id为空
    private Long parentId;
    //序号
    private Long rowNum;
    //验证码
    private String captcha;
    //部门ID
    private Long orgId;
    private Long taskId;
    private Long buildingId;
    private Long buildingFloorId;
    //部门ID
    private Long appOrgId;
    private Long appTaskId;
    private Long appTaskParentId;
    private Long appBuildingId;
    private Long appBuildingFloorId;

    public Long getAppOrgId() {
        return appOrgId;
    }

    public void setAppOrgId(Long appOrgId) {
        this.appOrgId = appOrgId;
    }

    public Long getAppTaskId() {
        return appTaskId;
    }

    public void setAppTaskId(Long appTaskId) {
        this.appTaskId = appTaskId;
    }

    public Long getAppTaskParentId() {
        return appTaskParentId;
    }

    public void setAppTaskParentId(Long appTaskParentId) {
        this.appTaskParentId = appTaskParentId;
    }

    public Long getAppBuildingId() {
        return appBuildingId;
    }

    public void setAppBuildingId(Long appBuildingId) {
        this.appBuildingId = appBuildingId;
    }

    public Long getAppBuildingFloorId() {
        return appBuildingFloorId;
    }

    public void setAppBuildingFloorId(Long appBuildingFloorId) {
        this.appBuildingFloorId = appBuildingFloorId;
    }

    public boolean isOrgIdNull() {
        return orgIdNull;
    }

    public void setOrgIdNull(boolean orgIdNull) {
        this.orgIdNull = orgIdNull;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
    public  void setId(Long userId){
        super.setId(userId);
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCertificateLevel() {
        return certificateLevel;
    }

    public void setCertificateLevel(Integer certificateLevel) {
        this.certificateLevel = certificateLevel;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getProjectRoleId() {
        return projectRoleId;
    }

    public void setProjectRoleId(Long projectRoleId) {
        this.projectRoleId = projectRoleId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long getRowNum() {
        return rowNum;
    }

    public void setRowNum(Long rowNum) {
        this.rowNum = rowNum;
    }

    public String getStatusDesc(){
        if(getStatus() != null){// 0未激活   1已激活
            if (getStatus() == 1){
                return "已激活";
            }
            return "未激活";
        }
        return null;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getBuildingFloorId() {
        return buildingFloorId;
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }
}
