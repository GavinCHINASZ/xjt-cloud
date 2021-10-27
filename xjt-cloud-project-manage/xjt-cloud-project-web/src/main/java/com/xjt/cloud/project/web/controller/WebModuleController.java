package com.xjt.cloud.project.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.AppModuleService;
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
@RequestMapping("/project/webModule")
public class WebModuleController extends AbstractController {

    @Autowired
    private AppModuleService appModuleService;

    /**@MethodName: findByUserProjectWebModule
     * @Description: 查询用户项目内Web模块
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/30 9:53
     **/
    @RequestMapping(value = "/findByUserProjectWebModule")
    public Data findByUserProjectWebModule(String json) {
        return appModuleService.findByUserProjectWebModule(json);
    }

    /**@MethodName: saveUserProjectWebModule
     * @Description: 保存用户项目内APP模块
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/30 9:54
     **/
    @RequestMapping(value = "/saveUserProjectWebModule")
    public Data saveUserProjectWebModule(String json) {
        return appModuleService.saveUserProjectWebModule(json);
    }

}
