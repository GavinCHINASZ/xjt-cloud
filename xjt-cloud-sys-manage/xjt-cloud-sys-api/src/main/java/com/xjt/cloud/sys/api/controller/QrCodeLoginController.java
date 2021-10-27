package com.xjt.cloud.sys.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.service.service.QrCodeLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/26 09:41
 * @Description: 二维码登录
 */
@RestController
public class QrCodeLoginController extends AbstractController {
    @Autowired
    private QrCodeLoginService qrCodeLoginService;

    /**
     *
     * 功能描述:生成二维码
     *
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/26 9:58
     */
    @RequestMapping(value = "loginGenerateQRCode")
    public Data generateQRCode(){
        return qrCodeLoginService.generateQRCode();
    }

    /**
     *
     * 功能描述: app扫描上传用户登录信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/26 14:17
     */
    @RequestMapping(value = "loginUpQrCodeLoginInfo")
    public Data upQrCodeLoginInfo(String json){
        return qrCodeLoginService.upQrCodeLoginInfo(json);
    }

    /**
     *
     * 功能描述:二维码登录，由pc页面轮循
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/26 14:18
     */
    @RequestMapping(value = "loginQrCodeLogin")
    public Data qrCodeLogin(String json){
        return qrCodeLoginService.qrCodeLogin(json,"APP");
    }
}
