<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>入库管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/inventory/putStorageList.js"></script>
    <script type="text/javascript" src="/resource/js/easyui-datetime.js"></script>
</head>
<!-- 主页面 begin -->
<div  id="common_search" class="easyui-panel" fit="true">
    <!--查询行 开始 -->
    <div id="toolbar" style="padding:5px" >
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openPutStorageSaveEdit();" >添加</a>
        <label>厂商：</label><select id="producerSel" onchange="initProductTypeList('productSel','producerSel')" ></select><span class="select-arrow"></span>
        <label>产品：</label><select id="productSel" ></select><span class="select-arrow"></span>
        <label>订单号：</label><input type="text" id="orderNumTxt" style='width:150px'/>
        <a href="javascript:void(0);" onclick="findPutStorageList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        <a href="/inventory/put/storage/downPutStorageProductModel" class="easyui-linkbutton" data-options="iconCls:'icon-downward'">模板下载</a>

        <label>&nbsp;&nbsp;&nbsp;批量导入：</label>
        <img src="" name="thumbnail" style='height:80px;' id="thumbnail"/>
        <input type="hidden" name="thumbnail" id="thumbnail_hid"/>
        <input id="thumbnail_file_upload" style="width: 180px;" type="file"/>
        <a href="javascript:void(0);" onclick="uploadExcelFile('thumbnail', document.getElementById('putStorageIdHid2').value, document.getElementById('producerIdHid2').value)"
           class="easyui-linkbutton" data-options="iconCls:'icon-upward'">上传</a>
    </div>
    <table id="put_storage_table" style="height: auto" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 新增修改页面 begin-->
<div id="put_storage_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'入库单信息',iconCls:'icon-save'" style="padding:5px;width:500px;height:300px;">
    <form action="" id="put_storageForm">
        <input type="hidden" name="id" id="put_storageId" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>厂商：</label></span></td>
                <td class="tableTdLeft">
                    <select id="producerSel1" name="producerId" onchange="initProductTypeList('productSel1','producerSel1')" ></select><span class="select-arrow"/>
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>产品：</label></span></td>
                <td class="tableTdLeft">
                    <select id="productSel1" name="productId" ></select><span class="select-arrow"/>
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>订单编号：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="orderNum" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>购买日期：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" id="orderTimeDt" name="orderTime"  class="easyui-datetimebox">
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>数量：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="num" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
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
<div id="put_storage_productDialog" class="easyui-dialog nopadding" data-options="closed:true,modal:true,title:'入库产品管理'" style="width:1300px;height:760px">
    <input type="hidden" name="putStorageId" id="putStorageIdHid2" />
    <input type="hidden" name="producerId" id="producerIdHid2" />
    <input type="hidden" id="producerNameHid2" name="producerName">
    <input type="hidden" name="productId" id="productIdHid2" />
    <input type="hidden" id="productNameHid2" name="productName">
    <input type="hidden" id="orderNumHid2" name="orderNum">
    <div id="searchToolPutStorageProduct" style="padding:5px" >
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  onclick="openPutStorageProductSaveEdit();" >添加</a>
        <label>产品状态：</label>
        <select id="productStatusSel" ><option value="">ALL</option><option value="1">待激活</option><option value="2">已激活</option><option value="4">停机</option>
            <option value="6">可测试</option><option value="7">库存</option><option value="8">预销户</option>
        </select>
        <span class="select-arrow"></span>
        <label>库存状态：</label>
        <select id="storageStatusSel" ><option value="">ALL</option><option value="1">库存</option><option value="2">已出库</option><option value="3">退回</option>
        <option value="4">报废</option></select><span class="select-arrow"></span>
        <a href="javascript:void(0);" onclick="findPutStorageProductList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>

    </div>
    <table id="put_storage_product_table" style="height: auto" ></table>
</div>

<!-- 弹出词典项页面 结束 -->

<!-- 新增修改页面 begin-->
<div id="put_storage_product_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'物联网卡信息',iconCls:'icon-save'" style="padding:5px;width:700px;height:700px;">
    <form action="" id="putStorageProductForm">
        <input type="hidden" name="id" id="putStorageProductId" />
        <input type="hidden" name="putStorageId" id="putStorageIdHid3" />
        <input type="hidden" name="producerId" id="producerIdHid3" />
        <input type="hidden" name="productId" id="productHid3" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>订单编号：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="orderNum" id="orderNumHid3" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
                <td class="tableTdRight"><span><label>厂商名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="producerName" id="producerNameHid3" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>产品：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="productName" id="productName3" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
                <td class="tableTdRight"><span><label>产品状态：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="productStatusDesc" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>库存状态：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <select name="storageStatus" ><option value="1">库存</option><option value="2">已出库</option><option value="3">退回</option><option value="4">报废</option>
                    </select><span class="select-arrow"></span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>参数1/ICCID：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="property1" style="width: 180px;" data-options="validType:'length[1,50]'">
                </td>
                <td class="tableTdRight"><span><label>参数2/MSISDN：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="property2" style="width: 180px;" data-options="validType:'length[1,50]'">
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>参数3/IMSI：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="property3" style="width: 180px;" data-options="validType:'length[1,50]'">
                </td>
                <td class="tableTdRight"><span><label>参数4：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="property4" style="width: 180px;" data-options="validType:'length[1,50]'">
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>参数5：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="property5" style="width: 180px;" data-options="validType:'length[1,50]'">
                </td>
                <td class="tableTdRight"><span><label>参数6：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="property6" style="width: 180px;" data-options="validType:'length[1,50]'">
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>总流量(M)：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="totalFlow" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
                <td class="tableTdRight"><span><label>已用流量(K)：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="usedFlow" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>开卡日期：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="openDate" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
                <td class="tableTdRight"><span><label>激活日期：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="activeDate" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>备注：</label></span></td>
                <td class="tableTdLeft" colspan="10">
                    <textarea name="memo" data-options="required:true,validType:'length[0,100]'" class="z_textarea" style="width: 450px;height: 100px"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

</html>