$(function () {
    $('#project_dialog').dialog({
        buttons: [{
            text: '确 定',
            handler: function () {
                modifyProject();
            }
        }, {
            text: '取消',
            handler: function () {
                $("#project_dialog").dialog('close');
                cleanDialog("project_dialog");
            }
        }]
    });

    $('#projectReview_dialog').dialog({
        buttons: [{
            text: '确 定',
            handler: function () {
                saveProjectReviewRecord();
            }
        }, {
            text: '取消',
            handler: function () {
                $("#projectReview_dialog").dialog('close');
                cleanDialog("projectReview_dialog");
            }
        }]
    });

    $('#permissionPath_search').dialog({
        buttons: [{
            text: '确 定',
            handler: function () {
                saveProjectMsgLevel();
            }
        }, {
            text: '取消',
            handler: function () {
                $("#permissionPath_search").dialog('close');
                cleanDialog("permissionPath_search");
            }
        }]
    });

    initProjectDataGrid();
    projectParentPermissionSelInit();
});

function assParameter() {
    var projectName = $("#searchProjectName").val();
    var projectStatus = $("#searchProjectStatus").val();
    parameter = {
        projectName: projectName,
        projectStatus: projectStatus
    };
}

function findProjectList() {
    initProjectDataGrid();
}

/**
 * 查询数据表格
 */
function initProjectDataGrid() {
    assParameter();
    $('#project_table').datagrid({
        nowrap: true,
        autoRowHeight: true,
        striped: true,
        toolbar: "#toolbar",
        fit: false,
        fitColumns: true,
        collapsible: true,
        url: '/project/findAllProjectList?' + access_token,
        queryParams: parameter,
        remoteSort: false,
        singleSelect: true,
        idField: 'id',
        columns: [[
            {field: 'projectName', title: '项目名称', width: 200},
            {field: 'companyName', title: '公司名称', width: 200},
            {field: 'createTime', title: '创建时间', width: 100},
            {
                field: 'projectStatus', title: '项目状态', width: 100, formatter: function (val) {
                    return val == 0 ? '<span class="STATE1">待审核</span>' : val == 1 ? '<span class="STATE1">已驳回</span>' : '<span class="STATE1">已通过</span>';
                }
            },
            {
                field: 'id', title: '操作', width: 200, formatter: function (value, data) {
                    var review = '';
                    if (data.projectStatus == 0) {
                        review = '<a href="javascript:void(0)" onclick = projectReviewEdit(' + value + ')>审核</a>  | ';
                    }
                    return '<a href="javascript:void(0)" onclick = delProject(' + value + ')>删除</a>  | ' +
                        '<a href="javascript:void(0)" onclick = modifyProjectPage(' + value + ')>编辑</a> | ' +
                        review +
                        '<a href="javascript:void(0)" onclick = projectReviewRecordPage(' + value + ')>审核记录</a> | ' +
                        '<a href="javascript:void(0)" onclick = openProjectPermission(' + value + ')>权限管理</a> | ' +
                        '<a href="javascript:void(0)" onclick = dictItemTreeMsgLevelPage(' + value + ')>报警等级</a>';

                }
            }
        ]],
        pagination: true,
        pageSize: 30,
        rownumbers: true
    });
}

