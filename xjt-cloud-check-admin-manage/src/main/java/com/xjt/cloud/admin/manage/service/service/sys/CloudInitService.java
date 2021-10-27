package com.xjt.cloud.admin.manage.service.service.sys;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.sys.CloudInit;
import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/16 11:34
 * @Description: 平台信息初使化管理接口
 */
public interface CloudInitService {
    /**
     *
     * 功能描述:平台信息初使化列表
     *
     * @param ajaxPage
     * @param cloudInit
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    ScriptPage<CloudInit> findCloudInitList(AjaxPage ajaxPage, CloudInit cloudInit);

    /**
     *
     * 功能描述: 新增平台信息初使化信息
     *
     * @param cloudInit
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/28 16:22
     */
    Data saveCloudInit(CloudInit cloudInit);

    /**
     *
     * 功能描述: 修改平台信息初使化信息
     *
     * @param cloudInit
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/28 16:22
     */
    Data modifyCloudInit(CloudInit cloudInit);
}
