package com.xjt.cloud.iot.api.eye;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.eye.FireEyeDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * app火眼
 *
 * @author huanggc
 * @date 2021/01/20
 */
@RestController
@RequestMapping("/fireEyeDevice/")
public class FireEyeDeviceController extends AbstractController {

    @Autowired
    private FireEyeDeviceService fireEyeDeviceService;

    /**
     * 保存火眼设备
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:02
     **/
    @RequestMapping(value = "saveFireEyeDevice/{projectId}")
    public Data saveFireEyeDevice(String json) {
        return fireEyeDeviceService.saveFireEyeDevice(json);
    }

    /**
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * 删除火眼设备
     * @author zhangZaiFa
     * @date 2020/4/20 15:02
     **/
    @RequestMapping(value = "delFireEyeDevice/{projectId}")
    public Data delFireEyeDevice(String json) {
        return fireEyeDeviceService.delFireEyeDevice(json);
    }

    /**
     * 修改火眼设备
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:02
     **/
    @RequestMapping(value = "updFireEyeDevice/{projectId}")
    public Data updFireEyeDevice(String json) {
        return fireEyeDeviceService.updFireEyeDevice(json);
    }

    /**
     * 查询火眼设备
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:02
     **/
    @RequestMapping(value = "findFireEyeDevice/{projectId}")
    public Data findFireEyeDevice(String json) {
        return fireEyeDeviceService.findFireEyeDevice(json);
    }

    /**
     * 查询火眼设备状态
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021-03-22
     **/
    @RequestMapping(value = "findFireEyeDeviceState/{projectId}")
    public Data findFireEyeDeviceState(String json) {
        return fireEyeDeviceService.findFireEyeDeviceState(json);
    }

    /**
     * 查询火眼设备列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:02
     **/
    @RequestMapping(value = "findFireEyeDeviceList/{projectId}")
    public Data findFireEyeDeviceList(String json) {
        return fireEyeDeviceService.findFireEyeDeviceList(json);
    }

    /**
     * 火眼离线任务
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021-03-23
     **/
    @RequestMapping(value = "fireEyeDeviceOffLineTask")
    public Data fireEyeDeviceOffLineTask() {
        return fireEyeDeviceService.fireEyeDeviceOffLineTask();
    }

    /**
     * 查询火眼设备监测状态
     *
     * @param json 参数
     * @author huanggc
     * @date 2021-03-25
     **/
    @RequestMapping(value = "findFireEyeDeviceMonitoringStatus/{projectId}")
    public Data findFireEyeDeviceMonitoringStatus(String json) {
        return fireEyeDeviceService.findFireEyeDeviceMonitoringStatus(json);
    }

    /**
     * @Description 查询app首页火眼信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "findUserProjectFireEyeData/{projectId}")
    public JSONObject findUserProjectFireEyeData(String json){
        return fireEyeDeviceService.findUserProjectFireEyeData(json);
    }
}
