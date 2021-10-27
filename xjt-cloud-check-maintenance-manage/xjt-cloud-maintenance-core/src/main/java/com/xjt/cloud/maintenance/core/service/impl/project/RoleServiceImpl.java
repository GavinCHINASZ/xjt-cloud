package com.xjt.cloud.maintenance.core.service.impl.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.HttpUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import com.xjt.cloud.maintenance.core.dao.project.RoleDao;
import com.xjt.cloud.maintenance.core.entity.project.*;
import com.xjt.cloud.maintenance.core.service.service.project.*;
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
        List<Role> roles =  projectRoleDao.findByProjectRoleList(entity);
        if(roles.size()>0){
            return asseData(600,"角色名称已存在");
        }
        if(entity.getRoleType() == null){
            entity.setRoleType(0);
        }
        String userName = SecurityUserHolder.getUserName();
        entity.setSourceType(2);
        Long userId = getLoginUserId(userName);
        entity.setCreateUserId(userId);
        entity.setCreateUserName(userName);
        entity.setIsAdmin(2);
        entity.setIsOrdinary(2);
        projectRoleDao.addProjectRole(entity);
        securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_ADD_PROJECT_ROLE", SecurityUserHolder.getUserName(), entity, null,entity.getSourceId(),2);
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
        Role oldRole = projectRoleDao.findByProjectRole(entity);
        projectRoleDao.updateProjectRole(entity);
        securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_PROJECT_ROLE", SecurityUserHolder.getUserName(), entity,oldRole,entity.getSourceId(),2);
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
        List<Role> roleList =projectRoleDao.findByProjectRoleId(projectRole.getIds());
        //删除角色下的成员信息

        orgUserRoleService.deleteRoleIds(projectRole.getIds());
        //删除角色
        projectRoleDao.deleteProjectRole(projectRole.getIds());
        for (Role role: roleList ) {
            securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_DEL_PROJECT_ROLE", SecurityUserHolder.getUserName(), role,null,role.getSourceId(),2);
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
        List<Permission> permissions = permissionService.findByPermission(permission);
        for (Permission entity : permissions) {
            //boolean isPermission = rolePermissionService.findByRolePermission(projectRole.getId(), entity.getId());
            //entity.setPermission(isPermission);
            //子权限
            Permission temp = new Permission();
            temp.setParentId(entity.getId());
            List<Permission> subPermissions = permissionService.findByPermissionParentId(temp);
            for (Permission subPermission : subPermissions) {
                boolean isSubPermission = rolePermissionService.findByRolePermission(projectRole.getId(), subPermission.getId());
                subPermission.setPermission(isSubPermission);
            }
            entity.setChild(subPermissions);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("permissions", permissions);
        OrgUserRole orgUserRole = new OrgUserRole() ;
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
        List<Role> list = projectRoleDao.findByProjectRoleList(entity);
        return asseData(list);
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
        Long projectId = role.getSourceId();//项目ID
        List<Long> orgUserIds = role.getOrgUserIds();//平台下用户ID
        List<Long> permissionIds = role.getPermissionIds();//权限Id
        role.setSourceType(2);
        if (orgUserIds.size() != 0) {
            //该角色的新项目成员
            List<OrgUser> orgUsers = orgUserService.findByOrgUserIdList(orgUserIds);
            //旧角色的项目成员
            OrgUser orgUs = new OrgUser();
            orgUs.setRoleId(role.getId());
            List<OrgUser> oldOrgUsers = orgUserService.findByRoleOrgUserList(orgUs);
            if(role.getIsOrdinary() != null && role.getIsOrdinary() == 1 ){//普通成员只能添加用户不能取消用户普通权限
                Set<OrgUser> set = new HashSet<>();
                set.addAll(orgUsers);
                set.addAll(oldOrgUsers);
                orgUsers.clear();
                orgUsers.addAll(set);
            }
            for (OrgUser orgUser:orgUsers) {
                orgUser.setRoleName(role.getRoleName());
            }
            orgUserRoleService.saveProjectUserOrgRole(orgUsers, role,projectId);
            //得到新增的成员
            orgUserLog(role,orgUsers,oldOrgUsers,projectId);
        }
        if (permissionIds.size() != 0) {
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
        //添加项目管理员
        Role role = new Role();
        role.setIsAdmin(1);
        role.setRoleName("公司管理员");
        role.setSourceId(projectId);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        role.setCreateUserId(userId);
        role.setCreateUserName(userName);
        role.setIsOrdinary(2);
        role.setSourceType(2);
        role.setRoleType(1);
        //添加角色信息
        projectRoleDao.addProjectRole(role);

        Role ordinaryRole = new Role();
        //授权给普通成员
        List<Long> ordinaryRoleIds =  new ArrayList<>();
        ordinaryRoleIds.add(170L);//手机执行权限
        ordinaryRoleIds.add(167L);//检测项目管理
        ordinaryRole.setRoleName("检测员");
        ordinaryRole.setSourceId(projectId);
        ordinaryRole.setIsOrdinary(1);
        ordinaryRole.setSourceType(2);
        ordinaryRole.setIsAdmin(2);
        ordinaryRole.setCreateUserId(userId);
        ordinaryRole.setCreateUserName(userName);
        ordinaryRole.setRoleType(4);

        //授权给维保人员
        ordinaryRoleIds.add(170L);//手机执行权限
        ordinaryRoleIds.add(167L);//检测项目管理
        ordinaryRole.setRoleName("维保人员");
        ordinaryRole.setSourceId(projectId);
        ordinaryRole.setIsOrdinary(1);
        ordinaryRole.setSourceType(2);
        ordinaryRole.setIsAdmin(2);
        ordinaryRole.setCreateUserId(userId);
        ordinaryRole.setCreateUserName(userName);
        ordinaryRole.setRoleType(4);

        //添加角色信息
        projectRoleDao.addProjectRole(ordinaryRole);

        Role ordinaryManageRole = new Role();
        List<Long> ordinaryManageRoleIds =  new ArrayList<>();
        ordinaryManageRoleIds.add(73L);//查看权限
        ordinaryManageRoleIds.add(71L);//系统管理
        ordinaryManageRoleIds.add(167L);//检测项目管理
        ordinaryManageRoleIds.add(168L);//后台编辑权限
        ordinaryManageRoleIds.add(169L);//后台查看权限
        ordinaryManageRoleIds.add(170L);//手机执行权限
        ordinaryManageRole.setRoleName("项目管理员");
        ordinaryManageRole.setSourceId(projectId);
        ordinaryManageRole.setIsOrdinary(1);
        ordinaryManageRole.setSourceType(2);
        ordinaryManageRole.setIsAdmin(2);
        ordinaryManageRole.setRoleType(2);
        ordinaryManageRole.setCreateUserId(userId);
        ordinaryManageRole.setCreateUserName(userName);
        projectRoleDao.addProjectRole(ordinaryManageRole);

        ordinaryManageRoleIds.add(73L);//查看权限
        ordinaryManageRoleIds.add(71L);//系统管理
        ordinaryManageRoleIds.add(167L);//检测项目管理
        ordinaryManageRoleIds.add(168L);//后台编辑权限
        ordinaryManageRoleIds.add(169L);//后台查看权限
        ordinaryManageRoleIds.add(170L);//手机执行权限
        ordinaryManageRole.setRoleName("项目负责人");
        ordinaryManageRole.setSourceId(projectId);
        ordinaryManageRole.setIsOrdinary(1);
        ordinaryManageRole.setSourceType(2);
        ordinaryManageRole.setIsAdmin(2);
        ordinaryManageRole.setRoleType(2);
        ordinaryManageRole.setCreateUserId(userId);
        ordinaryManageRole.setCreateUserName(userName);
        projectRoleDao.addProjectRole(ordinaryManageRole);

        Role ordinaryEngineerRole = new Role();
        List<Long> ordinaryEngineerRoleIds =  new ArrayList();
        ordinaryEngineerRoleIds.add(167L);//检测项目管理
        ordinaryEngineerRoleIds.add(169L);//后台编辑权限
        ordinaryManageRoleIds.add(170L);//手机执行权限
        ordinaryEngineerRoleIds.add(172L);//签章
        ordinaryEngineerRole.setRoleName("工程师");
        ordinaryEngineerRole.setSourceId(projectId);
        ordinaryEngineerRole.setIsOrdinary(1);
        ordinaryEngineerRole.setSourceType(2);
        ordinaryEngineerRole.setRoleType(3);
        ordinaryEngineerRole.setIsAdmin(2);
        ordinaryEngineerRole.setCreateUserId(userId);
        ordinaryEngineerRole.setCreateUserName(userName);
        //添加角色信息
        projectRoleDao.addProjectRole(ordinaryEngineerRole);


        //给管理员授权角色信息
        rolePermissionService.addProjectAdminRolePermission(role);
        //给检测成员授权角色信息
        rolePermissionService.addProjectOrdinaryRolePermission(ordinaryRole,ordinaryRoleIds);
        rolePermissionService.addProjectOrdinaryRolePermission(ordinaryManageRole,ordinaryManageRoleIds);
        rolePermissionService.addProjectOrdinaryRolePermission(ordinaryEngineerRole,ordinaryEngineerRoleIds);


        //查询登录用户的当前平台成员对象
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
        role.setSourceId(projectId);
        role.setIsOrdinary(1);
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

