$(function(){
    infoManageDataGrid();
});


function assParameter(){
    parameter = {
        userName:$("#userNameTxt").val(),
        phone:$("#phoneTxt").val()
    };
}
function findRecruitInfoList(){
    assParameter();
    $('#recruitInfo_table').datagrid('options').queryParams = parameter;
    $('#recruitInfo_table').datagrid('options').pageNumber = 1;
    $('#recruitInfo_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#recruitInfo_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function infoManageDataGrid(){
    assParameter();
    $('#recruitInfo_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/recruit/info/findRecruitInfoList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'userName',title:'用户名称',width:150},
            {field:'phone',title:'电话',width:150},
            {field:'status',title:'信息状态',width:150,formatter:function(value){
                    if (value == 1){
                        return "未处理";
                    } else{
                        return "已处理";
                    }
                }},
            {field:'id',title:'操作',width:100,formatter:function(value,row){
                var s = "";
                    if (row.status == 1){
                        s  = '<a href="javascript:void(0)" onclick = modifyRecruitInfo(' + value + ',2)>处理</a>  | ';
                    }
                    return s +                         '<a href="javascript:void(0)" onclick = delRecruitInfo(' + value + ',99)>删除</a>';
                }
            }
        ]],
        pagination:true,
        pageSize:30,
        rownumbers:true
    });
}


/**
 * 删除词典信息
 */
function modifyRecruitInfo(id,status){
    $.post("/recruit/info/modifyRecruitInfo?" + access_token,{ "id":id,"status":status },function(data){
        if(200 == data.status){
            $('#recruitInfo_table').datagrid('reload');
            showMsg('删除成功！');
        }  else{
            showMsg(data.msg);
        }
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

function delRecruitInfo(id,status) {
    if (status == 99){
        $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
            if (r){
                modifyRecruitInfo(id,status);
            }
        });
    }
}

