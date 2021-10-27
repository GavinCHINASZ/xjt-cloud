package com.xjt.cloud.admin.manage.service.service.sys;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.QuartzConfig;
import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;

/**
 * 定时任务管理接口
 *
 * @author huanggc
 * @date 202020/09/15
 */
public interface QuartzConfigService extends BaseService {

    /**
     * 功能描述:查询 定时任务
     *
     * @param quartzConfig 任务实体
     * @param ajaxPage ajax
     * @author huanggc
     * @date 2020/09/15
     * @return ScriptPage<QuartzConfig>
     */
    ScriptPage<QuartzConfig> findQuartzConfigList(AjaxPage ajaxPage, QuartzConfig quartzConfig);

    /**
     * 功能描述:修改 定时任务
     *
     * @param quartzConfig 任务实体
     * @author huanggc
     * @date 2020/09/16
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data modifyQuartzConfig(QuartzConfig quartzConfig);

    /**
     * 功能描述:新增 保存 定时任务
     *
     * @param quartzConfig 任务实体
     * @author huanggc
     * @date 2020/09/16
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveQuartzConfig(QuartzConfig quartzConfig);

    /**
     * 功能描述:删除 定时任务
     *
     * @param quartzConfig 任务实体
     * @author huanggc
     * @date 2020/09/16
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data quartzConfigDel(QuartzConfig quartzConfig);

    /**
     * 功能描述:修改 定时任务 状态
     *
     * @param quartzConfig 任务实体
     * @author huanggc
     * @date 2020/09/16
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data updateQuartzConfigStatus(QuartzConfig quartzConfig);
}
