$(function(){
    $('#path_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                savePath();
            }
        },{
            text:'取消',
            handler:function(){
                $("#path_dialog").dialog('close');
                cleanDialog("path_dialog");
            }
        }]
    })
    initPathDataGrid();
});

function assParameter(type,parentId){
    parameter = {
        type : type,
        parentId:parentId,
        pathName:$("#pathNameTxt" + type).val(),
        url:$("#urlTxt" + type).val()
    };
}

function findPathList1() {
    assParameter(1,"");
    $('#path_table').datagrid('options').queryParams = parameter;
    $('#path_table').datagrid('options').pageNumber = 1;
    $('#path_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#path_table').datagrid('reload');
}

function findPathList2() {
    assParameter(2,$('#pathId').val());
    $('#pathMenu_table').datagrid('options').queryParams = parameter;
    $('#pathMenu_table').datagrid('options').pageNumber = 1;
    $('#pathMenu_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#pathMenu_table').datagrid('reload');
}

function findPathList3() {
    assParameter(3,$('#pathMenuId').val());
    $('#pathMenuFun_table').datagrid('options').queryParams = parameter;
    $('#pathMenuFun_table').datagrid('options').pageNumber = 1;
    $('#pathMenuFun_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#pathMenuFun_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initPathDataGrid(){
    assParameter(1,null);
    $('#path_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#pathToolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/permission/findPathListPage?'+ access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'pathName',title:'名称',width:100},
            {field:'orderNum',title:'排序号',width:100},
            {field:'type',title:'权限类型',width:50,formatter:function(val){
                    return val == 1 ? '模块' : val == 2 ? '菜单' :'功能';
                }},
            {field:'projectType',title:'Path类型',width:50,formatter:function(val){
                    return val == 1 ? 'PC' : val == 2 ? 'APP' : val == 4 ? '项目外':'管理后台';
                }},
            {field:'url',title:'URL',width:300},
            {field:'id',title:'操作',width:200,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = delPath(' + value + ',"path_table")>删除</a> | '+
                        '<a href="javascript:void(0)" onclick = pathEdit(' + value + ',"path",1)>编辑</a> | '+
                        '<a href="javascript:void(0)" onclick = pathMenuPage(' + value + ')>Path菜单类型管理</a>';
                }
            }
        ]],
        pagination:true,
        pageSize:30,
        rownumbers:true
    });
}

//菜单列表
function pathMenuPage(id){
    $('#pathId').val(id);
    console.log(id);
    $("#pathMenu_search").dialog('open');
    initPathMenuDataGrid(id);
}

