package com.xjt.cloud.other.core.entity;


/**
 * @ClassName CCCF
 * @Author zhangZaiFa
 * @Date 2019-11-14 10:10
 * @Description
 */
public class Cccf {
    private String id;
    private String code;
    //标志明码
    private String labelId;
    //部门id
    private String deptId;
    //产品名称
    private String productName;
    //产品批号
    private String batchNumber;
    //交易单号
    private String bargainNumber;
    //生产厂家
    private String deptName;
    //产品生产时间
    private String productTime;
    //产品系列
    private String productSerial;
    //产品code
    private String productCode;
    //表单类型
    private String fTableType;
    //质检员
    private String checker;
    //用户单位
    private String moveUnit;
    //到达日期
    private String goodsReachDate;
    //经销商信息
    private String businessmanInfo;
    //等于0 等于2代表退货
    private String status;
    //流向地区
    private String flowArea;
    //销售类别  1流向上报,其他属于经销商上报
    private String saleSort;
    //销售地区
    private String sellArea;
    private String moduleNum;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getDeptId() {
        return deptId;
    }

    public String getSellArea() {
        return sellArea;
    }

    public void setSellArea(String sellArea) {
        this.sellArea = sellArea;
    }

    public String getModuleNum() {
        return moduleNum;
    }

    public void setModuleNum(String moduleNum) {
        this.moduleNum = moduleNum;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getBargainNumber() {
        return bargainNumber;
    }

    public void setBargainNumber(String bargainNumber) {
        this.bargainNumber = bargainNumber;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getProductTime() {
        return productTime;
    }

    public void setProductTime(String productTime) {
        this.productTime = productTime;
    }

    public String getProductSerial() {
        return productSerial;
    }

    public void setProductSerial(String productSerial) {
        this.productSerial = productSerial;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getfTableType() {
        return fTableType;
    }

    public void setfTableType(String fTableType) {
        this.fTableType = fTableType;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getMoveUnit() {
        return moveUnit;
    }

    public void setMoveUnit(String moveUnit) {
        this.moveUnit = moveUnit;
    }

    public String getGoodsReachDate() {
        return goodsReachDate;
    }

    public void setGoodsReachDate(String goodsReachDate) {
        this.goodsReachDate = goodsReachDate;
    }

    public String getBusinessmanInfo() {
        return businessmanInfo;
    }

    public void setBusinessmanInfo(String businessmanInfo) {
        this.businessmanInfo = businessmanInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlowArea() {
        return flowArea;
    }

    public void setFlowArea(String flowArea) {
        this.flowArea = flowArea;
    }

    public String getSaleSort() {
        return saleSort;
    }

    public void setSaleSort(String saleSort) {
        this.saleSort = saleSort;
    }
}
