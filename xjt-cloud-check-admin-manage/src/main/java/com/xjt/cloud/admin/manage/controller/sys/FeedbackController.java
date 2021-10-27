package com.xjt.cloud.admin.manage.controller.sys;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.sys.Feedback;
import com.xjt.cloud.admin.manage.service.service.sys.FeedbackService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:49
 * @Description: 问题反馈管理
 */
@Controller
@RequestMapping("/feedback/")
public class FeedbackController extends AbstractController {
    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping(value = "toFeedbackListPage")
    public ModelAndView toFeedbackListPage(){
        return new ModelAndView("app/feedbackList");
    }

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
    @RequestMapping(value = "findFeedbackList")
    @ResponseBody
    public ScriptPage<Feedback> findFeedbackList(AjaxPage ajaxPage, Feedback feedback){
        return feedbackService.findFeedbackList(ajaxPage, feedback);
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
    @RequestMapping(value = "modifyFeedback")
    @ResponseBody
    public Data modifyFeedback(Feedback feedback){
        return feedbackService.modifyFeedback(feedback);
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
    @RequestMapping(value = "delFeedback")
    @ResponseBody
    public Data delFeedback(Feedback feedback){
        return feedbackService.delFeedback(feedback);
    }
}
