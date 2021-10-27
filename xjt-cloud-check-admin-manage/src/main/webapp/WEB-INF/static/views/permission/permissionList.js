$(function(){
    $('#permission_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                savePermission();
            }
        },{
            text:'取消',
            handler:function(){
                $("#permission_dialog").dialog('close');
                cleanDialog("permission_dialog");
            }
        }]
    });

    $('#permissionPath_search').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                savePermissionPath();
            }
        },{
            text:'取消',
            handler:function(){
                $("#permissionPath_search").dialog('close');
                cleanDialog("permissionPath_search");
            }
        }]
    });
    parentPermissionSelInit();
    initPermissionDataGrid();
});

function parentPermissionSelInit() {
    $("#parentPermissionSel").empty();
    $("#parentPermissionSel1").empty();
    $("#parentPermissionSel").append('<option value="">ALL</option>');
    $("#parentPermissionSel").append('<option value="0">一级</option>');
    $("#parentPermissionSel1").append('<option value="0">一级</option>');
    $.post("/permission/findPermissionList?" + access_token,null,function(data){
        $.each(data,function(index,permission){
            $("#parentPermissionSel").append('<option value=' + permission.id + '>' + permission.permissionName + '</option>');
            $("#parentPermissionSel1").append('<option value=' + permission.id + '>' + permission.permissionName + '</option>');
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

function assParameter(){
    var orderCols =[];
    orderCols[0] = "sort";
    parameter = {
        permissionName:$('#permissionNameTxt').val(),
        sign:$('#signTxt').val(),
        perType:$('#perTypeSel').val(),
        permissionType:$('#permissionSel').val(),
        parentId:$('#parentPermissionSel').val()
    };
}

function findPermissionList(){
    assParameter();
    $('#permission_table').datagrid('options').queryParams = parameter;
    $('#permission_table').datagrid('options').pageNumber = 1;
    $('#permission_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#permission_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initPermissionDataGrid(){
    assParameter();
    $('#permission_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/permission/findPermissionListPage?'+ access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'menuName',title:'菜单名称',width:150},
            {field:'menuSign',title:'菜单别名',width:150},
            {field:'permissionName',title:'权限名称',width:150},
            {field:'sign',title:'别名',width:150},
            {field:'sort',title:'排序',width:50},
            {field:'perType',title:'类型',width:80,formatter:function(val){
                    return val == 1 ? '<span class="STATE1">后台管理权限</span>' : '<span class="STATE0">项目权限</span>';
                }},
            {field:'permissionType',title:'权限类型',width:100,formatter:function(val){
                    return val == 1 ? '<span class="STATE1">模块</span>' : val == 2 ? '<span class="STATE1">菜单</span>' :'<span class="STATE1">功能</span>';
                }},
            {field:'status',title:'权限状态',width:70,formatter:function(value){
                    if (value == 1){
                        return '上线';
                    } else if(value == 2){
                        return '<font color="red">下线</font>';
                    }
                    return '';
                }},
            {field:'memo',title:'备注',width:200},
            {field:'id',title:'操作',width:200,formatter:function(value,data){
                    var line = "";
                    if(data.status == 1){
                        line = '<a href="javascript:void(0)" onclick = modifyPermissionStatus(' + value + ',2)>下线</a> | ';
                    }else if(data.status == 2){
                        line = '<a href="javascript:void(0)" onclick = modifyPermissionStatus(' + value + ',1)>上线</a> | ';
                    }
                    return '<a href="javascript:void(0)" onclick = permissionEdit(' + value + ',1)>编辑</a>  | ' +
                        '<a href="javascript:void(0)" onclick = delPermission(' + value + ',1)>删除</a>  | ' +
                        line  +  '<a href="javascript:void(0)" onclick = permissionPathPage(' + value + ',"'+data.permissionName+'",'+data.perType+')>权限管理</a> ';

                }
            }
        ]],
        pagination:true,
        pageSize:30,
        rownumbers:true
    });
}

function delPermission(id) {
    $.messager.confirm(titleInfo, '您确定删除该权限吗？', function(r) {
        if (r) {
            parameter = {
                id:id,
                status: 99
            };
            $.post("/permission/modifyPermission?" + access_token, parameter, function (data) {
                if (200 == data.status) {
                    $('#permission_table').datagrid('reload');
                    parentPermissionSelInit();
                    showMsg("权限删除成功!");
                } else {
                    showMsg(data.msg);
                }
            }, "json").error(function (error, status, info) {
                showMsg(error.responseText);
            });
        }
    });
}

/**
 * 打开添加窗口
 */
function openPermissionSaveEdit() {
    cleanDialog("permission_dialog");
    $('#permission_dialog').dialog('open');
}

/**
 * 修改信息
 */
function permissionEdit(id){
    cleanDialog("product_dialog");
    //设置弹出框信息
    $('#permission_table').datagrid('selectRecord',id);
    var row = $('#permission_table').datagrid('getSelected');
    generateDialog(row,"permission_dialog");
    $('#permission_dialog').dialog('open');
}

function modifyPermissionStatus(id,status) {
    var msg;
    if (status == 1) {
        msg = "上线";
    } else {
        msg = "下线";
    }
    $.messager.confirm(titleInfo, '您确定' + msg + '吗？', function(r) {
        if (r) {
            parameter = {
                id:id,
                status: status
            };
            $.post("/permission/modifyPermission?" + access_token, parameter, function (data) {
                if (200 == data.status) {
                    $('#permission_table').datagrid('reload');
                    $('#permission_dialog').dialog('close');

                    showMsg("权限" + msg + "成功!");
                } else {
                    showMsg(data.msg);
                }
            }, "json").error(function (error, status, info) {
                showMsg(error.responseText);
            });
        }
    });
}

/**
 * 保存数据信息
 */
function savePermission() {
    //判断页面填写是否为空
    if(!$("#permissionForm").form("validate")){
        return;
    }
    parameter = $('#permissionForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var permissionId = $('#permissionIdHid').val();
    //
    if(null == permissionId || permissionId == ""){
        $.post("/permission/savePermission?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#permission_table').datagrid('reload');
                $('#permission_dialog').dialog('close');
                parentPermissionSelInit();
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/permission/modifyPermission?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#permission_table').datagrid('reload');
                $('#permission_dialog').dialog('close');
                parentPermissionSelInit();
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
function permissionPathEdit(id){
    cleanDialog("permission_dialog");
    //设置弹出框信息
    $('#permission_table').datagrid('selectRecord',id);
    generateDialog($('#permission_table').datagrid('getSelected'),"permission_dialog");
    $('#permission_dialog').dialog('open');
}

var permissionPath;
function assPermissionPath(){
    permissionPath = {
        permissionId:$('#permissionId').val()
    };
}

/**
 * 管理
 */
function permissionPathPage(id,permissionName,perType){
    $('#tree').tree([]);
    $('#permissionId').val(id);
    $('#permissionName').val(permissionName);
    $("#perType").val(perType);
    $("#permissionPath_search").dialog('open');
    initPermissionPathDataGrid();
}

/**
 * 管理
 */
function permissionProjectPage(id){
    cleanDialog("permissionProject_search");
    $('#permissionId1').val(id);
    $("#permissionProject_search").dialog('open');
    initPermissionProjectDataGrid();
}

function findPermissionProjectList() {
    asseFindPermissionProjectParameter();
    $('#permissionProject_table').datagrid('options').queryParams = parameter;
    $('#permissionProject_table').datagrid('options').pageNumber = 1;
    $('#permissionProject_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#permissionProject_table').datagrid('reload');
}

function asseFindPermissionProjectParameter() {
    parameter = {
        id:$('#permissionId1').val(),
        status: $('#permissionStatusSel').val(),
        projectName:$('#projectNameTxt').val()
    };
}

/**
 * 查询权限树结构
 */
function initPermissionProjectDataGrid(){
    asseFindPermissionProjectParameter();
    $('#permissionProject_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#projectPermissionToolbar",
        fit:false,
        fitColumns:true,
        collapsible:true,
        url:'/permission/findRoleProjectList?'+ access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: false,
        idField:'id',
        columns:[[
            {field:'projectId',title:'<input type=\"checkbox\" id=\"projectRadioList\"  onchange="selectAll(this,\'projectRadioName\')"/>',
                width:50,formatter:function (value) {
                    return "<input type=\"checkbox\"  name=\"projectRadioName\" value=\"" + value + "\" >";
                }},
            {field:'projectName',title:'项目名称',width:200},
            {field:'permissionName',title:'权限名称',width:200},
            {field:'sign',title:'权限别名',width:200},
            {field:'permissionType',title:'权限类型',width:100,formatter:function(val){
                    return val == 1 ? '<span class="STATE1">模块</span>' : val == 2 ? '<span class="STATE1">菜单</span>' :'<span class="STATE1">功能</span>';
                }},
            {field:'status',title:'权限状态',width:100,formatter:function(value){
                if (value == 1){
                    return '上线';
                } else if(value == 2){
                    return '<font color="red">下线</font>';
                }
                return '';
            }},
            {field:'id',title:'操作',width:100,formatter:function(value,data){
                    var line = "";
                    if(data.status == 1){
                        line = '<a href="javascript:void(0)" onclick = modifyPermissionProjectStatus(' + data.projectId + ',2)>下线</a> ';
                    }else if(data.status == 2){
                        line = '<a href="javascript:void(0)" onclick = modifyPermissionProjectStatus(' + data.projectId + ',1)>上线</a>  ';
                    }
                    return line;
                }
            }
        ]],
        pagination:true,
        pageSize : 30,
        rownumbers:true
    });
}

function modifyPermissionProjectStatus(id,status) {
    var ids;
    if (id == '' && id != 'ALL'){
        ids = getCheckedRadioValue("projectRadioName");
        if(ids == null|| ids == undefined){
            alert("请选择记录");
            return;
        }
    }else if(id == 'ALL'){
        id = '';
    }

    var msg;
    if (status == 1) {
        msg = "上线";
    } else {
        msg = "下线";
    }
    $.messager.confirm(titleInfo, '您确定' + msg + '吗？', function(r) {
        if (r) {
            parameter = {
                id:$('#permissionId1').val(),
                projectId:id,
                projectIds:ids,
                status: status
            };
            $.post("/role/modifyRolePermission?" + access_token, parameter, function (data) {
                if (200 == data.status) {
                    $('#permissionProject_table').datagrid('reload');
                    showMsg("权限" + msg + "成功!");
                } else {
                    showMsg(data.msg);
                }
            }, "json").error(function (error, status, info) {
                showMsg(error.responseText);
            });
        }
    });
}

function getPathTreeParameter() {
    var permissionId =  $('#permissionId').val();
    var projectTypes ;
    if($('#perType').val()==1){
        projectTypes = [3];
    }else {
        projectTypes = [1,2,4];
    }

    return "&permissionId=" + permissionId + "&projectTypes=" + projectTypes;
}

/**
 * 查询权限树结构
 */
function initPermissionPathDataGrid(){

    $('#tree').tree([]);
    $('#tree').tree({
        url: "/permission/findPermissionPathTree?" + access_token + getPathTreeParameter(),
        loadFilter: function(data){
            if (data.d){
                return data.d;
            } else {
                return data;
            }
        },
        onLoadSuccess:function() {
            $(this).tree("collapseAll");
        }
    });
}

/**
 * 打开添加Path权限
 */
function openPermissionPathSaveEdit() {
    cleanDialog("permission_item_dialog");
    $("#parentId").val($("#permissionId").val());
    $('#permissionPath_dialog').dialog('open');
    initPathList($("#preType").val(),$("#permissionId").val());
}

/**
 * 重置加载项
 * @param projectType
 */
function initPathList(preType,permissionId){
    var projectTypes ;
    if(preType==1){
        projectTypes = [3];
    }else {
        projectTypes = [1,2];
    }
    $.post("/permission/findPathList?" + access_token+"&projectTypes="+projectTypes+"&permissionId="+permissionId,null,function(data){

        var html="";
        $.each(data,function(index,dict){
            html +='<option value=' + dict.id + '>' + dict.pathName + '</option>';
        });
        $("#pathId").html(html);
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}


/**
 * 保存权限
 */
function savePermissionPath() {
    //判断页面填写是否为空
    var obj = $('#tree').tree('getChecked', "checked");
    var paraItem = obj.map(function (item) {
        return {
            pathId:item.id,
            permissionId:item.permissionId,
            url:item.url,
            projectType:item.projectType,
            permissionType:item.type
        }
    })
   var json = {json: JSON.stringify( paraItem )};
    $.post("/permission/savePermissionPath?" + access_token,json,function(data){
        if(200 == data.status){
            $('#permission_item_table').datagrid('reload');
            $('#permission_item_dialog').dialog('close');
            showMsg('保存成功！');
        }else{
            showMsg(data.msg);
        }
    },"json");

}
