package com.xjt.cloud.admin.manage.service.impl.inventory;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.backstage.InventoryDao;
import com.xjt.cloud.admin.manage.entity.inventory.Inventory;
import com.xjt.cloud.admin.manage.service.service.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName InventoryServiceImpl
 * @Description 库存管理
 * @Author wangzhiwen
 * @Date 2020/8/20 16:17
 **/
@Service
public class InventoryServiceImpl extends AbstractAdminService implements InventoryService {
    @Autowired
    private InventoryDao inventoryDao;

    /**
     * @param ajaxPage
     * @param inventory
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.inventory.Inventory>
     * @Description 查询库存信息列表
     * @author wangzhiwen
     * @date 2020/9/7 15:23
     */
    @Override
    public ScriptPage<Inventory> findInventoryList(AjaxPage ajaxPage, Inventory inventory) {
        inventory = asseFindObj(inventory, ajaxPage);
        return asseScriptPage(inventoryDao.findInventoryList(inventory), inventoryDao.findInventoryListTotalCount(inventory));
    }
}
