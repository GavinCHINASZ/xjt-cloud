package com.xjt.cloud.admin.manage.controller.info;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.info.InfoContent;
import com.xjt.cloud.admin.manage.service.service.info.InfoManageService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/8/3 14:40
 * @Description: 资讯信息初使化信息配置接口
 */
@Controller
@RequestMapping("/info/manage/")
public class InfoManageController extends AbstractController {
    @Autowired
    private InfoManageService infoManageService;

    /**
     *
     * 功能描述:跳转到资讯信息管理页面
     *
     * @param
     * @return: org.springframework.web.servlet.ModelAndView
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @RequestMapping(value = "toInfoManageListPage")
    public ModelAndView toInfoManageListPage(){
        return new ModelAndView("info/infoManageList");
    }

    /**
     *
     * 功能描述:查询资讯信息管理列表
     *
     * @param
     * @return: org.springframework.web.servlet.ModelAndView
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @RequestMapping(value = "findInfoManageList")
    @ResponseBody
    public ScriptPage<InfoContent> findInfoManageList(AjaxPage ajaxPage, InfoContent infoContent){
        return infoManageService.findInfoManageList(ajaxPage, infoContent);
    }

    /**
     *
     * 功能描述:保存资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @RequestMapping(value = "saveInfoManage")
    @ResponseBody
    public Data saveInfoManage(InfoContent infoContent){
        return infoManageService.saveInfoManage(infoContent);
    }

    /**
     *
     * 功能描述:修改资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @RequestMapping(value = "modifyInfoManage")
    @ResponseBody
    public Data modifyInfoManage(InfoContent infoContent){
        return infoManageService.modifyInfoManage(infoContent);
    }

    /**
     *
     * 功能描述:删除资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @RequestMapping(value = "delInfoManage")
    @ResponseBody
    public Data delInfoManage(InfoContent infoContent){
        return infoManageService.delInfoManage(infoContent);
    }

    ///////////////////////////////////////资讯信息内容/////////////////////////////////////

    /**
     *
     * 功能描述:查询资讯信息管理列表
     *
     * @param
     * @return: org.springframework.web.servlet.ModelAndView
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @RequestMapping(value = "findInfoContentList")
    @ResponseBody
    public ScriptPage<InfoContent> findInfoContentList(AjaxPage ajaxPage, InfoContent infoContent){
        return infoManageService.findInfoContentList(ajaxPage, infoContent);
    }

    /**
     *
     * 功能描述:保存资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @RequestMapping(value = "saveInfoContent")
    @ResponseBody
    public Data saveInfoContent(InfoContent infoContent){
        return infoManageService.saveInfoContent(infoContent);
    }

    /**
     *
     * 功能描述:修改资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @RequestMapping(value = "modifyInfoContent")
    @ResponseBody
    public Data modifyInfoContent(InfoContent infoContent){
        return infoManageService.modifyInfoContent(infoContent);
    }

    /**
     *
     * 功能描述:删除资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @RequestMapping(value = "delInfoContent")
    @ResponseBody
    public Data delInfoContent(InfoContent infoContent){
        return infoManageService.delInfoContent(infoContent);
    }
}
