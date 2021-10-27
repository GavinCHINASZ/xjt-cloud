package com.xjt.cloud.admin.manage.entity.inventory;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName OutStorageProduct
 * @Description
 * @Author wangzhiwen
 * @Date 2020/8/26 15:20
 **/
public class OutStorageProduct extends BaseEntity {
    private Long rowNum;//
    private Long outStorageId;//出库单编号
    private Long putStorageId;//入库单id',
    private String orderNum;//出库单号
    private Long producerId;//厂商id',
    private String producerName;//厂商名称
    private String projectName1;//
    private Long productId;//产品id',
    private String productName;//产品名称
    private Long putStorageProductId;//库存产品id'
    private Integer productStatus;//物联卡状态1：待激活 2：已激活 4：停机 6：可测试 7：库存 8：预销户
    private Integer storageStatus;//库存状态 1库存 2已出库 3退回，4报废
    private Integer totalFlow;//总流量byte 1M = 1024K = 1024 * 1024 byte
    private Integer usedFlow;//已用流量byte
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date activeDate;//激活日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date openDate;//开卡日期
    private Long buildingId;//建筑物id
    private Long buildingFloorId;//楼层id
    private Long checkPointId;//巡检点id
    private String pointName;//巡检点名称
    private String qrNo;//巡检点二难码
    private Long deviceId;//设备id
    private String deviceQrNo;//设备二维码
    private String sensorNo;//物联设备传感器id
    private Integer deviceType;//默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾 11视频监控 12防火门监控 13水厢以前单位为Ｍ 14两端压力设备 15多端设备 16吸气式烟雾探测器 17微型消防站  19烟感',
    private Long deviceTypeId;
    private Long iotId;//物联设备id
    private String memo;//备注
    private String property1;//物联网卡为iccid
    private String iccid;//物联网卡为iccid
    private String property2;//物联网卡为msisdn
    private String property3;//物联网卡为imsi
    private String property4;//
    private String property5;//
    private String property6;//
    private Boolean relationProduct;//是否查询已关联产品，true查询已关联 false查询未关联
    private String deviceIdAndDeviceType;//设备类型与设备id以_分隔
    private Long outStorageProductId;

    public String getProjectName1() {
        return projectName1;
    }

    public void setProjectName1(String projectName1) {
        this.projectName1 = projectName1;
    }

    public Long getRowNum() {
        return rowNum;
    }

    public void setRowNum(Long rowNum) {
        this.rowNum = rowNum;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Long getOutStorageProductId() {
        return outStorageProductId;
    }

    public void setOutStorageProductId(Long outStorageProductId) {
        this.outStorageProductId = outStorageProductId;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getDeviceIdAndDeviceType() {
        return deviceIdAndDeviceType;
    }

    public void setDeviceIdAndDeviceType(String deviceIdAndDeviceType) {
        this.deviceIdAndDeviceType = deviceIdAndDeviceType;
    }

    public String getBuildingName(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, buildingId, "buildingName");
    }
    public String getBuildingFloorName(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, buildingFloorId, "floorName");
    }
    public String getProjectName(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, getProjectId(), "projectName");
    }

    public Boolean getRelationProduct() {
        return relationProduct;
    }

    public void setRelationProduct(Boolean relationProduct) {
        this.relationProduct = relationProduct;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }

    public String getDeviceQrNo() {
        return deviceQrNo;
    }

    public void setDeviceQrNo(String deviceQrNo) {
        this.deviceQrNo = deviceQrNo;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    public String getProperty3() {
        return property3;
    }

    public void setProperty3(String property3) {
        this.property3 = property3;
    }

    public String getProperty4() {
        return property4;
    }

    public void setProperty4(String property4) {
        this.property4 = property4;
    }

    public String getProperty5() {
        return property5;
    }

    public void setProperty5(String property5) {
        this.property5 = property5;
    }

    public String getProperty6() {
        return property6;
    }

    public void setProperty6(String property6) {
        this.property6 = property6;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public Integer getStorageStatus() {
        return storageStatus;
    }

    public void setStorageStatus(Integer storageStatus) {
        this.storageStatus = storageStatus;
    }

    public Integer getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(Integer totalFlow) {
        this.totalFlow = totalFlow;
    }

    public Integer getUsedFlow() {
        return usedFlow;
    }

    public void setUsedFlow(Integer usedFlow) {
        this.usedFlow = usedFlow;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getOutStorageId() {
        return outStorageId;
    }

    public void setOutStorageId(Long outStorageId) {
        this.outStorageId = outStorageId;
    }

    public Long getPutStorageId() {
        return putStorageId;
    }

    public void setPutStorageId(Long putStorageId) {
        this.putStorageId = putStorageId;
    }

    public Long getProducerId() {
        return producerId;
    }

    public void setProducerId(Long producerId) {
        this.producerId = producerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getPutStorageProductId() {
        return putStorageProductId;
    }

    public void setPutStorageProductId(Long putStorageProductId) {
        this.putStorageProductId = putStorageProductId;
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

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Long getIotId() {
        return iotId;
    }

    public void setIotId(Long iotId) {
        this.iotId = iotId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
