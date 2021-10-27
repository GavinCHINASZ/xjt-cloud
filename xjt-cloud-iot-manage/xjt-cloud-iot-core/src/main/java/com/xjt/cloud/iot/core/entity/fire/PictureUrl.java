package com.xjt.cloud.iot.core.entity.fire;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName PictureUrl
 * @Author dwt
 * @Date 2020-05-12 9:44
 * @Description 事件图片路径
 * @Version 1.0
 */
public class PictureUrl extends BaseEntity {
    private Long eventId;
    private String url;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
