package com.xjt.cloud.iot.core.service.impl.air;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.commons.utils.other.MetroUtils;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.dao.iot.air.AirSamplingDeviceDao;
import com.xjt.cloud.iot.core.dao.iot.air.AirSamplingEventDao;
import com.xjt.cloud.iot.core.dao.iot.air.AirSamplingRecordDao;
import com.xjt.cloud.iot.core.entity.air.AirSamplingDevice;
import com.xjt.cloud.iot.core.entity.air.AirSamplingEvent;
import com.xjt.cloud.iot.core.entity.air.AirSamplingRecord;
import com.xjt.cloud.iot.core.service.service.air.AirSamplingRecordService;
import com.xjt.cloud.message.manage.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @ClassName AirSamplingRecordServiceImpl
 * @Description 空气采样设备记录管理
 * @Author wangzhiwen
 * @Date 2021/3/31 9:10
 **/
@Service
public class AirSamplingRecordServiceImpl extends AbstractService implements AirSamplingRecordService {
    @Autowired
    private AirSamplingRecordDao airSamplingRecordDao;
    @Autowired
    private AirSamplingDeviceDao airSamplingDeviceDao;
    @Autowired
    private AirSamplingEventDao airSamplingEventDao;
    @Autowired
    private MessageService messageService;

    /**
     * @Description 查询空气采样记录曲线图
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 10:13
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findAirSamplingRecordGraph(String json){
        AirSamplingRecord airSamplingRecord = JSONObject.parseObject(json, AirSamplingRecord.class);
        Integer dateType = airSamplingRecord.getDateType();
        String dateIndex = airSamplingRecord.getDateIndex();
        Date date = DateUtils.getDate();
        Date beginDate = date;
        Date endDate = date;
        if (dateType != null) {//周　　月　年处理
            if (dateType == 1) {//周
                beginDate = DateUtils.reduce24Hours(DateUtils.getDate(), 1 - DateUtils.getWeek(DateUtils.getDate()));
            } else if (dateType == 2) {//月
                beginDate = DateUtils.strToY_M(dateIndex);
                endDate = DateUtils.strToY_M_D(dateIndex + "-" + DateUtils.getMonthMaxmumDay(beginDate));
            } else if (dateType == 3) {//年
                beginDate = DateUtils.strToY_M(dateIndex + "-01");
                endDate = DateUtils.strToY_M_D(dateIndex + "-12-30");
            }
            airSamplingRecord.setCreateTime(beginDate);
            airSamplingRecord.setEndTime(endDate);
        }
        if (airSamplingRecord.getCreateTime() == null) {
            airSamplingRecord.setCreateTime(beginDate);
            airSamplingRecord.setEndTime(endDate);
        }

        List<AirSamplingRecord> list;
        if (airSamplingRecord.getEndTimeDesc().getTime() - airSamplingRecord.getCreateTime().getTime() > 24 * 3600 * 1000) {
            airSamplingRecord.setGroupType(DateUtils.getBetweenDateTimeType(airSamplingRecord.getCreateTime(), airSamplingRecord.getEndTime()));
            String[] orderCols = {"timeDesc"};
            if (airSamplingRecord.getOrderCols() == null) {
                airSamplingRecord.setOrderCols(orderCols);
            }
            list = airSamplingRecordDao.findAirSamplingRecordGraph(airSamplingRecord);
        } else {
            String[] orderCols = {"createTime"};
            if (airSamplingRecord.getOrderCols() == null) {
                airSamplingRecord.setOrderCols(orderCols);
            }
            list = airSamplingRecordDao.findAirSamplingRecordList(airSamplingRecord);
        }
        return asseData(list);
    }

    /**
     * @Description 查询空气采样记录列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 10:13
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findAirSamplingRecordList(String json){
        AirSamplingRecord airSamplingRecord = JSONObject.parseObject(json, AirSamplingRecord.class);

        if (airSamplingRecord.getOrderCols() == null) {
            String[] orderCols = {"createTime"};
            airSamplingRecord.setOrderCols(orderCols);
        }
        Integer totalCount = airSamplingRecord.getTotalCount();
        Integer pageSize = airSamplingRecord.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {//判断是否存在总数，如没有，则查询总记录数
            totalCount = airSamplingRecordDao.findAirSamplingRecordListTotalCount(airSamplingRecord);
        }
        List<AirSamplingRecord> list = airSamplingRecordDao.findAirSamplingRecordList(airSamplingRecord);
        return asseData(totalCount, list);
    }

    /**
     * @Description 下载空气采样单个设备记录信息列表
     *
     * @param response
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 17:38
     * @return void
     */
    @Override
    public void downloadAirSamplingDeviceRecordList(HttpServletResponse response, String json){
        AirSamplingRecord airSamplingRecord = JSONObject.parseObject(json, AirSamplingRecord.class);
        JSONObject projectJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, airSamplingRecord.getProjectId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", projectJson.getString("projectName") + "（" + airSamplingRecord.getDeviceCoding() + "）- 设备采样记录表 " +
                (airSamplingRecord.getCreateTime() != null && airSamplingRecord.getEndTime() != null ?
                        DateUtils.formatDate(airSamplingRecord.getCreateTime()) + " - " + DateUtils.formatDate(airSamplingRecord.getEndTime()) : ""));

