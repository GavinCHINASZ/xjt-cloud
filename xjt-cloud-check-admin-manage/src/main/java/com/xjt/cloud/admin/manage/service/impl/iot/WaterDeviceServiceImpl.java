package com.xjt.cloud.admin.manage.service.impl.iot;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.iot.WaterDeviceDao;
import com.xjt.cloud.admin.manage.entity.iot.WaterDevice;
import com.xjt.cloud.admin.manage.service.service.iot.WaterDeviceService;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/6 10:54
 * @Description: 水压设备管理接口实现类
 */
@Service
public class WaterDeviceServiceImpl extends AbstractAdminService implements WaterDeviceService {
    @Autowired
    private WaterDeviceDao waterDeviceDao;

    /**
     * 功能描述:  查询水压设备列表
     *
     * @param ajaxPage
     * @param waterDevice
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.WaterDevice>
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:22
     */
    @Override
    public ScriptPage<WaterDevice> findWaterDeviceList(AjaxPage ajaxPage, WaterDevice waterDevice) {
        waterDevice = asseFindObj(waterDevice, ajaxPage);
        return asseScriptPage(waterDeviceDao.findWaterDeviceList(waterDevice), waterDeviceDao.findWaterDeviceListTotalCount(waterDevice));
    }

    /**
     * 功能描述:修改水压设备
     *
     * @param waterDevice
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    @Override
    public Data modifyWaterDevice(WaterDevice waterDevice) {
        return asseData(waterDeviceDao.modifyWaterDevice(waterDevice));
    }

    /**
     * 功能描述:  查询物联设备列表
     *
     * @param waterDevice
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.iot.WaterDevice>
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:22
     */
    @Override
    public List<WaterDevice> findSensorNoList(WaterDevice waterDevice) {
        String[] deviceIdAndDeviceType = waterDevice.getDeviceIdAndDeviceType().split("_");
        if (deviceIdAndDeviceType == null || deviceIdAndDeviceType.length < 2) {
            return null;
        }
        waterDevice.setDeviceId(Long.parseLong(deviceIdAndDeviceType[0]));
        int deviceType = Integer.parseInt(deviceIdAndDeviceType[1]);
        String tableName;
        if (deviceType == 5) {//极早期预警
            tableName = "v_vesa_device";
        } else if (deviceType == 6) {//火眼报警
            tableName = "e_fire_eye_event";
        } else if (deviceType == 4) {//火警主机
            tableName = "f_fire_alarm_device";
        } else if (deviceType == 20) {//声光报警装置
            tableName = "l_linkage_device";
        } else if (deviceType == 19) {//烟感设备
            tableName = "s_smoke_device";
        } else {//水压，水浸，水厢，消火栓，
            tableName = "w_water_device";
        }
        waterDevice.setTableName(tableName);
        return waterDeviceDao.findSensorNoList(waterDevice);
    }
}
