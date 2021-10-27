package com.xjt.cloud.safety.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

/**
 * @ClassName Organization 公司、部门实体
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public class Organization extends BaseEntity {
    //类型  2、公司 3、部门
    private Integer orgType;
    //公司类型0维保;1物业;2维保和物业;3设备厂商;4业主;5保险公司;6其它
    private Integer comType;
    //公司名称或部门名称
    private String orgName;
    //父类ID
    private Long parentId;
    //负责人
    private String personName;
    //手机号
    private String phone;
    //所属公司
    private Long owerCompany;
    //用户ID
    private Long userId;
    //角色ID
    private Long roleId;
    //项目成员 ID
    private Long orgUserId;

    private Long depId;//部门id
    private String orgDepName;//部门名称
    private String name ;//名称搜索
    private String userName;//用户名称


    private List<Long> orgIds;

    private List<Long> ids;

    public Organization() {
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    //公司类型数组
    private List<Integer> comTypes;

    public List<Integer> getComTypes() {
        return comTypes;
    }

    public void setComTypes(List<Integer> comTypes) {
        this.comTypes = comTypes;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<Long> orgIds) {
        this.orgIds = orgIds;
    }

    public Long getOwerCompany() {
        return owerCompany;
    }

    public void setOwerCompany(Long owerCompany) {
        this.owerCompany = owerCompany;
    }

    public Integer getComType() {
        return comType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComType(Integer comType) {
        this.comType = comType;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public String getOrgName() {
        return orgName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getOrgUserId() {
        return orgUserId;
    }

    public void setOrgUserId(Long orgUserId) {
        this.orgUserId = orgUserId;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        if (!(obj instanceof Organization)) {
            return false;
        }
        // 对象类型强制转换，如果核心域比较相等，则返回true，否则返回false
        Organization other = (Organization) obj;
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


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
