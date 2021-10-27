package com.xjt.cloud.admin.manage.entity.inventory;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.dict.DictUtils;

/**
 * @ClassName Inventory
 * @Description 库存信息对象
 * @Author wangzhiwen
 * @Date 2020/9/7 15:17
 **/
public class Inventory extends BaseEntity {
    private Long producerId;//厂商id
    private String producerName;//厂商名称
    private Long productId;//产品id
    private String productName;//产品名称
    private Integer productType;//产品类型见数据词典
    private Integer totalNum;//总库存数
    private Integer outNum;//出库数

    public Long getProducerId() {
        return producerId;
    }

    public void setProducerId(Long producerId) {
        this.producerId = producerId;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
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

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getOutNum() {
        return outNum;
    }

    public void setOutNum(Integer outNum) {
        this.outNum = outNum;
    }

    public String getProductTypeDesc() {
        if (productType == null ){
            return "";
        }
        return DictUtils.getItemNameByDictAndItemValue("INVENTORY_PRODUCT_TYPE",productType + "");
    }
}
