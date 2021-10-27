package com.xjt.cloud.admin.manage.service.service.inventory;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.inventory.Inventory;

/**
 * @ClassName InventoryService
 * @Description  库存管理
 * @Author wangzhiwen
 * @Date 2020/8/20 16:17
 **/
public interface InventoryService {
    /**
     * @Description 查询库存信息列表
     *
     * @param ajaxPage
     * @param inventory
     * @author wangzhiwen
     * @date 2020/9/7 15:23
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.inventory.Inventory>
     */
    ScriptPage<Inventory> findInventoryList(AjaxPage ajaxPage, Inventory inventory);
}
