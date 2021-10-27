package com.xjt.cloud.safety.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName Project 平台成员实体
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public class OrgUser extends BaseEntity {
    private Long[] ids;
    //组织架构ID
    private Long orgId;
    //组织架构ID
    private Long newOrgId;
    //用户ID
    private Long userId;
    //成员名称
    private String userName;
    //成员名称
    private String[] userNames;
    //角色id
    private Long roleId;
    //角色id
    private Long[] roleIds;
    //userIds
    private Long[] userIds;
    //组织结构Id
    private Long[] orgIds;
    //公司名称
    private String companyName;
    //部门名称
    private String departmentName;
    //手机号
    private String phone;
    //用户名称
    private String loginName;
    //用户激活状态 0、未激活 1、已激活
    private Integer status;
    //角色名称
    private String roleName;
    //验证码
    private String captcha;
    //邀请码
    private  String randomCode;
    //项目拥有者
    private Boolean isOwner;
    //部门名称
    private String depName;
    //名称
    private  String name;
    //证书等级
    private String certificateLevel;
    //证书编号
    private String certificateNumber;


    public OrgUser() {
    }

    public OrgUser(Long userId, Long projectId) {
        this.userId = userId;
        this.setProjectId(projectId);
    }

    public String getCertificateLevel() {
        return certificateLevel;
    }

    public void setCertificateLevel(String certificateLevel) {
        this.certificateLevel = certificateLevel;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Boolean isOwner) {
        this.isOwner = isOwner;
    }


    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String[] getUserNames() {
        return userNames;
    }

    public void setUserNames(String[] userNames) {
        this.userNames = userNames;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long[] getUserIds() {
        return userIds;
    }

    public void setUserIds(Long[] userIds) {
        this.userIds = userIds;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public void setStatus(Integer status) {
        this.status = status;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }



    public void initOrgUser(Long orgId, Long userId, Long projectId, String userName){
        this.orgId = orgId;
        this.userId = userId;
        this.setProjectId(projectId);
        this.userName = userName;
    }

    public Long[] getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(Long[] orgIds) {
        this.orgIds = orgIds;
    }

    public Long getNewOrgId() {
        return newOrgId;
    }

    public void setNewOrgId(Long newOrgId) {
        this.newOrgId = newOrgId;
    }

    @Override
    public boolean equals(Object obj) {
        // 这里使用==显示判断比较对象是否是同一对象
        if (this == obj) {
            return true;
        }
        // 对于任何非null的引用值x，x.equals(null)必须返回false
        if (obj == null) {
            return false;
        }
        // 通过 instanceof 判断比较对象类型是否合法
        if (!(obj instanceof OrgUser)) {
            return false;
        }
        // 对象类型强制转换，如果核心域比较相等，则返回true，否则返回false
        OrgUser other = (OrgUser) obj;
        // 如果两者相等，返回true（含两者皆空的情形），否则比较两者值是否相等
        if(other.getId().equals(this.getId())) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if(getId()!=null){
            return getId().hashCode();
        }
        return 0;
    }
}
