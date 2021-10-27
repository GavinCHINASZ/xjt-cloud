package com.xjt.cloud.task.core.entity.check;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 巡检记录
 *
 * @Author dwt
 * @Date 2019-07-25 9:30
 * @Description:定时任务生成的巡检记录
 * @Version 1.0
 */
public class CheckRecords extends BaseEntity {
    //设备名称
    private String deviceName;
    //设备二维码
    private String deviceQrNo;
    // 设备数量
    private Integer deviceCount;
    //巡查点名称
    private String checkPointName;
    //巡更点二维码
    private String checkPointQrNo;
    // 设备位置
    private String checkPointLocation;
    //巡检结果 0：正常，1：故障
    private Integer checkResult;
    //巡检人名称
    private String checkerName;
    //任务名称
    private String taskName;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceQrNo() {
        return deviceQrNo;
    }

    public void setDeviceQrNo(String deviceQrNo) {
        this.deviceQrNo = deviceQrNo;
    }

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }

    public String getCheckPointName() {
        return checkPointName;
    }

    public void setCheckPointName(String checkPointName) {
        this.checkPointName = checkPointName;
    }

    public String getCheckPointQrNo() {
        return checkPointQrNo;
    }

    public void setCheckPointQrNo(String checkPointQrNo) {
        this.checkPointQrNo = checkPointQrNo;
    }

    public String getCheckPointLocation() {
        return checkPointLocation;
    }

    public void setCheckPointLocation(String checkPointLocation) {
        this.checkPointLocation = checkPointLocation;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public String getCheckResultDesc() {
        if (checkResult == null){
            return  "/";
        }
        if (checkResult == 0){
            return "正常";
        }
        return "故障";// 5.0:故障,  地铁:异常
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCreateTimeDescs() {
        if(getCreateTime() != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(getCreateTime());
        }
        return "";
    }
}
