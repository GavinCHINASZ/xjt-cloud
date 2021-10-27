package com.xjt.cloud.admin.manage.service.service.device;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.device.CheckPoint;
import com.xjt.cloud.admin.manage.entity.device.DeviceQrNo;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 *@ClassName DeviceQrNoService
 *@Author dwt
 *@Date 2020-10-28 10:01
 *@Version 1.0
 */
public interface DeviceQrNoService {
    /**
     *@Author: dwt
     *@Date: 2020-10-28 10:11
     *@Param:
     *@Return:
     *@Description 二维码上传
     */
    Data uploadDeviceQrNorExcelFile(MultipartFile file);

    void downDeviceQrNoModelExcel(HttpServletResponse response);

    /**
     * @Description 查询巡检点id列表
     *
     * @param checkPoint
     * @author wangzhiwen
     * @date 2020/11/23 15:40
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.device.CheckPoint>
     */
    ScriptPage<CheckPoint> findCheckPointQrNoList(AjaxPage ajaxPage, CheckPoint checkPoint);

    /**
     * @Description 保存巡检点id
     *
     * @param checkPoint
     * @author wangzhiwen
     * @date 2020/11/23 15:43
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveCheckPoint(CheckPoint checkPoint);

    /**
     * @Description 修改巡检点id
     *
     * @param deviceQrNo
     * @author wangzhiwen
     * @date 2020/11/23 15:43
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data modifyCheckPoint(DeviceQrNo deviceQrNo);
}
