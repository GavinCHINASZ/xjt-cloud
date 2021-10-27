package com.xjt.cloud.safety.api.controller.device;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.safety.core.service.service.device.RiskProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RiskProblemController
 * @Description 高风险问题管理
 * @Author wangzhiwen
 * @Date 2021/5/7 17:03
 **/
@RestController
@RequestMapping("/risk/problem/")
public class RiskProblemController extends AbstractController {
    @Autowired
    private RiskProblemService riskProblemService;

    /**
     * @Description 新增高风险问题信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveRiskProblem")
    public Data saveRiskProblem(String json){
        return riskProblemService.saveRiskProblem(json);
    }

    /**
     * @Description 新增高风险问题信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveRiskProblemList")
    public Data saveRiskProblemList(String json){
        return riskProblemService.saveRiskProblemList(json);
    }

    /**
     * @Description 修改高风险问题信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "modifyRiskProblem")
    public Data modifyRiskProblem(String json){
        return riskProblemService.modifyRiskProblem(json);
    }

    /**
     * @Description 查询高风险问题信息列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findRiskProblemList")
    public Data findRiskProblemList(String json){
        return riskProblemService.findRiskProblemList(json);
    }
}
