<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>设备系统管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/app/faqsList.js"></script>

</head>
<!-- 主页面 begin -->
<div  id="Faqs_search" class="easyui-panel" fit="true">
    <!--查询行 开始 -->
    <div id="FaqsToolbar" fit="true">
        <form id="FaqsSearch_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openFaqsSaveEdit();" >添加</a>
            <label>系统名称：</label><input type="text" id="FaqsNameTxt" style='width:150px'/>
            <label>系统名称首写字母：</label><input type="text" id="pinYinInitialsTxt" style='width:150px'/>
            <a href="javascript:void(0);" onclick="findFaqsList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="Faqs_table" style="height: auto" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 新增修改页面 begin-->
<div id="Faqs_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'设备系统信息',iconCls:'icon-save'" style="padding:5px;width:500px;height:300px;">
    <form action="" id="FaqsForm">
        <input type="hidden" name="id" id="FaqsId" />
        <input type="hidden" name="type" value="1" id="FaqsTypeHid" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="FaqsName" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>拼音首写字母：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" id="sysPinYinInitialsTxt" name="pinYinInitials" readonly>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

<!-- 弹出词典项页面 开始-->
<div id="Faqse_search" class="easyui-dialog " data-options="closed:true,modal:true,title:'设备类型管理'" style="width:1000px;height:750px">
    <input type="hidden" id="FaqsId2" name="id">
    <!--查询行 开始 -->
    <div id="FaqseToolbarItem" fit="true">
        <form id="FaqseSearch_item_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openFaqseSaveEdit();" >添加</a>
        </form>
    </div>
    <table id="Faqse_table" style="height:635px;"></table>
</div>
<!-- 弹出词典项页面 结束 -->

<!-- 新增修改页面 begin-->
<div id="Faqse_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'设备类型信息',iconCls:'icon-save'" style="padding:5px;width:1000px;height:800px;">
    <form action="" id="FaqseForm">
        <input type="hidden" name="id" id="FaqseId" />
        <input type="hidden" name="parentId" id="FaqsId3" />
        <input type="hidden" name="type" value="9" id="FaqseTypeHid" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>设备名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="FaqsName" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>拼音首写字母 ：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="pinYinInitials" readonly>
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>是否具有压力监测或液位监测：</label></span></td>
                <td class="tableTdLeft">
                    <label><input type="radio" name="pressureMonitor" value="1">是</label>
                    <label><input type="radio" name="pressureMonitor" value="0">否</label>
                </td>
                <td class="tableTdRight"><span><label>系统端压力 ：</label></span></td>
                <td class="tableTdLeft" >
                    <label><input type="radio" name="sysPressure" value="1">是</label>
                    <label><input type="radio" name="sysPressure" value="0">否</label>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>设备类型 ：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <select id="FaqseSel" name="Faqse"></select><span class="select-arrow"></span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>功能和使用方法  ：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea name="useMethod" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 330px;height: 100px"></textarea>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>描述  ：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea name="description" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 330px;height: 100px"></textarea>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>图片  ：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <img src="" name="imgUrl" style='height:80px;' id="imgUrl"/><br/>
                    <input type="hidden" name="imgUrl" id="imgUrl_hid" />
                    <input id="imgUrl_file_upload" type="file"/>
                    <a href="javascript:void(0);" onclick="uploadJs('imgUrl','faqs')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">上传</a>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

</html>