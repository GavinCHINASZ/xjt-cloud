package com.xjt.cloud.admin.manage.dao.device;

import com.xjt.cloud.admin.manage.entity.device.CheckItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:30
 * @Description:巡检项管理
 */
@Repository
public interface CheckItemDao {

    /**
     *
     * 功能描述:查询设备巡检项列表总记录数
     *
     * @param checkItem
     * @return: com.xjt.cloud.device.core.entity.CheckItem
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    Integer findCheckItemListTotalCount(CheckItem checkItem);

    /**
     *
     * 功能描述:查询设备巡检项列表
     *
     * @param checkItem
     * @return: com.xjt.cloud.device.core.entity.CheckItem
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    List<CheckItem> findCheckItemList(CheckItem checkItem);

    /**
     *
     * 功能描述:添加设备巡检项
     *
     * @param checkItem
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    Integer saveCheckItem(CheckItem checkItem);

    /**
     *
     * 功能描述:修改设备巡检项
     *
     * @param checkItem
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    Integer modifyCheckItem(CheckItem checkItem);

    /**
     * @Description 保存项目巡检项信息
     *
     * @param checkItem
     * @author wangzhiwen
     * @date 2020/10/21 14:31
     * @return com.xjt.cloud.commons.utils.Data
     */
    Integer saveProjectCheckItem(CheckItem checkItem);
}