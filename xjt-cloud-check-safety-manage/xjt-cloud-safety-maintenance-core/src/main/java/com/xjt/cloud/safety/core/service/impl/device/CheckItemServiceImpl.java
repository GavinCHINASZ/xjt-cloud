package com.xjt.cloud.safety.core.service.impl.device;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.safety.core.dao.device.CheckItemDao;
import com.xjt.cloud.safety.core.entity.device.CheckItem;
import com.xjt.cloud.safety.core.entity.device.DeviceType;
import com.xjt.cloud.safety.core.entity.project.Building;
import com.xjt.cloud.safety.core.service.service.device.CheckItemService;
import com.xjt.cloud.safety.core.service.service.device.DeviceTypeService;
import com.xjt.cloud.safety.core.service.service.project.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:30
 * @Description:巡检项管理业务接口实现类
 */
@Service
public class CheckItemServiceImpl extends AbstractService implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private DeviceTypeService deviceTypeService;

    /**
     *
     * 功能描述:查询设备巡检项列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @Override
    public Data findCheckItemList(String json){
        CheckItem checkItem = JSONObject.parseObject(json, CheckItem.class);
        Integer totalCount = checkItem.getTotalCount();
        if (null == totalCount && checkItem.getPageSize() != null && checkItem.getPageSize() != 0){
            totalCount = checkItemDao.findCheckItemListTotalCount(checkItem);
        }
        List<CheckItem> list = checkItemDao.findCheckItemList(checkItem);
        return asseData(totalCount, list);
    }

    /**
     *
     * 功能描述:查询设备巡检项列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @Override
    public Data findOfflineCheckInfoList(String json){
        CheckItem checkItem = JSONObject.parseObject(json, CheckItem.class);
        JSONObject jsonObject = new JSONObject();
        List<Building> buildingList = buildingService.findCheckProjectBuilding(checkItem.getCheckProjectId());
        jsonObject.put("buildingList" , buildingList);
        List<CheckItem> list = checkItemDao.findCheckItemList(checkItem);
        jsonObject.put("checkItemList" , list);
        DeviceType deviceType = new DeviceType();
        deviceType.setType(1);
        deviceType.setDeviceSysIds(checkItem.getDeviceSysIds());
        List<DeviceType> deviceSysList = deviceTypeService.findDeviceTypeList(deviceType);
        jsonObject.put("deviceSysList" , deviceSysList);
        deviceType = new DeviceType();
        deviceType.setType(9);
        deviceType.setDeviceSysIds(checkItem.getDeviceSysIds());
        List<DeviceType> deviceTypeList = deviceTypeService.findDeviceTypeList(deviceType);
        jsonObject.put("deviceTypeList" , deviceTypeList);
        return asseData(jsonObject);
    }

    /**
     *
     * 功能描述:查询设备巡检项列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @Override
    public Data findAllCheckItemTotal(String json){
        CheckItem checkItem = JSONObject.parseObject(json, CheckItem.class);
        return asseData(checkItemDao.findAllCheckItemTotal(checkItem));
    }

    /**
     *
     * 功能描述:查询设备巡检项
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:28
     */
    @Override
    public Data findCheckItem(String json){
        CheckItem checkItem = JSONObject.parseObject(json, CheckItem.class);
        checkItem = checkItemDao.findCheckItem(checkItem);
        return asseData(checkItem);
    }

    /**
     *
     * 功能描述: 设备巡检项缓存初使化
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/11/26 14:13
     */
    @Override
    public Data deviceCheckItemCacheInit(){
        CheckItem checkItem = new CheckItem();
        checkItem.setPageSize(null);
        String[] keys = {"deviceTypeId"};
        checkItem.setOrderCols(keys);
        List<CheckItem> list = checkItemDao.findCheckItemList(checkItem);
        //CacheUtils.setCacheByGroupList(list, Constants.DEVICE_CHECK_ITEM_CACHE_INIT_CACHE_KEY, keys, keys, CheckItem.class);//初使化缓存
        return Data.isSuccess();
    }
}
