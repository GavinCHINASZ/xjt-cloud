package com.xjt.cloud.task.core.service.impl.protect;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.task.core.common.ConstantsDevice;
import com.xjt.cloud.task.core.dao.protect.ProtectDao;
import com.xjt.cloud.task.core.dao.protect.ProtectRecordDao;
import com.xjt.cloud.task.core.dao.protect.ProtectUserDao;
import com.xjt.cloud.task.core.entity.protect.*;
import com.xjt.cloud.task.core.service.service.protect.ProtectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 地铁 班前防护 ServiceImpl
 *
 * @author huangGuiChuan
 * @date 2020/09/27
 */
@Service
public class ProtectServiceImpl extends AbstractService implements ProtectService {
    @Autowired
    private ProtectDao protectDao;
    @Autowired
    private ProtectUserDao protectUserDao;
    @Autowired
    private ProtectRecordDao protectRecordDao;

    /**
     * 查询 班前防护
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findProtect(String json) {
        Protect protect = JSONObject.parseObject(json, Protect.class);
        protect = protectDao.findProtect(protect);

        String[] imageUrls = protectDao.findProtectImageUrls(protect.getId());
        protect.setImageUrls(imageUrls);
        return asseData(protect);
    }

    /**
     * 查询 班前防护 list
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findProtectList(String json) {
        Protect protect = JSONObject.parseObject(json, Protect.class);
        if (protect.getCreateTime() != null){
            Date createTime = protect.getCreateTime();
            protect.setCreateTime(DateUtils.monthStarDate(createTime));
            protect.setLastModifyTime(DateUtils.monthEndDate(createTime));
        }

        Integer totalCount = protect.getTotalCount();
        Integer pageSize = protect.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){
            totalCount = protectDao.findProtectListTotalCount(protect);
        }

        if (protect.getProtectState() != null && protect.getProtectState() == 4){
            String[] orderCols = {"protectCloseTime"};
            protect.setOrderCols(orderCols);
            protect.setOrderDesc(true);
        }else {
            if (null == protect.getOrderCols()){
                String[] orderCols = {"createTime"};
                protect.setOrderCols(orderCols);
                protect.setOrderDesc(true);
            }
        }

        List<Protect> protectList = protectDao.findProtectList(protect);
        return asseData(totalCount, protectList);
    }

    /**
     * 编辑 班前防护
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data updateProtect(String json) {
        Protect protect = JSONObject.parseObject(json, Protect.class);

        Date createTime = new Date();
        // 2进行中  防护开始时间
        if (protect.getStatus() != null && protect.getStatus() == 2){
            protect.setProtectCreateTime(createTime);
        }

        if (protect.getProtectState() != null){
            // 3结束防护  4防护关闭
            if (protect.getProtectState() == 3){
                protect.setProtectEndTime(createTime);
            }else if (protect.getProtectState() == 4){
                protect.setProtectCloseTime(createTime);
            }
        }

        Protect entity = null;
        Long id = protect.getId();
        if (id != null){
            entity = protectDao.findProtectById(protect.getId());
        }

        StringJoiner sj = new StringJoiner(";");
        if (entity != null){
            if (protect.getProtectName() != null && !protect.getProtectName().equals(entity.getProtectName())){
                sj.add("修改作业名称:" + protect.getProtectName());
            }

            if (protect.getProtectGradeId() != null && !protect.getProtectGradeId().equals(entity.getProtectGradeId())){
                sj.add("修改安全防护等级:" + protect.getProtectGrade());
            }
        }else {
            entity = new Protect();
        }

        protect.setCreateTime(createTime);
        // 作业人员
        Long[] operatorsIds = protect.getOperatorsIds();
        if (null != operatorsIds){
            ProtectUser protectUser = new ProtectUser();
            protectUser.setProtectId(protect.getId());
            List<Long> protectUserIdList = protectUserDao.findProtectUserIdList(protectUser);

            List<Long> listB = Arrays.asList(operatorsIds);
            // list1中有，list2中没有的元素
            List<Long> diff = diff(listB, protectUserIdList);
            if (CollectionUtils.isNotEmpty(diff)){
                entity.setUserIdList(diff);
                String userNameString = protectDao.findUserNameString(entity);
                // 增加
                sj.add("增加作业人员:" + userNameString);
            }

            // list1中没有，list2中有的元素
            List<Long> diff1 = diff(protectUserIdList, listB);
            if (CollectionUtils.isNotEmpty(diff1)){
                entity.setUserIdList(diff1);
                String userNameString = protectDao.findUserNameString(entity);
                // 删除
                sj.add("删除作业人员:" + userNameString);
            }

            protectUserDao.deleteProtectUser(protectUser);

            protectUser.setUserIds(protect.getOperatorsIds());
            protectUser.setCreateTime(createTime);
            protectUserDao.saveProtectUsers(protectUser);
        }

        submitterMethod(protect);

        // 火警事件
        if (protect.getFireAlarmEventIds() != null && protect.getFireAlarmEventIds().length > 0){
            // 没有重新检索前的数据
            List<FireAlarmEvent> fireAlarmEvent = protect.getFireAlarmEventList();

            Integer sign = protectDao.findSign(protect);

            // 最新的数据
            entity.setSign(sign != null && sign > 0 ? sign : 1);
            List<FireAlarmEvent> fireAlarmEvents = protectDao.findFireAlarmEventList(entity);
            diffFireEvent(fireAlarmEvent, fireAlarmEvents, sj);

            protectDao.deletedProtectFireEvent(protect);
            protectDao.saveProtectFireEvent(protect);
        }

        // 综合监测
        List<IntegratedMonitoring> integratedMonitoringList = protect.getIntegratedMonitoringList();
        if (CollectionUtils.isNotEmpty(integratedMonitoringList)){
            for (IntegratedMonitoring integratedMonitoring : integratedMonitoringList) {
                Integer oldMonitoringStatus = integratedMonitoring.getOldMonitoringStatus();
                if (oldMonitoringStatus != null && oldMonitoringStatus != integratedMonitoring.getMonitoringStatus()){
                    sj.add("综合监测:" + integratedMonitoring.getMonitoringName() + " 改变状态" + protect.getProtectName() +
                            (integratedMonitoring.getMonitoringStatus() == 1 ? "未完成" : "已完成"));
                }
            }

            // 删除作业下的 综合监测
            protectDao.deletedIntegratedMonitoringByProtectId(id);

            // 保存 综合监测
            protectDao.saveIntegratedMonitoring(id, integratedMonitoringList);
        }

        if (protect.getImageUrls() != null && protect.getImageUrls().length > 0){
            protect.setCreateTime(createTime);
            // 作业图片
            protectDao.saveProtectImageList(protect);
            sj.add("上传照片");
        }

        if (protect.getDeleted()){
            // 删除作业相关的人员
            protectUserDao.deleteProtectUserByProtect(protect);
        }

        // 更新作业
        protectDao.updateProtect(protect);

        String protectEventStr = sj.toString();
        if (StringUtils.isNotEmpty(protectEventStr)) {
            ProtectRecord protectRecord = new ProtectRecord();
            protectRecord.setProtectId(protect.getId());
            protectRecord.setProtectEvent(protectEventStr.length() > 230 ? protectEventStr.substring(0, 230) + "......" : protectEventStr);
            protectRecord.setSubmitter(protect.getSubmitter());
            protectRecord.setCreateTime(createTime);
            // 保存记录
            protectRecordDao.saveProtectRecord(protectRecord);
        }
        return asseData(protect);
    }

    /**
     * 新增 班前防护
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveProtect(String json) {
        Protect protect = JSONObject.parseObject(json, Protect.class);
        protectDao.saveProtect(protect);

        if (protect.getOperatorsIds() != null){
            // 作业人员
            ProtectUser protectUser = new ProtectUser();
            protectUser.setUserIds(protect.getOperatorsIds());
            protectUser.setProtectId(protect.getId());
            protectUserDao.saveProtectUsers(protectUser);
        }

        if (protect.getImageUrls() != null && protect.getImageUrls().length > 0){
            protectDao.saveProtectImageList(protect);
        }

        if (protect.getFireAlarmEventIds() != null && protect.getFireAlarmEventIds().length > 0){
            protectDao.deletedProtectFireEvent(protect);
            protectDao.saveProtectFireEvent(protect);
        }
        return asseData(protect);
    }

    /**
     * 开始防护
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/10/10
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data startProtect(String json) {
        Protect protect = JSONObject.parseObject(json, Protect.class);
        redisUtils.set(protect.getProjectId() + "_" + protect.getId() + "_protect", System.currentTimeMillis(), protect.getProtectDuration() * 60);
        return Data.isSuccess();
    }

    /**
     * 查询 剩余防护 时间
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/10/10
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data surplusProtect(String json) {
        Protect protect = JSONObject.parseObject(json, Protect.class);
        String redisKey = redisUtils.getString(protect.getProjectId() + "_" + protect.getId() + "_protect");

        if (StringUtils.isEmpty(redisKey)){
            return Data.isFail();
        }

        // 防护开始时间
        long currentTimeMillisOld = Long.parseLong(redisKey);
        // 当前时间
        long currentTimeMillis = System.currentTimeMillis();
        // 剩余时间
        long surplusTime = (currentTimeMillis - currentTimeMillisOld) / 1000;
        // 防护时间
        int timeOut = protect.getProtectDuration() * 60;
        return asseData(surplusTime > timeOut ? 0 : surplusTime);
    }

    /**
     * 结束防护
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/10/10
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data deletedProtect(String json) {
        Protect protect = JSONObject.parseObject(json, Protect.class);
        // 删除作业的防护时间
        redisUtils.del(protect.getProjectId() + "_" + protect.getId() + "_protect");
        return Data.isSuccess();
    }

    /**
     * 查询用户是否在项目的作业中
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/10/10
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findIsProtectPerson(String json) {
        Protect protect = JSONObject.parseObject(json, Protect.class);
        int num = protectDao.findIsProtectPerson(protect);
        return asseData(num);
    }

    /**
     * 查询 班前防护记录
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/12/18
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findProtectRecordList(String json) {
        ProtectRecord protectRecord = JSONObject.parseObject(json, ProtectRecord.class);

        Integer totalCount = protectRecord.getTotalCount();
        Integer pageSize = protectRecord.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){
            totalCount = protectRecordDao.findProtectRecordTotalCount(protectRecord);
        }

        if (null == protectRecord.getOrderCols()){
            String[] orderCols = {"createTime"};
            protectRecord.setOrderCols(orderCols);
            protectRecord.setOrderDesc(true);
        }
        List<ProtectRecord> protectList = protectRecordDao.findProtectRecordList(protectRecord);
        return asseData(totalCount, protectList);
    }

    /**
     * 更新 作业状态
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2021/03/09
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data updateProtectState(String json) {
        if (json.startsWith(",")){
            json = json.substring(1);
        }
        Protect protect = JSONObject.parseObject(json, Protect.class);
        int num = protectDao.updateProtect(protect);
        if (num > 0) {
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * 关闭 班前防护 list
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2021/03/09
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data closeProtect(String json) {
        Protect protect = JSONObject.parseObject(json, Protect.class);

        Date createTime = new Date();
        protect.setProtectCloseTime(createTime);

        submitterMethod(protect);

        int num = protectDao.closeProtect(protect);
        if (num > 0) {
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * 查询 作业状态
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2021/03/30
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findUserProtectState(String json) {
        Protect protect = JSONObject.parseObject(json, Protect.class);
        // 查询 成员 所在的作业是否完成
        int num = protectDao.findUserProtectState(protect);

        return asseData(num);
    }

    /**
     * 查询 作业的综合监测
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2021/04/02
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findIntegratedMonitoringList(String json) {
        IntegratedMonitoring integratedMonitoring = JSONObject.parseObject(json, IntegratedMonitoring.class);

        List<IntegratedMonitoring> integratedMonitoringList = protectDao.findIntegratedMonitoringList(integratedMonitoring);
        return asseData(integratedMonitoringList);
    }

    /**
     * 提交人 方法
     *
     * @param protect Protect
     */
    private void submitterMethod(Protect protect) {
        if (StringUtils.isEmpty(protect.getSubmitter())) {
            // 提交人
            Long projectId = protect.getProjectId();
            // 登录名
            String userName = SecurityUserHolder.getUserName();
            Long userId = getLoginUserId(userName);

            JSONObject jsonObject = new JSONObject(2);
            jsonObject.put("projectId", projectId);
            jsonObject.put("userId", userId);
            try {
                // 项目内 成员名称
                userName = HttpUtils.sendGet(ConstantsDevice.FIND_ORG_USER_NAME_URL, "json=" + jsonObject.toJSONString());
            } catch (Exception e) {
                userName = "/";
            }
            protect.setSubmitter(userName);
        }
    }

