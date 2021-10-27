package com.xjt.cloud.project.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.BuildingService;
import com.xjt.cloud.project.core.service.service.NationalFireProtectionSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName BuildingController 全民消防设置
 * @Author zhangZaiFa
 * @Date 2019-12-02 15:15
 * @Description
 */
@RestController
@RequestMapping("/project/nationalFireProtectionSetting")
public class NationalFireProtectionSettingController extends AbstractController {

    @Autowired
    private NationalFireProtectionSettingService nationalFireProtectionSettingService;




    /**@MethodName: findByProjectSetting
     * @Description: 查询项目全民消防设置
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/2 15:40
     **/
    @RequestMapping(value = "/findByProjectSetting")
    public Data findByProjectSetting(String json) {
        return nationalFireProtectionSettingService.findByProjectSetting(json);
    }


    /**@MethodName: saveProjectSetting
     * @Description: 保存项目全民消防设置
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/2 16:50
     **/
    @RequestMapping(value = "/saveProjectSetting/{projectId}")
    public Data saveProjectSetting(String json) {
        return nationalFireProtectionSettingService.saveProjectSetting(json);
    }

    /**@MethodName: saveFaultRecord
     * @Description: 生成故障记录
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/6 14:59
     **/
    @RequestMapping(value = "/saveFaultRecord")
    public Data saveFaultRecord(String json) {
        return nationalFireProtectionSettingService.saveFaultRecord(json);
    }

    /**@MethodName: findWeChatUser
     * @Description: 获取微信用户信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/6 14:59
     **/
    @RequestMapping(value = "/findWeChatUser")
    public Data findWeChatUser(String json) {
        return nationalFireProtectionSettingService.findWeChatUser(json);
    }



}
