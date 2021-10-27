package com.xjt.cloud.admin.manage.entity.inventory;

import com.xjt.cloud.commons.base.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 入库信息关联物产品实体类
 *
 * @author wangzhiwen
 * @date 2020/7/30 11:17
 */
public class PutStorageProduct extends BaseEntity {
    // 行号
    private Long rowNum;
    private Long putStorageId;//订单编号
    private Long producerId;//厂商id
    private String orderNum;//订单编号
    private String producerName; //厂商名称
    private String productNum;//产品编号
    private Long productId;//产品id
    private String productName; //产品名称
    private String property1;//物联网卡为iccid
    private String property2;//物联网卡为msisdn
    private String property3;//物联网卡为imsi
    private String property4;//
    private String property5;//
    private String property6;//
    private Integer productStatus;//物联卡状态1：待激活 2：已激活 4：停机 6：可测试 7：库存 8：预销户
    private Integer storageStatus;//库存状态 1库存 2已出库 3退回，4报废
    private Integer totalFlow;//总流量byte 1M = 1024K = 1024 * 1024 byte
    private Integer usedFlow;//已用流量byte
    private Integer remainFlow;//剩余流量
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date activeDate;//激活日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date openDate;//开卡日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderTime;//购买时间
    private String memo;//备注
    private Integer productType;//产品类型见数据词典
    private String[] productTypes;//产品类型见数据词典
    private String offeringId;//资费Id
    private String offeringName;//资费名称
    private String sql;
    private String ids;

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    //物联网卡流量处理时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date flowDandleDate;

    public Long getRowNum() {
        return rowNum;
    }

    public void setRowNum(Long rowNum) {
        this.rowNum = rowNum;
    }

    public Date getFlowDandleDate() {
        return flowDandleDate;
    }

    public void setFlowDandleDate(Date flowDandleDate) {
        this.flowDandleDate = flowDandleDate;
    }

    public Integer getRemainFlow() {
        return remainFlow;
    }

    public void setRemainFlow(Integer remainFlow) {
        this.remainFlow = remainFlow;
    }

    public String getOfferingId() {
        return offeringId;
    }

    public void setOfferingId(String offeringId) {
        this.offeringId = offeringId;
    }

    public String getOfferingName() {
        return offeringName;
    }

    public void setOfferingName(String offeringName) {
        this.offeringName = offeringName;
    }

    public Integer getStorageStatus() {
        return storageStatus;
    }

    public void setStorageStatus(Integer storageStatus) {
        this.storageStatus = storageStatus;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
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


    public String getStorageStatusDesc() {
        String s;
        if (storageStatus == 2){
            s = "已出库";
        } else if(storageStatus == 3){
            s = "退回";
        }else if(storageStatus == 4){
            s = "报废";
        }else{
            s = "库存";
        }
        return s;
    }

    public String getProductStatusDesc() {
        String s;
        if (productStatus == 1){
            s = "待激活";
        } else if(productStatus == 2){
            s = "已激活";
        }else if(productStatus == 4){
            s = "停机";
        }else if(productStatus == 6){
            s = "可测试";
        }else if(productStatus == 7){
            s = "库存";
        }else{
            s = "预销户";
        }
        return s;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String[] getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(String[] productTypes) {
        this.productTypes = productTypes;
    }
}
