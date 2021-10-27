package com.xjt.cloud.message.core.common.util;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.SpringBeanUtil;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.message.core.service.service.PageViewService;

import javax.servlet.http.HttpServletRequest;

/**
 * 多线程 保存 pageView
 *
 * @author huanggc
 * @date 2020/10/23
 */
public class PageViewThread extends Thread {
    private String json;

    public PageViewThread(String json, HttpServletRequest request) {
        JSONObject object = JSONObject.parseObject(json);
        // 获取发送请求的客户端主机的IP
        object.put("remoteAddr", request.getRemoteAddr());
        object.put("userAgent", request.getHeader("user-agent"));
        this.json = object.toString();
    }

    @Override
    public void run() {
        try {
            PageViewService pageViewService = SpringBeanUtil.getBean(PageViewService.class);
            pageViewService.savePageViewThread(json);
        } catch (Exception var2) {
            SysLog.error(var2);
        }
    }
}
