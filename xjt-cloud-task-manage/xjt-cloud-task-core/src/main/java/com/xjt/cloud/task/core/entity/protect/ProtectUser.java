package com.xjt.cloud.task.core.entity.protect;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * 地铁 班前防护 作业人员
 *
 * @author huangGuiChuan
 * @date 2020/09/27
 */
public class ProtectUser extends BaseEntity {
    /**
     * 作业名称
     */
    private Long protectId;

    /**
     * 成员ID  p_org_user id
     */
    private Long userId;

    /**
     * 作业人ID
     */
    private Long[] userIds;

    public Long getProtectId() {
        return protectId;
    }

    public void setProtectId(Long protectId) {
        this.protectId = protectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long[] getUserIds() {
        return userIds;
    }

    public void setUserIds(Long[] userIds) {
        this.userIds = userIds;
    }
}