        airSamplingRecord.setPageSize(0);
        if (airSamplingRecord.getOrderCols() == null){
            String[] orderCols = {"deviceCoding","createTime"};
            airSamplingRecord.setOrderCols(orderCols);
            airSamplingRecord.setOrderDesc(true);
        }
        List<AirSamplingRecord> list = airSamplingRecordDao.findAirSamplingRecordList(airSamplingRecord);

        String[] keys = {"rowNum", "deviceCoding", "qrNo", "pipelineValue1Desc", "pipelineValue2Desc", "smogValueDesc","pipelineStatusDesc", "createTimeDesc", "pointLocationDesc"};

        String[] files = airSamplingRecord.getFiles();
        if (files != null){
            MultipartFile[] fs = new MultipartFile[files.length];
            for (int i = 0 ;i < files.length;i++){
                fs[i] = Base64DecodeMultipartFile.base64Convert(files[i]);
            }
            int[] pictRowIndexs = {2,2};
            int[] pictColIndexs = {0,5};
            ExcelUtils.createAndDownloadImgAndListExcelSingleSheetNotStyle(response, list, keys, ConstantsIot.AIR_SAMPLING_DEVICE_RECORD_LIST,
                    5, null, jsonObject, "1:0", fs, pictRowIndexs,pictColIndexs);
        }
    }

    /**
     * @Description 下载空气采样多个设备记录信息列表
     *
     * @param response
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 17:38
     * @return void
     */
    @Override
    public void downloadAirSamplingDevicesRecordList(HttpServletResponse response, String json){
        AirSamplingRecord airSamplingRecord = JSONObject.parseObject(json, AirSamplingRecord.class);
        if (airSamplingRecord.getOrderCols() == null){
            String[] orderCols = {"deviceCoding","createTime"};
            airSamplingRecord.setOrderCols(orderCols);
            airSamplingRecord.setOrderDesc(true);
        }
        airSamplingRecord.setPageSize(0);
        List<AirSamplingRecord> list = airSamplingRecordDao.findAirSamplingRecordList(airSamplingRecord);
        JSONObject projectJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, airSamplingRecord.getProjectId());
        String fileName = "-空气采样多设备记录表";
        String modelFilePath = ConstantsIot.AIR_SAMPLING_DEVICES_RECORD_LIST;
        String[] keys = {"rowNum", "deviceCoding", "qrNo", "pipelineValue1Desc", "pipelineValue2Desc", "smogValueDesc","pipelineStatusDesc", "createTimeDesc", "pointLocationDesc"};

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", projectJson.getString("projectName") + fileName + " " +
                (airSamplingRecord.getCreateTime() != null && airSamplingRecord.getEndTime() != null ?
                        DateUtils.formatDate(airSamplingRecord.getCreateTime()) + " - " + DateUtils.formatDate(airSamplingRecord.getEndTime()):""));

        ExcelUtils.createAndDownloadExcel(response, list, keys, modelFilePath,3, null, jsonObject, "1:0");
    }

    /**
     * @Description 空气采样数据获取接口
     *
     * @author wangzhiwen
     * @date 2021/4/2 11:37
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data airSamplingRecordGetDataTask(){
        String key = "air_sampling_devices";
        String jsonArrStr = redisUtils.getString(key);
        List<AirSamplingDevice> list;
        if (StringUtils.isNotEmpty(jsonArrStr)){//判断缓存中是否存在设备信息
            list = JSONArray.parseArray(jsonArrStr,AirSamplingDevice.class);
        }else{
            AirSamplingDevice airSamplingDevice = new AirSamplingDevice();
            airSamplingDevice.setPageSize(0);
            list = airSamplingDeviceDao.findAirSamplingDeviceList(airSamplingDevice);
        }
        if (CollectionUtils.isNotEmpty(list)) {
            JSONArray deviceCodingArr = new JSONArray(list.size());
            for (AirSamplingDevice asd : list){
                deviceCodingArr.add(asd.getDeviceCoding());
            }

            String res = MetroUtils.heLiShiIntegratedMonitoring(deviceCodingArr.toJSONString());//调用接口，获取当前数据
            //String res = "{  \"retcode\": 1,  \"retmsg\": \"操作成功\",  \"data\": [    {      \"pointData\": {        \"2704:ASD_0005.AI02\": \"88.2\",        \"2704:ASD_0005.AI01\": \"101.9\",        \"2704:ASD_0005.AIYW\": \"0.01\"      },      \"deviceCode\": \"ASD_11\"    },    {      \"pointData\": {        \"2704:ASD_0006.AI01\": \"86.1\",        \"2704:ASD_0006.AIYW\": \"0.14\",        \"2704:ASD_0006.AI02\": \"101.6\"      },      \"deviceCode\": \"ASD_12\"    },    {      \"pointData\": {        \"2704:ASD_0007.AIYW\": \"0.05\",        \"2704:ASD_0007.AI02\": \"80.7\",        \"2704:ASD_0007.AI01\": \"86.9\"      },      \"deviceCode\": \"ASD_13\"    },    {      \"pointData\": {        \"2704:ASD_0008.AI01\": \"89.4\",        \"2704:ASD_0008.AI02\": \"88.7\",        \"2704:ASD_0008.AIYW\": \"0.1\"      },      \"deviceCode\": \"ASD_14\"    },    {      \"pointData\": {        \"2704:ASD_0009.AIYW\": \"0.12\",        \"2704:ASD_0009.AI01\": \"103.8\",        \"2704:ASD_0009.AI02\": \"101.6\"      },      \"deviceCode\": \"ASD_15\"    },    {      \"pointData\": {        \"2704:ASD_0010.AIYW\": \"0.05\",        \"2704:ASD_0010.AI02\": \"90.7\",        \"2704:ASD_0010.AI01\": \"98.9\"      },      \"deviceCode\": \"ASD_16\"    },    {      \"pointData\": {        \"2704:ASD_0011.AI01\": \"87.3\",        \"2704:ASD_0011.AI02\": \"95.1\",        \"2704:ASD_0011.AIYW\": \"0.0\"      },      \"deviceCode\": \"ASD_17\"    },    {      \"pointData\": {        \"2704:ASD_0012.AI02\": \"97.6\",        \"2704:ASD_0012.AI01\": \"99.8\",        \"2704:ASD_0012.AIYW\": \"0.08\"      },      \"deviceCode\": \"ASD_18\"    }  ]}";
            SysLog.info("和利时接口调用返回:" + res);
            if (StringUtils.isNotEmpty(res)){
                JSONObject jsonObject = JSONObject.parseObject(res);
                if(1 == jsonObject.getInteger("retcode")){//判断接口是否返回成功
                    JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("data"));
                    String deviceCoding;
                    String deviceCode = "deviceCode";
                    String pointData = "pointData";
                    for (int i = 0;i < jsonArray.size();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        deviceCoding = jsonObject.getString(deviceCode).trim();
                        for (AirSamplingDevice asd:list){
                            if (deviceCoding.equals(asd.getDeviceCoding())){
                                airSamplingRecordResolve(asd, jsonObject.getJSONObject(pointData));
                                break;
                            }
                        }
                    }
                }
            }
            redisUtils.set(key, JSONArray.toJSONString(list), ConstantsIot.AIR_SAMPLING_EXPIRES_TIME);
        }
        return Data.isSuccess();
    }


    /**
     * @Description 处理采样接品返回数据
     *
     * @param oldAsd 上一次设备信息
     * @param pointDataJson 最新设备信息
     * @author wangzhiwen
     * @date 2021/4/2 14:03
     * @return void
     */
    private void airSamplingRecordResolve(AirSamplingDevice oldAsd, JSONObject pointDataJson){
        String pointKey;
        String pipeline1Key = "AI01";//汽流值1key
        String pipeline2Key = "AI02";//汽流值1key
        String smogKey = "AIYW";//烟雾key
        String oldAsdJson = JSON.toJSONString(oldAsd);
        AirSamplingRecord airSamplingRecord = JSONObject.parseObject(oldAsdJson,AirSamplingRecord.class);
        airSamplingRecord.setAirSamplingId(oldAsd.getId());
        airSamplingRecord.setId(null);
        AirSamplingEvent airSamplingEvent = JSONObject.parseObject(oldAsdJson,AirSamplingEvent.class);
        airSamplingEvent.setAirSamplingId(oldAsd.getId());
        airSamplingEvent.setId(null);

        int value;
        int deviationValue;
        int eventType1 = 1;
        int eventType2 = 1;
        String msg1 = null;
        String msg2 = null;
        String deviationMsg1 = null;
        String deviationMsg2 = null;
        for (Map.Entry<String, Object> entry : pointDataJson.entrySet()) {
            pointKey = entry.getKey();
            BigDecimal value1 = new BigDecimal(entry.getValue().toString());
            BigDecimal value2 = new BigDecimal("100.00");
            value = (int)(value1.multiply(value2).doubleValue());
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            if (pointKey.indexOf(pipeline1Key) != -1){//管道1数据
                eventType1 = isPipelineStatusFail(value,oldAsd.getPipelineMaxValue(),oldAsd.getPipelineMinValue());
                if (eventType1 >= 2){//判断是否超出阈值
                    //if (eventType1 != oldAsd.getPipelineStatus1()){
                        msg1 = "管道1 的气流值为 " + df.format((float)value/100) + "%/m," + (eventType1 == 2 ? "高于" : "低于")  + "正常范围，请尽快处理。";
                    //}
                    airSamplingEvent.setPipelineStatus1(eventType1);
                }
                oldAsd.setPipelineStatus1(eventType1);
                airSamplingRecord.setPipelineStatus1(eventType1);
                deviationValue = Math.abs(value - oldAsd.getPipelineValue1());
                if (deviationValue > oldAsd.getDeviationSet()){//判断是否超出偏差值
                    oldAsd.setDeviationStatus(2);
                    airSamplingRecord.setDeviationStatus(2);
                    deviationMsg1 = "管道1 的气流值为 " + df.format((float)value/100) + "%/m,偏差" + df.format((float)deviationValue/100)  + "%/m，请尽快处理。";
                }else {
                    oldAsd.setDeviationStatus(1);
                    airSamplingRecord.setDeviationStatus(1);
                }

                oldAsd.setPipelineValue1(value);
                oldAsd.setDeviationValue1(deviationValue);

                airSamplingRecord.setPipelineValue1(value);
                airSamplingRecord.setDeviationValue1(deviationValue);

                airSamplingEvent.setPipelineValue1(value);
            }else if (pointKey.indexOf(pipeline2Key) != -1){//管道2数据
                eventType2 = isPipelineStatusFail(value,oldAsd.getPipelineMaxValue(),oldAsd.getPipelineMinValue());
                if (eventType2 >= 2){//判断是否超出阈值
                    //if (eventType2 != oldAsd.getPipelineStatus2()){
                        msg2 = "管道2 的气流值为 " + df.format((float)value/100) + "%/m," + (eventType2 == 2 ? "高于" : "低于")  + "正常范围，请尽快处理。";
                    //}
                    airSamplingEvent.setPipelineStatus2(eventType2);
                }
                oldAsd.setPipelineStatus2(eventType2);
                airSamplingRecord.setPipelineStatus2(eventType2);
                deviationValue = Math.abs(value - oldAsd.getPipelineValue2());
                if (deviationValue > oldAsd.getDeviationSet()){//判断是否超出偏差值
                    oldAsd.setDeviationStatus(2);
                    airSamplingRecord.setDeviationStatus(2);
                    deviationMsg2 = "管道2 的气流值为 " + df.format((float)value/100) + "%/m,偏差" + df.format((float)deviationValue/100)  + "%/m，请尽快处理。";
                }else {
                    oldAsd.setDeviationStatus(1);
                    airSamplingRecord.setDeviationStatus(1);
                }

                oldAsd.setPipelineValue2(value);
                oldAsd.setDeviationValue2(deviationValue);

                airSamplingRecord.setPipelineValue2(value);
                airSamplingRecord.setDeviationValue2(deviationValue);

                airSamplingEvent.setPipelineValue2(value);
            }else if (pointKey.indexOf(smogKey) != -1){//烟雾数据
                oldAsd.setSmogValue(value);
                airSamplingRecord.setSmogValue(value);
                airSamplingEvent.setSmogValue(value);
            }
        }
        airSamplingDeviceDao.modifyAirSamplingDeviceData(oldAsd);
        airSamplingRecordDao.saveAirSamplingRecord(airSamplingRecord);
        airSamplingEvent.setRecordId(airSamplingRecord.getId());
        if (StringUtils.isNotEmpty(msg1)){
            airSamplingEvent.setEventType(eventType1 - 1);
            airSamplingEventDao.saveAirSamplingEvent(airSamplingEvent);
            sendMsg(oldAsd,airSamplingRecord,msg1);
        }
        if (StringUtils.isNotEmpty(msg2)){
            airSamplingEvent.setEventType(eventType2 + 1);
            airSamplingEventDao.saveAirSamplingEvent(airSamplingEvent);
            sendMsg(oldAsd,airSamplingRecord,msg1);
        }

        if (StringUtils.isNotEmpty(deviationMsg1)){
            sendMsg(oldAsd,airSamplingRecord,deviationMsg1);
        }
        if (StringUtils.isNotEmpty(deviationMsg2)){
            sendMsg(oldAsd,airSamplingRecord,deviationMsg2);
        }
    }

    /**
     * @Description 判断记录是否异常
     *
     * @param value
     * @param maxValue
     * @param minValue
     * @author wangzhiwen
     * @date 2021/4/2 14:29
     * @return boolean
     */
    private int isPipelineStatusFail(int value, int maxValue,int minValue){
        if (value > maxValue){
            return 2;
        }else if(value < minValue){
            return 3;
        }
        return 1;
    }

    /**
     * @Description 发送消息
     *
     * @param oldAsd
     * @param airSamplingRecord
     * @author wangzhiwen
     * @date 2021/4/2 16:10
     * @return void
     */
    private void sendMsg(AirSamplingDevice oldAsd, AirSamplingRecord airSamplingRecord,String msg){
        String buildingName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, oldAsd.getBuildingId(), "buildingName");
        String floorName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, oldAsd.getBuildingFloorId(), "floorName");
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, oldAsd.getProjectId(), "projectName");
        //组装要发送的信息内容
        String content = "【" + projectName + "】 " + oldAsd.getPointLocationDesc() +
                oldAsd.getDeviceCoding() + "," + msg;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("projectName",projectName);
        jsonObject.put("buildingName",buildingName);
        jsonObject.put("floorName",floorName);
        jsonObject.put("deviceLocation",oldAsd.getPointLocation());
        jsonObject.put("deviceName",oldAsd.getDeviceCoding());
        jsonObject.put("qrNo",oldAsd.getDeviceQrNo());
        jsonObject.put("pointQrNo",oldAsd.getQrNo());
        jsonObject.put("event",msg);
        jsonObject.put("buildingId",oldAsd.getBuildingId());
        jsonObject.put("sensorNo",oldAsd.getDeviceCoding());
        jsonObject.put("date",DateUtils.getDateTimeString(airSamplingRecord.getCreateTime()));
        //发送提醒信息
        messageService.saveMessageRole(17, Arrays.asList("air_sampling_msg".split(",")), "空气采样通知",1,3, content,
                "" , oldAsd.getProjectId(), airSamplingRecord.getId(), null, jsonObject);
    }
}
