package com.xjt.cloud.maintenance.api.controller.project;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.service.service.project.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName ProjectLogController 项目日志
 * @Author zhangZaiFa
 * @Date 2019-08-20 15:15
 * @Description
 */
@RestController
@RequestMapping("/project/projectLog")
public class LogController extends AbstractController {

    @Autowired
    private LogService logService;


    /**
     * @MethodName: findByProjectLogList 查询项目日志
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/8/20 16:40
     **/
    @RequestMapping(value = "/findByProjectLogList/{projectId}")
    public Data findByProjectLogList(String json) {
        return logService.findByProjectLogList(json);
    }

    /**@MethodName: findByProjectLogTypeList 查询项目日志类型
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/8 14:17 
     **/
    @RequestMapping(value = "/findByProjectLogTypeList/{projectId}")
    public Data findByProjectLogTypeList(String json) {
        return logService.findByProjectLogTypeList(json);
    }


    /**@MethodName: downloadProjectLog
     * @Description: 导出项目日志
     * @Param: [response, json]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/3/25 9:59
     **/
    @RequestMapping(value = "downloadProjectLog/{projectId}")
    public void downloadProjectLog(HttpServletResponse response, String json){
        logService.downloadProjectLog(response, json);
    }


}
