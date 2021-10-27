package com.xjt.cloud.iot.web.controller.linkage;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageDeviceService;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageDeviceTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 联动设备表 Controller
 *
 * @author huanggc
 * @date 2020/08/25
 **/
@RestController
@RequestMapping("/linkage/table/")
public class LinkageDeviceTableController extends AbstractController {
    @Autowired
    private LinkageDeviceTableService linkageDeviceTableService;

    /**
     * 功能描述:查询 联动设备 列表
     *
     * @param json
     * @auther huanggc
     * @date 2020/08/25
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findDeviceTableList/{projectId}")
    public Data findDeviceTableList(String json) {
        return linkageDeviceTableService.findDeviceTableList(json);
    }

}
