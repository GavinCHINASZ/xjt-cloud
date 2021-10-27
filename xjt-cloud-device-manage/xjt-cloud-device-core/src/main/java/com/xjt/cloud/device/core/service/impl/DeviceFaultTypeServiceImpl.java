package com.xjt.cloud.device.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.device.core.dao.device.DeviceFaultTypeDao;
import com.xjt.cloud.device.core.entity.DeviceFaultType;
import com.xjt.cloud.device.core.service.service.DeviceFaultTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备故障类型ServiceImpl
 *
 * @author huanggc
 * @date 2020/11/27
 */
@Service
public class DeviceFaultTypeServiceImpl extends AbstractService implements DeviceFaultTypeService {
    @Autowired
    private DeviceFaultTypeDao deviceFaultTypeDao;

    /**
     * 功能描述:查询设备故障类型信息列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/11/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findDeviceFaultTypeList(String json) {
        DeviceFaultType deviceFaultType = JSONObject.parseObject(json, DeviceFaultType.class);
        if (deviceFaultType.getDeviceType() == null){
            deviceFaultType.setDeviceType(1);
        }
        if (deviceFaultType.getOrderCols() == null || deviceFaultType.getOrderCols().length == 0) {
            String[] orderCols = {"sort"};
            deviceFaultType.setOrderCols(orderCols);
        }
        List<DeviceFaultType> deviceFaultTypeList = deviceFaultTypeDao.findDeviceFaultTypeList(deviceFaultType);
        Integer totalCount = deviceFaultTypeDao.findDeviceFaultTypeListTotalCount(deviceFaultType);
        return asseData(totalCount, deviceFaultTypeList);
    }

    /**
     * 功能描述:查询 异常类型(告警类型)
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/10
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findFireFaultTypeList(String json) {
        DeviceFaultType deviceFaultType = JSONObject.parseObject(json, DeviceFaultType.class);
        List<DeviceFaultType> deviceFaultTypeList = deviceFaultTypeDao.findFireFaultTypeList(deviceFaultType);
        return asseData(deviceFaultTypeList);
    }
}
