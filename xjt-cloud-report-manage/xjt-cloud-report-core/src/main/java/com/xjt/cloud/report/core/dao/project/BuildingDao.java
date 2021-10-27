package com.xjt.cloud.report.core.dao.project;

import com.xjt.cloud.report.core.entity.project.Building;
import org.springframework.stereotype.Repository;

/**
 * @program: xjt-cloud-project-manage
 * @description:
 * @author: zxb
 * @create: 2019-07-31 14:05
 **/
@Repository
public interface BuildingDao {

    /**
     * @Description: 根据 设备id 查询建筑物
     * @Param: deviceId 设备ID
     * @Author: huanggc
     * @Date:2019/11/20
     * @Return: Building
     **/
    Building findByDeviceId(Long deviceId);
}
