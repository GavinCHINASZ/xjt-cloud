package com.xjt.cloud.iot.core.dao.iot.water;

import com.xjt.cloud.iot.core.entity.water.WaterDeviceReport;
import com.xjt.cloud.iot.core.entity.water.WaterDevice;
import com.xjt.cloud.iot.core.entity.linkage.LinkageRequirement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/23 17:48
 * @Description:
 */
@Repository
public interface WaterDeviceDao {

    /**
     *
     * 功能描述:检查传感器id是否存在
     *
     * @param waterDevice
     * @return: List<WaterDevice>
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    WaterDevice checkWaterDeviceSensorNo(WaterDevice waterDevice);

    /**
     *
     * 功能描述:保存水压设备信息
     *
     * @param waterDevice
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    int saveWaterDevice(WaterDevice waterDevice);

    /**
     *
     * 功能描述:添加水压设备设备值修改记录
     *
     * @param waterDevice
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    int saveWaterDeviceEdit(WaterDevice waterDevice);

    /**
     *
     * 功能描述:查询水压设备修改记录列表
     *
     * @param waterDevice
     * @return: List<WaterDevice>
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    List<WaterDevice> findWaterDeviceEditList(WaterDevice waterDevice);

    /**
     *
     * 功能描述:修改水压设备信息
     *
     * @param waterDevice
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    int modifyWaterDevice(WaterDevice waterDevice);
    /**
     *
     * 功能描述:删除水压设备信息
     *
     * @param waterDevice
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    int delWaterDevice(WaterDevice waterDevice);

    /**
     *
     * 功能描述:查询水压设备信息列表
     *
     * @param waterDevice
     * @return: List<WaterDevice>
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    List<WaterDevice> findWaterDeviceList(WaterDevice waterDevice);

    /**
     *
     * 功能描述:查询水压设备信息列表
     *
     * @param waterDevice
     * @return: WaterDevice
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    WaterDevice findWaterDevice(WaterDevice waterDevice);

    /**
     *
     * 功能描述:查询水压设备信息列表
     *
     * @param waterDevice
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    int findWaterDeviceListTotalCount(WaterDevice waterDevice);

    /**
     *
     * 功能描述:查询水压设备汇总报表
     *
     * @param waterDevice
     * @return: WaterDeviceReport
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:59
     */
    WaterDeviceReport findWaterDeviceSummaryReport(WaterDevice waterDevice);

    /**
     * 功能描述: 根据 触发条件 查询水设备
     *
     * @param linkageRequirementList 触发条件 实体list
     * @return: List<WaterDevice> 水设备实体list
     * @auther: huanggc
     * @date: 2019/10/23
     */
    List<WaterDevice> findByLinkageRequirement(List<LinkageRequirement> linkageRequirementList);

    /**
     *
     * 功能描述: 保存水设备离线事件信息
     *
     * @param date
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/11/21 10:57
     */
    Integer saveWaterOffLineEvent(@Param("date") Date date);

    /**
     *
     * 功能描述: 保存水设备离线记录信息
     *
     * @param date
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/11/21 10:57
     */
    Integer saveWaterOffLineRecord(@Param("date") Date date);

    /**
     *
     * 功能描述:水压设备离线任务
     * @param waterDevice
     * @return: 此次设备离线的设备数
     * @auther: wangzhiwen
     * @date: 2019/11/19 14:44
     */
    Integer waterOffLineTask(WaterDevice waterDevice);

    /**
     *
     * 功能描述: 水压设备离线，修改设备信息中的物联状态
     *
     * @param date
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/12/5 16:09
     */
    int waterOffLineModifyDeviceIotStatus(@Param("date") Date date);

    /**
     *
     * 功能描述: 查询时间内传感器是否绑定过
     *
     * @param waterDevice
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/12/5 16:09
     */
    WaterDevice findWaterDeviceDelTime(WaterDevice waterDevice);
    /**
     *@Author: dwt
     *@Date: 2020-08-10 14:05
     *@Param: WaterDevice
     *@Return: WaterDeviceReport
     *@Description 查询消火栓设备总数和故障数
     */
    WaterDeviceReport findHydrantDeviceNum(WaterDevice waterDevice);
    /**
     *@Author: dwt
     *@Date: 2020-08-10 14:13
     *@Param: WaterDevice
     *@Return: WaterDevice
     *@Description 查询项目消火栓设备巡查点信息列表
     */
    List<WaterDevice> findHydrantDeviceCheckPoints(WaterDevice waterDevice);
    /**
     *@Author: dwt
     *@Date: 2020-08-11 15:34
     *@Param: WaterDevice
     *@Return: int
     *@Description 修改巡查点经纬度
     */
    int modifyCheckPointLatAndLng(WaterDevice waterDevice);
}
