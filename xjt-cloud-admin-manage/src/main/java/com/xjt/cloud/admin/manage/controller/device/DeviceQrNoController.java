package com.xjt.cloud.admin.manage.controller.device;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.device.CheckPoint;
import com.xjt.cloud.admin.manage.entity.device.DeviceQrNo;
import com.xjt.cloud.admin.manage.service.service.device.DeviceQrNoService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName DeviceQrNoController
 * @Author dwt
 * @Date 2020-10-28 10:51
 * @Version 1.0
 */
@Controller
@RequestMapping("/device/qrNo/")
public class DeviceQrNoController extends AbstractController {

    @Autowired
    private DeviceQrNoService deviceQrNoService;

    /**
     *@Author: dwt
     *@Date: 2020-10-28 10:57
     *@Param:
     *@Return:
     *@Description 调到设备二维码页面
     */
    @RequestMapping("toDeviceQrNoListPage")
    public ModelAndView toDeviceQrNoListPage(){
        return new ModelAndView("device/deviceQrNoList");
    }


    /**
     *@Author: dwt
     *@Date: 2020-10-28 10:11
     *@Param:
     *@Return:
     *@Description 二维码上传
     */
    @RequestMapping(value = "uploadDeviceQrNorExcelFile")
    @ResponseBody
    public Data uploadDeviceQrNorExcelFile(MultipartFile file){
        return deviceQrNoService.uploadDeviceQrNorExcelFile(file);
    }

    @RequestMapping(value = "downDeviceQrNoModelExcel")
    public void downDeviceQrNoModelExcel(HttpServletResponse response){
        deviceQrNoService.downDeviceQrNoModelExcel(response);
    }

    /**
     * @Description 查询巡检点id列表
     *
     * @param checkPoint
     * @author wangzhiwen
     * @date 2020/11/23 15:40
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.device.CheckPoint>
     */
    @RequestMapping(value = "findCheckPointQrNoList")
    @ResponseBody
    public ScriptPage<CheckPoint> findCheckPointQrNoList(AjaxPage ajaxPage,CheckPoint checkPoint){
        return deviceQrNoService.findCheckPointQrNoList(ajaxPage, checkPoint);
    }

    /**
     * @Description 保存巡检点id
     *
     * @param checkPoint
     * @author wangzhiwen
     * @date 2020/11/23 15:43
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveCheckPoint")
    @ResponseBody
    public Data saveCheckPoint(CheckPoint checkPoint){
        return deviceQrNoService.saveCheckPoint(checkPoint);
    }

    /**
     * @Description 保存巡检点id
     *
     * @param deviceQrNo
     * @author wangzhiwen
     * @date 2020/11/23 15:43
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "modifyCheckPoint")
    @ResponseBody
    public Data modifyCheckPoint(DeviceQrNo deviceQrNo){
        return deviceQrNoService.modifyCheckPoint(deviceQrNo);
    }
}
