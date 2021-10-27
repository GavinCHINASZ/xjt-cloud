package com.xjt.cloud.video.core.dao;

import com.xjt.cloud.video.core.entity.Video;

import java.util.List;

/**
 *@ClassName VideoDao
 *@Author dwt
 *@Date 2019-09-09 15:42
 *@Description video Dao层
 *@Version 1.0
 */
public interface VideoDao {
    /**
     *@Author: dwt
     *@Date: 2019-09-09 17:10
     *@Param: Video实体
     *@Return: java.lang.Integer
     *@Description 保存视频设备信息
     */
    Integer saveVideo(Video video);
    /**
     *@Author: dwt
     *@Date: 2019-09-10 10:16
     *@Param: Video实体
     *@Return: void
     *@Description 修改视频设备信息
     */
    Integer updateVideoDeviceInfoById(Video video);
    /**
     *@Author: dwt
     *@Date: 2019-09-09 16:59
     *@Param: Video实体
     *@Return: 视频设备列表
     *@Description 筛选符合条件的视频设备
     */
    List<Video> findVideoDeviceList(Video video);
    /**
     *@Author: dwt
     *@Date: 2019-09-11 14:01
     *@Param:
     *@Return: 视频设备列表
     *@Description 查询父id为空的视频设备
     */
    List<Video> findParentIdIsNullList();
}
