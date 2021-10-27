package com.xjt.cloud.project.core.service.service.project;

import com.xjt.cloud.commons.utils.Data;

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
}
