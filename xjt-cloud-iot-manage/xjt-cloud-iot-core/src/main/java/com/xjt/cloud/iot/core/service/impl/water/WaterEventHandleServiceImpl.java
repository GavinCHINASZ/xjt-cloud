package com.xjt.cloud.iot.core.service.impl.water;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.iot.core.common.EventFaultReportUtils;
import com.xjt.cloud.iot.core.dao.iot.water.WaterEventHandleDao;
import com.xjt.cloud.iot.core.entity.water.EventFaultReport;
import com.xjt.cloud.iot.core.entity.water.WaterEventHandle;
import com.xjt.cloud.iot.core.service.service.water.WaterEventHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName WaterEventHandleServiceImpl
 * @Description 水压水浸消火栓事件处理信息
 * @Author wangzhiwen
 * @Date 2020/12/7 10:29
 **/
@Service
public class WaterEventHandleServiceImpl extends AbstractService implements WaterEventHandleService {
    @Autowired
    private WaterEventHandleDao waterEventHandleDao;

    /**
     * @Description 保存事件处理信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveWaterEventHandle(String json){
        WaterEventHandle waterEventHandle = JSONObject.parseObject(json, WaterEventHandle.class);
        waterEventHandle = setEntityUserIdName(SecurityUserHolder.getUserName(),waterEventHandle.getProjectId(), waterEventHandle);
        List<WaterEventHandle> list = new ArrayList<>();
        Long[] eventIds = waterEventHandle.getEventIds();
        if (eventIds != null && eventIds.length > 0){
            WaterEventHandle tmp;
            Long[] deviceIds = waterEventHandle.getDeviceIds();
            Long[] waterIds = waterEventHandle.getWaterIds();

            for (int i = 0; i < eventIds.length;i++){
                tmp = new WaterEventHandle(waterEventHandle.getProjectId(), deviceIds[i], waterEventHandle.getDeviceFaultTypeId(), waterIds[i],
                        eventIds[i], waterEventHandle.getDeviceType(), waterEventHandle.getDescription(), waterEventHandle.getImgUrls(),
                        waterEventHandle.getCreateUserId(), waterEventHandle.getCreateUserName());
                list.add(tmp);
            }
        }
        return asseData(waterEventHandleDao.saveWaterEventHandle(list));
    }

    /**
     * @Description 查询事件处理信息列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findWaterEventHandleList(String json){
        WaterEventHandle waterEventHandle = JSONObject.parseObject(json, WaterEventHandle.class);
        if (waterEventHandle.getOrderCols() == null) {
            String[] orderCols = {"createTime"};
            waterEventHandle.setOrderCols(orderCols);
            waterEventHandle.setOrderDesc(true);
        }

        Integer totalCount = waterEventHandle.getTotalCount();
        Integer pageSize = waterEventHandle.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {//判断是否存在总数，如没有，则查询总记录数
            totalCount = waterEventHandleDao.findWaterEventHandleListCount(waterEventHandle);
        }
        List<WaterEventHandle> list = waterEventHandleDao.findWaterEventHandleList(waterEventHandle);
        return asseData(totalCount, list);
    }

    /**
     * @Description 查询水设备事件导常分类统计柱状图
     *
     * @param json
     * @author wangzhiwen
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findWaterEventFaultTypeColumnChart(String json){
        WaterEventHandle waterEventHandle = JSONObject.parseObject(json, WaterEventHandle.class);
        if (waterEventHandle.getCreateTime() == null){
            Date date = DateUtils.getDate();
            waterEventHandle.setCreateTime(date);
            waterEventHandle.setEndTime(date);
        }

        List<EventFaultReport> list = waterEventHandleDao.findWaterEventFaultTypeColumnChart(waterEventHandle);
        return asseData(EventFaultReportUtils.eventFaultHandleReportSort(list));
    }
}
