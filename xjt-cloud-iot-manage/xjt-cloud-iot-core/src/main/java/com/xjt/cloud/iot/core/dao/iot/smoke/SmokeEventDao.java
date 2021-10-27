package com.xjt.cloud.iot.core.dao.iot.smoke;

import com.xjt.cloud.iot.core.entity.smoke.SmokeDevice;
import com.xjt.cloud.iot.core.entity.smoke.SmokeDeviceReport;
import com.xjt.cloud.iot.core.entity.smoke.SmokeEvent;
import com.xjt.cloud.iot.core.entity.water.WaterDeviceReport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 烟感事件 dao
 *
 * @author huanggc
 * @date 2020/07/15
 */
@Repository
public interface SmokeEventDao {
    /**
     * 功能描述 查询烟感事件列表
     *
     * @param smokeDevice 烟感事件 实体
     * @auther huanggc
     * @date 2020/07/15
     * @return List<SmokeEvent> 烟感事件 实体list
     */
    List<SmokeEvent> findSmokeEventList(SmokeEvent smokeEvent);

    /**
     * 功能描述 查询烟感事件 数量
     *
     * @param smokeDevice 烟感事件 实体
     * @auther huanggc
     * @date 2020/07/15
     * @return Integer
     */
    Integer findSmokeEventListTotalCount(SmokeEvent smokeEvent);

    /**
     * 功能描述: 保存 烟感事件
     *
     * @param smokeDevice
     * @auther huanggc
     * @date 2020/07/16
     * @return Integer
     */
    Integer saveSmokeEvent(SmokeEvent newInterSomkeEvent);

    /**
     * 功能描述: 烟感告警事件 报表 饼图
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    SmokeDeviceReport findSmokeEventSummaryReport(SmokeEvent smokeEvent);

    /**
     * 功能描述: 烟感告警事件 报表 曲线图
     *
     * @param smokeEvent
     * @auther huanggc
     * @date 2020/07/31
     * @return List<WaterDeviceReport>
     */
    List<SmokeEvent> findSmokeEventReportCount(SmokeEvent smokeEvent);

    /**
     * 功能描述: 烟感告警事件 恢复 事件类型
     *
     * @param smokeEvent
     * @auther huanggc
     * @date 2020/08/10
     * @return Integer
     */
    Integer updateSmokeEvent(SmokeEvent smokeEvent);
}
