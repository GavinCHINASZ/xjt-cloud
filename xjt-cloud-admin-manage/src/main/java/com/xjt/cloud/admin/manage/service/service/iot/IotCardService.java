package com.xjt.cloud.admin.manage.service.service.iot;

/**
 * @ClassName IotCardService
 * @Description 物联网卡管理
 * @Author wangzhiwen
 * @Date 2020/9/9 14:14
 **/
public interface IotCardService {
    /**
     * @return
     * @Description 移动平台物联网卡状态定时任务
     * @author wangzhiwen
     * @date 2020/9/8 15:54
     */
    void iotCardStatusOneLinkTask();

    /**
     * @return
     * @Description 移动平台物联网卡流量查询定时任务
     * @author wangzhiwen
     * @date 2020/9/8 15:54
     */
    void iotCardFlowOneLinkTask();

    /**
     * @return
     * @Description 移动平台物联网卡余额查询定时任务
     * @author wangzhiwen
     * @date 2020/9/8 15:54
     */
    void iotCardBalanceOneLinkTask();
}
