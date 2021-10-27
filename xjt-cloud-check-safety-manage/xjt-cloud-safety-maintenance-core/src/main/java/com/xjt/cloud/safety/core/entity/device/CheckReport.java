package com.xjt.cloud.safety.core.entity.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/11 17:30
 * @Description:设备系统检测报告
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckReport {
    private Long deviceSysId;//设备系统id
    private String deviceSysName;//设备系统名称
    private String deviceSysTypeName;//设备系统类型名称
    private Integer deviceTypeNum;//设备类型总数
    private Long deviceTypeId;//设备类型id
    private String deviceTypeName;//设备类型名称
    private Long deviceCheckItemId;//设备检测项id
    private String checkName;//检测项名称
    private String checkSpecification;//检测标准
    private List<String> checkSpecificationList;//检测标准
    private String code;//项目编号
    private Integer weight;//权重
    private String weightDesc;//权重
    private Integer typeWeight;//权重
    private String typeWeightDesc;//权重
    private Integer checkNum;//测试总数
    private Integer totalNum;//总数
    private Integer score;//评分
    private String scoreDesc;//评分
    private String itemScore;//分项评分
    private String totalScore;//总评分
    private Integer checkResult;//'评定结果1A 2B 3C 4D 5无',
    private String checkResultDesc;//'评定结果1A 2B 3C 4D 5无',
    private Integer deviceType;//默认 1建筑防火  2消防设施及器材  3消防安全管理
    private String inspection;//检查情况
    private String checkResultA;//'评定结果1A 2B 3C 4D 5无',
    private String checkResultB;//'评定结果1A 2B 3C 4D 5无',
    private String checkResultC;//'评定结果1A 2B 3C 4D 5无',
    private String checkResultD;//'评定结果1A 2B 3C 4D 5无',

    public String getWeightDesc() {
        if (weight == null){
            return "--";
        }
        if (weight != null){
            double s = (double)weight / 100.00D;
            return String.format("%.2f", s);
        }
        return "";
    }
    public String getTotalScoreDesc() {
        if (score != null){
            double s = (double)score / 10000.00D;
            return String.format("%.2f", s);
        }
        return totalScore;
    }

    public void setWeightDesc(String weightDesc) {
        this.weightDesc = weightDesc;
    }

    public String getTypeWeightDesc() {
        if (typeWeight != null){
            double s = (double)typeWeight / 100.00D;
            return String.format("%.2f", s);
        }
        return "";
    }

    public void setTypeWeightDesc(String typeWeightDesc) {
        this.typeWeightDesc = typeWeightDesc;
    }

    public void setCheckSpecificationList(List<String> checkSpecificationList) {
        this.checkSpecificationList = checkSpecificationList;
    }

    public String getScoreDesc() {
        if (weight == null){
            return "--";
        }
        if (checkNum == 0){
            return "/";
        }
        if (score != null){
            double s = (double)score / 100.00D;
            return String.format("%.2f", s);
        }
        return "";
    }

    public void setScoreDesc(String scoreDesc) {
        this.scoreDesc = scoreDesc;
    }

    public String getItemScore() {
        return itemScore;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setItemScore(String itemScore) {
        this.itemScore = itemScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public List<String> getCheckSpecificationList() {
        if (StringUtils.isNotEmpty(checkSpecification)){
            return Arrays.asList(checkSpecification.split("\\n"));
        }
        return checkSpecificationList;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getCheckResultDesc() {
        return checkResult == null || checkResult == 5 ? "" : checkResult + "";
    }

    public void setCheckResultDesc(String checkResultDesc) {
        this.checkResultDesc = checkResultDesc;
    }

    public String getCheckResultA() {
        return checkResult != null && checkResult == 1 ? "√" :"";
    }

    public void setCheckResultA(String checkResultA) {
        this.checkResultA = checkResultA;
    }

    public String getCheckResultB() {
        return checkResult != null && checkResult == 2 ? "√" :"";
    }

    public void setCheckResultB(String checkResultB) {
        this.checkResultB = checkResultB;
    }

    public String getCheckResultC() {
        return checkResult != null && checkResult == 3 ? "√" :"";
    }

    public void setCheckResultC(String checkResultC) {
        this.checkResultC = checkResultC;
    }

    public String getCheckResultD() {
        return checkResult != null && checkResult == 4 ? "√" :"";
    }

    public void setCheckResultD(String checkResultD) {
        this.checkResultD = checkResultD;
    }

    public String getInspection() {
        return StringUtils.isEmpty(inspection) ? "" : inspection;
    }

    public void setInspection(String inspection) {
        this.inspection = inspection;
    }

    public String getDeviceSysTypeName() {
        return deviceSysTypeName;
    }

    public void setDeviceSysTypeName(String deviceSysTypeName) {
        this.deviceSysTypeName = deviceSysTypeName;
    }

    public Integer getTypeWeight() {
        return typeWeight;
    }

    public void setTypeWeight(Integer typeWeight) {
        this.typeWeight = typeWeight;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
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

    public Integer getCheckResult() {
        return checkResult == null ? 0 : checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public Integer getDeviceTypeNum() {
        return deviceTypeNum;
    }

    public void setDeviceTypeNum(Integer deviceTypeNum) {
        this.deviceTypeNum = deviceTypeNum;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Integer checkNum) {
        this.checkNum = checkNum;
    }

    public Integer getScore() {
        return score == null ? 0 : score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getDeviceSysId() {
        return deviceSysId;
    }

    public void setDeviceSysId(Long deviceSysId) {
        this.deviceSysId = deviceSysId;
    }

    public String getDeviceSysName() {
        if (StringUtils.isEmpty(deviceSysName)){
            return "";
        }
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }


}
