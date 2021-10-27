package com.xjt.cloud.iot.core.dao.iot.smoke;

import com.xjt.cloud.iot.core.entity.smoke.SmokeEventHandle;
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
public interface SmokeEventHandleDao {

    /**
     * 保存事件处理信息
     *
     * @param smokeEventHandleList SmokeEventHandle
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return Integer
     */
    Integer saveSmokeEventHandle(List<SmokeEventHandle> smokeEventHandleList);

    /**
     * 查询事件处理信息列表
     *
     * @param smokeEventHandle SmokeEventHandle
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return List<SmokeEventHandle>
     */
    List<SmokeEventHandle> findSmokeEventHandleList(SmokeEventHandle smokeEventHandle);

    /**
     * 查询事件处理信息总数
     *
     * @param smokeEventHandle SmokeEventHandle
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return List<SmokeEventHandle>
     */
    Integer findSmokeEventHandleListCount(SmokeEventHandle smokeEventHandle);

    /**
     * 查询事件处理信息列表
     *
     * @param smokeEventHandle SmokeEventHandle
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return List<EventFaultReport>
     */
    List<EventFaultReport> findSmokeEventFaultTypeColumnChart(SmokeEventHandle smokeEventHandle);
}
