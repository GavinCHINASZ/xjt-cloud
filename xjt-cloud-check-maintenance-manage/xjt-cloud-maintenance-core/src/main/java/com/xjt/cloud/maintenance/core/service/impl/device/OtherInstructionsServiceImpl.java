package com.xjt.cloud.maintenance.core.service.impl.device;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.dao.device.OtherInstructionsDao;
import com.xjt.cloud.maintenance.core.entity.device.OtherInstructions;
import com.xjt.cloud.project.core.service.service.device.OtherInstructionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName OtherInstructionsServiceImpl
 * @Author dwt
 * @Date 2020-04-10 16:00
 * @Version 1.0
 */
@Service
public class OtherInstructionsServiceImpl extends AbstractService implements OtherInstructionsService {

    @Autowired
    private OtherInstructionsDao otherInstructionsDao;

    @Override
    public Data findOtherInstructions(String json) {
        OtherInstructions o = JSONObject.parseObject(json,OtherInstructions.class);
        OtherInstructions other = otherInstructionsDao.findOtherInstructions(o);
        return asseData(other);
    }

    @Override
    public Data saveOtherInstructions(String json) {
        OtherInstructions o = JSONObject.parseObject(json,OtherInstructions.class);
        Integer a;
        if(o.getId() == null || o.getId() == 0){
            a = otherInstructionsDao.saveOtherInstructions(o);
        }else{
            a = otherInstructionsDao.modifyOtherInstructions(o);
        }
        if(a > 0){
            return Data.isSuccess();
        }
        return Data.isFail();
    }
}
