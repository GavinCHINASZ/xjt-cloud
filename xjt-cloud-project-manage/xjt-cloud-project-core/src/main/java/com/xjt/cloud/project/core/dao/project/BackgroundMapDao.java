package com.xjt.cloud.project.core.dao.project;

import com.xjt.cloud.project.core.entity.BackgroundMap;
import org.springframework.stereotype.Repository;

/**
 *@ClassName BackgroundMapDao
 *@Author dwt
 *@Date 2020-09-02 15:25
 *@Version 1.0
 */
@Repository
public interface BackgroundMapDao {
    /**
     *@Author: dwt
     *@Date: 2020-09-02 15:44
     *@Param: BackgroundMap
     *@Return: java.lang.Long
     *@Description 保存背景图
     */
    int saveBackgroundMap(BackgroundMap backgroundMap);
    /**
     *@Author: dwt
     *@Date: 2020-09-02 15:45
     *@Param: BackgroundMap
     *@Return: java.lang.Long
     *@Description 修改背景图
     */
    int modifyBackgroundMap(BackgroundMap backgroundMap);
    /**
     *@Author: dwt
     *@Date: 2020-09-02 15:46
     *@Param: BackgroundMap
     *@Return: BackgroundMap
     *@Description 查询背景图
     */
    BackgroundMap findBackgroundMap(BackgroundMap backgroundMap);
}
