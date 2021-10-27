package com.xjt.cloud.safety.core.dao.device;

import com.xjt.cloud.safety.core.entity.device.CheckRecord;

import java.util.List;

/**
 * @author wangzhiwen
 * @date 2020/4/12 15:55
 */
public interface CheckPointDao {

    /**
     * 功能描述:新增app检测
     *
     * @param checkRecord CheckRecord
     * @return com.xjt.cloud.device.core.entity.CheckRecord
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    Integer saveCheckPoint(CheckRecord checkRecord);

    /**
     * 功能描述:修改app检测
     *
     * @param checkRecord CheckRecord
     * @return com.xjt.cloud.device.core.entity.CheckRecord
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    Integer modifyCheckPoint(CheckRecord checkRecord);

    /**
     * 功能描述:查询app检测列表总行数
     *
     * @param checkRecord CheckRecord
     * @return Integer
     * @author wangzhiwen
     * @date 2019/7/19 13:54
     */
    Integer findCheckPointListCount(CheckRecord checkRecord);

    /**
     * 功能描述:查询app检测列表
     *
     * @param checkRecord CheckRecord
     * @return java.util.List<com.xjt.cloud.device.core.entity.CheckRecord>
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    List<CheckRecord> findCheckPointList(CheckRecord checkRecord);

    /**
     * 功能描述:查询 列表
     *
     * @param checkRecord CheckRecord
     * @return Integer
     * @author huanggc
     * @date 2021/04/21
     */
    Integer findCheckPointSysListCount(CheckRecord checkRecord);

    /**
     * 功能描述:查询 列表数量
     *
     * @param checkRecord CheckRecord
     * @return List<CheckRecord>
     * @author huanggc
     * @date 2021/04/21
     */
    List<CheckRecord> findCheckPointSysList(CheckRecord checkRecord);
}
