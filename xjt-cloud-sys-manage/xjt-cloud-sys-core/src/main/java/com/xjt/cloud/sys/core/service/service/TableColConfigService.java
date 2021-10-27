package com.xjt.cloud.sys.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/10/31 10:13
 * @Description: 页面表格显示配置接口
 */
public interface TableColConfigService {
    /**
     *
     * 功能描述:查询用户项目表格配置信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/10/31 10:36
     */
    Data findTableColConfig(String json);

    /**
     *
     * 功能描述:保存用户项目表格配置信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/10/31 10:36
     */
    Data saveTableColConfig(String json);

    /**
     *
     * 功能描述:保存用户项目表格配置信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/10/31 10:36
     */
    Data modifyTableColConfig(String json);
}
