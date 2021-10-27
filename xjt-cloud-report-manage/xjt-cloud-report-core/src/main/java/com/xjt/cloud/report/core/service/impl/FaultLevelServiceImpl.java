package com.xjt.cloud.report.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.report.core.dao.report.FaultLevelDao;
import com.xjt.cloud.report.core.entity.report.FaultLevel;
import com.xjt.cloud.report.core.service.service.FaultLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: zhangzf
 * @Date: 2019/11/12
 * @Description: 等级设置
 */
@Service
public class FaultLevelServiceImpl extends AbstractService implements FaultLevelService {

    @Autowired
    private FaultLevelDao faultLevelDao;

    /**@MethodName: save 保存故障等级设置
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/12 14:41
     **/
    @Override
    public Data save(String json) {
        FaultLevel faultLevel = JSONObject.parseObject(json,FaultLevel.class);
        //更新之前记录为老版本
        faultLevelDao.updateVersion(faultLevel);
        //保存故障设置
        faultLevelDao.save(faultLevel);
        return asseData(faultLevel);
    }

    /**@MethodName: findByProjectNewVersionFaultLevel 查询项目内最新版本值班记录设置信息
     * @Description:
     * @Param: [projectId]
     * @Return: com.xjt.cloud.report.core.entity.report.FaultLevel
     * @Author: zhangZaiFa
     * @Date:2019/11/12 15:41
     **/
    @Override
    public FaultLevel findByProjectNewVersionFaultLevel(Long projectId) {
        FaultLevel faultLevel = new FaultLevel();
        faultLevel.setProjectId(projectId);
        faultLevel.setNewVersion(1);
        return faultLevelDao.findByFaultLevel(faultLevel);
    }

    /**@MethodName: findByFaultLevel 查询项目设置信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/13 9:39
     **/
    @Override
    public Data findByFaultLevel(String json) {
        FaultLevel faultLevel = JSONObject.parseObject(json,FaultLevel.class);
        if(faultLevel.getNewVersion() == null || faultLevel.getNewVersion() == 0){
            faultLevel.setNewVersion(1);
        }
        FaultLevel entity = faultLevelDao.findByFaultLevel(faultLevel);
        if(entity == null){
            entity = new FaultLevel(70, 50, 30, 10, 1, faultLevel.getProjectId());
        }
        return asseData(entity);
    }
}
