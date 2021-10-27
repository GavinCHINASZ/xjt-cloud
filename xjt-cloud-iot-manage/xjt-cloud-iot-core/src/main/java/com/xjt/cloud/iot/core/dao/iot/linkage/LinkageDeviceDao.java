package com.xjt.cloud.iot.core.dao.iot.linkage;

import com.xjt.cloud.iot.core.entity.linkage.LinkageAction;
import com.xjt.cloud.iot.core.entity.linkage.LinkageDevice;
import com.xjt.cloud.iot.core.entity.linkage.LinkageDeviceReport;
import com.xjt.cloud.iot.core.entity.project.Building;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 联动设备
 * 
 * @author huanggc
 * @date 2019/09/19
 **/
@Repository
public interface LinkageDeviceDao {
    /**
     * 功能描述:查询 联动设备 列表
     *
     * @param linkageDevice 联动设备 实体
     * @author huanggc
     * @date 2019/09/19
     * @return List<LinkageDevice>
     */
    List<LinkageDevice> findLinkageDeviceList(LinkageDevice linkageDevice);

    /**
     * 功能描述:查询 联动设备 汇总
     *
     * @param linkageDevice 联动设备
     * @author huanggc
     * @date 2019/09/19
     * @return com.xjt.cloud.commons.utils.Data
     */
    LinkageDeviceReport findLinkageDeviceSummary(LinkageDevice linkageDevice);

    /**
     * 功能描述:查询 联动设备 总条数
     *
     * @param linkageDevice 联动设备 实体
     * @author huanggc
     * @date 2019/09/19
     * @return Integer
     */
    Integer findLinkageDeviceTotalCount(LinkageDevice linkageDevice);

    /**
     * 功能描述:删除 联动设备
     *
     * @param ids 联动id数组
     * @author huanggc
     * @date 2020/08/20
     * @return com.xjt.cloud.commons.utils.Data
     */
    Integer deleteLinkageDevice(Long[] ids);

    /**
     * 功能描述:联动设备--新增
     *
     * @param linkageDevice 联动设备  实体
     * @author huanggc
     * @date 2019/09/19
     * @return Integer
     */
    Integer saveLinkageDevice(LinkageDevice linkageDevice);

    /**
     * 功能描述:联动设备--更新
     *
     * @param linkageDevice 联动设备  实体
     * @author huanggc
     * @date 2019/09/19
     * @return Integer
     */
    Integer updateLinkageDevice(LinkageDevice linkageDevice);

    /**
     * 功能描述:查询 联动控制 列表
     *
     * @param linkageAction 触发动作 实体
     * @author huanggc
     * @date 2019/09/26
     * @return List<String> 联动设备名称(去重)
     */
    List<String> findNames(LinkageAction linkageAction);

    /**
     * 功能描述:查询 联动设备
     *
     * @param linkageDevice 联动设备 实体
     * @author huanggc
     * @date 2019/10/22
     * @return LinkageDevice 联动设备
     */
    LinkageDevice findLinkageDevice(LinkageDevice linkageDevice);

    /**
     * 查询的所有联动设备ID
     *
     * @param linkageDevice 联动设备
     * @return List<LinkageDevice> LinkageDevice
     * @author zhangZaiFa
     * @date 2020/3/31 10:51
     **/
    List<LinkageDevice> findAllLinkageDevices(LinkageDevice linkageDevice);

    /**
     * 修改联动设备离线状态
     *
     * @param date 时间
     * @author zhangZaiFa
     * @date 2020/3/31 11:06
     * @return Integer
     **/
    Integer updateLinkageDeviceOffLineStatus(@Param("date") Integer date);

    /**
     * 更新传感器ID的心跳时间
     *
     * @param sensorId 传感器ID
     * @author zhangZaiFa
     * @date 2020/3/31 11:49
     **/
    void updateLinkageDeviceEndHeartbeatTime(@Param("sensorId") String sensorId);

    /**
     * 查询 设备ID
     *
     * @param ids 联动ID数组
     * @author hunaggc
     * @date 2020/08/20
     * @return Long[]
     **/
    Long[] findDeviceIdList(Long[] ids);

    /**
     * 功能描述:查询 声光报警设备所在的建筑物
     *
     * @param linkageDevice 声光报警设备实体
     * @author huanggc
     * @date 2020/08/21
     * @return  List<Building> 建筑物list
     **/
    List<Building> findlinkageDeviceBuilding(LinkageDevice linkageDevice);

    /**
     * 功能描述:查询 声光报警设备所有的巡查点
     *
     * @param linkageDevice 声光报警设备实体
     * @author huanggc
     * @date 2020/08/21
     * @return List<LinkageDevice>
     **/
    List<LinkageDevice> findLinkageDeviceCheckPoint(LinkageDevice linkageDevice);

    /**
     * 功能描述:查询 声光报警设备
     *
     * @param linkageDevice 声光报警设备实体
     * @author huanggc
     * @date 2020/08/24
     * @return List<LinkageDevice> 声光报警设备实体list
     **/
    List<LinkageDevice> findLinkageDeviceAndRelation(LinkageDevice linkageDevice);

    /**
     * 功能描述:查询 声光报警设备
     *
     * @param date 时间
     * @author huanggc
     * @date 2020/08/24
     * @return List<LinkageDevice> 声光报警设备实体list
     **/
    List<LinkageDevice> findLinkageDeviceOffLineTaskList(@Param("date") Integer date);

    /**
     * 功能描述:批量更新 声光报警设备
     *
     * @param linkageDeviceList 声光报警设备实体
     * @author huanggc
     * @date 2020/08/27
     * @return Integer
     **/
    Integer updateLinkageDeviceList(List<LinkageDevice> linkageDeviceList);

    /**
     * 功能描述:批量更新 声光报警设备
     *
     * @param linkageDevice 声光报警设备实体
     * @author huanggc
     * @date 2020/08/27
     **/
    void updateLinkageDeviceBySensorId(LinkageDevice linkageDevice);
}
