package com.xjt.cloud.iot.web.controller.fire;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.iot.core.service.service.fire.FireAlarmEventIoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FireAlarmEventIoController 导出
 *
 * @author huanggc
 * @date 2020/07/10
 */
@RestController
@RequestMapping("/fireAlarmIo")
public class FireAlarmEventIoController extends AbstractController {

    @Autowired
    private FireAlarmEventIoService fireAlarmEventService;

    /**
     * 快照报表 导出功能
     *
     * @author huanggc
     * @date 2020/07/10
     * @param json
     * @param request
     * @param response
     */
    @RequestMapping(value = "/findFireAlarmIo/{projectId}")
    public void findFireAlarmIo(String json, HttpServletRequest request, HttpServletResponse response){
        fireAlarmEventService.findFireAlarmIo(json, request, response);
    }

}
