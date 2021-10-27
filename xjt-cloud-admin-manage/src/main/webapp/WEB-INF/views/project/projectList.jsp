<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>项目管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/project/projectList.js"></script>
</head>
<!-- 主页面 begin -->
<div id="common_search" class="easyui-panel" fit="true">
    <!--查询行 开始 -->
    <div id="toolbar" fit="true">
        <form id="projectSearch_form">
            <label>项目名称：</label><input type="text" id="searchProjectName" style='width:150px'/>
            <label>项目状态：</label>
            <select  id="searchProjectStatus" style='width:150px'>
                <option value="">ALL</option>
                <option value="0">待审核</option>
                <option value="1">已驳回</option>
                <option value="2">已通过</option>

            </select>
            <a href="javascript:void(0);" onclick="findProjectList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="project_table" style="height: auto" fit="true"></table>
</div>


<!-- 主页面 end -->

<!-- 项目审核页面 begin-->
<div id="project_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'权限信息',iconCls:'icon-save'"
     style="padding:5px;width:800px;height:800px;">
    <form action="" id="projectReviewRecordForm">
        <input type="hidden" name="id" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>项目名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="projectName" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
            </tr>

            <tr>
                <td class="tableTdRight"><span><label>公司名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="companyName" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'"
                    style="width: 330px">
                </td>
            </tr>

            <tr>
                <td class="tableTdRight"><span><label>地址：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="address" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'"
                    style="width: 330px">
                </td>
            </tr>

            <tr>
                <td class="tableTdRight"><span><label>消防安全责任人：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="fireSafetyOwner" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                </td>
                <td class="tableTdRight"><span><label>联系电话：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <input type="text"  name="fireSafetyOwnerPhone" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                </td>
            </tr>

            <tr>
                <td class="tableTdRight"><span><label>消防安全管理人：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="fireSafetyManager" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                </td>
                <td class="tableTdRight"><span><label>联系电话：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <input type="text"  name="fireSafetyManagerPhone" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>巡检项类型：</label></span></td>
                <td class="tableTdLeft" colspan="5">
                    <select name="checkItemVsType"><option value="1">默认巡检项</option><option value="3">自定义</option></select>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>状态：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="projectStatusDesc" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
                <td class="tableTdRight"><span><label>审核人：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="reviewName" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
                <td class="tableTdRight"><span><label>审核时间：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="reviewTime" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>营业执照：</label></span></td>
                <td class="tableTdLeft" colspan="5">
                    <img src="" name="imageUrl" alt="" width="500px" height="500px"  id="imageUrl">
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>

<div id="projectReview_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'审核',iconCls:'icon-save'"
     style="padding:5px;width:500px;height:300px;">
    <input type="hidden" name="projectId" id="projectIdHid1" />
    <table>
        <tbody>
            <tr>
                <td class="tableTdRight"><span><label>审核状态：</label></span></td>
                <td class="tableTdLeft">
                    <select id="projectStatus" ><option value="2">通过</option><option value="1">驳回</option></select>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>审核意见：</label></span></td>
                <td class="tableTdLeft">
                    <textarea cols="25" rows="5" id ="reviewOpinion" name ="reviewOpinion"></textarea>
                </td>
            </tr>
        </tbody>
    </table>
</div>

<%--记录列表--%>
<div id="projectReviewRecord_search" class="easyui-dialog " data-options="closed:true,modal:true,title:'项目审核记录'"
     style="width:1000px;height:650px">
    <!--查询行 开始 -->
    <div id="projectReviewRecordToolbar" fit="true">
        <form id="projectReviewRecordSearch_form">

        </form>
    </div>
    <!-- 菜单管理 begin-->
    <table id="projectReviewRecord_table" style="height:638px"></table>
</div>

<%-- 报警等级管理列表 --%>
<div id="permissionPath_search" class="easyui-dialog " data-options="closed:true,modal:true,title:'报警等级管理'"
     style="width:1000px;height:650px">
    <input type="hidden" id="projectId"/>

    <!-- 菜单管理 begin-->
    <div class="easyui-panel" style="padding:5px">
        <ul id="tree" class="easyui-tree" data-options="animate:true,checkbox:true"></ul>
    </div>
</div>

<!-- 弹出词典项页面 开始-->
<div id="projectPermissionManagerDialog" class="easyui-dialog nopadding" data-options="closed:true,modal:true,title:'项目权限信息'" style="width:1000px;height:750px">
    <input type="hidden" id="projectIdHid2">
    <!--查询行 开始 -->
    <div id="projectPermissionToolbar" fit="true">
        <form id="search_form">
            <div id="searchtoolCp" style="padding:5px" >
                <label>权限名称：</label><input type="text" id="permissionNameTxt" style='width:150px'/>
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
                <a href="javascript:void(0);" onclick="findProjectPermissionList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </div>
        </form>
    </div>
    <!-- 菜单管理 begin-->
    <table id="projectPermission_table" style="height: auto" fit="true"></table>
</div>

</html>