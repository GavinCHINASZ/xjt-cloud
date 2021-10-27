<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>出库管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/inventory/outStorageList.js"></script>
</head>
<!-- 主页面 begin -->
<div  id="common_search" class="easyui-panel" fit="true">
    <!--查询行 开始 -->
    <div id="toolbar" style="padding:5px" >
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openOutStorageSaveEdit();" >添加</a>
        <label>厂商：</label><select id="producerSel" onchange="initProductTypeList('productSel','producerSel')" ></select><span class="select-arrow"></span>
        <label>产品：</label><select id="productSel" ></select><span class="select-arrow"></span>
        <label>项目：</label><select id="projectSel" ></select><span class="select-arrow"></span>
        <label>订单号：</label><input type="text" id="orderNumTxt" style='width:150px'/>
        <a href="javascript:void(0);" onclick="findOutStorageList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        <a href="javascript:void(0);" onclick="downOutProductModelExcel()" class="easyui-linkbutton" data-options="iconCls:'icon-downward'">模板下载</a>

        <label>&nbsp;&nbsp;&nbsp;批量导入：</label>
        <img src="" name="thumbnail" style='height:80px;' id="thumbnail"/>
        <input type="hidden" name="thumbnail" id="thumbnail_hid"/>
        <input id="thumbnail_file_upload" style="width: 180px;" type="file"/>
        <a href="javascript:void(0);" onclick="uploadOutProducerExcelFile('thumbnail')"
           class="easyui-linkbutton" data-options="iconCls:'icon-upward'">上传</a>
    </div>
    <table id="out_storage_table" style="height: auto" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 新增修改页面 begin-->
<div id="out_storage_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'出库单信息',iconCls:'icon-save'" style="padding:5px;width:500px;height:500px;">
    <form action="" id="out_storageForm">
        <input type="hidden" name="id" id="out_storageId" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>项目：</label></span></td>
                <td class="tableTdLeft">
                    <select id="projectSel1" name="projectId" ></select><span class="select-arrow"></span>
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>订单编号：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="orderNum" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>领用人：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="recipients" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>备注：</label></span></td>
                <td class="tableTdLeft" colspan="10">
                    <textarea name="memo" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 330px;height: 100px;"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

<!-- 弹出词典项页面 开始-->
<div id="out_storage_productDialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'出库产品管理',iconCls:'icon-save'" style="width:1500px;height:750px">
    <input type="hidden" name="outStorageId" id="outStorageIdHid2" />
    <input type="hidden" name="projectId" id="projectIdHid" />
    <div id="pkgDiv" class="easyui-tabs" fit="true">
        <div title="已关联产品" style="padding:0;">
            <div id="searchToolOutStorageProduct" style="padding:5px" fit="true">
                <label>产品状态：</label><select id="productStatusSel" ><option value="">ALL</option><option value="1">待激活</option><option value="2">已激活</option><option value="4">停机</option><option value="6">可测试</option><option value="7">库存</option><option value="8">预销户</option></select><span class="select-arrow"></span>
                <a href="javascript:void(0);" onclick="findOutStorageProductList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </div>
            <table id="relationOutStorageProductTable" fit="true"></table>
        </div>
        <div title="未关联产品" style="padding:0">
            <div id="searchToolNotOutStorageProduct" style="padding:5px" fit="true">
                <label>产品状态：</label><select id="productStatusSel1" ><option value="">ALL</option><option value="1">待激活</option><option value="2">已激活</option><option value="4">停机</option><option value="6">可测试</option><option value="7">库存</option><option value="8">预销户</option></select><span class="select-arrow"></span>
                <a href="javascript:void(0);" onclick="findOutStorageNotProductList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </div>
            <table id="notRelationOutStorageProductTable" fit="true"></table>
        </div>
    </div>
</div>
<!-- 弹出词典项页面 结束 -->

<!-- 新增修改页面 begin-->
<div id="out_storage_product_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'出库产品信息',iconCls:'icon-save'" style="padding:5px;width:700px;height:700px;">
    <form action="" id="outStorageProductForm">
        <input type="hidden" name="id" id="outStorageProductId" />
        <input type="hidden" name="outStorageId" id="outStorageIdHid3" />
        <input type="hidden" name="putStorageId" id="putStorageIdHid" />
        <input type="hidden" name="putStorageProductId" id="putStorageProductIdHid" />
        <input type="hidden" name="projectId" id="projectIdHid2" />
        <input type="hidden" name="producerId" id="producerIdHid" />
        <input type="hidden" name="productId" id="productIdHid" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>建筑物：</label></span></td>
                <td class="tableTdLeft">
                    <select id="buildingSel" name="buildingId" onchange="findBuildingFloorList()"></select><span class="select-arrow"></span>
                </td>
                <td class="tableTdRight"><span><label>楼层：</label></span></td>
                <td class="tableTdLeft">
                    <select id="buildingFloorSel" name="buildingFloorId" onchange="findDeviceTypeByBuildingFloorList()"></select><span class="select-arrow"></span>
                </td>
                <td class="tableTdRight"><span><label>设备类型：</label></span></td>
                <td class="tableTdLeft">
                    <select id="deviceTypeSel" name="deviceTypeId" onchange="findCheckPointList()" ></select><span class="select-arrow"></span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>巡检点：</label></span></td>
                <td class="tableTdLeft">
                    <select id="checkPointSel" name="checkPointId" onchange="findDeviceList()" ></select><span class="select-arrow"></span>
                </td>
                <td class="tableTdRight"><span><label>设备：</label></span></td>
                <td class="tableTdLeft">
                    <select id="deviceSel" name="deviceIdAndDeviceType" onchange="findSensorNoSelList()" ></select><span class="select-arrow"></span>
                </td>
                <td class="tableTdRight"><span><label>传感器ID：</label></span></td>
                <td class="tableTdLeft" colspan="5">
                    <select id="sensorNoSel" name="iotId" ></select><span class="select-arrow"></span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>备注：</label></span></td>
                <td class="tableTdLeft" colspan="10">
                    <textarea name="memo" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 330px;height: 100px"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

</html>