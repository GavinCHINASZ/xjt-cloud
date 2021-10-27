package com.xjt.cloud.report.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.report.core.service.service.DutyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: huanggc
 * @Date: 2019/11/04
 * @Description: 报表
 */
@RestController
@RequestMapping("/report/dutyRecord")
public class DutyRecordController extends AbstractController {
    @Autowired
    private DutyRecordService dutyRecordService;

    /**@MethodName: save 保存值班记录信息
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/13 9:32 
     **/
    @RequestMapping(value = "save/{projectId}")
    public Data save(String json){
        return dutyRecordService.save(json);
    }


    /**@MethodName: findByDutyRecord 查询值班信息列表
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/13 9:33
     **/
    @RequestMapping(value = "findByDutyRecordList/{projectId}")
    public Data findByDutyRecordList(String json){
        return dutyRecordService.findByDutyRecordList(json);
    }

    /**
     * @Description 查询app首页值班信息信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "findUserProjectDutyRecordData/{projectId}")
    public JSONObject findUserProjectDutyRecordData(String json){
        return dutyRecordService.findUserProjectDutyRecordData(json);
    }

    /**@MethodName: findByDutyRecord 查询值班信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/13 9:33
     **/
    @RequestMapping(value = "findByDutyRecord/{projectId}")
    public Data findByDutyRecord(String json){
        return dutyRecordService.findByDutyRecord(json);
    }

    /**@MethodName: findByDataChart 查询数据图表
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/13 14:05 
     **/
    @RequestMapping(value = "findByDataChart/{projectId}")
    public Data findByDataChart(String json){
        return dutyRecordService.findByDataChart(json);
    }
}
