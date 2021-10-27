package com.xjt.cloud.maintenance.web.controller.project;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.service.service.project.OrgUserService;
import com.xjt.cloud.maintenance.core.service.service.sys.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName orgUserController 组织架构
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@RestController
@RequestMapping("/project/orgUser")
public class OrgUserController extends AbstractController {

    @Autowired
    private OrgUserService orgUserService;
    @Autowired
    private UserService userService;

    /**
     * 功能描述:新增成员:手动添加
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2019/5/27
     */
    @RequestMapping(value = "/saveUser/{projectId}")
    public Data saveUser(String json) {
        Data data = userService.saveUser(json);
        return data;
    }

    /**
     * 功能描述:新增成员:手动添加
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2019/5/27
     */
    @RequestMapping(value = "/relateUsers/{projectId}")
    public Data relateUsers(String json) {
        return orgUserService.relateUsers(json);
    }

    /**
     * 功能描述:PC成员管理:删除
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2019/5/27
     */
    @RequestMapping(value = "/deleteOrgUser/{projectId}")
    public Data deleteOrgUser(String json) {
        return orgUserService.deleteOrgUser(json);
    }


    /**
     * 功能描述:成员管理:编辑成员信息
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2019/5/28
     */
    @RequestMapping(value = "/updateOrgUser/{projectId}")
    public Data updateOrgUser(String json) {
        return orgUserService.updateOrgUser(json);
    }

    /**
     * 功能描述: 成员管理--导入成员--点击下载模板
     *
     * @param
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2019/08/07
     */
    @RequestMapping(value = "/downModel/{projectId}")
    public Data downModel(HttpServletResponse resp) {
        return orgUserService.downModel(resp);
    }

    /**
     * 功能描述: 上传 成员excel表格 (成员管理--导入成员--添加文件)
     *
     * @param file
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: huanggc
     * @date: 2019/08/07
     */
    @RequestMapping(value = "/uploadUserExcel/{projectId}")
    public Data uploadUserExcel(MultipartFile file, String json) {
        return orgUserService.uploadUserExcel(file, json);
    }


    /**
     * @MethodName: 查询用户部门公司树关系
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/23 15:07
     **/
    @RequestMapping(value = "/findOrgUserTree/{projectId}")
    public Data findOrgUserTree(String json) {
        return orgUserService.findOrgUserTree(json);
    }

    /**
     * @MethodName: findOrgUserList 查询项目成员列表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/20 9:49
     **/
    @RequestMapping(value = "/findOrgUserList/{projectId}")
    public Data findOrgUserList(String json) {
        return orgUserService.findOrgUserList(json);
    }

    /**
     * @MethodName: orgUserInvite 成员邀请
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/26 10:05
     **/
    @RequestMapping(value = "/orgUserInviteSave")
    public Data orgUserInviteSave(String json) {
        return orgUserService.orgUserInviteSave(json);
    }

    /**
     * @MethodName: orgUserInviteCaptcha 获取成员邀请验证码
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/26 13:47
     **/
    @RequestMapping(value = "/orgUserInviteCaptcha")
    public Data orgUserInviteCaptcha(String json) {
        return orgUserService.orgUserInviteCaptcha(json);
    }


    /**
     * @MethodName: orgUserInviteCaptchaConnection 项目邀请连接
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/26 15:23
     **/

    @RequestMapping(value = "/orgUserInviteCaptchaConnection/{projectId}")
    public Data orgUserInviteCaptchaConnection(String json) {
        return orgUserService.orgUserInviteCaptchaConnection(json);
    }

    /**
     * @MethodName: findByProjectJoinOrgUserTree 查询用户参与的项目的成员结构
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/29 9:57
     **/
    @RequestMapping(value = "/findByProjectJoinOrgUserTree/{projectId}")
    public Data findByProjectJoinOrgUserTree(String json) {
        return orgUserService.findByProjectJoinOrgUserTree(json);
    }

    /**@MethodName: projectJoinOrgUserSave 从用户参与的项目批量添加成员
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/29 13:40 
     **/
    @RequestMapping(value = "/projectJoinOrgUserSave/{projectId}")
    public Data projectJoinOrgUserSave(String json) {
        return orgUserService.projectJoinOrgUserSave(json);
    }


    /**@MethodName: updateUserProject 更新用户默认项目信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/12 9:37
     **/
    @RequestMapping(value = "/updateUserProject")
    public Data updateUserProject(String json) {
        return orgUserService.updateUserProject(json);
    }

    /**@MethodName: findByUser 获取用户信息
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/12 14:47
     **/
    @RequestMapping(value = "/findByUser/{projectId}")
    public Data findByUser(String json) {
        return orgUserService.findByUser(json);
    }

    /**@MethodName: findByProjectCaptcha 获取修改项目转让验证码
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/12 14:53
     **/
    @RequestMapping(value = "/findByProjectCaptcha/{projectId}")
    public Data findByProjectCaptcha(String json) {
        return orgUserService. findByProjectCaptcha(json);
    }

    /**@MethodName: checkProjectTransferCaptcha 校验项目转让验证码
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/12 15:04 
     **/
    @RequestMapping(value = "/checkProjectTransferCaptcha/{projectId}")
    public Data checkProjectTransferCaptcha(String json) {
        return orgUserService.checkProjectTransferCaptcha(json);
    }


    /**@MethodName: findByOrgUserPermission 查询当前用户在项目中权限
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/7 10:13
     **/
    @RequestMapping(value = "/findByOrgUserProjectPermission")
    public Data findByOrgUserProjectPermission(String json) {
        return orgUserService.findByOrgUserProjectPermission(json);
    }

    /**
     * 功能描述: 项目成员信息缓存初使化
     * @Description:
     * @Param: [json]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/7 14:08
     */
    @RequestMapping(value = "/orgUserCacheInit")
    public void projectCacheInit(String json) {
        orgUserService.orgUserCacheInit(json);
    }



   /**@MethodName: orgUserProjectPermissionInit 当前用户项目权限信息初始化缓存
     * @Param: [json]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/7 14:08
     **/
    @RequestMapping(value = "/orgUserProjectPermissionInit")
    public void orgUserProjectPermissionInit(String json) {
        orgUserService.orgUserProjectPermissionInit(json);
    }

    /**@MethodName: findProjectRoleUserList 查询项目角色类型成员
     * @Param: [json]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/7 14:08
     **/
    @RequestMapping(value = "/findProjectRoleUserList/{projectId}")
    public Data findProjectPermissionUserList(String json) {
       return  orgUserService.findProjectRoleUserList(json);
    }


}
