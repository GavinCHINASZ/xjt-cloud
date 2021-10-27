package com.xjt.cloud.sys.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/16 11:34
 * @Description: 平台信息初使化管理接口
 */
public interface CloudInitService {
    /**
     *
     * 功能描述:平台信息初使化列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    Data findCloudInitList(String json);

    /**
     *
     * 功能描述:平台信息初使化列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    Data findCloudInitChildList(String json);
}
