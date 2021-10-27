package com.xjt.cloud.sys.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.entity.EnterpriseWeChatUser;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.commons.utils.other.MetroUtils;
import com.xjt.cloud.sys.core.dao.sys.EnterpriseWeChatDao;
import com.xjt.cloud.sys.core.entity.User;
import com.xjt.cloud.sys.core.service.service.EnterpriseWeChatService;
import com.xjt.cloud.sys.core.service.service.LoginService;
import com.xjt.cloud.sys.core.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 企业微信管理
 *
 * @author wangzhiwen
 * @date 2020/3/12 09:43
 */
@Service
public class EnterpriseWeChatServiceImpl extends AbstractService implements EnterpriseWeChatService {
    @Autowired
    private EnterpriseWeChatDao enterpriseWeChatDao;
    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;

    /**
     * 功能描述: 企业微信授权回调接口
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2020/3/12 9:52
     */
    @Override
    public Data authorizeCallback(String json, String cloudType) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String code = jsonObject.getString("code");
        SysLog.error(" code=============================:" + code);
        EnterpriseWeChatUser enterpriseWeChatUser = MetroUtils.getEnterpriseWeChatUser(code);
        //判断token是否过期
        if (null != enterpriseWeChatUser && StringUtils.isNotEmpty(enterpriseWeChatUser.getErrCode()) && "42001".equals(enterpriseWeChatUser.getErrCode())) {
            getToken(true);
            enterpriseWeChatUser = MetroUtils.getEnterpriseWeChatUser(code);
        }
        if (null != enterpriseWeChatUser && StringUtils.isEmpty(enterpriseWeChatUser.getErrCode())) {
            //判断获取企业微信用户信息是否存功
            //以得到的微信用户id为账号,从平台用户表中查询用户信息
            User user = userService.isUserExistByPhone(null, enterpriseWeChatUser.getWeChatUserId(), null, false);
            if (null != user) {
                //判断是否存在微信用户id为账号的平台用户信息
                //判断微信信息与平台用户信息绑定表中是否存在
                EnterpriseWeChatUser oldEWCU = enterpriseWeChatDao.findEnterpriseWeChatUserByWeChatUserId(enterpriseWeChatUser);
                if (oldEWCU != null) {
                    //存在,修改绑定信息
                    oldEWCU.setUserId(user.getId());
                    enterpriseWeChatDao.modifyEnterpriseWeChatUser(oldEWCU);
                } else {//不存在,新增绑定信息
                    enterpriseWeChatUser.setUserId(user.getId());
                    enterpriseWeChatDao.saveEnterpriseWeChatUser(enterpriseWeChatUser);
                }
                return loginService.userLogin(user.getLoginName(), user.getPassword(), cloudType);
            } else {
                return asseData(ServiceErrCode.REQ_ERR.getCode(), "用户id在平台中不存在！");
            }
        }
        return asseData(ServiceErrCode.REQ_ERR.getCode(), enterpriseWeChatUser, "企业微信授权失败!");
    }

    /**
     * 功能描述: 从缓存中获取token
     *
     * @param isClear boolean
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2020/3/12 11:49
     */
    @Override
    public String getToken(boolean isClear) {
        return MetroUtils.getToken(isClear);
    }

    /**
     * 功能描述:获取企业微信权限签名
     *
     * @return String
     * @author wangzhiwen
     * @date 2020/4/3 14:14
     */
    @Override
    public String getJsapiTicket(boolean isClear, boolean isAgent) {
        return MetroUtils.getJsapiTicket(isClear,isAgent);
    }


    /**
     * 功能描述: 获取签名
     *
     * @param json 参数
     * @return JSONObject
     * @author wangzhiwen
     * @date 2020/4/3 14:39
     */
    @Override
    public JSONObject getSign(String json, boolean isAgent) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String url = jsonObject.getString("url");
        return MetroUtils.getSign(url,isAgent);
    }


    /**
     * 功能描述:以用户id数组得到用户登录账号
     *
     * @param userIds String[]
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2020/3/17 11:24
     */
    @Override
    public String getUserLoginNames(String[] userIds) {
        EnterpriseWeChatUser enterpriseWeChatUser = enterpriseWeChatDao.findUserLoginNames(userIds);
        return enterpriseWeChatUser == null ? "" : enterpriseWeChatUser.getUserNames();
    }
}
