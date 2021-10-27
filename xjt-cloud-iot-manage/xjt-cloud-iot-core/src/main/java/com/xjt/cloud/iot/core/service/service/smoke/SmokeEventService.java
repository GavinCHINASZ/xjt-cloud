package com.xjt.cloud.iot.core.service.service.smoke;

import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * SmokeEventService 烟感事件service接口
 *
 * @author huanggc
 * @date 2020/07/15
 **/
public interface SmokeEventService extends BaseService {

    /**
     * 功能描述 查询烟感事件列表
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findSmokeEventList(String json);

    /**
     * 功能描述: 增加 烟感事件
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data saveSmokeEvent(String json);

    /**
     * 功能描述: 更新 烟感事件
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data updateSmokeEvent(String json);

    /**
     * 功能描述: 删除 烟感事件
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data deletedSmokeEvent(String json);

    Data receive(HttpServletRequest request, String msg, String nonce, String signature);

    /**
     * 功能说明： URL&Token验证接口。如果验证成功返回msg的值，否则返回其他值。
     *
     * @param msg 验证消息
     * @param nonce 随机串
     * @param signature 签名
     * @auther huanggc
     * @return msg值
     */
    String check(String msg, String nonce, String signature);

    /**
     * 功能说明： 移动 智慧消防平台https://smartsensor.eastcmiot.com， http推送验证
     * @param encryptMsg 加密内容
     * @param msgType 消息内容
     * @author huanggc
     * @return 返回200后才可保存成功
     */
    String checkCM(String encryptMsg, String msgType);

    /**
     * 功能说明： 移动 智慧消防平台https://smartsensor.eastcmiot.com， http推送验证
     * @param request 加密内容
     * @author huanggc
     * @date 2020/07/21
     * @return
     */
    String checkCM(HttpServletRequest request);

    /**
     * 功能描述: 烟感告警事件 报表 饼图
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findSmokeEventSummaryReport(String json);

    /**
     * 功能描述: 烟感告警事件 报表 曲线图
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/31
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findSmokeEventReportCount(String json);

    /**
     * 功能描述 导出烟感告警事件列表
     *
     * @param json
     * @auther huanggc
     * @date 2020/08/07
     * @return: com.xjt.cloud.commons.utils.Data
     */
    void downSmokeEventList(String json, HttpServletResponse response);
}
