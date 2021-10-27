package com.xjt.cloud.admin.manage.service.impl.device;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.device.CheckItemDao;
import com.xjt.cloud.admin.manage.entity.device.CheckItem;
import com.xjt.cloud.admin.manage.service.service.device.CheckItemService;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 巡检项管理业务接口实现类
 *
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:30
 */
@Service
public class CheckItemServiceImpl extends AbstractAdminService implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 功能描述:查询设备巡检项列表
     *
     * @param ajaxPage
     * @param checkItem
     * @return: ScriptPage<CheckItem>
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @Override
    public ScriptPage<CheckItem> findCheckItemList(AjaxPage ajaxPage, CheckItem checkItem) {
        String[] orderCols = {"checkItemVsType", "p.id", "ds.id", "dci.device_type_id"};
        checkItem.setOrderCols(orderCols);
        checkItem = asseFindObj(checkItem, ajaxPage);
        return asseScriptPage(checkItemDao.findCheckItemList(checkItem), checkItemDao.findCheckItemListTotalCount(checkItem));
    }

    /**
     * 功能描述:添加设备巡检项
     *
     * @param checkItem
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @Override
    public Data saveCheckItem(CheckItem checkItem) {
        int num = checkItemDao.saveCheckItem(checkItem);
        return asseData(num);
    }

    /**
     * 功能描述:修改设备巡检项
     *
     * @param checkItem
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @Override
    public Data modifyCheckItem(CheckItem checkItem) {
        CheckItem oldCheckItem = new CheckItem();
        oldCheckItem.setId(checkItem.getId());
        int num = checkItemDao.modifyCheckItem(checkItem);
        return asseData(num);
    }

    /**
     * @param checkItem
     * @return com.xjt.cloud.commons.utils.Data
     * @Description 保存项目巡检项信息
     * @author wangzhiwen
     * @date 2020/10/21 14:31
     */
    @Override
    public Data saveProjectCheckItem(CheckItem checkItem) {
        return asseData(checkItemDao.saveProjectCheckItem(checkItem));
    }
}
