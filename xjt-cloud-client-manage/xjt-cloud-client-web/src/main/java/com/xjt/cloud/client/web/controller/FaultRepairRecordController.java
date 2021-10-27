package com.xjt.cloud.client.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.client.core.service.service.FaultRepairRecordService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 故障报修
 *
 * @Author huanggc
 * @Date 2020-03-25
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
     * @date 2020-03-25
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findFaultRepairRecordList/{appId}")
    public Data findFaultRepairRecordList(String json) {
        return repairRecordService.findFaultRepairRecordList(json);
    }

}
