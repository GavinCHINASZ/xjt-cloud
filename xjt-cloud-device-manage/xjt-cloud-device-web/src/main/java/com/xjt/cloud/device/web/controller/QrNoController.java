package com.xjt.cloud.device.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.device.core.service.service.QrNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:25
 * @Description:二维码管理
 */
@RestController
@RequestMapping("/qr/no/")
public class QrNoController extends AbstractController {
    @Autowired
    private QrNoService qrNoService;
    /**
     *
     * 功能描述:批量生成二维码
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/22 14:00
     */
    @RequestMapping(value = "generationQrNo/{projectId}")
    public Data generationQrNo(String json){
        return qrNoService.generationQrNo(json);
    }

    /**
     *
     * 功能描述:查询二维码列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/22 9:01
     */
    @RequestMapping(value = "findQrNoList/{projectId}")
    public Data findQrNoList(String json){
        return qrNoService.findQrNoList(json);
    }

    /**
     *
     * 功能描述:下载二维码列表
     *
     * @param json
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/8/7 9:37
     */
    @RequestMapping(value = "downloadQrNoExcelByList/{projectId}")
    public void downloadQrNoExcelByList(HttpServletResponse resp, String json){
        qrNoService.downloadQrNoExcelByList(resp,json);
    }

    /////////////////////////////////////////////////// 二维码 配置 //////////////////////////////////////////////////////
    /**
     *
     * 功能描述:查询二维码模版信息列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/22 9:37
     */
    @RequestMapping(value = "findQrNoConfigList/{projectId}")
    public Data findQrNoConfigList(String json){
        return qrNoService.findQrNoConfigList(json);
    }

    /**
     *
     * 功能描述:生成二维码图片
     *
     * @param backFile 二维码背景图片
     * @param logoFile 二维码logo
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/22 9:40
     */
    @RequestMapping(value = "generationQrNoImg/{projectId}")
    public Data generationQrNoImg(MultipartFile backFile, MultipartFile logoFile, String json){
        return qrNoService.generationQrNoImg(backFile, logoFile, json);
    }

    /**
     *
     * 功能描述:保存二维码模版
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/26 10:45
     */
    @RequestMapping(value = "saveQrNoConfig/{projectId}")
    public Data saveQrNoConfig(String json){
        return qrNoService.saveQrNoConfig(json);
    }

    /**
     *
     * 功能描述:修改二维码模版
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/26 10:45
     */
    @RequestMapping(value = "modifyQrNoConfig/{projectId}")
    public Data modifyQrNoConfig(String json){
        return qrNoService.modifyQrNoConfig(json);
    }

    /**
     *
     * 功能描述:删除二维码模版
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/26 10:45
     */
    @RequestMapping(value = "delQrNoConfig/{projectId}")
    public Data delQrNoConfig(String json){
        return qrNoService.delQrNoConfig(json);
    }
}
