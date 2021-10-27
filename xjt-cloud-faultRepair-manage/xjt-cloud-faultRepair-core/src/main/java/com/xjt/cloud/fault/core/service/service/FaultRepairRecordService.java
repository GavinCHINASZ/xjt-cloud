package com.xjt.cloud.fault.core.service.service;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * 故障报修 Service接口
 *
 * @Author huanggc
 * @Date 2019/09/02
 **/
public interface FaultRepairRecordService extends BaseService {

    /**
     * 功能描述:查询 故障报修 列表
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/02
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findFaultRepairRecordList(String json);

    /**
     * @Description 查询app首页故障报修信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject findUserProjectFaultRepairData(String json);

    /**
     * 功能描述:故障报修 导出表格功能
     *
     * @param json
     * @param resp  HttpServletResponse
     * @auther: huanggc
     * @date: 2019/09/04
     * @return: com.xjt.cloud.commons.utils.Data
     */
    void downFaultRepairRecord(String json, HttpServletResponse resp);

    /**
     * 功能描述: 报表GB25201--B1导出方法
     *
     * @param json
     * @param response HttpServletResponse
     * @auther: huanggc
     * @date: 2020-04-09
     * @return: Data
     */
    void downWordB1(String json, HttpServletResponse resp);

    /**
     * 功能描述:故障报修 删除功能
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/04
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data deletedFaultRepairRecord(String json);

    /**
     * 功能描述:点击"工单号"查看报修进度
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/09
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findFaultProgress(String json);

    /**
     * 功能描述:报修工单进度更新
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/09
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data updateFaultProgress(String json);

    /**
     * 功能描述: APP保存继续维修(临时保存)
     *
     * @param json
     * @auther: huanggc
     * @date: 2020-03-27
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data updateFaultRepair(String json);

    /**
     * 新建故障报修
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/12
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data saveFaultRepairRecord(String json);

    /**
     * 新建故障报修
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/12
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data saveFaultRepairRecords(String json);

    /**
     * 功能描述: 故障处理人(项目中的成员)
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/10/28
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data faultUser(String json);

    /**
     * 功能描述:查询 故障报修 各状态数量
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/02
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findFaultRepairRecords(String json);

    /**
     * 功能描述: 项目主页 数量
     *
     * @param json
     * @auther: huanggc
     * @date: 2020-03-19
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findProjectCount(String json);

    /**
     * 功能描述:查询 故障报修 单个
     *
     * @param json
     * @auther huanggc
     * @date 2020-03-27
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findFaultRepairRecord(String json);

    /**
     * 功能描述: 大屏--故障报修列表
     *
     * @param json
     * @auther: huanggc
     * @date: 2020-03-19
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findScreenList(String json);

    /**
     * 功能描述: 地铁大屏--故障报修列表
     *
     * @param json
     * @auther: huanggc
     * @date: 2020-05-13
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findMetroScreenList(String json);

    /**@MethodName: findFaultRepairAnalysis
     * @Description: 查询故障分析数据
     * @Param: [json]
     * @Return: com.alibaba.fastjson.JSONObject
     * @Author: zhangZaiFa
     * @Date:2020/5/13 15:13
     **/
    Data findFaultRepairAnalysis(String json);
}
