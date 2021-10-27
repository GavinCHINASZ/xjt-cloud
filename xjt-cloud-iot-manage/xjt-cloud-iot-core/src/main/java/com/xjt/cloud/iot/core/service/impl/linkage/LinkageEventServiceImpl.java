package com.xjt.cloud.iot.core.service.impl.linkage;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.dao.iot.linkage.LinkageEventDao;
import com.xjt.cloud.iot.core.entity.linkage.LinkageDeviceReport;
import com.xjt.cloud.iot.core.entity.linkage.LinkageEvent;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 声光报警
 * 联动事件 service实现类
 *
 * @author huanggc
 * @date 2020/08/18
 **/
@Service
public class LinkageEventServiceImpl extends AbstractService implements LinkageEventService {
    @Autowired
    private LinkageEventDao linkageEventDao;

    /**
     * 功能描述:查询 联动事件 列表
     *
     * @param json 参数 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/08/18
     */
    @Override
    public Data findLinkageEventList(String json) {
        LinkageEvent linkageEvent = JSONObject.parseObject(json, LinkageEvent.class);

        Integer totalCount = linkageEvent.getTotalCount();
        Integer pageSize = linkageEvent.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = linkageEventDao.findLinkageEventTotalCount(linkageEvent);
        }

        List<LinkageEvent> linkageEventList = findLinkageEventList(linkageEvent);
        return asseData(totalCount, linkageEventList);
    }

    /**
     * 功能描述:导出 联动事件 列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/08/19
     */
    @Override
    public void downLinkageEventList(String json, HttpServletResponse response) {
        LinkageEvent linkageEvent = JSONObject.parseObject(json, LinkageEvent.class);
        List<LinkageEvent> linkageEventList = findLinkageEventList(linkageEvent);
        if (CollectionUtils.isEmpty(linkageEventList)) {
            return;
        }

        Long projectId = linkageEvent.getProjectId();
        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        // 表名
        JSONObject jsonObject = JSONObject.parseObject(json);
        jsonObject.put("title", projectName + "--声光报警告警事件列表");

        String[] keys = {"rowNum", "eventTypeDesc", "deviceQrNo", "checkPointQrNo", "sensorId", "createTimeDesc", "endHeartbeatTimeDesc", "recoverStatusDesc",
                "updateUserNameDesc", "handleStatusDesc", "endHandleTimesDesc", "pointLocationDesc"};

        ExcelUtils.createAndDownloadExcel(response, linkageEventList, keys, ConstantsIot.LINKAGE_EVENT_EXCEL_MODEL_FILE_PATH, 3, null,
                jsonObject, "1:0");
    }

    /**
     * 查询 声光报警事件list
     *
     * @param linkageEvent LinkageEvent
     * @return List<LinkageEvent>
     */
    public List<LinkageEvent> findLinkageEventList(LinkageEvent linkageEvent) {
        if (null == linkageEvent.getOrderCols()) {
            String[] orderCols = {"lastModifyTime"};
            linkageEvent.setOrderCols(orderCols);
            linkageEvent.setOrderDesc(true);
        }
        return linkageEventDao.findLinkageEventList(linkageEvent);
    }

    /**
     * 功能描述:查询 联动事件 饼图
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/08/18
     */
    @Override
    public Data findLinkageEventSummaryReport(String json) {
        LinkageEvent linkageEvent = com.alibaba.fastjson.JSONObject.parseObject(json, LinkageEvent.class);

        if (linkageEvent.getCreateTime() == null) {
            Date date = DateUtils.getDate();
            linkageEvent.setCreateTime(date);
            linkageEvent.setEndTime(date);
        }
        LinkageDeviceReport linkageEventReport = linkageEventDao.findLinkageEventSummaryReport(linkageEvent);
        return asseData(linkageEventReport);
    }

    /**
     * 功能描述:查询 联动事件 拆线图
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/08/18
     */
    @Override
    public Data findLinkageEventReportCount(String json) {
        LinkageEvent linkageEvent = com.alibaba.fastjson.JSONObject.parseObject(json, LinkageEvent.class);
        if (linkageEvent.getCreateTime() == null) {
            Date date = DateUtils.getDate();
            linkageEvent.setCreateTime(date);
            linkageEvent.setEndTime(date);
        }
        linkageEvent.setGroupType(DateUtils.getBetweenDateTimeType(linkageEvent.getCreateTime(), linkageEvent.getEndTime()));

        List<LinkageEvent> linkageEventList = linkageEventDao.findLinkageEventReportCount(linkageEvent);
        return asseData(linkageEventList);
    }

}
