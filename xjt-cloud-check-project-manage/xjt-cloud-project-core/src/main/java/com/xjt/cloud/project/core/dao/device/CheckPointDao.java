package com.xjt.cloud.project.core.dao.device;

import com.xjt.cloud.project.core.entity.device.CheckRecord;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/12 15:55
 * @Description:
 */
public interface CheckPointDao {

    /**
     *
     * 功能描述:新增app检测
     *
     * @param
     * @return: com.xjt.cloud.device.core.entity.CheckRecord
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Integer saveCheckPoint(CheckRecord checkRecord);

    /**
     *
     * 功能描述:修改app检测
     *
     * @param
     * @return: com.xjt.cloud.device.core.entity.CheckRecord
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Integer modifyCheckPoint(CheckRecord checkRecord);
    /**
     *
     * 功能描述:查询app检测列表总行数
     *
     * @param checkRecord
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    Integer findCheckPointListCount(CheckRecord checkRecord);

    /**
     *
     * 功能描述:查询app检测列表
     *
     * @param
     * @return: java.util.List<com.xjt.cloud.device.core.entity.CheckRecord>
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    List<CheckRecord> findCheckPointList(CheckRecord checkRecord);
}
