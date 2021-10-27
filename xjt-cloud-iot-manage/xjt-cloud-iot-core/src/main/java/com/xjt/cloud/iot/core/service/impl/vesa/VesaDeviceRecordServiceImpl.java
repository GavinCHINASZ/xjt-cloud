package com.xjt.cloud.iot.core.service.impl.vesa;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.dao.iot.vesa.VesaDeviceDao;
import com.xjt.cloud.iot.core.dao.iot.vesa.VesaDeviceRecordDao;
import com.xjt.cloud.iot.core.entity.vesa.VesaDevice;
import com.xjt.cloud.iot.core.entity.vesa.VesaDeviceReport;
import com.xjt.cloud.iot.core.entity.vesa.VesaRecord;
import com.xjt.cloud.iot.core.service.service.vesa.VesaDeviceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 极早期设备管理接口实现类
 *
 * @author wangzhiwen
 * @date 2019/9/26 15:35
 */
@Service
public class VesaDeviceRecordServiceImpl extends AbstractService implements VesaDeviceRecordService {
    @Autowired
    private VesaDeviceRecordDao vesaDeviceRecordDao;
    @Autowired
    private VesaDeviceDao vesaDeviceDao;

    /**
     * 功能描述:　查询极早期设备事件记录列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/26 15:44
     */
    @Override
    public Data findVesaDeviceEventList(String json) {
        VesaRecord vesaRecord = JSONObject.parseObject(json, VesaRecord.class);
        Integer totalCount = vesaRecord.getTotalCount();
        Integer pageSize = vesaRecord.getPageSize();
        if (vesaRecord.getOrderCols() == null) {
            String[] orderCols = {"createTime"};
            vesaRecord.setOrderCols(orderCols);
            // 倒序的设置，最新发生的时间，放在最前面
            vesaRecord.setOrderDesc(true);
        }

        // 根据 id查询时不用判断时间
        if (vesaRecord.getId() == null && vesaRecord.getStartTime() == null && vesaRecord.getCreateTime() == null) {
            Date date = DateUtils.getDate();
            vesaRecord.setStartTime(date);
            vesaRecord.setEndTime(date);
        }

        endTimeUpdate(vesaRecord);

        if (null == totalCount && null != pageSize && 0 != pageSize) {
            //判断是否存在总数，如没有，则查询总记录数
            totalCount = vesaDeviceRecordDao.findVesaDeviceEventListTotalCount(vesaRecord);
        }
        List<VesaRecord> list = vesaDeviceRecordDao.findVesaDeviceEventList(vesaRecord);
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
    public Data findVesaDeviceEventSummaryReport(String json) {
        VesaRecord vesaRecord = JSONObject.parseObject(json, VesaRecord.class);
        if (vesaRecord.getStartTime() == null) {
            Date date = DateUtils.getDate();
            vesaRecord.setStartTime(date);
            vesaRecord.setEndTime(date);
        }

        endTimeUpdate(vesaRecord);
        VesaDeviceReport vesaDeviceReport = vesaDeviceRecordDao.findVesaDeviceEventSummaryReport(vesaRecord);
        return asseData(vesaDeviceReport);
    }

    /**
     * @Description 查询app首页极早期信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject findUserProjectVesaData(String json){
        VesaRecord vesaRecord = JSONObject.parseObject(json, VesaRecord.class);
        Date date = DateUtils.getDate();
        vesaRecord.setStartTime(date);
        vesaRecord.setEndTime(date);

        VesaDeviceReport vesaDeviceReport = vesaDeviceRecordDao.findVesaDeviceEventSummaryReport(vesaRecord);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("modelIndex",20);
        if (vesaDeviceReport != null) {
            jsonObject.put("fireAlarm1Total", vesaDeviceReport.getFireAlarm1Total());
            jsonObject.put("fireAlarm2Total", vesaDeviceReport.getFireAlarm2Total());
            jsonObject.put("actionTotal", vesaDeviceReport.getActionTotal());
            jsonObject.put("alarmTotal", vesaDeviceReport.getAlarmTotal());
            jsonObject.put("faultTotal", vesaDeviceReport.getFaultTotal());
        }else{
            VesaDevice vesaDevice = JSONObject.parseObject(json, VesaDevice.class);
            Integer total = vesaDeviceDao.findVesaDeviceListTotalCount(vesaDevice);
            if(total == null || total == 0){
                jsonObject.put("deviceCount", 0);
            }
            jsonObject.put("fireAlarm1Total", 0);
            jsonObject.put("fireAlarm2Total", 0);
            jsonObject.put("actionTotal", 0);
            jsonObject.put("alarmTotal", 0);
            jsonObject.put("faultTotal", 0);
        }
        return jsonObject;
    }

    /**
     * 功能描述: 查询极早期设备记录
     *
     * @param vesaRecord VesaRecord
     * @param orderCols  排序
     * @return java.util.List<com.xjt.cloud.iot.core.entity.water.VesaRecord>
     * @author wangzhiwen
     * @date 2019/11/4 17:12
     */
    private VesaRecord findVesaDeviceRecordList(VesaRecord vesaRecord, String[] orderCols) {
        vesaRecord.setOrderCols(orderCols);
        vesaRecord.setOrderDesc(true);
        vesaRecord.setPageSize(null);
        return vesaRecord;
    }

    /**
     * 功能描述:　查询极早期设备事件记录按时间汇总（曲线图）
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/26 15:44
     */
    @Override
    public Data findVesaDeviceEventReportCount(String json) {
        VesaRecord vesaRecord = JSONObject.parseObject(json, VesaRecord.class);
        if (vesaRecord.getStartTime() == null) {
            Date date = DateUtils.getDate();
            vesaRecord.setStartTime(date);
            vesaRecord.setEndTime(date);
        }

        endTimeUpdate(vesaRecord);
        vesaRecord.setGroupType(DateUtils.getBetweenDateTimeType(vesaRecord.getStartTime(), vesaRecord.getEndTime()));
        List<VesaRecord> list = vesaDeviceRecordDao.findVesaDeviceEventReportCount(vesaRecord);
        return asseData(list);
    }

    /**
     * 功能描述:　查询极早期设备事件记录汇总，饼图
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/26 15:44
     */
    @Override
    public Data findVesaDeviceEventReportTotal(String json) {
        VesaRecord vesaRecord = JSONObject.parseObject(json, VesaRecord.class);
        endTimeUpdate(vesaRecord);
        List<VesaRecord> list = vesaDeviceRecordDao.findVesaDeviceEventReportTotal(vesaRecord);
        return asseData(list);
    }

    /**
     * 功能描述:设备事件下载(所有设备)
     *
     * @param response HttpServletResponse
     * @param json     参数
     * @author wangzhiwen
     * @date 2019/10/8 9:51
     */
    @Override
    public void downloadVesaDeviceEvent(HttpServletResponse response, String json) {
        VesaRecord vesaRecord = JSONObject.parseObject(json, VesaRecord.class);
        JSONObject projectJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, vesaRecord.getProjectId());
        vesaRecord.setTitle(projectJson.getString("projectName") + "-极早期告警事件表");
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(vesaRecord);

        List<VesaRecord> list = getVesaRecordList(vesaRecord);

        String[] keys = {"rowNum", "eventTypeDesc", "eventDesc", "handleMeasure", "sensorName", "loopName", "detector", "detectorType", "position", "createTimeDesc",
                "recoverTimeDesc", "recoverStatusDesc", "handleStatusDesc", "eventHandleTimeDesc"};
        ExcelUtils.createAndDownloadExcel(response, list, keys, ConstantsIot.REPORT_VESA_DEVICE_EVENT_MODEL_FILE_PATH,
                4, null, jsonObject, "1:0");
    }

