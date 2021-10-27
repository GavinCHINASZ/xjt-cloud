package com.xjt.cloud.admin.manage.entity.sys;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.Date;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:50
 * @Description: 问题反馈管理实体类
 */
public class Feedback extends BaseEntity {
    //反馈问题用户手机号码
    private String userPhone;
    //问题图片1
    private String imgUrl1;
    //问题图片2
    private String imgUrl2;
    //问题图片3
    private String imgUrl3;
    //问题图片4
    private String imgUrl4;
    //问题内容
    private String content;
    //处理人
    private String handleUserName;
    //处理时间
    private Date handleTime;
    //备注
    private String memo;
    //项目名称
    private String projectName;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getImgUrl1() {
        return imgUrl1;
    }

    public void setImgUrl1(String imgUrl1) {
        this.imgUrl1 = imgUrl1;
    }

    public String getImgUrl2() {
        return imgUrl2;
    }

    public void setImgUrl2(String imgUrl2) {
        this.imgUrl2 = imgUrl2;
    }

    public String getImgUrl3() {
        return imgUrl3;
    }

    public void setImgUrl3(String imgUrl3) {
        this.imgUrl3 = imgUrl3;
    }

    public String getImgUrl4() {
        return imgUrl4;
    }

    public void setImgUrl4(String imgUrl4) {
        this.imgUrl4 = imgUrl4;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHandleUserName() {
        return handleUserName;
    }

    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStatusDesc(){
        if (getStatus() != null){
            int s = getStatus();
            if (s == 1){
                return "未处理";
            }else if(s == 2){
                return "处理中";
            }else if(s == 3){
                return "已修复";
            }else if(s == 4){
                return "无法修复";
            }else if(s == 5){
                return "不存在";
            }else if(s == 99){
                return "删除";
            }
        }
        return "";
    }
}
