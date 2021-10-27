package com.xjt.cloud.admin.manage.dao.device;

import com.xjt.cloud.admin.manage.entity.device.Device;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName DeviceDao
 * @Description
 * @Author wangzhiwen
 * @Date 2020/10/10 17:38
 **/
@Repository
public interface DeviceDao {
    /**
     * @param device
     * @return List<Device>
     * @Description 查询设备列表
     * @author wangzhiwen
     * @date 2020/10/10 17:43
     */
   List<Device> findDeviceList(Device device);
}
