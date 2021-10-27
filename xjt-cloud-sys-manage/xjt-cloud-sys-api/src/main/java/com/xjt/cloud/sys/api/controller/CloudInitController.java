package com.xjt.cloud.sys.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.service.service.CloudInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/16 11:35
 * @Description: 平台信息初使化管理controller
 */
@RestController
@RequestMapping("/cloud/init/")
public class CloudInitController extends AbstractController {
    @Autowired
    private CloudInitService cloudInitService;

    /**
     *
     * 功能描述:查询平台信息初使化列表
     *
     * @param json
     * @return: ScriptPage<CloudInit>
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    @RequestMapping(value = "findCloudInitList")
    public Data findCloudInitList(String json){
        return cloudInitService.findCloudInitList(json);
    }
    /**
     *
     * 功能描述:查询平台信息初使化列表
     *
     * @param json
     * @return: ScriptPage<CloudInit>
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    @RequestMapping(value = "findCloudInitChildList")
    public Data findCloudInitChildList(String json){
        return cloudInitService.findCloudInitChildList(json);
    }
}
