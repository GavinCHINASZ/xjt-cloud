package com.xjt.cloud.device.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.device.core.service.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 巡检项管理
 *
 * @author wangzhiwen
 * @date 2019/7/18 17:24
 */
@RestController
@RequestMapping("/check/item/")
public class CheckItemController extends AbstractController {
    @Autowired
    private CheckItemService checkItemService;

    /**
     * 功能描述:查询设备巡检项列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/19 9:28
     */
    @RequestMapping(value = "findCheckItemList/{projectId}")
    public Data findCheckItemList(String json){
        return checkItemService.findCheckItemList(json);
    }

}
