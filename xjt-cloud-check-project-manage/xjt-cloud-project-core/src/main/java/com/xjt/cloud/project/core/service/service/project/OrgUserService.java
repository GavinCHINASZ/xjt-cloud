package com.xjt.cloud.project.core.service.service.project;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.entity.project.OrgUser;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName OrgUserService 平台成员
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public interface OrgUserService {

    /**
     * 功能描述:PC成员管理:删除
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2019/5/27
     */
    Data deleteOrgUser(String json);

    /**
     * 功能描述:批量关联用户
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author wangzhiwen
     * @Date 2019/5/27
     */
    Data relateUsers(String json);

    /**
     * 功能描述:成员管理:编辑成员信息
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2019/5/28
     */
    Data updateOrgUser(String json);

    /**
     *
     * 功能描述: 保存用户部门角色信息
     *
     * @param orgUser
     * @param projectId
     * @param roleIds
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/9/5 15:41
     */
    int saveOrgUserRole(OrgUser orgUser, Long projectId, Long[] roleIds );

    /**
     * 功能描述: 成员管理--导入成员--点击下载模板
     * @Author huanggc
     * @Date 2019/08/07
     * @param
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data downModel(HttpServletResponse resp);

    /**
     * 功能描述: 上传 成员excel表格 (成员管理--导入成员--添加文件)
     *
     * @param file
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: huanggc
     * @date: 2019/08/07
     */
    Data uploadUserExcel(MultipartFile file, String json);

    /**
     * @return
     * @MethodName: findByOrgTree 查询平台成员树结构
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/23 15:09
     */
    Data findOrgUserTree(String json);

    /**@MethodName: findOrgUser 查询成员
     * @Description: 
     * @Param: [orgUser]
     * @Return: com.xjt.cloud.project.core.entity.OrgUser
     * @Author: zhangZaiFa
     * @Date:2019/9/9 16:55
     **/
    OrgUser findOrgUser(OrgUser orgUser);

    /**@MethodName: saveOrgUser 添加项目成员
     * @Description:
     * @Param: [orgUser]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/9/10 11:13
     **/
    Data saveOrgUser(OrgUser orgUser);

    /**@MethodName: findOrgUserList 查询项目成员列表
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/20 9:57
     **/
    Data findOrgUserList(String json);

    /**@MethodName: orgUserInvite 成员邀请
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/26 13:36
     **/
    Data orgUserInviteSave(String json);

    /**@MethodName: orgUserInviteCaptcha 获取成员邀请码
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/26 13:47 
     **/
    Data orgUserInviteCaptcha(String json);

    /**@MethodName: orgUserInviteCaptchaConnection 邀请连接
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/26 15:23 
     **/
    Data orgUserInviteCaptchaConnection(String json);

    /**@MethodName: findByProjectJoinOrgUserTree 查询用户参与项目成员结构
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/29 9:58
     **/
    Data findByProjectJoinOrgUserTree(String json);

    /**@MethodName: projectJoinOrgUserSave 从用户参与的项目批量添加成员
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/29 13:45
     **/
    Data projectJoinOrgUserSave(String json);

    /**@MethodName: findByOrgUserIdList 通过ID查询对象
     * @Description: 
     * @Param: [orgUserIds]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @Author: zhangZaiFa
     * @Date:2019/9/30 16:46
     **/
    List<OrgUser> findByOrgUserIdList(List<Long> orgUserIds);

    /**@MethodName: updateUserProject 修改用户项目信息
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/12 9:58 
     **/
    Data updateUserProject(String json);

    /**@MethodName: findByUser 查询用户信息
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/12 14:50
     **/
    Data findByUser(String json);

    /**@MethodName: findByProjectCaptcha 获取修改项目转让验证码
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/12 14:53
     **/
    Data findByProjectCaptcha(String json);

    /**@MethodName: checkProjectTransferCaptcha 校验项目转让验证码
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/12 15:04
     **/
    Data checkProjectTransferCaptcha(String json);

    /**@MethodName: findByDepIdOrgUserList 查询部门下面的人员
     * @Description: 
     * @Param: [ids]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @Author: zhangZaiFa
     * @Date:2019/10/30 16:15 
     **/
    List<OrgUser> findByDepIdOrgUserList(List<Long> ids);

    /**@MethodName: findByOrgUserList 按条件查询成员
     * @Description: 
     * @Param: [orgUser]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @Author: zhangZaiFa
     * @Date:2019/11/5 18:11 
     **/
    List<OrgUser> findByRoleOrgUserList(OrgUser orgUser);


    /**@MethodName: findByOrgUserPermission 查询当前用户在项目中权限
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/7 10:13
     **/
    Data findByOrgUserProjectPermission(String json);

    /**@MethodName: orgUserCacheInit 项目成员信息初始化
     * @Description:
     * @Param: [json]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/7 14:08
     **/
    void orgUserCacheInit(String json);

    /**@MethodName: orgUserProjectPermissionInit 当前用户项目权限信息初始化缓存
     * @Description:
     * @Param: [json]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/7 14:08
     **/
    void orgUserProjectPermissionInit(String json);


    /**@MethodName: findProjectRoleUserList
     * @Description: 查询项目角色类型成员
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/15 11:25
     **/
    Data findProjectRoleUserList(String json);
}
