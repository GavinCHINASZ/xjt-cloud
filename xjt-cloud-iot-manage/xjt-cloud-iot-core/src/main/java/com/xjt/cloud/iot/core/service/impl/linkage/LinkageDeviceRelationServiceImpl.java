package com.xjt.cloud.iot.core.service.impl.linkage;

import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.dao.iot.linkage.LinkageDeviceRelationDao;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageDeviceRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (设计变动这个 service 还没用到)
 * 联动设备 service实现类
 *
 * @author huanggc
 * @date 2019/09/19
 **/
@Service
public class LinkageDeviceRelationServiceImpl extends AbstractService implements LinkageDeviceRelationService {
    @Autowired
    private LinkageDeviceRelationDao linkageDeviceRelationDao;

    /**
     * 根据 设备ID数组 删除联动设备(声光)
     *
     * @param deviceIds 设备id数组
     * @return Data
     */
    @Override
    public Data deleteDeviceTableByDeviceId(Long[] deviceIds) {
        Integer num = linkageDeviceRelationDao.findDeviceTableNumByDeviceId(deviceIds);
        return asseData(num);
    }

}
