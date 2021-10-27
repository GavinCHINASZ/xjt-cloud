package com.xjt.cloud.task.api.controller.protect;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.service.service.protect.ProtectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  地铁 班前防护 Controller
 *
 * @author huangGuiChuan
 * @date 2020/09/27
 */
@RestController
@RequestMapping("/protect/")
public class ProtectController extends AbstractController {
    @Autowired
    private ProtectService protectService;

    /**
     * 查询 班前防护
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findProtect/{projectId}")
    public Data findProtect(String json) {
        return protectService.findProtect(json);
    }

    /**
     * 查询 班前防护 list
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findProtectList/{projectId}")
    public Data findProtectList(String json) {
        return protectService.findProtectList(json);
    }

    /**
     * 编辑 班前防护 list
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "updateProtect/{projectId}")
    public Data updateProtect(String json) {
        return protectService.updateProtect(json);
    }

    /**
     * 关闭 班前防护 list
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2021/03/09
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "closeProtect/{projectId}")
    public Data closeProtect(String json) {
        return protectService.closeProtect(json);
    }

    /**
     * 新增 班前防护 list
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveProtect/{projectId}")
    public Data saveProtect(String json) {
        return protectService.saveProtect(json);
    }

    /**
     * 开始防护
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/10/10
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "startProtect/{projectId}")
    public Data startProtect(String json) {
        return protectService.startProtect(json);
    }

    /**
     * 查询 剩余防护 时间
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/10/10
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "surplusProtect/{projectId}")
    public Data surplusProtect(String json) {
        return protectService.surplusProtect(json);
    }

    /**
     * 结束防护
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/10/10
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "deletedProtect/{projectId}")
    public Data deletedProtect(String json) {
        return protectService.deletedProtect(json);
    }

    /**
     * 查询用户是否在项目的作业中
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/12/18
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findIsProtectPerson/{projectId}")
    public Data findIsProtectPerson(String json) {
        return protectService.findIsProtectPerson(json);
    }

    /**
     * 查询 班前防护记录
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/12/18
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findProtectRecordList/{projectId}")
    public Data findProtectRecordList(String json) {
        return protectService.findProtectRecordList(json);
    }

    /**
     * 更新 作业状态
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2021/03/09
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "updateProtectState")
    public Data updateProtectState(String json) {
        return protectService.updateProtectState(json);
    }

    /**
     * 查询 作业状态
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2021/03/30
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findUserProtectState/{projectId}")
    public Data findUserProtectState(String json) {
        return protectService.findUserProtectState(json);
    }

    /**
     * 查询 作业的综合监测
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2021/04/02
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findIntegratedMonitoringList/{projectId}")
    public Data findIntegratedMonitoringList(String json) {
        return protectService.findIntegratedMonitoringList(json);
    }

}
