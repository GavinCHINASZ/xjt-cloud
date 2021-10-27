package com.xjt.cloud.safety.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

/**
 * @ClassName ProjectPermission 项目权限菜单实体
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public class Permission extends BaseEntity {
    //url
    private String url;

    //菜单名称
    private String permissionName;

    //对菜单进行排序
    private Integer sort;

    //标识
    private String sign;

    //父类ID(上级菜单ID)
    private Long parentId;

    //1平台，2项目基本，3是项目
    private Integer perType;

    //1.模块  2.菜单功能  3.组建权限
    private Integer permissionType;

    //角色Id
    private Long roleId;

    //是否拥有权限
    private boolean isPermission;


    //菜单LOGO
    private String imageUrl;

    //用户Id
    private Long userId;

    //id
    private List<Long> ids;

    public Permission() {
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    //子目录
    private List<Permission> child;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Permission> getChild() {
        return child;
    }

    public void setChild(List<Permission> child) {
        this.child = child;
    }


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public boolean isPermission() {
        return isPermission;
    }

    public void setPermission(boolean permission) {
        isPermission = permission;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getPerType() {
        return perType;
    }

    public void setPerType(Integer perType) {
        this.perType = perType;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
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
        if (!(obj instanceof Permission)) {
            return false;
        }
        // 对象类型强制转换，如果核心域比较相等，则返回true，否则返回false
        Permission other = (Permission) obj;
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
