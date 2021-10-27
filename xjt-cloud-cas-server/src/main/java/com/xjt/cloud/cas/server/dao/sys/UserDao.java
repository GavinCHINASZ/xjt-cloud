package com.xjt.cloud.cas.server.dao.sys;


import com.xjt.cloud.cas.server.entity.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao{
    SysUser selectOne(String loginName);
}
