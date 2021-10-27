$(function(){
    $('#checkItem_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveCheckItem();
            }
        },{
            text:'取消',
            handler:function(){
                $("#checkItem_dialog").dialog('close');
                cleanDialog("checkItem_dialog");
            }
        }]
    });
    initDeviceCheckItemDataGrid();
    initProjectList();
});

/**
 * 初使列表
 * user:wangzhiwen
 * date:2017/8/29 17:26
 **/
function initProjectList(){
    $("#projectSel1").append('<option value="">ALL</option>');
    $("#projectSel2").append('<option value="">ALL</option>');
    $.post("/project/findAllProject?" + access_token,{"checkItemVsType":3},function(data){
        $.each(data,function(index,project){
            $("#projectSel").append('<option value=' + project.id + '>' + project.projectName + '</option>');
            $("#projectSel1").append('<option value=' + project.id + '>' + project.projectName + '</option>');
            $("#projectSel2").append('<option value=' + project.id + '>' + project.projectName + '</option>');
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}


function assParameter(){
    parameter = {
        deviceSysName:$("#deviceSysNameTxt").val(),
        deviceTypeName:$("#deviceTypeNameTxt").val(),
        projectId:$("#projectSel2").val(),
        checkItemVsType:$("#vsTypeSel").val()
    };
}

function findCheckItemList(){
    assParameter();
    $('#deviceCheckItem_table').datagrid('options').queryParams = parameter;
    $('#deviceCheckItem_table').datagrid('options').pageNumber = 1;
    $('#deviceCheckItem_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#deviceCheckItem_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initDeviceCheckItemDataGrid(){
    assParameter();
    $('#deviceCheckItem_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#deviceCheckItemToolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/check/item/findCheckItemList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'checkItemVsType',title:'版本类型',width:100,formatter:function(value){
                if (value != undefined){
                    if(value == 1){
                        return "默认版";
                    }else if(value == 2){
                        return "精简版";
                    }else if(value == 3){
                        return "自定义版";
                    }
                }
                return "";
            }},
            {field:'projectName',title:'项目名称',width:100},
            {field:'deviceSysName',title:'系统名称',width:100},
            {field:'deviceTypeName',title:'设备名称',width:100},
            {field:'checkName',title:'巡检项名称',width:100},
            {field:'checkAction',title:'类型',width:100,formatter:function(value){
                    if (value != undefined){
                        if(value == 1){
                            return "巡检";
                        }else if(value == 2){
                            return "检测";
                        }else if(value == 3){
                            return "保养";
                        }
                    }
                    return "";
                }},
            {field:'checkType',title:'巡检方式',width:100,formatter:function(value){
                    if (value != undefined){
                        if (value == 0){
                            return "默认";
                        }else if(value == 1){
                            return "数值";
                        }else if(value == 2){
                            return "计数";
                        }else if(value == 3){
                            return "描述";
                        }
                    }
                    return "";
                }},
            {field:'periodType',title:'建议巡检周期',width:100,formatter:function(value){
                    if (value != undefined){
                        if (value == 9){
                            return "每两年";
                        }else if(value == 5){
                            return "每年";
                        }else if(value == 4){
                            return "每半年";
                        }else if(value == 3){
                            return "每季度";
                        }else if(value == 2){
                            return "每两月";
                        }else if(value == 1){
                            return "每月";
                        }else if(value == 10){
                            return "每半月";
                        }else if(value == 7){
                            return "每周";
                        }
                    }
                    return "";
                }},
            {field:'checkSpecification',title:'检查标准',width:100},
            {field:'type',title:'巡检值类型',width:100,formatter:function(value){
                    if (value != undefined){
                        if(value == 1){
                            return "一般项";
                        }else if(value == 2){
                            return "大小值项";
                        }
                    }
                    return "";
                }},
            {field:'unit',title:'单位',width:100},
            {field:'maxValueFloat',title:'最大值',width:100},
            {field:'minValueFloat',title:'最小值',width:100},
            {field:'itemIndex',title:'巡检项id',width:100},
            {field:'id',title:'操作',width:100,formatter:function(value,data){
                    if (data.checkItemVsType == 3) {
                        return '<a href="javascript:void(0)" onclick = delProjectCheckItem(' + value + ')>删除</a>';
                    }else {
                        return ;
                    }
                }
            }
        ]],
        pagination:true,
        rownumbers:true
    });
}

/**
 * 删除词典项信息
 */
