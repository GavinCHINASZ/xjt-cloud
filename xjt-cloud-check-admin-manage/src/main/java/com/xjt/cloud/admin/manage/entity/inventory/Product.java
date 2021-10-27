package com.xjt.cloud.admin.manage.entity.inventory;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.dict.DictUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName Product
 * @Description  仓库产品管理
 * @Author wangzhiwen
 * @Date 2020/8/17 14:10
 **/
public class Product  extends BaseEntity {
    private String productName;//产品名称
    private Long producerId;//厂商id
    private String producerName;//厂商
    private String brand;//品牌
    private String model;//产品型号
    private String spec;//规格参数
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date productionDate;//生产日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;//有效期
    private Integer productType;//产品类型见数据词典
    private String apiInfo;//接口信息
    private String memo;//备注

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

    public Long getProducerId() {
        return producerId;
    }

    public void setProducerId(Long producerId) {
        this.producerId = producerId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(String apiInfo) {
        this.apiInfo = apiInfo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getProductTypeDesc() {
        if (productType == null ){
            return "";
        }
        return DictUtils.getItemNameByDictAndItemValue("INVENTORY_PRODUCT_TYPE",productType + "");
    }
}
