package com.xjt.cloud.safety.api.controller.device;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.safety.core.service.service.device.CheckRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 巡查记录
 * 
 * @author wangzhiwen
 * @date 2020/4/11 09:29
 */
@RestController
@RequestMapping("/check/record/")
public class CheckRecordController extends AbstractController {
    @Autowired
    private CheckRecordService checkRecordService;

    /**
     * @Description 查询设备系统评估汇总信息列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/11 18:01
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findCheckRecordDeviceSysCountReport")
    public Data findCheckRecordDeviceSysCountReport(String json){
        return checkRecordService.findCheckRecordDeviceSysCountReport(json);
    }

    /**
     * @Description 查询设备类型评估汇总信息列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/11 18:01
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findCheckRecordDeviceTypeCountReport")
    public Data findCheckRecordDeviceTypeCountReport(String json){
        return checkRecordService.findCheckRecordDeviceTypeCountReport(json);
    }

    /**
     * @Description 查询项目评估汇总信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/11 18:01
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findCheckRecordTotalReport")
    public Data findCheckRecordTotalReport(String json){
        return checkRecordService.findCheckRecordTotalReport(json);
    }

    /**
     * 功能描述:查询app检测列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/19 9:28
     */
    @RequestMapping(value = "findCheckRecordList")
    public Data findCheckRecordList(String json) {
        return checkRecordService.findCheckRecordList(json);
    }

    /**
     * 功能描述:新增app检测
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    @RequestMapping(value = "saveCheckRecord")
    public Data saveCheckRecord(String json) {
        return checkRecordService.saveCheckRecord(json);
    }

    /**
     * 功能描述:修改app检测
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    @RequestMapping(value = "modifyCheckRecord")
    public Data modifyCheckRecord(String json) {
        return checkRecordService.modifyCheckRecord(json);
    }
    
    /**
     * 功能描述: 删除app检测
     *
     * @param json 参数
     * @return Data
     * @author wangzhiwen
     * @date 2019/8/13 9:22
     */
    @RequestMapping(value = "delCheckRecord")
    public Data delCheckRecord(String json) {
        return checkRecordService.modifyCheckRecord(json);
    }

    /*--------------------------CheckPoint-----------------------------*/
    /**
     * 功能描述:查询app检测列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/19 9:28
     */
    @RequestMapping(value = "findCheckPointList")
    public Data findCheckPointList(String json) {
        return checkRecordService.findCheckPointList(json);
    }

    /**
     * 功能描述:查询app检测列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/19 9:28
     */
    @RequestMapping(value = "findCheckPointList/{projectId}")
    public Data findCheckPointLists(String json) {
        return checkRecordService.findCheckPointList(json);
    }

    /**
     * 功能描述:查询 列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/21
     */
    @RequestMapping(value = "findCheckPointSysList/{projectId}")
    public Data findCheckPointSysList(String json) {
        return checkRecordService.findCheckPointSysList(json);
    }

    /**
     * 功能描述:修改app检测信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    @RequestMapping(value = "modifyCheckPoint")
    public Data modifyCheckPoint(String json) {
        return checkRecordService.modifyCheckPoint(json);
    }

    /**
     * 功能描述:修改app检测信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    @RequestMapping(value = "modifyCheckPoint/{projectId}")
    public Data modifyCheckPoints(String json) {
        return checkRecordService.modifyCheckPoint(json);
    }

    /**
     * 保存 CheckPoint
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/21
     */
    @RequestMapping(value = "saveCheckPoint/{projectId}")
    public Data saveCheckPoint(String json) {
        return checkRecordService.saveCheckPoint(json);
    }
}
