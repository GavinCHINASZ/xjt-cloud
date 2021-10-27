package com.xjt.cloud.iot.core.service.impl.air;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.ExcelUtils;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.dao.iot.air.AirSamplingDeviceDao;
import com.xjt.cloud.iot.core.entity.air.AirSamplingDevice;
import com.xjt.cloud.iot.core.service.service.air.AirSamplingDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName AirSamplingDeviceServiceImpl
 * @Description 空气采样设备管理
 * @Author wangzhiwen
 * @Date 2021/3/30 9:26
 **/
@Service
public class AirSamplingDeviceServiceImpl extends AbstractService implements AirSamplingDeviceService {
    @Autowired
    private AirSamplingDeviceDao airSamplingDeviceDao;

    /**
     * @Description 查询空气采样设备统计报表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findAirSamplingDeviceSummaryReport(String json){
        AirSamplingDevice airSamplingDevice = JSONObject.parseObject(json, AirSamplingDevice.class);
        airSamplingDevice = airSamplingDeviceDao.findAirSamplingDeviceSummaryReport(airSamplingDevice);
        return asseData(airSamplingDevice);
    }

    /**
     * @Description 查询空气采样设备信息列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findAirSamplingDeviceList(String json){
        AirSamplingDevice airSamplingDevice = JSONObject.parseObject(json, AirSamplingDevice.class);
        Integer totalCount = airSamplingDevice.getTotalCount();
        Integer pageSize = airSamplingDevice.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {//判断是否存在总数，如没有，则查询总记录数
            totalCount = airSamplingDeviceDao.findAirSamplingDeviceListTotalCount(airSamplingDevice);
        }
        List<AirSamplingDevice> list = airSamplingDeviceDao.findAirSamplingDeviceList(airSamplingDevice);
        return asseData(totalCount, list);
    }

    /**
     * @Description 修改空气采样设备阈值
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data modifyAirSamplingDevice(String json){
        AirSamplingDevice airSamplingDevice = JSONObject.parseObject(json, AirSamplingDevice.class);
        setEntityUserIdName(SecurityUserHolder.getUserName(), airSamplingDevice.getProjectId(), airSamplingDevice);
        airSamplingDeviceDao.saveAirSamplingDeviceUpdateRecord(airSamplingDevice);
        redisUtils.del("air_sampling_devices");
        return asseData(airSamplingDeviceDao.modifyAirSamplingDevice(airSamplingDevice));
    }

    /**
     * @Description 查询空气采样设备阈值修改列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findAirSamplingDeviceUpdateRecordList(String json){
        AirSamplingDevice airSamplingDevice = JSONObject.parseObject(json, AirSamplingDevice.class);
        Integer totalCount = airSamplingDevice.getTotalCount();
        Integer pageSize = airSamplingDevice.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {//判断是否存在总数，如没有，则查询总记录数
            totalCount = airSamplingDeviceDao.findAirSamplingDeviceUpdateRecordListTotalCount(airSamplingDevice);
        }
        List<AirSamplingDevice> list = airSamplingDeviceDao.findAirSamplingDeviceUpdateRecordList(airSamplingDevice);
        return asseData(totalCount, list);
    }

    /**
     * @Description 下载空气采样设备信息
     *
     * @param response
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 17:38
     * @return void
     */
    @Override
    public void downloadAirSamplingDeviceList(HttpServletResponse response, String json){
        AirSamplingDevice airSamplingDevice = JSONObject.parseObject(json, AirSamplingDevice.class);
        airSamplingDevice.setPageSize(0);
        String[] orderCols = {"deviceCoding"};
        airSamplingDevice.setOrderCols(orderCols);
        List<AirSamplingDevice> list = airSamplingDeviceDao.findAirSamplingDeviceList(airSamplingDevice);
        JSONObject projectJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, airSamplingDevice.getProjectId());
        String fileName = "-空气采样设备管理表";
        String modelFilePath = ConstantsIot.AIR_SAMPLING_DEVICE_LIST;
        String[] keys = {"rowNum", "deviceCoding", "qrNo", "pipelineValue1Desc", "pipelineValue2Desc", "smogValueDesc", "pipelineStatusDesc", "dataUpdateTimeDesc", "pointLocationDesc"};
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", projectJson.getString("projectName") + fileName);

        ExcelUtils.createAndDownloadExcel(response, list, keys, modelFilePath,3, null, jsonObject, "1:0");
    }
}
