$(function(){
    $('#checkQrNoModifyDialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                modifyCheckQrNo();
            }
        },{
            text:'取消',
            handler:function(){
                $("#checkQrNoModifyDialog").dialog('close');
                cleanDialog("checkQrNoModifyDialog");
            }
        }]
    });
    initCheckQrNoDataGrid();
    initProjectList();
});

function assParameter(){
    parameter = {
        projectId:$("#projectSel").val(),
        qrNo:$("#checkQrNoTxt").val()
    };
}

/**
 * 查询数据表格
 */
function initCheckQrNoDataGrid(){
    assParameter();
    $('#checkQrNo_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/device/qrNo/findCheckPointQrNoList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'projectId',
        columns:[[
            {field:'projectName',title:'项目名称',width:100},
            {field:'buildingName',title:'建筑物',width:100},
            {field:'buildingFloorName',title:'楼层',width:100},
            {field:'pointLocation',title:'位置',width:100},
            {field:'pointName',title:'巡检点名称',width:100},
            {field:'qrNo',title:'巡检ID',width:100},
            {field:'projectId',title:'操作',width:166,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = qrNoEdit(' + value + ')>编辑</a>';
                }
            }
        ]],
        pagination:true,
        pageSize:30,
        rownumbers:true
    });
}

/**
 * 修改二维码信息
 */
function qrNoEdit(id){
    $('#deviceQrNoModifySearch_form')[0].reset();
    cleanDialog("checkQrNoModifyDialog");
    //设置弹出框信息
    $('#checkQrNo_table').datagrid('selectRecord',id);
    var row = $('#checkQrNo_table').datagrid('getSelected');
    $("#projectSel12").val(id);
    $("#qrNoTxt").val(row.qrNo);
    $('#checkQrNoModifyDialog').dialog('open');
}

function findCheckQrNoList(){
    assParameter();
    $('#checkQrNo_table').datagrid('options').queryParams = parameter;
    $('#checkQrNo_table').datagrid('options').pageNumber = 1;
    $('#checkQrNo_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#checkQrNo_table').datagrid('reload');
}

function initProjectList(){
    $("#projectSel").append('<option value="">ALL</option>');
    $.post("/project/findAllProject?" + access_token,null,function(data){
        $.each(data,function(index,project){
            $("#projectSel").append('<option value=' + project.id + '>' + project.projectName + '</option>');
            $("#projectSel1").append('<option value=' + project.id + '>' + project.projectName + '</option>');
            $("#projectSel12").append('<option value=' + project.id + '>' + project.projectName + '</option>');
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

/**
 * 打开添加窗口
 */
function openCheckQrNoPage() {
    $('#checkQrNoSaveDialog').dialog('open');
}

function saveCheckQrNo() {
    var projectId = $('#projectSel1').val();

    if(projectId == null || projectId == ''){
        alert("请选择要添加的项目!");
        return;
    }

    var checkQrNo = $('#checkQrNoTxt1').val();
    if(checkQrNo == null || checkQrNo == ''){
        alert("请输入巡检点ID!");
        return;
    }

    parameter = {
        projectId:$("#projectSel1").val(),
        qrNo:$("#checkQrNoTxt1").val()
    };
    $.post("/device/qrNo/saveCheckPoint?" + access_token,parameter,function(data){
        if(200 == data.status){
            $('#checkQrNo_table').datagrid('reload');
            showMsg('保存成功！');
        }  else{
            showMsg(data.msg);
        }
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

function modifyCheckQrNo() {

    parameter = {
        projectId:$("#projectSel12").val(),
        qrNo:$("#qrNoTxt").val()
    };
    $.messager.confirm(titleInfo, '您确定要修改该二维码的所属项目吗？', function(r){
        if (r){
            $.post("/device/qrNo/modifyCheckPoint?" + access_token,parameter,function(data){
                if(200 == data.status){
                    $('#checkQrNo_table').datagrid('reload');
                    showMsg('修改成功！');
                    $("#checkQrNoModifyDialog").dialog('close');
                }  else{
                    showMsg(data.msg);
                }
            },"json").error(function (error, status, info){
                showMsg(error.responseText);
            });
        }
    });
}


/**
 * 上传
 * user:dwt
 * param: file_id
 * return:
 * date:2020/09/23
 **/
function uploadDeviceQrNoExcelFile(file_id){
    var formData = new FormData();
    formData.append("file",$("#" + file_id + "_file_upload")[0].files[0]);
    $.ajax({
        url:'/device/qrNo/uploadDeviceQrNorExcelFile?' + access_token, /*接口域名地址*/
        type:'post',
        data: formData,
        dataType: "json",
        contentType: false,
        processData: false,
        success:function(data){
            if(200 == data.status){
                $('#out_storage_table').datagrid('reload');
                showMsg(data.msg);
            }  else{
                showMsg(data.msg);
            }
        }
    })
}


function downDeviceQrNoModelExcel(){
    window.location.href = "device/qrNo/downDeviceQrNoModelExcel?" + access_token;
}