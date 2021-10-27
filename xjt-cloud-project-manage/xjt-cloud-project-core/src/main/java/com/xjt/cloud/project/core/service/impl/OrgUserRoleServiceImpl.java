package com.xjt.cloud.project.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.dao.project.OrgUserDao;
import com.xjt.cloud.project.core.dao.project.OrgUserRoleDao;
import com.xjt.cloud.project.core.entity.OrgUser;
import com.xjt.cloud.project.core.entity.OrgUserRole;
import com.xjt.cloud.project.core.entity.Project;
import com.xjt.cloud.project.core.entity.Role;
import com.xjt.cloud.project.core.service.service.OrgUserRoleService;
import com.xjt.cloud.project.core.service.service.OrgUserService;
import com.xjt.cloud.project.core.service.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织架构 角色
 * 
 * @author wangzhiwen
 * @date  2019/9/6 16:24
 */
@Service
public class OrgUserRoleServiceImpl extends AbstractService implements OrgUserRoleService {
    @Autowired
    private OrgUserRoleDao orgUserRoleDao;
    @Autowired
    private OrgUserDao orgUserDao;
    @Autowired
    private OrgUserService orgUserService;
    @Autowired
    private RoleService roleService;
    
    /**
     * 功能描述:保存用户部门角色关系
     *
     * @param list
     * @param roleList
     * @param projectId
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date  2019/9/6 14:58
     */
    @Override
    public Data saveUserOrgRole(List<OrgUser> list, List<Role> roleList, Long projectId){
        OrgUserRole orgUserRole;
        Project project = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId,Project.class);

        List<OrgUserRole> orgUserRoleList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        for (OrgUser orgUser:list){
        //完善用户信息的公司部门角色id
            if(!orgUser.getUserId().equals(project.getOwnerId())){//导入成员不能是项目所属人
                orgUserRole = new OrgUserRole();
                orgUserRole.setProjectId(projectId);
                orgUserRole.setOrgUserId(orgUser.getId());

                orgUserRole.setUserId(orgUser.getUserId());
                for (Role role:roleList){
                    if (orgUser.getRoleName().equals(role.getRoleName())){
                        orgUserRole.setRoleId(role.getId());
                        break;
                    }
                }
                orgUserRoleList.add(orgUserRole);
                sql.append(" SELECT '" + orgUserRole.getOrgUserId() + "' org_user_Id, '" + orgUserRole.getRoleId() + "' newRoleId, '" + orgUserRole.getUserId() + "' user_id UNION ");

            }
        }
        //判断已经存在的用户部门角色信息,如果存在则不添加,如有删除关系则删除数据
        List<OrgUserRole> temOrgUserRoleList = orgUserRoleDao.findByRoleOrgUserBySql(list, sql.substring(0,sql.length() - 6), projectId);
        orgUserRoleList = new ArrayList<>();
        List<Long> listDelIds = new ArrayList<>();
        for (int i = 0;i < temOrgUserRoleList.size();i++){
            orgUserRole = temOrgUserRoleList.get(i);
            if (orgUserRole.getNewRoleId() == null){//添加要删除关系的列表
                listDelIds.add(orgUserRole.getId());
            } else if (orgUserRole.getRoleId() == null){//添加要新增关系的列表
                orgUserRole.setProjectId(projectId);
                orgUserRole.setRoleId(orgUserRole.getNewRoleId());
                orgUserRoleList.add(orgUserRole);
            }
        }

        if (CollectionUtils.isNotEmpty(listDelIds)) {
            orgUserRole = new OrgUserRole();//删除不存在的关系
            orgUserRole.setIds(listDelIds.toArray(new Long[listDelIds.size()]));
            orgUserRole.setProjectId(projectId);
            orgUserRoleDao.deleteOrgUserRoleByUser(orgUserRole);
        }

