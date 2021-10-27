package com.xjt.cloud.task.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.service.service.CheckItemRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName CheckItemRecordController
 * @Author dwt
 * @Date 2019-07-26 14:15
 * @Description 巡检项记录
 * @Version 1.0
 */
@RestController
@RequestMapping("/checkItemRecord/")
public class CheckItemRecordController extends AbstractController {

    @Autowired
    private CheckItemRecordService checkItemRecordService;

    /**
     *@Author: dwt
     *@Date: 2019-07-26 14:08
     *@Param: json
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 查询巡检项记录列表
     */
    @RequestMapping("findCheckItemRecordList/{projectId}")
    public Data findCheckItemRecordList(String json) {
        return checkItemRecordService.findCheckItemRecordList(json);
    }

    /**
     *@Author: dwt
     *@Date: 2019-07-26 14:13
     *@Param: json
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 保存巡检项记录
     */
    public Data saveCheckItemRecord(String json) {
        return checkItemRecordService.saveCheckItemRecord(json);
    }

    /**
     * 地铁报表下载
     *
     *@Author: huanggc
     *@Date: 2020-05-11
     *@param json
     *@Return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping("downReportMetro/{projectId}")
    public Data downReportMetro(String json, HttpServletResponse response, HttpServletRequest request) {
        return checkItemRecordService.downReportMetro(json, response, request);
    }

    /**
     * 地铁站务--运营总部消防设施日常巡查记录表 Q/SZDY 0044-04-B3
     *
     *@Author: huanggc
     *@Date: 2020-06-02
     *@param json
     *@param response
     *@param request
     *@Return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping("findDailyPatrol/{projectId}")
    public Data findDailyPatrol(String json, HttpServletResponse response, HttpServletRequest request) {
        return checkItemRecordService.findDailyPatrol(json, response, request);
    }
}
