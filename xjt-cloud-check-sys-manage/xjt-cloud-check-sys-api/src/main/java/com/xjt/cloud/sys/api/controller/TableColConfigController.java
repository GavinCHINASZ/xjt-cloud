package com.xjt.cloud.sys.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.service.service.TableColConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/10/31 10:06
 * @Description: 页面表格显示配置controller
 */
@RestController
@RequestMapping("/table/col/config/")
public class TableColConfigController extends AbstractController {

    @Autowired
    private TableColConfigService tableColConfigService;

    /**
     *
     * 功能描述:查询用户项目表格配置信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/10/31 10:36
     */
    @RequestMapping(value = "findTableColConfig")
    public Data findTableColConfig(String json){
        return tableColConfigService.findTableColConfig(json);
    }

    /**
     *
     * 功能描述:保存用户项目表格配置信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/10/31 10:36
     */
    @RequestMapping(value = "saveTableColConfig")
    public Data saveTableColConfig(String json){
        return tableColConfigService.saveTableColConfig(json);
    }

    /**
     *
     * 功能描述:保存用户项目表格配置信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/10/31 10:36
     */
    @RequestMapping(value = "modifyTableColConfig")
    public Data modifyTableColConfig(String json){
        return tableColConfigService.modifyTableColConfig(json);
    }
}
