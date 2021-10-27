<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用户管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/sys/userList.js"></script>
</head>
<!-- 主页面 begin -->
<div  id="common_search" class="easyui-panel" fit="true">
    <!--查询行 开始 -->
    <div id="toolbar" fit="true">
        <form id="search_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openUserSaveEdit();" >添加</a>
            <label>姓名：</label><input type="text"  id="userNameTxt"></span>
            <label>账号：</label><input type="text"  id="loginNameTxt"></span>
            <label>手机号码：</label><input type="text" id="phoneTxt"></span>
            <a href="javascript:void(0);" onclick="findUserList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="user_table" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 新增修改页面 begin-->
<div id="user_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'用户信息',iconCls:'icon-save'" style="padding:5px;width:500px;height:300px;">
    <form action="" id="userForm">
        <input type="hidden" name="id" id="userId" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>账号：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="loginName">
                </td>
                <td class="tableTdRight"><span><label>手机号码：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="phone">
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>姓名：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <input type="text" name="userName">
                </td>
            </tr>
            <tr id="userStatusTr">
                <td class="tableTdRight"><span><label>账号修改状态：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="modifyStatusDesc" readonly>
                </td>
                <td class="tableTdRight"><span><label>状态：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="statusDesc" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>头像：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <img src="" name="headPortrait" style='height:80px;' id="headPortrait"/><br/>
                    <input type="hidden" name="headPortrait" id="headPortrait_hid" />
                    <input id="headPortrait_file_upload" type="file"/>
                    <a href="javascript:void(0);" onclick="uploadJs('headPortrait','info')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">上传</a>
                    <img name="" alt="">
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

<!-- 弹出词典项页面 开始-->
<div id="userRoleDialog" class="easyui-dialog nopadding" data-options="closed:true,modal:true,title:'用户后台角色管理'" style="width:1000px;height:750px">
    <input type="hidden" name="id" id="userIdHid" />
    <div id="pkgDiv" class="easyui-tabs" fit="true">
        <div title="已关联角色" style="padding:0;">
            <table id="user_relation_role_table" style="height:380px;"></table>
        </div>
        <div title="未关联角色" style="padding:0">
            <div id="searchtoolRole" style="padding:5px" fit="true">
                <label>角色名称：</label><input type="text" id="roleNameTxt"></span>
                <a href="javascript:void(0);" onclick="findNotRelationRoleList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </div>
            <table id="user_not_relation_role_table" style="height:335px;"></table>
        </div>
    </div>
</div>
<!-- 弹出词典项页面 结束 -->


</html>