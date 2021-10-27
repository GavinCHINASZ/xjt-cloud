$(function(){
    $('#reportSystem_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveReportSystem();
            }
        },{
            text:'取消',
            handler:function(){
                $("#reportSystem_dialog").dialog('close');// 关闭 新增/修改弹框页面
                cleanDialog("reportSystem_dialog");
            }
        }]
    });
    initReportSystemDataGrid();
});

/**
 * 初使 报表名称列表
 * user:huangGuiChuan
 * date:2019/12/13
 **/
function initReportNameList(){
    $.post("/reportSystem/findCacheDictItemListByCode?" + access_token, {"dictCode":"DEVICE_SYS_TYPE"}, function(data){
        $.each(data, function(index, reportSystem){
            $("#reportNameSel").append('<option value=' + reportSystem.itemValue + '>' + reportSystem.reportName + '</option>');
        });
    }, "json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

function assParameter(){
    parameter = {
        itemName:$("#itemNameTxt").val()
    };
}

function findReportSystemList(){
    initReportSystemDataGrid();
}

/**
 *
 * 查询数据表格
 */
function initReportSystemDataGrid(){
    assParameter();
    $('#reportSystem_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/reportSystem/findReportSystemList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'reportName', title:'报表名称', width:30},
            {field:'reportNo', title:'报表编号', width:20},
            {field:'type', title:'类型', width:20, formatter:function(value){
                    if (value == 1) {
                        return "检测";
                    }else if (value == 2) {
                        return "维修";
                    }else{
                        return "保养";
                    }
                }
            },
            {field:'sortNo', title:'排序', width:20},
            {field:'itemName', title:'名称', width:160},
            {field:'id', title:'操作', width:20, formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = reportSystemEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delReportSystem(' + value + ')>删除</a> ';
                }
            }
        ]],
        pagination:true,
        pageSize:30,
        rownumbers:true
    });
}

/**
 * 打开 添加窗口
 */
function openReportSystemAdd() {
    cleanDialog("add_reportSystem_dialog");
    $('#add_reportSystem_dialog').dialog('open');
}

/**
 * 保存 报表系统项
 */
function saveReportSystem() {
    // 判断页面填写是否为空
    if(!$("#reportSystemForm").form("validate")){
        return;
    }
    parameter = $('#reportSystemForm').serialize();
    // 获取主键值，根据主键值判断添加 或 修改
    var reportSystemId = $('#reportSystemId').val();
    if(null == reportSystemId || reportSystemId == ""){
        $.post("/reportSystem/saveReportSystem?" + access_token, parameter, function(data){
            if(200 == data.status){
                $('#reportSystem_table').datagrid('reload');
                $('#reportSystem_dialog').dialog('close');
                showMsg('新增成功！');
            }else{
                showMsg(data.msg);
            }
        }, "json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/reportSystem/modifyReportSystem?" + access_token, parameter, function(data){
            if(200 == data.status){
                $('#reportSystem_table').datagrid('reload');
                $('#reportSystem_dialog').dialog('close');
                showMsg('修改成功！');
            }else{
                showMsg(data.msg);
            }
        }, "json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }
}

/**
 * 修改 报表系统项
 */
function reportSystemEdit(id){
    cleanDialog("reportSystem_dialog");
    //设置弹出框信息
    $('#reportSystem_table').datagrid('selectRecord', id);
    generateDialog($('#reportSystem_table').datagrid('getSelected'),"reportSystem_dialog");
    $('#reportSystem_dialog').dialog('open');
}

/**
 * 删除报表系统项
 */
function delReportSystem(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/reportSystem/delReportSystem?" + access_token, {"id":id, "deleted":1}, function(data){
                if(200 == data.status){
                    $('#reportSystem_table').datagrid('reload');
                    showMsg('删除成功！');
                }  else{
                    showMsg(data.msg);
                }
            }, "json").error(function (error, status, info){
                showMsg(error.responseText);
            });
        }
    });
}

/**
 * 词典项管理
 */
function deviceTypePage(id){
    initDeviceTypeDataGrid();
}

/**
 * 查询数据表格
 */
function initDeviceTypeDataGrid(){
    assParameterProduct();
    $('#reportSystem_table').datagrid({
        nowrap: true,
        autoRowHeight: true,
        striped: true,
        toolbar: "#toolbarItem",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/reportSystem/findReportSystemList?' + access_token,
        queryParams:parameterItem,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'reportName', title:'报表名称', width:30},
            {field:'reportNo', title:'报表编号', width:20},
            {field:'type', title:'类型', width:20, formatter:function(value){
                    if (value == 1) {
                        return "检测";
                    }else if (value == 2) {
                        return "维修";
                    }else{
                        return "保养";
                    }
                }
            },
            {field:'sortNo', title:'排序', width:20},
            {field:'itemName', title:'名称', width:160},
            {field:'id',title:'操作',width:100,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = deviceTypeEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delDeviceType(' + value + ')>删除</a>';
                }
            }
        ]],
        pagination:true,
        pageSize:15,
        rownumbers:true
    });
}
