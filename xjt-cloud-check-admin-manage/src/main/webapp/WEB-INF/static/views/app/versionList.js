$(function(){
    $('#version_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveVersion();
            }
        },{
            text:'取消',
            handler:function(){
                $("#version_dialog").dialog('close');
                cleanDialog("version_dialog");
            }
        }]
    });
    initVersionSelList();
    initVersionDataGrid();
});

/**
 * 初使设备类型列表
 * user:wangzhiwen
 * date:2017/8/29 17:26
 **/
function initVersionSelList(){
    $.post("/dict/findCacheDictItemListByCode?" + access_token,{"dictCode":"APP_TYPE"},function(data){
        $("#typeSel").html('<option value="">所有</option>');
        $("#saveTypeSel").html('<option value="">请选择</option>');
        $.each(data,function(index,dict){
            $("#typeSel").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
            $("#saveTypeSel").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
    $.post("/dict/findCacheDictItemListByCode?" + access_token,{"dictCode":"APP_SOURCE_TYPE"},function(data){
        $("#sourceTypeSel").html('<option value="">所有</option>');
        $("#saveSourceTypeSel").html('<option value="">请选择</option>');
        $.each(data,function(index,dict){
            $("#sourceTypeSel").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
            $("#saveSourceTypeSel").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

function assParameter(){
    parameter = {
    };
}

function findVersionList(){
    assParameter();
    $('#version_table').datagrid('options').queryParams = parameter;
    $('#version_table').datagrid('options').pageNumber = 1;
    $('#version_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#version_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initVersionDataGrid(){
    assParameter();
    $('#version_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbar",
        fit:false,
        fitColumns:true,
        collapsible:true,
        url:'/version/findVersionList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'version',title:'版本',width:100},
            {field:'versionNum',title:'版本号',width:100},
            {field:'versionSize',title:'版本大小',width:100},
            {field:'typeDesc',title:'类型',width:100},
            {field:'sourceTypeDesc',title:'来源',width:100},
            {field:'url',title:'下载路径',width:100},
            {field:'createUserName',title:'发布用户',width:100},
            {field:'fileSize',title:'文件大小',width:100,formatter:function(value){
                    return (value == undefined ? "" : value + 'M');
                }},
            {field:'updateType',title:'更新类型',width:100,formatter:function(value){
                    if (value == 2){
                        return "每次推送";
                    } else if(value == 3){
                        return "强制更新";
                    }else {
                        return "推送一次";
                    }
                }},
            {field:'versionTitle',title:'更新主题',width:100},
            {field:'updateContent',title:'更新内容',width:100},
            {field:'id',title:'操作',width:100,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = versionEdit(' + value + ')>编辑</a> ';
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
function openVersionSaveEdit() {
    cleanDialog("version_dialog");
    $('#version_dialog').dialog('open');
}

/**
 * 保存数据信息
 */
function saveVersion() {
    //判断页面填写是否为空
    if(!$("#versionForm").form("validate")){
        return;
    }
    parameter = $('#versionForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var versionId = $('#versionId').val();
    //
    if(null == versionId || versionId == ""){
        $.post("/version/saveVersion?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#version_table').datagrid('reload');
                $('#version_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/version/modifyVersion?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#version_table').datagrid('reload');
                $('#version_dialog').dialog('close');
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
function versionEdit(id){
    cleanDialog("version_dialog");
    //设置弹出框信息
    $('#version_table').datagrid('selectRecord',id);
    generateDialog($('#version_table').datagrid('getSelected'),"version_dialog");
    $('#version_dialog').dialog('open');
}
