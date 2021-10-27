package com.xjt.cloud.admin.manage.service.impl.info;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.info.InfoManageDao;
import com.xjt.cloud.admin.manage.entity.info.InfoContent;
import com.xjt.cloud.admin.manage.service.service.info.InfoManageService;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/8/3 14:41
 * @Description: 资讯信息初使化信息配置接口
 */
@Service
public class InfoManageServiceImpl extends AbstractAdminService implements InfoManageService {

    @Autowired
    private InfoManageDao infoManageDao;

    /**
     *
     * 功能描述:查询资讯信息管理列表
     *
     * @param
     * @return: org.springframework.web.servlet.ModelAndView
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @Override
    public ScriptPage<InfoContent> findInfoManageList(AjaxPage ajaxPage, InfoContent infoContent){
        infoContent = asseFindObj(infoContent, ajaxPage);
        return asseScriptPage(infoManageDao.findInfoManageList(infoContent), infoManageDao.findInfoManageListTotalCount(infoContent));
    }

    /**
     *
     * 功能描述:保存资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @Override
    public Data saveInfoManage(InfoContent infoContent){
        return asseData(infoManageDao.saveInfoManage(infoContent));
    }

    /**
     *
     * 功能描述:修改资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @Override
    public Data modifyInfoManage(InfoContent infoManage){
        return asseData(infoManageDao.modifyInfoManage(infoManage));
    }

    /**
     *
     * 功能描述:删除资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @Override
    public Data delInfoManage(InfoContent infoContent){
        return asseData(infoManageDao.modifyInfoManage(infoContent));
    }

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
    @Override
    public ScriptPage<InfoContent> findInfoContentList(AjaxPage ajaxPage, InfoContent infoContent){
        infoContent = asseFindObj(infoContent, ajaxPage);
        return asseScriptPage(infoManageDao.findInfoContentList(infoContent), infoManageDao.findInfoContentListTotalCount(infoContent));
    }

    /**
     *
     * 功能描述:保存资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @Override
    public Data saveInfoContent(InfoContent infoContent){
        return asseData(infoManageDao.saveInfoContent(infoContent));
    }

    /**
     *
     * 功能描述:修改资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @Override
    public Data modifyInfoContent(InfoContent infoContent){
        return asseData(infoManageDao.modifyInfoContent(infoContent));
    }

    /**
     *
     * 功能描述:删除资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @Override
    public Data delInfoContent(InfoContent infoContent){
        return asseData(infoManageDao.modifyInfoContent(infoContent));
    }
}
