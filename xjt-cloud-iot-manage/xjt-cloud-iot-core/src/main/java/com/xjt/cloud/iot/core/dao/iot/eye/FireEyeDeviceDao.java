package com.xjt.cloud.iot.core.dao.iot.eye;

import com.xjt.cloud.iot.core.entity.eye.FireEyeDevice;
import com.xjt.cloud.iot.core.entity.eye.FireEyeEvent;
import com.xjt.cloud.iot.core.entity.eye.FireEyeEventReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 火眼设备接口Dao
 *
 * @author zhangZaifa
 * @date 2020/4/3 15:37
 */
@Repository
public interface FireEyeDeviceDao {
    /**
     * 查询火眼设备
     *
     * @param fireEyeDevice FireEyeDevice
     * @return com.xjt.cloud.iot.core.entity.eye.FireEyeDevice
     * @author zhangZaiFa
     * @date 2020/4/3 17:30
     **/
    FireEyeDevice findFireEyeDevice(FireEyeDevice fireEyeDevice);

    /**
     * 查询火眼设备状态
     *
     * @param fireEyeDevice 火眼设备
     * @return FireEyeDevice
     * @author huanggc
     * @date 2021-03-22
     **/
    FireEyeDevice findFireEyeDeviceState(FireEyeDevice fireEyeDevice);

    /**
     * 删除火眼设备
     *
     * @param fireEyeDevice FireEyeDevice
     * @return int
     * @author zhangZaiFa
     * @date 2020/4/20 15:15
     **/
    int delFireEyeDevice(FireEyeDevice fireEyeDevice);

    /**
     * 修改火眼设备
     *
     * @param fireEyeDevice FireEyeDevice
     * @return int
     * @author zhangZaiFa
     * @date 2020/4/20 15:15
     **/
    int updFireEyeDevice(FireEyeDevice fireEyeDevice);

    /**
     * 查询火眼设备数量
     *
     * @param fireEyeDevice FireEyeDevice
     * @return java.lang.Integer
     * @author zhangZaiFa
     * @date 2020/4/20 15:15
     **/
    Integer findFireEyeDeviceListCount(FireEyeDevice fireEyeDevice);

    /**
     * 查询火眼设备列表
     *
     * @param fireEyeDevice FireEyeDevice
     * @return java.util.List<com.xjt.cloud.iot.core.entity.eye.FireEyeDevice>
     * @author zhangZaiFa
     * @date 2020/4/20 15:15
     **/
    List<FireEyeDevice> findFireEyeDeviceList(FireEyeDevice fireEyeDevice);

    /**
     * 保存火眼设备
     *
     * @param fireEyeDevice 火眼实体
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021-03-22
     **/
    int saveFireEyeDevice(FireEyeDevice fireEyeDevice);

    /**
     * 查询火眼离线的设备
     *
     * @param date 离线时间分钟
     * @return List<FireEyeDevice>
     * @author huanggc
     * @date 2021-03-23
     **/
    List<FireEyeDevice> fireEyeDeviceOffLineTask(@Param("date") Integer date);

    /**
     * 修改 火眼设备状态
     *
     * @param date 离线时间分钟
     * @author huanggc
     * @date 2021-03-23
     **/
    void updateFireEyeDeviceOffLineStatus(@Param("date") Integer date);

    /**
     * 修改 火眼设备
     *
     * @param fireEyeDevice FireEyeDevice
     * @author huanggc
     * @date 2021-03-23
     * @return int
     **/
    int updateFireEyeDevice(FireEyeDevice fireEyeDevice);

    /**
     * 查询火眼设备监测状态
     *
     * @param fireEyeDevice FireEyeDevice
     * @author huanggc
     * @date 2021-03-25
     * @return FireEyeEventReport
     **/
    FireEyeEventReport findFireEyeDeviceMonitoringStatus(FireEyeDevice fireEyeDevice);

    /**
     * 根据 火眼事件查询火眼设备
     *
     * @param fireEyeEvent 火眼事件实体
     * @author huanggc
     * @date 2021-04-02
     * @return FireEyeDevice
     **/
    FireEyeDevice findFireEyeDeviceByFireEyeEvent(FireEyeEvent fireEyeEvent);
}
