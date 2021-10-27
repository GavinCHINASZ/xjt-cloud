package com.xjt.cloud.admin.manage.dao.iot;

import com.xjt.cloud.admin.manage.entity.iot.WaterDevice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/6 10:55
 * @Description: 水压设备管理dao
 */
@Repository
public interface WaterDeviceDao {
    /**
     *
     * 功能描述:  查询水压设备列表
     *
     * @param waterDevice
     * @return: List<WaterDevice>
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:22
     */
    List<WaterDevice> findWaterDeviceList(WaterDevice waterDevice);

    /**
     *
     * 功能描述:  查询水压设备列表
     *
     * @param waterDevice
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:22
     */
    int findWaterDeviceListTotalCount(WaterDevice waterDevice);

    /**
     *
     * 功能描述:修改水压设备
     *
     * @param waterDevice
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    int modifyWaterDevice(WaterDevice waterDevice);

    /**
     *
     * 功能描述:  查询物联设备列表
     *
     * @param waterDevice
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.iot.WaterDevice>
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:22
     */
    List<WaterDevice> findSensorNoList(WaterDevice waterDevice);
}
