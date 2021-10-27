package com.xjt.cloud.project.core.dao.project;

import com.xjt.cloud.project.core.entity.NationalFireProtectionSetting;
import org.springframework.stereotype.Repository;

/**
 * @program: xjt-cloud-project-manage
 * @description:全民消防设置
 * @author: zxb
 * @create: 2019-12-02 15:44
 **/
@Repository
public interface NationalFireProtectionSettingServiceDao {
    /**@MethodName: findByProjectSetting
     * @Description: 查询全民消防设置
     * @Param: [entity]
     * @Return: com.xjt.cloud.project.core.entity.NationalFireProtectionSetting
     * @Author: zhangZaiFa
     * @Date:2019/12/2 15:47
     **/
    NationalFireProtectionSetting findByProjectSetting(NationalFireProtectionSetting entity);

    /**@MethodName: saveProjectSetting
     * @Description: 保存全民消防设置
     * @Param: [entity]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/12/2 17:18
     **/
    void saveProjectSetting(NationalFireProtectionSetting entity);

    /**@MethodName: deleteProjectSetting
     * @Description: 删除全民消防设置
     * @Param: [entity]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/12/4 14:34
     **/
    void deleteProjectSetting(NationalFireProtectionSetting entity);
}
