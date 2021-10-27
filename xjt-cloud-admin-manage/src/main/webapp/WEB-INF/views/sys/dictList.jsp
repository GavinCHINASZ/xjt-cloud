<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>数据词典管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/sys/dictList.js"></script>
</head>
<!-- 主页面 begin -->
<div  id="common_search" class="easyui-panel" fit="true">
    <!--查询行 开始 -->
    <div id="searchtoolDict" fit="true">
        <form id="search_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openDictSaveEdit();" >添加</a>
            <label>父词典名称：</label><input type="text" id="dictNameTxt" style='width:150px'/>
            <label>父词典Code：</label><input type="text" id="dictCodeTxt" style='width:150px'/>
            <label>子词典名称：</label><input type="text" id="dictItemNameTxt" style='width:150px'/>
            <label>子词典Code：</label><input type="text" id="dictItemCodeTxt" style='width:150px'/>
            <a href="javascript:void(0);" onclick="findDictList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="dict_table" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 新增修改页面 begin-->
<div id="dict_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'数据词典信息',iconCls:'icon-save'" style="padding:5px;width:500px;height:300px;">
    <form action="" id="dictForm">
        <input type="hidden" name="id" id="dictId" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="dictName" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'"
                        style="width: 330px">
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>编码：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="dictCode" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'"
                        style="width: 330px">
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>描述：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea name="description" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 330px;height: 100px;"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

<!-- 弹出词典项页面 开始-->
<div id="itemDialog" class="easyui-dialog nopadding" data-options="closed:true,modal:true,title:'词典项管理'" style="width:1000px;height:750px">
    <input type="hidden" id="dictId2" name="id">
    <!--查询行 开始 -->
    <div id="toolbarItem" fit="true">
        <form id="search_item_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  onclick="openDictItemSaveEdit();" >添加</a>
            <label>子词典名称：</label><input type="text" id="dictItemNameTxt1" style='width:150px'/>
            <label>子词典Code：</label><input type="text" id="dictItemCodeTxt1" style='width:150px'/>
            <a href="javascript:void(0);" onclick="findDictItemList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="dict_item_table" style="height:635px;"></table>
</div>
<!-- 弹出词典项页面 结束 -->

<!-- 新增修改页面 begin-->
<div id="dict_item_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'词典项信息',iconCls:'icon-save'" style="padding:5px;width:720px;height:700px;">
    <form action="" id="dictItemForm">
        <input type="hidden" name="id" id="dictItemId" />
        <input type="hidden" name="dictId" id="dictId3" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>字典项名字：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="itemName" style="width: 220px" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>

                <td class="tableTdRight"><span><label>字典项值 ：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="itemValue" style="width: 220px" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>字典项编码：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="itemCode" style="width: 220px" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>

                <td class="tableTdRight"><span><label>排序 ：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="sortOrder" style="width: 220px" data-options="validType:'length[1,50]'">
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>字典项描述 ：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea name="description" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 540px;height: 90px;"></textarea>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>备注  ：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea name="memo" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 540px;height: 90px"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

</html>