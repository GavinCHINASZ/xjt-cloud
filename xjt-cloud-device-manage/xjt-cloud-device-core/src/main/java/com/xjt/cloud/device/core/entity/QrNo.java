package com.xjt.cloud.device.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:29
 * @Description:二维码信息实体类
 */
public class QrNo extends BaseEntity {
    private String qrNo;//二维码
    private Integer num;//生成二维码个数
    private String pointLocation;// 巡检位置
    private String pointName;// 巡检名称
    private String imgStr;//二维码图片base64字符串
    private Integer type;//类型 1 巡检点码 2 设备码
    private Integer oldStatus;//是否是老的二维码 1否 2是
    private String content;//内容
    private String[] qrNos;//要生成二维码的数组

    public String[] getQrNos() {
        return qrNos;
    }

    public void setQrNos(String[] qrNos) {
        this.qrNos = qrNos;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
    }
}
