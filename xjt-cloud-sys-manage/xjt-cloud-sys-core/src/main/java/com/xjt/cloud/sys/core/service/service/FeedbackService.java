package com.xjt.cloud.sys.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:50
 * @Description:问题反馈管理接口
 */
public interface FeedbackService {

    /**
     *
     * 功能描述:查询问题反馈信息列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    Data findFeedbackList(String json);

    /**
     *
     * 功能描述:查询问题反馈信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    Data findFeedback(String json);

    /**
     *
     * 功能描述:新增问题反馈信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    Data saveFeedback(String json);

    /**
     *
     * 功能描述:修改问题反馈信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    Data modifyFeedback(String json);

    /**
     *
     * 功能描述:删除问题反馈信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    Data delFeedback(String json);
}
