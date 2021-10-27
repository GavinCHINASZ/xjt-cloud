package com.xjt.cloud.safety.core.service.impl.fault;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.safety.core.dao.fault.FaultHandlerDao;
import com.xjt.cloud.safety.core.dao.fault.FaultRepairRecordDao;
import com.xjt.cloud.safety.core.dao.fault.RepairProgressDao;
import com.xjt.cloud.safety.core.entity.fault.FaultHandler;
import com.xjt.cloud.safety.core.entity.fault.FaultRepairRecord;
import com.xjt.cloud.safety.core.entity.fault.RepairProgress;
import com.xjt.cloud.safety.core.service.service.fault.FaultRepairRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 故障报修
 *
 * @author huanggc
 * @date 2019/09/02
 **/
@Service
public class FaultRepairRecordServiceImpl extends AbstractService implements FaultRepairRecordService {
    @Autowired
    private FaultRepairRecordDao faultRepairRecordDao;
    @Autowired
    private RepairProgressDao repairProgressDao;
    @Autowired
    private FaultHandlerDao faultHandlerDao;

    /**
     * 功能描述: 新建故障报修
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/22
     */
    @Override
    public Data saveFaultRepairRecord(String json) {
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);
        // 新增时默认状态
        faultRepairRecord.setWorkOrderStatus(2);

        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        String orgUserName = getOrgUserName(userId, faultRepairRecord.getProjectId());

        faultRepairRecord.setCreateUserName(userName);
        faultRepairRecord.setCreateUserId(userId);

        int num = faultRepairRecordDao.saveFaultRepairRecord(faultRepairRecord);
        if (num > 0){
            // 日志,记录
            RepairProgress repairProgress = new RepairProgress();
            repairProgress.setFaultRepairRecordId(faultRepairRecord.getId());
            repairProgress.setTitle("新增故障报修");
            repairProgress.setContent(orgUserName + " 提交了维修订单");
            repairProgress.setCreateUserId(userId);
            repairProgress.setCreateUserName(orgUserName);
            repairProgressDao.saverRepairProgress(repairProgress);
        }

        return asseData(faultRepairRecord);
    }

    /**
     * 功能描述:查询 故障报修 列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/02
     */
    @Override
    public Data findFaultRepairRecordList(String json) {
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);

        if (faultRepairRecord.getHaveAll() != null && faultRepairRecord.getHaveAll() == 1){
            String userName = SecurityUserHolder.getUserName();
            Long userId = getLoginUserId(userName);
            faultRepairRecord.setRepairUserId(userId);
        }

        Integer totalCount = faultRepairRecord.getTotalCount();
        Integer pageSize = faultRepairRecord.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = faultRepairRecordDao.findFaultRepairRecordTotalCount(faultRepairRecord);
        }

        if (null == faultRepairRecord.getOrderCols()) {
            String[] orderCols = {"lastModifyTime"};
            faultRepairRecord.setOrderCols(orderCols);
            faultRepairRecord.setOrderDesc(true);
        }
        List<FaultRepairRecord> repairRecordList = faultRepairRecordDao.findFaultRepairRecordList(faultRepairRecord);

        Map<String, Object> map = new HashMap<>(2);
        map.put("listObj", repairRecordList);
        map.put("totalCount", totalCount);
        return asseData(map);
    }

    /**
     * 功能描述:查询 故障报修
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/02
     */
    @Override
    public Data findFaultRepairRecord(String json) {
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);
        FaultRepairRecord entity = faultRepairRecordDao.findFaultRepairRecord(faultRepairRecord);
        return asseData(entity);
    }

    /**
     * 功能描述:查询 故障报修类型
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/02
     */
    @Override
    public Data findFaultType(String json) {
        /*String s = "";
        try {
            String findFaultType = ConstantsDevice.FIND_FAULT_TYPE;
            s = new String(findFaultType.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            SysLog.error(e);
        }*/
        return asseData("接触不良的故障;其它");
    }

    /**
     * 功能描述:更新故障报修
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/02
     */
    @Override
    public Data updateFaultRepairRecord(String json) {
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);

        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
       String orgUserName = getOrgUserName(userId, faultRepairRecord.getProjectId());
        if (faultRepairRecord.getRepairUserId() != null){
            FaultHandler faultHandler = new FaultHandler();
            faultHandler.setFaultRepairRecordId(faultRepairRecord.getId());
            faultHandler.setRepairUserId(faultRepairRecord.getRepairUserId());
            int num = faultHandlerDao.saveFaultHandler(faultHandler);

            faultRepairRecord.setMaintenanceUserId(faultRepairRecord.getRepairUserId());
            faultRepairRecordDao.updateFaultRepairRecord(faultRepairRecord);

            RepairProgress repairProgress = new RepairProgress();
            repairProgress.setTitle("转派故障报修");
            repairProgress.setContent(orgUserName + " 转派维修订单");
            repairProgress.setCreateUserId(userId);
            repairProgress.setCreateUserName(orgUserName);
            repairProgressDao.saverRepairProgress(repairProgress);
            if (num > 0) {
                return Data.isSuccess();
            }else {
                return Data.isFail();
            }
        }else {
            faultRepairRecord.setUpdateUserId(userId);
            faultRepairRecord.setUpdateUserName(userName);

            if (faultRepairRecord.getWorkOrderStatus() != null) {
                // 日志,记录
                RepairProgress repairProgress = new RepairProgress();
                repairProgress.setFaultRepairRecordId(faultRepairRecord.getId());
                if (faultRepairRecord.getWorkOrderStatus() == 4){
                    repairProgress.setTitle("完成故障报修");
                    repairProgress.setContent(orgUserName + " 完成维修订单");
                }else if (faultRepairRecord.getWorkOrderStatus() == 6){
                    repairProgress.setTitle("暂停故障报修");
                    repairProgress.setContent(orgUserName + " 暂停维修订单");
                }
                repairProgress.setCreateUserId(userId);
                repairProgress.setCreateUserName(orgUserName);
                repairProgressDao.saverRepairProgress(repairProgress);
            }

            if(StringUtils.isNotEmpty(faultRepairRecord.getMaintenanceResult())){
                RepairProgress repairProgress = new RepairProgress();
                repairProgress.setFaultRepairRecordId(faultRepairRecord.getId());
                repairProgress.setTitle("维修说明");
                repairProgress.setContent(orgUserName + " 修改维修说明");
                repairProgress.setCreateUserId(userId);
                repairProgress.setCreateUserName(orgUserName);
                repairProgressDao.saverRepairProgress(repairProgress);
            }

            faultRepairRecordDao.updateFaultRepairRecord(faultRepairRecord);
            return Data.isSuccess();
        }
    }

    /**
     * 功能描述:查询 日志/记录 列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/22
     */
    @Override
    public Data findRepairProgressList(String json) {
        RepairProgress repairProgress = JSONObject.parseObject(json, RepairProgress.class);

        if (null == repairProgress.getOrderCols()) {
            String[] orderCols = {"lastModifyTime"};
            repairProgress.setOrderCols(orderCols);
            repairProgress.setOrderDesc(true);
        }

        Integer totalCount = repairProgress.getTotalCount();
        Integer pageSize = repairProgress.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = repairProgressDao.findRepairProgressTotalCount(repairProgress);
        }
        List<RepairProgress> repairProgressList = repairProgressDao.findRepairProgressList(repairProgress);

        return asseData(totalCount, repairProgressList);
    }

}
