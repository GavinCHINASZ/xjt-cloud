package com.xjt.cloud.project.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.OrgUserService;
import com.xjt.cloud.project.core.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * orgUserController 组织架构
 * 
 * @author zhangZaiFa
 * @date 2019-07-29 1515
 * 
 */
@RestController
@RequestMapping("/project/orgUser")
public class OrgUserController extends AbstractController {

    @Autowired
    private OrgUserService orgUserService;
    @Autowired
    private UserService userService;

    /**
     * 功能描述新增成员手动添加
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/5/27
     */
    @RequestMapping(value = "/saveUser/{projectId}")
    public Data saveUser(String json) {
        return userService.saveUser(json);
    }

    /**
     * 功能描述新增成员手动添加
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/5/27
     */
    @RequestMapping(value = "/relateUsers/{projectId}")
    public Data relateUsers(String json) {
        return orgUserService.relateUsers(json);
    }

    /**
     * 功能描述PC成员管理删除
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/5/27
     */
    @RequestMapping(value = "/deleteOrgUser/{projectId}")
    public Data deleteOrgUser(String json) {
        return orgUserService.deleteOrgUser(json);
    }


    /**
     * 功能描述成员管理编辑成员信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/5/28
     */
    @RequestMapping(value = "/updateOrgUser/{projectId}")
    public Data updateOrgUser(String json) {
        return orgUserService.updateOrgUser(json);
    }

    /**
     * 功能描述 成员管理--导入成员--点击下载模板
     *
     * @param resp HttpServletResponse
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/08/07
     */
    @RequestMapping(value = "/downModel/{projectId}")
    public Data downModel(HttpServletResponse resp) {
        return orgUserService.downModel(resp);
    }

    /**
     * 功能描述 上传 成员excel表格 (成员管理--导入成员--添加文件)
     *
     * @param file 上传的文件
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/08/07
     */
    @RequestMapping(value = "/uploadUserExcel/{projectId}")
    public Data uploadUserExcel(MultipartFile file, String json) {
        return orgUserService.uploadUserExcel(file, json);
    }


    /**
     *  查询用户部门公司树关系
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/7/23
     **/
    @RequestMapping(value = "/findOrgUserTree/{projectId}")
    public Data findOrgUserTree(String json) {
        return orgUserService.findOrgUserTree(json);
    }

    /**
     *  findOrgUserList 查询项目成员列表
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/9/20
     **/
    @RequestMapping(value = "/findOrgUserList/{projectId}")
    public Data findOrgUserList(String json) {
        return orgUserService.findOrgUserList(json);
    }

    /**
     *  orgUserInvite 成员邀请
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/9/26
     **/
    @RequestMapping(value = "/orgUserInviteSave")
    public Data orgUserInviteSave(String json) {
        return orgUserService.orgUserInviteSave(json);
    }

    /**
     * orgUserInviteCaptcha 获取成员邀请验证码
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/9/26
     **/
    @RequestMapping(value = "/orgUserInviteCaptcha")
    public Data orgUserInviteCaptcha(String json) {
        return orgUserService.orgUserInviteCaptcha(json);
    }

    /**
     *  项目邀请连接
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/9/26
     **/
    @RequestMapping(value = "/orgUserInviteCaptchaConnection/{projectId}")
    public Data orgUserInviteCaptchaConnection(String json) {
        return orgUserService.orgUserInviteCaptchaConnection(json);
    }

    /**
     *  findByProjectJoinOrgUserTree 查询用户参与的项目的成员结构
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/9/29
     **/
    @RequestMapping(value = "/findByProjectJoinOrgUserTree/{projectId}")
    public Data findByProjectJoinOrgUserTree(String json) {
        return orgUserService.findByProjectJoinOrgUserTree(json);
    }

    /** projectJoinOrgUserSave 从用户参与的项目批量添加成员
     *  
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/9/29
     **/
    @RequestMapping(value = "/projectJoinOrgUserSave/{projectId}")
    public Data projectJoinOrgUserSave(String json) {
        return orgUserService.projectJoinOrgUserSave(json);
    }

    /** updateUserProject 更新用户默认项目信息
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/10/12
     **/
    @RequestMapping(value = "/updateUserProject")
    public Data updateUserProject(String json) {
        return orgUserService.updateUserProject(json);
    }

    /** findByUser 获取用户信息
     *  
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/10/12
     **/
    @RequestMapping(value = "/findByUser/{projectId}")
    public Data findByUser(String json) {
        return orgUserService.findByUser(json);
    }

    /** findByProjectCaptcha 获取修改项目转让验证码
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/10/12
     **/
    @RequestMapping(value = "/findByProjectCaptcha/{projectId}")
    public Data findByProjectCaptcha(String json) {
        return orgUserService. findByProjectCaptcha(json);
    }

    /**
     * 校验项目转让验证码
     *  
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/10/12
     **/
    @RequestMapping(value = "/checkProjectTransferCaptcha/{projectId}")
    public Data checkProjectTransferCaptcha(String json) {
        return orgUserService.checkProjectTransferCaptcha(json);
    }

    /** findByOrgUserPermission 查询当前用户在项目中权限
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/7
     **/
    @RequestMapping(value = "/findByOrgUserProjectPermission")
    public Data findByOrgUserProjectPermission(String json) {
        return orgUserService.findByOrgUserProjectPermission(json);
    }

    /**
     * 功能描述 项目成员信息缓存初使化
     * 
     * @param json 参数
     * @author zhangZaiFa
     * @date 2019/11/7
     */
    @RequestMapping(value = "/orgUserCacheInit")
    public void projectCacheInit(String json) {
        orgUserService.orgUserCacheInit(json);
    }

   /** orgUserProjectPermissionInit 当前用户项目权限信息初始化缓存
     * @param json 参数
     * @author zhangZaiFa
     * @date 2019/11/7
     **/
    @RequestMapping(value = "/orgUserProjectPermissionInit")
    public void orgUserProjectPermissionInit(String json) {
        orgUserService.orgUserProjectPermissionInit(json);
    }

    /**
     * findByOrgUser
     *  查询用户信息
     *
     * @param json 参数
     * @return void
     * @author zhangZaiFa
     * @date 2020/5/28
     **/
    @RequestMapping(value = "/findByOrgUsers/{projectId}")
    public Data findByOrgUsers(String json) {
        return orgUserService.findByOrgUsers(json);
    }

    /**
     *  查询成员名称
     *
     * @param json 参数
     * @return String 成员名
     * @author huanggc
     * @date 2020/09/10
     **/
    @RequestMapping(value = "/findUsersName")
    public String findUsersName(String json) {
        return orgUserService.findUsersName(json);
    }

}
