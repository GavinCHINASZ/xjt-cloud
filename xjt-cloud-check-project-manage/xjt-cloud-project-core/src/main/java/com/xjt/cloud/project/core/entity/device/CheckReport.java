package com.xjt.cloud.project.core.entity.device;

import com.xjt.cloud.commons.utils.StringUtils;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/11 17:30
 * @Description:设备系统检测报告
 */
public class CheckReport {
    private Long deviceSysId;//设备系统id
    private String deviceSysName;//设备系统名称
    private String deviceSysCode;//设备系统编码
    private Integer gradeATotal;//a级总数
    private Integer gradeBTotal;//b级总数
    private Integer gradeCTotal;//c级总数
    private Integer gradeAFailResult;//a级不合格总数
    private Integer gradeBFailResult;//b级不合格总数
    private Integer gradeCFailResult;//c级不合格总数
    private Integer decisionResult;//判定结果,1合格, 2合格

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

    public String getDeviceSysCode() {
        if (StringUtils.isEmpty(deviceSysCode)){
            return "";
        }
        return deviceSysCode;
    }

    public void setDeviceSysCode(String deviceSysCode) {
        this.deviceSysCode = deviceSysCode;
    }

    public Integer getGradeATotal() {
        return gradeATotal;
    }

    public void setGradeATotal(Integer gradeATotal) {
        this.gradeATotal = gradeATotal;
    }

    public Integer getGradeBTotal() {
        return gradeBTotal;
    }

    public void setGradeBTotal(Integer gradeBTotal) {
        this.gradeBTotal = gradeBTotal;
    }

    public Integer getGradeCTotal() {
        return gradeCTotal;
    }

    public void setGradeCTotal(Integer gradeCTotal) {
        this.gradeCTotal = gradeCTotal;
    }

    public Integer getGradeAFailResult() {
        return gradeAFailResult;
    }

    public void setGradeAFailResult(Integer gradeAFailResult) {
        this.gradeAFailResult = gradeAFailResult;
    }

    public Integer getGradeBFailResult() {
        if (null == gradeBFailResult){
            return 0;
        }
        return gradeBFailResult;
    }

    public void setGradeBFailResult(Integer gradeBFailResult) {
        this.gradeBFailResult = gradeBFailResult;
    }

    public Integer getGradeCFailResult() {
        if (null == gradeCFailResult){
            return 0;
        }
        return gradeCFailResult;
    }

    public void setGradeCFailResult(Integer gradeCFailResult) {
        this.gradeCFailResult = gradeCFailResult;
    }

    public Integer getDecisionResult() {
        return decisionResult;
    }

    public String getDecisionResultDesc() {
        if (decisionResult != null && 1 == decisionResult){
            return "合格";
        }
        return "不合格";
    }

    public void setDecisionResult(Integer decisionResult) {
        this.decisionResult = decisionResult;
    }
}