        if (CollectionUtils.isNotEmpty(orgUserRoleList)) {
            //删除用户部门的现有角色关系
            int num = orgUserRoleDao.saveOrgUserRolesByList(orgUserRoleList);//指新增用户部门角色关系
            return asseData(num);
        }
        return Data.isSuccess();
    }

    /**
     * saveProjectUserOrgRole  保存角色信息
     * 
     * @param list 成员
     * @param role 角色
     * @param projectId 项目id
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date  2019/11/19 14:44
     **/
    @Override
    public Data saveProjectUserOrgRole(List<OrgUser> list, Role role, Long projectId){
        Long[] userIds = new Long[list.size()];
        int i = 0;
        OrgUserRole orgUserRole;
        List<OrgUserRole> orgUserRoleList = new ArrayList<>();
        for (OrgUser orgUser:list){
            //完善用户信息的公司部门角色id
            orgUserRole = new OrgUserRole();
            orgUserRole.setProjectId(projectId);
            orgUserRole.setOrgUserId(orgUser.getId());
            orgUserRole.setUserId(orgUser.getUserId());
            userIds[i] = orgUser.getUserId();
            i++;
            if (orgUser.getRoleName().equals(role.getRoleName())){
                orgUserRole.setRoleId(role.getId());
            }
            orgUserRoleList.add(orgUserRole);
        }
        if(Constants.COMPATIBLE_VERSION.equals("5.0")) {
            orgUserRole = new OrgUserRole();
            orgUserRole.setRoleId(role.getId());
            orgUserRole.setProjectId(projectId);
            orgUserRoleDao.deleteOrgUserRoleByUser(orgUserRole);
        }
        //删除用户部门的现有角色关系
        int num = 1;
        if(orgUserRoleList.size()!=0){
            num = orgUserRoleDao.saveOrgUserRolesByList(orgUserRoleList);//指新增用户部门角色关系
        }
        return asseData(num);
    }

    /**
     * deleteRole 删除角色信息下的成员
     *
     * @param roleIds 角色ID
     * @return void
     * @author zhangZaiFa
     * @date 2019/9/19 15:48 
     **/
    @Override
    public Data deleteRoleIds(List<Long> roleIds) {
        orgUserRoleDao.deleteRoleIds(roleIds);
        return Data.isSuccess();
    }
    
    /**
     * findByRoleOrgUserTree 查询角色成员树结构
     *
     * @param orgUserRole 组织架构 角色
     * @return java.util.List<com.xjt.cloud.project.core.entity.OrgUserRole>
     * @author zhangZaiFa
     * @date 2019/9/20 14:51 
     **/
    @Override
    public List<OrgUserRole> findByRoleOrgUserTree(OrgUserRole orgUserRole) {
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            return orgUserRoleDao.findByRoleOrgUserTreeCV5(orgUserRole);
        }
        return orgUserRoleDao.findByRoleOrgUserTree(orgUserRole);
    }

    /**
     * updateAdmin 修改项目管理员
     *
     * @param oldOwnerId oldOwnerId
     * @param oldOwnerId oldOwnerId
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/9/24 10:16
     **/
    @Override
    public Data updateAdmin(Long oldOwnerId, Long ownerId,Long projectId) {
        OrgUser orgUser = new OrgUser();
        orgUser.setUserId(ownerId);
        orgUser.setProjectId(projectId);
        Long orgUserId = orgUserDao.findOrgUser(orgUser).getId();
        orgUser.setUserId(oldOwnerId);
        Long oldOrgUserId = orgUserDao.findOrgUser(orgUser).getId();
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            orgUserRoleDao.updateAdminCV5(oldOrgUserId,orgUserId,ownerId,projectId);
        }else {
            orgUserRoleDao.updateAdmin(oldOrgUserId, orgUserId, ownerId, projectId);
        }
        return Data.isSuccess();
    }

    /**
     * findByRoleOrgUser 按条件查询
     *  
     * @param orgUserRole 组织架构角色
     * @return java.util.List<com.xjt.cloud.project.core.entity.OrgUserRole>
     * @author zhangZaiFa
     * @date 2019/9/25 17:18 
     **/
    @Override
    public List<OrgUserRole> findByRoleOrgUser(OrgUserRole orgUserRole) {
        return orgUserRoleDao.findByRoleOrgUser(orgUserRole);
    }

    /** deleteOrgUserRoleByUser 按用户条件删除
     * 
     * @param orgUserRole 组织架构角色
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/5 10:48
     **/
    @Override
    public Data deleteOrgUserRoleByUser(OrgUserRole orgUserRole) {
        orgUserRoleDao.deleteOrgUserRoleByUser(orgUserRole);
        return Data.isSuccess();
    }

    /**
     * addOrdinary 添加普通权限
     *  
     * @param userId 用户ID
     * @param projectId 项目ID
     * @author zhangZaiFa
     * @date 2019/11/12 17:41 
     **/
    @Override
    public void addOrdinary(Long userId, Long projectId) {
        OrgUserRole our = new OrgUserRole();
        our.setUserId(userId);
        our.setProjectId(projectId);
        List<OrgUserRole> list = orgUserRoleDao.findByRoleOrgUser(our);
        //查询成员
        OrgUser orgUser = new OrgUser(userId,projectId);
        orgUser.setUserId(userId);
        orgUser.setProjectId(projectId);
        orgUser = orgUserService.findOrgUser(orgUser);

        //没有其他权限
        if(list.size() == 0 && orgUser != null){
            our.setOrgUserId(orgUser.getId());
            //查询项目普通角色
            Role role = roleService.findByProjectOrdinaryRole(projectId);
            our.setRoleId(role.getId());
            List<OrgUserRole> orgUserRoles = new ArrayList<>();
            orgUserRoles.add(our);
            orgUserRoleDao.saveOrgUserRolesByList(orgUserRoles);
        }
    }

    /**
     * findByProjectRoleUserIdListInit 查询项目指定权限的UserId
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/15 10:31
     **/
    @Override
    public Data findByProjectPermissionUserIdList(String json) {
        OrgUserRole orgUserRole = JSONObject.parseObject(json, OrgUserRole.class);
        List<String> userIds = orgUserRoleDao.findByProjectPermissionUserIdList(orgUserRole);
        return asseData(userIds);
    }

    /**
     * 查询用户在指定项目内是否有此权限
     *  
     * @param userId 用户ID
     * @param projectId 项目ID
     * @param signList 别名
     * @return java.lang.Integer
     * @author zhangZaiFa
     * @date 2020/1/15 11:24 
     **/
    @Override
    public Integer findByIsOrgUserRoleSign(Long userId, Long projectId, List<String> signList) {
        return orgUserRoleDao.findByIsOrgUserRoleSign(userId,projectId,signList);
    }
}
