package com.xjt.cloud.maintenance.core.service.service.project;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName CheckProjectService 检测项目信息
 * @Author zhangZaiFa
 * @Date 2019-07-31 15:15
 * @Description
 */
public interface CheckProjectService {
    /**@MethodName: findByCheckProjectList
     * @Description: 查询检测项目信息列表
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:46
     **/
    Data findByCheckProjectList(String json);

    /**@MethodName: saveCheckProject
     * @Description: 保存检测项目信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:45
     **/
    Data saveCheckProject(String json);

    /**@MethodName: delCheckProject
     * @Description: 删除检测项目信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:45
     **/
    Data delCheckProject(String json);

    /**@MethodName: updCheckProject
     * @Description: 更新检测项目信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:45
     **/
    Data updCheckProject(String json);

    /**@MethodName: findByCheckProject
     * @Description: 查询检测项目信息详情
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:46
     **/
    Data findByCheckProject(String json);

    /**
     * 报告生成
     *
     * @param json
     * @Author huanggc
     * @Date 2020-04-10
     * @return com.xjt.cloud.commons.utils.Data
     **/
    Data saveReport(String json, HttpServletResponse response);

    /**
     * 报告查询
     *
     * @param json
     * @Author huanggc
     * @Date 2020-04-10
     * @return com.xjt.cloud.commons.utils.Data
     **/
    Data findReport(String json);

    /**
     * 报告下载
     *
     * @param json
     * @Author huanggc
     * @Date 2020-04-10
     * @return com.xjt.cloud.commons.utils.Data
     **/
    void downReport(String json, HttpServletResponse response);

    /**@MethodName: findCheckProjectReport
     * @Description: 查询检测报告扫描信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/12 10:27
     **/
    Data findCheckProjectReport(String number);

    /**@MethodName: findByMyCheckProjectList
     * @Description: 查询我的检测项目列表
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/14 9:15
     **/
    Data findByMyCheckProjectList(String json);

    /**
     * 打印标签
     *
     * @param json
     * @param response HttpServletResponse
     * @Author huanggc
     * @Date 2020-04-14
     * @return com.xjt.cloud.commons.utils.Data
     **/
    Data saveLabel(String json, HttpServletResponse response);

    ///////////////////////////////////合同管理///////////////////

    /**
     * @Description 查询合同列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/17 10:52
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findContractList(String json);

    /**
     * @Description 查询合同列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/17 10:52
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveContract(String json);

    /**
     * @Description 删除合同列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/17 10:52
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data delContract(String json);
}
