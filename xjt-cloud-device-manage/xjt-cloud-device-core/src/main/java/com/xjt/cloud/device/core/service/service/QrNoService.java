package com.xjt.cloud.device.core.service.service;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.device.core.entity.QrNo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:32
 * @Description:二维码管理
 */
public interface QrNoService {

    /**
     *
     * 功能描述:批量生成二维码
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/22 14:00
     */
    Data generationQrNo(String json);
    /**
     *
     * 功能描述:批量生成二维码
     *
     * @param qrNo
     * @return: List<String>
     * @auther: wangzhiwen
     * @date: 2019/7/22 14:00
     */
    List<QrNo> generationQrNo(QrNo qrNo);
    /**
     *
     * 功能描述: 生成一个二维码
     *
     * @param projectId 项目id
     * @param type 类型 1 巡检点码 2 设备码
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/8/7 16:01
     */
    String getOneQrNo(Long projectId, int type);

    /**
     *
     * 功能描述:查询二维码列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/22 9:01
     */
    Data findQrNoList(String json);

    /**
     *
     * 功能描述:下载二维码列表
     *
     * @param json
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/8/7 9:37
     */
    void downloadQrNoExcelByList(HttpServletResponse resp, String json);

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
    Data findQrNoConfigList(String json);

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
    Data generationQrNoImg(MultipartFile backFile, MultipartFile logoFile, String json);

    /**
     *
     * 功能描述:保存二维码模版
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/26 10:45
     */
    Data saveQrNoConfig(String json);

    /**
     *
     * 功能描述:修改二维码模版
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/26 10:45
     */
    Data modifyQrNoConfig(String json);

    /**
     *
     * 功能描述:删除二维码模版
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/26 10:45
     */
    Data delQrNoConfig(String json);

    /**@MethodName: findQrNoInformation
     * @Description: 查询二维码相关信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/4 10:26
     **/
    Data findQrNoInformation(String json, HttpServletRequest request);

    /**
     *
     * 功能描述: 二维码扫描
     *
     * @param qrNo
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/12/17 14:12
     */
    Data scanQrNo(String qrNo);
}
