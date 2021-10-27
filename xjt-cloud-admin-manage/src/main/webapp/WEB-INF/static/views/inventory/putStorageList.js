$(function(){
    $('#put_storage_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                savePutStorage();
            }
        },{
            text:'取消',
            handler:function(){
                $("#put_storage_dialog").dialog('close');
                cleanDialog("put_storage_dialog");
            }
        }]
    });
    $('#put_storage_product_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                savePutStorageProduct();
            }
        },{
            text:'取消',
            handler:function(){
                $("#put_storage_product_dialog").dialog('close');
                cleanDialog("put_storage_product_dialog");
            }
        }]
    });
    initProducerList();
    initPutStorageDataGrid();
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
    };
}

function findPutStorageList(){
    assParameter();
    $('#put_storage_table').datagrid('options').queryParams = parameter;
    $('#put_storage_table').datagrid('options').pageNumber = 1;
    $('#put_storage_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#put_storage_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initPutStorageDataGrid(){
    assParameter();
    $('#put_storage_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/inventory/put/storage/findPutStorageList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'orderNum',title:'订单编号',width:100},
            {field:'producerName',title:'厂商名称',width:100},
            {field:'num',title:'数量',width:100},
            {field:'productName',title:'产品名称',width:100},
            {field:'orderTime',title:'购买时间',width:100},
            {field:'createTime',title:'创建时间',width:100},
            {field:'id',title:'操作',width:100,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = putStorageEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delPutStorage(' + value + ')>删除</a> | ' +
                        '<a href="javascript:void(0)" onclick = putStorageProductPage(' + value + ')>产品管理</a>';
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
function openPutStorageSaveEdit() {
    cleanDialog("put_storage_dialog");
    $('#put_storage_dialog').dialog('open');
}

/**
 * 保存数据词典信息
 */
function savePutStorage() {
    //判断页面填写是否为空
    if(!$("#put_storageForm").form("validate")){
        return;
    }
    if($("#producerSel1").val() == ""){
        showMsg('厂商不能为空！');
        return;
    }

    parameter = $('#put_storageForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var putStorageId = $('#put_storageId').val();
    //
    if(null == putStorageId || putStorageId == ""){
        $.post("/inventory/put/storage/savePutStorage?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#put_storage_table').datagrid('reload');
                $('#put_storage_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json");
    }else{
        $.post("/inventory/put/storage/modifyPutStorage?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#put_storage_table').datagrid('reload');
                $('#put_storage_dialog').dialog('close');
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
 * 修改词典信息
 */
function putStorageEdit(id){
    cleanDialog("put_storage_dialog");
    //设置弹出框信息
    $('#put_storage_table').datagrid('selectRecord',id);
    var row = $('#put_storage_table').datagrid('getSelected');
    $('#producerSel1').val(row.producerId);
    $('#orderTimeDt').datebox('setValue',row.orderTime);
    initProductTypeList('productSel1','producerSel1');
    $('#productSel1').val(row.productId);
    generateDialog(row,"put_storage_dialog");
    $('#put_storage_dialog').dialog('open');
}

/**
 * 删除词典信息
 */
function delPutStorage(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/inventory/put/storage/delPutStorage?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#put_storage_table').datagrid('reload');
                    showMsg('删除成功！');
                }  else{
                    showMsg(data.msg);
                }
            },"json").error(function (error, status, info){
                showMsg(error.responseText);
            });
        }
    });
}

var parameterItem;
function assParameterProduct(){
    parameterItem = {
        putStorageId:$('#putStorageIdHid2').val(),
        productStatus:$('#productStatusSel').val(),
        storageStatus:$('#storageStatusSel').val()
    };
}

function findPutStorageProductList(){
    assParameterProduct();
    $('#put_storage_product_table').datagrid('options').queryParams = parameterItem;
    $('#put_storage_product_table').datagrid('options').pageNumber = 1;
    $('#put_storage_product_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#put_storage_product_table').datagrid('reload');
}

/**
 * 入库模板表格下载
 */
function downPutStorageProductModel(){
    $.get("/inventory/put/storage/downPutStorageProductModel");
}

/**
 * 词典项管理
 */
function putStorageProductPage(id){
    $('#put_storage_table').datagrid('selectRecord',id);
    var row = $('#put_storage_table').datagrid('getSelected');
    $('#putStorageIdHid2').val(id);
    $('#producerIdHid2').val(row.producerId);
    $('#producerNameHid2').val(row.producerName);
    $('#productIdHid2').val(row.productId);
    $('#productNameHid2').val(row.productName);
    $('#orderNumHid2').val(row.orderNum);
    $("#put_storage_productDialog").dialog('open');
    initPutStorageProductDataGrid();
}

/**
 * 查询数据表格
 */
function initPutStorageProductDataGrid(){
    assParameterProduct();
    $('#put_storage_product_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#searchToolPutStorageProduct",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/inventory/put/storage/findPutStorageProductList?' + access_token,
        queryParams:parameterItem,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'producerName',title:'厂商名称',width:100},
            {field:'productName',title:'产品名称',width:100},
            {field:'property1',title:'参数1/ICCID',width:180},
            {field:'property2',title:'参数1/MSISDN',width:150},
            {field:'property3',title:'参数1/IMSI',width:100},
            {field:'productStatusDesc',title:'产品状态',width:80},
            {field:'storageStatusDesc',title:'库存状态',width:80},
            {field:'totalFlow',title:'总流量(M)',width:90},
            {field:'usedFlow',title:'已用流量(K)',width:90},
            {field:'residue',title:'剩余流量(K)',width:100,formatter:function(value,row){
                    return row.totalFlow * 1024 - row.usedFlow;
                }
            },
            {field:'openDate',title:'开卡日期',width:140},
            {field:'activeDate',title:'激活日期',width:140},
            {field:'id',title:'操作',width:100,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = putStorageProductEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delPutStorageProduct(' + value + ')>删除</a>';
                }
            }
        ]],
        pagination:true,
        pageSize : 30,
        rownumbers:true
    });
}

