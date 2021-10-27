$(function(){
    $('#feedback_dialog').dialog({
        buttons:[{
            text:'取消',
            handler:function(){
                $("#feedback_dialog").dialog('close');
                cleanDialog("feedback_dialog");
            }
        }]
    });

    $('#feedback_memo_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                editFeedback();
            }
        },{
            text:'取消',
            handler:function(){
                $("#feedback_memo_dialog").dialog('close');
                cleanDialog("feedback_memo_dialog");
            }
        }]
    });

    feedback_memo_dialog
    initFeedbackDataGrid();
});

function assParameter(){
    parameter = {
        projectName:$("projectNameTxt").val(),
        userPhone:$("userPhoneTxt").val(),
        status:$("statusSel").val(),
        createUserName:$("#createUserNameTxt").val()
    };
}

function findFeedbackList(){
    assParameter();
    $('#feedback_table').datagrid('options').queryParams = parameter;
    $('#feedback_table').datagrid('options').pageNumber = 1;
    $('#feedback_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#feedback_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initFeedbackDataGrid(){
    assParameter();
    $('#feedback_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#feedbackToolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/feedback/findFeedbackList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'createUserName',title:'用户名',width:50},
            {field:'userPhone',title:'用户电话',width:50},
            {field:'createTime',title:'创建时间',width:60},
            {field:'content',title:'内容',width:200,formatter:function(value,row){
                    return '<a href="javascript:void(0)" onclick = feedbackInfo(' + row.id + ')><span style="color: green">' + (value == undefined ? "" : value) + '</span></a> ';
                }},
            {field:'projectName',title:'项目名称',width:50},
            {field:'statusDesc',title:'状态',width:30},
            {field:'handleUserName',title:'处理人',width:50},
            {field:'handleTime',title:'处理时间',width:60},
            {field:'memo',title:'备注',width:100},
            {field:'status',title:'操作',width:150,formatter:function(value,row){
                var res = "";
                if (value == 1){
                    res = '<a href="javascript:void(0)" onclick = openEditFeedback(' + row.id + ',2)>处理中</a> | '
                }
                if(value <= 2){
                    res += '<a href="javascript:void(0)" onclick = openEditFeedback(' + row.id + ',3)>已修复</a> | ' +
                        '<a href="javascript:void(0)" onclick = openEditFeedback(' + row.id + ',4)>无法修复</a> | ' +
                        '<a href="javascript:void(0)" onclick = openEditFeedback(' + row.id + ',5)>不存在</a> | ';
                }

                res += '<a href="javascript:void(0)" onclick = openEditFeedback(' + row.id + ',99)>删除</a> ';
                return res;
            }}
        ]],
        pagination:true,
        rownumbers:true
    });
}

/**
 * 保存信息
 */
function openEditFeedback(id, status) {
    cleanDialog("feedback_memo_dialog");
    $("#feedbackMemoId").val(id);
    $("#feedbackStatusId").val(status);
    $('#feedback_memo_dialog').dialog('open');
}

/**
 * 保存信息
 */
function editFeedback() {
    if ($("#feedbackMemoTxt").val() == null || $("#feedbackMemoTxt").val() == ""){
        alert("请输入备注");
        return;
    }
    var param = {
        id:$("#feedbackMemoId").val(),
        status:$("#feedbackStatusId").val(),
        memo:$("#feedbackMemoTxt").val()
    };
    $.post("/feedback/modifyFeedback?" + access_token,param,function(data){
        if(200 == data.status){
            $('#feedback_table').datagrid('reload');
            $('#feedback_memo_dialog').dialog('close');
            showMsg('保存成功！');
        }else{
            showMsg(data.msg);
        }
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

/**
 * 修改信息
 */
function feedbackInfo(id){
    cleanDialog("feedback_dialog");
    //设置弹出框信息
    $('#feedback_table').datagrid('selectRecord',id);
    generateDialog($('#feedback_table').datagrid('getSelected'),"feedback_dialog");
    $('#feedback_dialog').dialog('open');
}