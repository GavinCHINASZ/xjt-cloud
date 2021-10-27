package com.xjt.cloud.project.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import com.xjt.cloud.project.core.dao.project.RoleDao;
import com.xjt.cloud.project.core.entity.*;
import com.xjt.cloud.project.core.service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName ProjectRolePermissionServiceImpl 项目角色
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Service
public class RoleServiceImpl extends AbstractService implements RoleService {

    @Autowired
    private RoleDao projectRoleDao;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private SecurityLogService securityLogService;

    @Autowired
    private OrgUserService orgUserService;

    @Autowired
    private OrgUserRoleService orgUserRoleService;

    @Value("${refresh.permission.init.url}")
    private String USER_PERMISSIONS_URL;

    /***@MethodName: addProjectRole 添加项目角色
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/19 14:04 
     **/
    @Override
    public Data addProjectRole(String json) {
        Role entity = JSONObject.parseObject(json, Role.class);
        List<Role> roles;
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            roles =  projectRoleDao.findByProjectRoleListCV5(entity);
        }else{
            roles =  projectRoleDao.findByProjectRoleList(entity);
        }
        if(roles.size()>0){
            return asseData(600,"角色名称已存在");
        }
        String userName = SecurityUserHolder.getUserName();
        entity.setSourceType(2);
        Long userId = getLoginUserId(userName);
        entity.setCreateUserId(userId);
        entity.setCreateUserName(userName);
        entity.setIsAdmin(2);
        entity.setIsOrdinary(2);
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            projectRoleDao.addProjectRoleCV5(entity);
        }else {
            projectRoleDao.addProjectRole(entity);
        }
        securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_ADD_PROJECT_ROLE", SecurityUserHolder.getUserName(), entity, null,entity.getProjectId(),2);
        return Data.isSuccess();
    }

    /**
     * @MethodName: updateProjectRole 修改角色信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/19 14:41
     **/
    @Override
    public Data updateProjectRole(String json) {
        Role entity = JSONObject.parseObject(json, Role.class);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        entity.setUpdateUserId(userId);
        entity.setUpdateUserName(userName);
        Role oldRole;
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            oldRole = projectRoleDao.findByProjectRoleCV5(entity);
        }else{
            oldRole = projectRoleDao.findByProjectRole(entity);
        }
        projectRoleDao.updateProjectRole(entity);
        securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_PROJECT_ROLE", SecurityUserHolder.getUserName(), entity,oldRole,entity.getProjectId(),2);
        return Data.isSuccess();
    }

    /**
     * @MethodName: deleteProjectRole 删除角色信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/19 14:41
     **/
    @Override
    public Data deleteProjectRole(String json) {
        Role projectRole = JSONObject.parseObject(json, Role.class);
        List<Role> roleList;
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            roleList =projectRoleDao.findByProjectRoleIdCV5(projectRole.getIds());
        }else{
            roleList =projectRoleDao.findByProjectRoleId(projectRole.getIds());
        }
        //删除角色下的成员信息

        orgUserRoleService.deleteRoleIds(projectRole.getIds());
        //删除角色
        projectRoleDao.deleteProjectRole(projectRole.getIds());
        for (Role role: roleList ) {
            securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_DEL_PROJECT_ROLE", SecurityUserHolder.getUserName(), role,null,role.getProjectId(),2);
        }
        return Data.isSuccess();
    }

    /**
     * @MethodName: findProjectRole 查询项目角色信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/19 15:18
     **/
    @Override
    public Data findByProjectRole(String json) {
        Role projectRole = JSONObject.parseObject(json, Role.class);
        //查询该角色在项目里是否有菜单功能权限
        Permission permission = new Permission();
        permission.setPerType(2);//项目权限
        permission.setPermissionType(2);//菜单功能
        permission.setProjectId(projectRole.getProjectId());
        List<Permission> permissions = permissionService.findByPermission(permission);
        for (Permission entity : permissions) {
            //boolean isPermission = rolePermissionService.findByRolePermission(projectRole.getId(), entity.getId());
            //entity.setPermission(isPermission);
            //子权限
            Permission temp = new Permission();
            temp.setParentId(entity.getId());
            temp.setRoleId(projectRole.getId());
            temp.setProjectId(projectRole.getProjectId());
            List<Permission> subPermissions = permissionService.findByPermissionParentId(temp);
            /*for (Permission subPermission : subPermissions) {
                boolean isSubPermission = rolePermissionService.findByRolePermission(projectRole.getId(), subPermission.getId());
                subPermission.setPermission(isSubPermission);
            }*/
            entity.setChild(subPermissions);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("permissions", permissions);
        OrgUserRole  orgUserRole = new OrgUserRole() ;
        orgUserRole.setProjectId(projectRole.getProjectId());
        orgUserRole.setRoleId(projectRole.getId());
        List<OrgUserRole> orgUserRoles = orgUserRoleService.findByRoleOrgUser(orgUserRole);
        result.put("orgUserRoles",orgUserRoles);
        return asseData(result);
    }

    /**
     * @MethodName: findProjectRoleList 查询角色列表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/23 14:32
     **/
    @Override
    public Data findByProjectRoleList(String json) {
        Role entity = JSONObject.parseObject(json, Role.class);
        List<Role> list;
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            list = projectRoleDao.findByProjectRoleListCV5(entity);
            return asseData(list);
        }
        list = projectRoleDao.findByProjectRoleList(entity);
        JSONArray roleArray = new JSONArray();
        if (CollectionUtils.isNotEmpty(list)) {
            JSONArray menuArray = new JSONArray();
            Role role;
            JSONObject menuObject = new JSONObject();
            JSONObject roleObject = new JSONObject();
            List<String> permissionList = new ArrayList<>();
            int size = list.size();
            for (int i = 0;i < size;i++){
                role = list.get(i);
                permissionList.add(role.getPermissionName());
                if(!menuObject.containsKey("menuName")){
                    menuObject.put("menuName",role.getMenuName());
                }
                if (!roleObject.containsKey("roleName")){
                    roleObject.put("roleName",role.getRoleName());
                    roleObject.put("roleId",role.getId());
                }

                if (i < size - 1 && !role.getRoleName().equals(list.get(i + 1).getRoleName())){//判断是否是同一角色
                    menuObject.put("permissionList",permissionList);
                    menuArray.add(menuObject);
                    permissionList = new ArrayList();
                    menuObject = new JSONObject();

                    roleObject.put("menuList",menuArray);
                    roleArray.add(roleObject);
                    menuArray = new JSONArray();
                    roleObject = new JSONObject();
                }else if(i < size - 1 && !role.getMenuName().equals(list.get(i + 1).getMenuName())){//判断是否是同一菜单
                    menuObject.put("permissionList",permissionList);
                    menuArray.add(menuObject);
                    permissionList = new ArrayList();
                    menuObject = new JSONObject();
                }else if(i == size - 1){
                    menuObject.put("permissionList",permissionList);
                    menuArray.add(menuObject);
                    roleObject.put("menuList",menuArray);
                    roleArray.add(roleObject);
                    roleObject = new JSONObject();
                }


            }
        }
        return asseData(roleArray);
    }

    /**
     * @MethodName: addProjectMemberAndPermission 角色添加成员及授权
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/24 10:45
     **/
    @Override
    public Data addProjectOrgUserAndPermission(String json) {
        Role role = JSONObject.parseObject(json, Role.class);
        Long projectId = Constants.COMPATIBLE_VERSION.equals("5.0") ? role.getSourceId() : role.getProjectId();//项目ID
        List<Long> orgUserIds = role.getOrgUserIds();//平台下用户ID
        List<Long> permissionIds = role.getPermissionIds();//权限Id
        role.setSourceType(2);
        List<OrgUser> orgUsers = new ArrayList<>();
        if (orgUserIds!=null && orgUserIds.size() != 0) {
            //该角色的新项目成员
            orgUsers = orgUserService.findByOrgUserIdList(orgUserIds);
        }
        //旧角色的项目成员
        OrgUser orgUs = new OrgUser();
        orgUs.setRoleId(role.getId());
        List<OrgUser> oldOrgUsers = orgUserService.findByRoleOrgUserList(orgUs);
        /*if(role.getIsOrdinary() != null && role.getIsOrdinary() == 1 ){//普通成员只能添加用户不能取消用户普通权限
            Set<OrgUser> set = new HashSet<>();
            set.addAll(orgUsers);
            set.addAll(oldOrgUsers);
            orgUsers.clear();
            orgUsers.addAll(set);
        }*/
        for (OrgUser orgUser:orgUsers) {
            orgUser.setRoleName(role.getRoleName());
        }
        orgUserRoleService.saveProjectUserOrgRole(orgUsers, role,projectId);
        //得到新增的成员
        orgUserLog(role,orgUsers,oldOrgUsers,projectId);

        if (permissionIds !=null && permissionIds.size() != 0) {
            //角色授予权限
            Permission permission = new Permission();
            permission.setIds(permissionIds);
            permission.setPermissionType(3);
            permission.setPerType(2);
            List<Permission> permissions = permissionService.findByPermission(permission);
            permission.setRoleId(role.getId());
            permission.setProjectId(projectId);
            permission.setIds(null);
            List<Permission> oldPermissions = permissionService.findByPermission(permission);
            rolePermissionService.addProjectRolePermission(permissionIds, role);
            rolePermissionLog(oldPermissions,permissions,projectId,role);

        }
        User user = new User();
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        user.setLoginName(userName);
        user.setId(userId);
        HttpUtils.httpGet(USER_PERMISSIONS_URL + StringUtils.urlEncode(JSON.toJSONString(user)));
        //删除用户项目所属权限
        String key = Constants.ORG_USER_PROJECT_PERMISSION_CACHE_KEY + "_" + user.getId() + "_" + projectId;
        redisUtils.dels(key);
        return Data.isSuccess();
    }

    /**@MethodName: rolePermissionLog 角色授权权限日志
     * @Description:
     * @Param: []
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/8 15:45
     *
     * @param oldPermissions
     * @param permissions
     * @param projectId*/
    private void rolePermissionLog(List<Permission> oldPermissions, List<Permission> permissions, Long projectId,Role role) {
        String permissionName = "";
        List<Permission> permissionsList = new ArrayList<>();
        permissionsList.addAll(permissions);
        int temp = 0 ;
        if(permissions.size()!=0){
            permissions.removeAll(oldPermissions);
            if(permissions.size()!=0) {
                for (Permission p: permissions) {
                    temp++;
                    permissionName+=p.getPermissionName();
                    if(temp!=permissions.size()){
                        permissionName +=  "、";
                    }
                }
                role.setPermissionName(permissionName);
                securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_ADD_ROLE_PERMISSION", SecurityUserHolder.getUserName(), role, null,projectId,2);
            }
        }
        permissionName="";
        if(oldPermissions.size()!=0){
            oldPermissions.removeAll(permissionsList);
            if(oldPermissions.size()!=0) {
                for (Permission p: oldPermissions) {
                    temp++;
                    permissionName+=p.getPermissionName();
                    if(temp!=oldPermissions.size()){
                        permissionName +=  "、";
                    }
                }
                role.setPermissionName(permissionName);
                securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_DEL_ROLE_PERMISSION", SecurityUserHolder.getUserName(), role, null,projectId,2);
            }
        }
    }

    /**@MethodName: orgUserLog 生成成员授权日志
     * @Description: 
     * @Param: [role, orgUsers, oldOrgUsers]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/8 15:33 
     **/
    private void orgUserLog(Role role ,List<OrgUser> orgUsers,List<OrgUser> oldOrgUsers,Long projectId) {
        OrgUserRole our = new OrgUserRole();
        our.setRoleName(role.getRoleName());
        String userName = "";
        List<OrgUser> orgUserList =  new ArrayList<OrgUser>();
        orgUserList.addAll(orgUsers);
        int temp = 0;
        if(orgUsers.size()>0){
            orgUsers.removeAll(oldOrgUsers);
            if(orgUsers.size()>0){
                for (OrgUser ou: orgUsers) {
                    temp++;
                    userName+=ou.getUserName();
                    if(temp!=orgUsers.size()){
                        userName +=  "、";
                    }
                }
                our.setName(userName);
                securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_ADD_PERMISSION", SecurityUserHolder.getUserName(), our, null,projectId,2);
            }
        }
        if(oldOrgUsers.size()>0){
            temp = 0;
            oldOrgUsers.removeAll(orgUserList);
            //得到先删除的成员
            if(oldOrgUsers.size()>0){
                userName = "";
                for (OrgUser ou: oldOrgUsers) {
                    userName += ou.getUserName();
                    if(temp!=orgUsers.size()) {
                        userName +=  "、";
                    }
                }
                our.setName(userName);
                securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_DEL_PERMISSION", SecurityUserHolder.getUserName(), our, null,projectId,2);
                oldOrgUsers.remove(orgUsers);
            }
        }
    }


    /**
     * @MethodName: addProjectRoleIsAdmin 添加项目管理员
     * @Description:
     * @Param: [projectId]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/8/2 14:05
     **/
    @Override
    public Data addProjectRoleAdmin(Long projectId) {
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            return addProjectRoleAdminCV5(projectId);
        }
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        Role role = new Role();
        role.setSuperAdmin(2);
        role = projectRoleDao.findByProjectRole(role);
        // 查询登录用户的当前平台成员对象
        OrgUser orgUser = new OrgUser();
        orgUser.setUserId(userId);
        orgUser.setProjectId(projectId);
        orgUser.setOrgId(0L);
        orgUser.setRoleName(role.getRoleName());
        orgUser.setUserName(userName);
        orgUserService.saveOrgUser(orgUser);

        //角色添加成员
        List <OrgUser> orgUsers = new ArrayList<>();
        orgUsers.add(orgUser);
        orgUserRoleService.saveProjectUserOrgRole(orgUsers, role,projectId);
        return Data.isSuccess();
    }

    private Data addProjectRoleAdminCV5(Long projectId) {
        //添加项目管理员
        Role role = new Role();
        role.setIsAdmin(1);
        role.setRoleName("项目管理员");
        role.setSourceId(projectId);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        role.setCreateUserId(userId);
        role.setCreateUserName(userName);
        role.setIsOrdinary(2);
        role.setSourceType(2);

        //添加角色信息
        projectRoleDao.addProjectRoleCV5(role);

        Role ordinaryRole = new Role();
        ordinaryRole.setRoleName("普通成员");
        ordinaryRole.setSourceId(projectId);
        ordinaryRole.setIsOrdinary(1);
        ordinaryRole.setSourceType(2);
        ordinaryRole.setIsAdmin(2);
        ordinaryRole.setCreateUserId(userId);
        ordinaryRole.setCreateUserName(userName);
        //添加角色信息
        projectRoleDao.addProjectRoleCV5(ordinaryRole);

        //给管理员授权角色信息
        rolePermissionService.addProjectAdminRolePermission(role);
        //给普通成员授权角色信息
        rolePermissionService.addProjectOrdinaryRolePermission(ordinaryRole);

        // 查询登录用户的当前平台成员对象
        OrgUser orgUser = new OrgUser();
        orgUser.setUserId(userId);
        orgUser.setProjectId(projectId);
        orgUser.setOrgId(0L);
        orgUser.setRoleName(role.getRoleName());
        orgUser.setUserName(userName);
        orgUserService.saveOrgUser(orgUser);

        //角色添加成员
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        List <OrgUser> orgUsers = new ArrayList<>();
        orgUsers.add(orgUser);
        orgUserRoleService.saveProjectUserOrgRole(orgUsers, role,projectId);
        return Data.isSuccess();
    }

    /**
     *
     * 功能描述:以角色名称查询角色信息是否存在
     *
     * @param sql
     * @param roleList
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/6 10:44
     */
    @Override
    public Data checkUserListRole(String sql, List<Role> roleList){
        //以角色名称查询角色信息
        String[] strs = sql.split(",");
        if (roleList.size() < strs.length) {//判断是否有不存在的角色
            List<String> roles = new ArrayList<>();
            boolean isExist;
            for (String str:strs){
                str = str.trim();
                isExist = false;
                for (Role role:roleList){
                    if (str.equals(role.getRoleName())){
                        isExist = true;
                        break;
                    }
                }
                if (!isExist){
                    roles.add(str);
                }
            }
            return asseData(Constants.FAIL_CODE, roles, ServiceErrCode.NOTFOUND_RESULT_ERR.getMsg());
        }
        return Data.isSuccess();
    }

    /**@MethodName: findByRoleOrgUserTree 查询角色下成员树结构
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/20 14:18 
     **/
    @Override
    public Data findByRoleOrgUserTree(String json) {
        OrgUserRole orgUserRole = JSONObject.parseObject(json, OrgUserRole.class);
        List<OrgUserRole> result = orgUserRoleService.findByRoleOrgUserTree(orgUserRole);
        return asseData(result);
    }

    /**@MethodName: findByProjectOrdinaryRole  查询项目普通角色
     * @Description: 
     * @Param: [projectId]
     * @Return: com.xjt.cloud.project.core.entity.Role
     * @Author: zhangZaiFa
     * @Date:2019/9/26 14:43 
     **/
    @Override
    public Role findByProjectOrdinaryRole(Long projectId) {
        Role role = new Role();
        role.setSuperAdmin(3);
        role.setSourceId(projectId);
        role.setIsOrdinary(1);
        if(Constants.COMPATIBLE_VERSION.equals("5.0")) {
            return projectRoleDao.findByProjectRoleCV5(role);
        }
        return projectRoleDao.findByProjectRole(role);
    }

    /**@MethodName: findByProjectRoleUserIdListInit 查询项目指定权限的UserId
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/15 10:31
     **/
    @Override
    public Data findByProjectPermissionUserIdList(String json) {
        return orgUserRoleService.findByProjectPermissionUserIdList(json);
    }
}

