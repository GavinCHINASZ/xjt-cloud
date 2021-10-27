package com.xjt.cloud.admin.manage.controller.sys;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.sys.CloudInit;
import com.xjt.cloud.admin.manage.service.service.sys.CloudInitService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/16 11:35
 * @Description: 平台信息初使化管理controller
 */
@Controller
@RequestMapping("/cloud/init/")
public class CloudInitController extends AbstractController {
    @Autowired
    private CloudInitService cloudInitService;

    /**
     *
     * 功能描述: 跳转到平台信息初使化管理页面
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/12/16 13:28
     */
    @RequestMapping(value = "toCloudInitListPage")
    public ModelAndView toCloudInitListPage(){
        return new ModelAndView("sys/cloudInitList");
    }

    /**
     *
     * 功能描述:查询平台信息初使化列表
     *
     * @param ajaxPage
     * @param cloudInit
     * @return: ScriptPage<CloudInit>
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    @RequestMapping(value = "findCloudInitList")
    @ResponseBody
    public ScriptPage<CloudInit> findCloudInitList(AjaxPage ajaxPage, CloudInit cloudInit){
        return cloudInitService.findCloudInitList(ajaxPage,cloudInit);
    }

    /**
     *
     * 功能描述: 新增平台信息初使化信息
     *
     * @param cloudInit
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/28 16:22
     */
    @RequestMapping(value = "saveCloudInit")
    @ResponseBody
    public Data saveCloudInit(CloudInit cloudInit){
        return cloudInitService.saveCloudInit(cloudInit);
    }

    /**
     *
     * 功能描述: 修改平台信息初使化信息
     *
     * @param cloudInit
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/28 16:22
     */
    @RequestMapping(value = "modifyCloudInit")
    @ResponseBody
    public Data modifyCloudInit(CloudInit cloudInit){
        return cloudInitService.modifyCloudInit(cloudInit);
    }

    /**
     *
     * 功能描述: 删除平台信息初使化信息
     *
     * @param cloudInit
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/28 16:22
     */
    @RequestMapping(value = "delCloudInit")
    @ResponseBody
    public Data delCloudInit(CloudInit cloudInit){
        return cloudInitService.modifyCloudInit(cloudInit);
    }
}
