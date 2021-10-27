package com.xjt.cloud.task.core.service.impl.project;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.task.core.dao.device.DeviceCheckPointDao;
import com.xjt.cloud.task.core.dao.project.TaskOrganizationDao;
import com.xjt.cloud.task.core.entity.*;
import com.xjt.cloud.task.core.entity.device.DeviceCheckPoint;
import com.xjt.cloud.task.core.entity.task.TaskDevice;
import com.xjt.cloud.task.core.service.service.project.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * OrganizationServiceImpl
 *
 * @Author dwt
 * @Date 2019-08-09 10:26
 * 组织架构Service接口实现类
 */
@Service
public class OrganizationServiceImpl extends AbstractService implements OrganizationService {
    @Autowired
    private TaskOrganizationDao taskOrganizationDao;
    @Autowired
    private DeviceCheckPointDao deviceCheckPointDao;

    /**
     * 按部门添加设备:根据项目id查询公司
     *
     * @Author: dwt
     * @Date: 2019-08-07 17:42
     * @Param: java.lang.Long
     * @Return: 组织架构实体
     */
    @Override
    public Data findOrganizationByProjectId(String json) {
        TaskDevice device = JSONObject.parseObject(json, TaskDevice.class);
        List<Long> checkPointIds = device.getCheckPointIds();
        List<TaskOrganization> comList;
        if (checkPointIds != null && checkPointIds.size() > 0) {
            comList = taskOrganizationDao.findCompanyByProjectIdSelOrNotSel(device);
        } else {
            comList = taskOrganizationDao.findCompanyByProjectId(device);
        }
        if (comList != null && comList.size() > 0) {
            TaskDevice taskDevice = new TaskDevice();
            taskDevice.setType(device.getType());
            taskDevice.setProjectId(device.getProjectId());
            taskDevice.setCheckPointIds(checkPointIds);
            for (TaskOrganization o : comList) {
                o.setType(1);
                recurrenceOrg(o, taskDevice, 0);
            }
        }
        return asseData(comList);
    }

    /**
     * 查询部门及子部门下所有的巡查点
     *
     * @Author: dwt
     * @Date: 2020-07-24 9:36
     * @Param: json
     * @Return: Data
     */
    @Override
    public Data findOrgAndSonOrgCheckPoints(String json) {
        DeviceCheckPoint checkPoint = JSONObject.parseObject(json, DeviceCheckPoint.class);
        List<Long> list = deviceCheckPointDao.findOrgAndSonOrgCheckPoints(checkPoint);
        return asseData(list);
    }

