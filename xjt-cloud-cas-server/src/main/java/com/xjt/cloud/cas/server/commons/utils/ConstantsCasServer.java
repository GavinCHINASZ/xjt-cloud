package com.xjt.cloud.cas.server.commons.utils;

import com.xjt.cloud.commons.utils.PropertyUtils;

/**
 * @ClassName ConstantsCasServer
 * @Description
 * @Author wangzhiwen
 * @Date 2020/8/25 9:22
 **/
public interface ConstantsCasServer {
    //是否要删除token信息 true为删除，默认不删除
    String REMOVE_ACCESS_TOKEN = PropertyUtils.getProperty("remove.access.token");
}
