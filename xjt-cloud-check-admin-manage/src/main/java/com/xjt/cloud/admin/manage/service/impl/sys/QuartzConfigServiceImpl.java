package com.xjt.cloud.admin.manage.service.impl.sys;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.sys.QuartzConfigDao;
import com.xjt.cloud.admin.manage.entity.QuartzConfig;
import com.xjt.cloud.admin.manage.service.service.sys.QuartzConfigService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.quartz.manage.MySchedulerFactory;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 定时任务 接口实现类
 *
 * @author huanggc
 * @date 2020/09/15
 */
@Service
public class QuartzConfigServiceImpl extends AbstractAdminService implements QuartzConfigService {
    @Autowired
    private QuartzConfigDao quartzConfigDao;
    @Autowired
    private MySchedulerFactory mySchedulerFactory;

    /**
     * 功能描述:查询 定时任务
     *
     * @return ScriptPage<QuartzConfig>
     * @author huanggc
     * @date 2020/09/15
     */
    @Override
    public ScriptPage<QuartzConfig> findQuartzConfigList(AjaxPage ajaxPage, QuartzConfig quartzConfig) {
        quartzConfig.setPageIndex(ajaxPage.getPage());
        quartzConfig.setPageSize(ajaxPage.getRows());
        return asseScriptPage(quartzConfigDao.findQuartzConfigList(quartzConfig), quartzConfigDao.findQuartzConfigListTotalCount());
    }

    /**
     * 功能描述:修改 定时任务
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/09/16
     */
    @Override
    public Data modifyQuartzConfig(QuartzConfig quartzConfig) {
        int num = quartzConfigDao.updateByPrimaryKey(quartzConfig);
        try {
            // 修改 内存中的定时任务
            mySchedulerFactory.modifyJob(quartzConfig);
        } catch (SchedulerException e) {
            return Data.isFail();
        }
        return asseData(num);
    }

    /**
     * 功能描述:新增 保存 定时任务
     *
     * @param quartzConfig 任务实体
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/09/16
     */
    @Override
    public Data saveQuartzConfig(QuartzConfig quartzConfig) {
        quartzConfig.setStatus(0);
        int num = quartzConfigDao.insert(quartzConfig);
        try {
            // 新增 内存中的定时任务
            mySchedulerFactory.saveJob(quartzConfig);
        } catch (Exception e) {
            return Data.isFail();
        }
        return asseData(num);
    }

    /**
     * 功能描述:删除 定时任务
     *
     * @param quartzConfig 任务实体
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/09/16
     */
    @Override
    public Data quartzConfigDel(QuartzConfig quartzConfig) {
        int num = quartzConfigDao.updateByPrimaryKey(quartzConfig);
        try {
            // 删除 内存中的定时任务
            mySchedulerFactory.delJob(quartzConfig);
        } catch (Exception e) {
            return Data.isFail();
        }
        return asseData(num);
    }

    /**
     * 功能描述:修改 定时任务 状态
     *
     * @param quartzConfig 任务实体
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/09/16
     */
    @Override
    public Data updateQuartzConfigStatus(QuartzConfig quartzConfig) {
        try {
            if (0 == quartzConfig.getStatus()) {
                // 恢复某个任务
                mySchedulerFactory.resumeJob(quartzConfig);
            } else {
                // 暂停任务
                mySchedulerFactory.pauseJob(quartzConfig);
            }
            quartzConfigDao.updateByPrimaryKey(quartzConfig);
        } catch (Exception ex) {
            return Data.isFail();
        }
        return Data.isSuccess();
    }

}
