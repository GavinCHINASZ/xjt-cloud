package com.xjt.cloud.admin.manage.service.impl.sys;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.sys.CloudInitDao;
import com.xjt.cloud.admin.manage.entity.sys.CloudInit;
import com.xjt.cloud.admin.manage.service.service.sys.CloudInitService;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/16 11:34
 * @Description: 平台信息初使化管理接口实现类
 */
@Service
public class CloudInitServiceImpl extends AbstractAdminService implements CloudInitService {
    @Autowired
    private CloudInitDao cloudInitDao;

    /**
     * 功能描述:平台信息初使化列表
     *
     * @param ajaxPage
     * @param cloudInit
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    @Override
    public ScriptPage<CloudInit> findCloudInitList(AjaxPage ajaxPage, CloudInit cloudInit) {
        cloudInit = asseFindObj(cloudInit, ajaxPage);
        return asseScriptPage(cloudInitDao.findCloudInitList(cloudInit), cloudInitDao.findCloudInitListTotalCount(cloudInit));
    }


    /**
     * 功能描述: 新增平台信息初使化信息
     *
     * @param cloudInit
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/28 16:22
     */
    @Override
    public Data saveCloudInit(CloudInit cloudInit) {
        int num = cloudInitDao.saveCloudInit(cloudInit);
        return asseData(num);
    }

    /**
     * 功能描述: 修改平台信息初使化信息
     *
     * @param cloudInit
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/28 16:22
     */
    @Override
    public Data modifyCloudInit(CloudInit cloudInit) {
        int num = cloudInitDao.modifyCloudInit(cloudInit);
        return asseData(num);
    }
}
