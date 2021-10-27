package com.xjt.cloud.iot.core.dao.device;

import com.xjt.cloud.iot.core.entity.device.Device;
import com.xjt.cloud.iot.core.entity.fire.FireAlarmDevice;
import com.xjt.cloud.iot.core.entity.smoke.PointDevice;
import com.xjt.cloud.iot.core.entity.smoke.SmokeDevice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:32
 * @Description:设备管理Dao
 */
@Repository
public interface DeviceDao {
    /**
     * 功能描述:查询设备名称
     *
     * @param device
     * @auther: huanggc
     * @date: 2019/09/26
     * @return: List<String>
     */
    List<String> findDeviceName(Device device);

    /**
     * 功能描述:查询设备ID(二维码编号)
     *
     * @param device
     * @auther: huanggc
     * @date: 2019/09/26
     * @return: List<String>
     */
    List<String> findQrNo(Device device);

    /**
     *
     * 功能描述: 修改设备的物联设备id
     *
     * @param device
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/12/5 16:09
     */
    int modifyDevice(Device device);

    /**
     *
     * 功能描述: 修改设备的物联设备状态
     *
     * @param device
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/12/5 16:09
     */
    int modifyDeviceIotStatus(Device device);

    /**
     *
     * 功能描述: 修改设备的物联设备id
     *
     * @param device
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/12/5 16:09
     */
    int modifyDeviceClearIot(Device device);
    /**
     *@Author: dwt
     *@Date: 2019-12-13 14:10
     *@Param:
     *@Return:
     *@Description 批量修改设备的物联网设备id
     */
    void modifyFireAlarmDeviceClearIot(List<FireAlarmDevice> list);

    /**
     *
     * 功能描述: 查询巡检点id
     *
     * @param pointQrNo
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/1 14:38
     */
    Long findCheckPointId(@Param("pointQrNo")String pointQrNo);

    /**
     * 功能描述: 根据 巡检点ID 查询位置
     *
     * @param checkPointId
     * @auther huanggc
     * @date 2020/08/13
     * @return String
     */
    String findPointLocationById(Long checkPointId);

    /**
     *
     * 功能描述: 以类型名称数组查询设备类型列表
     *
     * @param sql
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/8/8 16:49
     */
    List<PointDevice> findDeviceListBySql(String sql);

    Integer modifyDeviceBySmokeDevice(List<SmokeDevice> smokeDeviceList);
}
