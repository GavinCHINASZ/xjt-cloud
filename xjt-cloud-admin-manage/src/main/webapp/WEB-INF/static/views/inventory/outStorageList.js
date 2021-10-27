$(function(){
    $('#out_storage_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveOutStorage();
            }
        },{
            text:'取消',
            handler:function(){
                $("#out_storage_dialog").dialog('close');
                cleanDialog("out_storage_dialog");
            }
        }]
    });
    $('#out_storage_product_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveOutStorageProduct();
            }
        },{
            text:'取消',
            handler:function(){
                $("#out_storage_product_dialog").dialog('close');
                cleanDialog("out_storage_product_dialog");
            }
        }]
    });
    initProducerList();
    initOutStorageDataGrid();
    initProjectList();
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

/**
 * 初使列表
 * user:wangzhiwen
 * date:2017/8/29 17:26
 **/
function initProjectList(){
    $("#projectSel").append('<option value="">ALL</option>');
    $("#projectSel1").append('<option value="">ALL</option>');
    $.post("/project/findAllProject?" + access_token,null,function(data){
        $.each(data,function(index,project){
            $("#projectSel").append('<option value=' + project.id + '>' + project.projectName + '</option>');
            $("#projectSel1").append('<option value=' + project.id + '>' + project.projectName + '</option>');
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

function assParameter(){
    parameter = {
    };
}

function findOutStorageList(){
    assParameter();
    $('#out_storage_table').datagrid('options').queryParams = parameter;
    $('#out_storage_table').datagrid('options').pageNumber = 1;
    $('#out_storage_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#out_storage_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initOutStorageDataGrid(){
    assParameter();
    $('#out_storage_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/inventory/out/storage/findOutStorageList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'orderNum',title:'订单编号',width:100},
            {field:'projectName',title:'项目名称',width:100},
            {field:'recipients',title:'领用人',width:100},
            {field:'createTime',title:'创建时间',width:100},
            {field:'id',title:'操作',width:100,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = outStorageEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delOutStorage(' + value + ')>删除</a> | ' +
                        '<a href="javascript:void(0)" onclick = outStorageProductPage(' + value + ')>产品管理</a>';
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
function openOutStorageSaveEdit() {
    cleanDialog("out_storage_dialog");
    $('#out_storage_dialog').dialog('open');
}

/**
 * 保存数据词典信息
 */
function saveOutStorage() {
    //判断页面填写是否为空
    if(!$("#out_storageForm").form("validate")){
        return;
    }
    if($("#producerSel1").val() == ""){
        showMsg('厂商不能为空！');
        return;
    }

    parameter = $('#out_storageForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var outStorageId = $('#out_storageId').val();
    //
    if(null == outStorageId || outStorageId == ""){
        $.post("/inventory/out/storage/saveOutStorage?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#out_storage_table').datagrid('reload');
                $('#out_storage_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json");
    }else{
        $.post("/inventory/out/storage/modifyOutStorage?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#out_storage_table').datagrid('reload');
                $('#out_storage_dialog').dialog('close');
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
function outStorageEdit(id){
    console.log(1)
    cleanDialog("out_storage_dialog");
    //设置弹出框信息
    $('#out_storage_table').datagrid('selectRecord',id);
    var row = $('#out_storage_table').datagrid('getSelected');
    generateDialog(row,"out_storage_dialog");
    $('#out_storage_dialog').dialog('open');
}

/**
 * 删除词典信息
 */
function delOutStorage(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/inventory/out/storage/delOutStorage?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#out_storage_table').datagrid('reload');
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
        relationProduct:true,
        outStorageId:$('#outStorageIdHid2').val(),
        productStatus:$('#productStatusSel').val()
    };
}

function findOutStorageProductList(){
    assParameterProduct();
    $('#relationOutStorageProductTable').datagrid('options').queryParams = parameterItem;
    $('#relationOutStorageProductTable').datagrid('options').pageNumber = 1;
    $('#relationOutStorageProductTable').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#relationOutStorageProductTable').datagrid('reload');
}
/**
 * 词典项管理
 */
function outStorageProductPage(id){
    $('#out_storage_table').datagrid('selectRecord',id);
    var row = $('#out_storage_table').datagrid('getSelected');
    $('#outStorageIdHid2').val(id);
    $('#projectIdHid').val(row.projectId);
    $("#out_storage_productDialog").dialog('open');
    initOutStorageProductDataGrid();
    initOutStorageNotProductDataGrid();
}

/**
 * 查询数据表格
 */
function initOutStorageProductDataGrid(){
    assParameterProduct();
    $('#relationOutStorageProductTable').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#searchToolOutStorageProduct",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/inventory/out/storage/findOutStorageProductList?' + access_token,
        queryParams:parameterItem,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'projectName',title:'项目名称',width:100},
            {field:'buildingName',title:'建筑物名称',width:100},
            {field:'floorName',title:'楼层名称',width:100},
            {field:'pointName',title:'巡检点名称',width:100},
            {field:'qrNo',title:'巡检点ID',width:100},
            {field:'deviceQrNo',title:'设备ID',width:100},
            {field:'deviceType',title:'物联设备类型',width:100},
            {field:'sensorNo',title:'传感器ID',width:100},
            {field:'property1',title:'参数1/ICCID',width:100},
            {field:'property2',title:'参数1/MSISDN',width:100},
            {field:'productStatusDesc',title:'产品状态',width:50},
            {field:'totalFlow',title:'总流量(M)',width:100},
            {field:'residue',title:'剩余流量(K)',width:100,formatter:function(value,row){
                    return row.totalFlow * 1024 - row.usedFlow;
                }
            },
            {field:'openDate',title:'开卡日期',width:100},
            {field:'activeDate',title:'激活日期',width:100},
            {field:'id',title:'操作',width:100,formatter:function(value,row){
                    return '<a href="javascript:void(0)" onclick = outStorageProductEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delOutStorageProduct(' + row.outStorageProductId + ')>删除</a>';
                }
            }
        ]],
        pagination:true,
        pageSize : 30,
        rownumbers:true
    });
}

function assParameterNotProduct(){
    parameterItem = {
        relationProduct:false,
        outStorageId:$('#outStorageIdHid2').val()
    };
}

function findOutStorageNotProductList(){
    assParameterNotProduct();
    $('#notRelationOutStorageProductTable').datagrid('options').queryParams = parameterItem;
    $('#notRelationOutStorageProductTable').datagrid('options').pageNumber = 1;
    $('#notRelationOutStorageProductTable').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#notRelationOutStorageProductTable').datagrid('reload');
}
/**
 * 查询数据表格
 */
function initOutStorageNotProductDataGrid(){
    assParameterNotProduct();
    $('#notRelationOutStorageProductTable').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#searchToolNotOutStorageProduct",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/inventory/out/storage/findOutStorageProductList?' + access_token,
        queryParams:parameterItem,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'producerName',title:'厂商名称',width:100},
            {field:'productName',title:'产品名称',width:100},
            {field:'property1',title:'参数1/ICCID',width:100},
            {field:'property2',title:'参数1/MSISDN',width:100},
            {field:'property3',title:'参数1/IMSI',width:100},
            {field:'productStatusDesc',title:'产品状态',width:50},
            {field:'storageStatusDesc',title:'库存状态',width:50},
            {field:'totalFlow',title:'总流量(M)',width:100},
            {field:'usedFlow',title:'已用流量(K)',width:100},
            {field:'residue',title:'剩余流量(K)',width:100,formatter:function(value,row){
                    if (row.totalFlow != null && row.usedFlow != null){
                        return row.totalFlow * 1024 - row.usedFlow;
                    }
                    return "";
                }
            },
            {field:'openDate',title:'开卡日期',width:100},
            {field:'activeDate',title:'激活日期',width:100},
            {field:'id',title:'操作',width:100,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = openOutStorageProductSaveEdit(' + value + ')>添加</a>';
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
function openOutStorageProductSaveEdit(id) {
    cleanDialog("out_storage_product_dialog");
    $('#outStorageProductId').val("");
    $('#outStorageIdHid3').val($("#outStorageIdHid2").val());
    $("#out_storageId3").val($("#out_storageId2").val());
    $("#projectIdHid2").val($("#projectIdHid").val());
    $("#putStorageProductIdHid").val(id);
    $('#notRelationOutStorageProductTable').datagrid('selectRecord',id);
    var row = $('#notRelationOutStorageProductTable').datagrid('getSelected');
    $('#producerIdHid').val(row.producerId);
    $('#productIdHid').val(row.productId);
    $('#putStorageIdHid').val(row.putStorageId);
    $('#out_storage_product_dialog').dialog('open');
    findBuildingList();
}

function findBuildingList(buildingId) {
    console.log(1)
    parameter = {
        projectId:$('#projectIdHid2').val()
    };
    $("#buildingSel").empty();
    $("#buildingFloorSel").empty();
    $("#deviceTypeSel").empty();
    $("#checkPointSel").empty();
    $("#deviceSel").empty();
    $("#sensorNoSel").empty();
    $("#buildingSel").append('<option value="">ALL</option>');
    $.post("/project/findBuildingList?" + access_token,parameter,function(data){
        $.each(data,function(index,building){
            $("#buildingSel").append('<option value=' + building.id + '>' + building.buildingName + '</option>');
            $("#buildingSel").val(buildingId);
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

function findBuildingFloorList(buildingId,buildingFloorId) {
    if (buildingId == null && ($('#buildingSel').val() == null || $('#buildingSel').val() == "")){
        return;
    }else if(buildingId == null || buildingId == ''){
        buildingId = $('#buildingSel').val();
    }
    parameter = {
        projectId:$('#projectIdHid2').val(),
        buildingId:buildingId
    };

    $("#buildingFloorSel").empty();
    $("#deviceTypeSel").empty();
    $("#checkPointSel").empty();
    $("#deviceSel").empty();
    $("#sensorNoSel").empty();
    $("#buildingFloorSel").append('<option value="">ALL</option>');
    $.post("/project/findBuildIngFloorList?" + access_token,parameter,function(data){
        $.each(data,function(index,buildingFloor){
            $("#buildingFloorSel").append('<option value=' + buildingFloor.id + '>' + buildingFloor.floorName + '</option>');
            $("#buildingFloorSel").val(buildingFloorId);
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

function findDeviceTypeByBuildingFloorList(buildingId,buildingFloorId,deviceTypeId) {
    if (buildingId == null && ($('#buildingFloorSel').val() == null || $('#buildingFloorSel').val() == "")){
        return;
    }else if(buildingId == null || buildingId == ''){
        buildingId = $('#buildingSel').val();
        buildingFloorId = $('#buildingFloorSel').val();
    }
    $("#deviceTypeSel").empty();
    $("#checkPointSel").empty();
    $("#deviceSel").empty();
    $("#sensorNoSel").empty();
    $("#deviceTypeSel").append('<option value="">ALL</option>');
    parameter = {
        projectId:$('#projectIdHid2').val(),
        buildingId:buildingId,
        buildingFloorId:buildingFloorId
    };
    $.post("/device/sys/findProjectDeviceTypeList?" + access_token,parameter,function(data){
        $.each(data,function(index,deviceType){
            $("#deviceTypeSel").append('<option value=' + deviceType.id + '>' + deviceType.deviceSysName + '</option>');
            $("#deviceTypeSel").val(deviceTypeId);
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

function findCheckPointList(buildingId,buildingFloorId,deviceTypeId,checkPointId) {
    if (buildingId == null && ($('#deviceTypeSel').val() == null || $('#deviceTypeSel').val() == "")){
        return;
    }else if(buildingId == null || buildingId == ''){
        buildingId = $('#buildingSel').val();
        buildingFloorId = $('#buildingFloorSel').val();
        deviceTypeId = $('#deviceTypeSel').val();
    }
    $("#checkPointSel").empty();
    $("#deviceSel").empty();
    $("#sensorNoSel").empty();
    $("#checkPointSel").append('<option value="">ALL</option>');
    parameter = {
        projectId:$('#projectIdHid2').val(),
        buildingId:buildingId,
        buildingFloorId:buildingFloorId,
        deviceTypeId:deviceTypeId
    };
    $.post("/device/sys/findProjectCheckPointList?" + access_token,parameter,function(data){
        $.each(data,function(index,checkPoint){
            $("#checkPointSel").append('<option value=' + checkPoint.id + '>' + checkPoint.qrNo + '</option>');
            $("#checkPointSel").val(checkPointId);
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

function findDeviceList(buildingId,buildingFloorId,deviceTypeId,checkPointId,deviceId) {
    if (buildingId == null && ($('#checkPointSel').val() == null || $('#checkPointSel').val() == "")){
        return;
    }else if(buildingId == null || buildingId == ''){
        buildingId = $('#buildingSel').val();
        buildingFloorId = $('#buildingFloorSel').val();
        deviceTypeId = $('#deviceTypeSel').val();
        checkPointId = $('#checkPointSel').val()
    }
    $("#deviceSel").empty();
    $("#sensorNoSel").empty();
    $("#deviceSel").append('<option value="">ALL</option>');
    parameter = {
        projectId:$('#projectIdHid2').val(),
        buildingId:buildingId,
        buildingFloorId:buildingFloorId,
        deviceTypeId:deviceTypeId,
        checkPointId:checkPointId
    };
    $.post("/device/sys/findProjectDeviceList?" + access_token,parameter,function(data){
        $.each(data,function(index,device){
            $("#deviceSel").append('<option value=' + device.id + '_' + device.deviceType + '>' + device.qrNo + '</option>');
            $("#deviceSel").val(deviceId);
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

function findSensorNoSelList(buildingId,buildingFloorId,deviceTypeId,checkPointId,deviceIdAndDeviceType,sensorNoId) {
    if (buildingId == null && ($('#deviceSel').val() == null || $('#deviceSel').val() == "")){
        return;
    }else if(buildingId == null || buildingId == ''){
        buildingId = $('#buildingSel').val();
        buildingFloorId = $('#buildingFloorSel').val();
        deviceTypeId = $('#deviceTypeSel').val();
        checkPointId = $('#checkPointSel').val();
        deviceIdAndDeviceType = $('#deviceSel').val();
    }
    $("#sensorNoSel").empty();
    $("#sensorNoSel").append('<option value="">ALL</option>');
    parameter = {
        projectId:$('#projectIdHid2').val(),
        buildingId:buildingId,
        buildingFloorId:buildingFloorId,
        deviceTypeId:deviceTypeId,
        checkPointId:checkPointId,
        deviceIdAndDeviceType:deviceIdAndDeviceType
    };
    $.post("/iot/water/findSensorNoList?" + access_token,parameter,function(data){
        $.each(data,function(index,iot){
            $("#sensorNoSel").append('<option value=' + iot.id + '>' + iot.sensorNo + '</option>');
            $("#sensorNoSel").val(sensorNoId);
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

/**
 * 保存数据词典项信息
 */
function saveOutStorageProduct() {
    //判断页面填写是否为空
    if(!$("#outStorageProductForm").form("validate")){
        return;
    }
    parameterItem = $('#outStorageProductForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var outStorageProductId = $('#outStorageProductId').val();
    //
    if(null == outStorageProductId || outStorageProductId == ""){
        $.post("/inventory/out/storage/saveOutStorageProduct?" + access_token,parameterItem,function(data){
            if(200 == data.status){
                $('#relationOutStorageProductTable').datagrid('reload');
                $('#notRelationOutStorageProductTable').datagrid('reload');
                $('#out_storage_product_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/inventory/out/storage/modifyOutStorageProduct?" + access_token,parameterItem,function(data){
            if(200 == data.status){
                $('#relationOutStorageProductTable').datagrid('reload');
                $('#notRelationOutStorageProductTable').datagrid('reload');
                $('#out_storage_product_dialog').dialog('close');
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
function outStorageProductEdit(id){
    cleanDialog("out_storage_product_dialog");
    //设置弹出框信息
    $('#relationOutStorageProductTable').datagrid('selectRecord',id);
    var row = $('#relationOutStorageProductTable').datagrid('getSelected');

    $('#projectIdHid2').val(row.projectId);
    findBuildingList(row.buildingId);
    findBuildingFloorList(row.buildingId,row.buildingFloorId);
    findDeviceTypeList(row.buildingId,row.buildingFloorId,row.deviceTypeId);
    findCheckPointList(row.buildingId,row.buildingFloorId,row.deviceTypeId,row.checkPointId);
    findDeviceList(row.buildingId,row.buildingFloorId,row.deviceTypeId,row.checkPointId,row.deviceId + "_" + row.deviceType);
    findSensorNoSelList(row.buildingId,row.buildingFloorId,row.deviceTypeId,row.checkPointId,row.deviceId + "_" + row.deviceType,row.iotId);
    generateDialog(row,"out_storage_product_dialog");
    $('#out_storage_product_dialog').dialog('open');
    $("#outStorageProductId").val(row.outStorageProductId);

}

/**
 * 删除词典项信息
 */
function delOutStorageProduct(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/inventory/out/storage/delOutStorageProduct?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#relationOutStorageProductTable').datagrid('reload');
                    $('#notRelationOutStorageProductTable').dialog('close');
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
function uploadOutProducerExcelFile(file_id){
    var formData = new FormData();
    formData.append("file",$("#" + file_id + "_file_upload")[0].files[0]);
    $.ajax({
        url:'/inventory/out/storage/uploadOutProducerExcelFile?' + access_token, /*接口域名地址*/
        type:'post',
        data: formData,
        dataType: "json",
        contentType: false,
        processData: false,
        success:function(data){
            if(200 == data.status){
                $('#out_storage_table').datagrid('reload');
                showMsg(data.msg);
            }  else{
                showMsg(data.msg);
            }
        }
    })
}


function downOutProductModelExcel(){
    window.location.href = "inventory/out/storage/downOutProductModelExcel?" + access_token;
}