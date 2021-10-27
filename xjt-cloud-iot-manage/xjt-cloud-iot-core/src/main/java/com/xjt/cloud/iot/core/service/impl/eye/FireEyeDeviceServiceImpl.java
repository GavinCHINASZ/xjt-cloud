package com.xjt.cloud.iot.core.service.impl.eye;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.dao.iot.eye.FireEyeDeviceDao;
import com.xjt.cloud.iot.core.dao.iot.eye.FireEyeEventDao;
import com.xjt.cloud.iot.core.entity.eye.FireEyeDevice;
import com.xjt.cloud.iot.core.entity.eye.FireEyeEventReport;
import com.xjt.cloud.iot.core.service.service.eye.FireEyeDeviceService;
import com.xjt.cloud.message.manage.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 火眼实现类
 *
 * @author zhangZaifa
 * @date 2020/4/3 15:35
 */
@Service
public class FireEyeDeviceServiceImpl extends AbstractService implements FireEyeDeviceService {
    @Autowired
    private FireEyeDeviceDao fireEyeDeviceDao;
    @Autowired
    private FireEyeEventDao fireEyeEventDao;
    @Autowired
    private MessageService messageService;

    /**
     * 查询火眼设备列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:10
     **/
    @Override
    public Data findFireEyeDeviceList(String json) {
        FireEyeDevice fireEyeDevice = JSONObject.parseObject(json, FireEyeDevice.class);
        fireEyeDevice.setDeleted(false);

        Integer totalCount = fireEyeDevice.getTotalCount();
        Integer pageSize = fireEyeDevice.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            // 判断是否存在总数，如没有，则查询总记录数
            totalCount = fireEyeDeviceDao.findFireEyeDeviceListCount(fireEyeDevice);
        }

        if (null == fireEyeDevice.getOrderCols()){
            String[] orderCols = {"lastModifyTime"};
            fireEyeDevice.setOrderCols(orderCols);
            // 降序
            fireEyeDevice.setOrderDesc(true);
        }

