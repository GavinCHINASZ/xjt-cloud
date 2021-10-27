package com.xjt.cloud.admin.manage.service.impl.device;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.device.DeviceTypeDao;
import com.xjt.cloud.admin.manage.entity.device.CheckPoint;
import com.xjt.cloud.admin.manage.entity.device.Device;
import com.xjt.cloud.admin.manage.entity.device.DeviceType;
import com.xjt.cloud.admin.manage.service.service.device.DeviceTypeService;
import com.xjt.cloud.commons.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/3 11:14
 * @Description: 设备类型管理接口实现类
 */
@Service
public class DeviceTypeServiceImpl extends AbstractAdminService implements DeviceTypeService {
    @Autowired
    private DeviceTypeDao deviceTypeDao;

    /**
     *
     * 功能描述:  查询设备系统列表
     *
     * @param ajaxPage
     * @param deviceType
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.device.DeviceType>
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:22
     */
    @Override
    public ScriptPage<DeviceType> findDeviceSysList(AjaxPage ajaxPage, DeviceType deviceType){
        deviceType.setType(1);
        deviceType = asseFindObj(deviceType, ajaxPage);
        return asseScriptPage(deviceTypeDao.findDeviceSysList(deviceType), deviceTypeDao.findDeviceSysListTotalCount(deviceType));
    }

    /**
     *
     * 功能描述:新增设备系统
     *
     * @param deviceType
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    @Override
    public Data saveDeviceSys(DeviceType deviceType){
        deviceType.setPinYinInitials(PinYinUtils.getFirstSpell(deviceType.getDeviceSysName()).toUpperCase());
        int num = deviceTypeDao.saveDeviceSys(deviceType);
        deviceSysCacheInit();
        return asseData(num);
    }

    /**
     *
     * 功能描述:修改设备系统
     *
     * @param deviceType
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    @Override
    public Data modifyDeviceSys(DeviceType deviceType){
        if (StringUtils.isNotEmpty(deviceType.getDeviceSysName())){
            deviceType.setPinYinInitials(PinYinUtils.getFirstSpell(deviceType.getDeviceSysName()).toUpperCase());
        }
        int num = deviceTypeDao.modifyDeviceSys(deviceType);
        deviceSysCacheInit();
        return asseData(num);
    }

    ////////////////////////////////////////　设备类型管理　///////////////////////////////////
    /**
     *
     * 功能描述:  查询设备系统类型列表
     *
     * @param ajaxPage
     * @param deviceType
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.device.DeviceType>
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:22
     */
    @Override
    public ScriptPage<DeviceType> findDeviceTypeList(AjaxPage ajaxPage, DeviceType deviceType){
        deviceType.setType(9);
        deviceType = asseFindObj(deviceType, ajaxPage);
        return asseScriptPage(deviceTypeDao.findDeviceSysList(deviceType), deviceTypeDao.findDeviceSysListTotalCount(deviceType));
    }

    /**
     *
     * 功能描述:新增设备系统类型
     *
     * @param deviceType
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    @Override
    public Data saveDeviceType(DeviceType deviceType){
        deviceType.setPinYinInitials(PinYinUtils.getFirstSpell(deviceType.getDeviceSysName()).toUpperCase());
        deviceType.setType(9);
        int num = deviceTypeDao.saveDeviceSys(deviceType);
        deviceSysCacheInit();
        return asseData(num);
    }

    /**
     *
     * 功能描述:修改设备系统类型
     *
     * @param deviceType
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    @Override
    public Data modifyDeviceType(DeviceType deviceType){
        if (StringUtils.isNotEmpty(deviceType.getDeviceSysName())){
            deviceType.setPinYinInitials(PinYinUtils.getFirstSpell(deviceType.getDeviceSysName()).toUpperCase());
        }
        int num = deviceTypeDao.modifyDeviceSys(deviceType);
        deviceSysCacheInit();
        return asseData(num);
    }

    /**
     *
     * 功能描述: 设备系统
     *
     * @param
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/12/12 11:12
     */
    private void deviceSysCacheInit(){
        try {
            HttpUtils.httpGet(ConstantsClient.DEVICE_SYS_CACHE);
        }catch (Exception ex){
            SysLog.error("设备系统!");
            SysLog.error(ex);
        }
    }

    /**
     * @Description 查询项目设备类型列表
     *
     * @param deviceType
     * @author wangzhiwen
     * @date 2020/8/27 15:59
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.device.DeviceType>
     */
    @Override
    public List<DeviceType> findProjectDeviceTypeList(DeviceType deviceType){
        return deviceTypeDao.findProjectDeviceTypeList(deviceType);
    }

    /**
     * @Description 查询项目巡检点列表
     *
     * @param checkPoint
     * @author wangzhiwen
     * @date 2020/8/27 15:59
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.device.CheckPoint>
     */
    @Override
    public List<CheckPoint> findProjectCheckPointList(CheckPoint checkPoint){
        return deviceTypeDao.findProjectCheckPointList(checkPoint);
    }

    /**
     * @Description 查询项目设备列表
     *
     * @param device
     * @author wangzhiwen
     * @date 2020/8/27 15:59
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.device.Device>
     */
    @Override
    public List<Device> findProjectDeviceList(Device device){
        return deviceTypeDao.findProjectDeviceList(device);
    }
}
