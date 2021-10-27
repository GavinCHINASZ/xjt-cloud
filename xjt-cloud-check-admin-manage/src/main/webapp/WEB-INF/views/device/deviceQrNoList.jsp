<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>二维码添加</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/device/deviceQrNoList.js"></script>
</head>
<!-- 主页面 begin -->
<div  id="common_search" class="easyui-panel" fit="true">
    <!--查询行 开始 -->
    <div id="toolbar" style="padding:5px" >
        <form id="deviceSysSearch_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openCheckQrNoPage();" >添加</a>
            <label>项目：</label><select id="projectSel" ></select>
            <label>巡检点ID：</label><input type="text" id="checkQrNoTxt" style='width:150px'/>
            <a href="javascript:void(0);" onclick="findCheckQrNoList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="checkQrNo_table" style="height: auto" fit="true"></table>
</div>
<!-- 主页面 end -->


<!-- 弹出词典项页面 开始-->
<div id="checkQrNoSaveDialog" class="easyui-dialog " data-options="closed:true,modal:true,title:'项目巡检项管理'" style="width:1200px;height:750px">
    <!--查询行 开始 -->
    <div id="deviceCheckItemRelationToolbarItem" fit="true">
        <form id="deviceCheckItemRelationSearch_form">
            <a href="javascript:void(0);" onclick="downDeviceQrNoModelExcel()" class="easyui-linkbutton" data-options="iconCls:'icon-downward'">模板下载</a>

            <label>&nbsp;&nbsp;&nbsp;批量导入：</label>
            <img src="" name="thumbnail" style='height:80px;' id="thumbnail"/>
            <input type="hidden" name="thumbnail" id="thumbnail_hid"/>
            <input id="thumbnail_file_upload" style="width: 180px;" type="file"/>
            <a href="javascript:void(0);" onclick="uploadDeviceQrNoExcelFile('thumbnail')"
               class="easyui-linkbutton" data-options="iconCls:'icon-upward'">上传</a>
            <br/>
            <br/>
            <table>
                <tbody>
                <tr>
                    <td class="tableTdRight"><span><label>项目：</label></span></td>
                    <td class="tableTdLeft" colspan="2">
                        <select id="projectSel1" ></select>
                    </td>
                </tr>
                <tr>
                    <td class="tableTdRight"><span><label>巡检点ID：</label></span></td>
                    <td class="tableTdLeft">
                        <textarea cols="25" rows="5" id ="checkQrNoTxt1" ></textarea>
                        <span class="star">*</span>
                    </td>
                    <td class="tableTdLeft">
                        <font color="red">一个ID一行</font>
                    </td>
                </tr>
                </tbody>
            </table>
            <a href="javascript:void(0);" onclick="saveCheckQrNo('')" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
        </form>
    </div>
</div>
<!-- 新增修改页面 end-->

<!-- 弹出词典项页面 开始-->
<div id="checkQrNoModifyDialog" class="easyui-dialog " data-options="closed:true,modal:true,title:'项目巡检项管理'" style="width:400px;height:300px">
    <!--查询行 开始 -->
    <div id="deviceQrNoModifyToolbarItem" fit="true">
        <form id="deviceQrNoModifySearch_form">
            <table>
                <tbody>
                <tr>
                    <td class="tableTdRight"><span><label>巡检点ID：</label></span></td>
                    <td class="tableTdLeft">
                        <input type="text" id="qrNoTxt"  name="qrNo" readonly>
                        <span class="star">*</span>
                    </td>
                </tr>
                <tr>
                    <td class="tableTdRight"><span><label>项目：</label></span></td>
                    <td class="tableTdLeft">
                        <select id="projectSel12" name="projectId" ></select>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
<!-- 新增修改页面 end-->

</html>