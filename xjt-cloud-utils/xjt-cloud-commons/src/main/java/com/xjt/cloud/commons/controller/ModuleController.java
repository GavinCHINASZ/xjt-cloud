package com.xjt.cloud.commons.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/6/18 10:32
 * @Description:
 */
@RestController
public class ModuleController extends AbstractController {

    /**
     *
     * 功能描述:项目模块检测路径
     *
     * @param
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2020/6/18 10:34
     */
    @RequestMapping(value = "moduleCheck")
    public String moduleCheck(){
        return "OK";
    }
}
