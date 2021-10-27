package com.xjt.cloud.iot.core.dao.iot.vesa;

import com.xjt.cloud.iot.core.entity.linkage.LinkageRequirement;
import com.xjt.cloud.iot.core.entity.vesa.VesaDevice;
import com.xjt.cloud.iot.core.entity.vesa.VesaDeviceReport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: yx
 * @Date: 2019/11/22 17:48
 * @Description:
 */
@Repository
public interface VesaDeviceDao {

    /**
     *
     * 功能描述:检查传感器id是否存在
     *
     * @param vesaDevice
     * @return: List<VesaDevice>
     * @auther: wangzhiwen
     * @date:
     */
    VesaDevice checkVesaDeviceSensorNo(VesaDevice vesaDevice);

    /**
     *
     * 功能描述:保存极早期设备信息
     *
     * @param vesaDevice
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    int saveVesaDevice(VesaDevice vesaDevice);

    /**
     *
     * 功能描述:修改极早期设备信息
     *
     * @param vesaDevice
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    int modifyVesaDevice(VesaDevice vesaDevice);

    /**
     *
     * 功能描述:查询极早期设备信息列表
     *
     * @param vesaDevice
     * @return: List<WaterDevice>
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    List<VesaDevice> findVesaDeviceList(VesaDevice vesaDevice);

    /**
     *
     * 功能描述:查询极早期设备信息列表APP
     *
     * @param vesaDevice
     * @return: List<VesaDevice>
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    List<VesaDevice> findVesaDeviceListApp(VesaDevice vesaDevice);

    /**
     *
     * 功能描述:查询极早期设备信息列表
     *
     * @param vesaDevice
     * @return: VesaDevice
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    VesaDevice findVesaDevice(VesaDevice vesaDevice);

    VesaDevice findVesaDeviceAndBuildingId(VesaDevice vesaDevice);

    /**
     *
     * 功能描述:查询极早期设备信息列表
     *
     * @param vesaDevice
     * @return: VesaDevice
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    VesaDevice findVesaDeviceById(VesaDevice vesaDevice);


    /**
     *
     * 功能描述:查询极早期设备信息列表
     *
     * @param vesaDevice
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    int findVesaDeviceListTotalCount(VesaDevice vesaDevice);

    /**
     *
     * 功能描述:查询极早期设备汇总报表
     *
     * @param vesaDevice
     * @return: VesaDeviceReport
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:59
     */
    VesaDeviceReport findVesaDeviceSummaryReport(VesaDevice vesaDevice);

    /**
     * 功能描述: 根据 触发条件 查询水设备
     *
     * @param linkageRequirementList 触发条件 实体list
     * @return: List<VesaDevice> 水设备实体list
     * @auther: huanggc
     * @date: 2019/10/23
     */
    List<VesaDevice> findByLinkageRequirement(List<LinkageRequirement> linkageRequirementList);

    /**
     *
     * 功能描述: 保存水设备离线事件信息
     *
     * @param date
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/11/21 10:57
     */
/*
    Integer saveVesaRecordOffLineEvent(@Param("date") Date date);
*/

    /**
     *
     * 功能描述:极早期设备离线
     * @param vesaDevice
     * @return: 此次设备离线的设备数
     * @auther: wangzhiwen
     * @date: 2019/11/19 14:44
     */
/*
    Integer vesaDeviceOffline(VesaDevice vesaDevice);
*/

    /**
     *
     * 功能描述:极早期设备在线
     * @param vesaDevice
     * @return: 此次设备在线的设备数
     * @auther: wangzhiwen
     * @date: 2019/11/19 14:44
     */
    Integer vesaDeviceOnline(VesaDevice vesaDevice);
}
