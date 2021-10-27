package com.xjt.cloud.sys.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.service.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:44
 * @Description: app版本管理
 */
@RestController
@RequestMapping("/version/")
public class VersionController extends AbstractController {

    @Autowired
    private VersionService versionService;

    /**
     *
     * 功能描述:查询版本列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @RequestMapping(value = "findVersionList")
    public Data findVersionList(String json){
        return versionService.findVersionList(json);
    }

    /**
     *
     * 功能描述:查询版本信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @RequestMapping(value = "findVersion")
    public Data findVersion(String json){
        return versionService.findVersion(json);
    }

    /**
     *
     * 功能描述:新增版本信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @RequestMapping(value = "saveVersion")
    public Data saveVersion(String json){
        return versionService.saveVersion(json);
    }

    /**
     *
     * 功能描述:修改版本信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @RequestMapping(value = "modifyVersion")
    public Data modifyVersion(String json){
        return versionService.modifyVersion(json);
    }

    /**
     *
     * 功能描述:删除版本信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @RequestMapping(value = "delVersion")
    public Data delVersion(String json){
        return versionService.delVersion(json);
    }
}
