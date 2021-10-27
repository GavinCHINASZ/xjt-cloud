package com.xjt.cloud.device.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.device.core.service.service.CheckPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:25
 * @Description:巡检点管理
 */
@RestController
@RequestMapping("/check/point/")
public class CheckPointController extends AbstractController {
    @Autowired
    private CheckPointService checkPointService;

    /**
     *
     * 功能描述:查询巡检点列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    @RequestMapping(value = "findCheckPointList/{projectId}")
    public Data findCheckPointList(String json){
        return checkPointService.findCheckPointList(json);
    }

    /**
     *
     * 功能描述:查询巡检点
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    @RequestMapping(value = "findCheckPoint/{projectId}")
    public Data findCheckPoint(String json){
        return checkPointService.findCheckPoint(json);
    }

    /**
     *
     * 功能描述:添加巡检点
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    @RequestMapping(value = "saveCheckPoint/{projectId}")
    public Data saveCheckPoint(String json){
        return checkPointService.saveCheckPoint(json);
    }

    /**
     *
     * 功能描述:修改巡检点
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    @RequestMapping(value = "modifyCheckPoint/{projectId}")
    public Data modifyCheckPoint(String json){
        return checkPointService.modifyCheckPoint(json);
    }

    /**
     *
     * 功能描述:删除巡检点
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/13 9:19
     */
    @RequestMapping(value = "delCheckPoint/{projectId}")
    public Data delCheckPoint(String json){
        return checkPointService.delCheckPoint(json);
    }

    /**
     *
     * 功能描述:查询巡检点与设备汇总信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/15 9:45
     */
    @RequestMapping(value = "findCheckPointAndDeviceReport/{projectId}")
    public Data findCheckPointAndDeviceReport(String json){
        return checkPointService.findCheckPointAndDeviceReport(json);
    }

    /**
     *
     * 功能描述: 上传巡检点设备excel表格
     *
     * @param file
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/5 9:29
     */
    @RequestMapping(value = "uploadPointDeviceExcel/{projectId}")
    public Data uploadPointDeviceExcel(String json, MultipartFile file){
        return checkPointService.uploadPointDeviceExcel(json, file);
    }

    /**
     *
     * 功能描述:下载巡检点设备列表
     *
     * @param json
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/8/7 9:37
     */
    @RequestMapping(value = "downloadPointDeviceExcel/{projectId}")
    public void downloadPointDeviceExcel(HttpServletResponse resp, String json){
        checkPointService.downloadPointDeviceExcel(resp,json);
    }

    /**
     *
     * 功能描述:下载巡检点设备列表
     *
     * @param json
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/8/7 9:37
     */
    @RequestMapping(value = "downloadPointDeviceExcelByList/{projectId}")
    public void downloadPointDeviceExcelByList(HttpServletResponse resp, String json){
        checkPointService.downloadPointDeviceExcel(resp,json);
    }

    /**
     *
     * 功能描述:查询未关联设备的巡检点列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "findCheckPointNotBoundIot/{projectId}")
    public Data findCheckPointNotBoundIot(String json){
        return checkPointService.findCheckPointNotBoundIot(json);
    }

    /**@MethodName: findProjectCheckPointCount
     * @Description: 查询当前项目巡查点总数
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/3/19 9:46
     **/
    @RequestMapping(value = "findProjectCheckPointCount/{projectId}")
    public Data findProjectCheckPointCount(String json){
        return checkPointService.findProjectCheckPointCount(json);
    }

    /**
     *
     * 功能描述:查询已关联物联设备的建物物楼层列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "findBuildingFloorBoundIot/{projectId}")
    public Data findBuildingFloorBoundIot(String json){
        return checkPointService.findBuildingFloorBoundIot(json);
    }
}
