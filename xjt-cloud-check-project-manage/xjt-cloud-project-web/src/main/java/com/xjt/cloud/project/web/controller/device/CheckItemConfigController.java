package com.xjt.cloud.project.web.controller.device;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.device.CheckItemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/11 09:26
 * @Description: pc检测项配置
 */
@RestController
@RequestMapping("/check/item/config/")
public class CheckItemConfigController extends AbstractController {

    @Autowired
    private CheckItemConfigService checkItemConfigService;

    /**
     *
     * 功能描述:查询pc检测项配置列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @RequestMapping(value = "findCheckResult")
    public Data findCheckResult(String json){
        return checkItemConfigService.findCheckResult(json);
    }

    /**
     *
     * 功能描述:查询pc检测项配置列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @RequestMapping(value = "findCheckItemConfigList")
    public Data findCheckItemConfigList(String json){
        return checkItemConfigService.findCheckItemConfigList(json);
    }

    /**
     *
     * 功能描述:新增pc检测项配置信息
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @RequestMapping(value = "saveCheckItemConfig")
    public Data saveCheckItemConfig(String json){
        return checkItemConfigService.saveCheckItemConfig(json);
    }

    /**
     *
     * 功能描述:修改pc检测项配置信息
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @RequestMapping(value = "modifyCheckItemConfig")
    public Data modifyCheckItemConfig(String json){
        return checkItemConfigService.modifyCheckItemConfig(json);
    }

    /**
     *
     * 功能描述: 删除pc检测项配置
     *
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/8/13 9:22
     */
    @RequestMapping(value = "delCheckItemConfig")
    public Data delCheckItemConfig(String json){
        return checkItemConfigService.modifyCheckItemConfig(json);
    }
}
