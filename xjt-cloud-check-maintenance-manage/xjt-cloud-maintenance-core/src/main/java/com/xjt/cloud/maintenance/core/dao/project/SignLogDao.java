package com.xjt.cloud.maintenance.core.dao.project;

import com.xjt.cloud.maintenance.core.entity.project.SignLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@ClassName SignLog
 *@Author dwt
 *@Date 2020-04-12 11:22
 *@Version 1.0
 */
@Repository
public interface SignLogDao {

    /**
     *@Author: dwt
     *@Date: 2020-04-12 14:08
     *@Param: SignLog
     *@Return: java.lang.Integer
     *@Description 保存检测员签到日志
     */
    Integer saveSignLog(SignLog signLog);
    /**
     *@Author: dwt
     *@Date: 2020-04-12 14:09
     *@Param: SignLog
     *@Return: List<SignLog>
     *@Description 查询项目检测签到日志
     */
    List<SignLog> findSignLogByProjectId(SignLog signLog);
    /**
     *@Author: dwt
     *@Date: 2020-04-12 14:09
     *@Param: SignLog
     *@Return: java.lang.Long
     *@Description 查询项目检测签到日志数量
     */
    Long findSignLogCountByProjectId(SignLog signLog);
    /**
     *@Author: dwt
     *@Date: 2020-04-12 14:11
     *@Param: SignLog
     *@Return: List<SignLog>
     *@Description 查询检测员签到日志
     */
    List<SignLog> findCheckUserSignLog(SignLog signLog);
}
