package com.xjt.cloud.message.core.entity;


/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/29 0029 13:45
 * @Description:
 */
public class User  {

    private Long id ;
    //员工姓名(成员姓名)
    private String userName;

    //用户ID
    private Long userId;

    public String getUserName() {
        return userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
