$(function(){
    $('#product_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveProduct();
            }
        },{
            text:'取消',
            handler:function(){
                $("#product_dialog").dialog('close');
                cleanDialog("product_dialog");
            }
        }]
    });
    initProductTypeSelList();
    initProducerList();
    initProductGrid();
});

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

/**
 * 初使设备类型列表
 * user:wangzhiwen
 * date:2017/8/29 17:26
 **/
function initProductTypeSelList(){
    console.log(1)
    $("#productTypeSel").html('<option value="">所有</option>');
    $("#productTypeSel2").html('<option value="">所有</option>');
    $.post("/dict/findCacheDictItemListByCode?" + access_token,{"dictCode":"INVENTORY_PRODUCT_TYPE"},function(data){
        $.each(data,function(index,dict){
            $("#productTypeSel").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
            $("#productTypeSel2").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

function assParameter(){
    parameter = {
    };
}

function findProductList(){
    assParameter();
    $('#product_table').datagrid('options').queryParams = parameter;
    $('#product_table').datagrid('options').pageNumber = 1;
    $('#product_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#product_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initProductGrid(){
    assParameter();
    $('#product_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/inventory/product/findProductList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'productTypeDesc',title:'产品类型',width:100},
            {field:'productName',title:'产品名称',width:100},
            {field:'producerName',title:'厂商',width:100},
            {field:'brand',title:'品牌',width:100},
            {field:'model',title:'产品型号',width:100},
            {field:'spec',title:'规格参数',width:100},
            {field:'productionDate',title:'生产日期',width:100},
            {field:'expiryDate',title:'有效期',width:100},
            {field:'id',title:'操作',width:100,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = productEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delProduct(' + value + ')>删除</a> ';
                }
            }
        ]],
        pagination:true,
        pageSize : 30,
        rownumbers:true
    });
}

/**
 * 打开添加窗口
 */
function openProductSaveEdit() {
    cleanDialog("product_dialog");
    $('#product_dialog').dialog('open');
}

/**
 * 保存数据信息
 */
function saveProduct() {
    //判断页面填写是否为空
    if(!$("#productForm").form("validate")){
        return;
    }
    parameter = $('#productForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var productId = $('#productId').val();
    //
    if(null == productId || productId == ""){
        $.post("/inventory/product/saveProduct?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#product_table').datagrid('reload');
                $('#product_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/inventory/product/modifyProduct?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#product_table').datagrid('reload');
                $('#product_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }
}

/**
 * 删除数据信息
 */
function delProduct(id) {
    parameter = {
        id:id,
        status:99
    };
    //
    $.post("/inventory/product/delProduct?" + access_token,parameter,function(data){
        if(200 == data.status){
            $('#product_table').datagrid('reload');
            $('#product_dialog').dialog('close');
            showMsg('保存成功！');
        }else{
            showMsg(data.msg);
        }
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

/**
 * 修改信息
 */
function productEdit(id){
    cleanDialog("product_dialog");
    //设置弹出框信息
    $('#product_table').datagrid('selectRecord',id);
    var row = $('#product_table').datagrid('getSelected');
    $('#productionDateTxt').datebox("setValue",row.productionDate);
    $('#expiryDateTxt').datebox("setValue",row.expiryDate);
    generateDialog(row,"product_dialog");
    $('#product_dialog').dialog('open');
}

