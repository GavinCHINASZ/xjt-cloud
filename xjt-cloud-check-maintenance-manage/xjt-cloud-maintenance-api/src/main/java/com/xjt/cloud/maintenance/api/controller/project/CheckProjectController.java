package com.xjt.cloud.maintenance.api.controller.project;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.service.service.project.CheckProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * CheckProjectController 项目信息
 *
 * @author zhangZaiFa
 * @date 2019-07-31 15:15
 */
@RestController
@RequestMapping("/project/checkProject")
public class CheckProjectController extends AbstractController {

    @Autowired
    private CheckProjectService checkProjectService;

    /**
     * findByCheckProjectList 查询检测项目信息列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/7/31 13:45
     **/
    @RequestMapping(value = "/findByCheckProjectList/{projectId}")
    public Data checkProjectService(String json) {
        return checkProjectService.findByCheckProjectList(json);
    }


    /**
     * findByMyCheckProjectList 查询我的检测项目信息列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/7/31 13:45
     **/
    @RequestMapping(value = "/findByMyCheckProjectList/{projectId}")
    public Data findByMyCheckProjectList(String json) {
        return checkProjectService.findByMyCheckProjectList(json);
    }

    /**
     * findByCheckProject 查询检测项目信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/7/31 13:45
     **/
    @RequestMapping(value = "/findByCheckProject/{projectId}")
    public Data findByCheckProject(String json) {
        return checkProjectService.findByCheckProject(json);
    }

    /**
     * findByCheckProject 查询检测项目报表扫描信息
     *
     * @param number 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/7/31 13:45
     **/
    @RequestMapping(value = "/findCheckProjectReport")
    public Data findCheckProjectReport(String number) {
        return checkProjectService.findCheckProjectReport(number);
    }

    /**
     * saveCheckProject 保存检测项目信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/7/31 13:45
     **/
    @RequestMapping(value = "/saveCheckProject/{projectId}")
    public Data saveCheckProject(String json) {
        return checkProjectService.saveCheckProject(json);
    }

    /**
     * delCheckProject 删除检测项目信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/7/31 13:45
     **/
    @RequestMapping(value = "/delCheckProject/{projectId}")
    public Data delCheckProject(String json) {
        return checkProjectService.delCheckProject(json);
    }

    /**
     * updCheckProject 更新检测项目信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/7/31 13:45
     **/
    @RequestMapping(value = "/updCheckProject/{projectId}")
    public Data updCheckProject(String json) {
        return checkProjectService.updCheckProject(json);
    }

    /**
     * 报告生成
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-04-10
     **/
    @RequestMapping(value = "/saveReport/{projectId}")
    public Data saveReport(String json, HttpServletResponse response) {
        return checkProjectService.saveReport(json, response);
    }

    /**
     * 报告查询
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-04-10
     **/
    @RequestMapping(value = "/findReport/{projectId}")
    public Data findReport(String json) {
        return checkProjectService.findReport(json);
    }

    /**
     * 报告下载
     *
     * @param json     参数
     * @param response HttpServletResponse
     * @author huanggc
     * @date 2020-04-10
     **/
    @RequestMapping(value = "/downReport/{projectId}")
    public void downReport(String json, HttpServletResponse response) {
        checkProjectService.downReport(json, response);
    }

    /**
     * 打印标签
     *
     * @param json     参数
     * @param response HttpServletResponse
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-04-14
     **/
    @RequestMapping(value = "/saveLabel/{projectId}")
    public Data saveLabel(String json, HttpServletResponse response) {
        return checkProjectService.saveLabel(json, response);
    }

    ///////////////////////////////////合同管理///////////////////

    /**
     * 查询合同列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2021/4/17 10:52
     */
    @RequestMapping(value = "/findContractList/{projectId}")
    public Data findContractList(String json) {
        return checkProjectService.findContractList(json);
    }

    /**
     * 查询合同列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2021/4/17 10:52
     */
    @RequestMapping(value = "/saveContract/{projectId}")
    public Data saveContract(String json) {
        return checkProjectService.saveContract(json);
    }

    /**
     * 删除合同列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2021/4/17 10:52
     */
    @RequestMapping(value = "/delContract/{projectId}")
    public Data delContract(String json) {
        return checkProjectService.delContract(json);
    }

}
