package com.xjt.cloud.safety.core.entity.device;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.StringUtils;

/**
 * @ClassName RiskProblem
 * @Description  高风险问题管理
 * @Author wangzhiwen
 * @Date 2021/5/7 17:03
 **/
public class RiskProblem  extends BaseEntity {
    private Long checkProjectId;//客户ID',
    private Long[] ids;
    private String checkProjectName;//客户名称
    private Integer safetyType;//安全类型 1建筑防火  2消防设施及器材  3消防安全管理',
    private Integer safetyLevel;//风险类别 1低 2中  3高',
    private Integer majorFirePotential;//重大火灾隐患 1是  2否',
    private String majorFirePotentialDesc;//重大火灾隐患 1是  2否',
    private Long num;//数量',
    private String numDesc;//数量',
    private String address;//位置',
    private String problem;//问题描述',
    private String countermeasure;//对策和措施',
    private String inspectionBasis;//检验依据',
    private String aftermath;//后果',
    private String fileUrl;//相关附件',
    private Long deviceSysId;//设备系统id
    private String deviceSysName;//设备系统名称
    private Long deviceTypeId;//设备类型id
    private String deviceTypeName;//设备类型名称
    private Long deviceCheckItemId;//设备类型巡检项id
    private String deviceCheckItemName;//设备类型巡检项名称
    private Long checkRecordId;//评估检测id

    public String getNumDesc() {
        return num == null ? "" : num + "";
    }

    public void setNumDesc(String numDesc) {
        this.numDesc = numDesc;
    }

    public String getMajorFirePotentialDesc() {
        if(majorFirePotential == null){
            return "";
        }
        if(majorFirePotential != null && majorFirePotential == 1){
                return "是";
        }
        return "否";
    }

    public void setMajorFirePotentialDesc(String majorFirePotentialDesc) {
        this.majorFirePotentialDesc = majorFirePotentialDesc;
    }

    public Long getCheckRecordId() {
        return checkRecordId;
    }

    public void setCheckRecordId(Long checkRecordId) {
        this.checkRecordId = checkRecordId;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public String getCheckProjectName() {
        return checkProjectName;
    }

    public void setCheckProjectName(String checkProjectName) {
        this.checkProjectName = checkProjectName;
    }

    public Long getDeviceSysId() {
        return deviceSysId;
    }

    public void setDeviceSysId(Long deviceSysId) {
        this.deviceSysId = deviceSysId;
    }

    public String getDeviceSysName() {
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public Long getDeviceCheckItemId() {
        return deviceCheckItemId;
    }

    public void setDeviceCheckItemId(Long deviceCheckItemId) {
        this.deviceCheckItemId = deviceCheckItemId;
    }

    public String getDeviceCheckItemName() {
        return deviceCheckItemName;
    }

    public void setDeviceCheckItemName(String deviceCheckItemName) {
        this.deviceCheckItemName = deviceCheckItemName;
    }

    public Long getCheckProjectId() {
        return checkProjectId;
    }

    public void setCheckProjectId(Long checkProjectId) {
        this.checkProjectId = checkProjectId;
    }

    public Integer getSafetyType() {
        return safetyType;
    }

    public void setSafetyType(Integer safetyType) {
        this.safetyType = safetyType;
    }

    public Integer getSafetyLevel() {
        return safetyLevel;
    }

    public void setSafetyLevel(Integer safetyLevel) {
        this.safetyLevel = safetyLevel;
    }

    public Integer getMajorFirePotential() {
        return majorFirePotential;
    }

    public void setMajorFirePotential(Integer majorFirePotential) {
        this.majorFirePotential = majorFirePotential;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getAddress() {
        return StringUtils.isEmpty(address) ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProblem() {
        return StringUtils.isEmpty(problem) ? "" : problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getCountermeasure() {
        return StringUtils.isEmpty(countermeasure) ? "" : countermeasure;
    }

    public void setCountermeasure(String countermeasure) {
        this.countermeasure = countermeasure;
    }

    public String getInspectionBasis() {
        return inspectionBasis;
    }

    public void setInspectionBasis(String inspectionBasis) {
        this.inspectionBasis = inspectionBasis;
    }

    public String getAftermath() {
        return aftermath;
    }

    public void setAftermath(String aftermath) {
        this.aftermath = aftermath;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
