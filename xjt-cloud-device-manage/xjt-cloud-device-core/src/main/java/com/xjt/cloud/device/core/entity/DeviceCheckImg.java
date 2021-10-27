package com.xjt.cloud.device.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName DeviceCheckImg
 * @Description  设备图片检测管理
 * @Author wangzhiwen
 * @Date 2021/3/8 9:54
 **/
public class DeviceCheckImg extends BaseEntity {
    private String imgKey1;//检测的key图片
    private String imgKey2;//检测的key图片
    private String imgKey3;//检测的key图片
    private String checkImg;//检测的图片
    private Integer differ;//比图片时，两个色值之间的差值

    public String getImgKey1() {
        return imgKey1;
    }

    public void setImgKey1(String imgKey1) {
        this.imgKey1 = imgKey1;
    }

    public String getImgKey2() {
        return imgKey2;
    }

    public void setImgKey2(String imgKey2) {
        this.imgKey2 = imgKey2;
    }

    public String getImgKey3() {
        return imgKey3;
    }

    public void setImgKey3(String imgKey3) {
        this.imgKey3 = imgKey3;
    }

    public String getCheckImg() {
        return checkImg;
    }

    public void setCheckImg(String checkImg) {
        this.checkImg = checkImg;
    }

    public Integer getDiffer() {
        return differ;
    }

    public void setDiffer(Integer differ) {
        this.differ = differ;
    }
}
