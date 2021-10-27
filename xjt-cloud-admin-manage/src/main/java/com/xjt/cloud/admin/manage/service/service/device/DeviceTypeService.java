package com.xjt.cloud.admin.manage.service.service.device;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.device.CheckPoint;
import com.xjt.cloud.admin.manage.entity.device.Device;
import com.xjt.cloud.admin.manage.entity.device.DeviceType;
import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/3 11:14
 * @Description: 设备类型管理接口
 */
public interface DeviceTypeService extends BaseService {

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
    ScriptPage<DeviceType> findDeviceSysList(AjaxPage ajaxPage, DeviceType deviceType);

    /**
     *
     * 功能描述:新增设备系统
     *
     * @param deviceType
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    Data saveDeviceSys(DeviceType deviceType);

    /**
     *
     * 功能描述:修改设备系统
     *
     * @param deviceType
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    Data modifyDeviceSys(DeviceType deviceType);

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
    ScriptPage<DeviceType> findDeviceTypeList(AjaxPage ajaxPage, DeviceType deviceType);

    /**
     *
     * 功能描述:新增设备系统类型
     *
     * @param deviceType
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    Data saveDeviceType(DeviceType deviceType);

    /**
     *
     * 功能描述:修改设备系统类型
     *
     * @param deviceType
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    Data modifyDeviceType(DeviceType deviceType);

    /**
     * @Description 查询项目设备类型列表
     *
     * @param deviceType
     * @author wangzhiwen
     * @date 2020/8/27 15:59
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.device.DeviceType>
     */
    List<DeviceType> findProjectDeviceTypeList(DeviceType deviceType);

    /**
     * @Description 查询项目巡检点列表
     *
     * @param checkPoint
     * @author wangzhiwen
     * @date 2020/8/27 15:59
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.device.CheckPoint>
     */
    List<CheckPoint> findProjectCheckPointList(CheckPoint checkPoint);

    /**
     * @Description 查询项目设备列表
     *
     * @param device
     * @author wangzhiwen
     * @date 2020/8/27 15:59
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.device.Device>
     */
    List<Device> findProjectDeviceList(Device device);
}
