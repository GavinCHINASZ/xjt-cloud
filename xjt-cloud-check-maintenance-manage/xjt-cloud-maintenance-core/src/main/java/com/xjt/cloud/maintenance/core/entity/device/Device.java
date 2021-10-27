package com.xjt.cloud.maintenance.core.entity.device;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;

/**
 * 设备登记信息实体
 *
 * @author dwt
 * @date 2020-04-10 15:38
 */
public class Device extends BaseEntity {
    //项目明细id
    private Long projectInfoId;
    //设备系统id
    private Long deviceTypeId;
    //系统设备id
    private Long deviceId;
    //系统名称
    private String deviceSysName;
    //设备名称
    private String deviceName;
    //数量
    private Integer num;
    //产品型号/生产厂家
    private String productModel;
    //符合法定市场准入规则的证明文件
    private String document;
    //合格证 0：没有，1：有
    private Integer certificate;
    //出厂日期
    private Date factoryDate;
    //备注
    private String remark;

    private String certificateStr;
    private Long[] ids;
    private Integer rowNum;

    //建筑物id
    private Long buildingId;
    //楼层id
    private Long floorId;
    // 建筑物名称
    private String buildingName;
    // 楼层名称
    private String floorName;
    private String manufacturer;//生产厂家
    private String address;//具体位置
    private String relevantFilePath;//相关附件

    /**
     * 客户名称
     */
    private String checkProjectName;

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRelevantFilePath() {
        return relevantFilePath;
    }

    public void setRelevantFilePath(String relevantFilePath) {
        this.relevantFilePath = relevantFilePath;
    }

    public Long getProjectInfoId() {
        return projectInfoId;
    }

    public void setProjectInfoId(Long projectInfoId) {
        this.projectInfoId = projectInfoId;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        if (deviceName == null) {
            deviceName = "";
        }
        this.deviceName = deviceName;
    }

    public String getProductModel() {
        if (StringUtils.isEmpty(productModel)) {
            productModel = "";
        }
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getDocument() {
        if (StringUtils.isEmpty(document)) {
            document = "";
        }
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Integer getCertificate() {
        return certificate;
    }

    public void setCertificate(Integer certificate) {
        this.certificate = certificate;
    }

    public Date getFactoryDate() {
        return factoryDate;
    }

    public void setFactoryDate(Date factoryDate) {
        this.factoryDate = factoryDate;
    }

    public String getRemark() {
        if (StringUtils.isEmpty(remark)) {
            remark = "";
        }
        return remark;
    }

    public void setRemark(String remark) {

        this.remark = remark;
    }

    public String getCertificateDesc() {
        String certificateDesc = "无";
        if (certificate != null && certificate == 1) {
            certificateDesc = "有";
        }
        return certificateDesc;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getNum() {
        if (num == null) {
            num = 0;
        }
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDeviceSysName() {
        if (StringUtils.isEmpty(deviceSysName)) {
            deviceSysName = "";
        }
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }

    public String getCertificateStr() {
        return certificateStr;
    }

    public void setCertificateStr(String certificateStr) {
        this.certificateStr = certificateStr;
    }

    public String getFactoryDateDesc() {
        String factoryDateDesc = "";
        if (factoryDate != null) {
            factoryDateDesc = DateUtils.formatyyyyMMddDate(factoryDate);
        }
        return factoryDateDesc;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getCheckProjectName() {
        return checkProjectName;
    }

    public void setCheckProjectName(String checkProjectName) {
        this.checkProjectName = checkProjectName;
    }

    /**
     * 功能描述:得到建筑物名称
     *
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/10/30 10:12
     */
    public String getBuildingName() {
        if (getBuildingId() != null && getBuildingId() != 0) {
            this.buildingName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, getBuildingId(), "buildingName");
        }
        if (StringUtils.isEmpty(buildingName)) {
            return "";
        }
        return buildingName;
    }

    /**
     * 功能描述:得到建筑物楼层名称
     *
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/10/30 10:12
     */
    public String getFloorName() {
        if (getFloorId() != null && getFloorId() != 0) {
            this.floorName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, getFloorId(), "floorName");
        }
        if (StringUtils.isEmpty(floorName)) {
            return "";
        }
        return floorName;
    }
}
