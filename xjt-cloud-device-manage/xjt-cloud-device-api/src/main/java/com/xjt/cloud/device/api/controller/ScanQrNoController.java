package com.xjt.cloud.device.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.device.core.common.ConstantsDevice;
import com.xjt.cloud.device.core.service.service.QrNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/17 13:53
 * @Description: 二维码扫描
 */
@RestController
public class ScanQrNoController extends AbstractController {

    @Autowired
    private QrNoService qrNoService;

    /**
     *
     * 功能描述: 二维码扫描
     *
     * @param qrNo
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019/12/17 14:14
     */
    @RequestMapping(value = "/xjt/mt/mtMaterialLoc/vqr/{qrNo}")
    public Data scanQrNo(HttpServletRequest request, HttpServletResponse response, @PathVariable String qrNo) throws IOException {
        if (!"XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {// not
            response.sendRedirect(ConstantsDevice.QR_NO_REDIRECT_URL + qrNo);
            return null;
        }
        return qrNoService.scanQrNo(qrNo);
    }

    /**
     *
     * 功能描述: 二维码扫描
     *
     * @param qrNo
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019/12/17 14:14
     */
    @RequestMapping(value = "/qrcode/{qrNo}")
    public Data qrcode(HttpServletRequest request, HttpServletResponse response, @PathVariable String qrNo) throws IOException {
        if (!"XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {// not
            response.sendRedirect(ConstantsDevice.QR_NO_REDIRECT_URL + qrNo);
            return null;
        }
        return qrNoService.scanQrNo(qrNo);
    }
}
