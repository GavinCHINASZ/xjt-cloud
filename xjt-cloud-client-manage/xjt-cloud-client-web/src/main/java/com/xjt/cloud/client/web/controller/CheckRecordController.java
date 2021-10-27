package com.xjt.cloud.client.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.client.core.service.service.CheckRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 巡检记录
 *
 * @ClassName CheckRecordController
 * @Author dwt
 * @Date 2019-07-26 11:49
 * @Version 1.0
 */
@RestController
@RequestMapping("/checkRecord")
public class CheckRecordController extends AbstractController {

    @Autowired
    private CheckRecordService checkRecordService;

    /**
     * 查询巡检记录列表
     *
     * @Author huangGuiChuan
     * @Date 2019-12-04
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findCheckRecordList/{projectId}")
    public Data findCheckRecordList(String json) {
        return checkRecordService.findCheckRecordList(json);
    }

}
