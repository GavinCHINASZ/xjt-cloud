package com.xjt.cloud.safety.core.service.service.project;

import com.xjt.cloud.commons.utils.Data;

/**
 * @ClassName SignLogService
 * @Author dwt
 * @Date 2020-04-12 14:13
 */
public interface SignLogService {

    /**
     * @Author: dwt
     * @Date: 2020-04-12 14:08
     * @Param: json
     * @Return: Data
     * @Description 保存检测员签到日志
     */
    Data saveSignLog(String json);

    /**
     * @Author: dwt
     * @Date: 2020-04-12 14:09
     * @Param: json
     * @Return: Data
     * @Description 查询项目检测签到日志
     */
    Data findSignLogByProjectId(String json);

    /**
     * @Author: dwt
     * @Date: 2020-04-12 14:11
     * @Param: json
     * @Return: Data
     * @Description 查询检测员签到日志
     */
    Data findCheckUserSignLog(String json);
}
