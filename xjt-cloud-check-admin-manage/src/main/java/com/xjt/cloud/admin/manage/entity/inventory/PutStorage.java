package com.xjt.cloud.admin.manage.entity.inventory;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.dict.DictUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 入库管理实体类
 * 
 * @author wangzhiwen
 * @date 2020/7/30 11:02
 */
public class PutStorage extends BaseEntity {
    private String orderNum;//订单编号
    private Long producerId;//厂商id
    private String producerName; //厂商名称
    private Long productId;//产品id
    private String productName;//产品名称
    private Integer num;//数量
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderTime;//购买时间
    private String memo;//备注
    private Integer productType;//产品类型见数据词典

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
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

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Long getProducerId() {
        return producerId;
    }

    public void setProducerId(Long producerId) {
        this.producerId = producerId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getProductTypeDesc() {
        if (productType == null ){
            return "";
        }
        return DictUtils.getItemNameByDictAndItemValue("INVENTORY_PRODUCT_TYPE",productType + "");
    }
}
