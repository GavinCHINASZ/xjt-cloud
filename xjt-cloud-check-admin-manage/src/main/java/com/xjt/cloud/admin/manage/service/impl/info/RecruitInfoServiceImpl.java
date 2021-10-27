package com.xjt.cloud.admin.manage.service.impl.info;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.info.RecruitInfoDao;
import com.xjt.cloud.admin.manage.entity.info.RecruitInfo;
import com.xjt.cloud.admin.manage.service.service.info.RecruitInfoService;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecruitInfoServiceImpl extends AbstractAdminService implements RecruitInfoService {

    @Autowired
    private RecruitInfoDao recruitInfoDao;

    /**
     * 功能描述:跳转到招聘信息管理页面
     *
     * @param
     * @return: org.springframework.web.servlet.ModelAndView
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @Override
    public ScriptPage<RecruitInfo> findRecruitInfoList(AjaxPage ajaxPage, RecruitInfo recruitInfo) {
        recruitInfo = asseFindObj(recruitInfo, ajaxPage);
        return asseScriptPage(recruitInfoDao.findRecruitInfoList(recruitInfo), recruitInfoDao.findRecruitInfoListTotalCount(recruitInfo));
    }

    /**
     * 功能描述:保存资讯信息管理
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @Override
    public Data saveRecruitInfo(RecruitInfo recruitInfo) {
        return asseData(recruitInfoDao.saveRecruitInfo(recruitInfo));
    }

    /**
     * 功能描述:跳转到招聘信息管理页面
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    @Override
    public Data modifyRecruitInfo(RecruitInfo recruitInfo) {
        return asseData(recruitInfoDao.modifyRecruitInfo(recruitInfo));
    }
}
