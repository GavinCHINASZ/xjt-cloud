package com.xjt.cloud.project.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 * @program: xjt-cloud-project-manage
 * @description:全民消防设置
 * @author: zxb
 * @create: 2019-12-02 15:40
 **/
public interface NationalFireProtectionSettingService {
    /**@MethodName: findByProjectSetting
     * @Description: 查询项目全民消防设置
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/2 15:41
     **/
    Data findByProjectSetting(String json);

    /**@MethodName: saveProjectSetting
     * @Description: 保存项目全民消防设置
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/2 16:50
     **/
    Data saveProjectSetting(String json);

    /**@MethodName: saveFaultRecord
     * @Description: 生成故障记录
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/6 14:59
     **/
    Data saveFaultRecord(String json);

    /**@MethodName: findWeChatUser
     * @Description: 获取微信用户信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/6 16:12
     **/
    Data findWeChatUser(String json);
}
