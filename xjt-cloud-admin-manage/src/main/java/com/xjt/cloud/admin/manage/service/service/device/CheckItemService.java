package com.xjt.cloud.admin.manage.service.service.device;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.device.CheckItem;
import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:30
 * @Description:巡检项管理接口
 */
public interface CheckItemService {
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
    ScriptPage<CheckItem> findCheckItemList(AjaxPage ajaxPage, CheckItem checkItem);

    /**
     *
     * 功能描述:添加设备巡检项
     *
     * @param checkItem
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    Data saveCheckItem(CheckItem checkItem);

    /**
     *
     * 功能描述:修改设备巡检项
     *
     * @param checkItem
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    Data modifyCheckItem(CheckItem checkItem);

    /**
     * @Description 保存项目巡检项信息
     *
     * @param checkItem
     * @author wangzhiwen
     * @date 2020/10/21 14:31
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveProjectCheckItem(CheckItem checkItem);
}
