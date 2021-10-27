$(function(){
    $('#quartz_config_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveQuartzConfig();
            }
        },{
            text:'取消',
            handler:function(){
                $("#quartz_config_dialog").dialog('close');
                cleanDialog("quartz_config_dialog");
            }
        }]
    });
    
    $('#quartz_config_item_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveQuartzConfigItem();
            }
        },{
            text:'取消',
            handler:function(){
                $("#quartz_config_item_dialog").dialog('close');
                cleanDialog("quartz_config_item_dialog");
            }
        }]
    });
    initQuartzConfigDataGrid();
});

function assParameter(){
    parameter = {
    };
}

/**
 * 查询数据表格
 */
function initQuartzConfigDataGrid(){
    assParameter();
    $('#quartz_config_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/quartz/config/findQuartzConfigList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect: true,
        idField: 'id',
        columns:[[
            {field:'group',title:'分组名称',width:100},
            {field:'name',title:'任务名称',width:140},
            {field:'cron',title:'cron',width:100},
            {field:'quartzClass',title:'quartzClass',width:220},
            {field:'msg',title:'描述',width:160},
            {field:'status',title:'状态',width:50,formatter:function(val){
                    return val == 0 ? '<span class="STATE1">启用</span>' : '<span class="STATE0">停用</span>';
                }},
            {field:'id', title:'操作', width:100, formatter:function(value,row){
                    return '<a href="javascript:void(0)" onclick = quartzConfigModifyStatus(' + value + ',' + row.status + ',\"'+ row.name + '\",\"' + row.group + '\")>启用/停用</a> | ' +
                    '<a href="javascript:void(0)" onclick = quartzConfigDel(' + value + ',\"'+ row.name + '\",\"' + row.group + '\")>删除</a> | ' +
                    '<a href="javascript:void(0)" onclick = quartzConfigEdit(' + value + ')>编辑</a>';
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
function openQuartzConfigSaveEdit() {
    cleanDialog("quartz_config_dialog");
    $('#quartz_config_dialog').dialog('open');
}

/**
 * 保存新增 或 更新 数据词典信息
 */
function saveQuartzConfig() {
    // 判断页面填写是否为空
    if(!$("#quartzConfigForm").form("validate")){
        return;
    }

    parameter = $('#quartzConfigForm').serialize();

    //获取主键值，根据主键值判断添加或修改
    var quartzConfigId = $('#quartzConfigId').val();
    // 新增 保存
    if(null == quartzConfigId || quartzConfigId == ""){
        $.post("/quartz/config/saveQuartzConfig?" + access_token, parameter, function(data){
            if(200 == data.status){
                $('#quartz_config_table').datagrid('reload');
                $('#quartz_config_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json");
    }else{
        // 更新
        $.post("/quartz/config/modifyQuartzConfig?" + access_token, parameter, function(data){
            if(200 == data.status){
                $('#quartz_config_table').datagrid('reload');
                $('#quartz_config_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error){
            showMsg(error.responseText);
        });
    }
}

/**
 * 修改词典信息
 */
function quartzConfigEdit(id){
    cleanDialog("quartz_config_dialog");
    //设置弹出框信息
    $('#quartz_config_table').datagrid('selectRecord',id);
    generateDialog($('#quartz_config_table').datagrid('getSelected'),"quartz_config_dialog");
    $('#quartz_config_dialog').dialog('open');
}

/**
 * 删除 定时任务
 */
function quartzConfigDel(id, name, group){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/quartz/config/quartzConfigDel?" + access_token, {"id":id, "name":name, "group":group, "status":99},
                function(data){
                    if(200 == data.status){
                        $('#quartz_config_table').datagrid('reload');
                        showMsg('删除成功！');
                    }  else{
                        showMsg(data.msg);
                    }
                }, "json").error(function (error){
                    showMsg(error.responseText);
                }
            );
        }
    });
}

/**
 * 启用/停用
 */
function quartzConfigModifyStatus(id, status, name, group){
    $.messager.confirm(titleInfo, '您确定操作吗？', function(r){
        if (r){
            $.post("/quartz/config/updateQuartzConfigStatus?" + access_token, {"id":id, "name":name, "group":group, "status": status != null && status == 0 ? 1 : 0},
                function(data){
                    if(200 == data.status){
                        $('#quartz_config_table').datagrid('reload');
                        showMsg('操作成功！');
                    }  else{
                        showMsg(data.msg);
                    }
                }, "json").error(function (error){
                    showMsg(error.responseText);
                }
            );
        }
    });
}

/**
 * 打开添加窗口
 */
function openDictItemSaveEdit() {
    cleanDialog("quartz_config_item_dialog");
    $("#quartzConfigId3").val($("#quartzConfigId2").val());
    $('#quartz_config_item_dialog').dialog('open');
}

/**
 * 保存数据词典项信息
 */
function saveQuartzConfigItem() {
    //判断页面填写是否为空
    if(!$("#quartzConfigItemForm").form("validate")){
        return;
    }
    parameterItem = $('#quartzConfigItemForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var quartzConfigItemId = $('#quartzConfigItemId').val();
    //
    if(null == quartzConfigItemId || quartzConfigItemId == ""){
        $.post("/quartz/config/saveQuartzConfigItem?" + access_token,parameterItem, function(data){
            if(200 == data.status){
                $('#quartz_config_item_table').datagrid('reload');
                $('#quartz_config_item_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/quartz/config/modifyQuartzConfigItem?" + access_token, parameterItem, function(data){
            if(200 == data.status){
                $('#quartz_config_item_table').datagrid('reload');
                $('#quartz_config_item_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }
}
