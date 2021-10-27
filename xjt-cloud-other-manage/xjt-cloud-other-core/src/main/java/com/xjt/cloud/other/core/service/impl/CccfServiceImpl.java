package com.xjt.cloud.other.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosoft.module.ws.ServerWSPortType;
import com.xjt.cloud.commons.abstracts.MsgAbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.other.core.common.ConstantsOtherMsg;
import com.xjt.cloud.other.core.entity.Cccf;
import com.xjt.cloud.other.core.service.service.CccfService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @ClassName CccfServiceImpl cccf查询
 * @Author zhangZaiFa
 * @Date 2020-01-13 15:15
 * @Description
 */
@Service
public class CccfServiceImpl extends MsgAbstractService implements CccfService {

    /**
     * @MethodName: findLabelInfoByCode
     * @Description: CCCF按条形码查询产品信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/1/13 14:15
     **/
    @Override
    public Data findLabelInfoByCode(String json) {
        Cccf entity = JSONObject.parseObject(json, Cccf.class);
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(ServerWSPortType.class);
        jaxWsProxyFactoryBean.setAddress(ConstantsOtherMsg.CCCF_LABEL_CODE_URL);
        ServerWSPortType serverWSPortType = jaxWsProxyFactoryBean.create(ServerWSPortType.class);
        String result = serverWSPortType.getLabelInfoByCode(entity.getCode());
        List<Cccf> list = JSONArray.parseArray(result, Cccf.class);
        SysLog.info("----返回信息------->" + result);
        if (result != null && list.size() > 0) {
            for (Cccf cccf : list) {
                cccf.setCode(entity.getCode());
                String result2 = serverWSPortType.findLableFlowInfo(entity.getCode(), cccf.getDeptId(), cccf.getDeptName());
                List<Cccf> cccfList = JSONArray.parseArray(result2, Cccf.class);
                if (cccfList != null && cccfList.size()>0) {
                    cccf.setGoodsReachDate(cccfList.get(0).getGoodsReachDate());
                    cccf.setBusinessmanInfo(cccfList.get(0).getBusinessmanInfo());
                    cccf.setStatus(cccfList.get(0).getStatus());
                    cccf.setFlowArea(cccfList.get(0).getFlowArea());
                    cccf.setSaleSort(cccfList.get(0).getSaleSort());
                }
            }
        }
        return asseData(list);
    }
}
