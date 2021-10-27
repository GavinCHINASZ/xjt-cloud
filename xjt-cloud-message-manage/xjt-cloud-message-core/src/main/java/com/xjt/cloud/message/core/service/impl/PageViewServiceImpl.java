package com.xjt.cloud.message.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.commons.utils.ThreadPoolUtils;
import com.xjt.cloud.message.core.common.util.PageViewThread;
import com.xjt.cloud.message.core.dao.pv.PageViewDao;
import com.xjt.cloud.message.core.dao.pv.PageViewReportDao;
import com.xjt.cloud.message.core.entity.PageView;
import com.xjt.cloud.message.core.service.service.PageViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * PageViewServiceImpl 页面浏览量或点击量
 *
 * @author huanggc
 * @date 2020/10/23
 */
@Service
public class PageViewServiceImpl extends AbstractService implements PageViewService {
    @Autowired
    private PageViewDao pageViewDao;
    @Autowired
    protected PageViewReportDao pageViewReportDao;

    /**
     * 保存 PV
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/10/23
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @Override
    public Data savePageView(String json, HttpServletRequest request) {
        try {
            ThreadPoolUtils.getInstance().execute(new PageViewThread(json, request));
        }catch (Exception e){
            return Data.isSuccess();
        }
        return Data.isSuccess();
    }

    /**
     * 多线程保存 PV
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/10/23
     **/
    @Override
    public void savePageViewThread(String json) {
        try {
            PageView pageView = JSONObject.parseObject(json, PageView.class);
            if (pageView.getUserAgent().length() > 198){
                pageView.setUserAgent(pageView.getUserAgent().substring(0, 198));
            }
            pageViewDao.savePageView(pageView);
        }catch (Exception e){
            SysLog.error(e);
        }
    }

    /**
     * 查询 PV list
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/10/23
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @Override
    public Data findPageViewList(String json) {
        try {
            PageView pageView = JSONObject.parseObject(json, PageView.class);
            List<PageView> pageViewList = pageViewDao.findPageViewList(pageView);
            return asseData(pageViewList);
        }catch (Exception e){
            return Data.isSuccess();
        }
    }

    /**
     * PV统计定时任务
     *
     * @author huanggc
     * @date 2020/11/03
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @Override
    public Data pageViewReportTask() {
        // 得到日历
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 设置为前一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        // 得到前一天的开始时间
        Date dBefore = calendar.getTime();

        // 一天的结束时间 yyyy:MM:dd 23:59:59
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        // 得到前一天的结束时间
        Date dayEnd = calendar.getTime();

        // 统计前一天(昨天)数据, 保存到pageViewReport
        PageView pageView = new PageView();
        pageView.setCreateTime(dBefore);
        pageView.setLastModifyTime(dayEnd);

        pageViewReportDao.saveReportByPageView(pageView);

        return asseData(200);
    }
}
