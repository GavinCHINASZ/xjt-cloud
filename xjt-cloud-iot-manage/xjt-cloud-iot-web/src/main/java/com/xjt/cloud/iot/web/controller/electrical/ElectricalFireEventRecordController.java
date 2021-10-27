package com.xjt.cloud.iot.web.controller.electrical;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.electrical.ElectricalFireEventRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ElectricalFireEventRecordController
 * @Author dwt
 * @Date 2020-09-29 15:01
 * @Version 1.0
 */
@RestController
@RequestMapping("/electrical/record")
public class ElectricalFireEventRecordController extends AbstractController {

    @Autowired
    private ElectricalFireEventRecordService eventRecordService;


    /**
     *@Author: dwt
     *@Date: 2020-09-29 15:02
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 查询电气火灾设备事件记录数据分析列表
     */
    @RequestMapping(value = "findDeviceEventRecordList/{projectId}")
    public Data findDeviceEventRecordList(String json) {
        return eventRecordService.findDeviceEventRecordList(json);
    }

    /**
     *@Author: dwt
     *@Date: 2020-09-29 14:30
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 查询电气火灾设备最新事件记录
     */
    @RequestMapping(value = "findNewestEventRecord/{projectId}")
    public Data findNewestEventRecord(String json) {
        return eventRecordService.findNewestEventRecord(json);
    }
}
