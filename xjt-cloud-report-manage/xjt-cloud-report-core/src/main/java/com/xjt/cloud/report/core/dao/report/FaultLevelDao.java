package com.xjt.cloud.report.core.dao.report;

import com.xjt.cloud.report.core.entity.report.FaultLevel;

/**
 * @Auther: zhangzf
 * @Date: 2019/11/12
 * @Description: 等级设置
 */
public interface FaultLevelDao {

    /**@MethodName: save 保存故障等级设置
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/12 14:41
     **/
    void save(FaultLevel faultLevel);

    /**@MethodName: findByFaultLevel 按条件查询信息
     * @Description: 
     * @Param: [faultLevel]
     * @Return: com.xjt.cloud.report.core.entity.report.FaultLevel
     * @Author: zhangZaiFa
     * @Date:2019/11/12 15:43 
     **/
    FaultLevel findByFaultLevel(FaultLevel faultLevel);

    /**@MethodName: updateVersion 更新版本
     * @Description:
     * @Param: [projectId]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/12 16:03
     **/
    void updateVersion(FaultLevel faultLevel);
}