//查询菜单列表
function initPathMenuDataGrid(id){
    assParameter(2,id);
    $('#pathMenu_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        fit:true,
        toolbar: "#pathMenuToolbarItem",
        fitColumns:true,
        collapsible:true,
        url:'/permission/findPathListPage?'+ access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'pathName',title:'名称',width:100},
            {field:'orderNum',title:'排序号',width:100},

            {field:'type',title:'权限类型',width:50,formatter:function(val){
                    return val == 1 ? '<span class="STATE1">模块</span>' : val == 2 ? '<span class="STATE1">菜单</span>' :'<span class="STATE1">功能</span>';
                }},
            {field:'projectType',title:'Path类型',width:50,formatter:function(val){
                    return val == 1 ? 'PC' : val == 2 ? 'APP' : val == 4 ? '项目外':'管理后台';
                }},
            {field:'url',title:'URL',width:300},
            {field:'id',title:'操作',width:150,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = delPath(' + value + ',"pathMenu_table")>删除</a> | '+
                        '<a href="javascript:void(0)" onclick = pathEdit(' + value + ',"pathMenu",2)>编辑</a> | '+
                        '<a href="javascript:void(0)" onclick = pathMenuFunPage(' + value + ')>菜单功能管理</a>';
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
function openPathSaveEdit(type) {
    cleanDialog("path_dialog");
    if(type == 1 ){
        $("#type").html("<option value = '1'>模块</option>");
        $("#parentId").val(0);
    }else if(type == 2){
        $("#type").html("<option value = '2'>菜单</option>");
        $("#parentId").val($("#pathId").val());
    }else if(type == 3){
        $("#parentId").val($("#pathMenuId").val());
        $("#type").html("<option value = '3'>功能</option>");
    }
    $('#path_dialog').dialog('open');
}

/**
 * 保存Path信息
 */
function savePath() {
    //判断页面填写是否为空
    if(!$("#pathForm").form("validate")){
        return;
    }
    parameter = $('#pathForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var pathId = $('#id').val();
    //
    if(null == pathId || pathId == ""){
        $.post("/permission/savePath?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#path_dialog').dialog('close');
                if ($('#type').val() == 1){
                    $('#path_table').datagrid('reload');
                } else if($('#type').val() == 2){
                    $('#pathMenu_table').datagrid('reload');
                }else if($('#type').val() == 3){
                    $('#pathMenuFun_table').datagrid('reload');
                }
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/permission/modifyPath?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#path_dialog').dialog('close');
                if ($('#type').val() == 1){
                    $('#path_table').datagrid('reload');
                } else if($('#type').val() == 2){
                    $('#pathMenu_table').datagrid('reload');
                }else if($('#type').val() == 3){
                    $('#pathMenuFun_table').datagrid('reload');
                }
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
 * 修改信息
 */
function pathEdit(id,tableName,type){
    console.log(tableName);
    cleanDialog("path_dialog");
    //设置弹出框信息
    var table = '#'+tableName+'_table';
    $(table).datagrid('selectRecord',id);
    if(type == 1 ){
        $("#parentId").val(0);
        $("#type").html("<option value = '1'>模块</option>");
    }else if(type == 2){
        $("#parentId").val(id);
        $("#type").html("<option value = '2'>菜单</option>");
    }else if(type == 3){
        $("#parentId").val(id);
        $("#type").html("<option value = '3'>功能</option>");
    }
    generateDialog($(table).datagrid('getSelected'),"path_dialog");
    $('#path_dialog').dialog('open');
    $(table).datagrid('reload');
}

/**
 * 菜单功能管理
 */
function pathMenuFunPage(id){
    $('#pathMenuId').val(id);
    console.log(id);
    $("#pathMenuFun_search").dialog('open');
    initPathMenuFunDataGrid(id);
}

/**
 * 菜单功能表格
 */
function initPathMenuFunDataGrid(id){
    assParameter(3,id);
    $('#pathMenuFun_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#pathMenuFunToolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/permission/findPathListPage?'+ access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'pathName',title:'名称',width:100},
            {field:'orderNum',title:'排序号',width:100},
            {field:'type',title:'权限类型',width:50,formatter:function(val){
                    return val == 1 ? '<span class="STATE1">模块</span>' : val == 2 ? '<span class="STATE1">菜单</span>' :'<span class="STATE1">功能</span>';
                }},
            {field:'projectType',title:'Path类型',width:50,formatter:function(val){
                    return val == 1 ? 'PC' : val == 2 ? 'APP' : val == 4 ? '项目外':'管理后台';
                }},
            {field:'url',title:'URL',width:300},
            {field:'id',title:'操作',width:150,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = delPath(' + value + ',"pathMenuFun_table")>删除</a> | '+
                        '<a href="javascript:void(0)" onclick = pathEdit(' + value + ',"pathMenuFun",3)>编辑</a>  '
            }}
        ]],
        pagination:true,
        pageSize:30,
        rownumbers:true
    });
}

/**
 * 删除词典信息
 */
function delPath(id,tableName){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/permission/delPath?" + access_token,{ "id":id},function(data){
                if(200 == data.status){
                        $('#' + tableName).datagrid('reload');
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


