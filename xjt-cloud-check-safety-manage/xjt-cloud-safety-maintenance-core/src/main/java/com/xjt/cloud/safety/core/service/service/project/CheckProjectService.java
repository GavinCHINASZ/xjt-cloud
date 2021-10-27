package com.xjt.cloud.safety.core.service.service.project;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName CheckProjectService 检测项目信息
 * @Author zhangZaiFa
 * @Date 2019-07-31 15:15
 * @Description
 */
public interface CheckProjectService {
    /**
     * @MethodName: findByCheckProjectList
     * @Description: 查询检测项目信息列表
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:46
     **/
    Data findByCheckProjectList(String json);

    /**
     * @MethodName: saveCheckProject
     * @Description: 保存检测项目信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:45
     **/
    Data saveCheckProject(String json);

    /**
     * @MethodName: delCheckProject
     * @Description: 删除检测项目信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:45
     **/
    Data delCheckProject(String json);

    /**
     * @MethodName: updCheckProject
     * @Description: 更新检测项目信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:45
     **/
    Data updCheckProject(String json);

    /**
     * @MethodName: findByCheckProject
     * @Description: 查询检测项目信息详情
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:46
     **/
    Data findByCheckProject(String json);

    /**
     * 报告查询
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2020-04-10
     **/
    Data findReport(String json);

    /**
     * 报告下载
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2020-04-10
     **/
    void downReport(String json, HttpServletResponse response);

    /**
     * @MethodName: findCheckProjectReport
     * @Description: 查询检测报告扫描信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/12 10:27
     **/
    Data findCheckProjectReport(String number);

    /**
     * @MethodName: findByMyCheckProjectList
     * @Description: 查询我的检测项目列表
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/14 9:15
     **/
    Data findByMyCheckProjectList(String json);

    ///////////////////////////////////合同管理///////////////////

    /**
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Description 查询合同列表
     * @author wangzhiwen
     * @date 2021/4/17 10:52
     */
    Data findContractList(String json);

    /**
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Description 查询合同列表
     * @author wangzhiwen
     * @date 2021/4/17 10:52
     */
    Data saveContract(String json);

    /**
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Description 删除合同列表
     * @author wangzhiwen
     * @date 2021/4/17 10:52
     */
    Data delContract(String json);
}
