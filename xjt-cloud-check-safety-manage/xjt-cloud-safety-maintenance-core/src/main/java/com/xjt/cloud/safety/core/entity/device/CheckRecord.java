package com.xjt.cloud.safety.core.entity.device;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.List;

/**
 * app检测
 *
 * @author wangzhiwen
 * @date 2020/4/11 09:32
 */
public class CheckRecord extends BaseEntity {
    private Long checkProjectId;//'检测项目id',
    private String checkProjectName;//客户名称
    private Long deviceSysId;//'设备系统id',
    private String deviceSysName;//'设备系统名称',
    private Long deviceTypeId;//'设备类型id',
    private String deviceTypeName;//'设备类型名称',
    /**
     * 设备编码
     */
    private String deviceCode;
    private String deviceTypeCode;//'设备类型编码',
    private Long deviceCheckItemId;//'设备检测项id',
    private String deviceCheckItemName;//'检测项名称',
    private String checkName;//'检测项名称',
    private String checkSpecification;//'检测标准',
    private Integer checkNum;//'检测数',
    private Integer checkResult;//'评定结果1A 2B 3C 4D 5无',

    private List<CheckRecord> recordList;
    private String code;//项目编号
    private String checkUserName;//检测人姓名
    private Integer checkType;//巡检类型 1维保 2巡检 3测试 4保养
    private String checkImgUrls;//巡检图片 以；分隔
    private String[] checkImgUrlArr;

    private Long[] deviceSysIds;//设备系统ids
    private Integer deviceType;//默认 1建筑防火  2消防设施及器材  3消防安全管理
    private Integer[] deviceTypes;//默认 1建筑防火  2消防设施及器材  3消防安全管理
    private Long[] ids;
    private Integer score;//评分
    private String totalScore;//评分
    private String inspection;//检查情况
    private Integer buildingType;//1厂房（甲、乙类） 2其它厂房 3仓库 4民用建筑
    private Integer assessItemA;//评分项A 默认1有 2没有
    private Integer assessItemB;//评分项A 默认1有 2没有
    private Integer assessItemC;//评分项A 默认1有 2没有
    private Integer assessItemD;//评分项A 默认1有 2没有
    private Integer complete;   //是否完成评估 1.未评估完成 2.已评估完成

    public Integer getAssessItemA() {
        return assessItemA;
    }

    public void setAssessItemA(Integer assessItemA) {
        this.assessItemA = assessItemA;
    }

    public Integer getAssessItemB() {
        return assessItemB;
    }

    public void setAssessItemB(Integer assessItemB) {
        this.assessItemB = assessItemB;
    }

    public Integer getAssessItemC() {
        return assessItemC;
    }

    public void setAssessItemC(Integer assessItemC) {
        this.assessItemC = assessItemC;
    }

    public Integer getAssessItemD() {
        return assessItemD;
    }

    public void setAssessItemD(Integer assessItemD) {
        this.assessItemD = assessItemD;
    }

    public String getTotalScore() {
        if (score != null){
            double s = (double)score / 10000.00D;
            return String.format("%.2f", s);
        }
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getComplete() {
        return complete;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }

    public Integer[] getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(Integer[] deviceTypes) {
        this.deviceTypes = deviceTypes;
    }

    public Integer getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(Integer buildingType) {
        this.buildingType = buildingType;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getInspection() {
        return inspection;
    }

    public void setInspection(String inspection) {
        this.inspection = inspection;
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

    public Long[] getDeviceSysIds() {
        return deviceSysIds;
    }

    public void setDeviceSysIds(Long[] deviceSysIds) {
        this.deviceSysIds = deviceSysIds;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getCheckImgUrls() {
        return checkImgUrls;
    }

    public void setCheckImgUrls(String checkImgUrls) {
        this.checkImgUrls = checkImgUrls;
    }

    public String[] getCheckImgUrlArr() {
        return checkImgUrlArr;
    }

    public void setCheckImgUrlArr(String[] checkImgUrlArr) {
        this.checkImgUrlArr = checkImgUrlArr;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

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

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceSysName() {
        if (StringUtils.isEmpty(deviceSysName)) {
            return "";
        }
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
        if (StringUtils.isEmpty(deviceTypeName)) {
            return "";
        }
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public String getDeviceTypeCode() {
        return deviceTypeCode;
    }

    public void setDeviceTypeCode(String deviceTypeCode) {
        if (deviceTypeCode == null) {
            deviceTypeCode = "";
        }
        this.deviceTypeCode = deviceTypeCode;
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
        if (deviceCheckItemName == null) {
            deviceCheckItemName = "";
        }
        this.deviceCheckItemName = deviceCheckItemName;
    }

    public String getCheckSpecification() {
        return checkSpecification;
    }

    public String[] getCheckSpecificationList() {
        if (StringUtils.isNotEmpty(checkSpecification)){
            return checkSpecification.split("\\r\\n");
        }
        return null;
    }

    public void setCheckSpecification(String checkSpecification) {
        if (checkSpecification == null) {
            checkSpecification = "";
        }
        this.checkSpecification = checkSpecification;
    }

    public List<CheckRecord> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<CheckRecord> recordList) {
        this.recordList = recordList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code == null) {
            code = "";
        }
        this.code = code;
    }

    public Integer getCheckNum() {
        if (checkNum == null) {
            return 0;
        }
        return checkNum;
    }

    public void setCheckNum(Integer checkNum) {
        this.checkNum = checkNum;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public String getCheckResultDesc() {
        // 检测结果1合格 2不合格 3无',
        if (checkResult != null) {
            if (checkResult == 1) {
                return "正常";
            } else if (checkResult == 2) {
                return "异常";
            }
        }
        return "";
    }

    public String getCreateTimeDesc() {
        if (getCreateTime() != null) {
            DateUtils.getDateYearMonthDays(getCreateTime());
        }
        return "";
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }


    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getCheckName() {
        if (StringUtils.isEmpty(checkName)) {
            return "";
        }
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

}
