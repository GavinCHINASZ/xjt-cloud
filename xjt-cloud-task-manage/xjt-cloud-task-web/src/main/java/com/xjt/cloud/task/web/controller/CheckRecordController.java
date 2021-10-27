package com.xjt.cloud.task.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.service.service.CheckRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ClassName CheckRecordController
 * @Author dwt
 * @Date 2019-07-26 11:49
 * @Description 巡检记录
 * @Version 1.0
 */
@RestController
@RequestMapping("/checkRecord")
public class CheckRecordController extends AbstractController {

    @Autowired
    private CheckRecordService checkRecordService;
    private String json;
    private HttpServletResponse resp;

    /**
     *  保存巡检记录
     *
     * @MethodName: saveCheckRecord
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/28 10:01
     **/
    @RequestMapping(value = "saveCheckRecord/{projectId}")
    public Data saveCheckRecord(String json, HttpServletRequest request) {
        return checkRecordService.saveCheckRecord(json,request);
    }

    /**
     *
     * 查询巡检记录列表
     * @Author huangGuiChuan
     * @Date 2019-12-04
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "/findCheckRecordList/{projectId}")
    public Data findCheckRecordList(String json) {
        return checkRecordService.findCheckRecordList(json);
    }

    /**@
     *  查询任务巡查点巡检记录
     *
     * MethodName: findTaskCheckPointCheckRecord
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/29 9:25
     **/
    @RequestMapping(value = "/findTaskCheckPointCheckRecord/{projectId}")
    public Data findTaskCheckPointCheckRecord(String json) {
        return checkRecordService.findTaskCheckPointCheckRecord(json);
    }

    /**
     *@Author: dwt
     *@Date: 2019-07-26 11:37
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 查询巡检记录详情
     */
    @RequestMapping(value = "findCheckRecordById/{projectId}")
    public Data findCheckRecordById(@PathVariable Long id) {
        return checkRecordService.findCheckRecordById(id);
    }

    /**
     *@Author: dwt
     *@Date: 2019-07-26 11:38
     *@Param: json
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 更新巡检记录
     */
    @RequestMapping(value = "/modifyCheckRecord/{projectId}")
    public Data modifyCheckRecord(String json) {
        return checkRecordService.modifyCheckRecord(json);
    }

    /**
     * PC巡检记录列表--导出表格功能
     *
     * @Author huanggc
     * @Date: 2019-08-13
     * @param json
     * @param resp HttpServletResponse
     * @return void
     */
    @RequestMapping(value = "/downCheckRecord/{projectId}")
    public void downCheckRecord(String json, HttpServletResponse resp) {
        checkRecordService.downCheckRecord(json, resp);
    }

    /**
     *@Author: dwt
     *@Date: 2019-08-14 16:51
     *@Param: java.lang.Long
     *@Return: 巡检详情
     *@Description 根据巡检记录id查询巡检信息
     */
    @RequestMapping(value = "/findCheckRecordDetails/{projectId}")
    public Data findCheckRecordDetails(String json){
        return checkRecordService.findCheckRecordDetails(json);
    }

    /**
     *@Author: dwt
     *@Date: 2019-08-15 16:37
     *@Param: java.lang.Long
     *@Return: 巡检详情
     *@Description App端根据巡检记录id查询已检设备巡检详情,根据任务id查询未检设备巡检详情
     */
    @RequestMapping(value = "findCheckRecordDetailsApp/{projectId}")
    public Data findCheckRecordDetailsApp(String json){
        return checkRecordService.findCheckRecordDetailsByIdApp(json);
    }

    /**
     * 删除, 批量删除巡查记录
     *
     * @param json
     * @Author huangGuiChuan
     * @Date 2020-01-16
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @RequestMapping(value = "/deletedCheckRecord/{projectId}")
    public Data deletedCheckRecord(String json) {
        return checkRecordService.deletedCheckRecord(json);
    }

    /**@MethodName: findProjectCurrentMonthFaultCheckRecordCount
     * @Description: 查询项目当月故障巡检记录数量
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/3/19 14:42
     **/
    @RequestMapping(value = "/findProjectCurrentMonthFaultCheckRecordCount")
    public Data findProjectCurrentMonthFaultCheckRecordCount(String json) {
        return checkRecordService.findProjectCurrentMonthFaultCheckRecordCount(json);
    }

