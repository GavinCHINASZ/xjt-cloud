package com.xjt.cloud.fault.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.fault.core.service.service.FaultRepairRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 故障报修 控制类
 *
 * @author huanggc
 * @date 2019/09/02
 **/
@RestController
@RequestMapping("/faultRepairRecord")
public class FaultRepairRecordController extends AbstractController {
    @Autowired
    private FaultRepairRecordService repairRecordService;

    /**
     * 功能描述:查询 故障报修 列表
     *
     * @param json
     * @author huanggc
     * @date  2019/09/02
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findFaultRepairRecordListByAppId/{appId}")
    public Data findFaultRepairRecordListByAppId(String json) {
        return repairRecordService.findFaultRepairRecordList(json);
    }

    /**
     * 功能描述:查询 故障报修 列表
     *
     * @param json
     * @author huanggc
     * @date  2019/09/02
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findFaultRepairRecordList/{projectId}")
    public Data findFaultRepairRecordList(String json) {
        return repairRecordService.findFaultRepairRecordList(json);
    }

    /**
     * 功能描述:查询 故障报修 各状态数量
     *
     * @param json
     * @author huanggc
     * @date  2019/09/02
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findFaultRepairRecords/{projectId}")
    public Data findFaultRepairRecords(String json) {
        return repairRecordService.findFaultRepairRecords(json);
    }
    /**
     * 功能描述: 新建故障报修(微信)
     *
     * @param json 封装的数据
     * @author huanggc
     * @date  2019/09/12
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveFaultRepairRecords/{projectId}")
    public Data saveFaultRepairRecords(String json) {
        return repairRecordService.saveFaultRepairRecords(json);
    }

    /**
     * 功能描述:故障报修 导出表格功能
     *
     * @param json
     * @author huanggc
     * @date  2019/09/04
     */
    @RequestMapping(value = "downFaultRepairRecord/{projectId}")
    public void downFaultRepairRecord(String json, HttpServletResponse resp) {
        repairRecordService.downFaultRepairRecord(json, resp);
    }

    /**
     * 功能描述: 报表GB25201--B1导出方法
     *
     * @param json
     * @param resp HttpServletResponse
     * @author huanggc
     * @date  2020-04-09
     * @return Data
     */
    @RequestMapping(value = "downWordB1/{projectId}")
    public void downWordB1(String json, HttpServletResponse resp) {
        repairRecordService.downWordB1(json, resp);
    }

    /**
     * 功能描述:故障报修 删除功能
     *
     * @param json
     * @author huanggc
     * @date  2019/09/04
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "deletedFaultRepairRecord/{projectId}")
    public Data deletedFaultRepairRecord(String json) {
        return repairRecordService.deletedFaultRepairRecord(json);
    }

    /**
     * 功能描述:点击"工单号"查看报修进度
     *
     * @param json
     * @author huanggc
     * @date  2019/09/09
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findFaultProgress/{projectId}")
    public Data findFaultProgress(String json) {
        return repairRecordService.findFaultProgress(json);
    }

    /**
     * 功能描述:报修工单进度更新
     *
     * @param json
     * @author huanggc
     * @date  2019/09/09
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "updateFaultProgress/{projectId}")
    public Data updateFaultProgress(String json) {
        return repairRecordService.updateFaultProgress(json);
    }

    /**
     * 功能描述: APP保存继续维修(临时保存)
     *
     * @param json
     * @author huanggc
     * @date  2020-03-27
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "updateFaultRepair/{projectId}")
    public Data updateFaultRepair(String json) {
        return repairRecordService.updateFaultRepair(json);
    }

    /**
     * 功能描述: 故障处理人(项目中的成员)
     *
     * @param json
     * @author huanggc
     * @date  2019/10/28
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "faultUser/{projectId}")
    public Data faultUser(String json) {
        return repairRecordService.faultUser(json);
    }

    /**
     * 功能描述: 项目主页 数量  大屏--故障报修数量统计
     *
     * @param json
     * @author huanggc
     * @date  2020-03-19
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findProjectCount")
    public Data findProjectCount(String json) {
        return repairRecordService.findProjectCount(json);
    }

    /**
     * 功能描述: 大屏--故障报修列表
     *
     * @param json
     * @author huanggc
     * @date  2020-03-19
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findScreenList")
    public Data findScreenList(String json) {
        return repairRecordService.findScreenList(json);
    }

    /**
     * 功能描述: 地铁大屏--故障报修列表
     *
     * @param json
     * @author huanggc
     * @date  2020-05-13
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findMetroScreenList")
    public Data findMetroScreenList(String json) {
        return repairRecordService.findMetroScreenList(json);
    }

    /** 
     * 查询故障分析数据
     * 
     * @param json
     * @return JSONObject
     * @author zhangZaiFa
     * @date 2020/5/13 15:13
     **/
    @RequestMapping(value = "findFaultRepairAnalysis")
    public Data findFaultRepairAnalysis(String json) {
        return repairRecordService.findFaultRepairAnalysis(json);
    }

}
