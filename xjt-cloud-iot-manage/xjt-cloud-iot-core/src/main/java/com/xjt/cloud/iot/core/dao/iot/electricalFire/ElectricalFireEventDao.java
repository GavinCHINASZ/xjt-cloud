package com.xjt.cloud.iot.core.dao.iot.electricalFire;

import com.xjt.cloud.iot.core.entity.electrical.ElectricalFireEvent;
import com.xjt.cloud.iot.core.entity.electrical.ElectricalFireReport;

import java.util.List;

/**
 *@ClassName ElectricalFireEventDao
 *@Author dwt
 *@Date 2020-09-15 11:52
 *@Version 1.0
 */
public interface ElectricalFireEventDao {
    /**
     *@Author: dwt
     *@Date: 2020-09-15 14:02
     *@Param: ElectricalFireEvent
     *@Return: ElectricalFireEvent
     *@Description 查询电气火灾事件列表
     */
   List<ElectricalFireEvent> findElectricalFireEventList(ElectricalFireEvent electricalFireEvent);
    /**
     *@Author: dwt
     *@Date: 2020-09-15 14:03
     *@Param: ElectricalFireEvent
     *@Return: java.lang.Integer
     *@Description 查询电气火灾事件总数
     */
   Integer findElectricalFireEventCount(ElectricalFireEvent electricalFireEvent);
    /**
     *@Author: dwt
     *@Date: 2020-09-15 14:06
     *@Param: ElectricalFireEvent
     *@Return: java.lang.Integer
     *@Description 编辑电气火灾事件
     */
   Integer modifyElectricalFireEvent(ElectricalFireEvent electricalFireEvent);
    /**
     *@Author: dwt
     *@Date: 2020-09-15 14:08
     *@Param: ElectricalFireEvent
     *@Return: java.lang.Integer
     *@Description 保存电气火灾事件
     */
   Integer saveElectricalFireEvent(ElectricalFireEvent electricalFireEvent);
   /**
    *@Author: dwt
    *@Date: 2020-09-21 11:35
    *@Param: ElectricalFireReport
    *@Return: ElectricalFireReport
    *@Description 统计电气火灾各事件数（漏电流事件数，温度事件数，故障事件数）
    */
   ElectricalFireReport countElectricalFireReport(ElectricalFireReport electricalFireReport);
    /**
     *@Author: dwt
     *@Date: 2020-09-24 9:39
     *@Param: ElectricalFireEvent
     *@Return: ElectricalFireReport
     *@Description 查询电气火灾事件折线图数据
    */
   List<ElectricalFireReport> findBrokenLineEventNum(ElectricalFireEvent electricalFireEvent);
    /**
     *@Author: dwt
     *@Date: 2020-09-24 14:05
     *@Param: ElectricalFireEvent
     *@Return: ElectricalFireEvent
     *@Description 查询电气火灾事件详情
     */
   ElectricalFireEvent findElectricalFireEventDetail(ElectricalFireEvent electricalFireEvent);
    /**
     *@Author: dwt
     *@Date: 2020-09-28 11:29
     *@Param: ElectricalFireEvent
     *@Return: java.lang.Integer
     *@Description 故障事件恢复
     */
   Integer recoverEventStatus(ElectricalFireEvent electricalFireEvent);
}
