package com.xjt.cloud.admin.manage.controller.sys;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.QuartzConfig;
import com.xjt.cloud.admin.manage.service.service.sys.QuartzConfigService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 定时任务 Controller
 * 
 * @author huanggc
 * @date 2020/09/15
 */
@Controller
@RequestMapping("/quartz/config/")
public class QuartzConfigController extends AbstractController {

    @Autowired
    private QuartzConfigService quartzConfigService;

    /**
     * 功能描述:定时任务管理页面
     *
     * @author huanggc
     * @date 2020/09/15
     * @return ModelAndView
     */
    @RequestMapping(value = "toQuartzConfigListPage")
    public ModelAndView toDictListPage(){
        return new ModelAndView("sys/quartzConfig");
    }

    /**
     * 功能描述:查询 定时任务
     *
     * @param quartzConfig 任务实体
     * @param ajaxPage ajax
     * @author huanggc
     * @date 2020/09/15
     * @return ScriptPage<QuartzConfig>
     */
    @RequestMapping(value = "findQuartzConfigList")
    @ResponseBody
    public ScriptPage<QuartzConfig> findQuartzConfigList(AjaxPage ajaxPage, QuartzConfig quartzConfig){
        return quartzConfigService.findQuartzConfigList(ajaxPage, quartzConfig);
    }

    /**
     * 功能描述:修改 定时任务
     *
     * @param quartzConfig 任务实体
     * @author huanggc
     * @date 2020/09/16
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "modifyQuartzConfig")
    @ResponseBody
    public Data modifyQuartzConfig(QuartzConfig quartzConfig){
        return quartzConfigService.modifyQuartzConfig(quartzConfig);
    }

    /**
     * 功能描述:新增 保存 定时任务
     *
     * @param quartzConfig 任务实体
     * @author huanggc
     * @date 2020/09/16
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveQuartzConfig")
    @ResponseBody
    public Data saveQuartzConfig(QuartzConfig quartzConfig){
        return quartzConfigService.saveQuartzConfig(quartzConfig);
    }

    /**
     * 功能描述:删除 定时任务
     *
     * @param quartzConfig 任务实体
     * @author huanggc
     * @date 2020/09/16
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "quartzConfigDel")
    @ResponseBody
    public Data quartzConfigDel(QuartzConfig quartzConfig){
        return quartzConfigService.quartzConfigDel(quartzConfig);
    }

    /**
     * 功能描述:修改 定时任务 状态
     *
     * @param quartzConfig 任务实体
     * @author huanggc
     * @date 2020/09/16
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "updateQuartzConfigStatus")
    @ResponseBody
    public Data updateQuartzConfigStatus(QuartzConfig quartzConfig){
        return quartzConfigService.updateQuartzConfigStatus(quartzConfig);
    }

}
