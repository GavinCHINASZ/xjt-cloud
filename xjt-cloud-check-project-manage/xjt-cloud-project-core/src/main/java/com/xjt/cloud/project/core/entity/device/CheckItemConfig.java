package com.xjt.cloud.project.core.entity.device;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/11 09:30
 * @Description: pc检测项配置
 */
public class CheckItemConfig extends BaseEntity{
    private Long checkProjectId;//'检测项目id',
    private Long deviceSysId;//'设备系统id',
    private Long checkPointId;//检测点id
    private String deviceSysName;// '设备系统名称',
    private String deviceSysCode;//'设备系统编码',
    private Long deviceTypeId;// '设备类型id',
    private String deviceTypeName;//'设备类型名称',
    private String deviceTypeCode;//'设备类型编码',
    private Long deviceCheckItemId;//'设备检测项id',
    private String checkName;//巡检项名称
    private String checkSpecification;//检查标准
    private String code;//编码
    private String grade;//等级
    private Integer checkTotal;//'pc配置检测总数',
    private String memo;//'备注',
    private Integer checkFailNum;//'检测不合格数量',
    private Integer checkOkNum;//'合格数',
    private Integer checkItemNum;//'已检项',
    private Integer failItemNum;//'不合格项',
    private Integer configItemNum;//'配置的检测项',
    private Integer checkNothingNum;//'无数',
    private Integer decisionResult;//判定结果　1合格　2不合格
    private Integer numNotEqual;//检测数量与配置数果是否相等 1 相等 2不相等
    private Long[] deviceSysIds;
    private String checkUserName;//检测人姓名

    public Long getCheckProjectId() {
        return checkProjectId;
    }

    public void setCheckProjectId(Long checkProjectId) {
        this.checkProjectId = checkProjectId;
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

    public String getDeviceSysCode() {
        return deviceSysCode;
    }

    public void setDeviceSysCode(String deviceSysCode) {
        this.deviceSysCode = deviceSysCode;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public String getDeviceTypeCode() {
        return deviceTypeCode;
    }

    public void setDeviceTypeCode(String deviceTypeCode) {
        this.deviceTypeCode = deviceTypeCode;
    }

    public Long getDeviceCheckItemId() {
        return deviceCheckItemId;
    }

    public void setDeviceCheckItemId(Long deviceCheckItemId) {
        this.deviceCheckItemId = deviceCheckItemId;
    }

    public Integer getCheckTotal() {
        return checkTotal;
    }

    public void setCheckTotal(Integer checkTotal) {
        this.checkTotal = checkTotal;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getCheckFailNum() {
        return checkFailNum;
    }

    public void setCheckFailNum(Integer checkFailNum) {
        this.checkFailNum = checkFailNum;
    }

    public Integer getCheckOkNum() {
        return checkOkNum;
    }

    public void setCheckOkNum(Integer checkOkNum) {
        this.checkOkNum = checkOkNum;
    }

    public Integer getDecisionResult() {
        return decisionResult;
    }

    public void setDecisionResult(Integer decisionResult) {
        this.decisionResult = decisionResult;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getCheckSpecification() {
        return checkSpecification;
    }

    public void setCheckSpecification(String checkSpecification) {
        this.checkSpecification = checkSpecification;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getCheckItemNum() {
        return checkItemNum;
    }

    public void setCheckItemNum(Integer checkItemNum) {
        this.checkItemNum = checkItemNum;
    }

    public Integer getConfigItemNum() {
        return configItemNum;
    }

    public void setConfigItemNum(Integer configItemNum) {
        this.configItemNum = configItemNum;
    }

    public Integer getCheckNothingNum() {
        return checkNothingNum;
    }

    public void setCheckNothingNum(Integer checkNothingNum) {
        this.checkNothingNum = checkNothingNum;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public Integer getFailItemNum() {
        return failItemNum;
    }

    public void setFailItemNum(Integer failItemNum) {
        this.failItemNum = failItemNum;
    }

    public Integer getNumNotEqual() {
        return numNotEqual;
    }

    public void setNumNotEqual(Integer numNotEqual) {
        this.numNotEqual = numNotEqual;
    }

    public Long[] getDeviceSysIds() {
        return deviceSysIds;
    }

    public void setDeviceSysIds(Long[] deviceSysIds) {
        this.deviceSysIds = deviceSysIds;
    }
}
