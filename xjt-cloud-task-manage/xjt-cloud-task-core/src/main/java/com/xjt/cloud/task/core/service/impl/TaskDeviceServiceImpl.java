package com.xjt.cloud.task.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.dao.device.TaskDeviceDao;
import com.xjt.cloud.task.core.dao.task.CheckRecordDao;
import com.xjt.cloud.task.core.entity.task.AppTaskDevice;
import com.xjt.cloud.task.core.entity.task.Task;
import com.xjt.cloud.task.core.entity.TaskDeviceEntity;
import com.xjt.cloud.task.core.service.service.device.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName DeviceServiceImpl
 * @Author dwt
 * @Date 2019-08-09 11:07
 * @Description 设备Service接口实现类
 * @Version 1.0
 */
@Service
public class TaskDeviceServiceImpl extends AbstractService implements DeviceService {

    @Autowired
    private TaskDeviceDao taskDeviceDao;
    @Autowired
    private CheckRecordDao checkRecordDao;

    /**
     * @Author: dwt
     * @Date: 2019-08-07 11:23
     * @Param: java.lang.Long
     * @Return: 设备列表
     * @Description 根据巡更点查询设备列表
     */
    @Override
    public Data findDeviceListByCheckPointId(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        List<TaskDeviceEntity> list = taskDeviceDao.findDeviceListByCheckPointId(task);
        return asseData(list);
    }

    /**
     * @Author: dwt
     * @Date: 2019-08-15 14:54
     * @Param: java.lang.Long
     * @Return: Data
     * @Description 根据任务id查询设备列表
     */
    @Override
    public Data findAppTaskDeviceListByTaskId(String json) {
        AppTaskDevice appTaskDevice = JSONObject.parseObject(json, AppTaskDevice.class);
        List<AppTaskDevice> appTaskDeviceList = taskDeviceDao.findAppTaskDeviceListByTaskId(appTaskDevice);
        long checkRecordId;
        if (appTaskDeviceList != null && appTaskDeviceList.size() > 0) {
            for (AppTaskDevice a : appTaskDeviceList) {
                checkRecordId = checkRecordDao.findCheckRecordIdByDeviceId(a.getDeviceId());
                a.setCheckRecordId(checkRecordId);
            }
        }
        return asseData(appTaskDeviceList);
    }
}
