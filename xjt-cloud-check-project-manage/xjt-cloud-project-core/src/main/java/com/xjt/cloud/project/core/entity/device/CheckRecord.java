package com.xjt.cloud.project.core.entity.device;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.StringUtils;

import java.sql.Struct;
import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/11 09:32
 * @Description:app检测
 */
public class CheckRecord extends BaseEntity {
    private Long checkProjectId;//'检测项目id',
    private Long deviceSysId;//'设备系统id',
    private String deviceSysName;//'设备系统名称',
    private String deviceSysCode;//'设备系统编码',
    private Long deviceTypeId;//'设备类型id',
    private String deviceTypeName;//'设备类型名称',
    private String deviceTypeCode;//'设备类型编码',
    private Long deviceCheckItemId;//'设备检测项id',
    private String deviceCheckItemName;//'检测项名称',
    private String checkName;//'检测项名称',
    private String deviceCheckItemCode;//'检测项编码',
    private String grade;//检测项等级
    private String checkSpecification;//'检测标准',
    private String memo;//'备注',
    private Long checkFailNum;//'检测不合格数量',
    private Long checkNum;//'检测数',
    private Integer checkResult;//'检测结果1合格 2不合格 3无',
    private Integer checkOkNum;//'合格数',
    private Long buildingId;//'建筑物id',
    private Long buildingFloorId;//楼层id
    private String address;//'具体地址',
    private List<CheckRecord> recordList;
    private String code;//项目编号
    private Long checkPointId;//检测点id
    private String checkUserName;//检测人姓名
    private String imgUrl;//检测图片
    private Integer[] sysTypeIdList;//类型ID数组

    public Integer[] getSysTypeIdList() {
        return sysTypeIdList;
    }

    public void setSysTypeIdList(Integer[] sysTypeIdList) {
        this.sysTypeIdList = sysTypeIdList;
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

    public String getDeviceSysName() {
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        if(deviceSysName == null){
            deviceSysName = "";
        }
        this.deviceSysName = deviceSysName;
    }

    public String getDeviceSysCode() {
        if (StringUtils.isEmpty(deviceSysCode)){
            return "";
        }
        return deviceSysCode;
    }

    public void setDeviceSysCode(String deviceSysCode) {
        if(deviceSysCode == null){
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
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        if(deviceTypeName == null){
            deviceTypeName = "";
        }
        this.deviceTypeName = deviceTypeName;
    }

    public String getDeviceTypeCode() {
        return deviceTypeCode;
    }

    public void setDeviceTypeCode(String deviceTypeCode) {
        if(deviceTypeCode == null){
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
        if (StringUtils.isEmpty(memo)){
            return "";
        }
        return memo;
    }

    public void setMemo(String memo) {
        if(memo == null){
            memo = "";
        }
        this.memo = memo;
    }

    public Long getCheckFailNum() {
        if (null == checkFailNum){
            return 0l;
        }
        return checkFailNum;
    }

    public void setCheckFailNum(Long checkFailNum) {
        this.checkFailNum = checkFailNum;
    }

    public Integer getCheckOkNum() {
        return checkOkNum;
    }

    public void setCheckOkNum(Integer checkOkNum) {
        if(checkOkNum == null){
            checkOkNum = 0;
        }
        this.checkOkNum = checkOkNum;
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
        return address;
    }

    public void setAddress(String address) {
        if(address == null){
            address = "";
        }
        this.address = address;
    }

    public String getDeviceCheckItemName() {
        return deviceCheckItemName;
    }

    public void setDeviceCheckItemName(String deviceCheckItemName) {
        if(deviceCheckItemName == null){
            deviceCheckItemName = "";
        }
        this.deviceCheckItemName = deviceCheckItemName;
    }

    public String getCheckSpecification() {
        return checkSpecification;
    }

    public void setCheckSpecification(String checkSpecification) {
        if(checkSpecification == null){
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
        if(deviceCheckItemCode == null){
            deviceCheckItemCode = "";
        }
        this.deviceCheckItemCode = deviceCheckItemCode;
    }

    public String getGrade() {
        if (StringUtils.isEmpty(grade)){
            return "";
        }
        return grade;
    }

    public void setGrade(String grade) {
        if(grade == null){
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
        if(code == null){
            code = "";
        }
        this.code = code;
    }

    public Long getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Long checkNum) {
        this.checkNum = checkNum;
    }

    public Integer getCheckResult() {
        return checkResult;
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

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     *
     * 功能描述:得到建筑物名称
     *
     * @param
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/10/30 10:12
     */
    public String getBuildingName(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, getBuildingId(), "buildingName");
    }

    /**
     *
     * 功能描述:得到建筑物楼层名称
     *
     * @param
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/10/30 10:12
     */
    public String getBuildingFloorName(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, getBuildingFloorId(), "floorName");
    }
}
