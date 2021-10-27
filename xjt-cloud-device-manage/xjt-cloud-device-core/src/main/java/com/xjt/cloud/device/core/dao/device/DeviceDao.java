package com.xjt.cloud.device.core.dao.device;

import com.xjt.cloud.device.core.entity.Device;
import com.xjt.cloud.device.core.entity.DeviceRemind;
import com.xjt.cloud.device.core.entity.PointDeviceReport;
import com.xjt.cloud.device.core.entity.ReportDeviceRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:32
 * @Description:设备管理Dao
 */
@Repository
public interface DeviceDao {
    /**
     *
     * 功能描述:查询设备列表
     *
     * @param device
     * @return: List<Device>
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    Integer findDeviceListTotalCount(Device device);
    /**
     *
     * 功能描述:查询设备列表
     *
     * @param device
     * @return: List<Device>
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    List<Device> findDeviceList(Device device);

    /**
     * 功能描述:查询设备列表 树结构
     *
     * @param device
     * @auther huanggc
     * @date 2020/08/24
     * @return com.xjt.cloud.commons.utils.Data
     */
    List<Device> findDeviceListTree(Device device);

    /**
     *
     * 功能描述:查询未关联水压设备的设备列表
     *
     * @param device
     * @return: List<Device>
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    List<Device> findDeviceListBoundIot(Device device);

    /**
     *
     * 功能描述:查询设备
     *
     * @param device
     * @return: Device
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    Device findDevice(Device device);

    /**
     *
     * 功能描述:新增设备信息
     *
     * @param device
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    Integer saveDevice(Device device);

    /**
     *
     * 功能描述:查询设备汇总信息
     *
     * @param pointDeviceReport
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    PointDeviceReport findDeviceReport(PointDeviceReport pointDeviceReport);
    /**
     *
     * 功能描述:修改设备信息
     *
     * @param device
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    Integer modifyDevice(Device device);

    /**
     *
     * 功能描述:修改设备信息
     *
     * @param lise
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    Integer saveDevices(List<Device> lise);

    /**
     *
     * 功能描述:巡检点批量清除设备绑定
     *
     * @param device
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    Integer pointClearBindDevices(Device device);

    /**
     *
     * 功能描述: 查询设备档案基本信息
     *
     * @param reportDeviceRecord
     * @return: ReportDeviceRecord
     * @auther: wangzhiwen
     * @date: 2019/9/10 15:24
     */
    ReportDeviceRecord reportFindDeviceRecordBaseInfo(ReportDeviceRecord reportDeviceRecord);

    /**
     *
     * 功能描述: 查询设备档案基本信息
     *
     * @param reportDeviceRecord
     * @return: List<ReportDeviceRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/10 15:24
     */
    List<ReportDeviceRecord> reportFindDeviceRecordList(ReportDeviceRecord reportDeviceRecord);

    /**
     *
     * 功能描述: 查询设备档案基本信息总数
     *
     * @param reportDeviceRecord
     * @return: List<ReportDeviceRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/10 15:24
     */
    ReportDeviceRecord findDeviceRecordCount(ReportDeviceRecord reportDeviceRecord);

    /**
     *
     * 功能描述: 查询设备档案基本信息
     *
     * @param reportDeviceRecord
     * @return: List<ReportDeviceRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/10 15:24
     */
    int reportFindDeviceRecordListTotalCount(ReportDeviceRecord reportDeviceRecord);

    /**
     *
     * 功能描述: 以巡检点id查询设备档案基本信息总数
     *
     * @param reportDeviceRecord
     * @return: List<ReportDeviceRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/10 15:24
     */
    ReportDeviceRecord findDeviceRecordCountByPoint(ReportDeviceRecord reportDeviceRecord);

    /**
     * 功能描述: 查询 项目内要过期的设备
     *
     * @param deviceRemind
     * @return List<Device>
     * @auther huanggc
     * @date 2020-03-30
     */
    List<Device> findDeviceRemindTask(DeviceRemind deviceRemind);

    /**
     * 功能描述: 查询 项目内要送修的设备
     *
     * @param deviceRemind
     * @return List<Device>
     * @auther huanggc
     * @date 2020-03-30
     */
    List<Device> findRepairRemindTask(DeviceRemind deviceRemind);

    /**
     *
     * 功能描述: 以巡检点id查询设备档案基本信息总数
     *
     * @param reportDeviceRecord
     * @return: List<ReportDeviceRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/10 15:24
     */
    List<ReportDeviceRecord> findDeviceReportPie(ReportDeviceRecord reportDeviceRecord);
}
