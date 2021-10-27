package com.xjt.cloud.fault.controller;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.fault.core.service.service.FaultRepairRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 故障报修
 *
 * @Author huanggc
 * @Date 2019/09/02
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
     * @auther huanggc
     * @date 2019/09/02
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findFaultRepairRecordList/{projectId}")
    public Data findFaultRepairRecordList(String json) {
        return repairRecordService.findFaultRepairRecordList(json);
    }

    /**
     * 功能描述:查询 故障报修 单个
     *
     * @param json
     * @auther huanggc
     * @date 2020-03-27
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findFaultRepairRecord/{projectId}")
    public Data findFaultRepairRecord(String json) {
        return repairRecordService.findFaultRepairRecord(json);
    }

    /**
     * 功能描述:点击"工单号"查看报修进度
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/09
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findFaultProgress/{projectId}")
    public Data findFaultProgress(String json) {
        return repairRecordService.findFaultProgress(json);
    }

    /**
     * 功能描述:报修工单进度更新
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/09
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "updateFaultProgress/{projectId}")
    public Data updateFaultProgress(String json) {
        return repairRecordService.updateFaultProgress(json);
    }


    /**
     * 功能描述: APP保存继续维修(临时保存)
     *
     * @param json
     * @auther: huanggc
     * @date: 2020-03-27
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "updateFaultRepair/{projectId}")
    public Data updateFaultRepair(String json) {
        return repairRecordService.updateFaultRepair(json);
    }

    /**
     * 功能描述: 新建故障报修
     *
     * @param json 封装的数据
     * @auther: huanggc
     * @date: 2019/09/12
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveFaultRepairRecord/{projectId}")
    public Data saveFaultRepairRecord(String json) {
        return repairRecordService.saveFaultRepairRecord(json);
    }

    /**
     * 功能描述: 新建故障报修(微信)
     *
     * @param json 封装的数据
     * @auther: huanggc
     * @date: 2019/09/12
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveFaultRepairRecords/{projectId}")
    public Data saveFaultRepairRecords(String json) {
        return repairRecordService.saveFaultRepairRecords(json);
    }

    /**
     * 功能描述: 故障处理人(项目中的成员)
     *
     * @param json
     * @auther: huanggc
     * @date 2020/02/20
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "faultUser/{projectId}")
    public Data faultUser(String json) {
        return repairRecordService.faultUser(json);
    }

    /**
     * @Description 查询app首页故障报修信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "findUserProjectFaultRepairData/{projectId}")
    public JSONObject findUserProjectFaultRepairData(String json){
        return repairRecordService.findUserProjectFaultRepairData(json);
    }
}
