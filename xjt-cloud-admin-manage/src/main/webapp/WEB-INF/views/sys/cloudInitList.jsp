<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>初使化管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/sys/cloudInitList.js"></script>

    <link rel="stylesheet" href="/resource/js/kindeditor/themes/default/default.css" />
    <link rel="stylesheet" href="/resource/js/kindeditor/plugins/code/prettify.css" />
    <script charset="utf-8" src="/resource/js/kindeditor/kindeditor.js"></script>
    <script charset="utf-8" src="/resource/js/kindeditor/lang/zh-CN.js"></script>
    <script charset="utf-8" src="/resource/js/kindeditor/plugins/code/prettify.js"></script>
</head>
<!-- 主页面 begin -->
<div  id="common_search" class="easyui-panel" fit="true">
    <!--查询行 开始 -->
    <div id="toolbar" fit="true">
        <form id="search_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openCloudInitSaveEdit();" >添加</a>
            <label>平台类型：</label><select id="cloudInitTypeSel" name="type"></select><span class="select-arrow"></span>
            <label>信息类型：</label><select id="cloudInitInfoTypeSel" name="sourceType"></select><span class="select-arrow"></span>
            <a href="javascript:void(0);" onclick="findCloudInitList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="cloudInit_table" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 新增修改页面 begin-->
<div id="cloudInit_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'初使化信息',iconCls:'icon-save'" style="padding:5px;width:1000px;height:900px;">
    <form action="" id="cloudInitForm">
        <input type="hidden" name="id" id="cloudInitIdHid" />
        <input type="hidden" name="parentId" id="saveParentIdHid" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>平台类型：</label></span></td>
                <td class="tableTdLeft">
                    <select id="saveCloudInitTypeSel" name="cloudType"></select><span class="select-arrow"></span>
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>信息分类：</label></span></td>
                <td class="tableTdLeft">
                    <select id="saveCloudInitInfoTypeSel" name="type"></select><span class="select-arrow"></span>
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>app版本号：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" id="saveAppVersionTxt" name="appVersion" >
                </td>
                <td class="tableTdRight" id="parentTitleTd"><span><label>父名称：</label></span></td>
                <td class="tableTdLeft" id="parentTxtTd">
                    <input type="text" id="parentTxt" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>信息名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="infoName" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>信息编码：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="typeCode" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>顺序：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="sortOrder" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>是否轮播：</label></span></td>
                <td class="tableTdLeft">
                    <label><input type="radio" name="carousel" value="1">是</label>
                    <label><input type="radio" name="carousel" value="2">否</label>
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>标题：</label></span></td>
                <td class="tableTdLeft" colspan="7">
                    <textarea name="title" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 800px;height: 30px;"></textarea>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>关键词：</label></span></td>
                <td class="tableTdLeft" colspan="7">
                    <textarea name="keyword" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 800px;height: 30px;"></textarea>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>缩略图：</label></span></td>
                <td class="tableTdLeft" colspan="7">
                    <img src="" name="imgUrl" style='height:80px;' id="imgUrl"/><br/>
                    <input type="hidden" name="imgUrl" id="imgUrl_hid" />
                    <input id="imgUrl_file_upload" type="file"/>
                    <a href="javascript:void(0);" onclick="uploadJs('imgUrl','cloudInit')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">上传</a>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>简介：</label></span></td>
                <td class="tableTdLeft" colspan="7">
                    <textarea name="intro" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 800px;height: 100px;"></textarea>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>信息内容：</label></span></td>
                <td class="tableTdLeft" colspan="7">
                    <textarea rows="3" style="width:800px;" id="cloudInitContent" name="content" class="easyui-validatebox" data-options="required:true,validType:'length[1,1000000]'" invalidMessage="最大长度不能超过1000000"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

<!-- 弹出词典项页面 开始-->
<div id="itemDialog" class="easyui-dialog nopadding" data-options="closed:true,modal:true,title:'初使化项管理'" style="width:1000px;height:750px">
    <input type="hidden" name="parentId" id="parentIdHid" />
    <input type="hidden" id="cloudTypeHid" />
    <input type="hidden" id="typeHid" />
    <!--查询行 开始 -->
    <div id="toolbarItem" fit="true">
        <form id="search_item_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  onclick="openCloudInitItemSaveEdit();" >添加</a>
            <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
            <label>父类名称：</label><input type="text" id="parentNameHid" style="background-color:transparent;border:0;" readonly>
            <label>平台类型：</label><input type="text" id="cloudTypeDescHid" style="background-color:transparent;border:0;" readonly>
            <label>信息分类：</label><input type="text" id="typeDescHid" style="background-color:transparent;border:0;" readonly>
            <label>app版本号：</label><input type="text" id="appVersionHid" style="background-color:transparent;border:0;" readonly>
        </form>
    </div>
    <table id="cloudInit_item_table" fit="true"></table>
</div>
<!-- 弹出词典项页面 结束 -->

<script>
    var editor;
    $(function() {
        editor = KindEditor.create('textarea[name="content"]',{resizeType : 1,width:"800px",height:"600px",
            uploadJson:"/kindeditorUploadFtpFile?packageName=cloudInit",
            imgFile:"file",
            //上传文件后执行的回调函数,获取上传图片的路径
            afterUpload: function (url) {
                var a = $("#cloudInitContent").val();
                if (a.length > 0) {
                    $("#cloudInitContent").val(a + "," + url);
                } else {
                    $("#cloudInitContent").val(url);
                }
            },
            afterChange:function(){
                this.sync();
            },afterBlur:function(){
                this.sync();
            }});
    });

</script>

</html>