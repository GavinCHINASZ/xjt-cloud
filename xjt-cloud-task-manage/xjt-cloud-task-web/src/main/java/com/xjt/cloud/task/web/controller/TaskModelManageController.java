package com.xjt.cloud.task.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 任务Controller层
 *
 * @author dwt
 * @date 2019-07-26 1032
 */
@RestController
@RequestMapping("/taskModelManage/")
public class TaskModelManageController extends AbstractController {
    @Autowired
    private TaskModelManageService taskModelManageService;

    /**
     * 查询 模板管理list
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/09
     */
    @RequestMapping(value = "findTaskModelManageList/{projectId}")
    public Data findTaskModelManageList(String json) {
        return taskModelManageService.findTaskModelManageList(json);
    }

    /**
     * 删除 模板管理list
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/09
     */
    @RequestMapping(value = "updateTaskModelManage/{projectId}")
    public Data updateTaskModelManage(String json) {
        return taskModelManageService.updateTaskModelManage(json);
    }

    /**
     * 任务模板设备导入
     *
     * @param json 参数
     * @param file 表格
     * @return Data
     * @author huanggc
     * @date 2021/03/10
     */
    @RequestMapping(value = "uploadTaskModelDevice/{projectId}")
    public Data uploadTaskModelDevice(String json, MultipartFile[] file) {
        return taskModelManageService.uploadTaskModelDevice(json, file);
    }


    /**
     * 查询 模板设备list
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/10
     */
    @RequestMapping(value = "findTaskModelDeviceList/{projectId}")
    public Data findTaskModelDeviceList(String json) {
        return taskModelManageService.findTaskModelDeviceList(json);
    }

    /**
     * 删除 模板设备list
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/10
     */
    @RequestMapping(value = "deletedTaskModelDevice/{projectId}")
    public Data deletedTaskModelDevice(String json) {
        return taskModelManageService.deletedTaskModelDevice(json);
    }

    /**
     * 保存 模板设备
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/10
     */
    @RequestMapping(value = "saveTaskModelDevice/{projectId}")
    public Data saveTaskModelDevice(String json) {
        return taskModelManageService.saveTaskModelDevice(json);
    }

    /**
     * 更新 模板设备
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/17
     */
    @RequestMapping(value = "updateTaskModelDevice/{projectId}")
    public Data updateTaskModelDevice(String json) {
        return taskModelManageService.updateTaskModelDevice(json);
    }
}
