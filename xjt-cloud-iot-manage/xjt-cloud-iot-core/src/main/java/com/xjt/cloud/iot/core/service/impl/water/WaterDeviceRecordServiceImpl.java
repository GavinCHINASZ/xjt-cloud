package com.xjt.cloud.iot.core.service.impl.water;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.dao.iot.water.WaterDeviceRecordDao;
import com.xjt.cloud.iot.core.entity.device.Device;
import com.xjt.cloud.iot.core.entity.water.WaterDeviceReport;
import com.xjt.cloud.iot.core.entity.water.WaterRecord;
import com.xjt.cloud.iot.core.service.service.water.WaterDeviceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 水设备管理接口实现类
 *
 * @author wangzhiwen
 * @date 2019/9/26 15:35
 */
@Service
public class WaterDeviceRecordServiceImpl extends AbstractService implements WaterDeviceRecordService {
    @Autowired
    private WaterDeviceRecordDao waterDeviceRecordDao;

    /**
     * 功能描述:查询水设备事件记录列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/26 15:44
     */
    @Override
    public Data findWaterDeviceEventList(String json) {
        WaterRecord waterRecord = JSONObject.parseObject(json, WaterRecord.class);
        Integer totalCount = waterRecord.getTotalCount();
        Integer pageSize = waterRecord.getPageSize();
        if (waterRecord.getCreateTime() == null && waterRecord.getId() == null && waterRecord.getRecordId() == null) {
            Date date = DateUtils.getDate();
            waterRecord.setCreateTime(date);
            waterRecord.setEndTime(date);
        }
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            //判断是否存在总数，如没有，则查询总记录数
            totalCount = waterDeviceRecordDao.findWaterDeviceEventListTotalCount(waterRecord);
        }
        List<WaterRecord> list = waterDeviceRecordDao.findWaterDeviceEventList(waterRecord);
        return asseData(totalCount, list);
    }

    /**
     * 功能描述:查询水压设备事件汇总饼图
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/26 14:59
     */
    @Override
    public Data findWaterDeviceEventSummaryReport(String json) {
        WaterRecord waterRecord = JSONObject.parseObject(json, WaterRecord.class);
        if (waterRecord.getCreateTime() == null) {
            Date date = DateUtils.getDate();
            waterRecord.setCreateTime(date);
            waterRecord.setEndTime(date);
        }
        WaterDeviceReport waterDeviceReport = waterDeviceRecordDao.findWaterDeviceEventSummaryReport(waterRecord);
        return asseData(waterDeviceReport);
    }

    /**
     * 功能描述:查询水设备记录列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/26 15:44
     */
    @Override
    public Data findWaterDeviceRecordList(String json) {
        WaterRecord waterRecord = JSONObject.parseObject(json, WaterRecord.class);
        String[] orderCols = {"createTime"};
        if (waterRecord.getOrderCols() == null) {
            waterRecord.setOrderCols(orderCols);
        }
        Integer totalCount = waterRecord.getTotalCount();
        Integer pageSize = waterRecord.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {//判断是否存在总数，如没有，则查询总记录数
            totalCount = waterDeviceRecordDao.findWaterDeviceRecordListTotalCount(waterRecord);
        }
        List<WaterRecord> list = waterDeviceRecordDao.findWaterDeviceRecordList(waterRecord);
        return asseData(totalCount, list);
    }

    /**
     * 功能描述:查询水设备记录曲线图
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/26 15:44
     */
    @Override
    public Data findWaterDeviceRecordCurveChart(String json) {
        WaterRecord waterRecord = JSONObject.parseObject(json, WaterRecord.class);
        Integer dateType = waterRecord.getDateType();
        String dateIndex = waterRecord.getDateIndex();
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
            waterRecord.setCreateTime(beginDate);
            waterRecord.setEndTime(endDate);
        }
        if (waterRecord.getCreateTime() == null) {
            waterRecord.setCreateTime(beginDate);
            waterRecord.setEndTime(endDate);
        }

        List<WaterRecord> list;
        if (waterRecord.getEndTime().getTime() - waterRecord.getCreateTime().getTime() > 24 * 3600 * 1000) {
            waterRecord.setGroupType(DateUtils.getBetweenDateTimeType(waterRecord.getCreateTime(), waterRecord.getEndTime()));
            String[] orderCols = {"deviceType", "timeDesc"};
            if (waterRecord.getOrderCols() == null) {
                waterRecord.setOrderCols(orderCols);
            }
            list = waterDeviceRecordDao.findWaterDeviceRecordCurveChart(waterRecord);
        } else {
            String[] orderCols = {"deviceType", "createTime"};
            if (waterRecord.getOrderCols() == null) {
                waterRecord.setOrderCols(orderCols);
            }
            list = waterDeviceRecordDao.findWaterDeviceRecordList(waterRecord);
            // 计算无数据时间,添加数据
            WaterRecord temTR = null;
            WaterRecord beforeTR = null;
            waterRecord.setFindLimitEndData(true);
            waterRecord.setOrderDesc(true);
            if (CollectionUtils.isEmpty(list)) {
                list = waterDeviceRecordDao.findWaterDeviceRecordList(waterRecord);
                if (CollectionUtils.isNotEmpty(list)) {
                    temTR = list.get(list.size() - 1);
                }
                list = new ArrayList<>();
                beginDate = waterRecord.getCreateTime();
            } else {
                temTR = list.get(list.size() - 1);
                // 查询日期之前的最后一条数据
                List<WaterRecord> tmpList = waterDeviceRecordDao.findWaterDeviceRecordList(waterRecord);
                if (CollectionUtils.isNotEmpty(tmpList)) {
                    beforeTR = tmpList.get(tmpList.size() - 1);
                }
                beginDate = temTR.getCreateTime();
            }
            if (null != beforeTR) {
                //添加第一条数据之前的数据
                list = addWaterDeviceRecordCurveChartData(list, beforeTR, waterRecord.getCreateTime().getTime(), list.get(0).getCreateTime(), true);
            }

            if (null != temTR) {
                //添加最后一条数据之后的数据
                date = new Date();
                list = addWaterDeviceRecordCurveChartData(list, temTR, beginDate.getTime(),
                        waterRecord.getEndTimeDesc().getTime() > date.getTime() ? date : waterRecord.getEndTimeDesc(), false);
            }
        }
        //List<String> timeList = waterDeviceRecordDao.findTimeList(waterRecord);
        //Data data = asseData(list);
        //data.setObjects(timeList);
        return asseData(list);
    }

    /**
     * 功能描述:为水压设备单日曲线图,前后添加数据
     *
     * @param list      数据列表
     * @param temTR     要添加的信息对象
     * @param beginTime 添加数据时间的开始时间
     * @param endDate   添加数据时间的截止时间
     * @param isBefore  是否是向前添加
     * @return java.util.List<com.xjt.cloud.iot.core.entity.water.WaterRecord>
     * @author wangzhiwen
     * @date 2020/8/5 14:44
     */
    private List<WaterRecord> addWaterDeviceRecordCurveChartData(List<WaterRecord> list, WaterRecord temTR, Long beginTime, Date endDate, boolean isBefore) {
        Integer maxValue = temTR.getMaxValue();
        Integer minValue = temTR.getMinValue();
        Integer presentValue = temTR.getPresentValue();
        String unit = temTR.getUnit();
        int index = 0;
        for (; ; ) {
            beginTime = beginTime + 7200 * 1000;
            if (beginTime >= endDate.getTime()) {
                break;
            }
            temTR = new WaterRecord();
            temTR.setMaxValue(maxValue);
            temTR.setMinValue(minValue);
            temTR.setPresentValue(presentValue);
            temTR.setUnit(unit);
            temTR.setTimeDesc(DateUtils.formatDate("HH:mm", new Date(beginTime)));
            if (isBefore) {
                list.add(index, temTR);
                index++;
            } else {
                list.add(temTR);
            }
        }
        return list;
    }

    /**
     * 功能描述:　查询水设备事件记录按时间汇总（曲线图）
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/26 15:44
     */
    @Override
    public Data findWaterDeviceEventReportCount(String json) {
        WaterRecord waterRecord = JSONObject.parseObject(json, WaterRecord.class);
        if (waterRecord.getCreateTime() == null) {
            Date date = DateUtils.getDate();
            waterRecord.setCreateTime(date);
            waterRecord.setEndTime(date);
        }
        waterRecord.setGroupType(DateUtils.getBetweenDateTimeType(waterRecord.getCreateTime(), waterRecord.getEndTime()));

        List<WaterDeviceReport> list = waterDeviceRecordDao.findWaterDeviceEventReportCount(waterRecord);
        return asseData(list);
    }

    /**
     * 功能描述:　查询水设备事件记录汇总，饼图
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/26 15:44
     */
    @Override
    public Data findWaterDeviceEventReportTotal(String json) {
        WaterRecord waterRecord = JSONObject.parseObject(json, WaterRecord.class);
        List<WaterRecord> list = waterDeviceRecordDao.findWaterDeviceEventReportTotal(waterRecord);
        return asseData(list);
    }

    /**
     * 功能描述:水压设备设备事件下载（单个设备）
     *
     * @param response HttpServletResponse
     * @param json 参数
     * @author wangzhiwen
     * @date 2019/10/8 9:51
     */
    @Override
    public void downloadWaterDeviceDeviceEvent(HttpServletResponse response, String json) {
        WaterRecord waterRecord = JSONObject.parseObject(json, WaterRecord.class);
        // 得到设备名称与二维码
        Device device = getObjByUrl(ConstantsIot.FIND_DEVICE_MODULE_CALLS, "{\"id\":" + waterRecord.getDeviceId() + "}", Device.class);
        String deviceName = "";
        String deviceQrNo = "";
        if (device != null) {
            deviceName = device.getDeviceName();
            deviceQrNo = device.getQrNo();
        }

        JSONObject projectJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, waterRecord.getProjectId());
        waterRecord.setTitle(projectJson.getString("projectName") + " - " + deviceName + "/" + deviceQrNo + "告警事件导出表");
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(waterRecord);
        waterRecord.setPageSize(0);
        if (waterRecord.getOrderCols() == null) {
            String[] orderCols = {"createTime"};
            waterRecord.setOrderCols(orderCols);
            waterRecord.setOrderDesc(true);
        }
        List<WaterRecord> list = waterDeviceRecordDao.findWaterDeviceEventList(waterRecord);
        String[] keys = {"rowNum", "createTimeDesc", "sensorNo", "presentValueDesc", "eventTypeDesc", "recoverTimeDesc", "recoverStatusDesc", "pointLocationDesc"};
        ExcelUtils.createAndDownloadExcel(response, list, keys, ConstantsIot.REPORT_WATER_DEVICE_DEVICE_EVENT_MODEL_FILE_PATH,
                4, null, jsonObject, "1:0");
    }

    /**
     * 功能描述:水压设备事件下载(所有设备)
     *
     * @param response HttpServletResponse
     * @param json 参数
     * @author wangzhiwen
     * @date 2019/10/8 9:51
     */
    @Override
    public void downloadWaterDeviceEvent(HttpServletResponse response, String json) {
        WaterRecord waterRecord = JSONObject.parseObject(json, WaterRecord.class);
        String[] keys = {"rowNum", "deviceSysName", "qrNo", "sensorNoDesc", "presentValueDesc", "eventTypeDesc", "createTimeDesc", "recoverTimeDesc",
                "recoverStatusDesc", "handleStatusDesc", "eventHandleTimeDesc", "pointLocationDesc"};
        String fileName = "-水压监测告警事件导出表";
        String modelFilePath = ConstantsIot.REPORT_WATER_DEVICE_EVENT_MODEL_FILE_PATH;
        Integer deviceType = waterRecord.getDeviceType();
        if (deviceType != null && 3 == deviceType) {
            fileName = "-智能水浸监测告警事件导出表";
            modelFilePath = ConstantsIot.REPORT_WATER_SOAKING_DEVICE_EVENT_MODEL_FILE_PATH;
            keys = new String[]{"rowNum", "deviceSysName", "qrNo", "sensorNoDesc",  "eventTypeDesc", "createTimeDesc", "recoverTimeDesc",
                    "recoverStatusDesc", "handleStatusDesc", "eventHandleTimeDesc", "pointLocationDesc"};
        } else if (deviceType != null && 8 == deviceType) {
            fileName = "-智能消火栓告警事件导出表";
            modelFilePath = ConstantsIot.REPORT_WATER_HYDRANT_DEVICE_EVENT_MODEL_FILE_PATH;
        }
        waterRecord.setTitle(CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, waterRecord.getProjectId(), "projectName") + fileName);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(waterRecord);

        List<WaterRecord> list = getWaterRecordList(waterRecord);
        ExcelUtils.createAndDownloadExcel(response, list, keys, modelFilePath,
                4, null, jsonObject, "1:0");
    }

    /**
     * 获取 事件list
     *
     * @param waterRecord WaterRecord
     * @return List<WaterRecord>
     */
    public List<WaterRecord> getWaterRecordList(WaterRecord waterRecord) {
        // 不分页
        waterRecord.setPageSize(0);
        if (waterRecord.getBeginTime() != null){
            // beginTime 与 endTime 组织使用
            waterRecord.setCreateTime(waterRecord.getBeginTime());
        }

        if (waterRecord.getCreateTime() == null) {
            Date date = DateUtils.getDate();
            waterRecord.setCreateTime(date);
            waterRecord.setEndTime(date);
        }

        if (waterRecord.getOrderCols() == null) {
            String[] orderCols = {"createTime"};
            waterRecord.setOrderCols(orderCols);
            waterRecord.setOrderDesc(true);
        }
        return waterDeviceRecordDao.findWaterDeviceEventList(waterRecord);
    }

    /**
     * 查询消火栓故障信息统计
     *
     * @param json 参数
     * @author dwt
     * @date 2020-08-10 10:43
     * @return Data
     */
    @Override
    public Data findHydrantFaultMsg(String json) {
        WaterRecord waterRecord = JSONObject.parseObject(json, WaterRecord.class);
        WaterDeviceReport waterDeviceReport = waterDeviceRecordDao.findHydrantFaultMsg(waterRecord);
        return asseData(waterDeviceReport);
    }
}
