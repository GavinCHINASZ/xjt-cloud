package com.xjt.cloud.iot.core.dao.iot.vesa;

import com.xjt.cloud.iot.core.entity.vesa.VesaEventHandle;
import com.xjt.cloud.iot.core.entity.water.EventFaultReport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 极早期 事件处理信息
 *
 * @author huanggc
 * @date 2020/12/9
 **/
@Repository
public interface VesaEventHandleDao {

    /**
     * 保存事件处理信息
     *
     * @param vesaEventHandleList VesaEventHandle
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return Integer
     */
    Integer saveVesaEventHandle(List<VesaEventHandle> vesaEventHandleList);

    /**
     * 查询事件处理信息列表
     *
     * @param vesaEventHandle VesaEventHandle
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return List<VesaEventHandle>
     */
    List<VesaEventHandle> findVesaEventHandleList(VesaEventHandle vesaEventHandle);

    /**
     * 查询事件处理信息总数
     *
     * @param vesaEventHandle VesaEventHandle
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return List<VesaEventHandle>
     */
    Integer findVesaEventHandleListCount(VesaEventHandle vesaEventHandle);

    /**
     * 查询事件处理信息列表
     *
     * @param vesaEventHandle VesaEventHandle
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return List<EventFaultReport>
     */
    List<EventFaultReport> findVesaEventFaultTypeColumnChart(VesaEventHandle vesaEventHandle);
}
