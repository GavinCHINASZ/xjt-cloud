package com.xjt.cloud.iot.core.dao.iot.smoke;

import com.xjt.cloud.iot.core.entity.smoke.SmokeDevice;
import com.xjt.cloud.iot.core.entity.smoke.SmokeDeviceReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 烟感设备 dao
 *
 * @author huanggc
 * @date 2020/07/15
 */
@Repository
public interface SmokeDeviceDao {
    /**
     * 功能描述: 查询 烟感设备
     *
     * @param smokeDevice 烟感设备
     * @author huanggc
     * @date 2020/07/25
     * @return List<SmokeDevice> 烟感设备list
     */
    List<SmokeDevice> findSmokeDeviceList(SmokeDevice smokeDevice);

    /**
     * 功能描述: 查询 烟感设备 数量
     *
     * @param smokeDevice 烟感设备
     * @author huanggc
     * @date 2020/07/25
     * @return Integer
     */
    Integer findSmokeDeviceListTotalCount(SmokeDevice smokeDevice);

    /**
     * 功能描述: 查询 烟感设备
     *
     * @param smokeDevice 烟感设备
     * @author huanggc
     * @date 2020/07/16
     * @return SmokeDevice
     */
    SmokeDevice findSmokeDevice(SmokeDevice smokeDevice);

    /**
     * 功能描述: 保存 烟感设备
     *
     * @param smokeDevice 烟感设备
     * @author huanggc
     * @date 2020/07/16
     * @return Integer
     */
    int saveSmokeDevice(SmokeDevice smokeDevice);

    /**
     * 功能描述: 批量保存 烟感设备
     *
     * @param smokeDeviceList 烟感设备list
     * @author huanggc
     * @date 2020/08/17
     * @return Integer
     */
    Integer saveSmokeDeviceList(List<SmokeDevice> smokeDeviceList);

    /**
     * 功能描述: 更新 烟感设备
     *
     * @param smokeDevice 烟感设备
     * @author huanggc
     * @date 2020/07/16
     * @return Integer
     */
    Integer updateSmokeDevice(SmokeDevice smokeDevice);

    /**
     * 功能描述: 烟感设备汇总报表 饼图
     *
     * @param smokeDevice 烟感设备
     * @author huanggc
     * @date 2020/07/27
     * @return SmokeDeviceReport
     */
    SmokeDeviceReport findSomkeDeviceSummaryReport(SmokeDevice smokeDevice);

    /**
     * 功能描述: 删除烟感设备
     *
     * @param smokeDeviceIdList 烟感设备ID集合
     * @author huanggc
     * @date 2020/07/31
     * @return Integer
     */
    Integer deletedSmokeDevice(List<Long> smokeDeviceIdList);

    /**
     * 功能描述: 保存 烟感设备离线事件信息
     *
     * @param date 时间
     * @author huanggc
     * @date 2020/08/04
     */
    void saveSmokeOffLineEvent(@Param("date") Date date);

    /**
     * 功能描述: 修改设备离线状态
     *
     * @param smokeDevice 烟感设备
     * @author huanggc
     * @date 2020/08/04
     * @return Integer
     */
    int smokeOffLineTask(SmokeDevice smokeDevice);

    /**
     * 功能描述: 修改设备离线状态
     *
     * @param date 时间
     * @author huanggc
     * @date 2020/08/04
     */
    void smokeOffLineModifyDeviceIotStatus(Date date);

    /**
     * 功能描述: 保存 烟感设备离线事件信息
     *
     * @param smokeDevice 烟感设备
     * @author huanggc
     * @date 2020/09/02
     * @return Long[]
     */
    Long[] findSmokeDeviceId(SmokeDevice smokeDevice);
}