        List<FireEyeDevice> list = fireEyeDeviceDao.findFireEyeDeviceList(fireEyeDevice);
        return asseData(totalCount, list);
    }

    /**
     * 查询火眼设备
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:10
     **/
    @Override
    public Data findFireEyeDevice(String json) {
        FireEyeDevice fireEyeDevice = JSONObject.parseObject(json, FireEyeDevice.class);
        fireEyeDevice.setDeleted(false);

        fireEyeDevice = fireEyeDeviceDao.findFireEyeDevice(fireEyeDevice);
        return asseData(fireEyeDevice);
    }

    /**
     * 查询火眼设备状态
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021-03-22
     **/
    @Override
    public Data findFireEyeDeviceState(String json) {
        FireEyeDevice fireEyeDevice = JSONObject.parseObject(json, FireEyeDevice.class);
        fireEyeDevice.setDeleted(false);

        FireEyeDevice entity = fireEyeDeviceDao.findFireEyeDeviceState(fireEyeDevice);
        return asseData(entity);
    }

    /**
     * 修改火眼设备
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:11
     **/
    @Override
    public Data updFireEyeDevice(String json) {
        FireEyeDevice fireEyeDevice = JSONObject.parseObject(json, FireEyeDevice.class);
        // 用户ID
        Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
        fireEyeDevice.setUpdateUserId(loginUserId);
        fireEyeDevice.setUpdateUserName(SecurityUserHolder.getUserName());
        int status = fireEyeDeviceDao.updFireEyeDevice(fireEyeDevice);
        if (status > 0) {
            return Data.isSuccess();
        } else {
            return Data.isFail();
        }
    }

    /**
     * 删除火眼设备
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:13
     **/
    @Override
    public Data delFireEyeDevice(String json) {
        FireEyeDevice fireEyeDevice = JSONObject.parseObject(json, FireEyeDevice.class);
        // 用户ID
        Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
        fireEyeDevice.setUpdateUserId(loginUserId);
        fireEyeDevice.setUpdateUserName(SecurityUserHolder.getUserName());
        int status = fireEyeDeviceDao.delFireEyeDevice(fireEyeDevice);
        if (status > 0) {
            return Data.isSuccess();
        } else {
            return Data.isFail();
        }
    }

    /**
     * 保存火眼设备
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:13
     **/
    @Override
    public Data saveFireEyeDevice(String json) {
        FireEyeDevice fireEyeDevice = JSONObject.parseObject(json, FireEyeDevice.class);
        // 用户ID
        Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
        fireEyeDevice.setCreateUserId(loginUserId);
        fireEyeDevice.setCreateUserName(SecurityUserHolder.getUserName());
        int status = fireEyeDeviceDao.saveFireEyeDevice(fireEyeDevice);

        if (status > 0) {
            return Data.isSuccess();
        } else {
            return Data.isFail();
        }
    }

    /**
     * 火眼离线任务
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021-03-23
     **/
    @Override
    public Data fireEyeDeviceOffLineTask() {
        try {
            // 从数据词典中得到声光离线时间
            Integer date = Integer.valueOf(DictUtils.getDictItemValueByDictAndItemCode(ConstantsIot.OFF_LINE_TIME, ConstantsIot.OFF_LINE_TIME_IOT_FIRE_EYE_DEVICE));
            // 查询出离线的火眼设备
            List<FireEyeDevice> linkageDeviceList = fireEyeDeviceDao.fireEyeDeviceOffLineTask(date);

            // 修改 火眼设备离线状态
            fireEyeDeviceDao.updateFireEyeDeviceOffLineStatus(date);

            if (CollectionUtils.isNotEmpty(linkageDeviceList)){
                Long projectId;
                StringBuilder sbu;
                String projectName;
                for (FireEyeDevice entity : linkageDeviceList) {
                    entity.setEventType(21);
                    entity.setRecoverStatus(0);
                    entity.setDeviceId(entity.getDeviceId());

                    // 发消息给 项目下 声光管理权限的人
                    List<String> roleSignList = new ArrayList<>(1);
                    // 火眼管理权限
                    roleSignList.add("fireEye_manage_edit");

                    JSONObject object = new JSONObject();
                    // 大屏消息弹杠需要用到buildingId
                    object.put("buildingId", entity.getBuildingId());

                    projectId = entity.getProjectId();
                    // 从缓存中取出项目对象
                    projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
                    sbu = new StringBuilder();
                    sbu.append("【")
                            .append(projectName)
                            .append(entity.getBuildingName())
                            .append("/" )
                            .append(entity.getFloorName()).append(" ")
                            .append(entity.getPointLocation()).append(" ")
                            .append(entity.getDeviceName()).append(" ")
                            .append(entity.getCheckPointQrNo())
                            .append(" 注册码ID:")
                            .append(entity.getSensorNo())
                            .append(" 发生离线事件，请及时处理。");

                    messageService.saveMessageRole(-2, roleSignList, "离线通知",21, 0, sbu.toString(),
                            "url", projectId, null, "data", object);
                }

                // 新增 离线事件
                fireEyeEventDao.saveFireEventByDevice(linkageDeviceList);
            }
        }catch (Exception e){
            // 错误日志要打印
            SysLog.error(e);
            return  Data.isFail();
        }
        return Data.isSuccess();
    }

    /**
     * 火眼设备列表导出表格
     *
     * @param json 参数
     * @param resp HttpServletResponse
     * @author huanggc
     * @date 2021-03-25
     **/
    @Override
    public void downFireEyeDeviceList(String json, HttpServletResponse resp) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        FireEyeDevice fireEyeDevice = JSONObject.parseObject(json, FireEyeDevice.class);
        fireEyeDevice.setDeleted(false);

        if (null == fireEyeDevice.getOrderCols()){
            String[] orderCols = {"lastModifyTime"};
            fireEyeDevice.setOrderCols(orderCols);
            fireEyeDevice.setOrderDesc(true);
        }
        List<FireEyeDevice> list = fireEyeDeviceDao.findFireEyeDeviceList(fireEyeDevice);

        if (CollectionUtils.isEmpty(list)){
            return ;
        }

        Long projectId = fireEyeDevice.getProjectId();

        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        // 表名
        jsonObject.put("title", projectName + "--类脑火眼设备管理表");

        String[] keys = {"rowNum", "videoName", "channelNo", "checkPointQrNo", "deviceStateDesc", "videoLocationDesc"};

        ExcelUtils.createAndDownloadExcel(resp, list, keys, ConstantsIot.DOWN_FIRE_EYE_DEVICE_LIST_PATH, 4, null,
                jsonObject, "1:0");
    }

    /**
     * 查询火眼设备监测状态
     *
     * @param json 参数
     * @author huanggc
     * @date 2021-03-25
     **/
    @Override
    public Data findFireEyeDeviceMonitoringStatus(String json) {
        FireEyeDevice fireEyeDevice = JSONObject.parseObject(json, FireEyeDevice.class);
        FireEyeEventReport fireEyeEventReport = fireEyeDeviceDao.findFireEyeDeviceMonitoringStatus(fireEyeDevice);
        return asseData(fireEyeEventReport);
    }

    /**
     * 查询app首页火眼信息
     *
     * @param json 参数
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return JSONObject
     */
    @Override
    public JSONObject findUserProjectFireEyeData(String json){
        FireEyeDevice fireEyeDevice = JSONObject.parseObject(json, FireEyeDevice.class);
        FireEyeEventReport fireEyeEventReport = fireEyeDeviceDao.findFireEyeDeviceMonitoringStatus(fireEyeDevice);
        JSONObject jsonObject = new JSONObject(8);
        jsonObject.put("modelIndex", 31);
        if (fireEyeEventReport != null) {
            jsonObject.put("smokeCount", fireEyeEventReport.getSmokeCount());
            jsonObject.put("smokeWarningCount", fireEyeEventReport.getSmokeWarningCount());
            jsonObject.put("flameCount", fireEyeEventReport.getFlameCount());
            jsonObject.put("flameWarningCount", fireEyeEventReport.getFlameWarningCount());
            jsonObject.put("occlusionCount", fireEyeEventReport.getOcclusionCount());
            jsonObject.put("faultEventCount", fireEyeEventReport.getFaultEventCount());
        }else{
            Integer total = fireEyeDeviceDao.findFireEyeDeviceListCount(fireEyeDevice);
            if(total == null || total == 0){
                jsonObject.put("deviceCount", 0);
            }
            jsonObject.put("smokeCount", 0);
            jsonObject.put("smokeWarningCount", 0);
            jsonObject.put("flameCount", 0);
            jsonObject.put("flameWarningCount", 0);
            jsonObject.put("occlusionCount", 0);
            jsonObject.put("faultEventCount", 0);
        }
        return jsonObject;
    }
}
