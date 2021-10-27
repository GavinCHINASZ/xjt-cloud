package com.xjt.cloud.sys.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/26 09:42
 * @Description: 二维码登录
 */
public interface QrCodeLoginService {
    /**
     *
     * 功能描述:生成二维码
     *
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/26 9:58
     */
    Data generateQRCode();

    /**
     *
     * 功能描述: app扫描上传用户登录信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/26 14:17
     */
    Data upQrCodeLoginInfo(String json);

    /**
     *
     * 功能描述:二维码登录，由pc页面轮循
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/26 14:18
     */
    Data qrCodeLogin(String json,String cloudType);
}
