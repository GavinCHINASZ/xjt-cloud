<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>厂商管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/inventory/producerList.js"></script>

</head>
<!-- 主页面 begin -->
<div  id="common_search" class="easyui-panel" fit="true">
    <div id="toolbar" style="padding:5px" >
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openProducerSaveEdit();" >添加</a>
        <label> 厂商名称：</label><input type="text" name="producerName" id="producerNameTxt"></span>
        <a href="javascript:void(0);" onclick="findProducerList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    </div>
    <table id="producer_table" style="height: auto" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 新增修改页面 begin-->
<div id="producer_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'版本信息',iconCls:'icon-save'" style="padding:5px;width:450px;height:300px;">
    <form action="" id="producerForm">
        <input type="hidden" name="id" id="producerId" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>厂商名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="producerName" class="easyui-validatebox" data-options="required:true,validType:'length[1,30]'">
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>联系人：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="contacts" class="easyui-validatebox" data-options="required:true,validType:'length[1,20]'">
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>联系电话：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="phone" class="easyui-validatebox" data-options="required:true,validType:'length[1,15]'">
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>厂商代码：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="codeKey" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>备注：</label></span></td>
                <td class="tableTdLeft" colspan="10">
                    <textarea id="updateContentTxt" name="memo"  data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 330px;height: 100px"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

</html>