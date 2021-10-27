$(function(){
    $('#dict_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveDict();
            }
        },{
            text:'取消',
            handler:function(){
                $("#dict_dialog").dialog('close');
                cleanDialog("dict_dialog");
            }
        }]
    });
    $('#dict_item_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveDictItem();
            }
        },{
            text:'取消',
            handler:function(){
                $("#dict_item_dialog").dialog('close');
                cleanDialog("dict_item_dialog");
            }
        }]
    });
    initDictDataGrid();
});
function assParameter(){
    parameter = {
        dictName:$('#dictNameTxt').val(),
        dictCode:$('#dictCodeTxt').val(),
        itemName:$('#dictItemNameTxt').val(),
        itemValue:$('#dictItemCodeTxt').val()
    };
}

function findDictList() {
    assParameter();
    $('#dict_table').datagrid('options').queryParams = parameter;
    $('#dict_table').datagrid('options').pageNumber = 1;
    $('#dict_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#dict_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initDictDataGrid(){
    assParameter();
    $('#dict_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#searchtoolDict",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/dict/findDictList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'dictName',title:'字典名称',width:100},
            {field:'dictCode',title:'字典编码',width:300},
            {field:'description',title:'描述',width:300},
            {field:'createTime',title:'创建时间',width:100},
            {field:'status',title:'状态',width:100,formatter:function(val){
                    return val == 1 ? '<span class="STATE1">启用</span>' : '<span class="STATE0">不启用</span>';
                }},
            {field:'id',title:'操作',width:100,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = dictEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delDict(' + value + ')>删除</a> | ' +
                        '<a href="javascript:void(0)" onclick = dictItemPage(' + value + ')>词典项管理</a>';
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
function openDictSaveEdit() {
    cleanDialog("dict_dialog");
    $('#dict_dialog').dialog('open');
}

/**
 * 保存数据词典信息
 */
function saveDict() {
    //判断页面填写是否为空
    if(!$("#dictForm").form("validate")){
        return;
    }
    parameter = $('#dictForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var dictId = $('#dictId').val();
    //
    if(null == dictId || dictId == ""){
        $.post("/dict/saveDict?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#dict_table').datagrid('reload');
                $('#dict_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json");
    }else{
        $.post("/dict/modifyDict?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#dict_table').datagrid('reload');
                $('#dict_dialog').dialog('close');
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
function dictEdit(id){
    cleanDialog("dict_dialog");
    //设置弹出框信息
    $('#dict_table').datagrid('selectRecord',id);
    generateDialog($('#dict_table').datagrid('getSelected'),"dict_dialog");
    $('#dict_dialog').dialog('open');
}

/**
 * 删除词典信息
 */
function delDict(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/dict/delDict?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#dict_table').datagrid('reload');
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
        dictId:$('#dictId2').val(),
        itemName:$('#dictItemNameTxt1').val(),
        itemValue:$('#dictItemCodeTxt1').val()
    };
}
/**
 * 词典项管理
 */
function dictItemPage(id){
    $('#dictId2').val(id);
    $("#itemDialog").dialog('open');
    initDictItemDataGrid();
}
function findDictItemList() {
    assParameterItem();
    $('#dict_item_table').datagrid('options').queryParams = parameterItem;
    $('#dict_item_table').datagrid('options').pageNumber = 1;
    $('#dict_item_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#dict_item_table').datagrid('reload');
}
/**
 * 查询数据表格
 */
function initDictItemDataGrid(){
    assParameterItem();
    $('#dict_item_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbarItem",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/dict/findDictItemList?' + access_token,
        queryParams:parameterItem,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'itemName',title:'字典项名字',width:100},
            {field:'itemCode',title:'字典项编码',width:300},
            {field:'itemValue',title:'字典项值',width:100},
            {field:'sortOrder',title:'排序',width:100},
            {field:'description',title:'字典项描述',width:300},
            {field:'memo',title:'备注',width:100},
            {field:'createTime',title:'创建时间',width:100},
            {field:'status',title:'状态',width:100,formatter:function(val){
                    return val == 1 ? '<span class="STATE1">启用</span>' : '<span class="STATE0">不启用</span>';
                }},
            {field:'id',title:'操作',width:100,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = dictItemEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delDictItem(' + value + ')>删除</a>';
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
function openDictItemSaveEdit() {
    cleanDialog("dict_item_dialog");
    $("#dictId3").val($("#dictId2").val());
    $('#dict_item_dialog').dialog('open');
}

/**
 * 保存数据词典项信息
 */
function saveDictItem() {
    //判断页面填写是否为空
    if(!$("#dictItemForm").form("validate")){
        return;
    }
    parameterItem = $('#dictItemForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var dictItemId = $('#dictItemId').val();
    //
    if(null == dictItemId || dictItemId == ""){
        $.post("/dict/saveDictItem?" + access_token,parameterItem,function(data){
            if(200 == data.status){
                $('#dict_item_table').datagrid('reload');
                $('#dict_item_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/dict/modifyDictItem?" + access_token,parameterItem,function(data){
            if(200 == data.status){
                $('#dict_item_table').datagrid('reload');
                $('#dict_item_dialog').dialog('close');
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
function dictItemEdit(id){
    cleanDialog("dict_item_dialog");
    //设置弹出框信息
    $('#dict_item_table').datagrid('selectRecord',id);
    generateDialog($('#dict_item_table').datagrid('getSelected'),"dict_item_dialog");
    $('#dict_item_dialog').dialog('open');
}

/**
 * 删除词典项信息
 */
function delDictItem(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/dict/delDictItem?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#dict_item_table').datagrid('reload');
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

