package com.xjt.cloud.maintenance.core.service.impl.device;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.dao.device.CheckItemConfigDao;
import com.xjt.cloud.maintenance.core.entity.device.CheckItemConfig;
import com.xjt.cloud.maintenance.core.service.service.device.CheckItemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/11 09:30
 * @Description:pc检测项配置
 */
@Service
public class CheckItemConfigServiceImpl  extends AbstractService implements CheckItemConfigService {
    @Autowired
    private CheckItemConfigDao checkItemConfigDao;

    /**
     *
     * 功能描述:查询pc检测项配置息列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data findCheckResult(String json){
        CheckItemConfig checkItemConfig = JSONObject.parseObject(json,CheckItemConfig.class);
        checkItemConfig = checkItemConfigDao.findCheckResult(checkItemConfig);
        return asseData(checkItemConfig);
    }

    /**
     *
     * 功能描述:查询pc检测项配置息列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data findCheckItemConfigList(String json){
        CheckItemConfig checkItemConfig = JSONObject.parseObject(json,CheckItemConfig.class);
        Integer totalCount = checkItemConfig.getTotalCount();
        Integer pageSize = checkItemConfig.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){//判断是否存在总数，如没有，则查询总记录数
            totalCount = checkItemConfigDao.findCheckItemConfigListCount(checkItemConfig);
        }
        List<CheckItemConfig> list = checkItemConfigDao.findCheckItemConfigList(checkItemConfig);
        return asseData(totalCount, list);
    }

    /**
     *
     * 功能描述:新增pc检测项配置息
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data saveCheckItemConfig(String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<CheckItemConfig> list = JSONArray.parseArray(jsonObject.getString("list"), CheckItemConfig.class);
        int num = 0;
        if (CollectionUtils.isNotEmpty(list)){
            CheckItemConfig checkItemConfig = list.get(0);
            if (checkItemConfig.getId() == null){
                num = checkItemConfigDao.saveCheckItemConfigList(list);
            }else{
                CheckItemConfig tmpCheckItemConfig = new CheckItemConfig();
                tmpCheckItemConfig.setCheckProjectId(checkItemConfig.getCheckProjectId());
                tmpCheckItemConfig.setDeviceTypeId(checkItemConfig.getDeviceTypeId());
                checkItemConfigDao.delCheckItemConfig(list);
                num = checkItemConfigDao.saveCheckItemConfigList(list);
            }
        }

        return asseData(num);
    }

    /**
     *
     * 功能描述:修改pc检测项配置信息
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data modifyCheckItemConfig(String json){
        CheckItemConfig checkItemConfig = JSONObject.parseObject(json,CheckItemConfig.class);
        int num = checkItemConfigDao.modifyCheckItemConfig(checkItemConfig);
        return asseData(num);
    }
}
