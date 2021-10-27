package com.xjt.cloud.iot.core.dao.iot.water;

import com.xjt.cloud.iot.core.entity.water.EventFaultReport;
import com.xjt.cloud.iot.core.entity.water.WaterEventHandle;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName WaterEventHandleDao
 * @Description 水压水浸消火栓事件处理信息
 * @Author wangzhiwen
 * @Date 2020/12/7 10:29
 **/
@Repository
public interface WaterEventHandleDao {

    /**
     * @Description 保存事件处理信息
     *
     * @param list
     * @author wangzhiwen
     * @date 2020/12/7 10:58
     * @return Integer
     */
    Integer saveWaterEventHandle(List<WaterEventHandle> list);

    /**
     * @Description 查询事件处理信息列表
     *
     * @param waterEventHandle
     * @author wangzhiwen
     * @date 2020/12/7 10:58
     * @return List<WaterEventHandle>
     */
    List<WaterEventHandle> findWaterEventHandleList(WaterEventHandle waterEventHandle);

    /**
     * @Description 查询事件处理信息总数
     *
     * @param waterEventHandle
     * @author wangzhiwen
     * @date 2020/12/7 10:58
     * @return List<WaterEventHandle>
     */
    Integer findWaterEventHandleListCount(WaterEventHandle waterEventHandle);

    /**
     * @Description 查询事件处理信息列表
     *
     * @param waterEventHandle
     * @author wangzhiwen
     * @date 2020/12/7 10:58
     * @return List<EventFaultReport>
     */
    List<EventFaultReport> findWaterEventFaultTypeColumnChart(WaterEventHandle waterEventHandle);
}
