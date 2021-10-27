package com.xjt.cloud.iot.core.service.impl.eye;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.iot.core.common.EventFaultReportUtils;
import com.xjt.cloud.iot.core.dao.iot.eye.FireEyeEventHandleDao;
import com.xjt.cloud.iot.core.entity.eye.FireEyeEventHandle;
import com.xjt.cloud.iot.core.entity.water.EventFaultReport;
import com.xjt.cloud.iot.core.service.service.eye.FireEyeEventHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 火眼 事件处理信息
 *
 * @author huanggc
 * @date 2020/12/9
 **/
@Service
public class FireEyeEventHandleServiceImpl extends AbstractService implements FireEyeEventHandleService {
    @Autowired
    private FireEyeEventHandleDao fireEyeEventHandleDao;

    /**
     * 保存事件处理信息
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveFireEyeEventHandle(String json){
        FireEyeEventHandle fireEyeEventHandle = JSONObject.parseObject(json, FireEyeEventHandle.class);
        fireEyeEventHandle = setEntityUserIdName(SecurityUserHolder.getUserName(), fireEyeEventHandle.getProjectId(), fireEyeEventHandle);

        List<FireEyeEventHandle> list = new ArrayList<>();
        Long[] eventIds = fireEyeEventHandle.getEventIds();
        if (eventIds != null && eventIds.length > 0){
            FireEyeEventHandle tmp;
            Long[] deviceIds = fireEyeEventHandle.getDeviceIds();
            Long[] smokeDeviceIds = fireEyeEventHandle.getFireEyeDeviceIds();

            for (int i = 0; i < eventIds.length; i++){
                tmp = new FireEyeEventHandle(fireEyeEventHandle.getProjectId(), deviceIds[i], fireEyeEventHandle.getDeviceFaultTypeId(), smokeDeviceIds[i],
                        eventIds[i], fireEyeEventHandle.getDeviceType(), fireEyeEventHandle.getDescription(), fireEyeEventHandle.getImgUrls(),
                        fireEyeEventHandle.getCreateUserId(), fireEyeEventHandle.getCreateUserName());
                list.add(tmp);
            }
        }
        return asseData(fireEyeEventHandleDao.saveFireEyeEventHandle(list));
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
    public Data findFireEyeEventHandleList(String json){
        FireEyeEventHandle fireEyeEventHandle = JSONObject.parseObject(json, FireEyeEventHandle.class);

        if (fireEyeEventHandle.getOrderCols() == null) {
            String[] orderCols = {"createTime"};
            fireEyeEventHandle.setOrderCols(orderCols);
            fireEyeEventHandle.setOrderDesc(true);
        }

        Integer totalCount = fireEyeEventHandle.getTotalCount();
        Integer pageSize = fireEyeEventHandle.getPageSize();

        if (null == totalCount && null != pageSize && 0 != pageSize) {
            // 判断是否存在总数，如没有，则查询总记录数
            totalCount = fireEyeEventHandleDao.findFireEyeEventHandleListCount(fireEyeEventHandle);
        }
        List<FireEyeEventHandle> list = fireEyeEventHandleDao.findFireEyeEventHandleList(fireEyeEventHandle);
        return asseData(totalCount, list);
    }

    /**
     * 查询 火眼设备事件导常分类统计柱状图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findFireEyeEventFaultTypeColumnChart(String json){
        FireEyeEventHandle fireEyeEventHandle = JSONObject.parseObject(json, FireEyeEventHandle.class);
        if (fireEyeEventHandle.getCreateTime() == null){
            Date date = DateUtils.getDate();
            fireEyeEventHandle.setCreateTime(date);
            fireEyeEventHandle.setEndTime(date);
        }
        List<EventFaultReport> list = fireEyeEventHandleDao.findFireEyeEventFaultTypeColumnChart(fireEyeEventHandle);

        return asseData(EventFaultReportUtils.eventFaultHandleReportSort(list));
    }
}
