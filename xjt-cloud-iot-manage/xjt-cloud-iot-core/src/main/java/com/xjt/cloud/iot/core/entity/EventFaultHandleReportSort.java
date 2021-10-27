package com.xjt.cloud.iot.core.entity;

import com.xjt.cloud.iot.core.entity.water.EventFaultReport;

import java.util.List;

/**
 * @ClassName EventFaultHandleReportSort
 * @Description 物联设备事件处理报表信息实体类排序类
 * @Author wangzhiwen
 * @Date 2020/12/16 9:21
 **/
public class EventFaultHandleReportSort implements Comparable<EventFaultHandleReportSort>{

    private Integer sortNum;
    private List<EventFaultReport> list;

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public List<EventFaultReport> getList() {
        return list;
    }

    public void setList(List<EventFaultReport> list) {
        this.list = list;
    }

    @Override
    public int compareTo(EventFaultHandleReportSort o) {
        if (this.sortNum.compareTo(o.getSortNum()) > 0){
            return -1;
        }else if(this.sortNum.compareTo(o.getSortNum()) == 0){
            return 0;
        }
        return 1;
    }
}