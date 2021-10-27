package com.xjt.cloud.admin.manage.service.impl.device;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.device.DeviceFaultTypeDao;
import com.xjt.cloud.admin.manage.entity.device.DeviceFaultType;
import com.xjt.cloud.admin.manage.service.service.device.DeviceFaultTypeService;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 设备故障类型管理ServiceImpl
 *
 * @author huanggc
 * @date 2020/11/19
 */
@Service
public class DeviceFaultTypeServiceImpl extends AbstractAdminService implements DeviceFaultTypeService {
    @Autowired
    private DeviceFaultTypeDao deviceFaultTypeDao;

    /**
     * 功能描述:查询 故障类型列表
     *
     * @param ajaxPage AjaxPage
     * @param deviceFaultType DeviceFaultType
     * @return ScriptPage<DeviceFaultType>
     * @author huanggc
     * @date 2020/11/19
     */
    @Override
    public ScriptPage<DeviceFaultType> findDeviceFaultTypeList(AjaxPage ajaxPage, DeviceFaultType deviceFaultType) {
        deviceFaultType.setDeleted(false);

        if (null == deviceFaultType.getOrderCols()){
            String[] orderCols = {"sort"};
            deviceFaultType.setOrderCols(orderCols);
            deviceFaultType.setOrderDesc(false);
        }

        deviceFaultType = asseFindObj(deviceFaultType, ajaxPage);
        return asseScriptPage(deviceFaultTypeDao.findDeviceFaultTypeList(deviceFaultType), deviceFaultTypeDao.findDeviceFaultTypeListTotalCount(deviceFaultType));
    }

    /**
     * 更新 故障类型
     *
     * @param deviceFaultType DeviceFaultType
     * @return ScriptPage<DeviceFaultType>
     * @author huanggc
     * @date 2020/11/19
     */
    @Override
    public Data updateDeviceFaultType(DeviceFaultType deviceFaultType) {
        int num = deviceFaultTypeDao.updateDeviceFaultType(deviceFaultType);
        return asseData(num);
    }

    /**
     * 保存 设备故障类型
     *
     * @param deviceFaultType DeviceFaultType
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/11/19
     */
    @Override
    public Data saveDeviceFaultType(DeviceFaultType deviceFaultType) {
        int num = deviceFaultTypeDao.saveDeviceFaultType(deviceFaultType);
        return asseData(num);
    }

}
