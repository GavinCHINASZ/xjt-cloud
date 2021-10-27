package com.xjt.cloud.report.core.service.service;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.report.core.entity.report.FaultLevel;

/**
 * @Auther: zhangzf
 * @Date: 2019/11/12
 * @Description: 等级设置
 */
public interface FaultLevelService {

    /**@MethodName: save 等级设置保存
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/12 14:26
     **/
    Data save(String json);

    /**@MethodName: findByProjectNewVersionFaultLevel 查询项目内最新版本值班记录设置信息
     * @Description: 
     * @Param: [projectId]
     * @Return: com.xjt.cloud.report.core.entity.report.FaultLevel
     * @Author: zhangZaiFa
     * @Date:2019/11/12 15:43
     **/
    FaultLevel findByProjectNewVersionFaultLevel(Long projectId);

    /**@MethodName: findByFaultLevel 查询值班设置信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/13 9:38
     **/
    Data findByFaultLevel(String json);
}
