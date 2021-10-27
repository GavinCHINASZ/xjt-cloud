package com.xjt.cloud.report.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.report.core.service.service.DutyNoticeService;
import com.xjt.cloud.report.core.service.service.FaultLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zhangzaifa
 * @Date: 2020/05/18
 * @Description:  值班记录提醒
 */
@RestController
@RequestMapping("/report/dutyNotice")
public class DutyNoticeController extends AbstractController {

    @Autowired
    private DutyNoticeService dutyNoticeService;

    /**
     * @MethodName: saveDutyNotice
     * @Description: 保存值班提醒
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/19 16:44
     **/
    @RequestMapping(value = "saveDutyNotice/{projectId}")
    public Data saveDutyNotice(String json) {
        return dutyNoticeService.saveDutyNotice(json);
    }

    /**@MethodName: findDutyNotice
     * @Description: 查询值班提醒
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/19 16:44
     **/
    @RequestMapping(value = "findDutyNotice/{projectId}")
    public Data findDutyNotice(String json) {
        return dutyNoticeService.findDutyNotice(json);
    }

    /**@MethodName: sendDutyNoticeMsg
     * @Description: 发送值班提醒消息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/20 15:11
     **/
    @RequestMapping(value = "sendDutyNoticeMsg")
    public Data sendDutyNoticeMsg() {
        return dutyNoticeService.sendDutyNoticeMsg();
    }

}
