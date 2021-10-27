package com.xjt.cloud.maintenance.core.entity.device;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;
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
    private String deviceSysCode;//'设备系统编码',
    private Long deviceTypeId;//'设备类型id',
    private String deviceTypeName;//'设备类型名称',
    private String q;//'设备类型名称',

    /**
     * 设备编码
     */
    private String deviceCode;

    // 设备数量
    private Integer deviceNum;
    private String deviceTypeCode;//'设备类型编码',
    private Long deviceCheckItemId;//'设备检测项id',
    private String deviceCheckItemName;//'检测项名称',
    private String checkName;//'检测项名称',
    private String deviceCheckItemCode;//'检测项编码',
    private String grade;//检测项等级
    private String checkSpecification;//'检测标准',
    private String memo;//'备注',
    private Integer checkFailNum;//'检测不合格数量',
    private Integer checkNum;//'检测数',
    private Integer checkResult;//'检测结果1合格 2不合格 3无',

    private Integer checkOkNum;//'合格数',
    private String checkOkNumStr;

    private Long buildingId;//'建筑物id',
    private Long buildingFloorId;//楼层id
    // 建筑物名称
    private String buildingName;
    // 楼层名称
    private String floorName;
    private String address;//'具体地址',
    private List<CheckRecord> recordList;
    private String code;//项目编号
    private Long checkPointId;//检测点id
    private Long[] checkPointIds;//检测点id
    private String checkUserName;//检测人姓名

    private String imgUrl;//检测图片
    private String imgUrl2;//检测图片

    private Integer checkType;//巡检类型 1维保 2巡检 3测试 4保养
    private Integer maintenanceOrCheck;//1维保 2（2巡检 3测试 4保养）
    private String checkImgUrls;//巡检图片 以；分隔
    private String[] checkImgUrlArr;
    private String causeFailure;//故障原因及处理

    /**
     * 处理措施
     * 1现场 解决  2故障报修
     */
    private Integer treatmentMeasures;

    private Long[] deviceSysIds;//设备系统ids
    private Integer deviceType;//巡检类型 1巡检 2维保巡检 3维保测试 4维保保养
    private Long planId;//计划id
    /**
     * 测试情况
     */
    private String testSituation;
    private Date checkDate;//巡检时间
    private Long[] ids;

    /**
     * 生产厂家
     */
    private String manufacturer;

    /**
     * 产品型号
     */
    private String productModel;
    /**
     * 相关附件 url
     */
    private String relevantFilePath;

    private Date startTime;
    private Date endTime;
    /**
     * 开始日期
     */
    private Date startDate;
    /**
     * 结束日期
     */
    private Date endDate;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getTreatmentMeasures() {
        return treatmentMeasures;
    }

    public void setTreatmentMeasures(Integer treatmentMeasures) {
        this.treatmentMeasures = treatmentMeasures;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public String getCheckDateDesc() {
        if (checkDate != null){
            return DateUtils.dateToY_M_DString(checkDate);
        }
        return "";
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Long getPlanId() {
        return planId;
    }

    public Integer getMaintenanceOrCheck() {
        return maintenanceOrCheck;
    }

    public void setMaintenanceOrCheck(Integer maintenanceOrCheck) {
        this.maintenanceOrCheck = maintenanceOrCheck;
    }

    public String getCheckProjectName() {
        return checkProjectName;
    }

    public void setCheckProjectName(String checkProjectName) {
        this.checkProjectName = checkProjectName;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
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

    public String getCauseFailure() {
        if (StringUtils.isEmpty(causeFailure)) {
            return "";
        }
        return causeFailure;
    }

    public void setCauseFailure(String causeFailure) {
        this.causeFailure = causeFailure;
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

    public String getDeviceSysNameDesc() {
        return CacheUtils.getCacheValueByTypeAndId(Constants.DEVICE_SYS_CACHE_KEY, getDeviceSysId(), "deviceSysName");
    }

    public String getDeviceTypeNameDesc() {
        return CacheUtils.getCacheValueByTypeAndId(Constants.DEVICE_SYS_CACHE_KEY, getDeviceTypeId(), "deviceSysName");
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
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

    public String getDeviceSysCode() {
        if (StringUtils.isEmpty(deviceSysCode)) {
            return "";
        }
        return deviceSysCode;
    }

    public void setDeviceSysCode(String deviceSysCode) {
        if (deviceSysCode == null) {
            deviceSysCode = "";
        }
        this.deviceSysCode = deviceSysCode;
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

    public String getMemo() {
        if (StringUtils.isEmpty(memo)) {
            return "";
        }
        return memo;
    }

    public void setMemo(String memo) {
        if (memo == null) {
            memo = "";
        }
        this.memo = memo;
    }

    public Integer getCheckFailNum() {
        if (null == checkFailNum) {
            return 0;
        }
        return checkFailNum;
    }

    public String getCheckFailNumDesc() {
        if (null == checkFailNum) {
            return "";
        }
        return checkFailNum + "";
    }

    public String getCheckFailNumD() {
        if (StringUtils.isEmpty(checkOkNumStr)) {
            return "×";
        }
        return "";
    }

    public void setCheckFailNum(Integer checkFailNum) {
        this.checkFailNum = checkFailNum;
    }

    public Integer getCheckOkNum() {
        return checkOkNum;
    }

    public void setCheckOkNum(Integer checkOkNum) {
        if (checkOkNum == null) {
            checkOkNum = 0;
        }
        this.checkOkNum = checkOkNum;
    }

    public String getCheckOkNumStr() {
        if (StringUtils.isEmpty(checkOkNumStr)) {
            return "";
        }
        return checkOkNumStr;
    }

    public void setCheckOkNumStr(String checkOkNumStr) {
        this.checkOkNumStr = checkOkNumStr;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getBuildingFloorId() {
        return buildingFloorId;
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }

    public String getAddress() {
        if (StringUtils.isEmpty(address)) {
            return "";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public void setCheckSpecification(String checkSpecification) {
        if (checkSpecification == null) {
            checkSpecification = "";
        }
        this.checkSpecification = checkSpecification;
    }

    public String getDeviceCheckItemCode() {
        /*if (StringUtils.isEmpty(deviceCheckItemCode)){
            return "";
        }*/
        return deviceTypeCode + getId();
    }

    public void setDeviceCheckItemCode(String deviceCheckItemCode) {
        if (deviceCheckItemCode == null) {
            deviceCheckItemCode = "";
        }
        this.deviceCheckItemCode = deviceCheckItemCode;
    }

    public String getGrade() {
        if (StringUtils.isEmpty(grade)) {
            return "";
        }
        return grade;
    }

    public void setGrade(String grade) {
        if (grade == null) {
            grade = "";
        }
        this.grade = grade;
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

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public Long[] getCheckPointIds() {
        return checkPointIds;
    }

    public void setCheckPointIds(Long[] checkPointIds) {
        this.checkPointIds = checkPointIds;
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

    public String getImgUrl() {
        if (StringUtils.isEmpty(imgUrl)) {
            return "";
        }
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl2() {
        if (StringUtils.isEmpty(imgUrl2)) {
            return "";
        }
        return imgUrl2;
    }

    public void setImgUrl2(String imgUrl2) {
        this.imgUrl2 = imgUrl2;
    }

    /**
     * 功能描述:得到建筑物名称
     *
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/10/30 10:12
     */
    public String getBuildingName() {
        return CacheUtils.getCacheValueByTypeAndId("maintenance_" + Constants.BUILDING_CACHE_KEY, getBuildingId(), "buildingName");
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getFloorName() {
        return CacheUtils.getCacheValueByTypeAndId("maintenance_" + Constants.BUILDING_FLOOR_CACHE_KEY, getBuildingFloorId(), "floorName");
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    /**
     * 功能描述:得到建筑物楼层名称
     *
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/10/30 10:12
     */
    /*public String getBuildingFloorName() {
        return CacheUtils.getCacheValueByTypeAndId("maintenance_" + Constants.BUILDING_FLOOR_CACHE_KEY, getBuildingFloorId(), "floorName");
    }*/

    public String getTestSituation() {
        if (StringUtils.isEmpty(testSituation)) {
            return "";
        }
        return testSituation;
    }

    public void setTestSituation(String testSituation) {
        this.testSituation = testSituation;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getRelevantFilePath() {
        return relevantFilePath;
    }

    public void setRelevantFilePath(String relevantFilePath) {
        this.relevantFilePath = relevantFilePath;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @JsonIgnore
    public Date getEndTimeDesc() {
        if (null == endTime) {
            return null;
        }
        return DateUtils.add24Hours(endTime);
    }
}
