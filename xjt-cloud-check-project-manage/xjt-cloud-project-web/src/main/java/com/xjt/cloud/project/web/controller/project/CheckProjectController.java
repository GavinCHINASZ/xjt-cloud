package com.xjt.cloud.project.web.controller.project;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.project.CheckProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName CheckProjectController 项目信息
 * @Author zhangZaiFa
 * @Date 2019-07-31 15:15
 * @Description
 */
@RestController
@RequestMapping("/project/checkProject")
public class CheckProjectController extends AbstractController {

    @Autowired
    private CheckProjectService checkProjectService;

    /**
     * @MethodName: findByCheckProjectList 查询检测项目信息列表
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 13:45
     **/
    @RequestMapping(value = "/findByCheckProjectList/{projectId}")
    public Data checkProjectService(String json) {
        return checkProjectService.findByCheckProjectList(json);
    }


    /**
     * @MethodName: findByMyCheckProjectList 查询我的检测项目信息列表
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 13:45
     **/
    @RequestMapping(value = "/findByMyCheckProjectList/{projectId}")
    public Data findByMyCheckProjectList(String json) {
        return checkProjectService.findByMyCheckProjectList(json);
    }

    /**
     * @MethodName: findByCheckProject 查询检测项目信息
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 13:45
     **/
    @RequestMapping(value = "/findByCheckProject/{projectId}")
    public Data findByCheckProject(String json) {
        return checkProjectService.findByCheckProject(json);
    }

    /**
     * @MethodName: findByCheckProject 查询检测项目报表扫描信息
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 13:45
     **/
    @RequestMapping(value = "/findCheckProjectReport")
    public Data findCheckProjectReport(String number) {
        return checkProjectService.findCheckProjectReport(number);
    }

    /**
     * @MethodName: saveCheckProject 保存检测项目信息
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 13:45
     **/
    @RequestMapping(value = "/saveCheckProject/{projectId}")
    public Data saveCheckProject(String json) {
        return checkProjectService.saveCheckProject(json);
    }

    /**
     * @MethodName: delCheckProject 删除检测项目信息
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 13:45
     **/
    @RequestMapping(value = "/delCheckProject/{projectId}")
    public Data delCheckProject(String json) {
        return checkProjectService.delCheckProject(json);
    }

    /**
     * @MethodName: updCheckProject 更新检测项目信息
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 13:45
     **/
    @RequestMapping(value = "/updCheckProject/{projectId}")
    public Data updCheckProject(String json) {
        return checkProjectService.updCheckProject(json);
    }

    /**
     * 报告生成
     *
     * @param json
     * @Author huanggc
     * @Date 2020-04-10
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @RequestMapping(value = "/saveReport/{projectId}")
    public Data saveReport(String json, HttpServletResponse response) {
        return checkProjectService.saveReport(json, response);
    }

    /**
     * 报告查询
     *
     * @param json
     * @Author huanggc
     * @Date 2020-04-10
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @RequestMapping(value = "/findReport/{projectId}")
    public Data findReport(String json) {
        return checkProjectService.findReport(json);
    }

    /**
     * 报告下载
     *
     * @param json
     * @Author huanggc
     * @Date 2020-04-10
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @RequestMapping(value = "/downReport/{projectId}")
    public void downReport(String json, HttpServletResponse response) {
        checkProjectService.downReport(json, response);
    }

    /**
     * 打印标签
     *
     * @param json
     * @param response HttpServletResponse
     * @Author huanggc
     * @Date 2020-04-14
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @RequestMapping(value = "/saveLabel/{projectId}")
    public Data saveLabel(String json, HttpServletResponse response) {
        return checkProjectService.saveLabel(json, response);
    }
}
