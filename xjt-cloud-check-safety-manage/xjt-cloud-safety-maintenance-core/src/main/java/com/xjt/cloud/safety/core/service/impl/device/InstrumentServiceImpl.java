package com.xjt.cloud.safety.core.service.impl.device;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.safety.core.dao.device.InstrumentDao;
import com.xjt.cloud.safety.core.entity.device.Instrument;
import com.xjt.cloud.safety.core.service.service.device.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName InstrumentServiceImpl
 * @Description 仪器管理
 * @Author wangzhiwen
 * @Date 2021/5/7 16:15
 **/
@Service
public class InstrumentServiceImpl extends AbstractService implements InstrumentService {
    @Autowired
    private InstrumentDao instrumentDao;

    /**
     * @Description 新增仪器信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveInstrument(String json){
        Instrument instrument = JSONObject.parseObject(json,Instrument.class);
        setEntityUserIdName(SecurityUserHolder.getUserName(), instrument.getProjectId(), instrument);
        return asseData(instrumentDao.saveInstrument(instrument));
    }

    /**
     * @Description 修改仪器信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data modifyInstrument(String json){
        Instrument instrument = JSONObject.parseObject(json,Instrument.class);
        return asseData(instrumentDao.modifyInstrument(instrument));
    }

    /**
     * @Description 查询仪器信息列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findInstrumentList(String json){
        Instrument instrument = JSONObject.parseObject(json,Instrument.class);
        Integer totalCount = instrument.getTotalCount();
        Integer pageSize = instrument.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){//判断是否存在总数，如没有，则查询总记录数
            totalCount = instrumentDao.findInstrumentListCount(instrument);
        }

        return asseData(totalCount, instrumentDao.findInstrumentList(instrument));
    }

    /////////////////////////////////////评估项目仪器管理 //////////////////////////////////////
    /**
     * @Description 新增评估项目仪器信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveCheckProjectInstrument(String json){
        Instrument instrument = JSONObject.parseObject(json,Instrument.class);
        Instrument delInstrument = new Instrument();
        delInstrument.setCheckProjectId(instrument.getCheckProjectId());
        instrumentDao.delCheckProjectInstrument(delInstrument);
        return asseData(instrumentDao.saveCheckProjectInstrument(instrument));
    }
    /**
     * @Description 删除评估项目仪器信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data delCheckProjectInstrument(String json){
        Instrument instrument = JSONObject.parseObject(json,Instrument.class);
        return asseData(instrumentDao.delCheckProjectInstrument(instrument));
    }

}
