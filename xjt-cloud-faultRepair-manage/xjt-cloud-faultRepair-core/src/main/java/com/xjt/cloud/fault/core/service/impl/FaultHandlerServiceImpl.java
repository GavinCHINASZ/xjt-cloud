package com.xjt.cloud.fault.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.fault.core.dao.fault.FaultHandlerDao;
import com.xjt.cloud.fault.core.entity.fault.FaultHandler;
import com.xjt.cloud.fault.core.service.service.FaultHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 故障处理人/维修人 实现类
 *
 * @author huanggc
 * @date 2019/09/02
 **/
@Service
public class FaultHandlerServiceImpl extends AbstractService implements FaultHandlerService {
    @Autowired
    private FaultHandlerDao faultHandlerDao;

    /**
     * 功能描述:查询 故障处理人 列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/02
     */
    @Override
    public Data findFaultHandlerList(String json) {
        FaultHandler faultHandler = JSONObject.parseObject(json, FaultHandler.class);

        List<FaultHandler> faultHandlerList = faultHandlerDao.findFaultHandlerList(faultHandler);
        return asseData(faultHandlerList);
    }

    /**
     * 功能描述:删除功能
     *
     * @param faultHandler 故障处理人 实体
     * @return Integer 删除成功的数量
     * @author huanggc
     * @date 2019/09/06
     */
    public Integer deletedFaultHandlerList(FaultHandler faultHandler) {
        return faultHandlerDao.deletedFaultHandler(faultHandler);
    }
}
