<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>库存管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/inventory/inventoryList.js"></script>
</head>
<!-- 主页面 begin -->
<div  id="common_search" class="easyui-panel" fit="true">
    <!--查询行 开始 -->
    <div id="toolbar" style="padding:5px" >
        <label>厂商：</label><select id="producerSel" onchange="initProductTypeList('productSel','producerSel')" ></select><span class="select-arrow"></span>
        <label>产品类型：</label><select id="productTypeSel" ></select><span class="select-arrow"></span>
        <label>产品：</label><select id="productSel" ></select><span class="select-arrow"></span>
        <label>订单号：</label><input type="text" id="orderNumTxt" style='width:150px'/>
        <a href="javascript:void(0);" onclick="findInventoryList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    </div>
    <table id="inventory_table" style="height: auto" fit="true"></table>
</div>
<!-- 主页面 end -->

</html>