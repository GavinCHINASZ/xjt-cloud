$(function(){
    $("#sysPinYinInitialsTxt").hide();
    $("#sysPinYinInitialsTxt").attr("disabled","disabled");

    $('#deviceSys_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveDeviceSys();
            }
        },{
            text:'取消',
            handler:function(){
                $("#deviceSys_dialog").dialog('close');
                cleanDialog("deviceSys_dialog");
            }
        }]
    });

    $('#deviceType_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveDeviceType();
            }
        },{
            text:'取消',
            handler:function(){
                $("#deviceType_dialog").dialog('close');
                cleanDialog("deviceType_dialog");
            }
        }]
    });

    $('#checkItem_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveCheckItem();
            }
        },{
            text:'取消',
            handler:function(){
                $("#checkItem_dialog").dialog('close');
                cleanDialog("checkItem_dialog");
            }
        }]
    });

    $('#faultType_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveDeviceFault();
            }
        },{
            text:'取消',
            handler:function(){
                $("#faultType_dialog").dialog('close');
                cleanDialog("faultType_dialog");
            }
        }]
    });

    initDeviceSysDataGrid();
    initDeviceTypeList();
});

/**
 * 初使设备类型列表
 * user:wangzhiwen
 * date:2017/8/29 17:26
 **/
