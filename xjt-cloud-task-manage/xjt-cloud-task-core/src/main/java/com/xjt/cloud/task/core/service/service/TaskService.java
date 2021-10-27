package com.xjt.cloud.task.core.service.service;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.entity.task.Task;
import com.xjt.cloud.task.core.entity.TaskExecutor;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 任务管理
 * 
 * @author dwt
 * @date 2019-07-26 9:34
 */
public interface TaskService {
    /**
     * 保存任务
     *
     * @author dwt
     * @date 2019-07-25 11:33
     * @param json 参数 任务实体
     * @return id
     */
    Data saveTask(String json);

    /**
     *  根据筛选条件查询符合条件的任务
     *
     * @author dwt
     * @date 2019-07-25 14:01
     * @param json 参数
     * @return 任务列表
     */
    Data findTaskList(String json);

    /**
     * @Description 查询app首页巡查工单信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject findUserProjectCheckWordOrderData(String json);

    /**
     *  根据id查询任务对象
     *
     * @author dwt
     * @date 2019-07-25 14:03
     * @param id id
     * @param flag flag
     * @return 任务对象
     */
    Data findTaskById(Long id, boolean flag);

    /**
     * 定时任务业务处理
     *
     * @author dwt
     * @date 2019-08-05 15:37
     * @param type 类型
     * @return Data
     */
    Data taskHandle(String type);

    /**
     *  任务管理--导出表格功能
     *
     * @author huanggc
     * @date 2019-08-12
     * @param json 参数
     * @param resp HttpServletResponse
     */
    void downTaskList(String json, HttpServletResponse resp);

    /**
     *  任务工单--导出表格功能
     *
     * @author huanggc
     * @date 2019-08-12
     * @param json 参数
     * @param resp HttpServletResponse
     */
    void downTaskWork(String json, HttpServletResponse resp);

    /**
     * APP端子任务列表查询
     * 
     * @author dwt
     * @date 2019-08-15 10:46
     * @param json 参数
     * @return Data
     */
    Data findSonTaskListApp(String json);

    /**
     * 删除任务
     * 
     * @author dwt
     * @date 2019-08-16 11:08
     * @param json 参数
     * @return Data
     */
    Data deleteTask(String json);

    /**
     *  保存任务添加事物
     *
     * @author dwt
     * @date 2019-10-11 17:40
     * @param json 参数
     * @return Data
     */
    Data transactionSaveTask(String json);

    /**
     *  删除任务事物接口
     *
     * @author dwt
     * @date 2019-10-18 11:26
     * @param json 参数
     * @return Data
     */
    Data transactionDeleteTask(String json);

    /**
     *  删除任务事物接口
     *
     * @author dwt
     * @date 2019-10-18 11:26
     * @param json 参数
     * @return Data
     */
    Data transactionTaskHandle(String json);

    /**
     * 编辑执行中的子任务添加事物
     *
     *@author dwt
     *@date 2019-11-05 13:39
     *@param json 参数
     *@return Data
     */
    Data transactionModifySonTask(String json);

    /**
     * 编辑执行中的子任务
     *
     *@author dwt
     *@date 2019-11-05 13:39
     *@param json 参数
     *@return Data
     */
    Data modifySonTask(String json);

    /**
     * 查询我的任务列表
     *
     *@author zhangzf
     *@date 2019-07-25 14:01
     *@param json 参数
     *@return Data
     */
    Data findMyTaskList(String json);

    /**
     * 查询任务工单详情
     *
     *@author zhangzf
     *@date 2019-07-25 14:01
     *@param json 参数
     *@return Data
     */
    Data findTaskLocation(String json);

    /**
     * 修改任务设备
     *
     * @param task 任务实体
     * @author dwt
     * @date 2019-11-05 11:04
     */
    void modifyTaskCheckPoint(Task task);

    /**
     *   查询   0、执行人1、审核人
     *
     * @param task Task
     * @param taskExecutor TaskExecutor
     * @param executorType int
     * @author zhangZaiFa
     * @date 2019/12/11 9:25
     * @return java.util.List<java.lang.String>
     **/
    List<String> findExecutorsName(Task task, TaskExecutor taskExecutor,int executorType);

    /**
     * 下载离线任务
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/12/19 14:14
     **/
    Data downloadOfflineTask(String json);

    /**
     * 查询我的执行任务列表
     *
     *@author zhangzf
     *@date 2019-07-25 14:01
     *@param json 参数
     *@return 任务列表
     */
    Data findMyExecuteTaskList(String json);

    /**
     * 查询当前登录人的待办任务数（执行中和待审核）
     *
     *@author dwt
     *@date 2020-03-18 10:00
     *@param json 参数
     *@return Data
     */
    Data findCurrLoginProjectTaskNum(String json);

    /**
     * 创建任务上传巡查点添加设备
     *
     *@author dwt
     *@date 2020-03-24 13:50
     *@param json 参数,file
     *@return Data（返回设备列表）
     */
    Data uploadCheckPointExcel(String json, MultipartFile file);

    /**
     * 任务详情导出
     *
     *@author dwt
     *@date 2020-03-25 15:04
     *@param request HttpServletRequest
     *@param response HttpServletResponse
     *@param json 参数
     */
    void downloadTaskDetail(HttpServletRequest request, HttpServletResponse response, String json);

    /**
     *  查询扫描二维码巡检的任务
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/23 14:44
     **/
    Data findScanQrNoCheckTaskList(String json);


    /**
     * 查询我的抽查任务列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/5/7 13:54
     **/
    Data findMySpotCheckTaskList(String json);

    /**
     * 查询我的抽查任务详情
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/5/7 14:08
     **/
    Data findMySpotCheckTaskLocation(String json);

    /**
     * 任务工单设备导入
     *
     *@author dwt
     *@date 2020-05-08 9:35
     *@param json 参数
     *@param file file
     *@return Data（返回设备列表）
     */
    Data uploadTaskSheetDevice(String json, MultipartFile file);

    /**
     * 任务设备导入模板下载
     *
     *@author dwt
     *@date 2020-05-09 10:52
     *@param type 类型
     *@param response HttpServletResponse
     */
    void downLoadTaskModel(Integer type, HttpServletResponse response);

    /**
     * 查询任务分析
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/5/13 10:50
     **/
    Data findMonthTaskAnalysis(String json);

    /**
     * 地铁 巡检工单列表(子任务)
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-05-13
     **/
    Data findTaskMetroScreenList(String json);

    /**
     * 查询大屏任务记录的数据分析表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/5/19 13:43
     **/
    Data findScreenTaskRecordAnalysis(String json);

    Data parentTaskCreateTaskSheetTest();

    /**
     * 我的任务角标数量
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/6/1 13:52
     **/
    Data findMyTaskSubscript(String json);

    /**
     * 分页查询任务设备列表详情
     *
     *@author dwt
     *@date 2020-06-29 9:22
     *@param json 参数
     *@return Data
     */
    Data findTaskSheetDeviceListByTaskId(String json);

    /**
     * 任务添加设备查询巡查点id列表
     *
     *@author dwt
     *@date 2020-07-20 11:27
     *@param json 参数
     *@return Data
     */
    Data findCheckPointLongList(String json);

    /**
     * 查询任务工单详情(用于离线)
     *
     * @param json 参数
     * @return 任务列表
     * @author huanggc
     * @date 2021-02-25
     */
    Data findTaskLocations(String json);
}
