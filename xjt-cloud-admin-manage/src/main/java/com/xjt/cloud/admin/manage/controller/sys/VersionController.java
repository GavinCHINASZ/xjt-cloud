package com.xjt.cloud.admin.manage.controller.sys;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.sys.Version;
import com.xjt.cloud.admin.manage.service.service.sys.VersionService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:44
 * @Description: app版本管理
 */
@Controller
@RequestMapping("/version/")
public class VersionController extends AbstractController {

    @Autowired
    private VersionService versionService;

    /**
     *
     * 功能描述:跳转到版本管理页面
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/12/13 14:16
     */
    @RequestMapping(value = "toVersionListPage")
    public ModelAndView toVersionListPage(){
        return new ModelAndView("app/versionList");
    }

    /**
     *
     * 功能描述:查询版本列表
     *
     * @param ajaxPage
     * @param version
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @RequestMapping(value = "findVersionList")
    @ResponseBody
    public ScriptPage<Version> findVersionList(AjaxPage ajaxPage, Version version){
        return versionService.findVersionList(ajaxPage, version);
    }

    /**
     *
     * 功能描述:新增版本信息
     *
     * @param version
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @RequestMapping(value = "saveVersion")
    @ResponseBody
    public Data saveVersion(Version version){
        return versionService.saveVersion(version);
    }

    /**
     *
     * 功能描述:修改版本信息
     *
     * @param version
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @RequestMapping(value = "modifyVersion")
    @ResponseBody
    public Data modifyVersion(Version version){
        return versionService.modifyVersion(version);
    }

    /**
     *
     * 功能描述:删除版本信息
     *
     * @param version
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @RequestMapping(value = "delVersion")
    @ResponseBody
    public Data delVersion(Version version){
        return versionService.delVersion(version);
    }
}
