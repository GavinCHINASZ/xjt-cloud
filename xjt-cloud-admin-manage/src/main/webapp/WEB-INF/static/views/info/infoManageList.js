$(function(){
    $('#infoManageDialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveInfoManage();
            }
        },{
            text:'取消',
            handler:function(){
                $("#infoManageDialog").dialog('close');
                $("#infoManageForm")[0].reset();
            }
        }]
    });

    $('#infoContent_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveInfoContent();
            }
        },{
            text:'取消',
            handler:function(){
                $("#infoContent_dialog").dialog('close');
                $("#infoContentForm")[0].reset();
            }
        }]
    });
    infoManageSelList();
    infoManageDataGrid();
});

/**
 * 初使设备类型列表
 * user:wangzhiwen
 * date:2017/8/29 17:26
 **/
function infoManageSelList(){
    //资讯平台
    $.post("/dict/findCacheDictItemListByCode?" + access_token,{"dictCode":"INFO_CLOUD_TYPE"},function(data){
        $("#cloudTypeSel").html('<option value="">所有</option>');
        $("#cloudTypeSel1").html('<option value="">所有</option>');
        $.each(data,function(index,dict){
            $("#cloudTypeSel").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
            $("#cloudTypeSel1").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });

    //资讯页面类型
    $.post("/dict/findCacheDictItemListByCode?" + access_token,{"dictCode":"INFO_PAGE_TYPE"},function(data){
        $("#pageTypeSel").html('<option value="">所有</option>');
        $("#pageTypeSel1").html('<option value="">所有</option>');
        $.each(data,function(index,dict){
            $("#pageTypeSel").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
            $("#pageTypeSel1").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });

    //资讯信息类型
    $.post("/dict/findCacheDictItemListByCode?" + access_token,{"dictCode":"INFO_INFO_TYPE"},function(data){
        $("#infoTypeSel").html('<option value="">所有</option>');
        $("#infoTypeSel1").html('<option value="">所有</option>');
        $.each(data,function(index,dict){
            $("#infoTypeSel").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
            $("#infoTypeSel1").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

function assParameter(){
    parameter = {
        cloudType:$("#cloudTypeSel").val(),
        pageType:$("#pageTypeSel").val(),
        infoType:$("#infoTypeSel").val()
    };
}
function findInfoManageList(){
    assParameter();
    $('#infoManage_table').datagrid('options').queryParams = parameter;
    $('#infoManage_table').datagrid('options').pageNumber = 1;
    $('#infoManage_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#infoManage_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function infoManageDataGrid(){
    assParameter();
    $('#infoManage_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/info/manage/findInfoManageList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'cloudTypeDesc',title:'平台类型',width:150},
            {field:'pageTypeDesc',title:'页面类型',width:150},
            {field:'infoTypeDesc',title:'信息类型',width:150},
            {field:'imgWidth',title:'图片宽',width:150},
            {field:'imgHeight',title:'图片高',width:150},
            {field:'id',title:'操作',width:100,formatter:function(value,row){
                    return '<a href="javascript:void(0)" onclick = infoManageEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delInfoManage(' + value + ')>删除</a> | ' +
                        '<a href="javascript:void(0)" onclick = infoManagePage(' + value + ',"' + row.cloudType + '","' + row.cloudTypeDesc +
                        '","' + row.pageType + '","' + row.pageTypeDesc + '","' + row.infoType + '","' + row.infoTypeDesc +'")>信息管理</a>';
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
function openInfoManageEdit() {
    $('#infoManageForm')[0].reset();
    cleanDialog("infoManageDialog");
    $('#infoManageDialog').dialog('open');
    $('#idHid').val();
}

/**
 * 保存数据词典信息
 */
function saveInfoManage() {
    //判断页面填写是否为空
    if(!$("#infoManageForm").form("validate")){
        return;
    }
    parameter = $('#infoManageForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var id = $('#idHid').val();
    //
    if(null == id || id == ""){
        $.post("/info/manage/saveInfoManage?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#infoManage_table').datagrid('reload');
                $('#infoManageDialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/info/manage/modifyInfoManage?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#infoManage_table').datagrid('reload');
                $('#infoManageDialog').dialog('close');
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
function infoManageEdit(id){
    $('#infoManageForm')[0].reset();
    cleanDialog("infoManageDialog");
    //设置弹出框信息
    $('#infoManage_table').datagrid('selectRecord',id);
    var row = $('#infoManage_table').datagrid('getSelected');
    generateDialog(row,"infoManageDialog");
    $('#infoManageDialog').dialog('open');
}

/**
 * 删除词典信息
 */
function delInfoManage(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/info/manage/delInfoManage?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#infoManage_table').datagrid('reload');
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
 * 词典项管理
 */
function infoManagePage(id,cloudType,cloudTypeDesc, pageType,pageTypeDesc, infoType,infoTypeDesc){
    $('#infoManageIdHid').val(id);
    $('#cloudTypeHid2').val(cloudType);
    $('#cloudTypeTxt').val(cloudTypeDesc);
    $('#pageTypeHid').val(pageType);
    $('#pageTypeTxt').val(pageTypeDesc);
    $('#infoTypeHid').val(infoType);
    $('#infoTypeTxt').val(infoTypeDesc);
    $("#itemDialog").dialog('open');
    infoContentDataGrid();
}


///////////////////////////////////////////////////////////////资讯信息管理////////////////////////////////////////
var parameterItem;
function assParameterItem(){
    parameterItem = {
        infoManageId:$('#infoManageIdHid').val()
    };
}
function findInfoContentList(){
    assParameterItem();
    $('#infoContent_table').datagrid('options').queryParams = parameterItem;
    $('#infoContent_table').datagrid('options').pageNumber = 1;
    $('#infoContent_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#infoContent_table').datagrid('reload');
}
/**
 * 查询数据表格
 */
function infoContentDataGrid(){
    assParameterItem();
    $('#infoContent_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbarItem",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/info/manage/findInfoContentList?' + access_token,
        queryParams:parameterItem,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'title',title:'标题',width:200},
            {field:'sort',title:'排序',width:50},
            {field:'imgWidth',title:'图片宽',width:100},
            {field:'imgHeight',title:'图片高',width:100},
            {field:'intro',title:'简介',width:500},
            {field:'url',title:'第三方url',width:200},
            {field:'thumbnail',title:'缩略图',width:200},
            {field:'id',title:'操作',width:150,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = infoContentEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delInfoContent(' + value + ')>删除</a> ';
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
function openInfoContentEdit() {
    $('#infoContentForm')[0].reset();
    cleanDialog("infoContent_dialog");
    editor.html("");
    $("#infoContentHid").val("");
    $("#infoManageIdHid1").val($("#infoManageIdHid").val());
    $("#cloudTypeHid3").val($("#cloudTypeHid2").val());
    $("#pageTypeHid1").val($("#pageTypeHid").val());
    $("#infoTypeHid1").val($("#infoTypeHid").val())
    $("#cloudTypeTxt3").val($("#cloudTypeTxt").val());
    $("#pageTypeTxt1").val($("#pageTypeTxt").val());
    $("#infoTypeTxt1").val($("#infoTypeTxt").val());
    $('#infoContent_dialog').dialog('open');
}

/**
 * 保存数据词典信息
 */
function saveInfoContent() {
    //判断页面填写是否为空
    if(!$("#infoContentForm").form("validate")){
        return;
    }
    parameterItem = $('#infoContentForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var infoContent = $('#infoContentHid').val();
    //
    if(null == infoContent || infoContent == ""){
        $.post("/info/manage/saveInfoContent?" + access_token,parameterItem,function(data){
            if(200 == data.status){
                $('#infoContent_table').datagrid('reload');
                $('#infoContent_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/info/manage/modifyInfoContent?" + access_token,parameterItem,function(data){
            if(200 == data.status){
                $('#infoContent_table').datagrid('reload');
                $('#infoContent_dialog').dialog('close');
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
function infoContentEdit(id){
    $('#infoContentForm')[0].reset();
    cleanDialog("infoContent_dialog");
    $("#infoManageIdHid1").val($("#infoManageIdHid").val());
    $("#cloudTypeHid3").val($("#cloudTypeHid2").val());
    $("#pageTypeHid1").val($("#pageTypeHid").val());
    $("#infoTypeHid1").val($("#infoTypeHid").val());
    //设置弹出框信息
    $('#infoContent_table').datagrid('selectRecord',id);
    var row = $('#infoContent_table').datagrid('getSelected');
    editor.html(row.content);
    generateDialog(row,"infoContent_dialog");
    $('#infoContent_dialog').dialog('open');
}

/**
 * 删除词典项信息
 */
function delInfoContent(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/info/manage/delInfoContent?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#infoContent_table').datagrid('reload');
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

