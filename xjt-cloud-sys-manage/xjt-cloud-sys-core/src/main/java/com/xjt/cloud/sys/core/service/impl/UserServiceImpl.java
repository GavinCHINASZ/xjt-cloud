package com.xjt.cloud.sys.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.commons.utils.other.MetroUtils;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import com.xjt.cloud.sys.core.common.utils.ConstantsClient;
import com.xjt.cloud.sys.core.common.utils.ErrCode;
import com.xjt.cloud.sys.core.dao.sys.UserDao;
import com.xjt.cloud.sys.core.entity.User;
import com.xjt.cloud.sys.core.service.service.UserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wangzhiwen
 * @date 2019/8/29 11:41
 */
@Service
public class UserServiceImpl extends AbstractService implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private SecurityLogService securityLogService;

    /**
     * 功能描述:根据用户
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/07/10
     */
    @Override
    public Data getUser(String json){
        User user = JSONObject.parseObject(json, User.class);
        user = userDao.getUser(user);
        if (null == user){
            return Data.isFail();
        }
        redisUtils.del(user.getLoginName());
        redisUtils.set(user.getLoginName(), JSON.toJSONString(user), Constants.CACHE_CANCEL);
        redisUtils.del("userId" + user.getId());
        redisUtils.set("userId" + user.getId(), JSON.toJSONString(user), Constants.CACHE_CANCEL);
        return asseData(user);
    }

    /**
     * 功能描述:修改用户信息
     *
     * @param json 参数 数据
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/11/25 9:37
     */
    @Override
    public Data modifyUser(String json){
        User user = JSONObject.parseObject(json, User.class);
        User oldUser = userDao.getUser(user);
        int num = userDao.modifyUser(user);

        securityLogService.saveSecurityLog(ConstantsClient.SECURITY_LOG_TYPE_MODIFY_USER, SecurityUserHolder.getUserName(), user, oldUser,
                null, Constants.SOURCE_PROJECT);
        return asseData(num);
    }

    /**
     *
     * 功能描述:修改用户登录用户名
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/11/25 9:37
     */
    @Override
    public Data modifyUserLoginName(String json){
        User user = JSONObject.parseObject(json, User.class);
        User tempUser = new User();
        tempUser.setLoginName(user.getLoginName());
        tempUser = userDao.getUser(tempUser);
        if (null != tempUser){
            return asseData(ErrCode.NOT_USER.getCode(),ConstantsClient.USER_LOGIN_NAME_EXIST);
        }

        tempUser = new User();
        tempUser.setId(user.getId());
        tempUser = userDao.getUser(tempUser);

        int num = userDao.modifyUserLoginName(user);

        securityLogService.saveSecurityLog(ConstantsClient.SECURITY_LOG_TYPE_MODIFY_USER, SecurityUserHolder.getUserName(), user, tempUser,
                null, Constants.SOURCE_PROJECT);
        return asseData(num);
    }

    /**
     * 功能描述:修改用户信息发送验证码
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/11/25 9:37
     */
    @Override
    public Data modifyUserSendCaptcha(String json){
        User user = JSONObject.parseObject(json, User.class);

        // 生成验证码
        String captcha = StringUtils.generateRanNum(6);
        boolean b = false;

        //判断是否是地铁项目
        String isMetro = DictUtils.getProjectParameterConfigByKey(ConstantsClient.IS_METRO);
        if (StringUtils.isNotEmpty(isMetro) && "true".equals(isMetro)){
            user = userDao.getUser(user);
            if (user != null) {
                String msg = "【消检通】验证码：<font color=\"#579cf9\"> " + captcha + "</font> （10分钟内有效），该验证码用于消检通账号密码修改，请勿泄露。";
                b = MetroUtils.sendMsg(user.getId() + "", msg);
            }
        }else{
            // 发送验证码信息
            b = TaoBaoSendMsg.sendMsg(user.getPhone(),"{\"code\":\"" + captcha + "\"}");
        }
        if (!b){
            return asseData(ServiceErrCode.SERVER_ERR.getCode(), ConstantsClient.SEND_MSG_FAIL);
        }
        String key = "key_" + System.nanoTime();
        redisUtils.set(key, captcha, Constants.TEN_TIME_SECONDS);
        return asseData(key);
    }

    /**
     * 功能描述:修改用户手机号码
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/11/25 9:37
     */
    @Override
    public Data modifyUserPhone(String json){
        User user = JSONObject.parseObject(json, User.class);
        User tempUser = new User();
        tempUser.setPhone(user.getPhone());
        tempUser = userDao.getUser(tempUser);
        if (null != tempUser){
            return asseData(ErrCode.NOT_USER.getCode(),ConstantsClient.USER_PHONE_EXIST);
        }
        Data data = checkCaptcha(user.getKey(), user.getCaptcha());
        if (data.getStatus() != Constants.SUCCESS_CODE){
            return data;
        }
        tempUser = new User();
        tempUser.setId(user.getId());
        tempUser = userDao.getUser(tempUser);

        int num = userDao.modifyUserPhone(user);

        securityLogService.saveSecurityLog(ConstantsClient.SECURITY_LOG_TYPE_MODIFY_USER, SecurityUserHolder.getUserName(), user, tempUser,
                null, Constants.SOURCE_PROJECT);
        return asseData(num);
    }

    /**
     * 功能描述:修改用户密码
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/11/25 9:37
     */
    @Override
    public Data modifyUserPassword(String json){
        User user = JSONObject.parseObject(json, User.class);
        Data data = checkCaptcha(user.getKey(), user.getCaptcha());
        if (data.getStatus() != Constants.SUCCESS_CODE){
            return data;
        }
        //密码加密
        PasswordEncoder passwordEncoder = new StandardPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User tempUser = userDao.getUser(user);

        int num = userDao.modifyUserPassword(user);

        securityLogService.saveSecurityLog(ConstantsClient.SECURITY_LOG_TYPE_MODIFY_USER, null, user, tempUser,
                null, Constants.SOURCE_PROJECT);
        return asseData(num);
    }

    /**
     * 功能描述:  校验验证码
     *
     * @param key key
     * @param captcha 验证码
     * @return boolean
     * @author wangzhiwen
     * @date 2019/11/25 10:40
     */
    private Data checkCaptcha(String key, String captcha){
        String cacheCaptcha = redisUtils.getString(key);
        if (StringUtils.isNotEmpty(cacheCaptcha)){
            // 判断验证码是否正确
            if (!cacheCaptcha.equals(captcha)){
                return asseData(ServiceErrCode.REQ_PARAM_ERR.getCode(), ConstantsClient.CAPTCHA_FAIL);
            }

            long keyTime = Long.parseLong(key.split("_")[1]);
            long time = System.nanoTime();
            // 判断二维码时间是否大于10分钟
            if ((time - keyTime) / 1000000000 < Constants.TEN_TIME_SECONDS) {
                redisUtils.del(key);
                return Data.isSuccess();
            }else{
                return asseData(ServiceErrCode.REQ_PARAM_ERR.getCode(), ConstantsClient.CAPTCHA_EXPIRE);
            }
        }
        redisUtils.del(key);
        return asseData(ServiceErrCode.REQ_PARAM_ERR);
    }

    /**
     * 功能描述:事务处理测试
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/07/10
     */
    @Override
    public Data transactionTest(String json){
        // 必须使用代码调用
        ((UserServiceImpl) AopContext.currentProxy()).test1();
        return Data.isSuccess();
    }

    /**
     * 功能描述:事务测试方法　　指定回滚的异常　事务级别　　事务管理器（在数据源中配置）
     *
     * @author wangzhiwen
     * @date 2019/9/19 17:56
     */
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED, transactionManager = "dataSourceTransactionManager")
    public void test1(){
        userDao.saveTest();
        userDao.saveTest1();
        throw new RuntimeException("");
    }

    /**
     *
     * 功能描述:用户注册
     *
     * @param user 用户
     * @return int
     * @author wangzhiwen
     * @date 2019/8/1 9:56
     */
    @Override
    public int registerUser(User user){
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            // 生成随机字符串密码
            password = StringUtils.generateRanStr(6);
        }

        // 判断密码是否正确
        PasswordEncoder passwordEncoder = new StandardPasswordEncoder();
        password = passwordEncoder.encode(password);
        user.setPassword(password);
        String loginName = user.getLoginName();
        if (StringUtils.isEmpty(loginName) || loginName.equals(user.getPhone())){
            User temUser = new User();
            for(;;){
                // 生成随机登录名称
                loginName = StringUtils.generateRanStr(6);
                temUser.setLoginName(loginName);
                temUser.setPhone(loginName);
                if (null == userDao.findUserByLoginNameOrPhone(user)){
                    break;
                }
            }
            user.setLoginName(loginName);
        }
        user.setUserName(loginName);

        return userDao.registerUser(user);
    }

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new StandardPasswordEncoder();
        String password = passwordEncoder.encode("Sqy123");
        System.out.println(password);
    }

    /**
     * 功能描述: 以手机号码查询是否存在该手机号码或登录名的用户
     *
     * @param phone 手机号
     * @param loginName 登录用户名
     * @param isRegister 如不存在是否要注册
     * @param password 密码
     * @return User
     * @author wangzhiwen
     * @date 2019/9/4 16:58
     */
    @Override
    public User isUserExistByPhone(String phone, String loginName,String password, boolean isRegister){
        User user = new User();
        user.setPhone(StringUtils.isEmpty(phone) ? loginName : phone);

        user.setLoginName(StringUtils.isEmpty(loginName) ? phone : loginName);
        // 以手机号码查询是否存在该手机号码或登录名的用户
        user = userDao.findUserByLoginNameOrPhone(user);
        if (user == null) {
            if (isRegister) {
                user = new User();
                user.setPhone(phone);
                user.setLoginName(loginName);
                user.setPassword(password);
                //注册用户
                int num = registerUser(user);
                user.setRegisterStatus(true);
                //判断是否添加成功
                if (0 == num) {
                    return null;
                }
            }
        }/*else if(0 == user.getStatus()){
            // 如果用户是未激活状态，则改成激活
            userDao.updateUserStatus(user);
        }*/
        return user;
    }

    /**
     * 功能描述:查询 PC主题颜色
     *
     * @author huanggc
     * @date 2020/09/09
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findThemeColor() {
        String pcThemeColorStr = DictUtils.getDictPropertyByDictItemCodePropertyName(ConstantsClient.THEME_CONFIG, ConstantsClient.PC_THEME_COLOR,
                "itemDescription");
        return asseData(pcThemeColorStr);
    }

    /**
     * 以经伟度得到地址信息
     *
     * @param json 参数
     * @author wangzhiwen
     * @date 2020/10/9 14:44
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data getAddressByLngLat(String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        String location = jsonObject.getString("location");
        String url = "http://api.map.baidu.com/geocoder/v2/?ak=zZlVYw9mCu6i0xGuYs2iF0f785Fr7RhG&coordtype=bd09ll&callback=renderReverse&output=json&pois=1&location="
                + location;

        String res = HttpUtils.sendGet(url,null);
        return asseData(res);
    }
}