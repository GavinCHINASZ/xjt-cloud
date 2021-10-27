package com.xjt.cloud.report.core.service.service;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: zhangzf
 * @Date: 2019/11/12
 * @Description: 值班记录
 */
public interface DutyRecordService {
    /**@MethodName: save 保存值班记录
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/12 14:24 
     **/
    Data save(String json);

    /**@MethodName: findByDutyRecord 查询值班信息列表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/13 9:33
     **/
    Data findByDutyRecordList(String json);

    /**
     * @Description 查询app首页值班信息信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject findUserProjectDutyRecordData(String json);
    /**@MethodName: findByDutyRecord 获取记录详情
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/13 10:29
     **/
    Data findByDutyRecord(String json);

    /**@MethodName: findByDataChart 查询数据图表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/13 14:05
     **/
    Data findByDataChart(String json);

    /**@MethodName: findByProjectMonthRecordCount
     * @Description: 查询项目月任务数
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/3/26 14:00
     **/
    Data findByProjectMonthRecordCount(String json);
}
