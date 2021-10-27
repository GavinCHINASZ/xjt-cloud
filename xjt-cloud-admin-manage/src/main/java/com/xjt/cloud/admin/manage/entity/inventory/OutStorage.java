package com.xjt.cloud.admin.manage.entity.inventory;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName OutStorage
 * @Description 出库单信息实体类
 * @Author wangzhiwen
 * @Date 2020/8/21 15:56
 **/
public class OutStorage extends BaseEntity {
    private String orderNum; //出库单号
    private String recipients;//领用人
    private String memo;//备注
    private Long producerId;//厂商
    private Long productId;//产品id
    private String projectName;//项目名称

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getProducerId() {
        return producerId;
    }

    public void setProducerId(Long producerId) {
        this.producerId = producerId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
