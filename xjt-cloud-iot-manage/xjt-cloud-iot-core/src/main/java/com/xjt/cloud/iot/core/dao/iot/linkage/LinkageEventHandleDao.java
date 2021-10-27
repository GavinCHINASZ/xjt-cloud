package com.xjt.cloud.iot.core.dao.iot.linkage;

import com.xjt.cloud.iot.core.entity.linkage.LinkageEventHandle;
import com.xjt.cloud.iot.core.entity.water.EventFaultReport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 烟感 事件处理信息
 *
 * @author huanggc
 * @date 2020/12/9
 **/
@Repository
public interface LinkageEventHandleDao {

    /**
     * 保存事件处理信息
     *
     * @param linkageEventHandleList LinkageEventHandle
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return Integer
     */
    Integer saveLinkageEventHandle(List<LinkageEventHandle> linkageEventHandleList);

    /**
     * 查询事件处理信息列表
     *
     * @param linkageEventHandle LinkageEventHandle
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return List<LinkageEventHandle>
     */
    List<LinkageEventHandle> findLinkageEventHandleList(LinkageEventHandle linkageEventHandle);

    /**
     * 查询事件处理信息总数
     *
     * @param linkageEventHandle LinkageEventHandle
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return List<LinkageEventHandle>
     */
    Integer findLinkageEventHandleListCount(LinkageEventHandle linkageEventHandle);

    /**
     * 查询事件处理信息列表
     *
     * @param linkageEventHandle LinkageEventHandle
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return List<EventFaultReport>
     */
    List<EventFaultReport> findLinkageEventFaultTypeColumnChart(LinkageEventHandle linkageEventHandle);
}
