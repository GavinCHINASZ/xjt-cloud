package com.xjt.cloud.device.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.device.core.service.service.QrNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: zhangzaifa
 * @Date: 2019/12/4 17:25
 * @Description:二维码管理
 */
@RestController
@RequestMapping("/qr/no/")
public class QrNoController extends AbstractController {

    @Autowired
    private QrNoService qrNoService;

    /**@MethodName: findQrNoInformation
     * @Description: 查询二维码相关信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/4 10:24
     **/
    @RequestMapping(value = "findQrNoInformation")
    public Data findQrNoInformation(String json, HttpServletRequest request){
        return qrNoService.findQrNoInformation(json,request);
    }




}
