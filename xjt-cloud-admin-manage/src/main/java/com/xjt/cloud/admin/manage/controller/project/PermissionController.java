package com.xjt.cloud.admin.manage.controller.project;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.iot.WaterDevice;
import com.xjt.cloud.admin.manage.entity.project.Path;
import com.xjt.cloud.admin.manage.entity.project.Permission;
import com.xjt.cloud.admin.manage.entity.project.PermissionPath;
import com.xjt.cloud.admin.manage.service.service.project.PermissionService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/10 10:17
 * @Description: 权限管理Controller
 */
@Controller
@RequestMapping("/permission/")
public class PermissionController extends AbstractController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 功能描述:获取所有权限
     * @Author wangzhiwen
     * @Date 2019/5/23
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "getPermissionList")
    @ResponseBody
    public Data getPermissionList(Boolean isClear){
        return permissionService.getPermissionList(isClear);
    }
    /**
     * 功能描述:获取用户权限
     * @Author wangzhiwen
     * @Date 2019/5/23
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "getUserPermissionList")
    @ResponseBody
    public Data getUserPermissionList(String json){
        return permissionService.getUserPermissionList(json);
    }

    /**@MethodName: toPermissionListPage
     * @Description: 跳转权限列表
     * @Param: []
     * @Return: org.springframework.web.servlet.ModelAndView
     * @Author: zhangZaiFa
     * @Date:2019/12/10 14:14
     **/
    @RequestMapping("toPermissionListPage")
    public ModelAndView toPermissionListPage(){
        return new ModelAndView("permission/permissionList");
    }

    /**@MethodName: toPathListPage
     * @Description: 跳转接口PATH
     * @Param: []
     * @Return: org.springframework.web.servlet.ModelAndView
     * @Author: zhangZaiFa
     * @Date:2019/12/11 16:30
     **/
    @RequestMapping("toPathListPage")
    public ModelAndView toPathListPage(){
        return new ModelAndView("permission/pathList");
    }


    /**@MethodName: permissionListPage
     * @Description: 跳转权限列表
     * @Param: []
     * @Return: org.springframework.web.servlet.ModelAndView
     * @Author: zhangZaiFa
     * @Date:2019/12/10 14:14
     **/
    @RequestMapping("findPermissionListPage")
    @ResponseBody
    public ScriptPage<Permission> findPermissionListPage(AjaxPage ajaxPage, Permission permission){
        return permissionService.findPermissionListPage(ajaxPage,permission);
    }

    /**@MethodName: permissionListPage
     * @Description: 跳转权限列表
     * @Param: []
     * @Return: org.springframework.web.servlet.ModelAndView
     * @Author: zhangZaiFa
     * @Date:2019/12/10 14:14
     **/
    @RequestMapping("findPermissionList")
    @ResponseBody
    public List<Permission> findPermissionList(){
        return permissionService.findPermissionList();
    }

    /**@MethodName: pathListPage
     * @Description: 查询接口路径
     * @Param: [ajaxPage, path]
     * @Return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Permission>
     * @Author: zhangZaiFa
     * @Date:2019/12/11 17:20
     **/
    @RequestMapping("findPathListPage")
    @ResponseBody
    public ScriptPage<Path> findPathListPage(AjaxPage ajaxPage, Path path){
        return permissionService.findPathListPage(ajaxPage,path);
    }

    /**@MethodName: permissionPathListPage
     * @Description: 查询权限Path
     * @Param: [ajaxPage, permissionPath]
     * @Return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.PermissionPath>
     * @Author: zhangZaiFa
     * @Date:2019/12/12 11:02
     **/
    @RequestMapping("findPermissionPathListPage")
    @ResponseBody
    public ScriptPage<PermissionPath> findPermissionPathListPage(AjaxPage ajaxPage, PermissionPath permissionPath){
        return permissionService.findPermissionPathListPage(ajaxPage,permissionPath);
    }

    /**@MethodName: savePath
     * @Description: 添加Path
     * @Param: [path]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/12 15:32
     **/
    @RequestMapping("savePath")
    @ResponseBody
    public Data savePath(Path path){
        return permissionService.savePath(path);
    }

    /**@MethodName: modifyPath
     * @Description: 修改Path
     * @Param: [path]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/12 15:32
     **/
    @RequestMapping("modifyPath")
    @ResponseBody
    public Data modifyPath(Path path){
        return permissionService.modifyPath(path);
    }

    /**@MethodName: delPath
     * @Description: 删除Path
     * @Param: [path]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/12 15:32
     **/
    @RequestMapping("delPath")
    @ResponseBody
    public Data delPath(Path path){
        return permissionService.delPath(path);
    }

    /**@MethodName: savePermissionPath
     * @Description: 保存权限Path
     * @Param: [path]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/13 9:25
     **/
    @RequestMapping("savePermissionPath")
    @ResponseBody
    public Data savePermissionPath(String  json){
        return permissionService.savePermissionPath(json);
    }

    /**@MethodName: modifyPermissionPath
     * @Description: 修改权限Path
     * @Param: [permissionPath]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/13 9:33
     **/
    @RequestMapping("modifyPermissionPath")
    @ResponseBody
    public Data modifyPermissionPath(PermissionPath permissionPath){
        return permissionService.modifyPermissionPath(permissionPath);
    }

    /**@MethodName: deletePermissionPath
     * @Description: 删除权限Path
     * @Param: [permissionPath]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/13 9:33
     **/
    @RequestMapping("deletePermissionPath")
    @ResponseBody
    public Data deletePermissionPath(PermissionPath permissionPath){
        return permissionService.deletePermissionPath(permissionPath);
    }

    /**@MethodName: savePermission
     * @Description: 保存权限
     * @Param: [permission]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/11 16:25
     **/
    @RequestMapping("savePermission")
    @ResponseBody
    public Data savePermission(Permission permission){
        return permissionService.savePermission(permission);
    }

    /**@MethodName: modifyPermission
     * @Description: 修改权限
     * @Param: [permission]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/13 9:33
     **/
    @RequestMapping("modifyPermission")
    @ResponseBody
    public Data modifyPermission(Permission permission){
        return permissionService.modifyPermission(permission);
    }


    /**
     *
     * 功能描述:查询权限关联的项目列表与状态
     *
     * @param permission
     * @return: ScriptPage<Permission>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    @RequestMapping("findRoleProjectList")
    @ResponseBody
    public ScriptPage<Permission> findRoleProjectList(AjaxPage ajaxPage,Permission permission){
        return permissionService.findRoleProjectList(ajaxPage,permission);
    }

    /**@MethodName: modifyPermission
     * @Description: 查询Path列表
     * @Param: [permission]
     * @Return: java.util.List<com.xjt.cloud.admin.manage.entity.project.Path>
     * @Author: zhangZaiFa
     * @Date:2019/12/13 18:06
     **/
    @RequestMapping("findPathList")
    @ResponseBody
    public List<Path> findPathList(Path path){
        return permissionService.findPathList(path);
    }

    /**@MethodName: findPermissionTree
     * @Description: 查询权限树结构
     * @Param: [path]
     * @Return: java.util.List<com.xjt.cloud.admin.manage.entity.project.Path>
     * @Author: zhangZaiFa
     * @Date:2020/4/1 14:17
     **/
    @RequestMapping("findPermissionPathTree")
    @ResponseBody
    public List<Path> findPermissionPathTree(Path path){
        return permissionService.findPermissionPathTree(path);
    }

}
