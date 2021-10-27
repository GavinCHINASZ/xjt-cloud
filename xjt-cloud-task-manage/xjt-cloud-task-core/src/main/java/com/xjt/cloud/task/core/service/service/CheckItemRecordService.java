package com.xjt.cloud.task.core.service.service;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *@ClassName CheckItemRecord
 *@Author dwt
 *@Date 2019-07-26 13:41
 *@Description 巡检项记录
 *@Version 1.0
 */
public interface CheckItemRecordService {

    /**
     *@Author: dwt
     *@Date: 2019-07-25 18:16
     *@Param: json
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description: 查询巡检项记录列表
     */
    Data findCheckItemRecordList(String json);

    /**
     *@Author: dwt
     *@Date: 2019-07-25 18:17
     *@Param: com.xjt.cloud.commons.utils.Data
     *@Return: 巡检记录
     *@Description: 根据主键id查询巡检记录
     */
    Data findCheckItemRecordById(Long id);

    /**
     *@Author: dwt
     *@Date: 2019-07-25 18:18
     *@Param: json
     *@Return: 主键id
     *@Description: 保存巡检记录
     */
    Data saveCheckItemRecord(String json);

    /**
     * 地铁报表下载
     *
     *@Author: huanggc
     *@Date: 2020-05-11
     *@param json
     *@Return: com.xjt.cloud.commons.utils.Data
     */
    Data downReportMetro(String json, HttpServletResponse response, HttpServletRequest request);

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
    Data findDailyPatrol(String json, HttpServletResponse response, HttpServletRequest request);
}
