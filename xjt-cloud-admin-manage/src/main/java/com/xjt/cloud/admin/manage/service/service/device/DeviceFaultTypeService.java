package com.xjt.cloud.admin.manage.service.service.device;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.device.DeviceFaultType;
import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;

/**
 * 设备故障类型管理Service
 *
 * @author huanggc
 * @date 2020/11/19
 */
public interface DeviceFaultTypeService extends BaseService {

    /**
     * 功能描述:查询 故障类型列表
     *
     * @param ajaxPage AjaxPage
     * @param deviceFaultType DeviceFaultType
     * @return ScriptPage<DeviceFaultType>
     * @author huanggc
     * @date 2020/11/19
     */
    ScriptPage<DeviceFaultType> findDeviceFaultTypeList(AjaxPage ajaxPage, DeviceFaultType deviceFaultType);

    /**
     * 更新 故障类型
     *
     * @param deviceFaultType DeviceFaultType
     * @return ScriptPage<DeviceFaultType>
     * @author huanggc
     * @date 2020/11/19
     */
    Data updateDeviceFaultType(DeviceFaultType deviceFaultType);

    /**
     * 保存 设备故障类型
     *
     * @param deviceFaultType DeviceFaultType
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/11/19
     */
    Data saveDeviceFaultType(DeviceFaultType deviceFaultType);
}
