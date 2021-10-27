package com.xjt.cloud.admin.manage.service.service.info;


import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.info.RecruitInfo;
import com.xjt.cloud.commons.utils.Data;

public interface RecruitInfoService {

    /**
     *
     * 功能描述:跳转到招聘信息管理页面
     *
     * @param
     * @return: org.springframework.web.servlet.ModelAndView
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    ScriptPage<RecruitInfo> findRecruitInfoList(AjaxPage ajaxPage, RecruitInfo recruitInfo);

    /**
     *
     * 功能描述:保存资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    Data saveRecruitInfo(RecruitInfo recruitInfo);

    /**
     *
     * 功能描述:跳转到招聘信息管理页面
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    Data modifyRecruitInfo(RecruitInfo recruitInfo);
}
