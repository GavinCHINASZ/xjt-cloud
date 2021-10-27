$(function(){
    $('#cloudInit_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveCloudInit();
            }
        },{
            text:'取消',
            handler:function(){
                $("#cloudInit_dialog").dialog('close');
                $("#cloudInitForm")[0].reset();
            }
        }]
    });
    initCloudInitSelList();
    initCloudInitDataGrid();
    $("#parentTitleTd").hide();
    $("#parentTxtTd").hide();
});

/**
 * 初使设备类型列表
 * user:wangzhiwen
 * date:2017/8/29 17:26
 **/
function initCloudInitSelList(){
    $.post("/dict/findCacheDictItemListByCode?" + access_token,{"dictCode":"CLOUD_INIT_TYPE"},function(data){
        $("#cloudInitTypeSel").html('<option value="">所有</option>');
        $("#saveCloudInitTypeSel").html('<option value="">所有</option>');
        $("#itemCloudInitTypeSel").html('<option value="">所有</option>');
        $.each(data,function(index,dict){
            $("#cloudInitTypeSel").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
            $("#saveCloudInitTypeSel").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
            $("#itemCloudInitTypeSel").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });

    $.post("/dict/findCacheDictItemListByCode?" + access_token,{"dictCode":"CLOUD_INIT_INFO_TYPE"},function(data){
        $("#cloudInitInfoTypeSel").html('<option value="">所有</option>');
        $("#saveCloudInitInfoTypeSel").html('<option value="">所有</option>');
        $("#itemCloudInitInfoTypeSel").html('<option value="">所有</option>');
        $.each(data,function(index,dict){
            $("#cloudInitInfoTypeSel").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
            $("#saveCloudInitInfoTypeSel").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
            $("#itemCloudInitInfoTypeSel").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

function assParameter(){
    parameter = {
        cloudType:$("#cloudInitTypeSel").val(),
        type:$("#cloudInitInfoTypeSel").val(),
        parentId:0
    };
}
function findCloudInitList(){
    assParameter();
    $('#cloudInit_table').datagrid('options').queryParams = parameter;
    $('#cloudInit_table').datagrid('options').pageNumber = 1;
    $('#cloudInit_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#cloudInit_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initCloudInitDataGrid(){
    assParameter();
    $('#cloudInit_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/cloud/init/findCloudInitList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'cloudTypeDesc',title:'平台类型',width:50},
            {field:'typeDesc',title:'信息分类',width:50},
            {field:'appVersion',title:'app版本号',width:50},
            {field:'carousel',title:'是否轮播',width:40,formatter:function(val){
                    return val == 1 ? '<span class="STATE1">是</span>' : '<span class="STATE0">否</span>';
                }},
            {field:'infoName',title:'信息名称',width:100},
            {field:'typeCode',title:'信息编码',width:80},
            {field:'title',title:'信息标题',width:100},
            {field:'sortOrder',title:'顺序',width:20},
            {field:'modifyStatus',title:'推送状态',width:40,formatter:function(val){
                    return val == 1 ? '<span class="STATE1">没有</span>' : '<span class="STATE0">有</span>';
                }},
            {field:'status',title:'状态',width:20,formatter:function(val){
                    return val == 1 ? '<span class="STATE1">启用</span>' : '<span class="STATE0">关闭</span>';
                }},
            {field:'keyword',title:'关键词',width:300},
            {field:'id',title:'操作',width:100,formatter:function(value,row){
                    return '<a href="javascript:void(0)" onclick = cloudInitEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delCloudInit(' + value + ')>删除</a> | ' +
                        '<a href="javascript:void(0)" onclick = cloudInitItemPage(' + value + ',"' + row.infoName + '","' + row.cloudType +
                        '","' + row.cloudTypeDesc + '","' + row.type + '","' + row.typeDesc + '","' + row.appVersion +'")>信息项管理</a>';
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
function openCloudInitSaveEdit() {
    //cleanDialog("cloudInit_dialog");
    $('#cloudInitForm')[0].reset();
    $("#saveParentIdHid").val(0);
    editor.html("");
    $("#parentTitleTd").hide();
    $("#parentTxtTd").hide();
    $('#cloudInit_dialog').dialog('open');
}

/**
 * 保存数据词典信息
 */
function saveCloudInit() {
    //判断页面填写是否为空
    if(!$("#cloudInitForm").form("validate")){
        return;
    }
    $("#saveCloudInitTypeSel").attr("disabled", false);
    $("#saveCloudInitInfoTypeSel").attr("disabled", false);
    var parentId = $("#saveParentIdHid").val();
    parameter = $('#cloudInitForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var cloudInitId = $('#cloudInitIdHid').val();
    //
    if(null == cloudInitId || cloudInitId == ""){
        $.post("/cloud/init/saveCloudInit?" + access_token,parameter,function(data){
            if(200 == data.status){
                if (parentId == null || parentId == "" || parentId == "0"){
                    $('#cloudInit_table').datagrid('reload');
                } else {
                    $('#cloudInit_item_table').datagrid('reload');
                }
                $('#cloudInit_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/cloud/init/modifyCloudInit?" + access_token,parameter,function(data){
            if(200 == data.status){
                if (parentId == null || parentId == "" || parentId == "0"){
                    $('#cloudInit_table').datagrid('reload');
                } else {
                    $('#cloudInit_item_table').datagrid('reload');
                }
                $('#cloudInit_dialog').dialog('close');
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
function cloudInitEdit(id){
    $('#cloudInitForm')[0].reset();
    //cleanDialog("cloudInit_dialog");
    $("#parentTitleTd").hide();
    $("#parentTxtTd").hide();
    //设置弹出框信息
    $('#cloudInit_table').datagrid('selectRecord',id);
    var row = $('#cloudInit_table').datagrid('getSelected');
    editor.html(row.content);
    generateDialog(row,"cloudInit_dialog");
    $('#cloudInit_dialog').dialog('open');
}

/**
 * 删除词典信息
 */
function delCloudInit(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/cloud/init/delCloudInit?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#cloudInit_table').datagrid('reload');
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
function assParameterItem(){
    parameterItem = {
        parentId:$('#parentIdHid').val()
    };
}
/**
 * 词典项管理
 */
function cloudInitItemPage(id,infoName,cloudType,cloudTypeDesc, type,typeDesc, appVersion){
    $('#parentIdHid').val(id);
    $('#parentNameHid').val(infoName);
    $('#cloudTypeHid').val(cloudType);
    $('#cloudTypeDescHid').val(cloudTypeDesc);
    $('#typeHid').val(type);
    $('#typeDescHid').val(typeDesc);
    $('#appVersionHid').val(appVersion);
    $("#itemDialog").dialog('open');
    initCloudInitItemDataGrid();
}

/**
 * 查询数据表格
 */
function initCloudInitItemDataGrid(){
    assParameterItem();
    $('#cloudInit_item_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbarItem",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/cloud/init/findCloudInitList?' + access_token,
        queryParams:parameterItem,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'carousel',title:'是否轮播',width:40,formatter:function(val){
                    return val == 1 ? '<span class="STATE1">是</span>' : '<span class="STATE0">否</span>';
                }},
            {field:'infoName',title:'信息名称',width:100},
            {field:'typeCode',title:'信息编码',width:80},
            {field:'title',title:'信息标题',width:100},
            {field:'sortOrder',title:'顺序',width:20},
            {field:'modifyStatus',title:'推送状态',width:40,formatter:function(val){
                    return val == 1 ? '<span class="STATE1">没有</span>' : '<span class="STATE0">有</span>';
                }},
            {field:'status',title:'状态',width:20,formatter:function(val){
                    return val == 1 ? '<span class="STATE1">启用</span>' : '<span class="STATE0">关闭</span>';
                }},
            {field:'keyword',title:'关键词',width:150},
            {field:'id',title:'操作',width:60,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = cloudInitItemEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delCloudInitItem(' + value + ')>删除</a> ';
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
function openCloudInitItemSaveEdit() {
    //cleanDialog("cloudInit_dialog");
    $('#cloudInitForm')[0].reset();
    editor.html("");
    $("#parentTitleTd").show();
    $("#parentTxtTd").show();
    $("#cloudInitIdHid").val("");
    $("#parentTxt").val($("#parentNameHid").val());
    $("#saveParentIdHid").val($("#parentIdHid").val());
    $("#saveCloudInitTypeSel").val($("#cloudTypeHid").val());
    $("#saveCloudInitInfoTypeSel").val($("#typeHid").val());
    $("#saveAppVersionTxt").val($("#appVersionHid").val());
    $("#parentTxt").attr("readonly", true);
    $("#saveCloudInitTypeSel").attr("disabled", true);
    $("#saveCloudInitInfoTypeSel").attr("disabled", true);
    $("#saveAppVersionTxt").attr("readonly", true);
    $('#cloudInit_dialog').dialog('open');
}

/**
 * 修改词典项信息
 */
function cloudInitItemEdit(id){
    //cleanDialog("cloudInit_dialog");
    $('#cloudInitForm')[0].reset();
    $("#parentTitleTd").show();
    $("#parentTxtTd").show();
    $("#parentTitleTd").show();
    $("#parentTxtTd").show();
    $("#saveParentIdHid").val($("#parentIdHid").val());
    $("#parentTxt").val($("#parentNameHid").val());
    $("#saveCloudInitTypeSel").val($("#cloudTypeHid").val());
    $("#saveCloudInitInfoTypeSel").val($("#typeHid").val());
    $("#saveAppVersionTxt").val($("#appVersionHid").val());
    $("#parentTxt").attr("readonly", true);
    $("#saveCloudInitTypeSel").attr("disabled", true);
    $("#saveCloudInitInfoTypeSel").attr("disabled", true);
    $("#saveAppVersionTxt").attr("readonly", true);
    //设置弹出框信息
    $('#cloudInit_item_table').datagrid('selectRecord',id);
    var row = $('#cloudInit_item_table').datagrid('getSelected');
    editor.html(row.content);
    generateDialog(row,"cloudInit_dialog");
    $('#cloudInit_dialog').dialog('open');
}

/**
 * 删除词典项信息
 */
function delCloudInitItem(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/cloud/init/delCloudInit?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#cloudInit_item_table').datagrid('reload');
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

