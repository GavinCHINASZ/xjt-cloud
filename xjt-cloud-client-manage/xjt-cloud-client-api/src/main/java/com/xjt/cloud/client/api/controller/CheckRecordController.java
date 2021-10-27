package com.xjt.cloud.client.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.client.core.service.service.CheckRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  巡查记录 Controller
 *
 * @Author huanggc
 * @Date 2020-03-25
 * @Version 1.0
 */
@RestController
@RequestMapping("/checkRecord")
public class CheckRecordController extends AbstractController {
    @Autowired
    private CheckRecordService checkRecordService;

    /**
     * 查询巡检记录列表
     *
     * @Author huanggc
     * @Date 2020-03-25
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findCheckRecordList/{projectId}")
    public Data findCheckRecordList(String json) {
        return checkRecordService.findCheckRecordList(json);
    }


}
