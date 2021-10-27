package com.xjt.cloud.admin.manage.service.service.iot;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.iot.WaterDevice;
import com.xjt.cloud.commons.utils.Data;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/6 10:54
 * @Description: 水压设备管理接口
 */
public interface WaterDeviceService {
    /**
     *
     * 功能描述:  查询水压设备列表
     *
     * @param ajaxPage
     * @param waterDevice
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.WaterDevice>
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:22
     */
    ScriptPage<WaterDevice> findWaterDeviceList(AjaxPage ajaxPage, WaterDevice waterDevice);

    /**
     *
     * 功能描述:修改水压设备
     *
     * @param waterDevice
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    Data modifyWaterDevice(WaterDevice waterDevice);

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