function delProject(id) {
    $.messager.confirm(titleInfo, '您确定删除吗？', function (r) {
        if (r) {
            $.post("/project/modifyProject?" + access_token, {"id": id, "deleted": "true"}, function (data) {
                if (200 == data.status) {
                    $('#project_table').datagrid('reload');
                    showMsg('删除成功！');
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
 * 保存数据词典信息
 */
function modifyProject() {
    parameter = $('#projectReviewRecordForm').serialize();
    //
    $.post("/project/modifyProject?" + access_token, parameter, function (data) {
        if (200 == data.status) {
            $('#project_table').datagrid('reload');
            $('#project_dialog').dialog('close');
            showMsg('保存成功！');
        } else {
            showMsg(data.msg);
        }
    }, "json").error(function (error, status, info) {
        showMsg(error.responseText);
    });
}

/**
 * 打开项目审核窗口
 */
function modifyProjectPage(id) {
    cleanDialog("project_dialog");
    //设置弹出框信息
    $('#project_table').datagrid('selectRecord', id);
    var row = $('#project_table').datagrid('getSelected');
    generateDialog(row, "project_dialog");
    $('#project_dialog').dialog('open');

}

/**
 * 打开项目审核窗口
 */
function projectReviewEdit(id) {
    cleanDialog("projectReview_dialog");
    //设置弹出框信息
    $('#projectIdHid1').val(id);
    $('#projectReview_dialog').dialog('open');

}

/**
 * 修改信息
 */
function projectReviewRecordEdit(id) {
    cleanDialog("project_dialog");
    //设置弹出框信息
    $('#projectReviewRecord_dialog').dialog('open');
}

function saveReviewEdit(status) {
    $("#projectStatus").val(status);
    if (status == 1) {
        $("#status").html("驳回");
    } else {
        $("#status").html("通过");
    }
    $('#projectReview_dialog').dialog('open');
}

var projectReviewRecord;

function assProjectReviewRecord() {
    projectReviewRecord = {
        projectId: $('#projectId').val()
    };
}

/**
 * 管理
 */
function projectReviewRecordPage(id, projectName, perType) {
    $('#projectId').val(id);
    $('#projectName').val(projectName);
    $("#preType").val(perType);
    $("#projectReviewRecord_search").dialog('open');
    initprojectReviewRecordDataGrid();
}

/**
 * 查询数据表格
 */
function initprojectReviewRecordDataGrid() {
    assProjectReviewRecord();
    $('#projectReviewRecord_table').datagrid({
        nowrap: true,
        autoRowHeight: true,
        striped: true,
        toolbar: "#projectReviewRecordToolbar",
        fit: false,
        fitColumns: true,
        collapsible: true,
        url: '/project/findProjectReviewRecordList?' + access_token,
        queryParams: projectReviewRecord,
        remoteSort: false,
        singleSelect: true,
        idField: 'id',
        columns: [[
            {field: 'versionNumber', title: '版本号', width: 100},
            {field: 'reviewName', title: '审核人', width: 100},
            {
                field: 'projectStatus', title: '审核状态', width: 50, formatter: function (val) {
                    return val == 0 ? '<span class="STATE1">待审核</span>' : val == 1 ? '<span class="STATE1">已驳回</span>' : '<span class="STATE1">已通过</span>';
                }
            },
            {field: 'reviewTime', title: '审核时间', width: 200},
            {field: 'reviewOpinion', title: '审核意见', width: 500}

        ]],
        pagination: true,
        rownumbers: true
    });
}


/**
 * 保存权限
 */
function saveProjectReviewRecord() {
    //获取主键值，根据主键值判断添加或修改
    var parameter = {
        projectId: $('#projectIdHid1').val(),
        projectStatus: $("#projectStatus").val(),
        reviewOpinion: $("#reviewOpinion").val()

    };
    var json = encodeURI(JSON.stringify(parameter))
    $.post("/project/projectReview?" + access_token + "&json=" + json, null, function (data) {
        if (200 == data.status) {
            $('#project_item_table').datagrid('reload');
            $('#projectReview_dialog').dialog('close');

            showMsg('保存成功！');
        } else {
            showMsg(data.msg);
        }
    }, "json").error(function (error, status, info) {
        showMsg(error.responseText);
    });

}

/**
 * 权限管理
 * @param id
 */
function openProjectPermission(id) {
    $('#project_table').datagrid('selectRecord', id);
    var row = $('#project_table').datagrid('getSelected');
    $('#projectIdHid').val(id);
    $("#projectPermissionManagerDialog").dialog('open');
    initProjectPermissionDataGrid();
}

/**
 * 报警等级
 */
function dictItemTreeMsgLevelPage(id) {
    $('#project_table').datagrid('selectRecord', id);
    var row = $('#project_table').datagrid('getSelected');
    $('#projectIdHid2').val(id);

    $("#permissionPath_search").dialog('open');
    initPermissionPathDataGrid(id);
}

/**
 * 查询权限树结构
 */
function initPermissionPathDataGrid(id) {
    $('#tree').tree([]);
    $('#tree').tree({
        url: "/dict/findDictItemTreeMsgLevel?" + access_token + "&projectId=" + id,
        loadFilter: function (data) {
            if (data.d) {
                return data.d;
            } else {
                return data;
            }
        },
        onLoadSuccess: function () {
            $(this).tree("collapseAll");
        }
    });
}

function projectParentPermissionSelInit() {
    $("#parentPermissionSel").append('<option value="">ALL</option>');
    $("#parentPermissionSel1").append('<option value="0">一级</option>');
    $.post("/permission/findPermissionList?" + access_token, null, function (data) {
        $.each(data, function (index, permission) {
            $("#parentPermissionSel").append('<option value=' + permission.id + '>' + permission.permissionName + '</option>');
            $("#parentPermissionSel1").append('<option value=' + permission.id + '>' + permission.permissionName + '</option>');
        });
    }, "json").error(function (error, status, info) {
        showMsg(error.responseText);
    });
}

function findProjectPermissionList() {
    assProjectPermission();
    $('#projectPermission_table').datagrid('options').queryParams = parameter;
    $('#projectPermission_table').datagrid('options').pageNumber = 1;
    $('#projectPermission_table').datagrid('getPager').pagination({
        pageNumber: 1
    });
    $('#projectPermission_table').datagrid('reload');
}

function assProjectPermission() {
    parameter = {
        projectId: $('#projectIdHid').val(),
        permissionName: $('#permissionNameTxt').val(),
        perType: $('#perTypeSel').val(),
        permissionType: $('#permissionSel').val(),
        parentId: $('#parentPermissionSel').val(),
        groupByStr: "p.id",
        orderCols: "sort"
    };
}

/**
 * 查询数据表格
 */
function initProjectPermissionDataGrid() {
    assProjectPermission();
    $('#projectPermission_table').datagrid({
        nowrap: true,
        autoRowHeight: true,
        striped: true,
        toolbar: "#projectPermissionToolbar",
        fit: true,
        fitColumns: true,
        collapsible: true,
        url: '/role/findRoleRelationPermissionList?' + access_token,
        queryParams: parameter,
        remoteSort: false,
        singleSelect: true,
        idField: 'id',
        columns: [[
            {field: 'permissionName', title: '权限名称', width: 200},
            {field: 'sign', title: '别名', width: 200},
            {field: 'sort', title: '排序', width: 100},
            {
                field: 'perType', title: '类型', width: 100, formatter: function (val) {
                    return val == 1 ? '<span class="STATE1">后台管理权限</span>' : '<span class="STATE0">项目权限</span>';
                }
            },
            {
                field: 'permissionType', title: '权限类型', width: 100, formatter: function (val) {
                    return val == 1 ? '<span class="STATE1">模块</span>' : val == 2 ? '<span class="STATE1">菜单</span>' : '<span class="STATE1">功能</span>';
                }
            },
            {
                field: 'status', title: '权限状态', width: 100, formatter: function (value) {
                    if (value == 1) {
                        return '上线';
                    } else if (value == 2) {
                        return '<font color="red">下线</font>';
                    }
                    return '';
                }
            },
            {
                field: 'id', title: '操作', width: 100, formatter: function (value, data) {
                    var line = "";
                    if (data.status == 1) {
                        line = '<a href="javascript:void(0)" onclick = modifyProjectPermissionStatus(' + value + ',2)>下线</a>';
                    } else if (data.status == 2) {
                        line = '<a href="javascript:void(0)" onclick = modifyProjectPermissionStatus(' + value + ',1)>上线</a>';
                    }
                    return line;

                }
            }
        ]],
        pagination: true,
        pageSize: 30,
        rownumbers: true
    });
}


function modifyProjectPermissionStatus(id, status) {
    var msg;
    if (status == 1) {
        msg = "上线";
    } else {
        msg = "下线";
    }
    $.messager.confirm(titleInfo, '您确定' + msg + '吗？', function (r) {
        if (r) {
            parameter = {
                id: id,
                projectId: $('#projectIdHid').val(),
                status: status
            };
            $.post("/role/modifyRolePermission?" + access_token, parameter, function (data) {
                if (200 == data.status) {
                    $('#projectPermission_table').datagrid('reload');
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
 * 保存 报警等级
 */
function saveProjectMsgLevel() {
    // 判断页面填写是否为空
    var obj = $('#tree').tree('getChecked', "checked");
    var paraItem = obj.map(function (item) {
        return {
            dictItemId: item.id,
            projectId: item.projectId,
            modelName: item.modelName,
            eventTypeNameLevel: item.eventTypeNameLevel,
            itemName: item.itemName
        }
    })

    var json = {json: JSON.stringify(paraItem)};
    $.post("/project/saveProjectMsgLevel?" + access_token, json, function (data) {
        if (200 == data.status) {
            $('#permission_item_table').datagrid('reload');
            $('#permission_item_dialog').dialog('close');
            showMsg('保存成功！');
        } else {
            showMsg(data.msg);
        }
    }, "json");
}
