package com.xjt.cloud.task.core.entity.check;

import java.util.List;

/**
 *
 * 巡查信息
 * @ClassName CheckInfo
 * @Author dwt
 * @Date 2019-08-14 15:45
 * @Version 1.0
 */
public class CheckInfo {
    private String sysName;
    private List<CheckItemRecord> checkItemRecordList;

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public List<CheckItemRecord> getCheckItemRecordList() {
        return checkItemRecordList;
    }

    public void setCheckItemRecordList(List<CheckItemRecord> checkItemRecordList) {
        this.checkItemRecordList = checkItemRecordList;
    }
}
