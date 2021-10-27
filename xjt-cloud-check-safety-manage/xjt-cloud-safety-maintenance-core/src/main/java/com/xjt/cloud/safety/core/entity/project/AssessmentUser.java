package com.xjt.cloud.safety.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;

/**
 * 评估成员
 *
 * @author huanggc
 * @date 2021/05/08
 */
public class AssessmentUser extends BaseEntity implements Cloneable{
    private Long[] ids;

    private Long checkProjectId;

    private Long orgId;
    private Long[] orgIds;
    /**
     * 用户ID
     */
    private Long userId;
    private Long[] userIds;

    /**
     * 成员名称
     */
    private String userName;

    /**
     * 1 项目负责人, 2成员
     */
    private Integer assessmentUserType;

    /**
     * 1 项目负责人, 2成员
     */
    private String assessmentUserTypeDesc;

    /**
     * 职务/职称
     */
    private String duties;

    /**
     * 执业资格证书编号
     */
    private String certificateCode;

    /**
     * 评估分工
     */
    private String assessmentDivision;
    private String formQuery;//用户对象

    public String getFormQuery() {
        return formQuery;
    }

    public void setFormQuery(String formQuery) {
        this.formQuery = formQuery;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Long getCheckProjectId() {
        return checkProjectId;
    }

    public void setCheckProjectId(Long checkProjectId) {
        this.checkProjectId = checkProjectId;
    }

    public String getAssessmentUserTypeDesc() {
        if (assessmentUserType != null){
            return assessmentUserType == 1 ? "项目负责人" : "成员";
        }
        return "";
    }

    public void setAssessmentUserTypeDesc(String assessmentUserTypeDesc) {
        this.assessmentUserTypeDesc = assessmentUserTypeDesc;
    }

    public void setAssessmentUserType(Integer assessmentUserType) {
        this.assessmentUserType = assessmentUserType;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long[] getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(Long[] orgIds) {
        this.orgIds = orgIds;
    }

    public Long[] getUserIds() {
        return userIds;
    }

    public void setUserIds(Long[] userIds) {
        this.userIds = userIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return StringUtils.isEmpty(userName) ? "" : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAssessmentUserType() {
        return assessmentUserType;
    }

    public String getDuties() {
        return StringUtils.isEmpty(duties) ? "" : duties;
    }

    public void setDuties(String duties) {
        this.duties = duties;
    }

    public String getCertificateCode() {
        return StringUtils.isEmpty(certificateCode) ? "" : certificateCode;
    }

    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode;
    }

    public String getAssessmentDivision() {
        return StringUtils.isEmpty(assessmentDivision) ? "" : assessmentDivision;
    }

    public void setAssessmentDivision(String assessmentDivision) {
        this.assessmentDivision = assessmentDivision;
    }

    @Override
    public AssessmentUser clone() {
        AssessmentUser assessmentUser = null;
        try{
            assessmentUser = (AssessmentUser)super.clone();
        }catch(CloneNotSupportedException e) {
            SysLog.error(e);
        }
        return assessmentUser;
    }
}
