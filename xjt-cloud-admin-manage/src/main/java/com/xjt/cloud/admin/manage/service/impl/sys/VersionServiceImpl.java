package com.xjt.cloud.admin.manage.service.impl.sys;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.sys.VersionDao;
import com.xjt.cloud.admin.manage.entity.sys.Version;
import com.xjt.cloud.admin.manage.service.service.sys.VersionService;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:46
 * @Description: app版本管理接口实现
 */
@Service
public class VersionServiceImpl extends AbstractAdminService implements VersionService {

    @Autowired
    private VersionDao versionDao;

    /**
     *
     * 功能描述:查询版本列表
     *
     * @param ajaxPage
     * @param version
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @Override
    public ScriptPage<Version> findVersionList(AjaxPage ajaxPage, Version version){
        version = asseFindObj(version, ajaxPage);
        return asseScriptPage(versionDao.findVersionList(version), versionDao.findVersionListTotalCount(version));
    }

    /**
     *
     * 功能描述:新增版本信息
     *
     * @param version
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @Override
    public Data saveVersion(Version version){
        version.setCreateUserName(SecurityUserHolder.getUserName());
        version.setVersion("vs.app." + (StringUtils.isEmpty(version.getVersionNum()) ? version.getVersionNum() : "") +
                (StringUtils.isEmpty(version.getVersionSize()) ? version.getVersionSize() : ""));
        int num = versionDao.saveVersion(version);
        return asseData(num);
    }

    /**
     *
     * 功能描述:修改版本信息
     *
     * @param version
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @Override
    public Data modifyVersion(Version version){
        version.setVersion("vs.app." + (StringUtils.isNotEmpty(version.getVersionNum()) ? version.getVersionNum() : "") +
                (StringUtils.isNotEmpty(version.getVersionSize()) ? version.getVersionSize() : ""));
        int num = versionDao.modifyVersion(version);
        return asseData(num);
    }

    /**
     *
     * 功能描述:删除版本信息
     *
     * @param version
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @Override
    public Data delVersion(Version version){
        int num = versionDao.modifyVersion(version);
        return asseData(num);
    }
}
