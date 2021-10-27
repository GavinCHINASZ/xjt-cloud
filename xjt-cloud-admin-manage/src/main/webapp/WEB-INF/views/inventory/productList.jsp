<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>产品管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/inventory/productList.js"></script>
    <script type="text/javascript" src="/resource/js/easyui-datetime.js"></script>

</head>
<!-- 主页面 begin -->
<div  id="common_search" class="easyui-panel" fit="true">
    <div id="toolbar" style="padding:5px" >
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openProductSaveEdit();" >添加</a>
        <label> 产品名称：</label><input type="text" name="productName" id="productNameTxt"></span>
        <label>产品类型：</label><select id="productTypeSel" ></select><span class="select-arrow"></span>
        <label>厂商：</label><select id="producerSel" ></select><span class="select-arrow"></span>
        <a href="javascript:void(0);" onclick="findProductList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    </div>
    <table id="product_table" style="height: auto" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 新增修改页面 begin-->
<div id="product_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'版本信息',iconCls:'icon-save'" style="padding:5px;width:700px;height:700px;">
    <form action="" id="productForm">
        <input type="hidden" name="id" id="productId" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>厂商：</label></span></td>
                <td class="tableTdLeft">
                    <select id="producerSel1" name="producerId" ></select><span class="select-arrow"></span>
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>产品名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="productName" class="easyui-validatebox" data-options="required:true,validType:'length[1,150]'">
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>产品类型：</label></span></td>
                <td class="tableTdLeft">
                    <select id="productTypeSel2" name="productType" ></select><span class="select-arrow"></span>
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>品牌：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="brand" class="easyui-validatebox" data-options="validType:'length[0,150]'">
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>产品型号：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="model" class="easyui-validatebox" data-options="validType:'length[0,150]'">
                </td>
                <td class="tableTdRight"><span><label>规格参数：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <input type="text" name="spec" data-options="required:true,validType:'length[1,150]'">
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>生产日期：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="productionDate" id="productionDateTxt" class="easyui-datetimebox">
                </td>
                <td class="tableTdRight"><span><label>有效期：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="expiryDate" id="expiryDateTxt" class="easyui-datetimebox">
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>接口信息：</label></span></td>
                <td class="tableTdLeft" colspan="10">
                    <textarea name="apiInfo" class="z_textarea" style="width: 330px;height: 200px"></textarea>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>备注：</label></span></td>
                <td class="tableTdLeft" colspan="10">
                    <textarea name="memo" class="z_textarea" style="width: 330px;height: 100px"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

</html>