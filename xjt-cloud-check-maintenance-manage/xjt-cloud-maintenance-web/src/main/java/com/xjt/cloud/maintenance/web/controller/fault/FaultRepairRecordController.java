package com.xjt.cloud.maintenance.web.controller.fault;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.service.service.fault.FaultRepairRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 故障报修Controller
 *
 * @author huanggc
 * @date 2021/04/22
 **/
@RestController
@RequestMapping("/fault/")
public class FaultRepairRecordController extends AbstractController {
    @Autowired
    private FaultRepairRecordService repairRecordService;

    /**
     * 功能描述: 新建故障报修
     *
     * @param json 封装的数据
     * @author huanggc
     * @date 2021/04/22
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveFaultRepairRecord/{projectId}")
    public Data saveFaultRepairRecord(String json) {
        return repairRecordService.saveFaultRepairRecord(json);
    }

    /**
     * 功能描述:查询 故障报修 列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/02
     */
    @RequestMapping(value = "findFaultRepairRecordList/{projectId}")
    public Data findFaultRepairRecordList(String json) {
        return repairRecordService.findFaultRepairRecordList(json);
    }

    /**
     * 功能描述:查询 故障报修
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/02
     */
    @RequestMapping(value = "findFaultRepairRecord/{projectId}")
    public Data findFaultRepairRecord(String json) {
        return repairRecordService.findFaultRepairRecord(json);
    }

    /**
     * 功能描述:查询 故障报修类型
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/02
     */
    @RequestMapping(value = "findFaultType/{projectId}")
    public Data findFaultType(String json) {
        return repairRecordService.findFaultType(json);
    }

    /**
     * 功能描述:更新故障报修
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/02
     */
    @RequestMapping(value = "updateFaultRepairRecord/{projectId}")
    public Data updateFaultRepairRecord(String json) {
        return repairRecordService.updateFaultRepairRecord(json);
    }

    /**
     * 功能描述:查询 日志/记录 列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/22
     */
    @RequestMapping(value = "findRepairProgressList/{projectId}")
    public Data findRepairProgressList(String json) {
        return repairRecordService.findRepairProgressList(json);
    }

}
