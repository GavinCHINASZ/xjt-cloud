package com.xjt.cloud.task.core.service.impl.project;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.dao.device.DeviceCheckPointDao;
import com.xjt.cloud.task.core.dao.project.BuildingFloorDao;
import com.xjt.cloud.task.core.dao.project.TaskBuildingDao;
import com.xjt.cloud.task.core.entity.project.TaskBuilding;
import com.xjt.cloud.task.core.entity.task.TaskDevice;
import com.xjt.cloud.task.core.entity.TaskDeviceCheckPoint;
import com.xjt.cloud.task.core.entity.project.TaskFloor;
import com.xjt.cloud.task.core.service.service.project.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 建筑物Service接口实现类
 *
 * @author dwt
 * @date 2019-08-09 10:21
 */
@Service
public class BuildingServiceImpl extends AbstractService implements BuildingService {
    @Autowired
    private TaskBuildingDao taskBuildingDao;
    @Autowired
    private BuildingFloorDao buildingFloorDao;
    @Autowired
    private DeviceCheckPointDao deviceCheckPointDao;

    /**
     * 按建筑物楼层添加设备：根据项目id查询
     *
     * @param json 参数
     * @return 建筑物，楼层，巡更点列表
     * @author dwt
     * @date 2019-08-06 18:03
     */
    @Override
    public Data findBuildingByProjectId(String json) {
        TaskDevice taskDevice = JSONObject.parseObject(json, TaskDevice.class);
        Long projectId = taskDevice.getProjectId();
        List<Long> checkPointIds = taskDevice.getCheckPointIds();
        List<TaskBuilding> list;
        if (checkPointIds != null && checkPointIds.size() > 0) {
            list = taskBuildingDao.findBuildingListByProjectId(taskDevice);
        } else {
            list = taskBuildingDao.findByProjectId(projectId);
        }
        list = buildingList(list, taskDevice);
        return asseData(list);
    }

    /**
     * 按建筑物楼添加设备：筛选参数
     *
     * @param json 任务设备筛选实体
     * @return 建筑物列表
     * @author dwt
     * @date 2019-08-08 10:37
     */
    @Override
    public Data findBuilding(String json) {
        TaskDevice taskDevice = JSONObject.parseObject(json, TaskDevice.class);
        List<Long> checkPointIds = taskDevice.getCheckPointIds();
        List<TaskBuilding> list;
        if (checkPointIds != null && checkPointIds.size() > 0) {
            list = taskBuildingDao.findBuildingSelORNOtSel(taskDevice);
        } else {
            list = taskBuildingDao.findBuilding(taskDevice);
        }
        list = buildingList(list, taskDevice);
        return asseData(list);
    }

    /**
     * 按建筑物楼筛选
     *
     * @param list 建筑物列表
     * @param taskDevice TaskDevice
     * @return 封装按建筑物查询结果
     * @author dwt
     * @date 2019-08-08 10:45
     */
    private List<TaskBuilding> buildingList(List<TaskBuilding> list, TaskDevice taskDevice) {
        List<TaskFloor> taskFloorList;
        List<TaskDeviceCheckPoint> checkPointList;
        List<Long> checkPointIds = taskDevice.getCheckPointIds();
        if (list != null && list.size() > 0) {
            for (TaskBuilding b : list) {
                taskDevice.setBuildingId(b.getId());
                if (checkPointIds != null && checkPointIds.size() > 0) {
                    taskFloorList = buildingFloorDao.findFloorListSelOrNotSel(taskDevice);
                } else {
                    taskFloorList = buildingFloorDao.findFloorListByTaskDevice(taskDevice);
                }
                taskDevice.setBuildingId(null);
                b.setTaskFloorList(taskFloorList);
                if (taskFloorList != null && taskFloorList.size() > 0) {
                    for (TaskFloor f : taskFloorList) {
                        taskDevice.setFloorId(f.getId());
                        checkPointList = deviceCheckPointDao.findCheckPointListByTaskDevice(taskDevice);
                        f.setCheckPointList(checkPointList);
                    }
                    taskDevice.setFloorId(null);
                }
            }
        }
        return list;
    }
}