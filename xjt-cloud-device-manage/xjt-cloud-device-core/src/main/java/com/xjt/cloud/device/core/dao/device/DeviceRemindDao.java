package com.xjt.cloud.device.core.dao.device;

import com.xjt.cloud.device.core.entity.Device;
import com.xjt.cloud.device.core.entity.DeviceRemind;
import com.xjt.cloud.device.core.entity.ReportDeviceRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 设备近期提醒Dao
 *
 * @Auther huanggc
 * @Date 2020-03-27
 */
@Repository
public interface DeviceRemindDao {
    /**
     * 功能描述:查询 设备过期提醒
     *
     * @param deviceRemind
     * @auther: huanggc
     * @date: 2020-03-27
     * @return: DeviceRemind
     */
    DeviceRemind findDeviceRemind(DeviceRemind deviceRemind);

    /**
     * 功能描述:更新 设备过期提醒
     *
     * @param deviceRemind
     * @auther: huanggc
     * @date: 2020-03-27
     * @return Integer
     */
    Integer updateDeviceRemind(DeviceRemind deviceRemind);

    /**
     * 功能描述:保存 设备过期提醒
     *
     * @param deviceRemind
     * @auther: huanggc
     * @date: 2020-03-27
     * @return Integer
     */
    Integer saveDeviceRemind(DeviceRemind deviceRemind);

    /**
     * 功能描述: 查询 设备过期提醒
     *
     * @auther: huanggc
     * @date: 2020-03-30
     * @return List<DeviceRemind>
     */
    List<DeviceRemind> findDeviceRemindTaskList();
}
