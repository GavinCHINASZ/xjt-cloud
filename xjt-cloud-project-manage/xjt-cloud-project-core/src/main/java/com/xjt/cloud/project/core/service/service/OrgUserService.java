package com.xjt.cloud.project.core.service.service;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.entity.OrgUser;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * OrgUserService 平台成员
 * 
 * @author  zhangZaiFa
 * @date 2019-07-29 1515
 */
public interface OrgUserService {

    /**
     * 功能描述PC成员管理删除
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author  huanggc
     * @date 2019/5/27
     */
    Data deleteOrgUser(String json);

    /**
     * 功能描述批量关联用户
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author  wangzhiwen
     * @date 2019/5/27
     */
    Data relateUsers(String json);

    /**
     * 功能描述成员管理编辑成员信息
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author  huanggc
     * @date 2019/5/28
     */
    Data updateOrgUser(String json);

    /**
     *
     * 功能描述 保存用户部门角色信息
     *
     * @param orgUser
     * @param projectId
     * @param roleIds
     * @author wangzhiwen
     * @date 2019/9/5
     */
    int saveOrgUserRole(OrgUser orgUser, Long projectId, Long[] roleIds );

    /**
     * 功能描述 成员管理--导入成员--点击下载模板
     * 
     * @author  huanggc
     * @date 2019/08/07
     * @param resp HttpServletResponse
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data downModel(HttpServletResponse resp);

    /**
     * 功能描述 上传 成员excel表格 (成员管理--导入成员--添加文件)
     *
     * @param file
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/08/07
     */
    Data uploadUserExcel(MultipartFile file, String json);

    /**
     * findByOrgTree 查询平台成员树结构
     * 
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/7/23
     */
    Data findOrgUserTree(String json);

    /** 
     * findOrgUser 查询成员
     *  
     * @param orgUser
     * @return com.xjt.cloud.project.core.entity.OrgUser
     * @author zhangZaiFa
     * @date 2019/9/9
     **/
    OrgUser findOrgUser(OrgUser orgUser);

    /** saveOrgUser 添加项目成员
     * 
     * @param orgUser
     * @return void
     * @author zhangZaiFa
     * @date 2019/9/10
     **/
    Data saveOrgUser(OrgUser orgUser);

    /** findOrgUserList 查询项目成员列表
     *  
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author  zhangZaiFa
     * @date 2019/9/20
     **/
    Data findOrgUserList(String json);

    /** orgUserInvite 成员邀请
     *  
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/9/26
     **/
    Data orgUserInviteSave(String json);

    /** orgUserInviteCaptcha 获取成员邀请码
     *  
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author  zhangZaiFa
     * @date 2019/9/26
     **/
    Data orgUserInviteCaptcha(String json);

    /** orgUserInviteCaptchaConnection 邀请连接
     *  
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author  zhangZaiFa
     * @date 2019/9/26
     **/
    Data orgUserInviteCaptchaConnection(String json);

    /** findByProjectJoinOrgUserTree 查询用户参与项目成员结构
     *  
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author  zhangZaiFa
     * @date 2019/9/29
     **/
    Data findByProjectJoinOrgUserTree(String json);

    /** projectJoinOrgUserSave 从用户参与的项目批量添加成员
     *  
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author  zhangZaiFa
     * @date 2019/9/29
     **/
    Data projectJoinOrgUserSave(String json);

    /**
     * findByOrgUserIdList 通过ID查询对象
     *  
     * @param orgUserIds
     * @return java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @author  zhangZaiFa
     * @date 2019/9/30
     **/
    List<OrgUser> findByOrgUserIdList(List<Long> orgUserIds);

    /** updateUserProject 修改用户项目信息
     *  
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author  zhangZaiFa
     * @date 2019/10/12
     **/
    Data updateUserProject(String json);

    /** findByUser 查询用户信息
     *  
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author  zhangZaiFa
     * @date 2019/10/12
     **/
    Data findByUser(String json);

    /** findByProjectCaptcha 获取修改项目转让验证码
     * 
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author  zhangZaiFa
     * @date 2019/10/12
     **/
    Data findByProjectCaptcha(String json);

    /** checkProjectTransferCaptcha 校验项目转让验证码
     * 
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author  zhangZaiFa
     * @date 2019/10/12
     **/
    Data checkProjectTransferCaptcha(String json);

    /**
     * findByDepIdOrgUserList 查询部门下面的人员
     *  
     * @param ids
     * @return java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @author  zhangZaiFa
     * @date2019/10/30 1615 
     **/
    List<OrgUser> findByDepIdOrgUserList(List<Long> ids);

    /** findByOrgUserList 按条件查询成员
     *  
     * @param orgUser
     * @return java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @author  zhangZaiFa
     * @date 2019/11/5
     **/
    List<OrgUser> findByRoleOrgUserList(OrgUser orgUser);


    /** findByOrgUserPermission 查询当前用户在项目中权限
     * 
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/7
     **/
    Data findByOrgUserProjectPermission(String json);

    /** orgUserCacheInit 项目成员信息初始化
     * 
     * @param json
     * @return void
     * @author  zhangZaiFa
     * @date 2019/11/7
     **/
    void orgUserCacheInit(String json);

    /** orgUserProjectPermissionInit 当前用户项目权限信息初始化缓存
     * 
     * @param json
     * @author  zhangZaiFa
     * @date 2019/11/7
     **/
    void orgUserProjectPermissionInit(String json);

    /**
     *  查询用户信息
     *
     * @param json
     * @return void
     * @author  zhangZaiFa
     * @date 2020/5/28
     **/
    Data findByOrgUsers(String json);

    /**
     * 查询成员名称
     *
     * @param json 参数
     * @return String 成员名
     * @author  huanggc
     * @date 2020/09/10
     **/
    String findUsersName(String json);

    /**
     * 查询 成员 根据 权限
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/09/28
     **/
    Data findOrgUserByPermission(String json);
}
