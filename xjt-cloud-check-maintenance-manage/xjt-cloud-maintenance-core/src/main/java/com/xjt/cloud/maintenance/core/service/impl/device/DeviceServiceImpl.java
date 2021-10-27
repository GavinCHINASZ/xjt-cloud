package com.xjt.cloud.maintenance.core.service.impl.device;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.ExcelUtils;
import com.xjt.cloud.maintenance.core.dao.device.DeviceDao;
import com.xjt.cloud.maintenance.core.dao.device.DeviceSystemDao;
import com.xjt.cloud.maintenance.core.entity.device.Device;
import com.xjt.cloud.maintenance.core.entity.device.DeviceSystem;
import com.xjt.cloud.maintenance.core.service.service.device.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DeviceServiceImpl
 *
 * @author dwt
 * @date 2020-04-10 15:59
 */
@Service
public class DeviceServiceImpl extends AbstractService implements DeviceService {
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private DeviceSystemDao deviceSystemDao;

    /**
     * 查询设备列表
     *
     * @param json Device
     * @return Data
     * @author dwt
     * @date 2020-04-10 17:12
     */
    @Override
    public Data findDeviceList(String json) {
        Device device = JSONObject.parseObject(json, Device.class);
        if (device.getProjectInfoId() == null || device.getProjectInfoId() == 0) {
            return null;
        }

        Integer totalCount = device.getTotalCount();
        Integer pageSize = device.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize){
            totalCount = deviceDao.findDeviceListTotalCount(device);
        }

        List<Device> list = deviceDao.findDeviceList(device);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("deviceList", list);
        return asseData(map);
    }

    /**
     * 查询设备信息
     *
     * @param json Device
     * @return Device
     * @author dwt
     * @date 2020-04-10 17:13
     */
    @Override
    public Data findDevice(String json) {
        Device device = JSONObject.parseObject(json, Device.class);
        Device d = deviceDao.findDevice(device);
        return asseData(d);
    }

    /**
     * 保存设备
     *
     * @param json Device
     * @return Data
     * @author dwt
     * @date 2020-04-10 17:15
     */
    @Override
    public Data saveDevice(String json) {
        Device device = JSONObject.parseObject(json, Device.class);
        Integer a;
        if (device.getId() == null || device.getId() == 0) {
            if (device.getCertificate() == null) {
                device.setCertificate(0);
            }
            a = deviceDao.saveDevice(device);
        } else {
            a = deviceDao.modifyDevice(device);
        }
        if (a <= 0) {
            Data.isFail();
        }
        return Data.isSuccess();
    }

    /**
     * 批量保存设备
     *
     * @param json Device
     * @return Data
     * @author dwt
     * @date 2020-04-10 17:16
     */
    @Override
    public Data saveDevices(String json) {
        List<Device> list = (List<Device>) JSONObject.parseObject(json, Device.class);
        Integer a = deviceDao.saveDevices(list);
        if (a <= 0) {
            Data.isFail();
        }
        return Data.isSuccess();
    }

    /**
     * 查询项目下所有系统设备
     *
     * @param projectInfoId projectInfoId
     * @return List<DeviceSystem>
     * @author dwt
     * @date 2020-04-11 9:17
     */
    @Override
    public List<DeviceSystem> findAllDeviceListByProjectInfoId(Long projectInfoId) {
        Device device = new Device();
        device.setProjectInfoId(projectInfoId);
        List<Device> list = deviceDao.findAllDeviceListByProjectInfoId(device);
        List<DeviceSystem> deviceSystems = new ArrayList<>();
        List<Device> dl;
        if (list != null && list.size() > 0) {
            DeviceSystem ds = new DeviceSystem();
            dl = new ArrayList<>();
            for (Device d : list) {
                if (ds.getId() != null && ds.getId() != 0) {
                    if (d.getDeviceTypeId() == ds.getId()) {
                        dl.add(d);
                    } else if (d.getDeviceTypeId() != ds.getId()) {
                        ds.setDeviceList(dl);
                        deviceSystems.add(ds);
                        dl = new ArrayList<>();
                        ds = new DeviceSystem();
                        ds.setId(d.getDeviceTypeId());
                        ds.setDeviceSysName(d.getDeviceSysName());
                        dl.add(d);
                    }
                } else {
                    ds.setId(d.getDeviceTypeId());
                    ds.setDeviceSysName(d.getDeviceSysName());
                    dl.add(d);
                }
                if (list.get(list.size() - 1).getId() == d.getId()) {
                    ds.setDeviceList(dl);
                    deviceSystems.add(ds);
                }
            }
        }
        return deviceSystems;
    }

    /**
     * 导入设备列表
     *
     * @param json 参数
     * @param file 文件
     * @return Data
     * @author dwt
     * @date 2020-04-11 10:17
     */
    @Override
    public Data uploadDeviceList(String json, MultipartFile file) {
        Device device = JSONObject.parseObject(json, Device.class);
        //解析表格，设备系统名称＝表格中的设备名称
        String[] keys = {"rowNum", "deviceName", "num", "productModel", "document", "certificateStr", "factoryDate", "remark"};
        //解析表格
        List<Device> list = ExcelUtils.readyExcel(file, 1, 0, 0, keys, Device.class);
        List<Device> dList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            Device ds;
            for (Device d : list) {
                if (d.getDeviceName() != null) {
                    ds = deviceSystemDao.findDeviceSystemByDeviceName(d);
                    if (ds != null) {
                        d.setDeviceId(ds.getDeviceId());
                        d.setDeviceTypeId(ds.getDeviceTypeId());
                        d.setDeviceSysName(ds.getDeviceSysName());
                        d.setStatus(1);
                        if ("有".equals(d.getCertificateStr())) {
                            d.setCertificate(1);
                        } else {
                            d.setCertificate(0);
                        }
                        d.setProjectInfoId(device.getProjectInfoId());
                        dList.add(d);
                    }
                }
            }
        }

        if (dList != null && dList.size() > 0) {
            Integer a = deviceDao.saveDevices(dList);
            if (a > 0) {
                return Data.isSuccess();
            }
        }
        return Data.isFail();
    }

    /**
     * 逻辑删除
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-04-11 17:55
     */
    @Override
    public Data deleteDevice(String json) {
        Device device = JSONObject.parseObject(json, Device.class);
        Integer a = deviceDao.modifyDevice(device);
        if (a > 0) {
            return Data.isSuccess();
        }
        return Data.isFail();
    }

}
