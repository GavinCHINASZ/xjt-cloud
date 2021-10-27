package com.xjt.cloud.admin.manage.controller.sys;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.sys.Faqs;
import com.xjt.cloud.admin.manage.service.service.sys.FaqsService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:48
 * @Description: 问题帮助管理
 */
@Controller
@RequestMapping("/faqs/")
public class FaqsController extends AbstractController {

    @Autowired
    private FaqsService faqsService;

    @RequestMapping(value = "toFaqsListPage")
    public ModelAndView toFaqsListPage(){
        return new ModelAndView("sys/faqsList");
    }

    /**
     *
     * 功能描述:查询常见问题信息列表
     *
     * @param ajaxPage
     * @param faqs
     * @return: ScriptPage<Faqs>
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    @RequestMapping(value = "findFaqsList")
    @ResponseBody
    public ScriptPage<Faqs> findFaqsList(AjaxPage ajaxPage, Faqs faqs){
        return faqsService.findFaqsList(ajaxPage, faqs);
    }

    /**
     *
     * 功能描述:新增常见问题信息
     *
     * @param faqs
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    @RequestMapping(value = "saveFaqs")
    @ResponseBody
    public Data saveFaqs(Faqs faqs){
        return faqsService.saveFaqs(faqs);
    }

    /**
     *
     * 功能描述:修改常见问题信息
     *
     * @param faqs
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    @RequestMapping(value = "modifyFaqs")
    @ResponseBody
    public Data modifyFaqs(Faqs faqs){
        return faqsService.modifyFaqs(faqs);
    }

    /**
     *
     * 功能描述:删除常见问题信息
     *
     * @param faqs
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    @RequestMapping(value = "delFaqs")
    @ResponseBody
    public Data delFaqs(Faqs faqs){
        return faqsService.delFaqs(faqs);
    }
}
