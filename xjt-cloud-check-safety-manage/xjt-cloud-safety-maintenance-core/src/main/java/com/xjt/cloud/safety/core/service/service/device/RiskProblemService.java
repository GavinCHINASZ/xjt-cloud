package com.xjt.cloud.safety.core.service.service.device;

import com.xjt.cloud.commons.utils.Data;

/**
 * @ClassName RiskProblemService
 * @Description  高风险问题管理
 * @Author wangzhiwen
 * @Date 2021/5/7 17:03
 **/
public interface RiskProblemService {

    /**
     * @Description 新增高风险问题信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveRiskProblem(String json);

    /**
     * @Description 新增高风险问题信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveRiskProblemList(String json);

    /**
     * @Description 修改高风险问题信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data modifyRiskProblem(String json);

    /**
     * @Description 查询高风险问题信息列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findRiskProblemList(String json);
}
