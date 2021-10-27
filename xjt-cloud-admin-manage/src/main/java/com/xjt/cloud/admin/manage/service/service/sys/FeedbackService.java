package com.xjt.cloud.admin.manage.service.service.sys;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.sys.Feedback;
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
     * @param ajaxPage
     * @param feedback
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    ScriptPage<Feedback> findFeedbackList(AjaxPage ajaxPage, Feedback feedback);

    /**
     *
     * 功能描述:修改问题反馈信息
     *
     * @param feedback
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    Data modifyFeedback(Feedback feedback);

    /**
     *
     * 功能描述:删除问题反馈信息
     *
     * @param feedback
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    Data delFeedback(Feedback feedback);
}
