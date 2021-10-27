package com.xjt.cloud.maintenance.web.controller.device;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.service.service.device.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:24
 * @Description:巡检项管理
 */
@RestController
@RequestMapping("/check/item/")
public class CheckItemController extends AbstractController {
    @Autowired
    private CheckItemService checkItemService;

    /**
     *
     * 功能描述:查询设备巡检项列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @RequestMapping(value = "findCheckItemList")
    public Data findCheckItemList(String json){
        return checkItemService.findCheckItemList(json);
    }

    /**
     *
     * 功能描述:查询设备巡检项列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @RequestMapping(value = "findOfflineCheckInfoList")
    public Data findOfflineCheckInfoList(String json){
        return checkItemService.findOfflineCheckInfoList(json);
    }

    /**
     *
     * 功能描述:查询设备巡检项列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @RequestMapping(value = "findAllCheckItemTotal")
    public Data findAllCheckItemTotal(String json){
        return checkItemService.findAllCheckItemTotal(json);
    }

    /**
     *
     * 功能描述:查询设备巡检项
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @RequestMapping(value = "findCheckItem")
    public Data findCheckItem(String json){
        return checkItemService.findCheckItem(json);
    }

    /**
     *
     * 功能描述: 设备巡检项缓存初使化
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/11/26 14:13
     */
    @RequestMapping(value = "deviceCheckItemCacheInit")
    public Data deviceCheckItemCacheInit(){
        return checkItemService.deviceCheckItemCacheInit();
    }
}
