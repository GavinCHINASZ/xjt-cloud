package com.xjt.cloud.project.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.AppModuleService;
import com.xjt.cloud.project.core.service.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AppModuleController APP模块管理
 * @Author zhangZaiFa
 * @Date 2019-12-30 15:15
 * @Description
 */
@RestController
@RequestMapping("/project/appModule")
public class AppModuleController extends AbstractController {

    @Autowired
    private AppModuleService appModuleService;


    /**@MethodName: findByUserProjectAppModule
     * @Description: 查询用户项目内APP模块
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/30 9:53
     **/
    @RequestMapping(value = "/findByUserProjectAppModule")
    public Data findByUserProjectAppModule(String json) {
        return appModuleService.findByUserProjectAppModule(json);
    }

    /**@MethodName: saveUserProjectAppModule
     * @Description: 保存用户项目内APP模块
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/30 9:54
     **/
    @RequestMapping(value = "/saveUserProjectAppModule")
    public Data saveUserProjectAppModule(String json) {
        return appModuleService.saveUserProjectAppModule(json);
    }

    /**
     * @Description 保存app首页模版类型
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 11:32
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "/saveAppHomeUserProjectModule")
    public Data saveAppHomeUserProjectModule(String json) {
        return appModuleService.saveAppHomeUserProjectModule(json);
    }

}
