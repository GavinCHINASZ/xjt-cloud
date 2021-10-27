<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>资讯信息管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/info/infoManageList.js"></script>

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
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openInfoManageEdit();" >添加</a>
            <label>平台类型：</label><select id="cloudTypeSel"></select><span class="select-arrow"></span>
            <label>页面类型：</label><select id="pageTypeSel"></select><span class="select-arrow"></span>
            <label>信息类型：</label><select id="infoTypeSel"></select><span class="select-arrow"></span>
            <a href="javascript:void(0);" onclick="findInfoManageList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="infoManage_table" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 新增修改页面 开始-->
<div id="infoManageDialog" class="easyui-dialog nopadding" data-options="closed:true,modal:true,title:'资讯信息管理'" style="width:1000px;height:750px">
    <form action="" id="infoManageForm">
        <input type="hidden" name="id" id="idHid" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>平台类型：</label></span></td>
                <td class="tableTdLeft">
                    <select id="cloudTypeSel1" name="cloudType"></select><span class="select-arrow"/>
                </td>
                <td class="tableTdRight"><span><label>页面类型：</label></span></td>
                <td class="tableTdLeft">
                    <select id="pageTypeSel1" name="pageType"></select><span class="select-arrow"/>
                </td>
                <td class="tableTdRight"><span><label>信息类型：</label></span></td>
                <td class="tableTdLeft">
                    <select id="infoTypeSel1" name="infoType"></select><span class="select-arrow"/>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>图片高：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="imgHeight" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                </td>
                <td class="tableTdRight"><span><label>图片宽：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <input type="text"  name="imgWidth" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 结束 -->

<!-- 弹出词典项页面 开始-->
<div id="itemDialog" class="easyui-dialog nopadding" data-options="closed:true,modal:true,title:'资讯内容管理'" style="width:1000px;height:750px">
    <input type="hidden" name="infoManageId" id="infoManageIdHid" />
    <input type="hidden" name="cloudType" id="cloudTypeHid2" />
    <input type="hidden" name="pageType" id="pageTypeHid" />
    <input type="hidden" name="infoType" id="infoTypeHid" />
    <!--查询行 开始 -->
    <div id="toolbarItem" fit="true">
        <form id="search_item_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  onclick="openInfoContentEdit();" >添加</a>
            <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
            <label>平台类型：</label><input type="text" id="cloudTypeTxt" style="background-color:transparent;border:0;" readonly>
            <label>页面类型：</label><input type="text" id="pageTypeTxt" style="background-color:transparent;border:0;" readonly>
            <label>信息类型：</label><input type="text" id="infoTypeTxt" style="background-color:transparent;border:0;" readonly>
            <a href="javascript:void(0);" onclick="findInfoContentList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="infoContent_table" fit="true"></table>
</div>
<!-- 弹出词典项页面 结束 -->

<!-- 新增修改页面 begin-->
<div id="infoContent_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'资讯内容',iconCls:'icon-save'" style="padding:5px;width:1000px;height:900px;">
    <form action="" id="infoContentForm">
        <input type="hidden" name="id" id="infoContentHid" />
        <input type="hidden" name="infoManageId" id="infoManageIdHid1" />
        <input type="hidden" name="cloudType" id="cloudTypeHid3" />
        <input type="hidden" name="pageType" id="pageTypeHid1" />
        <input type="hidden" name="infoType" id="infoTypeHid1" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>平台类型：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="cloudTypeDesc" id="cloudTypeTxt3" style="background-color:transparent;border:0;" readonly>
                </td>
                <td class="tableTdRight"><span><label>页面类型：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="pageTypeDesc" id="pageTypeTxt1" style="background-color:transparent;border:0;" readonly>
                </td>
                <td class="tableTdRight"><span><label>信息类型：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="infoTypeDesc" id="infoTypeTxt1" style="background-color:transparent;border:0;" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>标题：</label></span></td>
                <td class="tableTdLeft" colspan="5">
                    <input type="text" name="title" class="easyui-validatebox" style="width: 500px;" data-options="required:true,validType:'length[1,500]'">
                    <span class="star">*</span>
                </td>
            </tr>
            </tbody>
                <td class="tableTdRight"><span><label>排序：</label></span></td>
                <td class="tableTdLeft" colspan="5">
                    <input type="text"  name="sort" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>属性1：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="property1" class="easyui-validatebox" style="width: 200px;" data-options=validType:'length[1,500]'">
                </td>
                <td class="tableTdRight"><span><label>属性2：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="property2" class="easyui-validatebox" style="width: 200px;" data-options="validType:'length[1,50]'">
                </td>
                <td class="tableTdRight"><span><label>属性3：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="property3" class="easyui-validatebox" style="width: 200px;" data-options="validType:'length[1,500]'">
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>属性4：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="property4" class="easyui-validatebox" style="width: 200px;" data-options="validType:'length[1,50]'">
                </td>
                <td class="tableTdRight"><span><label>属性5：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="property5" class="easyui-validatebox" style="width: 200px;" data-options="validType:'length[1,500]'">
                </td>
                <td class="tableTdRight"><span><label>属性6：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="property6" class="easyui-validatebox" style="width: 200px;" data-options="validType:'length[1,50]'">
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>简介：</label></span></td>
                <td class="tableTdLeft" colspan="5">
                    <textarea name="intro" data-options="required:true,validType:'length[0,300]'"  class="z_textarea" style="width: 800px;height: 100px;"></textarea>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>第三方url：</label></span></td>
                <td class="tableTdLeft" colspan="5">
                    <input type="text"  name="url" class="easyui-validatebox" style="width: 500px;" data-options="required:true,validType:'length[1,300]'">
                    <span class="star">有第三方url时,内容为第三方网站</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>缩略图：</label></span></td>
                <td class="tableTdLeft" colspan="7">
                    <img src="" name="thumbnail" style='height:80px;' id="thumbnail"/><br/>
                    <input type="hidden" name="thumbnail" id="thumbnail_hid" />
                    <input id="thumbnail_file_upload" type="file"/>
                    <a href="javascript:void(0);" onclick="uploadJs('thumbnail','info')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">上传</a>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>信息内容：</label></span></td>
                <td class="tableTdLeft" colspan="7">
                    <textarea rows="3" style="width:800px;" id="infoContentContent" name="content" class="easyui-validatebox" data-options="required:true,validType:'length[1,1000000]'" invalidMessage="最大长度不能超过1000000"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->



<script>
    var editor;
    $(function() {
        editor = KindEditor.create('textarea[name="content"]',{resizeType : 1,width:"800px",height:"600px",
            uploadJson:"/kindeditorUploadFtpFile?packageName=info",
            imgFile:"file",
            //上传文件后执行的回调函数,获取上传图片的路径
            afterUpload: function (url) {
                var a = $("#infoContentContent").val();
                if (a.length > 0) {
                    $("#infoContentContent").val(a + "," + url);
                } else {
                    $("#infoContentContent").val(url);
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