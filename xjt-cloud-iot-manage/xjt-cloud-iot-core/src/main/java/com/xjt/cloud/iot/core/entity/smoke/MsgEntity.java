package com.xjt.cloud.iot.core.entity.smoke;

/**
 *
 * @author huanggc
 * @date 2020/07/21
 */
public class MsgEntity {
    private Long at;
    private String imei;
    private Integer type;
    private String ds_id;
    private String value;
    private Integer dev_id;

    private Integer login_type;
    private Integer status;

    public Long getAt() {
        return at;
    }

    public void setAt(Long at) {
        this.at = at;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDs_id() {
        return ds_id;
    }

    public void setDs_id(String ds_id) {
        this.ds_id = ds_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getDev_id() {
        return dev_id;
    }

    public void setDev_id(Integer dev_id) {
        this.dev_id = dev_id;
    }

    public Integer getLogin_type() {
        return login_type;
    }

    public void setLogin_type(Integer login_type) {
        this.login_type = login_type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
