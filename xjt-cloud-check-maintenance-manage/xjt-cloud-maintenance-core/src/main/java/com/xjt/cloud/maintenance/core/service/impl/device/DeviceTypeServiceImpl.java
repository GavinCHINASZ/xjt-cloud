package com.xjt.cloud.maintenance.core.service.impl.device;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import com.xjt.cloud.maintenance.core.common.ConstantsDevice;
import com.xjt.cloud.maintenance.core.dao.device.DeviceTypeDao;
import com.xjt.cloud.maintenance.core.entity.device.DeviceType;
import com.xjt.cloud.maintenance.core.service.service.device.DeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/17 14:52
 * @Description:设备系统与类型管理业务接口实现类
 */
@Service
public class DeviceTypeServiceImpl extends AbstractService implements DeviceTypeService {
    @Autowired
    private DeviceTypeDao deviceTypeDao;
    @Autowired
    private SecurityLogService securityLogService;

    /**
     *
     * 功能描述:查询设备系统信息列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data findParentDeviceTypeList(String json){
        return findDeviceTypeList(json, 1);
    }

    /**
     *
     * 功能描述:查询设备系统信息
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data findParentDeviceType(String json){
        DeviceType deviceType = JSONObject.parseObject(json,DeviceType.class);
        deviceType = deviceTypeDao.findParentDeviceType(deviceType);
        return asseData(deviceType);
    }


    /**
     *
     * 功能描述:查询设备类型信息列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data findDeviceTypeList(String json){
        return findDeviceTypeList(json, 9);
    }

    /**
     *
     * 功能描述:查询设备类型信息列表,以巡检点信息为条件 未绑定物联设备
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data findDeviceTypeListByCheckPoint(String json){
        DeviceType deviceType = JSONObject.parseObject(json,DeviceType.class);
        deviceType.setIotId(0L);
        List<DeviceType> list = deviceTypeDao.findDeviceTypeListByCheckPoint(deviceType);
        return asseData(list);
    }

    /**
     *
     * 功能描述:查询设备类型信息列表,以巡检点信息为条件 已绑定绑定物联设备
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data findDeviceTypeListByCheckPointBind(String json){
        DeviceType deviceType = JSONObject.parseObject(json,DeviceType.class);
        deviceType.setIotId(1L);
        List<DeviceType> list = deviceTypeDao.findDeviceTypeListByCheckPoint(deviceType);
        return asseData(list);
    }

    /**
     *
     * 功能描述:查询设备系统信息列表
     *
     * @param json
     * @param type
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/8 10:47
     */
    private Data findDeviceTypeList(String json, int type){
        DeviceType deviceType = JSONObject.parseObject(json,DeviceType.class);
        Integer totalCount = deviceType.getTotalCount();
        Integer pageSize = deviceType.getPageSize();
        deviceType.setType(type);
        if (null == totalCount && null != pageSize && 0 != pageSize){//判断是否存在总数，如没有，则查询总记录数
            totalCount = deviceTypeDao.findDeviceTypeListCount(deviceType);
        }

        return asseData(totalCount, findDeviceTypeList(deviceType));
    }

    /**
     *
     * 功能描述:查询设备类型信息列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public List<DeviceType> findDeviceTypeList(DeviceType deviceType){
        List<DeviceType> list = deviceTypeDao.findDeviceTypeList(deviceType);
        return list;
    }



    /**
     *
     * 功能描述:查询设备类型信息
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data findDeviceType(String json){
        DeviceType deviceType = JSONObject.parseObject(json,DeviceType.class);
        deviceType = deviceTypeDao.findDeviceType(deviceType);
        return asseData(deviceType);
    }

    /**
     *
     * 功能描述:查询设备系统树
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/9 9:03
     */
    @Override
    public Data findDeviceSysTree(String json){
        DeviceType deviceType = JSONObject.parseObject(json,DeviceType.class);
        List<DeviceType> list = deviceTypeDao.findDeviceSysTree(deviceType);
        return asseData(list);
    }

    /**
     *
     * 功能描述:查询未删除设备类型信息列表，以拼音排序
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/9 9:03
     */
    @Override
    public Data findDeviceTypeOrderPinYin(String json){
        DeviceType deviceType = JSONObject.parseObject(json,DeviceType.class);
        List<DeviceType> list = deviceTypeDao.findDeviceTypeOrderPinYin(deviceType);
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> jsonList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)){
            List<DeviceType> childList = new ArrayList<>();
            for (int i = 0; i < list.size();i++){
                deviceType = list.get(i);
                childList.add(deviceType);
                if (i == list.size() - 1 || (StringUtils.isEmpty(deviceType.getPinYinInitials()) && StringUtils.isNotEmpty(list.get(i + 1).getPinYinInitials()))
                || (StringUtils.isNotEmpty(deviceType.getPinYinInitials()) && StringUtils.isNotEmpty(list.get(i + 1).getPinYinInitials())
                && !deviceType.getPinYinInitials().substring(0,1).equals(list.get(i + 1).getPinYinInitials().substring(0, 1)))){
                    jsonObject.put("type", StringUtils.isNotEmpty(deviceType.getPinYinInitials()) ? deviceType.getPinYinInitials().substring(0, 1) : "");
                    jsonObject.put("child",childList);
                    jsonList.add(jsonObject);
                    childList = new ArrayList<>();
                    jsonObject = new JSONObject();
                }
            }
        }
        return asseData(jsonList);
    }


    /**
     *
     * 功能描述:设备系统缓存初使化
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/8 9:51
     */
    @Override
    public void deviceSysCacheInit(){
        DeviceType deviceType = new DeviceType();
        deviceType.setPageSize(null);
        List<DeviceType> list = deviceTypeDao.findDeviceTypeList(deviceType);
        CacheUtils.setCacheByList(list, Constants.DEVICE_SYS_CACHE_KEY,  DeviceType.class);//初使化缓存
    }
}
