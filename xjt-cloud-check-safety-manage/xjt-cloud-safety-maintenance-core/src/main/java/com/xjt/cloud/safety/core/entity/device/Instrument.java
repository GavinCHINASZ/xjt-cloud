package com.xjt.cloud.safety.core.entity.device;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;

/**
 * @ClassName Instrument
 * @Description 仪器管理
 * @Author wangzhiwen
 * @Date 2021/5/7 16:14
 **/
public class Instrument extends BaseEntity {
    private Long checkProjectId;//评估项目id
    private Long[] ids;
    private Long projectInstrumentId;//评估项目关联仪器信息表id
    private String deviceName;// 设备名称',
    private String deviceCode;// 设备编码',
    private String calibrationCode;// 校准证书编码',
    private Date termValidity;// 有效期',
    private String termValidityDesc;// 有效期',

    public String getTermValidityDesc() {
        return termValidity == null ? "" : DateUtils.dateToY_M_DString(termValidity);
    }

    public void setTermValidityDesc(String termValidityDesc) {
        this.termValidityDesc = termValidityDesc;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Long getCheckProjectId() {
        return checkProjectId;
    }

    public void setCheckProjectId(Long checkProjectId) {
        this.checkProjectId = checkProjectId;
    }

    public Long getProjectInstrumentId() {
        return projectInstrumentId;
    }

    public void setProjectInstrumentId(Long projectInstrumentId) {
        this.projectInstrumentId = projectInstrumentId;
    }

    public String getDeviceName() {
        return StringUtils.isEmpty(deviceName) ? "" : deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceCode() {
        return StringUtils.isEmpty(deviceCode) ? "" : deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getCalibrationCode() {
        return StringUtils.isEmpty(calibrationCode) ? "" : calibrationCode;
    }

    public void setCalibrationCode(String calibrationCode) {
        this.calibrationCode = calibrationCode;
    }

    public Date getTermValidity() {
        return termValidity;
    }

    public void setTermValidity(Date termValidity) {
        this.termValidity = termValidity;
    }
}
