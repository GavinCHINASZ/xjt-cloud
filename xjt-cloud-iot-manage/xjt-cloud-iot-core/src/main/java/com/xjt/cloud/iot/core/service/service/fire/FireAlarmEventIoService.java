package com.xjt.cloud.iot.core.service.service.fire;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FireAlarmEventIoService
 *
 * @author huanggc
 * @date 2020/07/10
 */
public interface FireAlarmEventIoService {

    /**
     * 快照报表 导出功能
     *
     * @author huanggc
     * @date 2020/07/10
     * @param json
     * @param request
     * @param response
     */
    void findFireAlarmIo(String json, HttpServletRequest request, HttpServletResponse response);
}
