<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>版本管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/app/versionList.js"></script>

</head>
<!-- 主页面 begin -->
<div  id="common_search" class="easyui-panel" fit="true">
    <div id="searchVersion" style="padding:5px" >
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openVersionSaveEdit();" >添加</a>
        <label>类型：</label><select id="typeSel" name="type"></select><span class="select-arrow"></span>
        <label>来源：</label><select id="sourceTypeSel" name="sourceType"></select><span class="select-arrow"></span>
        <a href="javascript:void(0);" onclick="findVersionList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    </div>
    <table id="version_table" style="height: auto" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 新增修改页面 begin-->
<div id="version_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'版本信息',iconCls:'icon-save'" style="padding:5px;width:700px;height:700px;">
    <form action="" id="versionForm">
        <input type="hidden" name="id" id="versionId" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>版本：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="version" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
                <td class="tableTdRight"><span><label>版本号：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="versionNum" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>版本大小：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="versionSize" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>类型：</label></span></td>
                <td class="tableTdLeft">
                    <select id="saveTypeSel" name="type"></select><span class="select-arrow"></span>
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>来源：</label></span></td>
                <td class="tableTdLeft">
                    <select id="saveSourceTypeSel" name="sourceType"></select><span class="select-arrow"></span>
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>下载路径：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="url"  >
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>发布用户：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="createUserName" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
                <td class="tableTdRight"><span><label>文件大小：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="fileSize" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">M
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>更新类型：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <select id="updateTypeSel" name="updateType" class="form-control">
                        <option value="1">推送一次</option>
                        <option value="2">每次推送</option>
                        <option value="3">强制更新</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>更新主题：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea id="versionTitleTxt" name="versionTitle"  data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 330px;height: 100px"></textarea>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>更新内容：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea id="updateContentTxt" name="updateContent"  data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 330px;height: 100px"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

</html>