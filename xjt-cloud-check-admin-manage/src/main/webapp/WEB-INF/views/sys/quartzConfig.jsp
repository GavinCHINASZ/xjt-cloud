<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>定时任务管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/sys/quartzConfig.js"></script>
</head>

<!-- 主页面 begin -->
<div  id="common_search" class="easyui-panel" fit="true">
    <!--查询行 开始 -->
    <div id="toolbar" fit="true">
        <form id="search_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openQuartzConfigSaveEdit();" >添加</a>
        </form>
    </div>

    <table id="quartz_config_table" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 新增修改页面 begin-->
<div id="quartz_config_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'定时任务信息',iconCls:'icon-save'"
     style="padding:5px;width:520px;height:460px;">
    <form action="" id="quartzConfigForm">
        <input type="hidden" name="id" id="quartzConfigId" />
        <input type="hidden" name="status" id="status">
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>分组名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="group" style="width: 330px" class="easyui-validatebox" data-options="required:true,validType:'length[1,80]'">
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>任务名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="name" style="width: 330px" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>cron：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="cron" style="width: 330px" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>quartzClass：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea name="quartzClass" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 330px;height: 60px;"></textarea>
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>描述：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea name="msg" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 330px;height: 100px;"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

<!-- 弹出词典项页面 开始-->
<div id="itemDialog" class="easyui-dialog nopadding" data-options="closed:true,modal:true,title:'词典项管理'" style="width:1000px;height:750px">
    <input type="hidden" id="quartzConfigId2" name="id">
    <!--查询行 开始 -->
    <div id="toolbarItem" fit="true">
        <form id="search_item_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  onclick="openDictItemSaveEdit();" >添加</a>
        </form>
    </div>
    <table id="quartz_config_item_table" style="height:635px;"></table>
</div>
<!-- 弹出词典项页面 结束 -->

<!-- 新增修改页面 begin-->
<div id="quartz_config_item_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'词典项信息',iconCls:'icon-save'" style="padding:5px;width:700px;height:700px;">
    <form action="" id="quartzConfigItemForm">
        <input type="hidden" name="id" id="quartzConfigItemId" />
        <input type="hidden" name="quartzConfigId" id="quartzConfigId3" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>字典项名字：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="itemName" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>字典项值 ：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="itemValue" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>字典项编码：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="itemCode" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>排序 ：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="sortOrder" data-options="validType:'length[1,50]'">
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>字典项描述 ：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea name="description" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 330px;height: 100px;"></textarea>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>备注  ：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea name="memo" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 330px;height: 100px"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

</html>