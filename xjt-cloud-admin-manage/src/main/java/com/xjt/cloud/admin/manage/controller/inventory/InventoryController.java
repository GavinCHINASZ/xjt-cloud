package com.xjt.cloud.admin.manage.controller.inventory;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.inventory.Inventory;
import com.xjt.cloud.admin.manage.service.service.inventory.InventoryService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName InventoryController
 * @Description  库存管理
 * @Author wangzhiwen
 * @Date 2020/8/20 16:16
 **/
@RequestMapping("/inventory/")
@Controller
public class InventoryController extends AbstractController {
    @Autowired
    private InventoryService inventoryService;

    /**
     * @Description 跳转库存列表页面
     *
     * @author wangzhiwen
     * @date 2020/9/7 14:56
     * @return org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping(value = "toInventoryListPage")
    public ModelAndView toInventoryListPage(){
        return new ModelAndView("inventory/inventoryList");
    }


    /**
     * @Description 查询库存信息列表
     *
     * @param ajaxPage
     * @param inventory
     * @author wangzhiwen
     * @date 2020/9/7 15:23
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.inventory.Inventory>
     */
    @RequestMapping(value = "findInventoryList")
    @ResponseBody
    public ScriptPage<Inventory> findInventoryList(AjaxPage ajaxPage, Inventory inventory){
        return inventoryService.findInventoryList(ajaxPage,inventory);
    }
}
