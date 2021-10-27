package com.xjt.cloud.safety.core.service.impl.device;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.safety.core.dao.device.RiskProblemDao;
import com.xjt.cloud.safety.core.entity.device.RiskProblem;
import com.xjt.cloud.safety.core.service.service.device.RiskProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RiskProblemServiceImpl
 * @Description  高风险问题管理
 * @Author wangzhiwen
 * @Date 2021/5/7 17:03
 **/
@Service
public class RiskProblemServiceImpl extends AbstractService implements RiskProblemService {
    @Autowired
    private RiskProblemDao riskProblemDao;

    /**
     * @Description 新增高风险问题信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveRiskProblem(String json){
        RiskProblem riskProblem = JSONObject.parseObject(json,RiskProblem.class);
        setEntityUserIdName(SecurityUserHolder.getUserName(), riskProblem.getProjectId(), riskProblem);
        if (riskProblem.getId() != null){
            return asseData(riskProblemDao.modifyRiskProblem(riskProblem));
        }
        return asseData(riskProblemDao.saveRiskProblem(riskProblem));
    }

    /**
     * @Description 新增高风险问题信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveRiskProblemList(String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<RiskProblem> list = JSONArray.parseArray(jsonObject.getString("list"), RiskProblem.class);
        RiskProblem riskProblem = JSONObject.parseObject(json,RiskProblem.class);
        RiskProblem delRiskProblem = new RiskProblem();
        delRiskProblem.setStatus(99);
        delRiskProblem.setCheckRecordId(riskProblem.getCheckRecordId());
        riskProblemDao.modifyRiskProblem(delRiskProblem);//删除该评估记录的所有问题信息
        setEntityUserIdName(SecurityUserHolder.getUserName(), riskProblem.getProjectId(), riskProblem);
        return asseData(riskProblemDao.saveRiskProblemList(riskProblem,list));
    }

    /**
     * @Description 修改高风险问题信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data modifyRiskProblem(String json){
        RiskProblem riskProblem = JSONObject.parseObject(json,RiskProblem.class);
        return asseData(riskProblemDao.modifyRiskProblem(riskProblem));
    }

    /**
     * @Description 查询高风险问题信息列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findRiskProblemList(String json){
        RiskProblem riskProblem = JSONObject.parseObject(json,RiskProblem.class);
        Integer totalCount = riskProblem.getTotalCount();
        Integer pageSize = riskProblem.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){//判断是否存在总数，如没有，则查询总记录数
            totalCount = riskProblemDao.findRiskProblemListCount(riskProblem);
        }

        return asseData(totalCount, riskProblemDao.findRiskProblemList(riskProblem));
    }
}
