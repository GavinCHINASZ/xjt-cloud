package com.xjt.cloud.device.core.dao.device;

import com.xjt.cloud.device.core.entity.DeviceCheckImg;
import org.springframework.stereotype.Repository;

/**
 * @ClassName DeviceCheckImgDao
 * @Description  设备图片检测管理
 * @Author wangzhiwen
 * @Date 2021/3/8 9:53
 **/
@Repository
public interface DeviceCheckImgDao {

    /**
     * @Description 保存设备图片检测信息
     *
     * @param deviceCheckImg
     * @author wangzhiwen
     * @date 2021/3/8 10:41
     * @return
     */
    int saveDeviceCheckImg(DeviceCheckImg deviceCheckImg);

    /**
     * @Description 保存设备图片检测信息
     *
     * @param deviceCheckImg
     * @author wangzhiwen
     * @date 2021/3/8 10:41
     * @return
     */
    int modifyDeviceCheckImg(DeviceCheckImg deviceCheckImg);

    /**
     * @Description 查询设备图片检测信息
     *
     * @param deviceCheckImg
     * @author wangzhiwen
     * @date 2021/3/8 10:41
     * @return
     */
    DeviceCheckImg findDeviceCheckImg(DeviceCheckImg deviceCheckImg);
}
