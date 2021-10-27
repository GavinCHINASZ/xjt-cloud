package com.xjt.cloud.device.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.device.core.dao.device.CheckItemDao;
import com.xjt.cloud.device.core.entity.CheckItem;
import com.xjt.cloud.device.core.service.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 巡检项管理业务接口实现类
 *
 * @author wangzhiwen
 * @date 2019/7/18 17:30
 */
@Service
public class CheckItemServiceImpl extends AbstractService implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 功能描述:查询设备巡检项列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/19 9:28
     */
    @Override
    public Data findCheckItemList(String json){
        CheckItem checkItem = JSONObject.parseObject(json, CheckItem.class);
        String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY,checkItem.getProjectId() + "","checkItemVsType");
        checkItem.setCheckItemVsType(StringUtils.isNotEmpty(checkItemVsType) ? Integer.parseInt(checkItemVsType) : 1);

        Integer totalCount = checkItem.getTotalCount();
        if (null == totalCount && checkItem.getPageSize() != null && checkItem.getPageSize() != 0){
            totalCount = checkItemDao.findCheckItemListTotalCount(checkItem);
        }

        List<CheckItem> list = checkItemDao.findCheckItemList(checkItem);
        return asseData(totalCount, list);
    }
}