    /**
     * 按部门添加设备：设备筛选条件
     *
     * @Author: dwt
     * @Date: 2019-08-07 16:23
     * @Param: com.xjt.cloud.task.core.entity.task.TaskDevice
     * @Return: 组织架构实体
     */
    @Override
    public Data findCompanyByTaskDevice(String json) {
        TaskDevice taskDevice = JSONObject.parseObject(json, TaskDevice.class);
        String qrNo = taskDevice.getQrNo();
        String pointName = taskDevice.getPointName();
        String orgName = taskDevice.getOrgName();
        List<TaskOrganization> parentList = new ArrayList<>();
        List<TaskOrganization> list;
        TaskOrganization taskOrganization;
        List<Long> checkPointIds = taskDevice.getCheckPointIds();

        // 避免在判断中使用复杂表达式
        boolean b = StringUtils.isNotEmpty(qrNo) || StringUtils.isNotEmpty(pointName);
        if (b && StringUtils.isEmpty(orgName)) {
            List<OrgCheckPoint> orgCheckPointList;
            if (checkPointIds != null && checkPointIds.size() > 0) {
                orgCheckPointList = deviceCheckPointDao.findCheckPointByPointNameSelOrNotSel(taskDevice);
            } else {
                orgCheckPointList = deviceCheckPointDao.findCheckPointByPointName(taskDevice);
            }

            Set<Long> set = new HashSet<>();
            if (orgCheckPointList != null && orgCheckPointList.size() > 0) {
                for (OrgCheckPoint o : orgCheckPointList) {
                    if (o.getCompanyId() != null && o.getCompanyId() != 0) {
                        set.add(o.getCompanyId());
                    }
                }
                if (set.size() > 0) {
                    taskDevice.setPointName(null);
                    taskDevice.setQrNo(null);
                    for (Long l : set) {
                        if (checkPointIds != null && checkPointIds.size() > 0) {
                            taskDevice.setCompanyId(l);
                            taskOrganization = taskOrganizationDao.findCompanyByCompanyIdSelOrNotSel(taskDevice);
                        } else {
                            taskOrganization = taskOrganizationDao.findCompanyByCompanyId(l);
                        }
                        if (taskOrganization != null) {
                            parentList.add(taskOrganization);
                            recurrenceCompanyOrg(taskOrganization, taskDevice, orgCheckPointList);
                        }
                    }
                }
            }
        } else {
            if (checkPointIds != null && checkPointIds.size() > 0) {
                list = taskOrganizationDao.findOrgSelOrNotSel(taskDevice);
            } else {
                list = taskOrganizationDao.findOrgByTaskDevice(taskDevice);
            }
            if (list != null && list.size() > 0) {
                List<TaskDeviceCheckPoint> checkPointList;
                DeviceCheckPoint deviceCheckPoint = new DeviceCheckPoint();
                deviceCheckPoint.setQrNo(qrNo);
                deviceCheckPoint.setPointName(pointName);
                deviceCheckPoint.setOrgName(orgName);
                List<TaskOrganization> comList;
                for (TaskOrganization o : list) {
                    deviceCheckPoint.setOrgId(o.getId());
                    checkPointList = deviceCheckPointDao.findCheckPointList(deviceCheckPoint);
                    o.setCheckPointList(checkPointList);
                    if (checkPointList != null) {
                        o.setCheckPointCount(checkPointList.size());
                    }
                    comList = taskOrganizationDao.findOrganizationById(o.getParentId());
                    recurrenceCompany(comList, o, parentList, deviceCheckPoint);
                }
            }
        }
        return asseData(parentList);
    }

    /**
     * 根据parentId反向递归查询公司
     *
     * @Author: dwt
     * @Date: 2019-11-11 10:30
     * @Param: 部门实体
     * @Return: List<TaskOrganization> 公司列表
     */
    private List<TaskOrganization> recurrenceCompany(List<TaskOrganization> comList, TaskOrganization od, List<TaskOrganization> parentList, DeviceCheckPoint deviceCheckPoint) {
        List<TaskOrganization> orgList;
        if (comList != null && comList.size() > 0) {
            Long parentId = comList.get(0).getParentId();
            List<TaskOrganization> child = new ArrayList<>();
            List<TaskDeviceCheckPoint> checkPointList;
            child.add(od);
            comList.get(0).setChild(child);
            if (parentId != null && parentId != 0) {
                deviceCheckPoint.setOrgId(parentId);
                checkPointList = deviceCheckPointDao.findCheckPointList(deviceCheckPoint);
                orgList = taskOrganizationDao.findOrganizationById(parentId);
                comList.get(0).setCheckPointList(checkPointList);
                comList.get(0).setCheckPointCount(checkPointList.size());
                recurrenceCompany(orgList, comList.get(0), parentList, deviceCheckPoint);
            } else {
                parentList.add(comList.get(0));
            }
        }
        return parentList;
    }

