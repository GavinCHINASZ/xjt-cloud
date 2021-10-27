package com.xjt.cloud.task.core.service.service;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ClassName CheckRecordService
 * @Author dwt
 * @Date 2019-07-26 11:31
 * @Description 巡检记录
 * @Version 1.0
 */
public interface CheckRecordService {

    /**
     *
     * @Description: 查询符合条件的巡检记录列表
     * @Author: dwt     huanggc
     * @Date: 2019-07-25  2019-12-04
     * @param json
     * @return Data
     */
    Data findCheckRecordList(String json);

    /**
     * @Description 查询app首页巡检记录信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject findUserProjectCheckRecordData(String json);

    /**
     *@Author: dwt
     *@Date: 2019-07-25 17:12
     *@Param: java.lang.Long
     *@Return: 巡检记录对象
     *@Description: 根据id查询巡检记录
     */
    Data findCheckRecordById(Long id);

    /**
     *@Author: dwt
     *@Date: 2019-07-25 17:13
     *@Param: 巡检记录
     *@Return: 主键id
     *@Description: 保存巡检记录
     */
    Data  saveCheckRecord(String json, HttpServletRequest request);

    /**
     *@Author: dwt
     *@Date: 2019-07-25 17:19
     *@Param: 巡检记录
     *@Return:
     *@Description: 修改巡检记录
     */
    Data modifyCheckRecord(String json);

    /**
     *
     * 巡检记录--导出表格功能
     * @Author: huangGuiChuan
     * @Date: 2019-08-13
     * @param json
     * @param resp HttpServletResponse
     * @return com.xjt.cloud.commons.utils.Data
     */
    void downCheckRecord(String json, HttpServletResponse resp);

    /**
     *@Author: dwt
     *@Date: 2019-08-14 16:42
     *@Param: java.lang.Long
     *@Return: 巡检详情
     *@Description 根据巡检记录id查询巡检详情
     */
    Data findCheckRecordDetails(String json);

    /**
     *@Author: dwt
     *@Date: 2019-08-15 16:37
     *@Param: java.lang.Long
     *@Return: 巡检详情
     *@Description App端根据巡检记录id查询巡检详情
     */
    Data findCheckRecordDetailsByIdApp(String json);

    /**@MethodName: findCheckPointDeviceCheckItem
     * @Description: 查询巡检点设备的巡检项
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/27 10:42
     **/
    Data findCheckPointDeviceCheckItem(String json);


    /**@MethodName: findCheckRecordLocation
     * @Description: 查询任务巡查点巡检记录
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/29 9:27
     **/
    Data findTaskCheckPointCheckRecord(String json);

    /**@MethodName: saveOfficeCheckRecord
     * @Description: 提交离线巡检任务
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/20 15:38
     **/
    Data saveOfficeCheckRecord(String json,HttpServletRequest request);

    /**
     * 删除, 批量删除巡查记录
     *
     * @param json
     * @Author huangGuiChuan
     * @Date 2020-01-16
     * @return com.xjt.cloud.commons.utils.Data
     **/
    Data deletedCheckRecord(String json);

    /**@MethodName: findProjectCurrentMonthFaultCheckRecordCount
     * @Description: 查询项目当月故障巡检记录数量
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/3/19 14:42
     **/
    Data findProjectCurrentMonthFaultCheckRecordCount(String json);

    /**
     * 大屏--巡检管理
     *
     * @MethodName: findCheckRecordManage
     * @param json
     * @Author huanggc
     * @Date 2020-04-07
     * @Return: com.xjt.cloud.commons.utils.Data
     **/
    Data findCheckRecordManage(String json);

    /**
     * 功能描述: 报表二级页面:如: C1
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: huanggc
     * @date: 2019/11/08
     */
    Data findReportItem(String json);

    /**
     * 11号线吸气式烟雾探测器检修记录表 QSZDY-K10149-07-B2
     *
     * @Author huanggc
     * @Date: 2020-04-29
     * @param json
     * @param resp HttpServletResponse
     * @return void
     */
    void downSmokeCheckRecord(String json, HttpServletResponse resp);

    /**
     * 防灾报警系统报警功能测试记录表 QSZDYK1014909B2
     *
     * @Author huanggc
     * @Date: 2020-04-30
     * @param json
     * @param resp HttpServletResponse
     * @return void
     */
    void downAlarmTable(String json, HttpServletResponse resp);

    /**@MethodName: findSpotCheckTaskDeviceCheckItem
     * @Description: 查询抽查设备的巡检项
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/8 14:53
     **/
    Data findSpotCheckTaskDeviceCheckItem(String json);


    /**@MethodName: saveSpotCheckRecord
     * @Description: 保存抽查设备记录
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/9 14:39
     **/
    Data saveSpotCheckRecord(String json);

    /**@MethodName: findSpotCheckRecord
     * @Description: 查询抽查设备记录
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/9 14:39
     **/
    Data findSpotCheckRecord(String json);

    /**@MethodName: findAutomaticCheckDevice
     * @Description: 查询自动检测设备状态
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/11 14:56
     **/
    Data findAutomaticCheckDeviceStatus(String json);

    /**@MethodName: updAutomaticCheckDeviceStatus
     * @Description: 修改自动检测设备状态
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/11 17:01
     **/
    Data updAutomaticCheckDeviceStatus(String json);

    /**
     * 11号线吸气式烟雾探测器检修记录表 QSZDY-K10149-08-B2
     *
     * @Author huanggc
     * @Date: 2020-05-13
     * @param json
     * @param resp HttpServletResponse
     * @return void
     */
    void downInhaleSmokeTable(String json, HttpServletResponse resp);

    /**
     * QSZDYK1014911B2
     *
     * @Author huanggc
     * @Date: 2020-05-14
     * @param json
     * @param resp HttpServletResponse
     * @return void
     */
    void downDirectelEctricTable(String json, HttpServletResponse resp);

    /**
     * downReportExcelMetro
     *
     * @Author huanggc
     * @Date: 2020-05-14
     * @param json
     * @param resp HttpServletResponse
     * @return void
     */
    void downReportExcelMetro(String json, HttpServletRequest request, HttpServletResponse resp);

    /**
     * 报表查看 excel
     *
     * @Author huanggc
     * @Date: 2020-05-19
     * @param json
     * @param resp HttpServletResponse
     * @return
     */
    Data findReportExcelMetro(String json);

    /**
     * 地铁:微型消防站巡检卡
     *
     * @Author huanggc
     * @Date: 2020-05-25
     * @param json
     * @param request HttpServletRequest
     * @param resp HttpServletResponse
     * @return Data
     */
    Data findFireControlList(String json, HttpServletRequest request, HttpServletResponse resp);

    /**
     * 地铁:巡检记录(巡检记录导出word)
     *
     * @Author huanggc
     * @Date: 2020-05-26
     * @param json 参数
     * @param request HttpServletRequest
     * @param resp HttpServletResponse
     * @return Data
     */
    Data findCheckRecordMetroList(String json, HttpServletRequest request, HttpServletResponse resp);

    /**@MethodName: saveTaskDeviceCheckRecord
     * @Description:保存巡检记录带任务检测工具，现只有地铁app使用此接口
     * @Param: [json, request]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/6/17 13:33
     **/
    Data saveTaskDeviceCheckRecord(String json);

    /**@MethodName: saveTaskDeviceOfficeCheckRecord
     * @Description:保存离线巡检记录带任务检测工具，现只有地铁app使用此接口
     * @Param: [json, request]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/6/17 13:33
     **/
    Data saveTaskDeviceOfficeCheckRecord(String json);
}
