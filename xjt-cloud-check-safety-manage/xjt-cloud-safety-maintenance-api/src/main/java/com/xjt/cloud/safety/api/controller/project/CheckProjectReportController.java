package com.xjt.cloud.safety.api.controller.project;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.safety.core.service.service.project.CheckProjectReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CheckProjectReportController
 * @Description 评估项目报表记录
 * @Author wangzhiwen
 * @Date 2021/5/14 10:41
 **/
@RestController
@RequestMapping("/project/report/")
public class CheckProjectReportController extends AbstractController {
    @Autowired
    private CheckProjectReportService checkProjectReportService;


    /**
     * @Description 新增评估项目报表记录
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/14 10:49
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveCheckProjectReport")
    public Data saveCheckProjectReport(String json){
        return checkProjectReportService.saveCheckProjectReport(json);
    }

    /**
     * @Description 修改评估项目报表记录
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/14 10:49
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "modifyCheckProjectReport")
    public Data modifyCheckProjectReport(String json){
        return checkProjectReportService.modifyCheckProjectReport(json);
    }

    /**
     * @Description 查询评估项目报表记录列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/14 10:49
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findCheckProjectReportList")
    public Data findCheckProjectReportList(String json){
        return checkProjectReportService.findCheckProjectReportList(json);
    }
}
