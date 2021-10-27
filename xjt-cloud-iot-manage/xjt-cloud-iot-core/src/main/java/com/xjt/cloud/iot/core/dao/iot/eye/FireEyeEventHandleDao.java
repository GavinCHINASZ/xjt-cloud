package com.xjt.cloud.iot.core.dao.iot.eye;

import com.xjt.cloud.iot.core.entity.eye.FireEyeEventHandle;
import com.xjt.cloud.iot.core.entity.water.EventFaultReport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 火眼 事件处理信息
 *
 * @author huanggc
 * @date 2021/01/20
 **/
@Repository
public interface FireEyeEventHandleDao {

    /**
     * 保存事件处理信息
     *
     * @param fireEyeEventHandleList List<FireEyeEventHandle>
     * @return Integer
     * @author huanggc
     * @date 2020/12/7 10:58
     */
    Integer saveFireEyeEventHandle(List<FireEyeEventHandle> fireEyeEventHandleList);

    /**
     * 查询事件处理信息列表
     *
     * @param fireEyeEventHandle FireEyeEventHandle
     * @return List<FireEyeEventHandle>
     * @author huanggc
     * @date 2020/12/7 10:58
     */
    List<FireEyeEventHandle> findFireEyeEventHandleList(FireEyeEventHandle fireEyeEventHandle);

    /**
     * 查询事件处理信息总数
     *
     * @param fireEyeEventHandle FireEyeEventHandle
     * @return List<FireEyeEventHandle>
     * @author huanggc
     * @date 2020/12/7 10:58
     */
    Integer findFireEyeEventHandleListCount(FireEyeEventHandle fireEyeEventHandle);

    /**
     * 查询事件处理信息列表
     *
     * @param fireEyeEventHandle FireEyeEventHandle
     * @return List<EventFaultReport>
     * @author huanggc
     * @date 2020/12/7 10:58
     */
    List<EventFaultReport> findFireEyeEventFaultTypeColumnChart(FireEyeEventHandle fireEyeEventHandle);
}
