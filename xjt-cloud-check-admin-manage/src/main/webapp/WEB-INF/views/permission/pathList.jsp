<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Path管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/permission/pathList.js"></script>
</head>
<!-- 主页面 begin -->
<div  id="common_search" class="easyui-panel" fit="true">
    <!--查询行 开始 -->
    <div id="pathToolbar" fit="true">
        <form id="search_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openPathSaveEdit(1);" >添加</a>
            <label>接口名：</label><input type="text" id="pathNameTxt1" style='width:150px'/>
            <label>接口url：</label><input type="text" id="urlTxt1" style='width:150px'/>
            <a href="javascript:void(0);" onclick="findPathList1()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="path_table" style="height: auto" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 新增修改页面 begin-->
<div id="path_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'Path信息',iconCls:'icon-save'" style="padding:5px;width:500px;height:300px;">
    <form action="" id="pathForm">
        <input type="hidden" name="id" id="id" />
        <input type="hidden" name="parentId" id="parentId" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="pathName" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'"
                    style="width: 330px">
                    <span class="star">*</span>
                </td>
            <tr>
            <tr>
                <td class="tableTdRight"><span><label>排序号：</label></span></td>
                <td class="tableTdLeft">
                    <input type="number" name="orderNum" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
            <tr>
                <td class="tableTdRight"><span><label>类型：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <select name = "type" id = "type">

                    </select>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>Path类型：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <select name = "projectType">
                        <option value = "1">PC</option>
                        <option value = "2">APP</option>
                        <option value = "3">管理后台</option>
                        <option value = "4">项目外</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>URL：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <input name="url" data-options="validType:'length[0,100]'"  class="easyui-validatebox" style="width: 330px">
                </td>
            </tr>

            </tbody>
        </table>
    </form>
</div>


<%--菜单列表--%>
<div id="pathMenu_search" class="easyui-dialog " data-options="closed:true,modal:true,title:'菜单管理'" style="width:1000px;height:650px">
    <!--查询行 开始 -->
    <input type="hidden" id="pathId">
    <div id="pathMenuToolbar" fit="true">
        <form id="pathMenuSearch_item_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openPathSaveEdit(2);" >添加</a>
            <label>接口名：</label><input type="text" id="pathNameTxt2" style='width:150px'/>
            <label>接口url：</label><input type="text" id="urlTxt2" style='width:150px'/>
            <a href="javascript:void(0);" onclick="findPathList2()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <!-- 菜单管理 begin-->
    <table id="pathMenu_table" style="height: auto"></table>
</div>



<%--菜单功能列表--%>
<div id="pathMenuFun_search" class="easyui-dialog " data-options="closed:true,modal:true,title:'菜单功能管理'" style="width:1000px;height:650px">
    <input type="hidden" id="pathMenuId">
    <!--查询行 开始 -->
    <div id="pathMenuFunToolbar" fit="true">
        <form id="pathMenuFunSearch_item_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openPathSaveEdit(3);" >添加</a>
            <label>接口名：</label><input type="text" id="pathNameTxt3" style='width:150px'/>
            <label>接口url：</label><input type="text" id="urlTxt3" style='width:150px'/>
            <a href="javascript:void(0);" onclick="findPathList3()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <!-- 菜单管理 begin-->
    <table id="pathMenuFun_table" style="height: auto"></table>
</div>




</html>