package com.xjt.cloud.admin.manage.service.impl.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.project.PathDao;
import com.xjt.cloud.admin.manage.dao.project.PermissionDao;
import com.xjt.cloud.admin.manage.dao.project.PermissionPathDao;
import com.xjt.cloud.admin.manage.entity.project.Path;
import com.xjt.cloud.admin.manage.entity.project.PermissionPath;
import com.xjt.cloud.admin.manage.entity.sys.User;
import com.xjt.cloud.admin.manage.entity.project.Permission;
import com.xjt.cloud.admin.manage.service.service.project.PermissionService;
import com.xjt.cloud.commons.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/10 10:11
 * @Description: 权限管理接口实现类
 */
@Service
public class PermissionServiceImpl extends AbstractAdminService implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private PermissionPathDao permissionPathDao;
    @Autowired
    private PathDao pathDao;
    /**
     * 功能描述: 查询用户权限列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/22 16:02
     */
    @Override
    public Data getUserPermissionList(String json) {
        User user = JSONObject.parseObject(json, User.class);
        Long userId = user.getId();
        String loginName = user.getLoginName();
        if (null == userId){
            userId = getLoginUserId(loginName);
        }
        String key = "userPermissionList_3_" + loginName + "_0";
        redisUtils.dels(key);
        List<Permission> list = permissionDao.getUserPermissionList(userId);
        if (CollectionUtils.isNotEmpty(list)){
            StringBuilder urls = new StringBuilder();
            int size = list.size();
            for (int i = 0;i < size;i++){
                urls.append("," + list.get(i).getUrl()+ ",");

            }
            redisUtils.set( key, urls, Constants.CACHE_CANCEL);
        }
        return Data.isSuccess();
    }

    /**
     * 功能描述: 查询所有权限菜单列表
     *
     * @param userId
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/22 16:02
     */
    @Override
    public Map<Permission,List<Permission>> findUserMenuList(Long userId){
        List<Permission> list = permissionDao.findUserMenuList(userId);
        LinkedHashMap<Permission,List<Permission>> map = new LinkedHashMap<>();
        if (CollectionUtils.isNotEmpty(list)){
            List<Permission> modelList = new ArrayList<>();
            List<Permission> menuList = new ArrayList<>();
            int i = 0;
            for (Permission permission :list){//得到所有的
                if (permission.getParentId() > 0){
                    break;
                }
                i++;
                modelList.add(permission);
            }
            Permission permission;
            for (;i < list.size();i++){
                permission = list.get(i);
                menuList.add(permission);
                if (i == list.size() - 1 || !permission.getParentId().equals(list.get(i + 1).getParentId())){
                    for (Permission p :modelList){
                        if (p.getId().equals(permission.getParentId())){
                            map.put(p,menuList);
                            menuList = new ArrayList<>();
                            break;
                        }
                    }
                }
            }
        }
        return map;
    }

    /**
     * 功能描述: 查询所有菜单权限列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/22 16:02
     */
    @Override
    public Data getPermissionList(Boolean isClear) {
        String permissionList = redisUtils.getString("permissionList_3");
        if (StringUtils.isEmpty(permissionList) || (isClear != null && isClear)) {
            List<String> list = permissionDao.getPermissionList();
            String jsonStr = JSON.toJSONString(list);
            redisUtils.set("permissionList_3", jsonStr, Constants.CACHE_CANCEL);
        }
        return Data.isSuccess();
    }

    /**@MethodName: permissionListPage
     * @Description: 查询权限列表
     * @Param: [json]
     * @Return: org.springframework.web.servlet.ModelAndView
     * @Author: zhangZaiFa
     * @Date:2019/12/10 16:37
     **/
    @Override
    public ScriptPage<Permission> findPermissionListPage(AjaxPage ajaxPage, Permission permission) {
        permission = asseFindObj(permission, ajaxPage);
        List<Permission> list = permissionDao.findPermissionListPage(permission);
        Integer totalCount = permissionDao.findPermissionListPageCount(permission);
        return asseScriptPage(list, totalCount);
    }

    /**@MethodName: permissionListPage
     * @Description: 查询权限列表
     * @Param: [json]
     * @Return: org.springframework.web.servlet.ModelAndView
     * @Author: zhangZaiFa
     * @Date:2019/12/10 16:37
     **/
    @Override
    public List<Permission> findPermissionList() {
        Permission permission = new Permission();
        permission.setParentId(0L);
        permission.setPageSize(null);
        return permissionDao.findPermissionListPage(permission);
    }


    /**@MethodName: pathListPage
     * @Description: 查询接口PATH
     * @Param: [ajaxPage, path]
     * @Return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Path>
     * @Author: zhangZaiFa
     * @Date:2019/12/11 18:21
     **/
    @Override
    public ScriptPage<Path> findPathListPage(AjaxPage ajaxPage, Path path) {
        path = asseFindObj(path, ajaxPage);
        List<Path> list = pathDao.findPathListPage(path);
        Integer totalCount = pathDao.findPathListPageCount(path);
        return asseScriptPage(list, totalCount);
    }

    /**@MethodName: savePath
     * @Description: 添加Path
     * @Param: [path]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/12 15:32
     **/
    @Override
    public Data savePath(Path path) {
        pathDao.savePath(path);
        return Data.isSuccess();
    }

    /**@MethodName: modifyPath
     * @Description:修改Path
     * @Param: [path]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/12 15:33
     **/
    @Override
    public Data modifyPath(Path path) {
        pathDao.modifyPath(path);
        if (path.getId() != null) {
            PermissionPath permissionPath = new PermissionPath();
            permissionPath.setPathId(path.getId());
            Integer perType = path.getProjectType();
            if (perType != null ){
                if (perType == 3){
                    perType = 1;
                }else if(perType == 4){
                    perType = 3;
                }else {
                    perType = 1;
                }
            }
            permissionPath.setPerType(perType);
            permissionPath.setUrl(path.getUrl());
            permissionPathDao.modifyPermissionPath(permissionPath);
        }
        return Data.isSuccess();
    }

    /**@MethodName: delPath
     * @Description: 删除Path
     * @Param: [path]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/12 15:32
     **/
    @Override
    public Data delPath(Path path) {
        pathDao.delPath(path);
        PermissionPath permissionPath = new PermissionPath();
        permissionPath.setPathId(path.getId());
        permissionPathDao.deletePermissionPath(permissionPath);
        return Data.isSuccess();
    }

    /**@MethodName: savePermission
     * @Description: 保存权限
     * @Param: [permission]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/11 16:33
     **/
    @Override
    public Data savePermission(Permission permission) {
        permissionDao.savePermission(permission);
        return Data.isSuccess();
    }

    /**@MethodName: modifyPermission
     * @Description: 修改权限
     * @Param: [permission]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/13 9:46
     **/
    @Override
    public Data modifyPermission(Permission permission) {
        permissionDao.modifyPermission(permission);
        if (permission.getId() != null) {
            PermissionPath permissionPath = new PermissionPath();
            permissionPath.setPermissionId(permission.getId());
            Integer perType = permission.getPerType();
            if (perType != null ){
                if (perType == 1){
                    perType = 3;
                }else if(perType == 2){
                    perType = 1;
                }
            }
            permissionPath.setProjectType(perType);
            permissionPathDao.modifyPermissionPath(permissionPath);
        }
        return Data.isSuccess();
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
    @Override
    public ScriptPage<Permission> findRoleProjectList(AjaxPage ajaxPage,Permission permission){
        permission = asseFindObj(permission, ajaxPage);
        return asseScriptPage(permissionDao.findRoleProjectList(permission), permissionDao.findRoleProjectListTotalCount(permission));
    }

    /**@MethodName: deletePermissionPath
     * @Description: 删除权限Path
     * @Param: [permission]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/13 9:46
     **/
    @Override
    public Data deletePermissionPath(PermissionPath permissionPath) {
        permissionPathDao.deletePermissionPath(permissionPath);
        return Data.isSuccess();
    }

    /**@MethodName: modifyPermissionPath
     * @Description: 修改权限Path
     * @Param: [permissionPath]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/13 9:46
     **/
    @Override
    public Data modifyPermissionPath(PermissionPath permissionPath) {
        permissionPathDao.modifyPermissionPath(permissionPath);
        return Data.isSuccess();
    }

    /**@MethodName: savePermissionPath
     * @Description: 保存权限Path
     * @Param: [permissionPath]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/13 9:47
     **/
    @Override
    public Data savePermissionPath(String json) {
        List<PermissionPath> list = JSONArray.parseArray(json,PermissionPath.class);
        permissionPathDao.deletePermissionPath(list.get(0));
        permissionPathDao.savePermissionPaths(list);
        return Data.isSuccess();
    }

    /**@MethodName: permissionPathListPage
     * @Description: 查询权限URL
     * @Param: [ajaxPage, permissionPath]
     * @Return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.PermissionPath>
     * @Author: zhangZaiFa
     * @Date:2019/12/11 18:10
     **/
    @Override
    public ScriptPage<PermissionPath> findPermissionPathListPage(AjaxPage ajaxPage, PermissionPath permissionPath) {
        permissionPath = asseFindObj(permissionPath, ajaxPage);
        List<PermissionPath> list = permissionPathDao.findPermissionPathListPage(permissionPath);
        Integer totalCount = permissionPathDao.findPermissionPathListPageCount(permissionPath);
        return asseScriptPage(list, totalCount);
    }

    /**@MethodName: findPathList
     * @Description: 查询Path列表
     * @Param: [path]
     * @Return: java.util.List<com.xjt.cloud.admin.manage.entity.project.Path>
     * @Author: zhangZaiFa
     * @Date:2019/12/13 18:07
     **/
    @Override
    public List<Path> findPathList(Path path) {
        List<Path> list = pathDao.findPathList(path);
        return list;
    }

    /**@MethodName: findPermissionTree
     * @Description: 查询权限树结构
     * @Param: [path]
     * @Return: java.util.List<com.xjt.cloud.admin.manage.entity.project.Path>
     * @Author: zhangZaiFa
     * @Date:2020/4/1 14:18
     **/
    @Override
    public List<Path> findPermissionPathTree(Path path) {
        List<Path> list = pathDao.findPermissionPathTree(path);
        return buildTree(list);
    }

    private List<Path> buildTree(List<Path> list){
        List<Path> tree = new ArrayList<>();
        for(Path node:list){
            if(node.getParentId() == 0){
                tree.add(findChild(node,list));
            }
        }
        for (Path node:tree){
            node.setChecked(treeIsChecked(node.getChildren()));
        }
        return tree;
    }

    private boolean treeIsChecked(List<Path> tree){
        boolean checked = true;
        if(CollectionUtils.isNotEmpty(tree)) {
            for (Path node : tree) {
                if (node != null && (node.getChildren() == null || node.getChildren().size() == 0)) {
                    if (!node.getChecked()) {
                        checked = false;
                    }
                } else {
                    node.setChecked(treeIsChecked(node.getChildren()));
                    if (!node.getChecked()) {
                        checked = false;
                    }
                }
            }
        }
        return checked;
    }

    private Path findChild(Path node, List<Path> list){
        for(Path p:list){
            if(p.getParentId().equals(node.getId())){
                if(node.getChildren() == null){
                    node.setChildren(new ArrayList<Path>());
                }
                node.getChildren().add(findChild(p,list));
            }
        }
        return node;
    }

}
