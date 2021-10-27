package com.xjt.cloud.device.core.dao.device;

import com.xjt.cloud.device.core.entity.*;
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
     * 功能描述:查询巡检点
     *
     * @param checkPoint
     * @return: CheckPoint
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    CheckPoint findCheckPoint(CheckPoint checkPoint);

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

    /**
     *
     * 功能描述:修改巡检点
     *
     * @param checkPoint
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    Integer modifyCheckPoint(CheckPoint checkPoint);

    /**
     *
     * 功能描述:查询巡检点与设备汇总信息
     *
     * @param pointDeviceReport
     * @return: PointDeviceReport
     * @auther: wangzhiwen
     * @date: 2019/8/15 9:45
     */
    PointDeviceReport findCheckPointAndDeviceReport(PointDeviceReport pointDeviceReport);

    /**
     *
     * 功能描述:查询巡检点与设备汇总信息按建筑物分组
     *
     * @param pointDeviceReport
     * @return: PointDeviceReport
     * @auther: wangzhiwen
     * @date: 2019/8/15 9:45
     */
    List<PointDeviceReport> findCheckPointAndDeviceReportGroupBuilding(PointDeviceReport pointDeviceReport);

    /**
     *
     * 功能描述:查询下载巡检点设备列表
     *
     * @param pointDevice
     * @return: List<PointDevice>
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    List<DownloadPointDevice> findDownloadPointDeviceList(PointDevice pointDevice);

    /**
     *
     * 功能描述:查询未关联水压设备的巡检点列表
     *
     * @param device
     * @return: List<CheckPoint>
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    List<CheckPoint> findCheckPointBoundIot(Device device);

    /**
     *
     * 功能描述:查询未关联水压设备的巡检点列表
     *
     * @param checkPoint
     * @return: List<CheckPoint>
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    List<Building> findBuildingFloorBoundIot(CheckPoint checkPoint);

    ///////////////////////////////////////////////上传表格处理///////////////////
    /**
     *
     * 功能描述: 新增巡检点信息
     *
     * @param list
     * @return: 
     * @auther: wangzhiwen
     * @date: 2019/8/8 16:41
     */
    int saveCheckPointList(List<PointDevice> list);

    /**
     *
     * 功能描述:新增巡设备信息
     *
     * @param list
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/8/8 16:41
     */
    int saveDeviceList(List<PointDevice> list);

    /**
     *
     * 功能描述: 以巡检点二维码列表，查询巡检点列表
     *
     * @param list
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/8/9 15:00
     */
    List<PointDevice> findPointListByList(List<PointDevice> list);
    /**
     *
     * 功能描述:修改巡检点信息
     *
     * @param list
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/8/8 16:41
     */
    int modifyCheckPointList(List<PointDevice> list);

    /**
     *
     * 功能描述:新增设备点信息
     *
     * @param list
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/8/8 16:41
     */
    int modifyDeviceList(List<PointDevice> list);
}
