package com.xjt.cloud.admin.manage.controller.device;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.device.CheckItem;
import com.xjt.cloud.admin.manage.service.service.device.CheckItemService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:24
 * @Description:巡检项管理
 */
@Controller
@RequestMapping("/check/item/")
public class CheckItemController extends AbstractController {
    @Autowired
    private CheckItemService checkItemService;

    /**
     *
     * 功能描述:查询设备巡检项列表
     *
     * @param ajaxPage
     * @param checkItem
     * @return: ScriptPage<CheckItem>
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @RequestMapping(value = "findCheckItemList")
    @ResponseBody
    public ScriptPage<CheckItem> findCheckItemList(AjaxPage ajaxPage, CheckItem checkItem){
        return checkItemService.findCheckItemList(ajaxPage,checkItem);
    }

    /**
     *
     * 功能描述:添加设备巡检项
     *
     * @param checkItem
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @RequestMapping(value = "saveCheckItem")
    @ResponseBody
    public Data saveCheckItem(CheckItem checkItem){
        return checkItemService.saveCheckItem(checkItem);
    }

    /**
     *
     * 功能描述:修改设备巡检项
     *
     * @param checkItem
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @RequestMapping(value = "modifyCheckItem")
    @ResponseBody
    public Data modifyCheckItem(CheckItem checkItem){
        return checkItemService.modifyCheckItem(checkItem);
    }

    /**
     *
     * 功能描述:删除巡检项信息
     *
     * @param checkItem
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/13 9:18
     */
    @RequestMapping(value = "delCheckItem")
    @ResponseBody
    public Data delCheckItem(CheckItem checkItem){
        return checkItemService.modifyCheckItem(checkItem);
    }

    ////////////////////////////////////////////////////////////////////项目巡检项管理 //////////////////////////////////////////////////

    /**
     *
     * 功能描述: 打开设备系统管理页面
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:21
     */
    @RequestMapping("toCheckItemListPage")
    public ModelAndView toDeviceSysListPage(){
        return new ModelAndView("device/checkItemList");
    }

    /**
     * @Description 保存项目巡检项信息
     *
     * @param checkItem
     * @author wangzhiwen
     * @date 2020/10/21 14:31
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping("saveProjectCheckItem")
    @ResponseBody
    public Data saveProjectCheckItem(CheckItem checkItem){
        return checkItemService.saveProjectCheckItem(checkItem);
    }
}
