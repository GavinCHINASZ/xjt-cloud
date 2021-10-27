package com.xjt.cloud.device.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.device.core.service.service.DeviceCheckImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DeviceCheckImgController
 * @Description 设备图片检测管理
 * @Author wangzhiwen
 * @Date 2021/3/8 9:52
 **/
@RestController
@RequestMapping("/device/check/img/")
public class DeviceCheckImgController extends AbstractController {
    @Autowired
    private DeviceCheckImgService deviceCheckImgService;

    /**
     * @Description 查询设备检测图片的key
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/11 17:06
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping("findDeviceCheckImgKey")
    public Data findDeviceCheckImgKey(String json){
        return deviceCheckImgService.findDeviceCheckImgKey(json);
    }

    /**
     * @Description 上传设备检测图片的key
     *
     * @author wangzhiwen
     * @date 2021/3/8 9:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "uploadDeviceCheckImgKey")
    public Data uploadDeviceCheckImgKey(String json){
        return deviceCheckImgService.uploadDeviceCheckImgKey(json);
    }

    /**
     * @Description 上传检测图片并计算
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/8 10:02
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "uploadDeviceCheckImgAndCompute")
    public Data uploadDeviceCheckImgAndCompute(String json){
        return deviceCheckImgService.uploadDeviceCheckImgAndCompute(json);
    }
}
