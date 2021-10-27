package com.xjt.cloud.iot.core.dao.iot.linkage;

import com.xjt.cloud.iot.core.entity.linkage.LinkageDevice;
import com.xjt.cloud.iot.core.entity.linkage.LinkageDeviceReport;
import com.xjt.cloud.iot.core.entity.linkage.LinkageEvent;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 联动事件
 *
 * @author huanggc
 * @date 2020/08/18
 **/
@Repository
public interface LinkageEventDao {
    /**
     * 功能描述:查询 联动事件 数量
     *
     * @param json
     * @auther linkageEvent
     * @date 2020/08/18
     * @return Integer
     */
    Integer findLinkageEventTotalCount(LinkageEvent linkageEvent);

    /**
     * 功能描述:查询 联动事件 列表
     *
     * @param linkageEvent
     * @auther huanggc
     * @date 2020/08/18
     * @return List<LinkageEvent>
     */
    List<LinkageEvent> findLinkageEventList(LinkageEvent linkageEvent);

    /**
     * 功能描述: 保存 联动事件
     *
     * @param linkageEvent
     * @auther huanggc
     * @date 2020/08/18
     * @return Integer
     */
    Integer saveLinkageEvent(LinkageEvent linkageEvent);

    /**
     * 功能描述:查询 联动事件 饼图
     *
     * @param linkageEvent
     * @auther huanggc
     * @date 2020/08/18
     * @return LinkageDeviceReport
     */
    LinkageDeviceReport findLinkageEventSummaryReport(LinkageEvent linkageEvent);

    /**
     * 功能描述:查询 联动事件 拆线图
     *
     * @param linkageEvent
     * @auther huanggc
     * @date 2020/08/18
     * @return List<LinkageEvent>
     */
    List<LinkageEvent> findLinkageEventReportCount(LinkageEvent linkageEvent);

    /**
     * 功能描述:批量保存 事件
     *
     * @param linkageDeviceList
     * @auther huanggc
     * @date 2020/08/27
     * @return Integer
     */
    Integer saveLinkageEventByDevice(List<LinkageDevice> linkageDeviceList);

    /**
     * 功能描述:　更新事件
     *
     * @param Integer
     * @auther huanggc
     * @date 2020/08/29
     * @return Integer
     */
    Integer updateLinkageEvent(LinkageEvent linkageEvent);
}
