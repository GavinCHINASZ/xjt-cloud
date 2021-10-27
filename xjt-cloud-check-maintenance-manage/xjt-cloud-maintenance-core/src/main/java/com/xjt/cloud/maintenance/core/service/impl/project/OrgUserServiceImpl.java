package com.xjt.cloud.maintenance.core.service.impl.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import com.xjt.cloud.maintenance.core.common.ConstantsProjectMsg;
import com.xjt.cloud.maintenance.core.dao.project.OrgUserDao;
import com.xjt.cloud.maintenance.core.dao.project.OrgUserRoleDao;
import com.xjt.cloud.maintenance.core.dao.project.PermissionDao;
import com.xjt.cloud.maintenance.core.dao.project.RoleDao;
import com.xjt.cloud.maintenance.core.dao.sys.UserDao;
import com.xjt.cloud.maintenance.core.entity.project.*;
import com.xjt.cloud.maintenance.core.service.service.project.*;
import com.xjt.cloud.maintenance.core.service.service.sys.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * @ClassName OrgUserServiceImpl 平台成员实现类
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Service
public class OrgUserServiceImpl extends AbstractService implements OrgUserService {

    @Autowired
    private OrgUserDao orgUserDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private OrgUserRoleDao orgUserRoleDao;
    @Autowired
    private OrgUserRoleService orgUserRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private InviteConnectionService inviteConnectionService;
    @Autowired
    private SecurityLogService securityLogService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private UserDao userDao;

    /**
     * 功能描述: 成员管理--导入成员--点击下载模板
     *
     * @param
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2019/08/07
     */
    @Override
    public Data downModel(HttpServletResponse resp) {
        List<User> list = new ArrayList<>();
        User entity = new User();
        entity.setRowNum(1L);
        entity.setUserName("老王");
        entity.setRoleName("平台管理员");
        entity.setPhone("13658205820");
        entity.setCompanyName("消检通(深圳)科技有限公司");
        entity.setDepartmentName("研发部");
        list.add(entity);

        String[] heads = {"序号", "成员姓名","手机号", "角色", "公司名称", "部门"};
        String[] keys = {"rowNum", "userName", "phone", "roleName", "companyName", "departmentName"};
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "成员信息表");

