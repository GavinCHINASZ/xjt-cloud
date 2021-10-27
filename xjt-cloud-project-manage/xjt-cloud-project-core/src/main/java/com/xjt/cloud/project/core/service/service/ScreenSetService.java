package com.xjt.cloud.project.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 * @program: xjt-cloud-project-manage
 * @description:
 * @author: zxb
 * @create: 2020-04-07 17:02
 **/
public interface ScreenSetService {

    /**@MethodName: findScreenSet
     * @Description: findScreenManage 查看项目大屏管理
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/7 17:02
     **/
    Data findScreenSet(String json);

    /**@MethodName: save
     * @Description: 保存大屏设置
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/7 18:24
     **/
    Data save(String json);

    /**@MethodName: findScreenProjectList
     * @Description: 查询项目列表
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/9 9:02
     **/
    Data findScreenProjectList(String json);

    /**@MethodName: findScreenProjectMapData
     * @Description: 查询大屏项目地图数据
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/9 9:44
     **/
    Data findScreenProjectMapData(String json);

    /**@MethodName: findUserScreenPermission
     * @Description: 查询用户是否有大屏功能
     * @Param: []
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/27 11:15
     **/
    Data findUserScreenPermission();

    /**
     * 查询 地铁大屏
     *
     * @MethodName: findMetroScreen
     * @param json
     * @Author hunaggc
     * @Date 2020-05-09
     * @Return com.xjt.cloud.commons.utils.Data
     **/
    Data findMetroScreen(String json);

    /**
     * 地铁大屏--跳转
     *
     * @MethodName: findMetroJump
     * @param json
     * @Author hunaggc
     * @Date 2020-05-09
     * @Return com.xjt.cloud.commons.utils.Data
     **/
    Data findMetroJump(String json);
}
