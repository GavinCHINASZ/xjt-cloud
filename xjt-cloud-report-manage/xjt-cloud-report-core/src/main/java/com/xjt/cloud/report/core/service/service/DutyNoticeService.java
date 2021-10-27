package com.xjt.cloud.report.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 * @program: xjt-cloud-report-manage
 * @description:值班记录提醒
 * @author: zxb
 * @create: 2020-05-19 16:46
 **/
public interface DutyNoticeService {

    /**@MethodName: saveDutyNotice
     * @Description: 保存值班记录提醒
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/19 16:47
     **/
    Data saveDutyNotice(String json);

    /**@MethodName: findDutyNotice
     * @Description: 查询值班记录提醒
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/19 16:47
     **/
    Data findDutyNotice(String json);

    /**@MethodName: sendDutyNoticeMsg
     * @Description: 发送值班提醒消息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/20 15:11
     **/
    Data sendDutyNoticeMsg();
}