    /**
     * 递归查询封装公司部门以及部门下面的巡更点
     *
     * @Author: dwt
     * @Date: 2019-08-19 11:24
     * @Param: 部门实体，项目id
     * @Return: void
     */
    private void recurrenceOrg(TaskOrganization o, TaskDevice taskDevice, Integer type) {
        if (type == 0) {
            taskDevice.setCompanyId(o.getId());
        }
        Integer t = taskDevice.getType();
        taskDevice.setParentId(o.getId());
        List<Long> checkPointIds = taskDevice.getCheckPointIds();
        List<TaskOrganization> departList;
        if (checkPointIds != null && checkPointIds.size() > 0) {
            departList = taskOrganizationDao.findOrganizationSelOrNotSel(taskDevice);
        } else {
            departList = taskOrganizationDao.findOrganizationByCompanyId(taskDevice);
        }
        List<TaskOrganization> departSonList;
        List<TaskDeviceCheckPoint> checkPointList;
        if (departList == null || departList.size() <= 0) {
            o.setChild(null);
        } else {
            o.setChild(departList);
            DeviceCheckPoint deviceCheckPoint = new DeviceCheckPoint();
            Integer count;
            for (TaskOrganization od : departList) {
                deviceCheckPoint.setOrgId(od.getId());
                od.setType(2);
                taskDevice.setParentId(od.getId());
                if (checkPointIds != null && checkPointIds.size() > 0) {
                    departSonList = taskOrganizationDao.findOrganizationSelOrNotSel(taskDevice);
                } else {
                    departSonList = taskOrganizationDao.findOrganizationByCompanyId(taskDevice);
                }
                if (t != null && t != 0) {
                    count = deviceCheckPointDao.findCheckPointCount(deviceCheckPoint);
                    if (count == null) {
                        count = 0;
                    }
                    od.setCheckPointCount(count);
                } else {
                    checkPointList = deviceCheckPointDao.findCheckPointList(deviceCheckPoint);
                    od.setCheckPointList(checkPointList);
                    if (od.getCheckPointList() != null && od.getCheckPointList().size() > 0) {
                        od.setCheckPointCount(od.getCheckPointList().size());
                    } else {
                        od.setCheckPointCount(0);
                    }
                }
                if (departSonList != null && departSonList.size() > 0) {
                    recurrenceOrg(od, taskDevice, 1);
                }
            }
        }
    }

    /**
     * 递归查询封装公司部门以及部门下面的巡更点
     *
     * @Author: dwt
     * @Date: 2019-08-19 11:24
     * @Param: 部门实体，项目id
     */
    private void recurrenceCompanyOrg(TaskOrganization o, TaskDevice taskDevice, List<OrgCheckPoint> orgCheckPointList) {
        taskDevice.setParentId(o.getId());
        List<Long> checkPointIds = taskDevice.getCheckPointIds();
        List<TaskOrganization> departList;
        if (checkPointIds != null && checkPointIds.size() > 0) {
            departList = taskOrganizationDao.findOrganizationSelOrNotSel(taskDevice);
        } else {
            departList = taskOrganizationDao.findOrganizationByCompanyId(taskDevice);
        }
        List<TaskDeviceCheckPoint> checkPointList = new ArrayList<>();
        Long orgId = o.getId();
        Long orgId1;
        for (OrgCheckPoint ocp : orgCheckPointList) {
            orgId1 = ocp.getOrgId();
            if (orgId.equals(orgId1)) {
                orgCheckPoint(ocp, checkPointList);
            }
        }
        if (checkPointList.size() > 0) {
            o.setCheckPointList(checkPointList);
        }
        if (departList == null || departList.size() <= 0) {
            o.setChild(null);
        } else {
            TaskOrganization od;
            for (int i = 0; i < departList.size(); i++) {
                od = departList.get(i);
                taskDevice.setParentId(od.getId());
                recurrenceCompanyOrg(od, taskDevice, orgCheckPointList);

                boolean b = od.getCheckPointList() == null || od.getCheckPointList().size() <= 0;
                boolean c = od.getChild() == null || od.getChild().size() <= 0;
                if (b && c) {
                    departList.remove(od);
                    i--;
                } else if (od.getCheckPointList() != null && od.getCheckPointList().size() > 0) {
                    od.setCheckPointCount(od.getCheckPointList().size());
                }
            }
            o.setChild(departList);
        }
    }

    /**
     * @param ocp            OrgCheckPoint
     * @param checkPointList TaskDeviceCheckPoint
     */
    private void orgCheckPoint(OrgCheckPoint ocp, List<TaskDeviceCheckPoint> checkPointList) {
        TaskDeviceCheckPoint taskDeviceCheckPoint = new TaskDeviceCheckPoint();
        taskDeviceCheckPoint.setTotalCount(ocp.getDeviceNum());
        taskDeviceCheckPoint.setCheckPointCount(ocp.getDeviceNum());
        taskDeviceCheckPoint.setPointName(ocp.getPointName());
        taskDeviceCheckPoint.setQrNo(ocp.getQrNo());
        taskDeviceCheckPoint.setId(ocp.getCheckPointId());

        taskDeviceCheckPoint.setBuildingId(ocp.getBuildingId());
        taskDeviceCheckPoint.setFloorId(ocp.getFloorId());

        checkPointList.add(taskDeviceCheckPoint);
    }
}
