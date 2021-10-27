package com.xjt.cloud.video.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 *@ClassName VideoService
 *@Author dwt
 *@Date 2019-09-09 15:40
 *@Description video 服务层
 *@Version 1.0
 */
public interface VideoService {

    /**
     *@Author: dwt
     *@Date: 2019-09-09 15:48
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 保存Video信息
     */
    Data saveVideo(String json);

    /**
     *@Author: dwt
     *@Date: 2019-09-10 10:26
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 修改视频设备信息
     */
    Data modifyVideoDeviceById(String json);

    /**
     *@Author: dwt
     *@Date: 2019-09-10 10:38
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 根据条件查询视频设备列表
     */
    Data getVideoDeviceList(String json);

    /**
     *@Author: dwt
     *@Date: 2019-09-11 15:23
     *@Param:
     *@Return: void
     *@Description 定时任务处理接口
     */
    void NVRInfoHandle();
}
