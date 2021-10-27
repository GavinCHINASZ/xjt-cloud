package com.xjt.cloud.project.core.service.service;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.entity.AppModule;

/**
 * @ClassName AppModuleService APP模块管理
 * @Author zhangZaiFa
 * @Date 2019-07-31 15:15
 * @Description
 */
public interface AppModuleService {
    /**@MethodName: findByUserProjectAppModule
     * @Description: 查询用户项目内APP模块
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/30 9:54
     **/
    Data findByUserProjectAppModule(String json);

    /**@MethodName: saveUserProjectAppModule
     * @Description: 保存用户项目内APP模块
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/30 9:55
     **/
    Data saveUserProjectAppModule(String json);

    /**
     * @Description 保存app首页模版类型
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 11:32
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveAppHomeUserProjectModule(String json);

    /**@MethodName: findByUserProjectWebModule
     * @Description: 查询用户项目内Web模块
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/30 9:53
     **/
    Data findByUserProjectWebModule(String json);

    /**@MethodName: saveUserProjectWebModule
     * @Description: 保存用户项目内APP模块
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/30 9:54
     **/
    Data saveUserProjectWebModule(String json);

    /**
     * @Description 查询app首页模板接口
     * @Param: projectId
     * @author wangzhiwen
     * @date 2021/3/25 10:35
     * @return String
     */
    String findAppHomeUserProjectModule(Long projectId,Long userId);
}