    /**
     * c1中没有，c2中有的元素
     *
     * @param c1 c1
     * @param c2 c2
     * @return List<T>
     */
    private static <T> List<T> diff(List<T> c1, List<T> c2) {
        if (c1 != null && c1.size() != 0 && c2 != null && c2.size() != 0) {
            List<T> difference = new ArrayList();
            Iterator i$ = c1.iterator();

            while(i$.hasNext()) {
                T item = (T) i$.next();
                if (!c2.contains(item)) {
                    difference.add(item);
                }
            }

            return difference;
        } else {
            return c1;
        }
    }

    /**
     *
     * @param c1 List
     * @param c2 List
     * @param sj StringJoiner
     */
    private void diffFireEvent(List<FireAlarmEvent> c1, List<FireAlarmEvent> c2, StringJoiner sj) {
        if (c1 != null && c1.size() != 0 && c2 != null && c2.size() != 0) {

            for (FireAlarmEvent f: c1) {
                for (FireAlarmEvent e: c2) {
                    if (f.getAlarmPosition().equals(e.getAlarmPosition())){
                        if (!f.getRecoverStatus().equals(e.getRecoverStatus()) || !f.getCreateTime().equals(e.getCreateTime())){
                            sj.add(e.getAlarmPosition());
                            // 结束内循环
                            break;
                        }
                    }
                }
            }
        }
    }

}
