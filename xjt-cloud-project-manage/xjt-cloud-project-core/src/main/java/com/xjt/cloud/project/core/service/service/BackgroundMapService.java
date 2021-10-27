package com.xjt.cloud.project.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 *@ClassName BackgroundMapService
 *@Author dwt
 *@Date 2020-09-02 15:48
 *@Version 1.0
 */
public interface BackgroundMapService {
    /**
     *@Author: dwt
     *@Date: 2020-09-02 15:44
     *@Param: json
     *@Return: Data
     *@Description 保存背景图
     */
    Data saveBackgroundMap(String json);

    /**
     *@Author: dwt
     *@Date: 2020-09-02 15:46
     *@Param: josn
     *@Return: Data
     *@Description 查询背景图
     */
    Data findBackgroundMap(String json);
}