/**
 * 打开添加数据词典项窗口
 */
function openPutStorageProductSaveEdit() {
    cleanDialog("put_storage_product_dialog");
    $('#putStorageIdHid3').val($("#putStorageIdHid2").val());
    $('#producerIdHid3').val($("#producerIdHid2").val());
    $("#put_storageId3").val($("#put_storageId2").val());
    $("#producerNameHid3").val($("#producerNameHid2").val());
    $("#productHid3").val($("#productIdHid2").val());
    $("#productName3").val($("#productNameHid2").val());
    $("#orderNumHid3").val($("#orderNumHid2").val());
    $('#put_storage_product_dialog').dialog('open');
}

/**
 * 保存数据词典项信息
 */
function savePutStorageProduct() {
    //判断页面填写是否为空
    if(!$("#putStorageProductForm").form("validate")){
        return;
    }
    parameterItem = $('#putStorageProductForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var putStorageProductId = $('#putStorageProductId').val();
    //
    if(null == putStorageProductId || putStorageProductId == ""){
        $.post("/inventory/put/storage/savePutStorageProduct?" + access_token,parameterItem,function(data){
            if(200 == data.status){
                $('#put_storage_product_table').datagrid('reload');
                $('#put_storage_product_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/inventory/put/storage/modifyPutStorageProduct?" + access_token,parameterItem,function(data){
            if(200 == data.status){
                $('#put_storage_product_table').datagrid('reload');
                $('#put_storage_product_dialog').dialog('close');
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
 * 修改词典项信息
 */
function putStorageProductEdit(id){
    cleanDialog("put_storage_product_dialog");
    //设置弹出框信息
    $('#put_storage_product_table').datagrid('selectRecord',id);
    $("#producerNameHid3").val($("#producerNameHid2").val());
    $("#orderNumHid3").val($("#orderNumHid2").val());
    generateDialog($('#put_storage_product_table').datagrid('getSelected'),"put_storage_product_dialog");
    $('#put_storage_product_dialog').dialog('open');
}

/**
 * 删除词典项信息
 */
function delPutStorageProduct(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/inventory/put/storage/delPutStorageProduct?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#put_storage_product_table').datagrid('reload');
                    showMsg('删除成功！');
                }  else{
                    showMsg(data.msg);
                }
            },"json").error(function (error, status, info){
                showMsg(error.responseText);
            });
        }
    });
}

/**
 * 上传
 * user:huanggc
 * param: file_id
 * return:
 * date:2020/09/23
 **/
function uploadExcelFile(file_id, putStorageId, producerId){
    var formData = new FormData();
    formData.append("file",$("#" + file_id + "_file_upload")[0].files[0]);
    formData.append("putStorageId", putStorageId);
    formData.append("producerId", producerId);
    $.ajax({
        url:'/inventory/put/storage/uploadExcelFile', /*接口域名地址*/
        type:'post',
        data: formData,
        dataType: "json",
        contentType: false,
        processData: false,
        success:function(data){
            if(200 == data.status){
                $('#put_storage_table').datagrid('reload');
                showMsg(data.msg);
            }  else{
                showMsg(data.msg);
            }
        }
    })
}