        //ExcelUtils.createAndDownloadExcel(resp, list, heads, keys, ConstantsProjectMsg.REPORT_PROJECT_ORG_USER_MODEL_FILE_PATH, 2);
        ExcelUtils.createAndDownloadExcel(resp, null, keys,  ConstantsProjectMsg.REPORT_PROJECT_ORG_USER_MODEL_FILE_PATH,
                3, null, jsonObject, "1:0");
        return Data.isSuccess();
    }

    /**
     * 功能描述: 上传 成员excel表格 (成员管理--导入成员--添加文件)
     *
     * @param file
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: huanggc
     * @date: 2019/08/07
     */
    @Override
    public Data uploadUserExcel(MultipartFile file, String json) {
        String[] keys = {"rowNum", "userName","phone", "roleName", "companyName", "departmentName"};
        //String[] heads = {"序号", "角色", "手机号", "状态", "公司名称", "部门"};
        List<User> list = ExcelUtils.readyExcel(file, 3, 0, 0, keys, User.class);

        if (CollectionUtils.isNotEmpty(list)) {
            if (list.size() > 100) {//验证是否一次大于100人
                return asseData(ServiceErrCode.REQ_ERR.getCode(), ConstantsProjectMsg.UPLOAD_USER_EXCEED_100);
            }
            OrgUser orgUser = JSONObject.parseObject(json, OrgUser.class);
            Long projectId = orgUser.getProjectId();
            StringBuilder roleNameStr = new StringBuilder();
            StringBuilder orgNameStr = new StringBuilder();
            StringBuilder userNameStr = new StringBuilder();
            List<User> nullList = new ArrayList<>();
            List<User> repeatList = new ArrayList<>();
            String str;
            for (User user : list) {
                //判断是否为空
                if (StringUtils.isEmpty(user.getCompanyName()) && StringUtils.isEmpty(user.getDepartmentName()) && StringUtils.isEmpty(user.getPhone())) {
                    break;
                }
                //判断是否为空
                if (StringUtils.isEmpty(user.getCompanyName()) || StringUtils.isEmpty(user.getDepartmentName()) || StringUtils.isEmpty(user.getPhone())) {
                    nullList.add(user);
                    continue;
                }
                if (StringUtils.isNotEmpty(user.getRoleName())) {
                    str = " '" + user.getRoleName() + "', ";
                    if (roleNameStr.indexOf(str) == -1) {//组装角色查询条件
                        roleNameStr.append(str);
                    }
                }

                str = " SELECT '" + user.getCompanyName() + "' coName, '" + user.getDepartmentName() + "' depName UNION ";
                if (orgNameStr.indexOf(str) == -1) {//组装公司部门查询条件
                    orgNameStr.append(str);
                }

                str = " SELECT '" + user.getPhone() + "' phone UNION ";
                if (userNameStr.indexOf(str) == -1) {//组装用户查询条件
                    userNameStr.append(str);
                } else {
                    repeatList.add(user);
                }
            }

            if (CollectionUtils.isNotEmpty(nullList)) {//验证信息是否有为空
                return asseData(ServiceErrCode.REQ_ERR.getCode(), nullList, ConstantsProjectMsg.UPLOAD_PARAM_NULL_FAIL);
            }

            if (CollectionUtils.isNotEmpty(repeatList)) {//判断是否存在重复用户
                return asseData(Constants.FAIL_CODE, repeatList, ConstantsProjectMsg.REPEAT_USER);
            }

            //判断公司部门是否都存在
            String sql = orgNameStr.substring(0, orgNameStr.length() - 6);
            List<Organization> orgList = orgUserDao.findCoDepByNames(sql, projectId);
            Data data = checkUserListOrg(sql, orgList);
            if (data.getStatus() != Constants.SUCCESS_CODE) {
                return data;
            }

            //判断角色是否都存在
            List<Role> roleList = new ArrayList<>();
            if (roleNameStr.length() > 0) {
                sql = roleNameStr.substring(0, roleNameStr.length() - 2);
                roleList = roleDao.findRoleByNames(sql, projectId);
                data = roleService.checkUserListRole(sql, roleList);
                if (data.getStatus() != Constants.SUCCESS_CODE) {
                    return data;
                }
            }

            list = userService.saveUser(list, userNameStr.substring(0, userNameStr.length() - 6));//新增或修改用户信息

            List<OrgUser> orgUserList = saveUserOrg(list, orgList, projectId);//保存用户部门信息

            securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_ADD_ORG_USER", SecurityUserHolder.getUserName(), "导入" + list.size() + "个成员信息", orgUser.getProjectId(), 2);
            if (CollectionUtils.isNotEmpty(roleList)) {//保存用户部门角色信息
                return orgUserRoleService.saveUserOrgRole(orgUserList, roleList, projectId);
            } else {
                return asseData(orgUserList.size());
            }

        }
        return Data.isSuccess();
    }

    /**
     * 功能描述:保存用户部门信息
     *
     * @param list
     * @param orgList
     * @param projectId
     * @return: java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @auther: wangzhiwen
     * @date: 2019/9/6 14:29
     */
    private List<OrgUser> saveUserOrg(List<User> list, List<Organization> orgList, Long projectId) {
        int i = 0;
        OrgUser orgUser;

        StringBuilder sql = new StringBuilder();
        List<OrgUser> resOrgUserList = new ArrayList<>();
        for (User user : list) {//完善用户信息的公司部门id
            orgUser = new OrgUser();
            orgUser.initOrgUser(null, user.getId(), projectId, user.getUserName());
            orgUser.setRoleName(user.getRoleName());
            i++;
            for (Organization org : orgList) {
                if (user.getCompanyName().equals(org.getOrgName()) && user.getDepartmentName().equals(org.getOrgDepName())) {
                    orgUser.setOrgId(org.getDepId());
                    break;
                }
            }
            resOrgUserList.add(orgUser);
            sql.append(" SELECT '" + orgUser.getOrgId() + "' newOrgId, '" + user.getId() + "' user_id , '" + user.getUserName() + "' user_name UNION ");
        }

        //判断已经存在的用户部门信息,如果存在则不添加,如有删除关系则删除数据
        List<OrgUser> temOrgUserList = orgUserDao.findOrgUserListBySql(list, sql.substring(0, sql.length() - 6), projectId);
        List<OrgUser> saveOrgUserList = new ArrayList<>();
        List<Long> listDelIds = new ArrayList<>();
        List<OrgUser> orgUserList = new ArrayList<>();
        for (i = 0; i < temOrgUserList.size(); i++) {
            orgUser = temOrgUserList.get(i);
            if (orgUser.getNewOrgId() == null) {//添加要删除关系的列表
                listDelIds.add(orgUser.getId());
            }else if (orgUser.getOrgId() == null) {//添加要新增关系的列表
                orgUser.setOrgId(orgUser.getNewOrgId());
                orgUser.setProjectId(projectId);
                saveOrgUserList.add(orgUser);
            }else{
                orgUserList.add(orgUser);
            }
        }

        if (CollectionUtils.isNotEmpty(listDelIds)) {
            orgUser = new OrgUser();
            orgUser.setProjectId(projectId);
            orgUser.setIds(listDelIds.toArray(new Long[listDelIds.size()]));
            orgUserDao.deleteOrgUser(orgUser);//删除不存在的关系
        }

        if (CollectionUtils.isNotEmpty(saveOrgUserList)){
            orgUserDao.relateOrgUsers(saveOrgUserList);
            orgUserList.addAll(saveOrgUserList);
        }

        for (OrgUser ou:resOrgUserList){
            for (OrgUser temObj:orgUserList){
                if (ou.getUserId().equals(temObj.getUserId()) && ou.getOrgId().equals(temObj.getOrgId())){
                    ou.setId(temObj.getId());
                    break;
                }
            }
        }
        return resOrgUserList;
    }

    /**
     * 功能描述:查询公司与部门是否存在
     *
     * @param sql
     * @param orgList
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/6 10:44
     */
    private Data checkUserListOrg(String sql, List<Organization> orgList) {
        //查询公司与部门是否存在
        String[] strs = sql.replaceAll("SELECT", "").replaceAll("coName", "公司").
                replaceAll("depName", "部门").split("UNION");
        if (orgList.size() < strs.length) {//判断是否有不存在的公司部门
            List<String> orgs = new ArrayList<>();
            boolean isExist;
            for (String str : strs) {
                str = str.trim();
                isExist = false;
                for (Organization org : orgList) {
                    if (str.indexOf(org.getOrgName()) != -1 && str.indexOf(org.getOrgDepName()) != -1) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    orgs.add(str);
                }
            }
            return asseData(Constants.FAIL_CODE, orgs, ServiceErrCode.NOTFOUND_RESULT_ERR.getMsg());
        }
        return Data.isSuccess();
    }

    /**
     * 功能描述:PC成员管理:删除
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2019/5/27
     */
    @Override
    public Data deleteOrgUser(String json) {
        OrgUser orgUser = JSON.parseObject(json, OrgUser.class);
        OrgUserRole orgUserRole = new OrgUserRole();
        orgUserRole.setOrgUserIds(orgUser.getUserIds());
        orgUserRole.setProjectId(orgUser.getProjectId());
        OrgUser projectManage = orgUserDao.findByProjectIsAdmin(orgUser);
        if (projectManage != null) {
            return asseData(600, "删除的成员中包含项目拥有者，不能删除！");
        }
        orgUserRoleService.deleteOrgUserRoleByUser(orgUserRole);
        List<OrgUser> orgUsers = orgUserDao.findByOrgUserList(orgUser);
        orgUserDao.deleteOrgUser(orgUser);
        for (OrgUser oldOrgUser : orgUsers) {
            securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_DEL_ORG_USER", SecurityUserHolder.getUserName(), oldOrgUser, null, oldOrgUser.getProjectId(), 2);
        }
        //删除用户号，把账号设置为未激活状态，默认项目ID设置为null
        userDao.updateUserStatus(orgUser.getUserIds());
        return Data.isSuccess();
    }

    /**
     * 功能描述:批量关联用户
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author wangzhiwen
     * @Date 2019/5/27
     */
    @Override
    public Data relateUsers(String json) {
        OrgUser orgUser = JSON.parseObject(json, OrgUser.class);
        Long projectId = orgUser.getProjectId();
        Long orgId = orgUser.getOrgId();
        Long[] userIds = orgUser.getUserIds();
        Long[] roleIds = orgUser.getRoleIds();
        String[] userNames = orgUser.getUserNames();

        //组装用户部门信息列表
        List<OrgUser> list = new ArrayList<>();
        OrgUser temOrgUser;
        for (int i = 0; i < userIds.length; i++) {
            temOrgUser = new OrgUser();
            temOrgUser.initOrgUser(orgId, userIds[i], projectId, userNames[i]);
            list.add(temOrgUser);
            securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_ADD_ORG_USER", SecurityUserHolder.getUserName(), temOrgUser, null, temOrgUser.getProjectId(), 2);
        }
        int num = orgUserDao.relateOrgUsers(list);//保存用户部门信息列表

        if (num > 0) {
            //组装用户部门角色关系列表
            OrgUserRole orgUserRole;
            Long orgUserId;
            List<OrgUserRole> listOUR = new ArrayList<>();
            for (OrgUser tem : list) {
                orgUserId = tem.getId();
                for (int i = 0; i < userIds.length; i++) {
                    orgUserRole = new OrgUserRole();
                    orgUserRole.initOrgUserRole(orgUserId, roleIds[i], projectId);
                    orgUserRole.setUserId(tem.getUserId());
                    listOUR.add(orgUserRole);
                }
            }
            num = orgUserRoleDao.saveOrgUserRolesByList(listOUR);//保存用户部门角色关系列表
        }
        return asseData(num);
    }


    /**
     * 功能描述:成员管理:编辑成员信息
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2019/5/28
     */
    @Override
    public Data updateOrgUser(String json) {
        OrgUser orgUser = JSON.parseObject(json, OrgUser.class);
        //删除绑定的用户部门与角色信息
        if (orgUser.getUserIds().length == 1) {
            orgUser.setUserId(orgUser.getUserIds()[0]);
            orgUser.setUserName(orgUser.getUserNames()[0]);
            OrgUser oldOrgUser = new OrgUser();
            oldOrgUser.setProjectId(orgUser.getProjectId());
            oldOrgUser.setUserId(orgUser.getUserId());
            oldOrgUser = orgUserDao.findOrgUser(oldOrgUser);
            orgUserDao.updateOrgUser(orgUser);
            User user = new User();
            user.setId(orgUser.getUserId());
            user.setUserName(orgUser.getUserName());
            userDao.updateUser(user);
            securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_ORG_USER", SecurityUserHolder.getUserName(), orgUser, oldOrgUser, oldOrgUser.getProjectId(), 2);
        } else {
            orgUserDao.updateOrgUsers(orgUser);
        }
        //修改成员角色
        if(orgUser.getRoleId()!=null){
            orgUserRoleDao.updateOrgUserRole(orgUser);
        }
        //清除缓存
        for (Long userId:orgUser.getUserIds()) {
            String key = Constants.ORG_USER_CACHE_KEY + "_" + userId+ "_" + orgUser.getProjectId();
            redisUtils.dels(key);
        }
        return Data.isSuccess();
    }

    /**
     * 功能描述: 保存用户部门角色信息
     *
     * @param orgUser
     * @param projectId
     * @param roleIds
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/9/5 15:41
     */
    @Override
    public int saveOrgUserRole(OrgUser orgUser, Long projectId, Long[] roleIds) {
        int num = orgUserDao.saveOrgUser(orgUser);
        if (num > 0) {//添加用户公司角色关系
            OrgUserRole orgUserRole;
            List<OrgUserRole> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                orgUserRole = new OrgUserRole();
                orgUserRole.initOrgUserRole(orgUser.getId(), roleId, projectId);
                orgUserRole.setUserId(orgUser.getUserId());
                list.add(orgUserRole);
            }
            num = orgUserRoleDao.saveOrgUserRoles(list);
        }
        if (orgUser.getCreateUserName() == null) {
            securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_ADD_ORG_USER", SecurityUserHolder.getUserName(), orgUser, null, orgUser.getProjectId(), 2);
        } else {
            securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_ADD_ORG_USER", orgUser.getCreateUserName(), orgUser, null, orgUser.getProjectId(), 2);
        }
        return num;
    }

    /**
     * @return
     * @MethodName: 查询用户部门公司树关系
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/23 15:14
     */
    @Override
    public Data findOrgUserTree(String json) {
        Organization entity = JSONObject.parseObject(json, Organization.class);
        //查询平台下所有成员
        List<Organization> list = orgUserDao.findOrgUserTree(entity);
        return asseData(list);
    }

    /**@MethodName: organizationTree 成员树结构生成
     * @Description: 
     * @Param: [list]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/7 18:25 
     **/
    private Data organizationTree(List<Organization> list) {
        Set<Organization> tree = new HashSet();
        for (Organization org: list) {
            tree.add(org);
            while(org.getParentId()!=null && org.getParentId()!=0){
                org = CacheUtils.getCacheByTypeAndKey(Constants.ORG_CACHE_KEY, org.getParentId().toString(), Organization.class);
                if(org.getOrgType()==2){
                    org.setOrgType(1);
                }else if(org.getOrgType()==3){
                    org.setOrgType(2);
                }
                org.setUserId(0L);
                org.setPhone("0");
                tree.add(org);
            }
        }
        return asseData(new ArrayList<>(tree));
    }


    /**
     * @MethodName: saveOrgUser 添加项目成员
     * @Description:
     * @Param: [orgUser]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/10 11:13
     **/
    @Override
    public Data saveOrgUser(OrgUser orgUser) {
        orgUserDao.saveOrgUser(orgUser);
        return Data.isSuccess();
    }

    /**
     * @MethodName: findOrgUser 查询成员
     * @Description:
     * @Param: [orgUser]
     * @Return: com.xjt.cloud.project.core.entity.OrgUser
     * @Author: zhangZaiFa
     * @Date:2019/9/9 16:28
     **/
    @Override
    public OrgUser findOrgUser(OrgUser orgUser) {
        return orgUserDao.findOrgUser(orgUser);
    }

    /**
     * @MethodName: findOrgUserList 查询项目成员列表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/20 9:57
     **/
    @Override
    public Data findOrgUserList(String json) {
        OrgUser orgUser = JSONObject.parseObject(json, OrgUser.class);
        List<OrgUser> list = orgUserDao.findOrgUserList(orgUser);
        Integer totalCount = orgUser.getTotalCount();
        Integer pageSize = orgUser.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {//判断是否存在总数，如没有，则查询总记录数
            totalCount = orgUserDao.findByOrgListCount(orgUser);
        }
        return asseData(totalCount, list);
    }

    /**
     * @MethodName: orgUserInvite 成员邀请
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/26 10:07
     **/
    @Override
    public Data orgUserInviteSave(String json) {
        OrgUser orgUser = JSONObject.parseObject(json, OrgUser.class);
        InviteConnection inviteConnection = new InviteConnection();
        inviteConnection.setProjectId(orgUser.getProjectId());
        inviteConnection.setRandomCode(orgUser.getRandomCode());
        InviteConnection oldInviteConnection = inviteConnectionService.findByProjectNotExpiredInviteConnection(inviteConnection);
        if (oldInviteConnection == null) {
            return asseData(600, "邀请时间已过期！");
        }
        String captcha = redisUtils.getString("inviteCaptcha_" + orgUser.getPhone());//从缓存中取出验证码
        if (captcha == null) {
            return asseData(600, "请重新获取验证码！");
        }
        if (!captcha.equals(orgUser.getCaptcha())) {
            return asseData(600, "验证码输入错误！");
        }
        User user = new User();
        user.setPhone(orgUser.getPhone());
        user.setProjectId(orgUser.getProjectId());
        User oldUser = userService.findByUser(user);
        Role role = roleService.findByProjectOrdinaryRole(user.getProjectId());
        Long[] roles = new Long[1];
        roles[0] = role.getId();
        //查询用户是否存在，不存在注册一个新的用户
        if (oldUser == null) {
            user.setRoleIds(roles);
            user.setOrgId(0l);
            //查询项目普通角色ID
            userService.saveUser(JSONObject.toJSONString(user));
        } else {

            oldUser.setRoleIds(roles);
            OrgUser newOrgUser = new OrgUser();
            newOrgUser.setCreateUserName(oldInviteConnection.getCreateUserName());
            newOrgUser.initOrgUser(oldUser.getOrgId(), oldUser.getId(), orgUser.getProjectId(), oldUser.getUserName());
            saveOrgUserRole(newOrgUser, orgUser.getProjectId(), oldUser.getRoleIds());
        }
        return Data.isSuccess();
    }

    /**
     * @MethodName: orgUserInviteCaptcha 获取成员邀请码
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/26 13:47
     **/
    @Override
    public Data orgUserInviteCaptcha(String json) {
        OrgUser orgUser = JSONObject.parseObject(json, OrgUser.class);
        OrgUser oldOrgUser = orgUserDao.findOrgUser(orgUser);
        if (oldOrgUser != null) {
            return asseData(600, "你已是项目成员！");
        }
        InviteConnection inviteConnection = new InviteConnection();
        inviteConnection.setProjectId(orgUser.getProjectId());
        inviteConnection.setRandomCode(orgUser.getRandomCode());
        InviteConnection oldInviteConnection = inviteConnectionService.findByProjectNotExpiredInviteConnection(inviteConnection);
        if (oldInviteConnection == null) {
            return asseData(600, "邀请时间已过期！");
        }
        return sendCaptcha(orgUser.getPhone(), "inviteCaptcha");
    }

    /**
     * @MethodName: orgUserInviteCaptchaConnection 邀请连接
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/26 15:24
     **/
    @Override
    public Data orgUserInviteCaptchaConnection(String json) {
        InviteConnection inviteConnection = JSONObject.parseObject(json, InviteConnection.class);
        inviteConnection = inviteConnectionService.findByProjectInviteConnection(inviteConnection);
        return asseData(inviteConnection);
    }

    /**
     * @MethodName: findByProjectJoinOrgUserTree 查询用户参与项目成员结构
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/29 9:58
     **/
    @Override
    public Data findByProjectJoinOrgUserTree(String json) {
        OrgUser orgUser = JSONObject.parseObject(json, OrgUser.class);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        List<Long> projectIds = orgUserDao.findByJoinProjectIds(userId);
        projectIds.remove(orgUser.getProjectId());
        List<Organization> list = new ArrayList<>();
        if (projectIds.size() != 0) {
            list = orgUserDao.findByProjectJoinOrgUserTree(projectIds);
        }
        return asseData(list);
    }

    /**
     * @MethodName: projectJoinOrgUserSave 从用户参与的项目批量添加成员
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/29 13:41
     **/
    @Override
    public Data projectJoinOrgUserSave(String json) {
        OrgUser orgUser = JSONObject.parseObject(json, OrgUser.class);
        Set<Long> userSet = new HashSet();
        List<OrgUser> orgUsers = new ArrayList<>();
        if (orgUser.getUserIds() == null || orgUser.getUserIds().length == 0) {
            return asseData(600, "请添加成员");
        }
        //批量添加成员信息
        for (Long userId : orgUser.getUserIds()) {
            OrgUser newOrgUser = new OrgUser();
            newOrgUser.setUserId(userId);
            newOrgUser.setProjectId(orgUser.getProjectId());
            OrgUser oldOrgUser = orgUserDao.findOrgUser(newOrgUser);
            //判断成员是否已在项目内
            if (userSet.add(userId) && oldOrgUser == null) {
                User user = new User();
                user.setId(userId);
                user = userService.findByUser(user);
                newOrgUser.initOrgUser(0L, userId, orgUser.getProjectId(), user.getUserName());
                newOrgUser.setRoleName("普通成员");
                orgUsers.add(newOrgUser);
                securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_ADD_ORG_USER", SecurityUserHolder.getUserName(), newOrgUser, null, newOrgUser.getProjectId(), 2);
            }
        }
        if (orgUsers.size() == 0) {
            return Data.isSuccess();
        }
        orgUserDao.relateOrgUsers(orgUsers);
        //给添加的新成员授予普通成员权限
        Role role = roleService.findByProjectOrdinaryRole(orgUser.getProjectId());
        return orgUserRoleService.saveProjectUserOrgRole(orgUsers, role, orgUser.getProjectId());
    }

    /**
     * @MethodName: findByOrgUserIdList 通过ID查询对象
     * @Description:
     * @Param: [orgUserIds]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @Author: zhangZaiFa
     * @Date:2019/9/30 16:44
     **/
    @Override
    public List<OrgUser> findByOrgUserIdList(List<Long> orgUserIds) {
        return orgUserDao.findByOrgUserIdList(orgUserIds);
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
    public Data updateUserProject(String json) {
        User user = JSONObject.parseObject(json, User.class);
        user.setProjectName(CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, user.getProjectId(), "projectName"));
        return userService.updateUserProject(user);
    }

    /**
     * @MethodName: findByUser 查询用户信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/12 14:49
     **/
    @Override
    public Data findByUser(String json) {
        User user = JSONObject.parseObject(json, User.class);
        user = userService.findByUser(user);
        return asseData(user);
    }

    /**
     * @MethodName: findByProjectCaptcha 获取修改项目转让验证码
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/12 14:53
     **/
    @Override
    public Data findByProjectCaptcha(String json) {
        User user = JSONObject.parseObject(json, User.class);
        user = userService.findByUser(user);
        return sendCaptcha(user.getPhone(), "projectTransferCaptcha");
    }

    /**
     * @MethodName: sendCaptcha 发送验证码
     * @Description:
     * @Param: [phone, captchaType]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/12 15:15
     **/
    private Data sendCaptcha(String phone, String captchaType) {

        String projectTransferCaptcha_ = StringUtils.generateRanNum(4);//生成验证码
        boolean b = TaoBaoSendMsg.sendMsg(phone, "{\"code\":\"" + projectTransferCaptcha_ + "\"}");//发送验证码信息
        if (!b) {
            return asseData(ServiceErrCode.SERVER_ERR.getCode(), "邀请码发送错误");
        }
        String key = captchaType + "_" + phone;
        redisUtils.set(key, projectTransferCaptcha_, Constants.TEN_TIME_SECONDS);

        return Data.isSuccess();
    }

    /**
     * @MethodName: checkProjectTransferCaptcha 校验项目转让验证码
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/12 15:04
     **/
    @Override
    public Data checkProjectTransferCaptcha(String json) {
        User user = JSONObject.parseObject(json, User.class);
        User oldUser = userService.findByUser(user);
        String captcha = redisUtils.getString("projectTransferCaptcha_" + oldUser.getPhone());//从缓存中取出验证码
        if (captcha == null) {
            return asseData(600, "请重新获取验证码！");
        }
        if (!captcha.equals(user.getCaptcha())) {
            return asseData(600, "验证码输入错误！");
        }
        return Data.isSuccess();
    }

    /**
     * @MethodName: findByDepIdOrgUserList 查询部门下面的人员
     * @Description:
     * @Param: [ids]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @Author: zhangZaiFa
     * @Date:2019/10/30 16:15
     **/
    @Override
    public List<OrgUser> findByDepIdOrgUserList(List<Long> ids) {
        return orgUserDao.findByDepIdOrgUserList(ids);
    }

    /**
     * @MethodName: findByOrgUserList 按条件查询成员信息
     * @Description:
     * @Param: [orgUser]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @Author: zhangZaiFa
     * @Date:2019/11/5 18:12
     **/
    @Override
    public List<OrgUser> findByRoleOrgUserList(OrgUser orgUser) {
        return orgUserDao.findByRoleOrgUserList(orgUser);
    }

    /**
     * @MethodName: findByOrgUserPermission 查询当前用户项目权限
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/7 10:43
     **/
    @Override
    public Data findByOrgUserProjectPermission(String json) {
        OrgUser orgUser = JSONObject.parseObject(json, OrgUser.class);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        orgUser.setUserId(userId);
        String key = Constants.ORG_USER_PROJECT_PERMISSION_CACHE_KEY + "_" + userId+ "_" + orgUser.getProjectId();
        //if (redisUtils.get(key) == null) {
            orgUserProjectPermissionInit(JSONObject.toJSONString(orgUser));
       // }
        return asseData(redisUtils.get(key));

    }

    /**
     * @MethodName: orgUserProjectPermissionInit 当前用户项目权限信息初始化缓存
     * @Param: [json]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/7 14:08
     **/
    @Override
    public void orgUserProjectPermissionInit(String json) {
        OrgUser orgUser = JSONObject.parseObject(json, OrgUser.class);
        List<String> permissions = permissionService.findByOrgUserProjectPermission(orgUser);
        String key = Constants.ORG_USER_PROJECT_PERMISSION_CACHE_KEY + "_" + orgUser.getUserId() + "_" + orgUser.getProjectId();
        redisUtils.dels(key);
        redisUtils.set(key, permissions);
    }

    /**
     * @MethodName: orgUserCacheInit 项目成员缓存
     * @Description:
     * @Param: json
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/7 11:20
     **/
    @Override
    public void orgUserCacheInit(String json) {
        OrgUser orgUser = JSONObject.parseObject(json, OrgUser.class);
        OrgUser oldOrgUser = orgUserDao.findOrgUser(orgUser);
        String key = Constants.ORG_USER_CACHE_KEY + "_" + orgUser.getUserId() + "_" + orgUser.getProjectId();
        redisUtils.dels(key);
        redisUtils.set(key, JSONObject.toJSON(oldOrgUser));
    }



    /**@MethodName: findProjectRoleUserList
     * @Description: 查询角色成员列表
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/15 11:26
     **/
    @Override
    public Data findProjectRoleUserList(String json) {
        Role permission = JSONObject.parseObject(json, Role.class);
        List<OrgUser> list = roleDao.findProjectRoleUserList(permission);
        return asseData(list);
    }
}
