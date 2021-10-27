package com.xjt.cloud.admin.manage.dao.sys;

import com.xjt.cloud.admin.manage.entity.sys.Feedback;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:50
 * @Description: 问题反馈管理DAO
 */
@Repository
public interface FeedbackDao {
    /**
     *
     * 功能描述:查询问题反馈信息列表
     *
     * @param feedback
     * @return: List<Feedback>
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    List<Feedback> findFeedbackList(Feedback feedback);

    /**
     *
     * 功能描述:查询问题反馈信息列表
     *
     * @param feedback
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    int findFeedbackListTotalCount(Feedback feedback);

    /**
     *
     * 功能描述:查询问题反馈信息
     *
     * @param feedback
     * @return: Feedback
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    Feedback findFeedback(Feedback feedback);

    /**
     *
     * 功能描述:新增问题反馈信息
     *
     * @param feedback
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    int saveFeedback(Feedback feedback);

    /**
     *
     * 功能描述:修改问题反馈信息
     *
     * @param feedback
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    int modifyFeedback(Feedback feedback);
}
