package com.xjt.cloud.project.api.controller.device;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.device.CheckRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/11 09:29
 * @Description:
 */
@RestController
@RequestMapping("/check/record/")
public class CheckRecordController extends AbstractController {
    @Autowired
    private CheckRecordService checkRecordService;

    /**
     *
     * 功能描述:查询app检测列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @RequestMapping(value = "findCheckPointList")
    public Data findCheckPointList(String json){
        return checkRecordService.findCheckPointList(json);
    }

    /**
     *
     * 功能描述:查询app检测列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @RequestMapping(value = "findCheckRecordList")
    public Data findCheckRecordList(String json){
        return checkRecordService.findCheckRecordList(json);
    }

    /**
     *
     * 功能描述:新增app检测
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @RequestMapping(value = "saveCheckRecord")
    public Data saveCheckRecord(String json){
        return checkRecordService.saveCheckRecord(json);
    }

    /**
     *
     * 功能描述:修改app检测
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @RequestMapping(value = "modifyCheckRecord")
    public Data modifyCheckRecord(String json){
        return checkRecordService.modifyCheckRecord(json);
    }
    /**
     *
     * 功能描述:修改app检测信息
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @RequestMapping(value = "modifyCheckPoint")
    public Data modifyCheckPoint(String json){
        return checkRecordService.modifyCheckPoint(json);
    }

    /**
     *
     * 功能描述: 删除app检测
     *
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/8/13 9:22
     */
    @RequestMapping(value = "delCheckRecord")
    public Data delCheckRecord(String json){
        return checkRecordService.modifyCheckRecord(json);
    }
}
