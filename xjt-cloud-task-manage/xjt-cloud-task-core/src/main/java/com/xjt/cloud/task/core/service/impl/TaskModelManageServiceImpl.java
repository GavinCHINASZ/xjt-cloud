package com.xjt.cloud.task.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.task.core.common.ConstantsDevice;
import com.xjt.cloud.task.core.dao.device.DeviceCheckPointDao;
import com.xjt.cloud.task.core.dao.sys.UserDao;
import com.xjt.cloud.task.core.dao.task.*;
import com.xjt.cloud.task.core.entity.task.TaskModelDevice;
import com.xjt.cloud.task.core.entity.task.TaskModelManage;
import com.xjt.cloud.task.core.service.service.TaskModelManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * TaskImplService 任务管理
 *
 * @author dwt
 * @date 2019-07-26 9:36
 */
@Service
public class TaskModelManageServiceImpl extends AbstractService implements TaskModelManageService {
    @Autowired
    private TaskModelManageDao taskModelManageDao;
    @Autowired
    private TaskModelDeviceDao taskModelDeviceDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DeviceCheckPointDao deviceCheckPointDao;

    /**
     * 查询 模板管理list
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/09
     */
    @Override
    public Data findTaskModelManageList(String json) {
        TaskModelManage taskModelManage = JSONObject.parseObject(json, TaskModelManage.class);
        taskModelManage.setDeleted(false);

        Date startTime = taskModelManage.getStartTime();
        if (startTime != null){
            taskModelManage.setCreateTime(DateUtils.monthStarDate(startTime));
            taskModelManage.setLastModifyTime(DateUtils.monthEndDate(startTime));
        }

        Integer totalCount = taskModelManage.getTotalCount();
        Integer pageSize = taskModelManage.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            // 判断是否存在总数，如没有，则查询总记录数
            totalCount = taskModelManageDao.findTaskModelManageTotalCount(taskModelManage);
        }

        if (taskModelManage.getOrderCols() == null || taskModelManage.getOrderCols().length == 0) {
            String[] orderCols = {"createTime"};
            taskModelManage.setOrderCols(orderCols);
        }
        List<TaskModelManage> taskModelManageList = taskModelManageDao.findTaskModelManageList(taskModelManage);

