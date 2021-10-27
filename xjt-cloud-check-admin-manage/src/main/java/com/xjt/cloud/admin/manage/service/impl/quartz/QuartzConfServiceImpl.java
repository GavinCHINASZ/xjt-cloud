package com.xjt.cloud.admin.manage.service.impl.quartz;

import com.alibaba.fastjson.JSON;
import com.xjt.cloud.admin.manage.dao.sys.QuartzConfigDao;
import com.xjt.cloud.admin.manage.entity.QuartzConfig;
import com.xjt.cloud.admin.manage.service.service.quartz.QuartzConfService;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.quartz.manage.MySchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:56
 * @Description: quartz定时任务逻辑处理接口实现
 */
@Service
public class QuartzConfServiceImpl extends AbstractService implements QuartzConfService {

    @Autowired
    private QuartzConfigDao quartzConfigMapper;

    @Autowired
    private MySchedulerFactory mySchedulerFactory;

    /**
     *
     * 功能描述:添加新任务
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/5/22 0022 17:17
     */
    @Override
    @Transactional
    public Data saveJob(String json){
        try{
            QuartzConfig quartzConfig = JSON.parseObject(json,QuartzConfig.class);
            quartzConfigMapper.insert(quartzConfig);
            mySchedulerFactory.saveJob(quartzConfig);
        }catch (Exception ex){
            return Data.isFail();
        }
        return Data.isSuccess();
    }

    /**
     *
     * 功能描述:删除任务
     *
     * @param id
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/5/22 0022 17:17
     */
    @Override
    public Data delJob(long id){
        return asseData(quartzConfigMapper.deleteByPrimaryKey(id));
    }

    /**
     *
     * 功能描述:查找任务列表
     *
     * @param
     * @return: java.util.List<com.xjt.cloud.sys.manage.entity.QuartzConfig>
     * @auther: wangzhiwen
     * @date: 2019/5/22 0022 17:16
     */
    @Override
    public List<QuartzConfig> getJobList() {
        return quartzConfigMapper.selectAll();
    }

    /**
     *
     * 功能描述: 以id查找任务对象
     *
     * @param id
     * @return: com.xjt.cloud.sys.manage.entity.QuartzConfig
     * @auther: wangzhiwen
     * @date: 2019/5/22 0022 17:16
     */
    @Override
    public QuartzConfig findById(long id) {
        return quartzConfigMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * 功能描述:修改任务的描述与时间
     *
     * @param id
     * @param cron
     * @param msg
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/5/22 0022 17:15
     */
    @Override
    @Transactional
    public Data updateJob(long id, String cron, String msg) {
        try {
            QuartzConfig quartzConfig = quartzConfigMapper.selectByPrimaryKey(id);
            quartzConfig.setCron(cron);
            quartzConfig.setMsg(msg);
            quartzConfigMapper.updateByPrimaryKey(quartzConfig);
            mySchedulerFactory.modifyJob(quartzConfig);
        }catch (Exception ex){
            return Data.isFail();
        }
        return Data.isSuccess();
    }
    /**
     *
     * 功能描述: 修改任务状态
     *
     * @param id
     * @param status
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/5/22 0022 17:06
     */
    @Override
    @Transactional
    public Data updateJobStatus(long id, int status) {
        try {
            QuartzConfig quartzConfig = quartzConfigMapper.selectByPrimaryKey(id);
            quartzConfig.setStatus(status);
            quartzConfigMapper.updateByPrimaryKey(quartzConfig);
            if (0 == status){
                mySchedulerFactory.resumeJob(quartzConfig);//恢复某个任务
            }else {
                mySchedulerFactory.pauseJob(quartzConfig);//暂停任务
            }
        }catch (Exception ex){
            return Data.isFail();
        }
        return Data.isSuccess();
    }

    /**
     *
     * 功能描述: 保存任务运行日志
     *
     * @param cronTrigger
     * @param exceptionMsg
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/12/2 10:59
     */
    @Override
    public void saveTaskLog(CronTriggerImpl cronTrigger, String exceptionMsg){
        QuartzConfig quartzConfig = new QuartzConfig();
        quartzConfig.setGroup(cronTrigger.getGroup());
        quartzConfig.setName(cronTrigger.getName());
        quartzConfig.setTaskLog(exceptionMsg.length() > 2900 ? exceptionMsg.substring(0,2900):exceptionMsg);
        quartzConfigMapper.saveTaskLog(quartzConfig);
    }
}