    /**
     * 大屏--巡检管理
     *
     * @MethodName: findCheckRecordManage
     * @param json
     * @Author huanggc
     * @Date 2020-04-07
     * @Return: com.xjt.cloud.commons.utils.Data
     **/
    @RequestMapping(value = "/findCheckRecordManage")
    public Data findCheckRecordManage(String json) {
        return checkRecordService.findCheckRecordManage(json);
    }

    /**
     * 功能描述: 报表二级页面:如: C1
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: huanggc
     * @date: 2019/11/08
     */
    @RequestMapping(value = "/findReportItem/{projectId}")
    public Data findReportItem(String json){
        return checkRecordService.findReportItem(json);
    }

    /**
     * 报表查看 excel
     *
     * @Author huanggc
     * @Date: 2020-05-19
     * @param json
     * @param resp HttpServletResponse
     * @return
     */
    @RequestMapping(value = "/findReportExcelMetro/{projectId}")
    public Data findReportExcelMetro(String json) {
        return  checkRecordService.findReportExcelMetro(json);
    }

    /**
     * 11号线吸气式烟雾探测器检修记录表 QSZDY-K10149-07-B2
     *
     * @Author huanggc
     * @Date: 2020-04-29
     * @param json
     * @param resp HttpServletResponse
     * @return void
     */
    @RequestMapping(value = "/downSmokeCheckRecord/{projectId}")
    public void downSmokeCheckRecord(String json, HttpServletResponse resp) {
        checkRecordService.downSmokeCheckRecord(json, resp);
    }

    /**
     * 1QSZDY-K10149-08-B2
     *
     * @Author huanggc
     * @Date: 2020-04-29
     * @param json
     * @param resp HttpServletResponse
     * @return void
     */
    @RequestMapping(value = "/downInhaleSmokeTable/{projectId}")
    public void downInhaleSmokeTable(String json, HttpServletResponse resp) {
        checkRecordService.downInhaleSmokeTable(json, resp);
    }

    /**
     * 防灾报警系统报警功能测试记录表 QSZDYK1014909B2
     *
     * @Author huanggc
     * @Date: 2020-04-30
     * @param json
     * @param resp HttpServletResponse
     * @return void
     */
    @RequestMapping(value = "/downAlarmTable/{projectId}")
    public void downAlarmTable(String json, HttpServletResponse resp) {
        checkRecordService.downAlarmTable(json, resp);
    }

    /**
     * QSZDYK1014911B2
     *
     * @Author huanggc
     * @Date: 2020-05-14
     * @param json
     * @param resp HttpServletResponse
     * @return void
     */
    @RequestMapping(value = "/downDirectelEctricTable/{projectId}")
    public void downDirectelEctricTable(String json, HttpServletResponse resp) {
        checkRecordService.downDirectelEctricTable(json, resp);
    }

    /**
     * downReportExcelMetro
     *
     * @Author huanggc
     * @Date: 2020-05-14
     * @param json
     * @param resp HttpServletResponse
     * @return void
     */
    @RequestMapping(value = "/downReportExcelMetro/{projectId}")
    public void downReportExcelMetro(String json, HttpServletRequest request, HttpServletResponse resp) {
        checkRecordService.downReportExcelMetro(json, request, resp);
    }

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
    @RequestMapping(value = "/findFireControlList/{projectId}")
    public Data findFireControlList(String json, HttpServletRequest request, HttpServletResponse resp) {
        return checkRecordService.findFireControlList(json, request, resp);
    }

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
    @RequestMapping(value = "/findCheckRecordMetroList/{projectId}")
    public Data findCheckRecordMetroList(String json, HttpServletRequest request, HttpServletResponse resp) {
        return checkRecordService.findCheckRecordMetroList(json, request, resp);
    }

}
