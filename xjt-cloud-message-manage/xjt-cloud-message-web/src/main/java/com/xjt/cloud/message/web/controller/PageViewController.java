package com.xjt.cloud.message.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.message.core.service.service.PageViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * PageViewController 页面浏览量或点击量
 *
 * @author huanggc
 * @date 2020/10/23
 */
@RestController
@RequestMapping("/pageView")
class PageViewController extends AbstractController {
    @Autowired
    private PageViewService pageViewService;

    /**
     * 保存 PV
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/10/23
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @RequestMapping(value = "/savePageView")
    public Data savePageView(String json, HttpServletRequest request) {
        return pageViewService.savePageView(json, request);
    }

    /**
     * PV统计定时任务
     *
     * @author huanggc
     * @date 2020/11/03
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @RequestMapping(value = "/pageViewReportTask")
    public Data pageViewReportTask() {
        return pageViewService.pageViewReportTask();
    }
}
