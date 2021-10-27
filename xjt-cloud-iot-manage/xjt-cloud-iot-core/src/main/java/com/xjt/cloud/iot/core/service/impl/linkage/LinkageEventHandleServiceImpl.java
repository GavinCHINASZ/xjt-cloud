package com.xjt.cloud.iot.core.service.impl.linkage;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.iot.core.common.EventFaultReportUtils;
import com.xjt.cloud.iot.core.dao.iot.linkage.LinkageEventHandleDao;
import com.xjt.cloud.iot.core.entity.linkage.LinkageEventHandle;
import com.xjt.cloud.iot.core.entity.water.EventFaultReport;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageEventHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 声光报警 事件处理信息
 *
 * @author huanggc
 * @date 2020/12/9
 **/
@Service
public class LinkageEventHandleServiceImpl extends AbstractService implements LinkageEventHandleService {
    @Autowired
    private LinkageEventHandleDao linkageEventHandleDao;

    /**
     * 保存事件处理信息
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveLinkageEventHandle(String json){
        LinkageEventHandle linkageEventHandle = JSONObject.parseObject(json, LinkageEventHandle.class);
        linkageEventHandle = setEntityUserIdName(SecurityUserHolder.getUserName(), linkageEventHandle.getProjectId(), linkageEventHandle);

        List<LinkageEventHandle> list = new ArrayList<>();
        Long[] eventIds = linkageEventHandle.getEventIds();
        if (eventIds != null && eventIds.length > 0){
            LinkageEventHandle tmp;
            Long[] deviceIds = linkageEventHandle.getDeviceIds();
            Long[] linkageDeviceIds = linkageEventHandle.getLinkageDeviceIds();

            for (int i = 0; i < eventIds.length;i++){
                tmp = new LinkageEventHandle(linkageEventHandle.getProjectId(), deviceIds[i], linkageEventHandle.getDeviceFaultTypeId(), linkageDeviceIds[i],
                        eventIds[i], linkageEventHandle.getDeviceType(), linkageEventHandle.getDescription(), linkageEventHandle.getImgUrls(),
                        linkageEventHandle.getCreateUserId(), linkageEventHandle.getCreateUserName());
                list.add(tmp);
            }
        }
        return asseData(linkageEventHandleDao.saveLinkageEventHandle(list));
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
    public Data findLinkageEventHandleList(String json){
        LinkageEventHandle linkageEventHandle = JSONObject.parseObject(json, LinkageEventHandle.class);

        if (linkageEventHandle.getOrderCols() == null) {
            String[] orderCols = {"createTime"};
            linkageEventHandle.setOrderCols(orderCols);
            linkageEventHandle.setOrderDesc(true);
        }

        Integer totalCount = linkageEventHandle.getTotalCount();
        Integer pageSize = linkageEventHandle.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            // 判断是否存在总数，如没有，则查询总记录数
            totalCount = linkageEventHandleDao.findLinkageEventHandleListCount(linkageEventHandle);
        }
        List<LinkageEventHandle> list = linkageEventHandleDao.findLinkageEventHandleList(linkageEventHandle);
        return asseData(totalCount, list);
    }

    /**
     * 查询 声光设备 事件导常分类统计柱状图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findLinkageEventFaultTypeColumnChart(String json){
        LinkageEventHandle linkageEventHandle = JSONObject.parseObject(json, LinkageEventHandle.class);
        if (linkageEventHandle.getCreateTime() == null){
            Date date = DateUtils.getDate();
            linkageEventHandle.setCreateTime(date);
            linkageEventHandle.setEndTime(date);
        }
        List<EventFaultReport> list = linkageEventHandleDao.findLinkageEventFaultTypeColumnChart(linkageEventHandle);
        return asseData(EventFaultReportUtils.eventFaultHandleReportSort(list));
    }
}
