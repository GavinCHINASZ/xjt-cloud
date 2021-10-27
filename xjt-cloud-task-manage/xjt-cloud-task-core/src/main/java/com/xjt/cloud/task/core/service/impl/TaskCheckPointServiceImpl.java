package com.xjt.cloud.task.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.dao.device.DeviceCheckPointDao;
import com.xjt.cloud.task.core.dao.device.TaskDeviceTypeDao;
import com.xjt.cloud.task.core.dao.task.TaskCheckPointDao;
import com.xjt.cloud.task.core.dao.task.TaskDao;
import com.xjt.cloud.task.core.dao.task.TaskModelDeviceDao;
import com.xjt.cloud.task.core.entity.*;
import com.xjt.cloud.task.core.entity.check.CheckPoint;
import com.xjt.cloud.task.core.entity.device.DeviceCheckPoint;
import com.xjt.cloud.task.core.entity.project.TaskFloor;
import com.xjt.cloud.task.core.entity.task.Task;
import com.xjt.cloud.task.core.entity.task.TaskDevice;
import com.xjt.cloud.task.core.entity.task.TaskModelDevice;
import com.xjt.cloud.task.core.service.service.CheckPointService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.GZIPOutputStream;

/**
 * 巡更点Service接口实现类
 *
 * @author dwt
 * @date 2019-08-09 10:16
 */
@Service
public class TaskCheckPointServiceImpl extends AbstractService implements CheckPointService {
    @Autowired
    private DeviceCheckPointDao checkPointTaskDao;
    @Autowired
    private TaskCheckPointDao taskCheckPointDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private TaskDeviceTypeDao taskDeviceTypeDao;
    @Autowired
    private DeviceCheckPointDao deviceCheckPointDao;
    @Autowired
    private TaskModelDeviceDao taskModelDeviceDao;

    /**
     * 根据巡更点添加设备：根据项目id查询
     *
     * @param json 参数
     * @return Data 巡更点列表
     * @author dwt
     * @date 2019-08-08 11:18
     */
    @Override
    public Data findCheckPointList(String json) {
        DeviceCheckPoint deviceCheckPoint = JSONObject.parseObject(json, DeviceCheckPoint.class);
        if (deviceCheckPoint.getTaskModelManageId() != null) {
            TaskModelDevice taskModelDevice = new TaskModelDevice();
            taskModelDevice.setDeleted(false);
            taskModelDevice.setTaskModelManageId(deviceCheckPoint.getTaskModelManageId());
            taskModelDevice.setPageSize(0);

            if (taskModelDevice.getOrderCols() == null || taskModelDevice.getOrderCols().length == 0) {
                String[] orderCols = {"createTime"};
                taskModelDevice.setOrderCols(orderCols);
            }
            List<TaskModelDevice> object = taskModelDeviceDao.findTaskModelDeviceList(taskModelDevice);
            return asseData(object);
        }else {
            List<TaskDeviceCheckPoint> list = checkPointTaskDao.findCheckPointList(deviceCheckPoint);
            return asseData(list);
        }
    }

    /**
     * 根据巡更点添加设备：筛选参数
     *
     * @param json 任务设备筛选实体
     * @return 巡更点列表
     * @author dwt
     * @date 2019-08-08 11:44
     */
    @Override
    public Data findCheckPoint(String json) {
        TaskDevice taskDevice = JSONObject.parseObject(json, TaskDevice.class);
        List<TaskDeviceCheckPoint> list = checkPointTaskDao.findCheckPoint(taskDevice);
        list = checkPointList(list);
        return asseData(list);
    }

    /**
     * 根据任务id查询
     *
     * @param taskId 任务ID
     * @return Data 寻根点设备列表
     * @author dwt
     * @date 2019-10-14 11:28
     */
    @Override
    public Data findCheckPointDeviceList(Long taskId) {
        List<CheckPoint> list = taskCheckPointDao.findCheckPointDeviceList(taskId);
        return asseData(list);
    }

    /**
     * 保存巡更点添加事物
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2019-10-14 14:40
     */
    @Override
    public Data transactionSaveCheckPoint(String json) {
        // 必须使用代码调用
        return ((TaskCheckPointServiceImpl) AopContext.currentProxy()).saveCheckPoint(json);
    }

