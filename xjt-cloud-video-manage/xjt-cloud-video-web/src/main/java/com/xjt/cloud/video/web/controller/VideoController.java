package com.xjt.cloud.video.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.video.core.service.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName VideoController
 * @Author dwt
 * @Date 2019-09-09 15:44
 * @Description video控制层
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/video/")
public class VideoController extends AbstractController {

    @Autowired
    private VideoService videoService;

    /**
     *@Author: dwt
     *@Date: 2019-09-10 10:04
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 保存视频设备信息
     */
    @RequestMapping(value = "saveVideo/{projectId}")
    public Data saveVideo(String json){
        return videoService.saveVideo(json);
    }
    /**
     *@Author: dwt
     *@Date: 2019-09-10 10:31
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 修改视频设备信息
     */
    @RequestMapping(value = "modifyVideoDevice/{projectId}")
    public Data modifyVideoDeviceInfoById(String json){
        return videoService.modifyVideoDeviceById(json);
    }

    /**
     *@Author: dwt
     *@Date: 2019-09-10 10:47
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 筛选视频设备
     */
    @RequestMapping(value = "findVideoDeviceList/{projectId}")
    public Data findVideoDeviceList(String json){
        return videoService.getVideoDeviceList(json);
    }
    /**
     *@Author: dwt
     *@Date: 2019-09-11 15:24
     *@Param:
     *@Return: void
     *@Description 视频设备状态定时任务处理接口
     */
    @RequestMapping(value = "handle/{projectId}")
    public void videoHandle(){
        videoService.NVRInfoHandle();
    }
}
