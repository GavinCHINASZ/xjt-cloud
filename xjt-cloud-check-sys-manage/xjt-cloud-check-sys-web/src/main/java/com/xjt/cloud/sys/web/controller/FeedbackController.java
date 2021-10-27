package com.xjt.cloud.sys.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.service.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:49
 * @Description: 问题反馈管理
 */
@RestController
@RequestMapping("/feedback/")
public class FeedbackController extends AbstractController {
    @Autowired
    private FeedbackService feedbackService;

    /**
     *
     * 功能描述:查询问题反馈信息列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:47
     */
    @RequestMapping(value = "findFeedbackList")
    public Data findFeedbackList(String json){
        return feedbackService.findFeedbackList(json);
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
    @RequestMapping(value = "findFeedback")
    public Data findFeedback(String json){
        return feedbackService.findFeedback(json);
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
    @RequestMapping(value = "saveFeedback")
    public Data saveFeedback(String json){
        return feedbackService.saveFeedback(json);
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
    @RequestMapping(value = "modifyFeedback")
    public Data modifyFeedback(String json){
        return feedbackService.modifyFeedback(json);
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
    @RequestMapping(value = "delFeedback")
    public Data delFeedback(String json){
        return feedbackService.delFeedback(json);
    }
}
