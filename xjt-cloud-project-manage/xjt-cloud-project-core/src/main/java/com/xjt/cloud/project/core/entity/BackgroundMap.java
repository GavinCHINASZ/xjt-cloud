package com.xjt.cloud.project.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName BackgroundMap
 * @Author dwt
 * @Date 2020-09-02 14:05
 * @Version 1.0
 */
public class BackgroundMap extends BaseEntity {
    private Integer setType;//背景图设置类型 1:默认平台，2:项目
    private String img1;//默认图片1
    private String img2;//默认图片2
    private String img3;//默认图片3
    private String img4;//默认图片4
    private String img5;//默认图片5
    private String img6;//默认图片6

    public Integer getSetType() {
        return setType;
    }

    public void setSetType(Integer setType) {
        this.setType = setType;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getImg5() {
        return img5;
    }

    public void setImg5(String img5) {
        this.img5 = img5;
    }

    public String getImg6() {
        return img6;
    }

    public void setImg6(String img6) {
        this.img6 = img6;
    }
}