    /**
     * 保存巡更点
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2019-10-14 14:41
     */
    @Override
    public Data saveCheckPoint(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        Long taskId = task.getId();
        if (taskId == null || taskId == 0) {
            return Data.isFail();
        }
        Long[] checkPoint = task.getCheckPointIds();
        Set<Long> set = new HashSet<>(Arrays.asList(checkPoint));
        Task oldTask = taskDao.findTaskById(taskId);
        if (oldTask != null && set.size() > 0) {
            taskCheckPointDao.deleteCheckPointByTaskId(taskId, null);
            TaskCheckPoint taskCheckPoint = new TaskCheckPoint();
            String pointName;
            Iterator<Long> it = set.iterator();
            Long checkPointId;
            while (it.hasNext()) {
                checkPointId = it.next();
                if (checkPointId != 0) {
                    taskCheckPoint.setId(null);
                    taskCheckPoint.setCheckPointId(checkPointId);
                    taskCheckPoint.setTaskId(taskId);
                    taskCheckPoint.setProjectId(oldTask.getProjectId());
                    pointName = checkPointTaskDao.findCheckPointName(checkPointId);
                    taskCheckPoint.setCheckPointName(pointName);
                    taskCheckPointDao.saveTaskCheckPoint(taskCheckPoint);
                }
            }
        }
        return Data.isSuccess();
    }

    /**
     * 查询楼层的巡查点列表
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-05-21 14:31
     */
    @Override
    public Data findCheckPointListByFloorId(String json) {
        TaskFloor floor = JSONObject.parseObject(json, TaskFloor.class);
        List<TaskDeviceCheckPoint> checkPoints = checkPointTaskDao.findCheckPointListByFloorId(floor);
        return asseData(checkPoints);
    }

    /**
     * 根据巡更点添加设备
     *
     * @param list 巡更点列表
     * @return List<TaskDeviceCheckPoint> 封装的巡更点列表和设备列表
     * @author dwt
     * @date 2019-08-08 11:52
     */
    private List<TaskDeviceCheckPoint> checkPointList(List<TaskDeviceCheckPoint> list) {
        List<TaskDeviceType> deviceTypeList;
        if (list != null && list.size() > 0) {
            for (TaskDeviceCheckPoint p : list) {
                deviceTypeList = taskDeviceTypeDao.findDeviceTypeByCheckPointId(p.getId());
                p.setDeviceTypeList(deviceTypeList);
            }
        }
        return list;
    }

    /**
     * 根据巡查点id查询巡查点下的设备列表
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-05-21 14:31
     */
    @Override
    public Data findDeviceTypeByCheckPointId(String json) {
        TaskCheckPoint point = JSONObject.parseObject(json, TaskCheckPoint.class);
        if (point.getId() != null && point.getId() != 0) {
            List<TaskDeviceType> deviceTypeList = taskDeviceTypeDao.findDeviceTypeByCheckPointId(point.getId());
            return asseData(deviceTypeList);
        }
        return Data.isSuccess();
    }

    /**
     * 根据设备类型id查询巡更点列表
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-06-15 16:28
     */
    @Override
    public Data findCheckPointByDeviceTypeId(String json) {
        TaskDevice taskDevice = JSONObject.parseObject(json, TaskDevice.class);
        List<TaskDeviceCheckPoint> pointList = deviceCheckPointDao.findCheckPointByDeviceTypeId(taskDevice);
        return asseData(pointList);
    }

    @Override
    public Data deleteCheckPointByCheckPointId(String json) {
        TaskCheckPoint checkPoint = JSONObject.parseObject(json, TaskCheckPoint.class);
        Integer count = taskCheckPointDao.deleteCheckPointByCheckPointId(checkPoint);
        if (count > 0) {
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * APP端添加设备巡查点列表
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-08-25 14:16
     */
    @Override
    public Data findCheckPointListBuildingAndSys(String json) throws Exception {
        DeviceCheckPoint checkPoint = JSONObject.parseObject(json, DeviceCheckPoint.class);
        List<AppCheckPoint> list = deviceCheckPointDao.findCheckPointListBuildingAndSys(checkPoint);
        return asseData(list);
    }

    /**
     *
     * @param str String
     * @return String
     */
    private String compress(String str) throws IOException {
        if (null == str || str.length() <= 0) {
            return str;
        }
        // 创建一个新的输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 使用默认缓冲区大小创建新的输出流
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        // 将字节写入此输出流
        // 因为后台默认字符集有可能是GBK字符集，所以此处需指定一个字符集
        gzip.write(str.getBytes("utf-8"));
        gzip.close();
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toString("ISO-8859-1");
    }
}
