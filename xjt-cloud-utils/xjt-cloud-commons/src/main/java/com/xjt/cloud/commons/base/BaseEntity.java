package com.xjt.cloud.commons.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xjt.cloud.commons.utils.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/29 0029 13:55
 * @Description: 实体类基类
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity implements Serializable {
    //主键id
    private Long id;
    //创建时间
    private Date createTime;
    //最后更新时间
    private Date lastModifyTime;
    //当前页(页数从0开始计算)
    @JsonIgnore
    @JSONField(serialize = false)
    private Integer pageIndex = 0;
    //每页条数
    @JsonIgnore
    @JSONField(serialize = false)
    private Integer pageSize = 15;
    //总行数
    @JsonIgnore
    @JSONField(serialize = false)
    private Integer totalCount;
    //项目id
    private Long projectId;
    //是否删除
    protected Boolean deleted = Boolean.FALSE;
    //状态99为删除，其它自定义
    private Integer status;
    // 创建用户名 不重要的创建与修改用户信息时，不需要用户id
    private String createUserName;
    // 修改用户名
    private String updateUserName;
    //创建人
    private Long createUserId;
    //修改人用户ID
    private Long updateUserId;
    //查询排序的列名组合 如 name，sex
    @JsonIgnore
    @JSONField(serialize = false)
    private String[] orderCols;
    //是否是降序排序
    @JsonIgnore
    @JSONField(serialize = false)
    private boolean orderDesc;
    //版本号
    private String version;
    //排序字符串
    @JsonIgnore
    @JSONField(serialize = false)
    private String orderStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }
    @JsonIgnore
    @JSONField(serialize = false)
    public Integer getBeginIndex(){
        return (pageIndex - 1) * pageSize;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String[] getOrderCols() {
        if (orderCols != null && orderCols.length > 0){
            String[] strs = new String[orderCols.length];
            for (int i = 0;i < orderCols.length;i++){
                strs[i] = StringUtils.humpToLine(orderCols[i]);
            }
            return strs;
        }
        return null;
    }

    public void setOrderCols(String[] orderCols) {
        this.orderCols = orderCols;
    }

    public boolean isOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(boolean orderDesc) {
        this.orderDesc = orderDesc;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
