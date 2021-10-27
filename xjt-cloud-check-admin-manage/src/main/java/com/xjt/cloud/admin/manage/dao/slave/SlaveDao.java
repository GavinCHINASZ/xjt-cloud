package com.xjt.cloud.admin.manage.dao.slave;

import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @ClassName slave
 * @Description
 * @Author wangzhiwen
 * @Date 2020/10/26 9:38
 **/
@Repository
public interface SlaveDao {
    /**
     * @Description 数据库主从监听任务
     * @author wangzhiwen
     * @date 2020/10/26 9:37
     */
    Map<String,Object> findDatabaseMasterSlaveStatus();
}
