package com.xjt.cloud.task.core.service.impl.project;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.task.core.dao.project.RoleDao;
import com.xjt.cloud.task.core.dao.sys.UserDao;
import com.xjt.cloud.task.core.entity.project.OrgRoleUser;
import com.xjt.cloud.task.core.entity.project.Role;
import com.xjt.cloud.task.core.entity.User;
import com.xjt.cloud.task.core.service.service.project.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RoleServiceImpl
 * @Author dwt
 * @Date 2019-08-09 9:58
 * @Description 角色接口实现类
 * @Version 1.0
 */
@Service
public class RoleServiceImpl extends AbstractService implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao userDao;

    /**
     * @Author: dwt
     * @Date: 2019-08-09 9:54
     * @Param: java.lang.Long
     * @Return: 角色列表
     * @Description
     */
    @Override
    public Data findRoleByProjectId(String json) {
        OrgRoleUser orgRoleUser = JSONObject.parseObject(json, OrgRoleUser.class);
        List<Role> list;
        if(Constants.COMPATIBLE_VERSION.equals("5.0")) {
            list = roleDao.getRoleNameBySourceIdCV5(orgRoleUser.getProjectId());
        }else{
            list = roleDao.getRoleNameBySourceId(orgRoleUser.getProjectId());
        }
        List<User> userList;
        List<Role> listR = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Role r : list) {
                if(Constants.COMPATIBLE_VERSION.equals("5.0")) {
                    userList = userDao.findUserByRoleIdCV5(r.getId(), orgRoleUser.getUserName(), orgRoleUser.getOrgIds());
                }else{
                    userList = userDao.findUserByRoleId(r.getId(), orgRoleUser.getUserName(), orgRoleUser.getOrgIds(), orgRoleUser.getProjectId());
                }
                r.setUserList(userList);
                if (StringUtils.isNotEmpty(orgRoleUser.getUserName())) {
                    if (userList != null && userList.size() > 0) {
                        listR.add(r);
                    }
                }
            }
        }
        if (StringUtils.isNotEmpty(orgRoleUser.getUserName())) {
            return asseData(listR);
        }
        return asseData(list);
    }
}
