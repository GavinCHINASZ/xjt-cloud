$(function(){
    initProducerList();
    initPutStorageDataGrid();
    initProductTypeSelList();
});

/**
 * 初使列表
 * user:wangzhiwen
 * date:2017/8/29 17:26
 **/
function initProductTypeList(productSel,producerSel){
    $("#" + productSel).append('<option value="">ALL</option>');
    var producerId = $("#" + producerSel).val();
    if (producerId == null){
        return;
    }
    $.post("/inventory/product/findProductListByProducerId?" + access_token,{"producerId":producerId},function(data){
        $.each(data,function(index,product){
            $("#" + productSel).append('<option value=' + product.id + '>' + product.productName + '</option>');
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}
/**
 * 初使设备类型列表
 * user:wangzhiwen
 * date:2017/8/29 17:26
 **/
function initProductTypeSelList(){
    $.post("/dict/findCacheDictItemListByCode?" + access_token,{"dictCode":"INVENTORY_PRODUCT_TYPE"},function(data){
        $("#productTypeSel").html('<option value="">所有</option>');
        $("#productTypeSel2").html('<option value="">所有</option>');
        $.each(data,function(index,dict){
            $("#productTypeSel").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
            $("#productTypeSel2").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}
/**
 * 初使列表
 * user:wangzhiwen
 * date:2017/8/29 17:26
 **/
function initProducerList(){
    $("#producerSel").append('<option value="">ALL</option>');
    $("#producerSel1").append('<option value="">ALL</option>');
    $.post("/inventory/producer/findProducerAll?" + access_token,null,function(data){
        $.each(data,function(index,producer){
            $("#producerSel").append('<option value=' + producer.id + '>' + producer.producerName + '</option>');
            $("#producerSel1").append('<option value=' + producer.id + '>' + producer.producerName + '</option>');
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

function assParameter(){
    parameter = {
        producerId:$('#producerSel').val(),
        productId:$('#productSel').val(),
        productType:$('#productTypeSel').val()
    };
}

function findInventoryList(){
    assParameter();
    $('#inventory_table').datagrid('options').queryParams = parameter;
    $('#inventory_table').datagrid('options').pageNumber = 1;
    $('#inventory_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#inventory_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initPutStorageDataGrid(){
    assParameter();
    $('#inventory_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/inventory/findInventoryList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'producerName',title:'厂商名称',width:100},
            {field:'productTypeDesc',title:'产品类型',width:100},
            {field:'productName',title:'产品名称',width:100},
            {field:'totalNum',title:'总库存数',width:100},
            {field:'outNum',title:'出库数',width:100},
            {field:'inventoryNum',title:'库存数',width:100,formatter:function (value,row) {
                var inventoryNum = row.totalNum - row.outNum;
                if (inventoryNum < 10){
                    return "<font color=''#FF0000'>" + inventoryNum + "</font>"
                }
                return inventoryNum;
            }}
        ]],
        pagination:true,
        pageSize : 30,
        rownumbers:true
    });
}
