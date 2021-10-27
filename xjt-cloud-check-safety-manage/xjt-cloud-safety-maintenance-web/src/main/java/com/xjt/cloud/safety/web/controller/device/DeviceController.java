package com.xjt.cloud.safety.web.controller.device;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.safety.core.service.service.device.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * DeviceController
 *
 * @author dwt
 * @date 2020-04-10 19:14
 */
@RestController
@RequestMapping("/device")
public class DeviceController extends AbstractController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 查询设备列表
     *
     * @author dwt
     * @date 2020-04-10 17:12
     * @param json Device
     * @return Data
     */
    @RequestMapping(value = "/findDeviceList/{projectId}")
    public Data findDeviceList(String json) {
        return deviceService.findDeviceList(json);
    }

    /**
     * 保存设备(新增/修改)
     *
     * @author dwt
     * @date 2020-04-10 17:15
     * @param json Device
     * @return Data
     */
    @RequestMapping(value = "/saveDevice/{projectId}")
    public Data saveDevice(String json) {
        return deviceService.saveDevice(json);
    }

    /**
     * 设备登记导入
     *
     * @author dwt
     * @date 2020-04-11 14:00
     * @param json, file
     * @return Data
     */
    @RequestMapping(value = "/uploadDeviceList/{projectId}")
    public Data uploadDeviceList(String json, MultipartFile file) {
        return deviceService.uploadDeviceList(json, file);
    }

    /**
     * 逻辑删除
     *
     * @author dwt
     * @date 2020-04-11 17:56
     * @param json 参数
     * @return Data
     */
    @RequestMapping(value = "/deleteDevice/{projectId}")
    public Data deleteDevice(String json) {
        return deviceService.deleteDevice(json);
    }

    /* -------------------评估测试记录------------------- */
    /**
     * 查询 评估测试记录 list
     *
     * @author huanggc
     * @date 2021/05/10
     * @param json 参数
     * @return Data
     */
    @RequestMapping(value = "/findAssessmentTestRecordList/{projectId}")
    public Data findAssessmentTestRecordList(String json) {
        return deviceService.findAssessmentTestRecordList(json);
    }

    /**
     * 查询 评估测试记录
     *
     * @author huanggc
     * @date 2021/05/10
     * @param json 参数
     * @return Data
     */
    @RequestMapping(value = "/findAssessmentTestRecord/{projectId}")
    public Data findAssessmentTestRecord(String json) {
        return deviceService.findAssessmentTestRecord(json);
    }

    /**
     * 保存 评估测试记录
     *
     * @author huanggc
     * @date 2021/05/10
     * @param json 参数
     * @return Data
     */
    @RequestMapping(value = "/saveAssessmentTestRecord/{projectId}")
    public Data saveAssessmentTestRecord(String json) {
        return deviceService.saveAssessmentTestRecord(json);
    }
}
