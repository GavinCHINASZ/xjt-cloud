<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>设备系统管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/report/reportSystemList.js"></script>
</head>

<!-- 主页面 begin -->
<div id="common_search" class="easyui-panel" fit="true">
    <!-- 树 -->
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'west'" style="width:260px">
            <ul id="ttOne" class="easyui-tree">
                <li>
                    <span>GA587</span>
                    <ul>
                        <li>
                            <span>Sub Folder 1</span>
                            <ul>
                                <li><span><a href="#">File 11</a></span></li>
                                <li><span>File 12</span></li>
                                <li><span>File 13</span></li>
                            </ul>
                        </li>
                    </ul>
                </li>
            </ul>

            <ul id="ttTwo" class="easyui-tree">
                <li>
                    <span>GB25201</span>
                    <ul>
                        <li>
                            <span>Sub Folder 1</span>
                            <ul>
                                <li><span><a href="#">File 11</a></span></li>
                                <li><span>File 12</span></li>
                                <li><span>File 13</span></li>
                            </ul>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
        <%-- 表格数据 --%>
        <div data-options="region:'center'">
            <div id="toolbar" fit="true">
                <form id="search_form">
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openReportSystemAdd();" >添加</a>
                    <label>名称：</label><input type="text" id="itemNameTxt" style='width:150px'/>
                    <a href="javascript:void(0);" onclick="findReportSystemList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                </form>
            </div>
            <table id="reportSystem_table" style="height: auto" fit="true"></table>
        </div>
    </div>
</div>
<!-- 主页面 end -->

<!-- 修改 页面 begin-->
<div id="reportSystem_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'编辑 报表巡查项目',iconCls:'icon-save'"
     style="padding:5px; width:500px; height:300px;">
    <form action="" id="reportSystemForm">
        <input type="hidden" name="id" id="reportSystemId" />
        <table>
            <tbody>
                <tr>
                    <td class="tableTdRight"><span><label>名称：</label></span></td>
                    <td class="tableTdLeft">
                        <input type="text" name="itemName" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                        <span class="star">*</span>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 修改 页面 end-->


<!-- 添加页面 begin-->
<div id="add_reportSystem_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'新建 报表巡查项目',iconCls:'icon-save'"
     style="padding:5px; width:1000px; height:800px;">
    <form action="" id="deviceTypeForm">
        <input type="hidden" name="id" id="deviceTypeId" />
        <input type="hidden" name="parentId" id="deviceSysId3" />
        <input type="hidden" name="type" value="9" id="deviceTypeTypeHid" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>报表名称 ：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <select id="reportNameSel" name="reportName"></select><span class="select-arrow"></span>
                </td>
            </tr>

            <tr>
                <td class="tableTdRight"><span><label>类型：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="deviceSysName" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
            </tr>

            <tr>
                <td class="tableTdRight"><span><label>名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="deviceSysName" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
            </tr>

            <tr>
                <td class="tableTdRight"><span><label>排序 ：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="sortNo" readonly>
                    <span class="star">*</span>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 添加页面 end-->

</html>