package com.xjt.cloud.task.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.dao.device.DeviceCheckPointDao;
import com.xjt.cloud.task.core.dao.device.TaskDeviceTypeDao;
import com.xjt.cloud.task.core.entity.task.TaskDevice;
import com.xjt.cloud.task.core.entity.TaskDeviceCheckPoint;
import com.xjt.cloud.task.core.entity.TaskDeviceType;
import com.xjt.cloud.task.core.service.service.device.DeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName DeviceTypeServiceImpl
 * @Author dwt
 * @Date 2019-08-09 10:06
 * @Description 设备类型Service接口实现类
 * @Version 1.0
 */
@Service
public class TaskDeviceTypeServiceImpl extends AbstractService implements DeviceTypeService {

    @Autowired
    private TaskDeviceTypeDao taskDeviceTypeDao;
    @Autowired
    private DeviceCheckPointDao deviceCheckPointDao;

    /**
     * @Author: dwt
     * @Date: 2019-08-08 14:24
     * @Param: 任务设备筛选实体
     * @Return: 设备系统列表
     * @Description: 按系统添加设备：根据项目id查询设备系统列表
     */
    @Override
    public Data findDeviceSysByProjectId(String json) {
        TaskDevice taskDevice = JSONObject.parseObject(json, TaskDevice.class);
        Integer type = taskDevice.getType();
        List<Long> checkPointIds = taskDevice.getCheckPointIds();
        List<TaskDeviceType> list;
        if (checkPointIds != null && checkPointIds.size() > 0) {
            list = taskDeviceTypeDao.findDeviceSysByProjectIdSelOrNotSel(taskDevice);
        } else {
            list = taskDeviceTypeDao.findDeviceSysByProjectId(taskDevice);
        }
        List<TaskDeviceType> typeList;
        List<TaskDeviceCheckPoint> pointList;
        boolean b = type == null || type == 0;
        if (b && list != null && list.size() > 0){
            for (TaskDeviceType d : list) {
                d.setType(0);
                taskDevice.setId(d.getId());
                if (checkPointIds != null && checkPointIds.size() > 0) {
                    typeList = taskDeviceTypeDao.findDeviceTypeBySysIdSelOrNotSel(taskDevice);
                } else {
                    typeList = taskDeviceTypeDao.findDeviceTypeBySysId(taskDevice);
                }
                if (typeList != null && typeList.size() > 0) {
                    for (TaskDeviceType dt : typeList) {
                        taskDevice.setId(dt.getId());
                        pointList = deviceCheckPointDao.findCheckPointByDeviceTypeId(taskDevice);
                        dt.setCheckPointList(pointList);

                    }
                }
                d.setTaskDeviceTypeList(typeList);
            }
        } else {
            Integer num;
            if (CollectionUtils.isNotEmpty(list)) {
                for (TaskDeviceType d : list) {
                    d.setType(0);
                    taskDevice.setId(d.getId());
                    if (checkPointIds != null && checkPointIds.size() > 0) {
                        typeList = taskDeviceTypeDao.findDeviceTypeBySysIdSelOrNotSel(taskDevice);
                    } else {
                        typeList = taskDeviceTypeDao.findDeviceTypeBySysId(taskDevice);
                    }
                    if (typeList != null && typeList.size() > 0) {
                        for (TaskDeviceType dt : typeList) {
                            taskDevice.setId(dt.getId());
                            num = deviceCheckPointDao.findCheckPointCountByDeviceTypeId(taskDevice);
                            dt.setTotalCount(num);
                        }
                    }
                    d.setTaskDeviceTypeList(typeList);
                }
            }
        }
        return asseData(list);
    }
}