        return asseData(totalCount, taskModelManageList);
    }

    /**
     * 修改 模板管理list
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/09
     */
    @Override
    public Data updateTaskModelManage(String json) {
        TaskModelManage taskModelManage = JSONObject.parseObject(json, TaskModelManage.class);

        if (taskModelManage.getDeleted()){
            // 删除模板下的设备
            TaskModelDevice taskModelDevice = new TaskModelDevice();
            taskModelDevice.setTaskModelManageIdArr(taskModelManage.getIds());
            taskModelDeviceDao.deletedTaskModelDevice(taskModelDevice);
        }

        int num = taskModelManageDao.updateTaskModelManage(taskModelManage);
        if (num > 0) {
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * 任务模板导入设备
     *
     * @param json 参数
     * @param files 表格
     * @return Data
     * @author huanggc
     * @date 2021/03/10
     */
    @Override
    public Data uploadTaskModelDevice(String json, MultipartFile[] files) {
        // 判断file数组不能为空并且长度大于0
        if(files != null && files.length > 0){
            //              序号"rowNum", 	    巡检点ID	巡检点名称	设备名称	模板类型	位置描述
            String[] keys = {"pointQrNo", "pointName", "deviceName", "modelTypeName", "pointLocation"};

            TaskModelDevice taskModelDevice = JSONObject.parseObject(json, TaskModelDevice.class);
            // 查询项目中是否已有模板名称
            TaskModelManage taskModelManage = new TaskModelManage();
            Long projectId = taskModelDevice.getProjectId();
            taskModelManage.setProjectId(projectId);

            StringJoiner joiner = new StringJoiner(",");
            // 循环获取file数组中得文件
            for(int i = 0; i < files.length; i++){
                MultipartFile file = files[i];

                String fileName = file.getOriginalFilename();
                if (StringUtils.isEmpty(fileName)) {
                    return asseData("表格名称错误!");
                }

                String excelName = fileName.substring(0, fileName.indexOf("."));
                if ("请修改表格名称".equals(excelName)){
                    return asseData("表格名称为模板名称,请修改表格名称!");
                }

                taskModelManage.setModelName(excelName);
                TaskModelManage modelManage = taskModelManageDao.findTaskModelManage(taskModelManage);
                if (modelManage != null){
                    // 模板已存在
                    return asseData("模板已存在!");
                }else {
                    // 解析表格
                    List<TaskModelDevice> taskModelDeviceList = ExcelUtils.readyExcel(file, 1, -1, 0, keys, TaskModelDevice.class);
                    if (CollectionUtils.isNotEmpty(taskModelDeviceList)) {
                        taskModelDeviceList.remove(taskModelDeviceList.size() - 1);
                        // 查询出项目中不存在的巡检点ID(上传的表格中输入巡检点在项目中不存在)
                        List<TaskModelDevice> checkPointNotIn =deviceCheckPointDao.findCheckPointNotIn(projectId, taskModelDeviceList);
                        if (CollectionUtils.isNotEmpty(checkPointNotIn)){
                            // 用来保存不在项目中的巡检点
                            List<TaskModelDevice> x = new ArrayList<>(checkPointNotIn.size());

                            for (TaskModelDevice t : checkPointNotIn){
                                String pointQrNo = t.getPointQrNo();
                                for (TaskModelDevice entity : taskModelDeviceList){
                                    if (pointQrNo.equals(entity.getPointQrNo())){
                                        // 不在项目中的巡检点
                                        x.add(entity);
                                        break;
                                    }
                                }
                                joiner.add(pointQrNo);
                            }

                            if (CollectionUtils.isNotEmpty(x)){
                                taskModelDeviceList.removeAll(x);
                            }
                        }

                        // 找出重复的巡检点并删除
                        if (CollectionUtils.isNotEmpty(taskModelDeviceList)) {
                            List<TaskModelDevice> a = new ArrayList<>();
                            for (int x = 0; x < taskModelDeviceList.size() - 1; x++) {
                                for (int j = x + 1; j < taskModelDeviceList.size(); j++) {
                                    if (taskModelDeviceList.get(x).getPointQrNo().equals(taskModelDeviceList.get(j).getPointQrNo())) {
                                        a.add(taskModelDeviceList.get(j));
                                    }
                                }
                            }

                            if (CollectionUtils.isNotEmpty(a)) {
                                taskModelDeviceList.removeAll(a);
                            }
                        }

                        // 新增模板管理
                        if (CollectionUtils.isNotEmpty(taskModelDeviceList)) {
                            String modelTypeName = taskModelDeviceList.get(0).getModelTypeName();
                            if ("抽检".equals(modelTypeName)) {
                                taskModelManage.setModelType(1);
                            } else {
                                taskModelManage.setModelType(2);
                            }
                            String userName = getUserNameString(projectId);
                            taskModelManage.setCreateUserName(userName);
                            String orgNameString = getOrgNameString(projectId);
                            taskModelManage.setOrgName(orgNameString);
                            taskModelManageDao.saveTaskModelManage(taskModelManage);

                            taskModelDeviceDao.saveTaskModelDeviceList(taskModelDeviceList, projectId, taskModelManage.getId());
                        }
                    }
                }
            }

            if (StringUtils.isNotEmpty(joiner.toString())){
                return asseData("巡检点ID不存在项目中:" +
                        (joiner.length() > 230 ? joiner.toString().substring(0, 230) + "......" : joiner));
            }
        }
        return Data.isSuccess();
    }

    /**
     *  获取成员名称
     *
     * @param projectId 项目ID
     * @return String
     */
    private String getUserNameString(Long projectId) {
        // 登录名
        String userName = SecurityUserHolder.getUserName();
        SysLog.info("登录名------------->" + userName);
        Long userId = getLoginUserId(userName);

        JSONObject jsonObject = new JSONObject(2);
        jsonObject.put("projectId", projectId);
        jsonObject.put("userId", userId);
        try {
            // 项目内 成员名称
            userName = HttpUtils.sendGet(ConstantsDevice.FIND_ORG_USER_NAME_URL, "json=" + jsonObject.toJSONString());
        } catch (Exception e) {
            userName = "/";
        }
        SysLog.info("成员名称------------->" + userName);
        return userName;
    }

    /**
     *  获取成员名称
     *
     * @param projectId 项目ID
     * @return String
     */
    private String getOrgNameString(Long projectId) {
        // 登录名
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        return userDao.getOrgNameString(projectId, userId);
    }

    /**
     * 查询 模板设备list
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/10
     */
    @Override
    public Data findTaskModelDeviceList(String json) {
        TaskModelDevice taskModelDevice = JSONObject.parseObject(json, TaskModelDevice.class);
        taskModelDevice.setDeleted(false);

        Integer totalCount = taskModelDevice.getTotalCount();
        Integer pageSize = taskModelDevice.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            // 判断是否存在总数，如没有，则查询总记录数
            totalCount = taskModelDeviceDao.findTaskModelDeviceTotalCount(taskModelDevice);
        }

        if (taskModelDevice.getOrderCols() == null || taskModelDevice.getOrderCols().length == 0) {
            String[] orderCols = {"createTime"};
            taskModelDevice.setOrderCols(orderCols);
        }
        List<TaskModelDevice> taskModelDeviceList = taskModelDeviceDao.findTaskModelDeviceList(taskModelDevice);

        return asseData(totalCount, taskModelDeviceList);
    }

    /**
     * 删除 模板设备list
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/10
     */
    @Override
    public Data deletedTaskModelDevice(String json) {
        TaskModelDevice taskModelDevice = JSONObject.parseObject(json, TaskModelDevice.class);
        int num = taskModelDeviceDao.deletedTaskModelDevice(taskModelDevice);
        if (num > 0) {
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * 保存 模板设备
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/10
     */
    @Override
    public Data saveTaskModelDevice(String json) {
        TaskModelDevice taskModelDevice = JSONObject.parseObject(json, TaskModelDevice.class);

        // 判断 巡检点 是否存在
        int checkPointNum = deviceCheckPointDao.findByTaskModelDevice(taskModelDevice);
        if (checkPointNum < 1){
            return asseData("巡检点不存在!");
        }

        int num = taskModelDeviceDao.saveTaskModelDevice(taskModelDevice);
        if (num > 0) {
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * 更新 模板设备
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/17
     */
    @Override
    public Data updateTaskModelDevice(String json) {
        TaskModelDevice taskModelDevice = JSONObject.parseObject(json, TaskModelDevice.class);

        int num = taskModelDeviceDao.updateTaskModelDevice(taskModelDevice);
        if (num > 0) {
            return Data.isSuccess();
        }
        return Data.isFail();
    }
}
