package com.xjt.cloud.iot.core.service.impl.smoke;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.iot.core.common.EventFaultReportUtils;
import com.xjt.cloud.iot.core.dao.iot.smoke.SmokeEventHandleDao;
import com.xjt.cloud.iot.core.entity.smoke.SmokeEventHandle;
import com.xjt.cloud.iot.core.entity.water.EventFaultReport;
import com.xjt.cloud.iot.core.service.service.smoke.SmokeEventHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 烟感 事件处理信息
 *
 * @author huanggc
 * @date 2020/12/9
 **/
@Service
public class SmokeEventHandleServiceImpl extends AbstractService implements SmokeEventHandleService {
    @Autowired
    private SmokeEventHandleDao smokeEventHandleDao;

    /**
     * 保存事件处理信息
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveSmokeEventHandle(String json){
        SmokeEventHandle smokeEventHandle = JSONObject.parseObject(json, SmokeEventHandle.class);
        smokeEventHandle = setEntityUserIdName(SecurityUserHolder.getUserName(), smokeEventHandle.getProjectId(), smokeEventHandle);

        List<SmokeEventHandle> list = new ArrayList<>();
        Long[] eventIds = smokeEventHandle.getEventIds();
        if (eventIds != null && eventIds.length > 0){
            SmokeEventHandle tmp;
            Long[] deviceIds = smokeEventHandle.getDeviceIds();
            Long[] smokeDeviceIds = smokeEventHandle.getSmokeDeviceIds();

            for (int i = 0; i < eventIds.length; i++){
                tmp = new SmokeEventHandle(smokeEventHandle.getProjectId(), deviceIds[i], smokeEventHandle.getDeviceFaultTypeId(), smokeDeviceIds[i],
                        eventIds[i], smokeEventHandle.getDeviceType(), smokeEventHandle.getDescription(), smokeEventHandle.getImgUrls(),
                        smokeEventHandle.getCreateUserId(), smokeEventHandle.getCreateUserName());
                list.add(tmp);
            }
        }
        return asseData(smokeEventHandleDao.saveSmokeEventHandle(list));
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
    public Data findSmokeEventHandleList(String json){
        SmokeEventHandle smokeEventHandle = JSONObject.parseObject(json, SmokeEventHandle.class);

        if (smokeEventHandle.getOrderCols() == null) {
            String[] orderCols = {"createTime"};
            smokeEventHandle.setOrderCols(orderCols);
            smokeEventHandle.setOrderDesc(true);
        }

        Integer totalCount = smokeEventHandle.getTotalCount();
        Integer pageSize = smokeEventHandle.getPageSize();

        if (null == totalCount && null != pageSize && 0 != pageSize) {
            // 判断是否存在总数，如没有，则查询总记录数
            totalCount = smokeEventHandleDao.findSmokeEventHandleListCount(smokeEventHandle);
        }
        List<SmokeEventHandle> list = smokeEventHandleDao.findSmokeEventHandleList(smokeEventHandle);
        return asseData(totalCount, list);
    }

    /**
     * 查询 烟感设备事件导常分类统计柱状图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findSmokeEventFaultTypeColumnChart(String json){
        SmokeEventHandle smokeEventHandle = JSONObject.parseObject(json, SmokeEventHandle.class);
        if (smokeEventHandle.getCreateTime() == null){
            Date date = DateUtils.getDate();
            smokeEventHandle.setCreateTime(date);
            smokeEventHandle.setEndTime(date);
        }
        List<EventFaultReport> list = smokeEventHandleDao.findSmokeEventFaultTypeColumnChart(smokeEventHandle);

        return asseData(EventFaultReportUtils.eventFaultHandleReportSort(list));
    }
}
