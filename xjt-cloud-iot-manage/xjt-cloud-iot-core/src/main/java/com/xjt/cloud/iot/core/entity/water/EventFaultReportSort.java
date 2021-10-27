package com.xjt.cloud.iot.core.entity.water;

import java.util.List;

/**
 * @ClassName EventFaultReportSort
 * @Description 物联设备运营报表信息实体类排序类
 * @Author wangzhiwen
 * @Date 2020/12/15 16:33
 **/
public class EventFaultReportSort implements Comparable<EventFaultReportSort>{
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
    public int compareTo(EventFaultReportSort o) {
        if (this.sortNum.compareTo(o.getSortNum()) > 0){
            return -1;
        }else if(this.sortNum.compareTo(o.getSortNum()) == 0){
            return 0;
        }
        return 1;
    }
}