    /**
     *  获取 记录list
     *
     * @param vesaRecord VesaRecord
     * @return List<VesaRecord>
     */
    public List<VesaRecord> getVesaRecordList(VesaRecord vesaRecord) {
        vesaRecord.setPageSize(0);

        endTimeUpdate(vesaRecord);

        if (vesaRecord.getOrderCols() == null) {
            String[] orderCols = {"createTime"};
            vesaRecord.setOrderCols(orderCols);
            vesaRecord.setOrderDesc(true);
        }
        return vesaDeviceRecordDao.findVesaDeviceEventList(vesaRecord);
    }

    /**
     * 功能描述:查询设备事件汇 APP
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/26 14:59
     */
    @Override
    public Data findVesaEventSummaryReportApp(String json) {
        VesaRecord vesaRecord = JSONObject.parseObject(json, VesaRecord.class);
        if (vesaRecord == null || vesaRecord.getProjectId() == null) {
            return null;
        }
        String timeType = vesaRecord.getTimeType();

        Calendar c = Calendar.getInstance();
        Date currentDate = c.getTime();
        Date startDate = null;
        Date previousDate = null;
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        if ("day".equals(timeType)) {
            startDate = c.getTime();
            c.add(Calendar.DAY_OF_MONTH, -1);
            previousDate = c.getTime();
        } else if ("month".equals(timeType)) {
            c.set(Calendar.DAY_OF_MONTH, 1);
            startDate = c.getTime();
            c.add(Calendar.MONTH, -1);
            previousDate = c.getTime();
        } else if ("year".equals(timeType)) {
            c.set(Calendar.MONTH, 1);
            c.set(Calendar.DAY_OF_MONTH, 1);
            startDate = c.getTime();
            c.add(Calendar.YEAR, -1);
            previousDate = c.getTime();
        }

        vesaRecord.setStartTime(startDate);
        vesaRecord.setEndTime(currentDate);
        VesaDeviceReport currentReport = vesaDeviceRecordDao.findVesaDeviceEventSummaryReport(vesaRecord);
        vesaRecord.setStartTime(previousDate);
        vesaRecord.setEndTime(startDate);
        VesaDeviceReport previousReport = vesaDeviceRecordDao.findVesaDeviceEventSummaryReport(vesaRecord);

        Map<String, Object> map = new HashMap<>(2);
        map.put("current", currentReport);
        map.put("previous", previousReport);
        return asseData(map);
    }

