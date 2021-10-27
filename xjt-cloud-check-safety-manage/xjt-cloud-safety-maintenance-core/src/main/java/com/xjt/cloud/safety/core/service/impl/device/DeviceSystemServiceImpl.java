package com.xjt.cloud.safety.core.service.impl.device;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import com.xjt.cloud.safety.core.common.ConstantsDevice;
import com.xjt.cloud.safety.core.dao.device.DeviceSystemDao;
import com.xjt.cloud.safety.core.entity.device.DeviceSystem;
import com.xjt.cloud.safety.core.service.service.device.DeviceSystemService;
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
public class DeviceSystemServiceImpl extends AbstractService implements DeviceSystemService {
    @Autowired
    private DeviceSystemDao deviceSystemDao;
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
        DeviceSystem deviceType = JSONObject.parseObject(json,DeviceSystem.class);
        deviceType = deviceSystemDao.findParentDeviceType(deviceType);
        return asseData(deviceType);
    }

    /**
     *
     * 功能描述:新增设备系统信息
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data saveParentDeviceType(String json){
        DeviceSystem deviceType = JSONObject.parseObject(json,DeviceSystem.class);
        deviceType.setPinYinInitials(PinYinUtils.getFirstSpell(deviceType.getDeviceSysName()).toUpperCase());
        deviceType.setType(1);
        int num = deviceSystemDao.saveParentDeviceType(deviceType);
        securityLogService.saveSecurityLog(ConstantsDevice.SECURITY_LOG_TYPE_ADD_DEVICE_SYS, SecurityUserHolder.getUserName(), deviceType,
                null, null, Constants.SOURCE_SYS);
        return asseData(num);
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
        DeviceSystem deviceType = JSONObject.parseObject(json,DeviceSystem.class);
        deviceType.setIotId(0L);
        List<DeviceSystem> list = deviceSystemDao.findDeviceTypeListByCheckPoint(deviceType);
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
        DeviceSystem deviceType = JSONObject.parseObject(json,DeviceSystem.class);
        deviceType.setIotId(1L);
        List<DeviceSystem> list = deviceSystemDao.findDeviceTypeListByCheckPoint(deviceType);
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
        DeviceSystem deviceType = JSONObject.parseObject(json,DeviceSystem.class);
        Integer totalCount = deviceType.getTotalCount();
        Integer pageSize = deviceType.getPageSize();
        deviceType.setType(type);
        if (null == totalCount && null != pageSize && 0 != pageSize){//判断是否存在总数，如没有，则查询总记录数
            totalCount = deviceSystemDao.findDeviceTypeListCount(deviceType);
        }

        List<DeviceSystem> list = deviceSystemDao.findDeviceTypeList(deviceType);
        return asseData(totalCount, list);
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
        DeviceSystem deviceType = JSONObject.parseObject(json,DeviceSystem.class);
        deviceType = deviceSystemDao.findDeviceType(deviceType);
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
        DeviceSystem deviceType = JSONObject.parseObject(json,DeviceSystem.class);
        List<DeviceSystem> list = deviceSystemDao.findDeviceSysTree(deviceType);
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
        DeviceSystem deviceType = JSONObject.parseObject(json,DeviceSystem.class);
        List<DeviceSystem> list = deviceSystemDao.findDeviceTypeOrderPinYin(deviceType);
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> jsonList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)){
            List<DeviceSystem> childList = new ArrayList<>();
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
     * 功能描述:新增设备类型信息
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data saveDeviceType(String json){
        DeviceSystem deviceType = JSONObject.parseObject(json,DeviceSystem.class);
        deviceType.setPinYinInitials(PinYinUtils.getFirstSpell(deviceType.getDeviceSysName()).toUpperCase());
        deviceType.setType(9);
        int num = deviceSystemDao.saveDeviceType(deviceType);
        securityLogService.saveSecurityLog(ConstantsDevice.SECURITY_LOG_TYPE_ADD_DEVICE_SYS, SecurityUserHolder.getUserName(), deviceType,
                null, null, Constants.SOURCE_SYS);
        return asseData(num);
    }

    /**
     *
     * 功能描述:修改设备类型信息,可以以父id为多少修改其下的所有类型
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data modifyDeviceType(String json){
        DeviceSystem deviceType = JSONObject.parseObject(json,DeviceSystem.class);
        if (StringUtils.isNotEmpty(deviceType.getDeviceSysName())){
            deviceType.setPinYinInitials(PinYinUtils.getFirstSpell(deviceType.getDeviceSysName()).toUpperCase());
        }
        DeviceSystem oldDeviceType = new DeviceSystem();
        oldDeviceType.setId(deviceType.getId());
        oldDeviceType = deviceSystemDao.findDeviceType(deviceType);
        int num = deviceSystemDao.modifyDeviceType(deviceType);
        securityLogService.saveSecurityLog(ConstantsDevice.SECURITY_LOG_TYPE_MODIFY_DEVICE_SYS, SecurityUserHolder.getUserName(),
                deviceType, oldDeviceType, null, Constants.SOURCE_SYS);
        return asseData(num);
    }

    /**
     *
     * 功能描述:检查输入的设备系统名称是否唯一
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:37
     */
    @Override
    public Data checkDeviceTypeNameUnique(String json){
        DeviceSystem deviceType = JSONObject.parseObject(json,DeviceSystem.class);
        int count = deviceSystemDao.checkDeviceTypeNameUnique(deviceType);
        if (count > 0){
            return asseData(Constants.SUCCESS_CODE,"系统名称已存在");
        }else {
            return asseData(Constants.NOT_DATA_CODE,"系统名称不存在");
        }
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
        DeviceSystem deviceType = new DeviceSystem();
        deviceType.setPageSize(null);
        List<DeviceSystem> list = deviceSystemDao.findDeviceTypeList(deviceType);
        CacheUtils.setCacheByList(list, Constants.DEVICE_SYS_CACHE_KEY,  DeviceSystem.class);//初使化缓存
    }
}
