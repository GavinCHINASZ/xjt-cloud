package com.xjt.cloud.iot.web.controller.linkage;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 联动设备 Controller
 *
 * @author huanggc
 * @date 2019/09/19
 **/
@RestController
@RequestMapping("/linkage/device/")
public class LinkageDeviceController extends AbstractController {
    @Autowired
    private LinkageDeviceService linkageDeviceService;

    /**
     * 功能描述:查询 联动设备 列表
     *
     * @param json
     * @auther huanggc
     * @date 2019/09/19
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findLinkageDeviceList/{projectId}")
    public Data findLinkageDeviceList(String json) {
        return linkageDeviceService.findLinkageDeviceList(json);
    }

    /**
     * 功能描述:查询 联动设备 汇总
     *
     * @param json
     * @auther huanggc
     * @date 2019/09/19
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findLinkageDeviceSummary/{projectId}")
    public Data findLinkageDeviceSummary(String json) {
        return linkageDeviceService.findLinkageDeviceSummary(json);
    }

    /**
     * 功能描述:导出 联动设备 列表
     *
     * @param json
     * @auther huanggc
     * @date 2019/09/19
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "downLinkageDeviceList/{projectId}")
    public void downLinkageDeviceList(String json, HttpServletResponse response) {
        linkageDeviceService.downLinkageDeviceList(json, response);
    }

    /**
     * 功能描述:删除 联动设备
     *
     * @param json
     * @auther huanggc
     * @date 2019/09/19
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "deleteLinkageDevice/{projectId}")
    public Data deleteLinkageDevice(String json) {
        return linkageDeviceService.deleteLinkageDevice(json);
    }

    /**
     * 功能描述:绑定声光联动 设备
     *
     * @param json
     * @auther huanggc
     * @date 2020/08/19
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveLinkageDevice/{projectId}")
    public Data saveLinkageDevice(String json) {
        return linkageDeviceService.saveLinkageDevice(json);
    }

    /**
     * 功能描述:联动设备--更新
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/19
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "updateLinkageDevice/{projectId}")
    public Data updateLinkageDevice(String json) {
        return linkageDeviceService.updateLinkageDevice(json);
    }

    /**
     * 功能描述:联动设备--点击"新增"按钮跳转"联动设备新增"页面
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/26
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "jumpNewPage/{projectId}")
    public Data jumpNewPage(String json) {
        return linkageDeviceService.jumpNewPage(json);
    }

    /**
     * 功能描述:联动设备--根据"设备名称"查询"设备ID"
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/26
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findQrNo/{projectId}")
    public Data findQrNo(String json) {
        return linkageDeviceService.findQrNo(json);
    }

    /**
     * 功能描述:查询 联动设备
     *
     * @param json
     * @auther huanggc
     * @date 2019/10/22
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findLinkageDevice/{projectId}")
    public Data findLinkageDevice(String json) {
        return linkageDeviceService.findLinkageDevice(json);
    }


    /**
     * 校验联动设备是否在线离线
     *
     * @author zhangZaiFa
     * @date 2020/3/31 10:29
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @RequestMapping(value = "linkageDeviceOffLineTask")
    public Data linkageDeviceOffLineTask() {
        return linkageDeviceService.linkageDeviceOffLineTask();
    }

    /**
     * 功能描述:查询 声光报警设备所在的建筑物
     *
     * @author huanggc
     * @date 2020/08/21
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @RequestMapping(value = "findLinkageDeviceBuilding/{projectId}")
    public Data findLinkageDeviceBuilding(String json) {
        return linkageDeviceService.findLinkageDeviceBuilding(json);
    }

    /**
     * 功能描述:查询 声光报警设备所有的巡查点
     *
     * @author huanggc
     * @date 2020/08/21
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @RequestMapping(value = "findLinkageDeviceCheckPoint/{projectId}")
    public Data findLinkageDeviceCheckPoint(String json) {
        return linkageDeviceService.findLinkageDeviceCheckPoint(json);
    }

    /**
     * 功能描述:查询单个 声光报警设备
     *
     * @author huanggc
     * @date 2020/08/26
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @RequestMapping(value = "findLinkageDeviceView/{projectId}")
    public Data findLinkageDeviceView(String json) {
        return linkageDeviceService.findLinkageDeviceView(json);
    }

}
