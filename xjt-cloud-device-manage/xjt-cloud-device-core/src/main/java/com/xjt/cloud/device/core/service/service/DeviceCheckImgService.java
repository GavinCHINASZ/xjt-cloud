package com.xjt.cloud.device.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 * @ClassName DeviceCheckImgService
 * @Description  设备图片检测管理
 * @Author wangzhiwen
 * @Date 2021/3/8 9:53
 **/
public interface DeviceCheckImgService {

    /**
     * @Description 查询设备检测图片的key
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/11 17:06
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findDeviceCheckImgKey(String json);

    /**
     * @Description 上传设备检测图片的key
     *
     * @author wangzhiwen
     * @date 2021/3/8 9:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data uploadDeviceCheckImgKey(String json);

    /**
     * @Description 上传检测图片并计算
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/8 10:02
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data uploadDeviceCheckImgAndCompute(String json);
}
