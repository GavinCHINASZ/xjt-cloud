package com.xjt.cloud.safety.core.service.impl.project;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.safety.core.dao.project.CheckProjectDao;
import com.xjt.cloud.safety.core.dao.project.CheckUserDao;
import com.xjt.cloud.safety.core.entity.project.SignLog;
import com.xjt.cloud.safety.core.common.ConstantsProjectMsg;
import com.xjt.cloud.safety.core.dao.project.SignLogDao;
import com.xjt.cloud.safety.core.entity.project.CheckProject;
import com.xjt.cloud.safety.core.entity.project.CheckUser;
import com.xjt.cloud.safety.core.service.service.project.SignLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签到
 *
 * @author dwt
 * @date 2020-04-12 14:14
 */
@Service
public class SignLogServiceImpl extends AbstractService implements SignLogService {

    @Autowired
    private SignLogDao signLogDao;
    @Autowired
    private CheckProjectDao checkProjectDao;
    @Autowired
    private CheckUserDao checkUserDao;

    /**
     * 保存检测员签到日志
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-04-12 14:08
     */
    @Override
    public Data saveSignLog(String json) {
        SignLog signLog = JSONObject.parseObject(json, SignLog.class);
        Long checkProjectId = signLog.getCheckProjectId();
        CheckUser checkUser = new CheckUser();
        checkUser.setUserId(signLog.getUserId());
        checkUser.setCheckProjectId(signLog.getCheckProjectId());
        checkUser = checkUserDao.findCheckUser(checkUser);

        // 根据要求屏蔽只有检测员才能签到
        //if(checkProjectId != null && checkProjectId != 0 && checkUser != null){
        if (checkUser != null) {
            signLog.setUserName(checkUser.getUserName());
            signLog.setUserId(checkUser.getId());
        } else {
            String createUserName = SecurityUserHolder.getUserName();
            // 用户ID
            Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
            signLog.setUserName(createUserName);
            signLog.setUserId(loginUserId);
        }

        CheckProject cp = new CheckProject();
        cp.setId(checkProjectId);
        cp = checkProjectDao.findByCheckProject(cp);
        if (cp != null) {
            signLog.setCompanyName(cp.getCheckProjectName());
            /*Integer c = signLogDao.saveSignLog(signLog);
            if(c > 0){
                return Data.isSuccess();
            }*/
            double lat1, lng1, lat2, lng2;
            lat1 = Double.parseDouble(signLog.getLat());
            lng1 = Double.parseDouble(signLog.getLng());
            lat2 = Double.parseDouble(cp.getLat());
            lng2 = Double.parseDouble(cp.getLng());
            Double s = getDistance(lat1, lng1, lat2, lng2);

            if (s <= ConstantsProjectMsg.SIGN_DISTANCE) {
                if (signLog.getSignType() == 1) {
                    signLog.setSignSucceed(1);
                } else {
                    signLog.setSignSucceed(3);
                }
            } else {
                if (signLog.getSignType() == 1) {
                    signLog.setSignSucceed(2);
                } else {
                    signLog.setSignSucceed(4);
                }
            }
            Integer n = signLogDao.saveSignLog(signLog);
            if (n > 0) {
                return Data.isSuccess();
            }
        }
        //}
        return Data.isFail();
    }

    /**
     * 查询项目检测签到日志
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-04-12 14:09
     */
    @Override
    public Data findSignLogByProjectId(String json) {
        SignLog signLog = JSONObject.parseObject(json, SignLog.class);
        signLog.setOrderStr("create_time");
        signLog.setOrderDesc(true);
        List<SignLog> signLogList = signLogDao.findSignLogByProjectId(signLog);
        Long totalCount = signLogDao.findSignLogCountByProjectId(signLog);

        Map<String, Object> map = new HashMap<>(2);
        map.put("totalCount", totalCount);
        map.put("signLogList", signLogList);
        return asseData(map);
    }

    /**
     * 查询检测员签到日志
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-04-12 14:11
     */
    @Override
    public Data findCheckUserSignLog(String json) {
        SignLog signLog = JSONObject.parseObject(json, SignLog.class);
        List<SignLog> logList = signLogDao.findCheckUserSignLog(signLog);
        return asseData(logList);
    }

    /**
     * 根据经纬度求地球表面两点间距离
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return 相差距离米
     */
    private double getDistance(double lat1, double lng1, double lat2, double lng2) {
        // 弧度转角度
        double radLat1 = lat1 * Math.PI / 180.0;
        // 弧度转角度
        double radLat2 = lat2 * Math.PI / 180.0;
        double a = radLat1 - radLat2;
        double b = (lng1 * Math.PI / 180.0) - (lng2 * Math.PI / 180.0);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378.135;
        s = Math.round(s * 1000);
        return s;
    }
}
