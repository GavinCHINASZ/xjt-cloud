package com.xjt.cloud.task.core.service.service;

import com.xjt.cloud.commons.utils.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 模板管理
 * 
 * @author huanggc
 * @date 2020/03/09
 */
public interface TaskModelManageService {

    /**
     * 查询 模板管理list
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/09
     */
    Data findTaskModelManageList(String json);

    /**
     * 删除 模板管理list
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/09
     */
    Data updateTaskModelManage(String json);



    /**
     * 任务模板设备导入
     *
     * @param json 参数
     * @param file 表格
     * @return Data
     * @author huanggc
     * @date 2021/03/10
     */
    Data uploadTaskModelDevice(String json, MultipartFile[] file);

    /**
     * 查询 模板设备list
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/10
     */
    Data findTaskModelDeviceList(String json);

    /**
     * 删除 模板设备list
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/10
     */
    Data deletedTaskModelDevice(String json);

    /**
     * 保存 模板设备
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/10
     */
    Data saveTaskModelDevice(String json);

    /**
     * 更新 模板设备
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/03/17
     */
    Data updateTaskModelDevice(String json);
}
