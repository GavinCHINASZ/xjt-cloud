package com.xjt.cloud.iot.core.dao.iot.eye;

import com.xjt.cloud.iot.core.entity.eye.FireEyeDevice;
import com.xjt.cloud.iot.core.entity.eye.FireEyeEvent;
import com.xjt.cloud.iot.core.entity.eye.FireEyeEventReport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 火眼事件接口Dao
 *
 * @author zhangZaifa
 * @date 2020/4/3 15:37
 */
@Repository
public interface FireEyeEventDao {
    /**
     * 保存火眼事件
     *
     * @param fireEyeEvent FireEyeEvent
     * @author zhangZaiFa
     * @date 2020/4/3 15:15
     **/
    int save(FireEyeEvent fireEyeEvent);

    /**
     * 查询火眼事件数量
     *
     * @param fireEyeEvent FireEyeEvent
     * @return java.lang.Integer
     * @author zhangZaiFa
     * @date 2020/4/20 15:32
     **/
    Integer findFireEyeEventListCount(FireEyeEvent fireEyeEvent);

    /**
     * 查询火眼事件列表
     *
     * @param fireEyeEvent FireEyeEvent
     * @return java.util.List<com.xjt.cloud.iot.core.entity.eye.FireEyeEvent>
     * @author zhangZaiFa
     * @date 2020/4/20 15:31
     **/
    List<FireEyeEvent> findFireEyeEventList(FireEyeEvent fireEyeEvent);

    /**
     * @param fireEyeEvent
     * @return FireEyeEvent
     * @Description 查询最后一条未恢复事件
     * @author wangzhiwen
     * @date 2021/5/8 11:07
     */
    int findFireEyeLastNotRecoverEvent(FireEyeEvent fireEyeEvent);

    /**
     * 更新 火眼事件
     *
     * @param fireEyeEvent FireEyeEvent
     * @return java.util.List<com.xjt.cloud.iot.core.entity.eye.FireEyeEvent>
     * @author zhangZaiFa
     * @date 2020/4/20 15:31
     **/
    int updateFireEyeEvent(FireEyeEvent fireEyeEvent);

    /**
     * 查询 火眼事件汇总
     *
     * @param fireEyeEvent FireEyeEvent
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/01/21
     **/
    FireEyeEventReport findFireEyeEventSummary(FireEyeEvent fireEyeEvent);

    /**
     * 查询 火眼事件 折线图
     *
     * @param fireEyeEvent 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021-03-23
     **/
    List<FireEyeEventReport> findFireEyeEventReportCount(FireEyeEvent fireEyeEvent);

    /**
     * 新增 火眼事件
     *
     * @param linkageDeviceList 参数
     * @author huanggc
     * @date 2021-03-23
     **/
    void saveFireEventByDevice(List<FireEyeDevice> linkageDeviceList);

    /**
     * 查询 火眼事件 大屏
     *
     * @param fireEyeEvent FireEyeEvent
     * @author huanggc
     * @date 2021/04/28
     * @return List<FireEyeEvent>
     **/
    List<FireEyeEvent> findFireEyeEventScreen(FireEyeEvent fireEyeEvent);
}
