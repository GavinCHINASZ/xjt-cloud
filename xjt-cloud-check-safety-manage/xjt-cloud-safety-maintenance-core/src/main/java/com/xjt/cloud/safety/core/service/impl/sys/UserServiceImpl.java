package com.xjt.cloud.safety.core.service.impl.sys;

import com.alibaba.fastjson.JSON;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.safety.core.entity.project.User;
import com.xjt.cloud.safety.core.service.service.sys.UserService;
import com.xjt.cloud.safety.core.dao.sys.UserDao;
import com.xjt.cloud.safety.core.entity.project.OrgUser;
import com.xjt.cloud.safety.core.service.service.project.OrgUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/6 17:10
 * @Description:
 */
@Service
public class UserServiceImpl extends AbstractService implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrgUserService orgUserService;

    /**
     * 功能描述:新增成员:手动添加
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2019/5/27
     */
    @Override
    @Transient
    public Data saveUser(String json) {
        User user = JSON.parseObject(json, User.class);
        Long projectId = user.getProjectId();
        user.setLoginName(user.getPhone());
        user.setProjectId(projectId);
        User oldUser = userDao.findUserByLoginNameOrPhone(user);//以手机号码查询是否存在该手机号码或登录名的用户
        if (null == oldUser) {
            int num = registerUser(user);//注册用户
            if (num == 0) {
                return Data.isFail();
            }
        } else {
            user.setId(oldUser.getId());
        }

        // 添加用户部门关系
        OrgUser orgUser = new OrgUser();
        OrgUser oldOrgUser = new OrgUser();
        //oldOrgUser.setProjectId(projectId);
        oldOrgUser.setUserId(user.getId());
        oldOrgUser.setPhone(user.getPhone());
        orgUser.initOrgUser(user.getOrgId(), user.getId(), projectId, user.getUserName());
        orgUser.setCertificateLevel(user.getCertificateLevel());
        orgUser.setCertificateNumber(user.getCertificateNumber());
        oldOrgUser = orgUserService.findOrgUser(oldOrgUser);
        if (oldOrgUser != null) {
            return asseData(600, "用户已存在于其他企业");
        }
        int num = orgUserService.saveOrgUserRole(orgUser, projectId, user.getRoleIds());
        if (num >= 0) {
            userDao.updateUser(user);
            return asseData(user);
        }
        return Data.isFail();
    }

    /**
     * 功能描述:保存用户信息
     *
     * @param list
     * @param userNameStr
     * @return: java.util.List<com.xjt.cloud.project.core.entity.User>
     * @auther: wangzhiwen
     * @date: 2019/9/6 13:57
     */
    @Override
    public List<User> saveUser(List<User> list, String userNameStr) {
        List<User> phoneList = userDao.findUserByPhones(userNameStr);
        List<User> newList = new ArrayList<>();
        List<User> modifyList = new ArrayList<>();
        String password;//生成随机字符串密码
        PasswordEncoder passwordEncoder = new StandardPasswordEncoder();//判断密码是否正确
        String userName;
        for (User user : list) {//计算新增用户与完善修改用户id
            for (User phoneUser : phoneList) {
                if (user.getPhone().equals(phoneUser.getPhone())) {
                    if (null != phoneUser.getId()) {
                        user.setId(phoneUser.getId());
                        modifyList.add(user);
                    } else {
                        password = StringUtils.generateRanStr(6);//生成随机字符串密码
                        password = passwordEncoder.encode(password);
                        userName = StringUtils.generateRanStr(12);
                        user.setLoginName(userName);
                        user.setPassword(password);
                        newList.add(user);
                    }
                    break;
                }
            }
        }
        if (CollectionUtils.isNotEmpty(newList)) {
            userDao.saveUserList(newList);
        }
        modifyList.addAll(newList);
        return modifyList;
    }

    /**
     * 功能描述: 用户注册
     *
     * @param user
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/9/5 15:41
     */
    private int registerUser(User user) {
        String password = StringUtils.generateRanStr(6);//生成随机字符串密码
        PasswordEncoder passwordEncoder = new StandardPasswordEncoder();//判断密码是否正确
        password = passwordEncoder.encode(password);
        user.setPassword(password);
        return userDao.saveUser(user);
    }

    /**
     * @MethodName: findByUser 查询用户信息
     * @Description:
     * @Param: [user]
     * @Return: com.xjt.cloud.project.core.entity.User
     * @Author: zhangZaiFa
     * @Date:2019/9/29 13:57
     **/
    @Override
    public User findByUser(User user) {
        return userDao.findByUser(user);
    }

    /**
     * @MethodName: updateUserProject 修改用户项目信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/12 9:58
     **/
    @Override
    public Data updateUserProject(User user) {
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        user.setId(userId);
        userDao.updateUser(user);
        return Data.isSuccess();
    }
}
