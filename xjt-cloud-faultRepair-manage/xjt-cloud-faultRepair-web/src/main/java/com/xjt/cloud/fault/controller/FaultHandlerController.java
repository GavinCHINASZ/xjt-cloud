package com.xjt.cloud.fault.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.fault.core.service.service.FaultHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 故障处理人/维修人 控制类
 *
 * @Author huanggc
 * @Date 2019/09/02
 **/
@RestController
@RequestMapping("/faultHandler")
public class FaultHandlerController extends AbstractController {
    @Autowired
    private FaultHandlerService faultHandlerService;

    /**
     * 功能描述:查询 故障处理人 列表
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/02
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findFaultHandlerList/{projectId}")
    public Data findFaultHandlerList(String json) {
        return faultHandlerService.findFaultHandlerList(json);
    }
}
