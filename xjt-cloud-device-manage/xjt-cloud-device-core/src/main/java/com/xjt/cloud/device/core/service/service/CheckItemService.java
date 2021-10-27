package com.xjt.cloud.device.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:30
 * @Description:巡检项管理接口
 */
public interface CheckItemService {
    /**
     *
     * 功能描述:查询设备巡检项列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    Data findCheckItemList(String json);
}
