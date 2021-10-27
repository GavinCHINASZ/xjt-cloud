package com.xjt.cloud.sys.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.dao.sys.CloudInitDao;
import com.xjt.cloud.sys.core.entity.CloudInit;
import com.xjt.cloud.sys.core.service.service.CloudInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/16 11:34
 * @Description: 平台信息初使化管理接口实现类
 */
@Service
public class CloudInitServiceImpl extends AbstractService implements CloudInitService {
    @Autowired
    private CloudInitDao cloudInitDao;

    /**
     *
     * 功能描述:平台信息初使化列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    @Override
    public Data findCloudInitList(String json){
        CloudInit cloudInit = JSONObject.parseObject(json, CloudInit.class);
        Integer totalCount = cloudInit.getTotalCount();
        Integer pageSize = cloudInit.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){//判断是否存在总数，如没有，则查询总记录数
            totalCount = cloudInitDao.findCloudInitListTotalCount(cloudInit);
        }
        List<CloudInit> list = cloudInitDao.findCloudInitList(cloudInit);
        return asseData(totalCount, list);
    }

    /**
     *
     * 功能描述:平台信息初使化列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    @Override
    public Data findCloudInitChildList(String json){
        CloudInit cloudInit = JSONObject.parseObject(json, CloudInit.class);
        Integer totalCount = cloudInit.getTotalCount();
        Integer pageSize = cloudInit.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){//判断是否存在总数，如没有，则查询总记录数
            totalCount = cloudInitDao.findCloudInitChildListTotalCount(cloudInit);
        }
        List<CloudInit> list = cloudInitDao.findCloudInitChildList(cloudInit);
        return asseData(totalCount, list);
    }
}
