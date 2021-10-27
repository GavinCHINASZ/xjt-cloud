package com.xjt.cloud.admin.manage.service.service.sys;

/**
 * @ClassName SysService
 * @Description
 * @Author wangzhiwen
 * @Date 2020/10/26 9:35
 **/
public interface SysService {

    /**
     * @Description 数据库主从监听任务
     * @author wangzhiwen
     * @date 2020/10/26 9:37
     */
    void databaseMasterSlaveCheckTask();
}
