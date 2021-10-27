package com.xjt.cloud.admin.manage.dao.device;

import com.xjt.cloud.admin.manage.entity.device.CheckPoint;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:31
 * @Description:巡检点管理Dao
 */
@Repository
public interface CheckPointDao {

    /**
     *
     * 功能描述:查询巡检点列表总行数
     *
     * @param checkPoint
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    Integer findCheckPointListCount(CheckPoint checkPoint);
    /**
     *
     * 功能描述:查询巡检点列表
     *
     * @param checkPoint
     * @return: List<CheckPoint>
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    List<CheckPoint> findCheckPointList(CheckPoint checkPoint);

    /**
     *
     * 功能描述:查询巡检点列表
     *
     * @param checkPoint
     * @return: List<CheckPoint>
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    CheckPoint findCheckQrNoList(CheckPoint checkPoint);



    /**
     *
     * 功能描述:添加巡检点
     *
     * @param checkPoint
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    Integer saveCheckPoint(CheckPoint checkPoint);

}
