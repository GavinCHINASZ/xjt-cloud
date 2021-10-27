package com.xjt.cloud.sys.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:48
 * @Description: 问题帮助管理接口
 */
public interface FaqsService {
    /**
     *
     * 功能描述:查询常见问题信息列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    Data findFaqsList(String json);

    /**
     *
     * 功能描述:查询常见问题信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    Data findFaqs(String json);

    /**
     *
     * 功能描述:新增常见问题信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    Data saveFaqs(String json);

    /**
     *
     * 功能描述:修改常见问题信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    Data modifyFaqs(String json);

    /**
     *
     * 功能描述:删除常见问题信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    Data delFaqs(String json);
}
