<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>角色权限管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/permission/roleList.js"></script>
</head>
<!-- 主页面 begin -->
<div  id="common_search" class="easyui-panel" fit="true">
    <!--查询行 开始 -->
    <div id="toolbar" fit="true">
        <form id="search_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openRoleSaveEdit();" >添加</a>
            <!--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-config" plain="true" onclick="openDefaultRoleSaveEdit();" >默认权限</a>-->
            <label>角色名称：</label><input type="text"  id="roleNameTxt"></span>
            <label>平台类型：</label><select id="cloudTypeSel"><option value="">ALL</option><option value="1">管理后台</option><option value="2">PC</option><option value="3">APP</option></select><span class="select-arrow"/>
            <label>项目名称：</label><input type="text"  id="projectNameTxt"></span>
            <a href="javascript:void(0);" onclick="findRoleList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="role_table" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 新增修改页面 begin-->
<div id="role_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'用户信息',iconCls:'icon-save'" style="padding:5px;width:500px;height:300px;">
    <form action="" id="roleForm">
        <input type="hidden" name="id" id="roleIdHid" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>角色名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" id="roleNameTxt1" name="roleName"><span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>平台类型：</label></span></td>
                <td class="tableTdLeft">
                    <select id="cloudTypeSel2" name="sourceType"><option value=""></option><option value="1">管理后台</option><option value="2">PC</option><option value="3">APP</option></select>
                    <span class="star">*</span>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

<!-- 弹出词典项页面 开始-->
<div id="roleRoleDialog" class="easyui-dialog nopadding" data-options="closed:true,modal:true,title:'角色权限信息'" style="width:1500px;height:750px">
    <input type="hidden" name="id" id="roleIdHid1" />
    <input type="hidden" name="sourceType" id="sourceTypeHid" />
    <input type="hidden" name="projectId" id="projectIdHid" />
    <div id="pkgDiv" class="easyui-tabs" fit="true">
        <div title="已关联权限" style="padding:0;">
            <div id="searchtoolRelationRole" style="padding:5px" fit="true">
                <label>菜单名称：</label><input type="text" id="menuNameTxt"></span>
                <label>权限名称：</label><input type="text" id="permissionNameTxt3"></span>
                <label>平台类型：</label><select id="cloudTypeSel3"><option value="">ALL</option><option value="1">管理后台</option><option value="2">PC</option><option value="3">APP</option></select><span class="select-arrow"/>
                <a href="javascript:void(0);" onclick="findRoleRelationRoleList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </div>
            <table id="relation_permissionIds_table" fit="true"></table>
        </div>
        <div title="未关联权限" style="padding:0">
            <div id="searchtoolRole" style="padding:5px" fit="true">
                <label>菜单名称：</label><input type="text" id="menuNameTxt1"></span>
                <label>权限名称：</label><input type="text" id="permissionNameTxt"></span>
                <label>平台类型：</label><select id="cloudTypeSel1"><option value="">ALL</option><option value="1">管理后台</option><option value="2">PC</option><option value="3">APP</option></select><span class="select-arrow"/>
                <a href="javascript:void(0);" onclick="findRoleNotRelationRoleList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </div>
            <table id="not_relation_permissionIds_table" fit="true"></table>
        </div>
    </div>
</div>
<!-- 弹出词典项页面 结束 -->

<!-- 弹出词典项页面 开始-->
<div id="projectUserManagerDialog" class="easyui-dialog nopadding" data-options="closed:true,modal:true,title:'项目用户管理'" style="width:1000px;height:750px">
    <input type="hidden" name="id" id="roleIdHid2" />
    <div id="projectUserManagerDiv" class="easyui-tabs" fit="true">
        <div title="已关联用户" style="padding:0;">
            <div id="projectManagerToolRole" style="padding:5px" fit="true">
                <span><label>项目：</label><select id="projectSel" ></select><span class="select-arrow"></span>
                <label>姓名：</label><input type="text"  id="userNameTxt"></span>
                <label>账号：</label><input type="text"  id="loginNameTxt"></span>
                <label>手机号码：</label><input type="text" id="phoneTxt"></span>
                <a href="javascript:void(0);" onclick="findRoleRelationProjectUserList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delRelationProjectManagerRole();" >删除</a>
            </div>
            <table id="projectUserManager_table" fit="true"></table>
        </div>
        <div title="未关联用户" style="padding:0">
            <div id="notProjectManagerToolRole" style="padding:5px" fit="true">
                <span><label>项目：</label><select id="projectSel1" ></select><span class="select-arrow"></span>
                <label>姓名：</label><input type="text"  id="userNameTxt1"></span>
                <label>账号：</label><input type="text"  id="loginNameTxt1"></span>
                <label>手机号码：</label><input type="text" id="phoneTxt1"></span>
                <a href="javascript:void(0);" onclick="findRoleNotRelationProjectUserList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="relationProjectManagerRole();" >关联</a>
            </div>
            <table id="not_projectUserManager_table" fit="true"></table>
        </div>
    </div>
</div>
<!-- 弹出词典项页面 结束 -->

</html>