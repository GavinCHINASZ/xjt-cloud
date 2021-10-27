<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>权限管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/permission/permissionList.js"></script>
</head>
<!-- 主页面 begin -->
<div id="common_search" class="easyui-panel" fit="true">
    <!--查询行 开始 -->
    <div id="toolbar" fit="true">
        <form id="search_form">
            <div id="searchtoolCp" style="padding:5px" >
                <label>权限名称：</label><input type="text" id="permissionNameTxt" style='width:150px'/>
                <label>别名：</label><input type="text" id="signTxt" style='width:150px'/>
                <label>父权限：</label><select id="parentPermissionSel" ></select><span class="select-arrow"></span>
                <label>平台类型：</label><select id="perTypeSel">
                    <option value="">ALL</option>
                    <option value="1">后台管理权限</option>
                    <option value="2"> 项目权限</option>
                </select>
                <label>权限类型：</label><select id="permissionSel">
                    <option value="">ALL</option>
                    <option value="1">模块</option>
                    <option value="2">菜单</option>
                    <option value="3">功能</option>
                </select>
                <a href="javascript:void(0);" onclick="findPermissionList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </div>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openPermissionSaveEdit();">添加</a>
        </form>
    </div>
    <table id="permission_table" style="height: auto" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 权限新增修改页面 begin-->
<div id="permission_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'权限信息',iconCls:'icon-save'"
     style="padding:5px;width:500px;height:500px;">
    <form action="" id="permissionForm">
        <input type="hidden" name="id" id = "permissionIdHid"/>
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>权限名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="permissionName" class="easyui-validatebox"
                           data-options="required:true,validType:'length[1,50]'" style="width: 200px;">
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>所属上级：</label></span></td>
                <td class="tableTdLeft">
                    <select id="parentPermissionSel1" name="parentId" style="width: 200px;"></select><span class="select-arrow"></span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>别名：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="sign" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'" style="width: 200px;">
                    <span class="star">*</span>
                </td>
            </tr>

            <tr>
                <td class="tableTdRight"><span><label>排序：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="sort" class="easyui-validatebox" data-options="validType:'length[1,50]'" style="width: 200px;">
                </td>
            </tr>

            <tr>
                <td class="tableTdRight"><span><label>类型：</label></span></td>
                <td class="tableTdLeft">
                    <select name="permissionType" style="width: 200px;">
                        <option value="3">功能</option>
                        <option value="2">菜单</option>
                        <option value="1">模块</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td class="tableTdRight"><span><label>权限类型：</label></span></td>
                <td class="tableTdLeft">
                    <select name="perType" style="width: 200px;">
                        <option value="2">项目权限</option>
                        <option value="1">后台管理权限</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>默认选择类型：</label></span></td>
                <td class="tableTdLeft">
                    <select name="type" style="width: 200px;">
                        <option value="1">是</option>
                        <option value="2">否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>备注：</label></span></td>
                <td class="tableTdLeft">
                    <textarea name="memo" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 330px;height: 100px"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>

<%--权限Path列表--%>
<div id="permissionPath_search" class="easyui-dialog " data-options="closed:true,modal:true,title:'权限Path管理'"
     style="width:1000px;height:650px">
    <input type="hidden" name="permissionId" id = "permissionId"/>
    <input type="hidden" name="perType" id="perType">

<%--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openPermissionPathSaveEdit(2);">添加</a>--%>

    <!-- 菜单管理 begin-->
    <div class="easyui-panel" style="padding:5px">
        <ul id="tree" class="easyui-tree" data-options="animate:true,checkbox:true"></ul>
    </div>
</div>

<!-- 弹出词典项页面 开始-->
<div id="permissionProject_search" class="easyui-dialog nopadding" data-options="closed:true,modal:true,title:'项目权限信息'" style="width:800px;height:900px">
    <input type="hidden" id="permissionId1">
    <!--查询行 开始 -->
    <div id="projectPermissionToolbar" fit="true">
        <form id="permissionProjectSearch_form">
                <label><font color="red">全部上下线与查询和选择无关，会上下线所有项目。上下线以选择为准！</font></label><br/>
                <label>权限状态：</label><select id="permissionStatusSel">
                    <option value="">ALL</option>
                    <option value="1">上线</option>
                    <option value="2">下线</option>
                </select>
                <label>项目名称：</label><input type="text" id="projectNameTxt" style='width:150px'/>
                <a href="javascript:void(0);" onclick="findPermissionProjectList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                <a href="javascript:void(0);" onclick="modifyPermissionProjectStatus('',1)" class="easyui-linkbutton" data-options="iconCls:'icon-upward'">上线</a>
                <a href="javascript:void(0);" onclick="modifyPermissionProjectStatus('',2)" class="easyui-linkbutton" data-options="iconCls:'icon-downward'">下线</a>
                <a href="javascript:void(0);" onclick="modifyPermissionProjectStatus('ALL',1)" class="easyui-linkbutton" data-options="iconCls:'icon-upward'">全部上线</a>
                <a href="javascript:void(0);" onclick="modifyPermissionProjectStatus('ALL',2)" class="easyui-linkbutton" data-options="iconCls:'icon-downward'">全部下线</a>
        </form>
    </div>
    <!-- 菜单管理 begin-->
    <table id="permissionProject_table" style="height: auto" fit="true"></table>
</div>
</html>