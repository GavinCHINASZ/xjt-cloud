package com.xjt.cloud.maintenance.core.service.service.device;

import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/11 09:30
 * @Description: pc检测项配置
 */
public interface CheckItemConfigService {

    /**
     *
     * 功能描述:查询pc检测项配置信息列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Data findCheckResult(String json);



    /**
     *
     * 功能描述:查询pc检测项配置信息列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Data findCheckItemConfigList(String json);

    /**
     *
     * 功能描述:新增pc检测项配置信息
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Data saveCheckItemConfig(String json);

    /**
     *
     * 功能描述:修改pc检测项配置信息
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Data modifyCheckItemConfig(String json);
}
