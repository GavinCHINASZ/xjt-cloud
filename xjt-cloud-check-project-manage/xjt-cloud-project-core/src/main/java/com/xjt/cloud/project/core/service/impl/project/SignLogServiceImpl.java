package com.xjt.cloud.project.core.service.impl.project;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.dao.project.CheckProjectDao;
import com.xjt.cloud.project.core.dao.project.CheckUserDao;
import com.xjt.cloud.project.core.dao.project.SignLogDao;
import com.xjt.cloud.project.core.entity.project.CheckProject;
import com.xjt.cloud.project.core.entity.project.CheckUser;
import com.xjt.cloud.project.core.entity.project.SignLog;
import com.xjt.cloud.project.core.service.service.project.SignLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SignLogServiceImpl
 * @Author dwt
 * @Date 2020-04-12 14:14
 * @Version 1.0
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
     *@Author: dwt
     *@Date: 2020-04-12 14:08
     *@Param: json
     *@Return: Data
     *@Description 保存检测员签到日志
     */
    @Override
    public Data saveSignLog(String json) {
        SignLog signLog = JSONObject.parseObject(json,SignLog.class);
        Long checkProjectId = signLog.getCheckProjectId();
        CheckUser checkUser = new CheckUser();
        checkUser.setUserId(signLog.getUserId());
        checkUser.setCheckProjectId(signLog.getCheckProjectId());
        checkUser = checkUserDao.findCheckUser(checkUser);
        //根据要求屏蔽只有检测员才能签到
        //if(checkProjectId != null && checkProjectId != 0 && checkUser != null){
        if(checkUser != null){
            signLog.setUserName(checkUser.getUserName());
            signLog.setCheckUserId(checkUser.getId());
        }else{
            String createUserName = SecurityUserHolder.getUserName();
            Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());// 用户ID
            signLog.setUserName(createUserName);
            signLog.setCheckUserId(loginUserId);
        }
        CheckProject cp = new CheckProject();
        cp.setId(checkProjectId);
        cp = checkProjectDao.findByCheckProject(cp);
        if(cp != null){
            signLog.setCompanyName(cp.getCheckProjectName());
            Integer c = signLogDao.saveSignLog(signLog);
            if(c > 0){
                return Data.isSuccess();
            }
            /*double lat1,lng1,lat2,lng2;
            lat1 = Double.parseDouble(signLog.getLat());
            lng1 = Double.parseDouble(signLog.getLng());
            lat2 = Double.parseDouble(cp.getLat());
            lng2 = Double.parseDouble(cp.getLng());
            Double s = getDistance(lat1, lng1, lat2, lng2);
            if(s <= 1000){
                Integer c = signLogDao.saveSignLog(signLog);
                if(c > 0){
                    return Data.isSuccess();
                }
            }*/
        }
        //}
        return Data.isFail();
    }
    /**
     *@Author: dwt
     *@Date: 2020-04-12 14:09
     *@Param: json
     *@Return: Data
     *@Description 查询项目检测签到日志
     */
    @Override
    public Data findSignLogByProjectId(String json) {
        SignLog signLog = JSONObject.parseObject(json,SignLog.class);
        signLog.setOrderStr("create_time");
        signLog.setOrderDesc(true);
        List<SignLog> signLogList = signLogDao.findSignLogByProjectId(signLog);
        Long totalCount = signLogDao.findSignLogCountByProjectId(signLog);
        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("signLogList",signLogList);
        return asseData(map);
    }
    /**
     *@Author: dwt
     *@Date: 2020-04-12 14:11
     *@Param: json
     *@Return: Data
     *@Description 查询检测员签到日志
     */
    @Override
    public Data findCheckUserSignLog(String json) {
        SignLog signLog = JSONObject.parseObject(json,SignLog.class);
        List<SignLog> logList = signLogDao.findCheckUserSignLog(signLog);
        return asseData(logList);
    }

    /**
     * 根据经纬度求地球表面两点间距离
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    private double getDistance(double lat1, double lng1, double lat2, double lng2){
        double radLat1 = lat1 * Math.PI / 180.0;//弧度转角度
        double radLat2 = lat2 * Math.PI / 180.0;//弧度转角度
        double a = radLat1 - radLat2;
        double b = (lng1 * Math.PI / 180.0) - (lng2 * Math.PI / 180.0);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * 6378.135;
        s = Math.round(s * 1000);
        return s;
    }
}
