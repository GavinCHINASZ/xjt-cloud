package com.xjt.cloud.task.core.entity.protect;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * 地铁 防护等级
 *
 * @author huangGuiChuan
 * @date 2020/09/27
 */
public class ProtectGrade extends BaseEntity {
    private Long parentId;

    /**
     * 防护等级 名称
     */
    private String gradeName;

    /**
     * 防护等级 代码
     */
    private String gradeCode;

    /**
     * 描述
     */
    private String memo;

    /**
     * 标记
     * 1 : FAS检修作业安全防护
     * 2 : 气体检修作业安全防护
     */
    private Integer sign;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }
}
