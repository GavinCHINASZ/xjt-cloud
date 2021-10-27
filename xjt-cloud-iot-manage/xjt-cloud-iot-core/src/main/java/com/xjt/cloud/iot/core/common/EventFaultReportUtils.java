package com.xjt.cloud.iot.core.common;

import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.iot.core.entity.EventFaultHandleReportSort;
import com.xjt.cloud.iot.core.entity.water.EventFaultReport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName EventFaultReportUtils
 * @Description
 * @Author wangzhiwen
 * @Date 2020/12/16 9:20
 **/
public class EventFaultReportUtils {
    /**
     * @Description 报表数据分组排序
     *
     * @param list
     * @author wangzhiwen
     * @date 2020/12/15 16:54
     * @return java.util.List<java.util.List < com.xjt.cloud.iot.core.entity.water.EventFaultReport>>
     */
    public static List<List<EventFaultReport>> eventFaultHandleReportSort(List<EventFaultReport> list){
        List<List<EventFaultReport>> temList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)){
            List<EventFaultReport> efrList = new ArrayList<>();
            List<EventFaultHandleReportSort> efhrsList = new ArrayList<>();
            EventFaultReport efr;
            EventFaultHandleReportSort efhrs;
            int size = list.size();
            int total = 0;
            for (int i = 0;i < size;i++){
                efr = list.get(i);
                efrList.add(efr);
                total += efr.getFaultCount();
                if (i < size - 1){
                    if (efr.getFaultEventType() != list.get(i + 1).getFaultEventType()){
                        efhrs = new EventFaultHandleReportSort();
                        efhrs.setSortNum(total);
                        efhrs.setList(efrList);
                        efhrsList.add(efhrs);
                        efrList = new ArrayList<>();
                        total = 0;
                    }
                }else{
                    efhrs = new EventFaultHandleReportSort();
                    efhrs.setSortNum(total);
                    efhrs.setList(efrList);
                    efhrsList.add(efhrs);
                }
            }
            if (CollectionUtils.isNotEmpty(efhrsList)) {
                Collections.sort(efhrsList);
                for(EventFaultHandleReportSort eventFaultHandleReportSort:efhrsList){
                    temList.add(eventFaultHandleReportSort.getList());
                }
            }
        }
        return temList;
    }
}
