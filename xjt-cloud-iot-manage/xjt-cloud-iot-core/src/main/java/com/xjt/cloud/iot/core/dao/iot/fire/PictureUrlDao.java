package com.xjt.cloud.iot.core.dao.iot.fire;

import com.xjt.cloud.iot.core.entity.fire.FireAlarmEvent;

/**
 *@ClassName PictureUrl
 *@Author dwt
 *@Date 2020-05-12 10:24
 *@Version 1.0
 */
public interface PictureUrlDao {
    /**
     *@Author: dwt
     *@Date: 2020-05-12 10:26
     *@Param: FireAlarmEvent
     *@Return: java.lang.Integer
     *@Description 保存事件处理照片路径
     */
    Integer saveEventHandlePictures(FireAlarmEvent event);
    /**
     *@Author: dwt
     *@Date: 2020-05-12 10:28
     *@Param: java.lang.Long
     *@Return: java.lang.String
     *@Description 根据事件id查询图片路径
     */
    String[] findPictureUrlByEventId(Long eventId);
    /**
     *@Author: dwt
     *@Date: 2020-05-15 14:30
     *@Param: java.lang.Long
     *@Return: void
     *@Description 删除事件图片
     */
    void deletePictureUrlByEventId(Long eventId);
}
