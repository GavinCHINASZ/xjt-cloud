package com.xjt.cloud.task.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.service.service.*;
import com.xjt.cloud.task.core.service.service.device.DeviceService;
import com.xjt.cloud.task.core.service.service.device.DeviceTypeService;
import com.xjt.cloud.task.core.service.service.project.BuildingService;
import com.xjt.cloud.task.core.service.service.project.OrganizationService;
import com.xjt.cloud.task.core.service.service.project.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 任务Controller层
 *
 * @author dwt
 * @date 2019-07-26 1032
 */
@RestController
@RequestMapping("/task/")
public class TaskController extends AbstractController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private CheckPointService checkPointService;
    @Autowired
    private DeviceTypeService deviceTypeService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SubwayTaskService subwayTaskService;

    /**
     * 保存任务
     *
     * @param json 任务实体
     * @return Data
     * @author dwt
     * @date 2019-07-25 1133
     */
    @RequestMapping(value = "saveTask/{projectId}")
    public Data saveTask(String json) {
        return taskService.transactionSaveTask(json);
    }

    /**
     * 定时任务处理
     *
     * @param json 参数
     * @return 处理时间
     * @author dwt
     * @date 2019-08-05 1532
     */
    @RequestMapping(value = "taskHandle")
    public Data taskHandle(String json) {
        return taskService.transactionTaskHandle(json);
    }

    /**
     * 按部门添加设备查询巡更点
     *
     * @param json 参数
     * @return Data 巡更点
     * @author dwt
     * @date 2019-08-07 1848
     */
    @RequestMapping(value = "findDepartmentDevice/{projectId}")
    public Data findDepartmentDevice(String json) {
        return organizationService.findOrganizationByProjectId(json);
    }

    /**
     * 按部门添加设备设备筛选条件
     *
     * @param json 参数
     * @return Data 组织架构实体
     * @author dwt
     * @date 2019-08-07 1623
     */
    @RequestMapping(value = "findCompanyByTaskDevice/{projectId}")
    public Data findCompanyByTaskDevice(String json) {
        return organizationService.findCompanyByTaskDevice(json);
    }

    /**
     * 按建筑物楼层添加设备：查询巡更点
     *
     * @param json
     * @return 建筑物封装实体
     * @author dwt
     * @date 2019-08-08 1052
     */
    @RequestMapping(value = "findBuildingByProjectId/{projectId}")
    public Data findBuildingByProjectId(String json) {
        return buildingService.findBuildingByProjectId(json);
    }

    /**
     * 按建筑物楼添加设备：设备筛选条件
     *
     * @param json 任务设备筛选实体
     * @return 建筑物列表
     * @author dwt
     * @date 2019-08-08 1037
     */
    @RequestMapping(value = "findBuilding/{projectId}")
    public Data findBuilding(String json) {
        return buildingService.findBuilding(json);
    }

    /**
     * 根据巡更点添加设备：根据项目id查询
     *
     * @param json 参数
     * @return 巡更点列表
     * @author dwt
     * @date 2019-08-08 1118
     */
    @RequestMapping(value = "findCheckPointList/{projectId}")
    public Data findCheckPointList(String json) {
        return checkPointService.findCheckPointList(json);
    }

    /**
     * 根据巡更点添加设备：筛选参数
     *
     * @param json 参数
     * @return 巡更点列表
     * @author dwt
     * @date 2019-08-08 1144
     */
    @RequestMapping(value = "findCheckPoint/{projectId}")
    public Data findCheckPoint(String json) {
        return checkPointService.findCheckPoint(json);
    }

    /**
     * 按系统添加设备：根据项目id查询设备系统列表
     *
     * @param json 参数
     * @return 设备系统列表
     * @author dwt
     * @date 2019-08-08 1424
     */
    @RequestMapping(value = "findDeviceSys/{projectId}")
    public Data findDeviceSysByProjectId(String json) {
        return deviceTypeService.findDeviceSysByProjectId(json);
    }

    /**
     * 根据巡更点id查询设备列表
     *
     * @param json 参数
     * @return 设备列表
     * @author dwt
     * @date 2019-08-09 1111
     */
    @RequestMapping(value = "findDeviceList/{projectId}")
    public Data findDeviceListByCheckPointId(String json) {
        return deviceService.findDeviceListByCheckPointId(json);
    }

    /**
     * 根据项目id查询角色列表
     *
     * @param json 参数
     * @return 角色列表
     * @author dwt
     * @date 2019-08-09 1127
     */
    @RequestMapping(value = "findRole/{projectId}")
    public Data findRoleByProjectId(String json) {
        return roleService.findRoleByProjectId(json);
    }

    /**
     * 根据筛选条件查询符合条件的任务
     *
     * @param json 任务实体
     * @return 任务列表
     * @author dwt
     * @date 2019-07-25 1401
     */
    @RequestMapping(value = "findTaskList/{projectId}")
    public Data findTaskList(String json) {
        return taskService.findTaskList(json);
    }

    /**
     * 根据id查询任务对象
     *
     * @param id id
     * @return 任务对象
     * @author dwt
     * @date 2019-07-25 1403
     */
    @RequestMapping(value = "findTaskById/{projectId}")
    public Data findTaskById(Long id) {
        return taskService.findTaskById(id, true);
    }

    /**
     * 任务管理--导出表格功能
     *
     * @param json 参数
     * @param resp HttpServletResponse
     * @author huanggc
     * @date 2019-08-12
     */
    @RequestMapping(value = "downTaskList/{projectId}")
    public void downTaskList(String json, HttpServletResponse resp) {
        taskService.downTaskList(json, resp);
    }

    /**
     * 任务工单--导出表格功能
     *
     * @param json 参数
     * @param resp HttpServletResponse
     * @author huanggc
     * @date 2019-08-12
     */
    @RequestMapping(value = "downTaskWork/{projectId}")
    public void downTaskWork(String json, HttpServletResponse resp) {
        taskService.downTaskWork(json, resp);
    }

    /**
     * app端子任务列表查询
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2019-08-15 1449
     */
    @RequestMapping(value = "findSonTaskListApp/{projectId}")
    public Data findSonTaskListApp(String json) {
        return taskService.findSonTaskListApp(json);
    }

    /**
     * 对任务进行逻辑删除和恢复
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2019-08-16 1347
     */
    @RequestMapping(value = "deletedTask/{projectId}")
    public Data deletedTask(String json) {
        return taskService.transactionDeleteTask(json);
    }

    /**
     * 根据任务id查询
     *
     * @param taskId 任务ID
     * @return 寻根点设备列表
     * @author dwt
     * @date 2019-10-14 1128
     */
    @RequestMapping(value = "findCheckPointDeviceList/{projectId}")
    public Data findCheckPointDeviceList(Long taskId) {
        return checkPointService.findCheckPointDeviceList(taskId);
    }

    /**
     * 任务编辑巡更点
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2019-10-14 1444
     */
    @RequestMapping(value = "modifyCheckPoint/{projectId}")
    public Data modifyCheckPoint(String json) {
        return checkPointService.transactionSaveCheckPoint(json);
    }

    /**
     * 修改执行中的子任务
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2019-11-05 1510
     */
    @RequestMapping(value = "modifyExecutingSonTask/{projectId}")
    public Data modifyExecutingSonTask(String json) {
        return taskService.transactionModifySonTask(json);
    }

    /**
     * 查询当前登录人的待办任务数（执行中和待审核）
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2020-03-18 1020
     */
    @RequestMapping(value = "findCurrLoginProjectTaskNum/{projectId}")
    public Data findCurrLoginProjectTaskNum(String json) {
        return taskService.findCurrLoginProjectTaskNum(json);
    }

    /**
     * 任务上传巡查点添加设备
     *
     * @param json 参数
     * @param file 文件
     * @return Data
     * @author dwt
     * @date 2020-03-25 1425
     */
    @RequestMapping(value = "uploadCheckPointExcel/{projectId}")
    public Data uploadCheckPointExcel(String json, MultipartFile file) {
        return taskService.uploadCheckPointExcel(json, file);
    }

    /**
     * 任务管理详情导出
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param json     参数
     * @author dwt
     * @date 2020-03-26 1526
     */
    @RequestMapping(value = "downloadTaskDetail/{projectId}")
    public void downloadTaskDetail(HttpServletRequest request, HttpServletResponse response, String json) {
        taskService.downloadTaskDetail(request, response, json);
    }

    /**
     * 任务上传巡查点添加设备
     *
     * @param json, file
     * @return Data
     * @author dwt
     * @date 2020-05-05 1425
     */
    @RequestMapping(value = "uploadTaskSheetDevice/{projectId}")
    public Data uploadTaskSheetDevice(String json, MultipartFile file) {
        return taskService.uploadTaskSheetDevice(json, file);
    }

    /**
     * 任务设备导入模板
     *
     * @param type     type
     * @param response HttpServletResponse
     * @author dwt
     * @date 2020-05-09 1105
     */
    @RequestMapping(value = "downLoadTaskModel/{projectId}")
    public void downLoadTaskModel(Integer type, HttpServletResponse response) {
        taskService.downLoadTaskModel(type, response);
    }

    /**
     * 查询任务分析
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/5/13 1050
     **/
    @RequestMapping(value = "findMonthTaskAnalysis")
    public Data findMonthTaskAnalysis(String json) {
        return taskService.findMonthTaskAnalysis(json);
    }

    /**
     * 地铁 巡检工单列表(子任务)
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-05-13
     **/
    @RequestMapping(value = "findTaskMetroScreenList")
    public Data findTaskMetroScreenList(String json) {
        return taskService.findTaskMetroScreenList(json);
    }

    /**
     * 地铁 查询大屏任务记录的数据分析表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaifa
     * @date 2020-05-19
     **/
    @RequestMapping(value = "findScreenTaskRecordAnalysis/{projectId}")
    public Data findScreenTaskRecordAnalysis(String json) {
        return taskService.findScreenTaskRecordAnalysis(json);
    }

    /**
     * 查询楼层的巡查点列表
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-05-21 1431
     */
    @RequestMapping(value = "findCheckPointListByFloorId/{projectId}")
    public Data findCheckPointListByFloorId(String json) {
        return checkPointService.findCheckPointListByFloorId(json);
    }

    /**
     * 根据巡查点id查设备列表
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-05-21 1515
     */
    @RequestMapping(value = "findDeviceTypeByCheckPointId/{projectId}")
    public Data findDeviceTypeByCheckPointId(String json) {
        return checkPointService.findCheckPointListByFloorId(json);
    }

    /**
     * 根据设备类型id查询巡更点列表
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-06-15 1628
     */
    @RequestMapping(value = "findCheckPointByDeviceTypeId/{projectId}")
    public Data findCheckPointByDeviceTypeId(String json) {
        return checkPointService.findCheckPointByDeviceTypeId(json);
    }

    /**
     * 根据巡查点id删除任务中的巡查点
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-06-28 1528
     */
    @RequestMapping(value = "deleteCheckPointByCheckPointId")
    public Data deleteCheckPointByCheckPointId(String json) {
        return checkPointService.deleteCheckPointByCheckPointId(json);
    }

    /**
     * 分页查询任务详情设备列表
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-06-29 955
     */
    @RequestMapping(value = "findTaskSheetDeviceListByTaskId/{projectId}")
    public Data findTaskSheetDeviceListByTaskId(String json) {
        return taskService.findTaskSheetDeviceListByTaskId(json);
    }

    /**
     * 地铁平面图巡查点统计
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-06-19 1026
     */
    @RequestMapping(value = "findCurrentMontSubwayTask/{projectId}")
    public Data findCurrentMontSubwayTask(String json) {
        return subwayTaskService.findCurrentMontSubwayTask(json);
    }

    /**
     * 地铁平面图建筑物楼层巡查点统计
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-06-22 1431
     */
    @RequestMapping(value = "findBuildingFloorMetroCheckPointCount/{projectId}")
    public Data findBuildingFloorMetroCheckPointCount(String json) {
        return subwayTaskService.findBuildingFloorMetroCheckPointCount(json);
    }

    /**
     * 地铁平面图根据任务id查询建筑物楼层列表
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-06-30 1438
     */
    @RequestMapping(value = "findBuildingAndFloorListByTaskId/{projectId}")
    public Data findBuildingAndFloorListByTaskId(String json) {
        return subwayTaskService.findBuildingAndFloorListByTaskId(json);
    }

    /**
     * 地铁平面图布点巡查点列表查询
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-07-02 1643
     */
    @RequestMapping(value = "findCheckPointListByTaskIdSubway/{projectId}")
    public Data findCheckPointListByTaskIdSubway(String json) {
        return subwayTaskService.findCheckPointListByTaskIdSubway(json);
    }

    /**
     * 地铁平面图用户信息查询
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-07-02 1742
     */
    @RequestMapping(value = "findUserMessageByUserId/{projectId}")
    public Data findUserMessageByUserId(String json) {
        return subwayTaskService.findUserMessageByUserId(json);
    }

    /**
     * 任务添加设备查询巡查点id列表
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-07-20 1127
     */
    @RequestMapping(value = "findCheckPointLongList/{projectId}")
    public Data findCheckPointLongList(String json) {
        return taskService.findCheckPointLongList(json);
    }

    /**
     * 查询部门及其子部门下所有巡查点id
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-07-24 1344
     */
    @RequestMapping(value = "findOrgAndSonOrgCheckPoints/{projectId}")
    public Data findOrgAndSonOrgCheckPoints(String json) {
        return organizationService.findOrgAndSonOrgCheckPoints(json);
    }

}
