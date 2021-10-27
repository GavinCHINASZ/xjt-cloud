package com.xjt.cloud.admin.manage.controller.info;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.info.RecruitInfo;
import com.xjt.cloud.admin.manage.service.service.info.RecruitInfoService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/recruit/info/")
public class RecruitInfoController extends AbstractController {

    @Autowired
    private RecruitInfoService recruitInfoService;

    /**
     *
     * 功能描述:跳转到招聘信息管理页面
     *
     * @param
     * @return: org.springframework.web.servlet.ModelAndView
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @RequestMapping(value = "toRecruitInfoListPage")
    public ModelAndView toRecruitInfoListPage(){
        return new ModelAndView("info/recruitInfoList");
    }

    /**
     *
     * 功能描述:跳转到招聘信息管理页面
     *
     * @param
     * @return: org.springframework.web.servlet.ModelAndView
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @RequestMapping(value = "findRecruitInfoList")
    @ResponseBody
    public ScriptPage<RecruitInfo> findRecruitInfoList(AjaxPage ajaxPage, RecruitInfo recruitInfo){
        return recruitInfoService.findRecruitInfoList(ajaxPage, recruitInfo);
    }

    /**
     *
     * 功能描述:跳转到招聘信息管理页面
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @RequestMapping(value = "modifyRecruitInfo")
    @ResponseBody
    public Data modifyRecruitInfo(RecruitInfo recruitInfo){
        return recruitInfoService.modifyRecruitInfo(recruitInfo);
    }
}
