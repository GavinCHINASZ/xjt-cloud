package com.xjt.cloud.project.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.project.core.common.ConstantsProjectMsg;
import com.xjt.cloud.project.core.dao.project.AppModuleDao;
import com.xjt.cloud.project.core.entity.AppModule;
import com.xjt.cloud.project.core.service.service.AppModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @ClassName AppModuleServiceImpl APP模块管理
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Service
public class AppModuleServiceImpl extends AbstractService implements AppModuleService {

    @Autowired
    private AppModuleDao appModuleDao;

    /**@MethodName: findByUserProjectAppModule
     * @Description: 查询用户项目APP模块
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/30 10:52
     **/
    @Override
    public Data findByUserProjectAppModule(String json) {
        AppModule appModule = JSONObject.parseObject(json, AppModule.class);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        appModule.setUserId(userId);
        appModule.setType(1);
        AppModule entity = appModuleDao.findByModule(appModule);
        if (entity == null || StringUtils.isEmpty(entity.getModuleType())) {
            entity = getDefaultModule(1);
        }
        entity.setAppModuleType(entity.getModuleType());
        return asseData(entity);
    }

    /**@MethodName: saveUserProjectAppModule
     * @Description: 保存用户项目APP模块
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/30 10:53
     **/
    @Override
    public Data saveUserProjectAppModule(String json) {
        AppModule appModule = JSONObject.parseObject(json, AppModule.class);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        appModule.setUserId(userId);
        //先删除后保存
        appModule.setType(1);
        appModuleDao.delete(appModule);
        appModule.setModuleType(appModule.getAppModuleType());
        appModuleDao.save(appModule);
        return Data.isSuccess();
    }

    /**
     * @Description 保存app首页模版类型
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 11:32
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveAppHomeUserProjectModule(String json){
        AppModule appModule = JSONObject.parseObject(json, AppModule.class);
        appModule.setUserId(getLoginUserId(SecurityUserHolder.getUserName()));
        //先删除后保存
        appModule.setType(3);
        appModuleDao.delete(appModule);
        String key = "APP_HOME_USER_PROJECT_MODEL_" + appModule.getUserId() + "_" + appModule.getProjectId();
        redisUtils.del(key);
        appModuleDao.save(appModule);
        return Data.isSuccess();
    }


    /**@MethodName: findByUserProjectWebModule
     * @Description: 查询用户项目WEB模块
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/30 10:52
     **/
    @Override
    public Data  findByUserProjectWebModule(String json) {
        AppModule appModule = JSONObject.parseObject(json, AppModule.class);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        appModule.setUserId(userId);
        appModule.setType(2);
        AppModule entity = appModuleDao.findByModule(appModule);
        if (entity == null || StringUtils.isEmpty(entity.getModuleType())) {
            entity = getDefaultModule(2);
        }
        entity.setWebModuleType(entity.getModuleType());
        return asseData(entity);
    }

    /**
     * @Description 得到默认的应用排序
     *
     * @param type 查询类型 1APP  2PC
     * @author wangzhiwen
     * @date 2020/11/4 14:08
     * @return com.xjt.cloud.project.core.entity.AppModule
     */
    private AppModule getDefaultModule(int type){
        String key = "DEFAULT_MODULE_" + type;
        String moduleType = redisUtils.getString(key);
        AppModule appModule;
        if (StringUtils.isEmpty(moduleType)){//判断缓存中是否存在
            if (StringUtils.isEmpty(ConstantsProjectMsg.HOME_APPLY_DEFAULT_MODULE)){
                appModule = appModuleDao.findDefaultModule(type);
                if (appModule != null){
                    moduleType = appModule.getModuleType();
                }
            }
            if (StringUtils.isEmpty(moduleType)){
                moduleType = ConstantsProjectMsg.HOME_APPLY_DEFAULT_MODULE;
            }
            redisUtils.set(key,moduleType,86400);
        }

        appModule = new AppModule();
        appModule.setModuleType(moduleType);
        return appModule;
    }

    /**@MethodName: saveUserProjectAppModule
     * @Description: 保存用户项目APP模块
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/30 10:53
     **/
    @Override
    public Data saveUserProjectWebModule(String json) {
        AppModule webModule = JSONObject.parseObject(json, AppModule.class);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        webModule.setUserId(userId);
        //先删除后保存
        webModule.setType(2);
        appModuleDao.delete(webModule);
        webModule.setModuleType(webModule.getWebModuleType());
        appModuleDao.save(webModule);
        return Data.isSuccess();
    }

    /**
     * @Description 查询app首页模板接口
     * @Param: projectId
     * @author wangzhiwen
     * @date 2021/3/25 10:35
     * @return String
     */
    @Override
    public String findAppHomeUserProjectModule(Long projectId,Long userId){
        String key = "APP_HOME_USER_PROJECT_MODEL_" + userId + "_" + projectId;
        String moduleType = redisUtils.getString(key);
        if (StringUtils.isEmpty(moduleType)) {
            AppModule appModule = new AppModule();
            appModule.setUserId(userId);
            appModule.setType(3);
            appModule.setProjectId(projectId);
            AppModule entity = appModuleDao.findByModule(appModule);
            if (entity == null || StringUtils.isEmpty(entity.getModuleType())) {
                moduleType = ConstantsProjectMsg.APP_HOME_USER_PROJECT_DEFAULT_MODULE;
                redisUtils.set(key, moduleType, 86400);
            }else{
                moduleType = entity.getModuleType();
            }
        }
        return moduleType;
    }
}
