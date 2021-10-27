package com.xjt.cloud.iot.core.service.impl.fire;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.iot.core.common.EventFaultReportUtils;
import com.xjt.cloud.iot.core.dao.iot.fire.FireAlarmEventHandleDao;
import com.xjt.cloud.iot.core.entity.fire.FireAlarmEventHandle;
import com.xjt.cloud.iot.core.entity.water.EventFaultReport;
import com.xjt.cloud.iot.core.service.service.fire.FireAlarmEventHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 火警 事件处理信息
 *
 * @author huanggc
 * @date 2020/12/9
 **/
@Service
public class FireAlarmEventHandleServiceImpl extends AbstractService implements FireAlarmEventHandleService {
    @Autowired
    private FireAlarmEventHandleDao fireAlarmEventHandleDao;

    /**
     * 保存事件处理信息
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveFireAlarmEventHandle(String json){
        FireAlarmEventHandle fireAlarmEventHandle = JSONObject.parseObject(json, FireAlarmEventHandle.class);
        fireAlarmEventHandle = setEntityUserIdName(SecurityUserHolder.getUserName(),fireAlarmEventHandle.getProjectId(), fireAlarmEventHandle);

        List<FireAlarmEventHandle> list = new ArrayList<>();
        Long[] eventIds = fireAlarmEventHandle.getEventIds();
        if (eventIds != null && eventIds.length > 0){
            FireAlarmEventHandle tmp;
            Long[] deviceIds = fireAlarmEventHandle.getDeviceIds();
            Long[] fireAlarmDeviceIds = fireAlarmEventHandle.getFireAlarmDeviceIds();

            for (int i = 0; i < eventIds.length;i++){
                tmp = new FireAlarmEventHandle(fireAlarmEventHandle.getProjectId(), deviceIds[i], fireAlarmEventHandle.getDeviceFaultTypeId(), fireAlarmDeviceIds[i],
                        eventIds[i], fireAlarmEventHandle.getDeviceType(), fireAlarmEventHandle.getDescription(), fireAlarmEventHandle.getImgUrls(),
                        fireAlarmEventHandle.getCreateUserId(), fireAlarmEventHandle.getCreateUserName());
                list.add(tmp);
            }
        }
        
        return asseData(fireAlarmEventHandleDao.saveFireAlarmEventHandle(list));
    }

    /**
     * 查询事件处理信息列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findFireAlarmEventHandleList(String json){
        FireAlarmEventHandle fireAlarmEventHandle = JSONObject.parseObject(json, FireAlarmEventHandle.class);

        if (fireAlarmEventHandle.getOrderCols() == null) {
            String[] orderCols = {"createTime"};
            fireAlarmEventHandle.setOrderCols(orderCols);
            fireAlarmEventHandle.setOrderDesc(true);
        }

        Integer totalCount = fireAlarmEventHandle.getTotalCount();
        Integer pageSize = fireAlarmEventHandle.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            //判断是否存在总数，如没有，则查询总记录数
            totalCount = fireAlarmEventHandleDao.findFireAlarmEventHandleListCount(fireAlarmEventHandle);
        }
        List<FireAlarmEventHandle> list = fireAlarmEventHandleDao.findFireAlarmEventHandleList(fireAlarmEventHandle);
        return asseData(totalCount, list);
    }

    /**
     * 查询 火警设备事件导常分类统计柱状图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findFireAlarmEventFaultTypeColumnChart(String json){
        FireAlarmEventHandle fireAlarmEventHandle = JSONObject.parseObject(json, FireAlarmEventHandle.class);
        if (fireAlarmEventHandle.getCreateTime() == null){
            Date date = DateUtils.getDate();
            fireAlarmEventHandle.setCreateTime(date);
            fireAlarmEventHandle.setEndTime(date);
        }
        List<EventFaultReport> list = fireAlarmEventHandleDao.findFireAlarmEventFaultTypeColumnChart(fireAlarmEventHandle);
        return asseData(EventFaultReportUtils.eventFaultHandleReportSort(list));
    }
}
