package com.xjt.cloud.project.core.service.service.device;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.entity.device.CheckRecord;
import com.xjt.cloud.project.core.entity.device.CheckReport;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/11 09:31
 * @Description:app检测
 */
public interface CheckRecordService {

    /**
     *
     * 功能描述:查询app检测列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Data findCheckPointList(String json);

    /**
     *
     * 功能描述:查询app检测列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Data findCheckRecordList(String json);

    /**
     *
     * 功能描述:新增app检测
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Data saveCheckRecord(String json);

    /**
     *
     * 功能描述:修改app检测
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Data modifyCheckRecord(String json);

    /**
     *
     * 功能描述:修改app检测
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Data modifyCheckPoint(String json);

    /**
     *@Author: dwt
     *@Date: 2020-04-11 15:14
     *@Param: java.lang.Long
     *@Return: java.util.List
     *@Description 查询所有检测情况统计列表
     */
    JSONObject findAllCheckRecordByProjectInfoId(Long id);

    /**
     *
     * 功能描述: 查询检测汇总报表
     *
     * @param checkRecord
     * @return: List<CheckReport>
     * @auther: wangzhiwen
     * @date: 2020/4/11 17:59
     */
    List<CheckReport> findCheckReport(CheckRecord checkRecord);
}
