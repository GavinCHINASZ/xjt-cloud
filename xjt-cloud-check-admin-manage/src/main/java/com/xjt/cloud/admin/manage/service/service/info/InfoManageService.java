package com.xjt.cloud.admin.manage.service.service.info;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.info.InfoContent;
import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/8/3 14:41
 * @Description: 资讯信息初使化信息配置接口
 */
public interface InfoManageService {

    /**
     *
     * 功能描述:查询资讯信息管理列表
     *
     * @param
     * @return: org.springframework.web.servlet.ModelAndView
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    ScriptPage<InfoContent> findInfoManageList(AjaxPage ajaxPage, InfoContent infoContent);

    /**
     *
     * 功能描述:保存资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    Data saveInfoManage(InfoContent infoContent);

    /**
     *
     * 功能描述:修改资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    Data modifyInfoManage(InfoContent infoContent);

    /**
     *
     * 功能描述:删除资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    Data delInfoManage(InfoContent infoContent);

    ///////////////////////////////////////资讯信息内容/////////////////////////////////////

    /**
     *
     * 功能描述:查询资讯信息管理列表
     *
     * @param
     * @return: org.springframework.web.servlet.ModelAndView
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    ScriptPage<InfoContent> findInfoContentList(AjaxPage ajaxPage, InfoContent infoContent);

    /**
     *
     * 功能描述:保存资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    Data saveInfoContent(InfoContent infoContent);

    /**
     *
     * 功能描述:修改资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    Data modifyInfoContent(InfoContent infoContent);

    /**
     *
     * 功能描述:删除资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    Data delInfoContent(InfoContent infoContent);
}
