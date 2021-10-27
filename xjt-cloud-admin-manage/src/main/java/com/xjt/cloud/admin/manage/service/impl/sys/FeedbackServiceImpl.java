package com.xjt.cloud.admin.manage.service.impl.sys;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.sys.FeedbackDao;
import com.xjt.cloud.admin.manage.entity.sys.Feedback;
import com.xjt.cloud.admin.manage.service.service.sys.FeedbackService;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:50
 * @Description: 问题反馈管理接口实现
 */
@Service
public class FeedbackServiceImpl extends AbstractAdminService implements FeedbackService {
    @Autowired
    private FeedbackDao feedbackDao;

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
    @Override
    public ScriptPage<Feedback> findFeedbackList(AjaxPage ajaxPage, Feedback feedback){
        feedback = asseFindObj(feedback, ajaxPage);
        return asseScriptPage(feedbackDao.findFeedbackList(feedback), feedbackDao.findFeedbackListTotalCount(feedback));
    }

    /**
     *
     * 功能描述:修改问题反馈信息
     *
     * @param feedback
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    @Override
    public Data modifyFeedback(Feedback feedback){
        feedback.setHandleUserName(SecurityUserHolder.getUserName());
        feedback.setHandleTime(DateUtils.getDate());
        int num = feedbackDao.modifyFeedback(feedback);
        return asseData(num);
    }

    /**
     *
     * 功能描述:删除问题反馈信息
     *
     * @param feedback
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    @Override
    public Data delFeedback(Feedback feedback){
        return modifyFeedback(feedback);
    }
}
