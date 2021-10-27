package com.xjt.cloud.sys.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.sys.core.dao.sys.ExceptionLogDao;
import com.xjt.cloud.sys.core.entity.ExceptionLog;
import com.xjt.cloud.sys.core.service.service.ExceptionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @ClassName ExceptionLogServiceImpl
 * @Description
 * @Author wangzhiwen
 * @Date 2020/9/14 9:36
 **/
@Service
public class ExceptionLogServiceImpl extends AbstractService implements ExceptionLogService {

    @Autowired
    private ExceptionLogDao exceptionLogDao;
    /**
     * @param json
     * @return Data
     * @Description 保存错误信息
     * @author wangzhiwen
     * @date 2020/9/14 9:48
     */
    @Override
    public Data saveExceptionLog(String json){
        ExceptionLog exceptionLog = JSONObject.parseObject(json,ExceptionLog.class);
        String parameters = exceptionLog.getParameters();
        if (StringUtils.isNotEmpty(parameters) && parameters.length() > 2900){
            exceptionLog.setParameters(parameters.substring(0,2900));
        }
        String errLog = exceptionLog.getErrLog();
        if (StringUtils.isNotEmpty(errLog) && errLog.length() > 4900){
            exceptionLog.setErrLog(parameters.substring(0,4900));
        }
        return asseData(exceptionLogDao.saveExceptionLog(exceptionLog));
    }
}
