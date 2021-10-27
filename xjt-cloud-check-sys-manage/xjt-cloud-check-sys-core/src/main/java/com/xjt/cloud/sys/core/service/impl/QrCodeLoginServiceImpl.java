package com.xjt.cloud.sys.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.ImgUtils;
import com.xjt.cloud.sys.core.common.utils.ConstantsClient;
import com.xjt.cloud.sys.core.dao.sys.UserDao;
import com.xjt.cloud.sys.core.entity.User;
import com.xjt.cloud.sys.core.service.service.QrCodeLoginService;
import com.xjt.cloud.sys.core.service.service.LoginService;
import com.xjt.cloud.sys.core.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/26 09:42
 * @Description: 二维码登录
 */
@Service
public class QrCodeLoginServiceImpl extends AbstractService implements QrCodeLoginService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;
    /**
     *
     * 功能描述:生成二维码
     *
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/26 9:58
     */
    @Override
    public Data generateQRCode(){
        String qrCodeKey = "key_" + System.nanoTime();
        redisUtils.set(qrCodeKey,  "",Constants.TEN_TIME_SECONDS);
        String content = ConstantsClient.QR_CODE_UP_LOGIN_INFO_URL + qrCodeKey;
        String imgStr = ImgUtils.generateQRCode(content, ConstantsClient.LOGIN_LOGO_PATH);
        Data data = new Data();
        data.setStatus(Constants.SUCCESS_CODE);
        data.setMsg(qrCodeKey);
        data.setObject(imgStr);
        return data;
    }

    /**
     *
     * 功能描述: app扫描上传用户登录信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/26 14:17
     */
    @Override
    public Data upQrCodeLoginInfo(String json){
        JSONObject jsonObj = JSONObject.parseObject(json);
        String qrCodeKey = jsonObj.getString("qrCodeKey");
        String loginName = SecurityUserHolder.getUserName();
        Object object = redisUtils.get(qrCodeKey);
        if (null != object && "".equals(object.toString())) {
            User user = new User();
            user.setLoginName(loginName);
            user = userDao.findLoginUser(user);
            if (null != user) {
                redisUtils.set(qrCodeKey, loginName, Constants.TEN_TIME_SECONDS);
                return Data.isSuccess();
            }else {
                return asseData(ServiceErrCode.NOT_URL_ERR);
            }
        }else{
            return asseData(Constants.FAIL_CODE, "二维码编号不存在");
        }
    }

    /**
     *
     * 功能描述:二维码登录，由pc页面轮循
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/26 14:18
     */
    @Override
    public Data qrCodeLogin(String json,String cloudType){
        JSONObject jsonObj = JSONObject.parseObject(json);
        String qrCodeKey = jsonObj.getString("qrCodeKey");
        Object object = redisUtils.get(qrCodeKey);
        if (null != object) {
            if (!"".equals(object.toString())) {
                User user = userService.isUserExistByPhone(null, object.toString(),null, false);
                if (null != user) {
                    Data data = loginService.userLogin(user.getLoginName(), user.getPassword(),cloudType);
                    if (data.getStatus() == Constants.SUCCESS_CODE) {
                        redisUtils.del(qrCodeKey);
                    }
                    return data;
                }else{
                    return asseData(Constants.NOT_DATA_CODE, "登录用户不存在");
                }
            }else{
                long time = System.nanoTime();
                long oldTime = Long.parseLong(qrCodeKey.split("_")[1]);
                if ((time - oldTime) / 1000000000 > Constants.TEN_TIME_SECONDS) {//判断二维码时间是否大于10分钟
                    redisUtils.del(qrCodeKey);
                }else{
                    return asseData(Constants.NOT_DATA_CODE, "用户未登录");
                }
            }
        }

        return asseData(Constants.FAIL_CODE, "二维码编号不存在");
    }
}
