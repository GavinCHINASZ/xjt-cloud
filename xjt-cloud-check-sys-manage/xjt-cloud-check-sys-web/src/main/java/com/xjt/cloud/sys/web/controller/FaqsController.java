package com.xjt.cloud.sys.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.service.service.FaqsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:48
 * @Description: 问题帮助管理
 */
@RestController
@RequestMapping("/faqs/")
public class FaqsController extends AbstractController {

    @Autowired
    private FaqsService faqsService;

    /**
     *
     * 功能描述:查询常见问题信息列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    @RequestMapping(value = "findFaqsList")
    public Data findFaqsList(String json){
        return faqsService.findFaqsList(json);
    }

    /**
     *
     * 功能描述:查询常见问题信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    @RequestMapping(value = "findFaqs")
    public Data findFaqs(String json){
        return faqsService.findFaqs(json);
    }

    /**
     *
     * 功能描述:新增常见问题信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    @RequestMapping(value = "saveFaqs")
    public Data saveFaqs(String json){
        return faqsService.saveFaqs(json);
    }

    /**
     *
     * 功能描述:修改常见问题信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    @RequestMapping(value = "modifyFaqs")
    public Data modifyFaqs(String json){
        return faqsService.modifyFaqs(json);
    }

    /**
     *
     * 功能描述:删除常见问题信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    @RequestMapping(value = "delFaqs")
    public Data delFaqs(String json){
        return faqsService.delFaqs(json);
    }
}