function initDeviceTypeList(){
    $("#deviceTypeSel1").append('<option value="">ALL</option>');
    $("#deviceTypeSel2").append('<option value="">ALL</option>');
    $.post("/dict/findCacheDictItemListByCode?" + access_token,{"dictCode":"DEVICE_SYS_TYPE"},function(data){
        $.each(data,function(index,dict){
            $("#deviceTypeSel").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
            $("#deviceTypeSel1").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
            $("#deviceTypeSel2").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

function assParameter(){
    parameter = {
        deviceSysName:$("#deviceSysNameTxt").val(),
        deviceTypeName:$("#deviceTypeNameTxt").val(),
        projectName:$("#projectNameTxt").val(),
        checkItemVsType:$("#vsTypeSel").val(),
        deviceType:$("#deviceTypeSel2").val()
    };
}

function findDeviceSysList(){
    assParameter();
    $('#deviceSys_table').datagrid('options').queryParams = parameter;
    $('#deviceSys_table').datagrid('options').pageNumber = 1;
    $('#deviceSys_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#deviceSys_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initDeviceSysDataGrid(){
    assParameter();
    $('#deviceSys_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#deviceSysToolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/device/sys/findDeviceSysList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'deviceSysName',title:'名称',width:100},
            {field:'pinYinInitials',title:'拼音首字母',width:100},
            {field:'createTime',title:'创建时间',width:100},
            {field:'id',title:'操作',width:100,formatter:function(value,row){
                    return '<a href="javascript:void(0)" onclick = deviceSysEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delDeviceSys(' + value + ')>删除</a> | ' +
                        '<a href="javascript:void(0)" onclick = deviceTypePage(' + value + ',"' + row.deviceSysName + '")>设备类型管理</a>';
                }
            }
        ]],
        pagination:true,
        pageSize:30,
        rownumbers:true
    });
}

/**
 * 打开添加窗口
 */
function openDeviceSysSaveEdit() {
    $('#deviceSys_dialog').dialog('open');
}

/**
 * 保存数据词典信息
 */
function saveDeviceSys() {
    //判断页面填写是否为空
    if(!$("#deviceSysForm").form("validate")){
        return;
    }
    parameter = $('#deviceSysForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var deviceSysId = $('#deviceSysId').val();
    //
    if(null == deviceSysId || deviceSysId == ""){
        $.post("/device/sys/saveDeviceSys?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#deviceSys_table').datagrid('reload');
                $('#deviceSys_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/device/sys/modifyDeviceSys?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#deviceSys_table').datagrid('reload');
                $('#deviceSys_dialog').dialog('close');
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
function deviceSysEdit(id){
    $("#sysPinYinInitialsTxt").show();
    cleanDialog("deviceSys_dialog");
    //设置弹出框信息
    $('#deviceSys_table').datagrid('selectRecord',id);
    generateDialog($('#deviceSys_table').datagrid('getSelected'),"deviceSys_dialog");
    $('#deviceSys_dialog').dialog('open');
}

/**
 * 删除词典信息
 */
function delDeviceSys(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/device/sys/delDeviceSys?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#deviceSys_table').datagrid('reload');
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

//////////////////////////////////////设备类型管理
var parameterItem;
function assParameterItem(){
    parameterItem = {
        parentId:$('#deviceSysId2').val(),
        deviceTypeName:$("#deviceTypeNameTxt").val(),
        projectName:$("#projectNameTxt").val(),
        checkItemVsType:$("#vsTypeSel").val(),
        deviceType:$("#deviceTypeSel1").val()
    };
}

/**
 * 词典项管理
 */
function deviceTypePage(id,deviceSysName){
    $('#deviceSysId2').val(id);
    $("#deviceSysNameHid").val(deviceSysName);
    $("#deviceType_search").dialog('open');
    initDeviceTypeDataGrid();
}

function findDeviceTypeList() {
    assParameterItem();
    $('#deviceType_table').datagrid('options').queryParams = parameterItem;
    $('#deviceType_table').datagrid('options').pageNumber = 1;
    $('#deviceType_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#deviceType_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initDeviceTypeDataGrid(){
    assParameterItem();
    $('#deviceType_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        fit:true,
        toolbar: "#deviceTypeToolbarItem",
        fitColumns:true,
        collapsible:true,
        url:'/device/sys/findDeviceTypeList?'　+　access_token,
        queryParams:parameterItem,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'deviceSysName',title:'名称',width:100},
            {field:'pinYinInitials',title:'拼音首字母',width:70},
            {field:'pressureMonitor',title:'是否具有压力监测或液位监测',width:100,formatter:function(value){
                    return value == 1 ? "是":"否";
                }},
            {field:'sysPressure',title:'系统端压力',width:50,formatter:function(value){
                    return value == 1 ? "是":"否";
                }},
            {field:'deviceTypeDesc',title:'设备类型',width:100},
            {field:'limitConfig',title:'设备限制',width:80},
            {field:'id',title:'操作',width:166,formatter:function(value,row){
                    return '<a href="javascript:void(0)" onclick = deviceTypeEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delDeviceType(' + value + ')>删除</a> | ' +
                        '<a href="javascript:void(0)" onclick = checkItemPage(' + value + ',"' + row.deviceSysName + '")>巡检项管理</a> | '+
                        '<a href="javascript:void(0)" onclick = faultTypePage(' + value + ',"' + row.parentId + '","' + row.deviceType + '")>故障类型管理</a>';
                }
            }
        ]],
        pagination:true,
        pageSize:20,
        rownumbers:true
    });
}

/**
 * 打开添加数据词典项窗口
 */
function openDeviceTypeSaveEdit() {
    $('#deviceTypeForm')[0].reset();
    $("#deviceSysId3").val($("#deviceSysId2").val());
    $('#deviceType_dialog').dialog('open');
}

/**
 * 保存数据词典项信息
 */
function saveDeviceType() {
    //判断页面填写是否为空
    if(!$("#deviceTypeForm").form("validate")){
        return;
    }
    parameterItem = $('#deviceTypeForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var deviceTypeId = $('#deviceTypeId').val();
    //
    if(null == deviceTypeId || deviceTypeId == ""){
        $.post("/device/sys/saveDeviceType?" + access_token,parameterItem,function(data){
            if(200 == data.status){
                $('#deviceType_table').datagrid('reload');
                $('#deviceType_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/device/sys/modifyDeviceType?" + access_token,parameterItem,function(data){
            if(200 == data.status){
                $('#deviceType_table').datagrid('reload');
                $('#deviceType_dialog').dialog('close');
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
function deviceTypeEdit(id){
    $('#deviceTypeForm')[0].reset();
    $("#deviceSysNameHid1").val($("#deviceSysNameHid").val());
    //设置弹出框信息
    $('#deviceType_table').datagrid('selectRecord',id);
    generateDialog($('#deviceType_table').datagrid('getSelected'),"deviceType_dialog");
    $('#deviceType_dialog').dialog('open');
}

/**
 * 删除词典项信息
 */
function delDeviceType(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/device/sys/delDeviceType?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#deviceType_table').datagrid('reload');
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

///////////////////////////////巡检项管理//////////////////////
var parameterCheckItem;
function assParameterCheckItem(){
    parameterCheckItem = {
        deviceTypeId:$('#checkItemDeviceTypeHid').val(),
        projectName:$("#projectNameTxt2").val(),
        checkItemVsType:$("#vsTypeSel2").val()
    };
}

function findCheckItemList() {
    assParameterCheckItem();
    $('#checkItem_table').datagrid('options').queryParams = parameterCheckItem;
    $('#checkItem_table').datagrid('options').pageNumber = 1;
    $('#checkItem_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#checkItem_table').datagrid('reload');
}

/**
 * 词典项管理
 */
function checkItemPage(id, deviceType){
    $("#deviceSysNameHid2").val($("#deviceSysNameHid1").val());
    $("#deviceTypeNameHid2").val(deviceType);
    $('#checkItemDeviceTypeHid').val(id);
    $("#checkItem_search").dialog('open');
    initCheckItemDataGrid();
}

/**
 * 查询数据表格
 */
function initCheckItemDataGrid(){
    assParameterCheckItem();
    $('#checkItem_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        fit:true,
        toolbar: "#checkItemToolbarItem",
        fitColumns:true,
        collapsible:true,
        url:'/check/item/findCheckItemList?'　+　access_token,
        queryParams:parameterCheckItem,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'checkName',title:'名称',width:100},
            {field:'checkAction',title:'类型',width:100,formatter:function(value){
                    if (value != undefined){
                        if(value == 1){
                            return "巡检";
                        }else if(value == 2){
                            return "检测";
                        }else if(value == 3){
                            return "保养";
                        }
                    }
                    return "";
                }},
            {field:'checkType',title:'巡检方式',width:100,formatter:function(value){
                if (value != undefined){
                    if (value == 0){
                        return "默认";
                    }else if(value == 1){
                        return "数值";
                    }else if(value == 2){
                        return "计数";
                    }else if(value == 3){
                        return "描述";
                    }
                }
                return "";
                }},
            {field:'periodType',title:'建议巡检周期',width:100,formatter:function(value){
                    if (value != undefined){
                        if (value == 9){
                            return "每两年";
                        }else if(value == 5){
                            return "每年";
                        }else if(value == 4){
                            return "每半年";
                        }else if(value == 3){
                            return "每季度";
                        }else if(value == 2){
                            return "每两月";
                        }else if(value == 1){
                            return "每月";
                        }else if(value == 10){
                            return "每半月";
                        }else if(value == 7){
                            return "每周";
                        }
                    }
                    return "";
                }},
            {field:'checkSpecification',title:'检查标准',width:100},
            {field:'type',title:'巡检值类型',width:100,formatter:function(value){
                    if (value != undefined){
                        if(value == 1){
                            return "一般项";
                        }else if(value == 2){
                            return "大小值项";
                        }
                    }
                    return "";
                }},
            {field:'unit',title:'单位',width:100},
            {field:'maxValueFloat',title:'最大值',width:100},
            {field:'minValueFloat',title:'最小值',width:100},
            {field:'id',title:'操作',width:80,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = checkItemEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delCheckItem(' + value + ')>删除</a>';
                }
            }
        ]],
        pagination:true,
        pageSize:20,
        rownumbers:true
    });
}


/**
 * 打开添加数据词典项窗口
 */
function openCheckItemSaveEdit() {
    $('#checkItemForm')[0].reset();
    $("#deviceSysNameHid3").val($("#deviceSysNameHid2").val());
    $("#deviceTypeNameHid3").val($("#deviceTypeNameHid2").val());
    $("#saveCheckItemDeviceTypeHid").val($("#checkItemDeviceTypeHid").val());
    $('#checkItem_dialog').dialog('open');
    onChangeCheckType()
}

/**
 * 保存数据词典项信息
 */
function saveCheckItem() {
    //判断页面填写是否为空
    if(!$("#checkItemForm").form("validate")){
        return;
    }
    parameterCheckItem = $('#checkItemForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var checkItemId = $('#checkItemIdHid').val();
    //
    if(null == checkItemId || checkItemId == ""){
        $.post("/check/item/saveCheckItem?" + access_token,parameterCheckItem,function(data){
            if(200 == data.status){
                $('#checkItem_table').datagrid('reload');
                $('#checkItem_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/check/item/modifyCheckItem?" + access_token,parameterCheckItem,function(data){
            if(200 == data.status){
                $('#checkItem_table').datagrid('reload');
                $('#checkItem_dialog').dialog('close');
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
function checkItemEdit(id){
    $('#checkItemForm')[0].reset();
    $("#deviceSysNameHid3").val($("#deviceSysNameHid2").val());
    $("#deviceTypeNameHid3").val($("#deviceTypeNameHid2").val());
    //设置弹出框信息
    $('#checkItem_table').datagrid('selectRecord',id);
    generateDialog($('#checkItem_table').datagrid('getSelected'),"checkItem_dialog");
    $('#checkItem_dialog').dialog('open');
    onChangeCheckType()
}

/**
 * 删除词典项信息
 */
function delCheckItem(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/check/item/delCheckItem?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#checkItem_table').datagrid('reload');
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

function onChangeCheckType() {
    if ($("#checkTypeSel").val() == 1){
        $("#checkItemTypeTr").show();
        $("#checkItemValueTr").show();
    }else{
        $("#checkItemTypeTr").hide();
        $("#checkItemValueTr").hide();
    }
}

//////////////////////////////设备故障类型
var parameterDeviceFaultType;
function assParameterDeviceFaultType(){
    parameterDeviceFaultType = {
        deviceTypeId:$('#deviceTypeIdHidden').val(),
        faultType:$('#vsSelFaultType').val(),
        causeAnalysis:$('#vsSelCauseAnalysis').val(),
        repairMethod:$('#vsSelRepairMethod').val(),
        repairProposal:$('#vsSelRepairProposal').val()
    };
}

function findDeviceFaultTypeList() {
    assParameterDeviceFaultType();
    $('#deviceFaultType_table').datagrid('options').queryParams = parameterDeviceFaultType;
    $('#deviceFaultType_table').datagrid('options').pageNumber = 1;
    $('#deviceFaultType_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#deviceFaultType_table').datagrid('reload');
}

/**
 * 设备故障类型管理
 */
function faultTypePage(id, parentId, deviceType){
    console.log(deviceType);
    $('#deviceTypeIdHidden').val(id);
    $('#deviceSysId6').val(parentId);
    $('#deviceTypeType').val(deviceType);
    $("#fault_type_search").dialog('open');
    initFaultTypeDataGrid();
}

/**
 * 查询 故障类型数据表格
 */
function initFaultTypeDataGrid(){
    assParameterDeviceFaultType();
    $('#deviceFaultType_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        fit:true,
        toolbar:"#faultTypeToolbarItem",
        fitColumns:true,
        collapsible:true,
        url:'/device/fault/findDeviceFaultTypeList?' + access_token,
        queryParams:parameterDeviceFaultType,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'faultType',title:'故障类型',width:130},
            {field:'deviceType',title:'设备类型',width:50,formatter:function(value){
                    if (value != undefined){
                        if (value == 1){
                            return "默认设备";
                        }else if(value == 2){
                            return "水压监测";
                        }else if(value == 3){
                            return "智能水浸";
                        }else if(value == 4){
                            return "火警主机";
                        }else if(value == 5){
                            return "极早期预警";
                        }else if(value == 6){
                            return "火眼报警";
                        }else if(value == 7){
                            return "声光报警";
                        }else if(value == 8){
                            return "智能消火栓";
                        }else if(value == 9){
                            return "可然气";
                        }else if(value == 10){
                            return "电气火灾";
                        }else if(value == 11){
                            return "智能烟感";
                        }
                    }
                    return "";
                }},
            {field:'faultColor',title:'颜色',width:40},
            {field:'faultEventType',title:'事件类型',width:70,formatter:function(value){
                    if (value != undefined){
                        if(value == 2){
                            return "水压监测事件";
                        }else if(value == 3){
                            return "智能水浸事件";
                        } else if(value == 4){
                            return "火警主机事件";
                        } else if(value == 5){
                            return "极早期预警事件";
                        } else if(value == 6){
                            return "火眼报警事件";
                        } else if(value == 7){
                            return "声光报警事件";
                        } else if(value == 8){
                            return "智能消火栓事件";
                        } else if(value == 9){
                            return "可然气事件";
                        } else if(value == 10){
                            return "电气火灾事件";
                        } else if(value == 11){
                            return "智能烟感事件";
                        }
                    }
                    return "默认事件";
                }},
            {field:'sort',title:'排序',width:20},
            {field:'faultLevel',title:'故障级别',width:40},
            {field:'causeAnalysis',title:'原因分析',width:130},
            {field:'repairMethod',title:'维修方式',width:130},
            {field:'repairProposal',title:'维护建议',width:130},
            {field:'id',title:'操作',width:80,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = deviceFaultTypeEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delDeviceFaultType(' + value + ')>删除</a>';
                }
            }
        ]],
        pagination:true,
        pageSize:20,
        rownumbers:true
    });
}

/**
 * 打开添加 设备故障类型窗口
 */
function openDeviceFaultTypeSaveEdit() {
    $('#deviceFaultTypeForm')[0].reset();

    $("#saveDeviceTypeIdHidden").val($("#deviceTypeIdHidden").val());
    $("#deviceSysId5").val($("#deviceSysId6").val());
    $("#deviceTypeType1").val($("#deviceTypeType").val());

    $('#faultType_dialog').dialog('open');
    //onChangeDeviceFaultType()
}

/**
 * 保存 设备故障类型
 */
function saveDeviceFault() {
    // 判断页面填写是否为空
    if(!$("#deviceFaultTypeForm").form("validate")){
        return;
    }
    parameterDeviceFaultType = $('#deviceFaultTypeForm').serialize();

    //获取主键值，根据主键值判断添加或修改
    var deviceFaultTypeId = $('#deviceFaultTypeIdHid').val();
    if(null == deviceFaultTypeId || deviceFaultTypeId == ""){
        $.post("/device/fault/saveDeviceFaultType?" + access_token, parameterDeviceFaultType, function(data){
            if(200 == data.status){
                $('#deviceFaultType_table').datagrid('reload');
                $('#faultType_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/device/fault/updateDeviceFaultType?" + access_token, parameterDeviceFaultType, function(data){
            if(200 == data.status){
                $('#deviceFaultType_table').datagrid('reload');
                $('#faultType_dialog').dialog('close');
                showMsg('修改成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }
}

/**
 * 修改 故障类型
 */
function deviceFaultTypeEdit(id){
    $('#deviceFaultTypeForm')[0].reset();
    //设置弹出框信息
    $('#deviceFaultType_table').datagrid('selectRecord', id);
    generateDialog($('#deviceFaultType_table').datagrid('getSelected'),"faultType_dialog");
    $('#faultType_dialog').dialog('open');
    //onChangeDeviceFaultType()
}

/**
 * 删除词典项信息
 */
function delDeviceFaultType(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/device/fault/delDeviceFaultType?" + access_token,{ "id":id, "deleted":1},function(data){
                if(200 == data.status){
                    $('#deviceFaultType_table').datagrid('reload');
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

function onChangeDeviceFaultType() {

}
