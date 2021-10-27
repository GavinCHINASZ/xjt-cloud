package com.xjt.cloud.project.core.service.impl.project;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.dao.project.AppModuleDao;
import com.xjt.cloud.project.core.entity.project.AppModule;
import com.xjt.cloud.project.core.service.service.project.AppModuleService;
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
        if (entity == null) {
            entity = new AppModule();
            entity.setUserId(userId);
            entity.setType(1);
            entity.setAppModuleType("0,1,2,3,4,5,6,7,8,9,10,11");
            entity.setModuleType("0,1,2,3,4,5,6,7,8,9,10,11");
            entity.setProjectId(appModule.getProjectId());
            if(appModule.getProjectId()!=null){
                appModuleDao.save(entity);
            }
        }
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
        if (entity == null) {
            entity = new AppModule();
            entity.setUserId(userId);
            entity.setType(2);
            entity.setWebModuleType("0,1,2,3,4,5,6,7,8,9,10,11");
            entity.setModuleType("0,1,2,3,4,5,6,7,8,9,10,11");
            entity.setProjectId(appModule.getProjectId());
            if(appModule.getProjectId()!=null){
                appModuleDao.save(entity);
            }
        }
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

}
