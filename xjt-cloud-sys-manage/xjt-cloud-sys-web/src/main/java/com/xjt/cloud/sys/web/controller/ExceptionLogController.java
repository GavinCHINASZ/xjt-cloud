package com.xjt.cloud.sys.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.service.service.ExceptionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName ExceptionLogController
 * @Description
 * @Author wangzhiwen
 * @Date 2020/9/14 9:37
 **/
@RestController
@RequestMapping("/exception/log/")
public class ExceptionLogController extends AbstractController {

    @Autowired
    ExceptionLogService exceptionLogService;

    /**
     * @Description保存错误日志
     *
     * @param json
     * @author wangzhiwen
     * @date 2020/9/14 9:54
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveExceptionLog")
    public Data saveExceptionLog(String json){
        return exceptionLogService.saveExceptionLog(json);
    }
}
