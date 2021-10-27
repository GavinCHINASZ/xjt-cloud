$(function(){
    $('#user_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveUser();
            }
        },{
            text:'取消',
            handler:function(){
                $("#user_dialog").dialog('close');
                cleanDialog("user_dialog");
            }
        }]
    });
    initUserDataGrid();
   // initProjectSel();
});
function initProjectSel(){
    $("#projectSel").append('<option value=""><ption>');
    $("#projectSel").append('<option value="0">管理后台<ption>');
    $("#projectSel1").append('<option value=""><ption>');
    $("#projectSel1").append('<option value="0">管理后台<ption>');
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
        loginName:$('#loginNameTxt').val(),
        userName:$('#userNameTxt').val(),
        phone:$('#phoneTxt').val()
    };
}

function findUserList(){
    assParameter();
    $('#user_table').datagrid('options').queryParams = parameter;
    $('#user_table').datagrid('options').pageNumber = 1;
    $('#user_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#user_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initUserDataGrid(){
    assParameter();
    $('#user_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/user/findUserList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'loginName',title:'账号',width:100},
            {field:'phone',title:'手机号码',width:100},
            {field:'userName',title:'姓名',width:100},
            {field:'projectName',title:'所属项目',width:100},
            {field:'modifyStatusDesc',title:'账号修改状态',width:300},
            {field:'createTime',title:'创建时间',width:150},
            {field:'statusDesc',title:'状态',width:100},
            {field:'id',title:'操作',width:150,formatter:function(value,row){
                    return '<a href="javascript:void(0)" onclick = userEdit(' + value + ')>编辑</a>' +
                        (row.status <= 98 ? ' | <a href="javascript:void(0)" onclick = delUser(' + value + ')>删除</a>' : "")  +
                    ' | <a href="javascript:void(0)" onclick = userRoleManage(' + value + ')>后台角色管理</a>';
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
function openUserSaveEdit() {
    $("#userStatusTr").hide();
    cleanDialog("user_dialog");
    $('#user_dialog').dialog('open');
}

/**
 * 保存数据词典信息
 */
function saveUser() {
    if($("#projectSel").val() == null){
        alert("请选择用户所属项目");
        return;
    }
    //判断页面填写是否为空
    if (!$("#userForm").form("validate")) {
        return;
    }
    parameter = $('#userForm').serialize();
    if ($("#").val() == null) {
        $.post("/user/saveUser?" + access_token, parameter, function (data) {
            if (200 == data.status) {
                $('#user_table').datagrid('reload');
                $('#user_dialog').dialog('close');
                showMsg('保存成功！');
            } else {
                showMsg(data.msg);
            }
        }, "json").error(function (error, status, info) {
            showMsg(error.responseText);
        });
    } else {
        $.post("/user/modifyUser?" + access_token, parameter, function (data) {
            if (200 == data.status) {
                $('#user_table').datagrid('reload');
                $('#user_dialog').dialog('close');
                showMsg('保存成功！');
            } else {
                showMsg(data.msg);
            }
        }, "json").error(function (error, status, info) {
            showMsg(error.responseText);
        });
    }
}
/**
 * 修改词典信息
 */
function userEdit(id){
    $("#userStatusTr").show();
    cleanDialog("user_dialog");
    //设置弹出框信息
    $('#user_table').datagrid('selectRecord',id);
    generateDialog($('#user_table').datagrid('getSelected'),"user_dialog");
    $('#user_dialog').dialog('open');
}

/**
 * 删除词典信息
 */
function delUser(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/user/modifyUser?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#user_table').datagrid('reload');
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

function userRoleManage(id) {
    $('#userIdHid').val(id);
    userRelationRoleTableInit();
    notUserRelationRoleTableInit();
    $('#userRoleDialog').dialog('open');
}

function assUserRelationRoleParameter(){
    parameter = {
        userId:$('#userIdHid').val(),
        roleName:$('#roleNameTxt1').val()
    };
}

function findUserRelationRoleList(){
    assUserRelationRoleParameter();
    $('#user_relation_role_table').datagrid('options').queryParams = parameter;
    $('#user_relation_role_table').datagrid('options').pageNumber = 1;
    $('#user_relation_role_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#user_relation_role_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function userRelationRoleTableInit(){
    assUserRelationRoleParameter();
    $('#user_relation_role_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#userRelationRoleToolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/role/findUserRelationAdminRoleList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'roleName',title:'角色名称',width:100},
            {field:'sourceType',title:'平台类型',width:100,formatter:function (value) {
                    if (value == 1){
                        return "管理后台";
                    } else if(value == 2){
                        return "PC";
                    }
                    return "其它";
                }},
            {field:'status',title:'状态',width:100,formatter:function (value) {
                    if (value == 1){
                        return "正常";
                    } else if(value == 2){
                        return "下线";
                    }
                    return "其它";
                }},
            {field:'id',title:'操作',width:100,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = delUserRelationRole(' + value + ')>删除</a>';
                }
            }
        ]],
        pagination:true,
        pageSize : 30,
        rownumbers:true
    });
}

/**
 * 删除词典信息
 */
function delUserRelationRole(id){
    parameter = {
        userId:$('#userIdHid').val(),
        id:id
    };
    $.messager.confirm(titleInfo, '您确定删除用户角色吗？', function(r){
        if (r){
            $.post("/role/delUserRelationRole?" + access_token,parameter,function(data){
                if(200 == data.status){
                    $('#user_relation_role_table').datagrid('reload');
                    $('#user_not_relation_role_table').datagrid('reload');
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

//未关联角色初使化
function assUserNotRelationRoleParameter(){
    parameter = {
        userId:$('#userIdHid').val(),
        roleName:$('#roleNameTxt').val()
    };
}

function findNotRelationRoleList(){
    assUserNotRelationRoleParameter();
    $('#user_not_relation_role_table').datagrid('options').queryParams = parameter;
    $('#user_not_relation_role_table').datagrid('options').pageNumber = 1;
    $('#user_not_relation_role_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#user_not_relation_role_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function notUserRelationRoleTableInit(){
    assUserNotRelationRoleParameter();
    $('#user_not_relation_role_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#searchtoolRole",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/role/findUserNotRelationAdminRoleList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'roleName',title:'角色名称',width:100},
            {field:'sourceType',title:'平台类型',width:100,formatter:function (value) {
                    if (value == 1){
                        return "管理后台";
                    } else if(value == 2){
                        return "PC";
                    }
                    return "其它";
                }},
            {field:'status',title:'状态',width:100,formatter:function (value) {
                    if (value == 1){
                        return "正常";
                    } else if(value == 2){
                        return "下线";
                    }
                    return "其它";
                }},
            {field:'id',title:'操作',width:100,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = relationRole(' + value  + ')>关联</a>' ;
                }
            }
        ]],
        pagination:true,
        pageSize : 30,
        rownumbers:true
    });
}

function relationRole(id){
    parameter = [{
        orgUserId:0,
        projectId:0,
        sourceType:1,
        userId:$('#userIdHid').val(),
        id:id
    }];
    parameter = $.param({"json":JSON.stringify(parameter)},true);
    $.post("/role/saveUserRelationRole?" + access_token,parameter,function(data){
        if(200 == data.status){
            $('#user_relation_role_table').datagrid('reload');
            $('#user_not_relation_role_table').datagrid('reload');
            showMsg('关联成功！');
        }  else{
            showMsg(data.msg);
        }
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}