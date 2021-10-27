package com.xjt.cloud.iot.core.service.impl.vesa;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.iot.core.common.EventFaultReportUtils;
import com.xjt.cloud.iot.core.dao.iot.vesa.VesaEventHandleDao;
import com.xjt.cloud.iot.core.entity.vesa.VesaEventHandle;
import com.xjt.cloud.iot.core.entity.water.EventFaultReport;
import com.xjt.cloud.iot.core.service.service.vesa.VesaEventHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 极早期 事件处理信息
 *
 * @author huanggc
 * @date 2020/12/9
 **/
@Service
public class VesaEventHandleServiceImpl extends AbstractService implements VesaEventHandleService {
    @Autowired
    private VesaEventHandleDao vesaEventHandleDao;

    /**
     * 保存事件处理信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/12/7 10:58
     */
    @Override
    public Data saveVesaEventHandle(String json) {
        VesaEventHandle vesaEventHandle = JSONObject.parseObject(json, VesaEventHandle.class);
        vesaEventHandle = setEntityUserIdName(SecurityUserHolder.getUserName(), vesaEventHandle.getProjectId(), vesaEventHandle);

        List<VesaEventHandle> list = new ArrayList<>();
        Long[] eventIds = vesaEventHandle.getEventIds();
        if (eventIds != null && eventIds.length > 0) {
            VesaEventHandle tmp;
            Long[] deviceIds = vesaEventHandle.getDeviceIds();
            Long[] vesaDeviceIds = vesaEventHandle.getVesaDeviceIds();

            for (int i = 0; i < eventIds.length; i++) {
                tmp = new VesaEventHandle(vesaEventHandle.getProjectId(), deviceIds[i], vesaEventHandle.getDeviceFaultTypeId(), vesaDeviceIds[i],
                        eventIds[i], vesaEventHandle.getDeviceType(), vesaEventHandle.getDescription(), vesaEventHandle.getImgUrls(),
                        vesaEventHandle.getCreateUserId(), vesaEventHandle.getCreateUserName());
                list.add(tmp);
            }
        }
        return asseData(vesaEventHandleDao.saveVesaEventHandle(list));
    }

    /**
     * 查询事件处理信息列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/12/7 10:58
     */
    @Override
    public Data findVesaEventHandleList(String json) {
        VesaEventHandle vesaEventHandle = JSONObject.parseObject(json, VesaEventHandle.class);

        if (vesaEventHandle.getOrderCols() == null) {
            String[] orderCols = {"createTime"};
            vesaEventHandle.setOrderCols(orderCols);
            vesaEventHandle.setOrderDesc(true);
        }

        Integer totalCount = vesaEventHandle.getTotalCount();
        Integer pageSize = vesaEventHandle.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            //判断是否存在总数，如没有，则查询总记录数
            totalCount = vesaEventHandleDao.findVesaEventHandleListCount(vesaEventHandle);
        }
        List<VesaEventHandle> list = vesaEventHandleDao.findVesaEventHandleList(vesaEventHandle);
        return asseData(totalCount, list);
    }

    /**
     * 查询 极早期设备事件导常分类统计柱状图
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/12/7 10:58
     */
    @Override
    public Data findVesaEventFaultTypeColumnChart(String json) {
        VesaEventHandle vesaEventHandle = JSONObject.parseObject(json, VesaEventHandle.class);
        if (vesaEventHandle.getCreateTime() == null) {
            Date date = DateUtils.getDate();
            vesaEventHandle.setCreateTime(date);
            vesaEventHandle.setEndTime(date);
        }
        List<EventFaultReport> list = vesaEventHandleDao.findVesaEventFaultTypeColumnChart(vesaEventHandle);
        return asseData(EventFaultReportUtils.eventFaultHandleReportSort(list));
    }
}
