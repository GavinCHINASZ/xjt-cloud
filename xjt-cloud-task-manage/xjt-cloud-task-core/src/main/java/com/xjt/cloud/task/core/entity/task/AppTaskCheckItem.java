package com.xjt.cloud.task.core.entity.task;

/**
 * @ClassName AppTaskCheckItem
 * @Author dwt
 * @Date 2019-08-15 16:05
 * @Description 巡检项记录
 * @Version 1.0
 */
public class AppTaskCheckItem {
    //巡检项id
    private Long deviceCheckItemId;
    //巡检项名称
    private String checkItemName;
    //巡检结果 0:正常；1：故障
    private Integer checkResult;
    //描述
    private String resultDescription;

    public String getCheckItemName() {
        return checkItemName;
    }

    public void setCheckItemName(String checkItemName) {
        this.checkItemName = checkItemName;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public Long getDeviceCheckItemId() {
        return deviceCheckItemId;
    }

    public void setDeviceCheckItemId(Long deviceCheckItemId) {
        this.deviceCheckItemId = deviceCheckItemId;
    }
}
