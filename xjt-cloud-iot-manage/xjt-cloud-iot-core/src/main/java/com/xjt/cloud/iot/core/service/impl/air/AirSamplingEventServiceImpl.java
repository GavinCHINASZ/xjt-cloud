package com.xjt.cloud.iot.core.service.impl.air;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.dao.iot.air.AirSamplingEventDao;
import com.xjt.cloud.iot.core.entity.air.AirSamplingEvent;
import com.xjt.cloud.iot.core.entity.air.AirSamplingEventReport;
import com.xjt.cloud.iot.core.service.service.air.AirSamplingEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @ClassName AirSamplingEventServiceImpl
 * @Description 空气采样设备事件管理
 * @Author wangzhiwen
 * @Date 2021/3/31 9:09
 **/
@Service
public class AirSamplingEventServiceImpl extends AbstractService implements AirSamplingEventService {
    @Autowired
    private AirSamplingEventDao airSamplingEventDao;

    /**
     * @Description 查询空气采样设备事件汇总曲线图
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 14:40
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findAirSamplingEventGraph(String json){
        AirSamplingEvent airSamplingEvent = JSONObject.parseObject(json, AirSamplingEvent.class);
        if (airSamplingEvent.getCreateTime() == null) {
            Date date = DateUtils.getDate();
            airSamplingEvent.setCreateTime(date);
            airSamplingEvent.setEndTime(date);
        }
        airSamplingEvent.setGroupType(DateUtils.getBetweenDateTimeType(airSamplingEvent.getCreateTime(), airSamplingEvent.getEndTime()));

        List<AirSamplingEventReport> list = airSamplingEventDao.findAirSamplingEventGraph(airSamplingEvent);
        return asseData(list);
    }

    /**
     * @Description 查询空气采样设备事件汇总饼图
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 14:40
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findAirSamplingEventPie(String json){
        AirSamplingEvent airSamplingEvent = JSONObject.parseObject(json, AirSamplingEvent.class);
        String[] orderCols = {"createTime"};
        if (airSamplingEvent.getOrderCols() == null) {
            airSamplingEvent.setOrderCols(orderCols);
        }
        AirSamplingEventReport airSamplingEventReport = airSamplingEventDao.findAirSamplingEventPie(airSamplingEvent);
        return asseData(airSamplingEventReport);
    }

    /**
     * @Description 查询空气采样设备事件列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 14:40
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findAirSamplingEventList(String json){
        AirSamplingEvent airSamplingEvent = JSONObject.parseObject(json, AirSamplingEvent.class);
        String[] orderCols = {"createTime"};
        if (airSamplingEvent.getOrderCols() == null) {
            airSamplingEvent.setOrderCols(orderCols);
        }
        Integer totalCount = airSamplingEvent.getTotalCount();
        Integer pageSize = airSamplingEvent.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {//判断是否存在总数，如没有，则查询总记录数
            totalCount = airSamplingEventDao.findAirSamplingEventListTotalCount(airSamplingEvent);
        }
        List<AirSamplingEvent> list = airSamplingEventDao.findAirSamplingEventList(airSamplingEvent);
        return asseData(totalCount, list);
    }

    /**
     * @Description 下载空气采样设备事件信息列表
     *
     * @param response
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 17:38
     * @return void
     */
    @Override
    public void downloadAirSamplingEventList(HttpServletResponse response, String json){
        AirSamplingEvent airSamplingEvent = JSONObject.parseObject(json, AirSamplingEvent.class);
        JSONObject projectJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, airSamplingEvent.getProjectId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", projectJson.getString("projectName") + "）- 空气采事件记录列表 " +
                (airSamplingEvent.getCreateTime() != null && airSamplingEvent.getEndTime() != null ?
                        DateUtils.formatDate(airSamplingEvent.getCreateTime()) + " - " + DateUtils.formatDate(airSamplingEvent.getEndTime()):""));

        airSamplingEvent.setPageSize(0);
        if (airSamplingEvent.getOrderCols() == null){
            String[] orderCols = {"deviceCoding","createTime"};
            airSamplingEvent.setOrderCols(orderCols);
            airSamplingEvent.setOrderDesc(true);
        }
        List<AirSamplingEvent> list = airSamplingEventDao.findAirSamplingEventList(airSamplingEvent);

        String[] keys = {"rowNum", "deviceCoding", "qrNo", "pipelineValue1Desc", "pipelineValue2Desc", "smogValueDesc","pipelineStatusDesc", "createTimeDesc", "pointLocationDesc"};

        String[] files = airSamplingEvent.getFiles();
        if (files != null){
            MultipartFile[] fs = new MultipartFile[files.length];
            for (int i = 0 ;i < files.length;i++){
                fs[i] = Base64DecodeMultipartFile.base64Convert(files[i]);
            }
            int[] pictRowIndexs = {3,5};
            int[] pictColIndexs = {0,0};
            ExcelUtils.createAndDownloadImgAndListExcelSingleSheetNotStyle(response, list, keys, ConstantsIot.AIR_SAMPLING_EVENT_LIST,
                    7, null, jsonObject, "1:0", fs, pictRowIndexs,pictColIndexs);
        }
    }
}
