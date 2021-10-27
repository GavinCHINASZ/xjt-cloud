package com.xjt.cloud.admin.manage.service.service.sys;


import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.sys.Version;
import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:45
 * @Description: app版本管理接口
 */
public interface VersionService {

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
    ScriptPage<Version> findVersionList(AjaxPage ajaxPage, Version version);

    /**
     *
     * 功能描述:新增版本信息
     *
     * @param version
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    Data saveVersion(Version version);

    /**
     *
     * 功能描述:修改版本信息
     *
     * @param version
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    Data modifyVersion(Version version);

    /**
     *
     * 功能描述:删除版本信息
     *
     * @param version
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    Data delVersion(Version version);
}
