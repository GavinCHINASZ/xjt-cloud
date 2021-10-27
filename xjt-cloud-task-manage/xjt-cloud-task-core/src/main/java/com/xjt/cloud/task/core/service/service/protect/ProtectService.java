package com.xjt.cloud.task.core.service.service.protect;

import com.xjt.cloud.commons.utils.Data;

/**
 * 地铁 班前防护 Service
 *
 * @author huangGuiChuan
 * @date 2020/09/27
 */
public interface ProtectService {

    /**
     * 查询 班前防护
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findProtect(String json);

    /**
     * 查询 班前防护 list
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findProtectList(String json);

    /**
     * 编辑 班前防护 list
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data updateProtect(String json);

    /**
     * 新增 班前防护 list
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveProtect(String json);

    /**
     * 开始防护
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/10/10
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data startProtect(String json);

    /**
     * 查询 剩余防护 时间
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/10/10
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data surplusProtect(String json);

    /**
     * 结束防护
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/10/10
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data deletedProtect(String json);

    /**
     * 查询用户是否在项目的作业中
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/10/10
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findIsProtectPerson(String json);

    /**
     * 查询 班前防护记录
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/12/18
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findProtectRecordList(String json);

    /**
     * 更新 作业状态
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2021/03/09
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data updateProtectState(String json);

    /**
     * 关闭 班前防护 list
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2021/03/09
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data closeProtect(String json);

    /**
     * 查询 作业状态
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2021/03/30
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findUserProtectState(String json);

    /**
     * 查询 作业的综合监测
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2021/04/02
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findIntegratedMonitoringList(String json);
}