    /**
     * 功能描述:查询当前极早期事件汇总 API接口
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/26 14:59
     */
    @Override
    public Data findVesaEventReportCountApp(String json) throws ParseException {
        VesaRecord vesaRecord = JSONObject.parseObject(json, VesaRecord.class);
        if (vesaRecord == null || vesaRecord.getProjectId() == null) {
            return null;
        }

        String timeType = vesaRecord.getTimeType();
        String pattern = null;
        int dateType = 0;
        if ("day".equals(timeType)) {
            pattern = "yyyy-MM-dd";
            dateType = 1;
        } else if ("month".equals(timeType)) {
            pattern = "yyyy-MM";
            dateType = 2;
        } else if ("year".equals(timeType)) {
            pattern = "yyyy";
            dateType = 3;
        }

        Date endDate = vesaRecord.getEndTime();
        Calendar c = Calendar.getInstance();
        if (endDate == null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date date = sdf.parse(vesaRecord.getTimeStr());
            vesaRecord.setStartTime(date);
            endDate = date;
        }

        c.setTime(endDate);
        if (dateType == 1) {
            c.add(Calendar.DAY_OF_MONTH, 1);
        } else if (dateType == 2) {
            c.add(Calendar.MONTH, 1);
        } else if (dateType == 3) {
            c.add(Calendar.YEAR, 1);
        }
        c.add(Calendar.SECOND, -1);
        endDate = c.getTime();

        vesaRecord.setEndTime(endDate);

        vesaRecord.setGroupType(DateUtils.getBetweenDateTimeType(vesaRecord.getStartTime(), vesaRecord.getEndTime()));

        List<VesaRecord> list = vesaDeviceRecordDao.findVesaDeviceEventReportCount(vesaRecord);
        return asseData(list);
    }

    /**
     * 功能描述:　查询极早期设备事件记录列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/26 15:44
     */
    @Override
    public Data findVesaDeviceEventListApp(String json) throws ParseException {
        VesaRecord vesaRecord = JSONObject.parseObject(json, VesaRecord.class);
        if (vesaRecord == null || vesaRecord.getProjectId() == null) {
            return null;
        }

        String timeType = vesaRecord.getTimeType();
        String pattern = null;
        // 1:hour时, 2:day天, 3:month月, 4:year年
        int dateType = 0;
        if ("day".equals(timeType)) {
            pattern = "yyyy-MM-dd";
            dateType = 1;
        } else if ("month".equals(timeType)) {
            pattern = "yyyy-MM";
            dateType = 2;
        } else if ("year".equals(timeType)) {
            pattern = "yyyy";
            dateType = 3;
        }

        Date endDate = vesaRecord.getEndTime();
        Calendar c = Calendar.getInstance();
        if (endDate == null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date date = sdf.parse(vesaRecord.getTimeStr());
            vesaRecord.setStartTime(date);
            endDate = date;

            c.setTime(endDate);
            if (dateType == 1) {
                c.add(Calendar.DAY_OF_MONTH, 1);
            } else if (dateType == 2) {
                c.add(Calendar.MONTH, 1);
            } else if (dateType == 3) {
                c.add(Calendar.YEAR, 1);
            }
            c.add(Calendar.SECOND, -1);
            endDate = c.getTime();

            vesaRecord.setEndTime(endDate);
        }else {
            Timestamp dayEndTime = DateUtils.getDayEndTime(endDate);
            Date d = new Date(dayEndTime.getTime());
            vesaRecord.setEndTime(d);
        }

        Integer totalCount = vesaRecord.getTotalCount();
        Integer pageSize = vesaRecord.getPageSize();
        if (vesaRecord.getOrderCols() == null) {
            String[] orderCols = {"createTime"};
            vesaRecord.setOrderCols(orderCols);
            // 倒序的设置，最新发生的时间，放在最前面
            vesaRecord.setOrderDesc(true);
        }

        if (null == totalCount && null != pageSize && 0 != pageSize) {
            // 判断是否存在总数，如没有，则查询总记录数
            totalCount = vesaDeviceRecordDao.findVesaDeviceEventListTotalCount(vesaRecord);
        }
        List<VesaRecord> list = vesaDeviceRecordDao.findVesaDeviceEventList(vesaRecord);
        return asseData(totalCount, list);
    }

