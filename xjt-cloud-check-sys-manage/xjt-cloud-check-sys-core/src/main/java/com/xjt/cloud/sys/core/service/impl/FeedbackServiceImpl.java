package com.xjt.cloud.sys.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.dao.sys.FeedbackDao;
import com.xjt.cloud.sys.core.entity.Feedback;
import com.xjt.cloud.sys.core.service.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:50
 * @Description: 问题反馈管理接口实现
 */
@Service
public class FeedbackServiceImpl extends AbstractService implements FeedbackService {
    @Autowired
    private FeedbackDao feedbackDao;

    /**
     *
     * 功能描述:查询问题反馈信息列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    @Override
    public Data findFeedbackList(String json){
        Feedback feedback = JSONObject.parseObject(json, Feedback.class);
        Integer totalCount = feedback.getTotalCount();
        Integer pageSize = feedback.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){//判断是否存在总数，如没有，则查询总记录数
            totalCount = feedbackDao.findFeedbackListTotalCount(feedback);
        }
        List<Feedback> list = feedbackDao.findFeedbackList(feedback);
        return asseData(totalCount, list);
    }

    /**
     *
     * 功能描述:查询问题反馈信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    @Override
    public Data findFeedback(String json){
        Feedback feedback = JSONObject.parseObject(json, Feedback.class);
        feedback = feedbackDao.findFeedback(feedback);
        return asseData(feedback);
    }

    /**
     *
     * 功能描述:新增问题反馈信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    @Override
    public Data saveFeedback(String json){
        Feedback feedback = JSONObject.parseObject(json, Feedback.class);
        String loginName = SecurityUserHolder.getUserName();
        feedback.setCreateUserId(getLoginUserId(loginName));
        feedback.setCreateUserName(getLoginUserName(loginName));
        int num = feedbackDao.saveFeedback(feedback);
        return asseData(num);
    }

    /**
     *
     * 功能描述:修改问题反馈信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    @Override
    public Data modifyFeedback(String json){
        Feedback feedback = JSONObject.parseObject(json, Feedback.class);
        int num = feedbackDao.modifyFeedback(feedback);
        return asseData(num);
    }

    /**
     *
     * 功能描述:删除问题反馈信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    @Override
    public Data delFeedback(String json){
        Feedback feedback = JSONObject.parseObject(json, Feedback.class);
        int num = feedbackDao.modifyFeedback(feedback);
        return asseData(num);
    }
}
