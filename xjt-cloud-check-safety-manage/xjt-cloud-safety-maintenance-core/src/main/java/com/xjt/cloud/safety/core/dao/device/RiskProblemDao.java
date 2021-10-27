package com.xjt.cloud.safety.core.dao.device;

import com.xjt.cloud.safety.core.entity.device.RiskProblem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName RiskProblemDao
 * @Description  高风险问题管理
 * @Author wangzhiwen
 * @Date 2021/5/7 17:04
 **/
@Repository
public interface RiskProblemDao {
    /**
     * @Description 新增高风险问题信息
     *
     * @param riskProblem
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return int
     */
    int saveRiskProblem(RiskProblem riskProblem);

    /**
     * @Description 新增高风险问题信息
     *
     * @param riskProblem
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return int
     */
    int saveRiskProblemList(@Param("riskProblem") RiskProblem riskProblem,@Param("list")List<RiskProblem> list);

    /**
     * @Description 修改高风险问题信息
     *
     * @param riskProblem
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    int modifyRiskProblem(RiskProblem riskProblem);

    /**
     * @Description 查询高风险问题信息列表
     *
     * @param riskProblem
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    List<RiskProblem> findRiskProblemList(RiskProblem riskProblem);

    /**
     * @Description 查询高风险问题信息列表
     *
     * @param riskProblem
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    int findRiskProblemListCount(RiskProblem riskProblem);
}
