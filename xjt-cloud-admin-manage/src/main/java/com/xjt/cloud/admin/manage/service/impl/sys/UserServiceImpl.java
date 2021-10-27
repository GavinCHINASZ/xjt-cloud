package com.xjt.cloud.admin.manage.service.impl.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.common.utils.ErrCode;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.sys.UserDao;
import com.xjt.cloud.admin.manage.entity.sys.User;
import com.xjt.cloud.admin.manage.service.service.sys.UserService;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.*;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/8/29 11:41
 * @Description:
 */
@Service
public class UserServiceImpl extends AbstractAdminService implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 功能描述:根据用户
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2019/07/10
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
        return asseData(user);
    }

    /**
     *
     * 功能描述:分页查询用户信息列表
     *
     * @param ajaxPage
     * @param user
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/1/15 9:34
     */
    @Override
    public ScriptPage<User> findUserList(AjaxPage ajaxPage, User user){
        user = asseFindObj(user, ajaxPage);
        return asseScriptPage(userDao.findUserList(user), userDao.findUserListTotalCount(user));
    }

    /**
     *
     * 功能描述:修改用户信息
     *
     * @param user
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    @Override
    public Data modifyUser(User user){
        int num = userDao.modifyUser(user);
        return asseData(num);
    }

    /**
     *
     * 功能描述:修改用户信息
     *
     * @param user
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    @Override
    public Data saveUser(User user){
        User tem = userDao.getUser(user);
        if (tem == null){
            // 判断密码是否正确
            PasswordEncoder passwordEncoder = new StandardPasswordEncoder();
            String password = passwordEncoder.encode("Xjt123");
            user.setPassword(password);
            int num = userDao.saveUser(user);
            return asseData(num);
        }

        return asseData(ServiceErrCode.REQ_ERR.getCode(),"添加的用户已存在！");
    }

    /**
     *
     * 功能描述:修改用户密码
     *
     * @param user
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/27 16:15
     */
    @Override
    public Data modifyPassword(User user){
        String loginName = SecurityUserHolder.getUserName();
        User temUser = new User();
        temUser.setLoginName(loginName);
        temUser = userDao.findLoginUser(temUser);

        PasswordEncoder passwordEncoder = new StandardPasswordEncoder();//判断密码是否正确
        boolean b = passwordEncoder.matches(user.getOldPassword(), temUser.getPassword());
        if (!b){
            return asseData(ErrCode.PASSWORD_ERR.getCode(), "旧密码输入错误!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(temUser.getId());
        int num = userDao.modifyUser(user);
        return asseData(num);

    }

    /**
     *
     * 功能描述: 清除过期数据
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/12/2 14:31
     */
    public void clearData(){
        userDao.clearData();
    }

}