function delProjectCheckItem(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/check/item/delCheckItem?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#deviceCheckItem_table').datagrid('reload');
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

/////////////////////////////////////////////
/**
 * 打开添加窗口
 */
function openProjectCheckItemPage() {
    $('#deviceCheckItemRelation_search').dialog('open');
    initCheckItemRelationDataGrid();
}

function assCheckItemRelationParameter(){
    var checkItemVsType = $("#vsTypeSel2").val();
    var projectId = $("#projectSel1").val();
    if (projectId != null && projectId != ''){
        checkItemVsType = 3;
    }
    parameter = {
        notProjectItem:true,
        checkAction:1,
        deviceSysName:$("#deviceSysNameTxt1").val(),
        saveProjectId:$("#projectSel").val(),
        deviceTypeName:$("#deviceTypeNameTxt1").val(),
        projectId:projectId,
        checkItemVsType:checkItemVsType
    };
}

function findRelationCheckItemList(){
    assCheckItemRelationParameter();
    $('#deviceCheckItemRelation_table').datagrid('options').queryParams = parameter;
    $('#deviceCheckItemRelation_table').datagrid('options').pageNumber = 1;
    $('#deviceCheckItemRelation_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#deviceCheckItemRelation_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initCheckItemRelationDataGrid(){
    assCheckItemRelationParameter();
    $('#deviceCheckItemRelation_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#deviceCheckItemRelationToolbarItem",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/check/item/findCheckItemList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: false,
        idField:'id',
        columns:[[
            {field:'id',title:'<input type=\"checkbox\" id=\"checkItemRadioList\"  onchange="selectAll(this,\'checkItemRadioName\')"/>',
                width:50,formatter:function (value) {
                    return "<input type=\"checkbox\"  name=\"checkItemRadioName\" value=\"" + value + "\" >";
                }},
            {field:'deviceSysName',title:'系统名称',width:200},
            {field:'deviceTypeName',title:'设备名称',width:150},
            {field:'checkName',title:'巡检项名称',width:100},
            {field:'checkAction',title:'类型',width:100,formatter:function(value){
                    if (value != undefined){
                        if(value == 1){
                            return "巡检";
                        }else if(value == 2){
                            return "检测";
                        }else if(value == 3){
                            return "保养";
                        }
                    }
                    return "";
                }},
            {field:'checkType',title:'巡检方式',width:100,formatter:function(value){
                    if (value != undefined){
                        if (value == 0){
                            return "默认";
                        }else if(value == 1){
                            return "数值";
                        }else if(value == 2){
                            return "计数";
                        }else if(value == 3){
                            return "描述";
                        }
                    }
                    return "";
                }},
            {field:'periodType',title:'建议巡检周期',width:100,formatter:function(value){
                    if (value != undefined){
                        if (value == 9){
                            return "每两年";
                        }else if(value == 5){
                            return "每年";
                        }else if(value == 4){
                            return "每半年";
                        }else if(value == 3){
                            return "每季度";
                        }else if(value == 2){
                            return "每两月";
                        }else if(value == 1){
                            return "每月";
                        }else if(value == 10){
                            return "每半月";
                        }else if(value == 7){
                            return "每周";
                        }
                    }
                    return "";
                }},
            {field:'checkSpecification',title:'检查标准',width:100},
            {field:'type',title:'巡检值类型',width:100,formatter:function(value){
                    if (value != undefined){
                        if(value == 1){
                            return "一般项";
                        }else if(value == 2){
                            return "大小值项";
                        }
                    }
                    return "";
                }},
            {field:'unit',title:'单位',width:100},
            {field:'maxValueFloat',title:'最大值',width:100},
            {field:'minValueFloat',title:'最小值',width:100},
            {field:'itemIndex',title:'巡检项id',width:100},
            {field:'deviceTypeId',title:'操作',width:100,formatter:function(value,data){
                    return '<a href="javascript:void(0)" onclick = saveProjectCheckItem(' + data.id + ')>添加</a>  ';
                }
            }
        ]],
        pagination:true,
        rownumbers:true
    });
}

function saveProjectCheckItem(id) {
    var projectId = $('#projectSel').val();
    if(projectId == null || projectId == ''){
        alert("请选择要添加的项目!");
        return;
    }

    var checkItemIds;
    if (id == 'ALL'){
        id = null;

    } else if (id == ''){
        checkItemIds = getCheckedRadioValue("checkItemRadioName");

        if (checkItemIds == null || checkItemIds == ''){
            alert("请选择要添加的巡检项！");
            return;
        }
    }

    parameter = {
        saveProjectId:projectId,
        ids:checkItemIds,
        id:id,
        deviceSysName:$("#deviceSysNameTxt1").val(),
        deviceTypeName:$("#deviceTypeNameTxt1").val(),
        projectId:$("#projectSel1").val(),
        checkItemVsType:$("#vsTypeSel2").val()
    };
    $.post("/check/item/saveProjectCheckItem?" + access_token,parameter,function(data){
        if(200 == data.status){
            $('#deviceCheckItemRelation_table').datagrid('reload');
            showMsg('保存成功！');
        }  else{
            showMsg(data.msg);
        }
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}