    /**
     * 功能描述:查询设备事件汇 APP
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/26 14:59
     */
    @Override
    public Data findVesaFaultNameCountApp(String json) throws ParseException {
        VesaRecord vesaRecord = JSONObject.parseObject(json, VesaRecord.class);
        if (vesaRecord == null || vesaRecord.getProjectId() == null) {
            return null;
        }

        String timeType = vesaRecord.getTimeType();
        String timeStr = vesaRecord.getTimeStr();
        String pattern = null;
        // 1:hour,2:day,3:month,4:year
        int dateType = 0;
        if ("day".equals(timeType)) {
            pattern = "yyyy-MM-dd";
            dateType = 1;
        } else if ("month".equals(timeType)) {
            pattern = "yyyy-MM";
            dateType = 2;
        } else if ("year".equals(timeType)) {
            pattern = "yyyy";
            dateType = 3;
        }

        Date endDate = vesaRecord.getEndTime();
        Date startDate = vesaRecord.getStartTime();
        Calendar c = Calendar.getInstance();
        if (endDate != null && startDate == null) {
            c.setTime(endDate);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            endDate = c.getTime();
            c.add(Calendar.DAY_OF_MONTH, -7);
            c.add(Calendar.SECOND, 1);
            startDate = c.getTime();
            vesaRecord.setEndTime(endDate);
            vesaRecord.setStartTime(startDate);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            startDate = sdf.parse(timeStr);
            vesaRecord.setStartTime(startDate);
            c.setTime(startDate);
            if (dateType == 1) {
                c.add(Calendar.DAY_OF_MONTH, 1);
            } else if (dateType == 2) {
                c.add(Calendar.MONTH, 1);
            } else if (dateType == 3) {
                c.add(Calendar.YEAR, 1);
            }
            c.add(Calendar.SECOND, -1);
            endDate = c.getTime();
            vesaRecord.setEndTime(endDate);
        }

        List<VesaRecord> list = vesaDeviceRecordDao.findVesaFaultNameCount(vesaRecord);
        return asseData(list);
    }

    /**
     * 功能描述:查询设备事件汇
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/26 14:59
     */
    @Override
    public Data findVesaFaultNameCount(String json) {
        VesaRecord vesaRecord = JSONObject.parseObject(json, VesaRecord.class);
        if (vesaRecord == null || vesaRecord.getProjectId() == null) {
            return null;
        }

        if (vesaRecord.getStartTime() == null) {
            Date date = DateUtils.getDate();
            vesaRecord.setStartTime(date);
            vesaRecord.setEndTime(date);
        }

        endTimeUpdate(vesaRecord);
        List<VesaRecord> list = vesaDeviceRecordDao.findVesaFaultNameCount(vesaRecord);
        return asseData(list);
    }

    /**
     * 功能描述: 项目主页 极早期
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/01/20
     */
    @Override
    public Data findVesaDeviceProjectHomeData(String json) {
        VesaRecord vesaRecord = JSONObject.parseObject(json, VesaRecord.class);
        VesaDeviceReport vesaDeviceReport = vesaDeviceRecordDao.findVesaDeviceProjectHomeData(vesaRecord);
        return asseData(vesaDeviceReport);
    }

    /**
     * endTime修改
     *
     * @param vesaRecord VesaRecord
     */
    private static void endTimeUpdate(VesaRecord vesaRecord) {
        Date endTime = vesaRecord.getEndTime();
        if (endTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(endTime);
            c.add(Calendar.DAY_OF_MONTH, 1);
            c.add(Calendar.SECOND, -1);
            endTime = c.getTime();
            vesaRecord.setEndTime(endTime);
        }
    }

}
