package com.xjt.cloud.iot.core.service.impl.inter;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.iot.core.dao.iot.fire.FireAlarmEventDao;
import com.xjt.cloud.iot.core.dao.iot.vesa.VesaDeviceRecordDao;
import com.xjt.cloud.iot.core.entity.fire.FireAlarmEvent;
import com.xjt.cloud.iot.core.entity.inter.InterEntity;
import com.xjt.cloud.iot.core.entity.vesa.VesaRecord;
import com.xjt.cloud.iot.core.service.service.inter.InterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName InterServiceImpl
 * @Author dwt
 * @Date 2020-07-15 15:02
 * @Description 因特需求
 * @Version 1.0
 */
@Service
public class InterServiceImpl extends AbstractService implements InterService {
    @Autowired
    private VesaDeviceRecordDao vesaDeviceRecordDao;
    @Autowired
    private FireAlarmEventDao fireAlarmEventDao;

    /**
     * @Author: dwt
     * @Date: 2020-07-15 16:41
     * @Param: json
     * @Return: Data
     * @Description 因特报表接口
     */
    @Override
    public Data findInterAreaTypeEventCount(String json) {
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);
        VesaRecord vesaRecord = JSONObject.parseObject(json, VesaRecord.class);
        Date endDate = fireAlarmEvent.getEndTime();
        Calendar c = Calendar.getInstance();
        Date endTime;
        Date startTime;
        if (endDate == null) {
            endTime = c.getTime();
        } else {
            c.setTime(endDate);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            endTime = c.getTime();
        }
        c.add(Calendar.DAY_OF_MONTH, -29);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        startTime = c.getTime();
        vesaRecord.setStartTime(startTime);
        vesaRecord.setEndTime(endTime);
        vesaRecord.setGroupType(2);
        List<InterEntity> vesaList = vesaDeviceRecordDao.findInterAreaTypeEventCount(vesaRecord);
        Integer dValue = DateUtils.getDateTimeCount(2, endTime, startTime);
        dValue = Math.abs(dValue);
        fireAlarmEvent.setDateType(2);
        fireAlarmEvent.setDateNum(dValue);
        fireAlarmEvent.setStartTime(startTime);
        fireAlarmEvent.setEndTime(endTime);
        List<InterEntity> fireList = fireAlarmEventDao.findInterAreaTypeEventCount(fireAlarmEvent);
        Map<String, Object> map = new HashMap<>(2);
        map.put("vesaList", vesaList);
        map.put("fireAlarmList", fireList);
        return asseData(map);
    }

    /**
     * @Author: dwt
     * @Date: 2020-07-16 11:05
     * @Param: json
     * @Return: Data
     * @Description 查询火警主机和极早期当天五点前的所有活跃事件（未恢复事件）
     */
    @Override
    public Data findFireAlarmAndVesaEvent(String json) {
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);
        VesaRecord vesaRecord = JSONObject.parseObject(json, VesaRecord.class);
        Date startDate = fireAlarmEvent.getStartTime();
        Calendar c = Calendar.getInstance();
        Date endTime;
        Date startTime;
        if (startDate != null) {
            startTime = startDate;
            c.setTime(startDate);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            endTime = c.getTime();
        } else {
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            startTime = c.getTime();
            c.set(Calendar.HOUR_OF_DAY, 18);
            endTime = c.getTime();
        }

        fireAlarmEvent.setEndTime(endTime);
        fireAlarmEvent.setStartTime(startTime);
        fireAlarmEvent.setRecoverStatus(2);
        vesaRecord.setStartTime(startTime);
        vesaRecord.setEndTime(endTime);
        vesaRecord.setRecoverStatus(0);
        Integer[] eventTypeArr = {0, 1, 2, 3, 4};
        vesaRecord.setEventTypeArr(eventTypeArr);
        setVesdaEventOrder(vesaRecord);
        List<VesaRecord> vList = vesaDeviceRecordDao.findVesaDeviceEventList(vesaRecord);
        Integer vCount = vesaDeviceRecordDao.findVesaDeviceEventListTotalCount(vesaRecord);
        int[] eventTypeArr2 = {1, 2, 7};
        fireAlarmEvent.setEventTypeArr(eventTypeArr2);
        setFireAlarmEventOrder(fireAlarmEvent);
        List<FireAlarmEvent> fList = fireAlarmEventDao.findFireAlarmEventList(fireAlarmEvent);
        Long fCount = fireAlarmEventDao.findFireAlarmEventCount(fireAlarmEvent);
        Map<String, Object> map = new HashMap<>(4);
        map.put("vList", vList);
        map.put("vCount", vCount);
        map.put("fList", fList);
        map.put("fCount", fCount);
        return asseData(map);
    }

    private void setFireAlarmEventOrder(FireAlarmEvent fireAlarmEvent) {
        String[] orderCols = fireAlarmEvent.getOrderCols();
        if (orderCols == null || orderCols.length <= 0) {
            orderCols = new String[]{"fae.createTime"};
            fireAlarmEvent.setOrderDesc(true);
            fireAlarmEvent.setOrderCols(orderCols);
        }
    }

    private void setVesdaEventOrder(VesaRecord vesaRecord) {
        String[] orderCols = vesaRecord.getOrderCols();
        if (orderCols == null || orderCols.length <= 0) {
            orderCols = new String[]{"e.createTime"};
            vesaRecord.setOrderDesc(true);
            vesaRecord.setOrderCols(orderCols);
        }
    }
}
