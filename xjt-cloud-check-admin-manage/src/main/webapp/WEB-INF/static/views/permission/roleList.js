$(function(){
    $('#role_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveRole();
            }
        },{
            text:'取消',
            handler:function(){
                $("#role_dialog").dialog('close');
                cleanDialog("role_dialog");
            }
        }]
    });
    initProjectList();
    initRoleDataGrid();
});
function initProjectList(){
    $("#projectSel").append('<option value="0">ALL<ption>');
    $("#projectSel1").append('<option value="0">ALL</option>');
    $("#projectSel").append('<option value="0">管理后台<ption>');
    $("#projectSel1").append('<option value="0">管理后台</option>');
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
        roleName:$('#roleNameTxt').val(),
        sourceType:$('#cloudTypeSel').val(),
        projectName:$('#projectNameTxt').val()
    };
}

function findRoleList(){
    assParameter();
    $('#role_table').datagrid('options').queryParams = parameter;
    $('#role_table').datagrid('options').pageNumber = 1;
    $('#role_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#role_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initRoleDataGrid(){
    assParameter();
    $('#role_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/role/findRoleList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'sourceType',title:'平台类型',width:100,formatter:function (value) {
                if (value == 1){
                    return "管理后台";
                } else if(value == 2){
                    return "PC";
                }
                    return "其它";
            }},
            {field:'roleName',title:'角色名称',width:100},
            {field:'createTime',title:'创建时间',width:150},
            {field:'id',title:'操作',width:150,formatter:function(value,data){
                    return '<a href="javascript:void(0)" onclick = roleEdit(' + value + ')>编辑</a>' +
                        ' | <a href="javascript:void(0)" onclick = delRole(' + value + ')>删除</a>' +
                        ' | <a href="javascript:void(0)" onclick = rolePermissionManage(' + value + ',' + data.sourceType + ',' + data.projectId +')>角色权限</a>' +
                        ' | <a href="javascript:void(0)" onclick = openDefaultRoleSaveEdit(' + value + ',' + data.sourceType + ',' + data.projectId +')>用户管理</a>';
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
function openRoleSaveEdit() {
    cleanDialog("role_dialog");
    $('#role_dialog').dialog('open');
}



/**
 * 保存数据词典信息
 */
function saveRole() {
    if ($("#roleNameTxt1").val() == "" || $("#cloudTypeSel2").val() == "") {
        alert("角色名称与平台类型不能为空");
        return;
    }
    parameter = $('#roleForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var roleId = $('#roleIdHid').val();
    //
    if(null == roleId || roleId == ""){
        $.post("/role/saveRole?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#role_table').datagrid('reload');
                $('#role_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/role/modifyRole?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#role_table').datagrid('reload');
                $('#role_dialog').dialog('close');
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
function roleEdit(id){
    cleanDialog("role_dialog");
    //设置弹出框信息
    $('#role_table').datagrid('selectRecord',id);
    generateDialog($('#role_table').datagrid('getSelected'),"role_dialog");
    $('#role_dialog').dialog('open');
}

/**
 * 删除词典信息
 */
function delRole(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/role/delRole?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#role_table').datagrid('reload');
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

function rolePermissionManage(id,sourceType,projectId) {
    $('#roleIdHid1').val(id);
    $('#sourceTypeHid').val(sourceType);
    relationRoleTableInit();
    notRelationRoleTableInit();
    $('#roleRoleDialog').dialog('open');
}

function assRelationRoleParameter(){
    parameter = {
        roleId:$('#roleIdHid1').val(),
        menuName:$('#menuNameTxt').val(),
        permissionName:$('#permissionNameTxt3').val(),
        perType:$('#cloudTypeSel3').val()
    };
}

function findRoleRelationRoleList() {
    assRelationRoleParameter();
    $('#relation_permissionIds_table').datagrid('options').queryParams = parameter;
    $('#relation_permissionIds_table').datagrid('options').pageNumber = 1;
    $('#relation_permissionIds_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#relation_permissionIds_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function relationRoleTableInit(){
    assRelationRoleParameter();
    $('#relation_permissionIds_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#searchtoolRelationRole",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/role/findRoleRelationPermissionList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'menuName',title:'菜单名称',width:150},
            {field:'menuSign',title:'菜单别名',width:150},
            {field:'permissionName',title:'权限名称',width:150},
            {field:'sign',title:'权限别名',width:150},
            {field:'perType',title:'平台类型',width:80,formatter:function (value) {
                    if (value == 1){
                        return "后台管理";
                    } else if(value == 2){
                        return "项目";
                    }
                    return "";
                }},
            {field:'permissionType',title:'权限类型',width:80,formatter:function (value) {
                    if (value == 1){
                        return "模块";
                    } else if(value == 2){
                        return "菜单";
                    }else if(value == 3){
                        return "功能";
                    }
                    return "";
                }},
            {field:'memo',title:'备注',width:300},
            {field:'rolePermissionId',title:'操作',width:100,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = delRelationRole(' + value + ')>删除</a>';
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
function delRelationRole(id){
    parameter = {
        rolePermissionId:id
    };
    $.messager.confirm(titleInfo, '您确定删除用户角色吗？', function(r){
        if (r){
            $.post("/role/delRoleRelationPermission?" + access_token,parameter,function(data){
                if(200 == data.status){
                    $('#not_relation_permissionIds_table').datagrid('reload');
                    $('#relation_permissionIds_table').datagrid('reload');
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
function assNotRelationRoleParameter(){
    parameter = {
        roleId:$('#roleIdHid1').val(),
        menuName:$('#menuNameTxt1').val(),
        permissionName:$('#permissionNameTxt').val(),
        perType:$('#cloudTypeSel1').val()
    };
}

function findRoleNotRelationRoleList(){
    assNotRelationRoleParameter();
    $('#not_relation_permissionIds_table').datagrid('options').queryParams = parameter;
    $('#not_relation_permissionIds_table').datagrid('options').pageNumber = 1;
    $('#not_relation_permissionIds_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#not_relation_permissionIds_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function notRelationRoleTableInit(){
    assNotRelationRoleParameter();
    $('#not_relation_permissionIds_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#searchtoolRole",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/role/findNotRoleRelationPermissionList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'menuName',title:'菜单名称',width:150},
            {field:'menuSign',title:'菜单别名',width:150},
            {field:'permissionName',title:'权限名称',width:150},
            {field:'sign',title:'权限别名',width:150},
            {field:'perType',title:'平台类型',width:80,formatter:function (value) {
                    if (value == 1){
                        return "后台管理";
                    } else if(value == 2){
                        return "项目";
                    }
                    return "";
                }},
            {field:'permissionType',title:'权限类型',width:80,formatter:function (value) {
                    if (value == 1){
                        return "模块";
                    } else if(value == 2){
                        return "菜单";
                    }else if(value == 3){
                        return "功能";
                    }
                    return "";
                }},
            {field:'memo',title:'备注',width:300},
            {field:'id',title:'操作',width:100,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = relationRole(' + value + ')>关联</a>' ;
                }
            }
        ]],
        pagination:true,
        pageSize : 30,
        rownumbers:true
    });
}

function relationRole(id){
    parameter = {
        roleId:$('#roleIdHid1').val(),
        perType:$('#sourceTypeHid').val(),
        id:id
    };

    $.post("/role/saveRoleRelationPermission?" + access_token,parameter,function(data){
        if(200 == data.status){
            $('#not_relation_permissionIds_table').datagrid('reload');
            $('#relation_permissionIds_table').datagrid('reload');
            showMsg('关联成功！');
        }  else{
            showMsg(data.msg);
        }
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}


////////////////////////////////////////////////////// 管理员默认权限管理//////////////////////////////////////////////////////////////
function openDefaultRoleSaveEdit(id){
    cleanDialog("projectUserManagerDialog");
    $("#roleIdHid2").val(id);
    relationProjectManagerRoleTableInit();
    notRelationProjectManagerRoleTableInit();
    $('#projectUserManagerDialog').dialog('open');
}

function assRelationProjectManagerRoleParameter(){
    parameter = {
        projectId:$('#projectSel').val(),
        userName:$('#userNameTxt').val(),
        loginName:$('#loginNameTxt').val(),
        phone:$('#phoneTxt').val(),
        id:$("#roleIdHid2").val()
    };
}

function findRoleRelationProjectUserList(){
    assRelationProjectManagerRoleParameter();
    $('#projectUserManager_table').datagrid('options').queryParams = parameter;
    $('#projectUserManager_table').datagrid('options').pageNumber = 1;
    $('#projectUserManager_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#projectUserManager_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function relationProjectManagerRoleTableInit(){
    assRelationProjectManagerRoleParameter();
    $('#projectUserManager_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#projectManagerToolRole",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/role/findUserRelationRoleList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'id',title:'<input type=\"checkbox\" id=\"userRadioList\"  onchange="selectAll(this,\'userRadioName\')"/>',
                width:50,formatter:function (value) {
                    return "<input type=\"checkbox\"  name=\"userRadioName\" value=\"" + value + "\" >";
                }},
            {field:'projectName',title:'项目名称',width:100},
            {field:'sourceType',title:'平台类型',width:100,formatter:function (value) {
                    if (value == 1){
                        return "后台管理";
                    } else if(value == 2){
                        return "项目";
                    }
                    return "";
            }},
            {field:'coName',title:'公司',width:200},
            {field:'orgName',title:'部门',width:100},
            {field:'userName',title:'成员名称',width:100},
            {field:'orgUserRoleId',title:'操作',width:100,formatter:function(value,data){
                    return '<a href="javascript:void(0)" onclick = delRelationProjectManagerRole(' + data.id + ')>删除</a>';
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
function delRelationProjectManagerRole(id){
    var ids = getCheckedRadioValue("userRadioName");
    if(id == null && ids == null){
        alert("请选择要删除的用户！");
        return;
    }
    if (id == null){
        parameter = {
            idsStr:ids
        };
    } else{
        parameter = {
            id:id
        };
    }

    $.messager.confirm(titleInfo, '您确定删除用户吗？', function(r){
        if (r){
            $.post("/role/delUserRelationRole?" + access_token,parameter,function(data){
                if(200 == data.status){
                    $('#not_projectUserManager_table').datagrid('reload');
                    $('#projectUserManager_table').datagrid('reload');
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
function assNotRelationProjectManagerRoleParameter(){
    parameter = {
        projectId:$('#projectSel1').val(),
        userName:$('#userNameTxt1').val(),
        loginName:$('#loginNameTxt1').val(),
        phone:$('#phoneTxt1').val(),
        id:$("#roleIdHid2").val()
    };
}

function findRoleNotRelationProjectUserList(){
    assNotRelationProjectManagerRoleParameter();
    $('#not_projectUserManager_table').datagrid('options').queryParams = parameter;
    $('#not_projectUserManager_table').datagrid('options').pageNumber = 1;
    $('#not_projectUserManager_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#not_projectUserManager_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function notRelationProjectManagerRoleTableInit(){
    console.log(1)
    assNotRelationProjectManagerRoleParameter();
    $('#not_projectUserManager_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#notProjectManagerToolRole",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/role/findUserNotRelationRoleList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'orgUserId',
        columns:[[
            {field:'orgUserId',title:'<input type=\"checkbox\" id=\"userRadioList1\"  onchange="selectAll(this,\'userRadioName1\')"/>',
                width:50,formatter:function (value) {
                    return "<input type=\"checkbox\"  name=\"userRadioName1\" value=\"" + value + "\" >";
                }},
            {field:'projectName',title:'项目名称',width:100},
            {field:'sourceType',title:'平台类型',width:100,formatter:function (value) {
                    if (value == 1){
                        return "后台管理";
                    } else if(value == 2){
                        return "项目";
                    }
                    return "";
                }},
            {field:'coName',title:'公司',width:200},
            {field:'orgName',title:'部门',width:100},
            {field:'userName',title:'成员名称',width:100},
            {field:'id',title:'操作',width:100,formatter:function(value,data){
                    return '<a href="javascript:void(0)" onclick = relationProjectManagerRole(' + data.orgUserId + ')>关联</a>' ;
                }
            }
        ]],
        pagination:true,
        pageSize : 30,
        rownumbers:true
    });
}

function relationProjectManagerRole(id){
    var ids = getCheckedRadioValue("userRadioName1");
    if(id == null && ids == null){
        alert("请选择要关联的用户！")
        return;
    }
    var roleId = $("#roleIdHid2").val();
    if (id == null){
        var idArray = ids.split(",");
        var parametersArray = [];
        for(var i=0; i<idArray.length; i++){
            var orgUserId = idArray[i];
            $('#not_projectUserManager_table').datagrid('selectRecord',orgUserId);
            var row = $('#not_projectUserManager_table').datagrid('getSelected');
            parameter = {
                orgUserId:row.orgUserId,
                id:roleId,
                projectId:row.projectId,
                userId:row.userId,
                sourceType:row.sourceType
            };
            parametersArray.push(parameter);
        }
        parameter = parametersArray;
    } else{
        $('#not_projectUserManager_table').datagrid('selectRecord',id);
        var row = $('#not_projectUserManager_table').datagrid('getSelected');
        parameter = [{
            orgUserId:row.orgUserId,
            id:roleId,
            projectId:row.projectId,
            userId:row.userId,
            sourceType:row.sourceType
        }];
    }
    parameter = $.param({"json":JSON.stringify(parameter)},true);

    $.post("/role/saveUserRelationRole?" + access_token,parameter,function(data){
        if(200 == data.status){
            $('#not_projectUserManager_table').datagrid('reload');
            $('#projectUserManager_table').datagrid('reload');
            showMsg('关联成功！');
        }  else{
            showMsg(data.msg);
        }
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

