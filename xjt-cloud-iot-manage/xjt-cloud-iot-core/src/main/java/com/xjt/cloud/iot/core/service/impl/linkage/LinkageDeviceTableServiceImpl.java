package com.xjt.cloud.iot.core.service.impl.linkage;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.dao.iot.linkage.LinkageDeviceTableDao;
import com.xjt.cloud.iot.core.entity.linkage.LinkageDeviceTable;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageDeviceTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 联动设备 service实现类
 *
 * @author huanggc
 * @date 2019/09/19
 **/
@Service
public class LinkageDeviceTableServiceImpl extends AbstractService implements LinkageDeviceTableService {
    @Autowired
    private LinkageDeviceTableDao linkageDeviceTableDao;

    /**
     * 功能描述:查询 联动设备 列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/08/17
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findDeviceTableList(String json) {
        LinkageDeviceTable linkageDeviceTable = JSONObject.parseObject(json, LinkageDeviceTable.class);

        Integer totalCount = linkageDeviceTable.getTotalCount();
        Integer pageSize = linkageDeviceTable.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize){
            totalCount = linkageDeviceTableDao.findDeviceTableTotalCount(linkageDeviceTable);
        }

        List<LinkageDeviceTable> linkageDeviceTableList = linkageDeviceTableDao.findDeviceTableList(linkageDeviceTable);
        return asseData(totalCount, linkageDeviceTableList);
    }

}
