package com.xjt.cloud.maintenance.core.service.service.device;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.entity.device.CheckReport;

import java.util.List;

/**
 * app检测
 *
 * @author wangzhiwen
 * @date 2020/4/11 09:31
 */
public interface CheckRecordService {

    /**
     * 功能描述:查询app检测列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    Data findCheckPointList(String json);

    /**
     * 功能描述:查询app检测列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    Data findCheckRecordList(String json);

    /**
     * 功能描述:新增app检测
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    Data saveCheckRecord(String json);

    /**
     * 功能描述:修改app检测
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    Data modifyCheckRecord(String json);

    /**
     * 功能描述:修改app检测
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    Data modifyCheckPoint(String json);

    /**
     * 查询所有检测情况统计列表
     *
     * @author dwt
     * @date 2020-04-11 15:14
     * @param id Long
     * @return JSONObject
     */
    JSONObject findAllCheckRecordByProjectInfoId(Long id);

    /**
     * 功能描述:查询 列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/21
     */
    Data findCheckPointSysList(String json);

    /**
     * 功能描述: 查询检测汇总报表
     *
     * @param checkProjectId 项目id
     * @return List<CheckReport>
     * @author wangzhiwen
     * @date 2020/4/11 17:59
     */
    List<CheckReport> findCheckReport(Long checkProjectId);

    /**
     * 保存 CheckPoint
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/21
     */
    Data saveCheckPoint(String json);
}
