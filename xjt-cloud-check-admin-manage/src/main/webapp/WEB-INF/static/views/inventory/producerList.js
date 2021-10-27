$(function(){
    $('#producer_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveProducer();
            }
        },{
            text:'取消',
            handler:function(){
                $("#producer_dialog").dialog('close');
                cleanDialog("producer_dialog");
            }
        }]
    });
    initProducerGrid();
});


function assParameter(){
    parameter = {
    };
}

function findProducerList(){
    assParameter();
    $('#producer_table').datagrid('options').queryParams = parameter;
    $('#producer_table').datagrid('options').pageNumber = 1;
    $('#producer_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#producer_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initProducerGrid(){
    assParameter();
    $('#producer_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/inventory/producer/findProducerList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'producerName',title:'厂商名称',width:100},
            {field:'contacts',title:'联系人',width:100},
            {field:'phone',title:'电话',width:100},
            {field:'codeKey',title:'代码',width:100},
            {field:'id',title:'操作',width:100,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = delProducer(' + value + ')>删除</a> | ' +
                        '<a href="javascript:void(0)" onclick = producerEdit(' + value + ')>编辑</a> ';
                }
            }
        ]],
        pagination:true,
        rownumbers:true
    });
}

/**
 * 打开添加窗口
 */
function openProducerSaveEdit() {
    cleanDialog("producer_dialog");
    $('#producer_dialog').dialog('open');
}

/**
 * 保存数据信息
 */
function saveProducer() {
    //判断页面填写是否为空
    if(!$("#producerForm").form("validate")){
        return;
    }
    parameter = $('#producerForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var producerId = $('#producerId').val();
    //
    if(null == producerId || producerId == ""){
        $.post("/inventory/producer/saveProducer?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#producer_table').datagrid('reload');
                $('#producer_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/inventory/producer/modifyProducer?" + access_token,parameter,function(data){
            if(200 == data.status){
                $('#producer_table').datagrid('reload');
                $('#producer_dialog').dialog('close');
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
 * 删除数据信息
 */
function delProducer(id) {
    parameter = {
        id:id,
        status:99
    };
    //
    $.post("/inventory/producer/delProducer?" + access_token,parameter,function(data){
        if(200 == data.status){
            $('#producer_table').datagrid('reload');
            $('#producer_dialog').dialog('close');
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
function producerEdit(id){
    cleanDialog("producer_dialog");
    //设置弹出框信息
    $('#producer_table').datagrid('selectRecord',id);
    generateDialog($('#producer_table').datagrid('getSelected'),"producer_dialog");
    $('#producer_dialog').dialog('open');
}

