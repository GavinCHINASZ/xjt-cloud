package com.xjt.cloud.admin.manage.entity.inventory;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/7/29 16:03
 * @Description: 物联卡厂家实体类
 */
public class Producer extends BaseEntity {
    private String producerName; //厂商名称
    private String contacts;//联系人
    private String phone;//联系电话
    private String memo;//备注
    private String codeKey;//厂商标识,用于标识平台接口调用

    public String getCodeKey() {
        return codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
