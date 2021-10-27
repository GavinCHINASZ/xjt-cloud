package com.xjt.cloud.iot.core.dao.iot.linkage;

import com.xjt.cloud.iot.core.entity.linkage.LinkageAction;
import com.xjt.cloud.iot.core.entity.linkage.LinkageDevice;
import com.xjt.cloud.iot.core.entity.linkage.LinkageDeviceReport;
import com.xjt.cloud.iot.core.entity.linkage.LinkageDeviceTable;
import com.xjt.cloud.iot.core.entity.project.Building;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 声光报警设备(真实设备)
 *
 * @author huanggc
 * @date 2020/08/25
 **/
@Repository
public interface LinkageDeviceTableDao {

    /**
     * 功能描述:查询 声光报警设备表数量
     *
     * @param linkageDeviceTable 声光报警设备表实体
     * @author huanggc
     * @date 2020/08/25
     * @return Integer
     **/
    Integer findDeviceTableTotalCount(LinkageDeviceTable linkageDeviceTable);

    /**
     * 功能描述:查询 声光报警设备
     *
     * @param linkageDeviceTable 声光报警设备表实体
     * @author huanggc
     * @date 2020/08/25
     * @return List<LinkageDeviceTable>
     **/
    List<LinkageDeviceTable> findDeviceTableList(LinkageDeviceTable linkageDeviceTable);

    /**
     * 功能描述:更新 声光报警设备表
     *
     * @param linkageDeviceTable 声光报警设备表实体
     * @author huanggc
     * @date 2020/08/25
     * @return Integer
     **/
    Integer updateDeviceTable(LinkageDeviceTable linkageDeviceTable);

}
