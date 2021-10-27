package com.xjt.cloud.maintenance.api.controller.project;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.service.service.project.AppModuleService;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * @MethodName: findByUserProjectAppModule
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

    /**
     * @MethodName: saveUserProjectAppModule
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

}
