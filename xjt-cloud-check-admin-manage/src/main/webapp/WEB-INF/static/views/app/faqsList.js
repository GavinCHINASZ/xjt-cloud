$(function(){
    $("#sysPinYinInitialsTxt").hide();
    $("#sysPinYinInitialsTxt").attr("disabled","disabled");
    $('#Faqs_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveFaqs();
            }
        },{
            text:'取消',
            handler:function(){
                $("#Faqs_dialog").dialog('close');
                cleanDialog("Faqs_dialog");
            }
        }]
    });
    $('#Faqse_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveFaqse();
            }
        },{
            text:'取消',
            handler:function(){
                $("#Faqse_dialog").dialog('close');
                cleanDialog("Faqse_dialog");
            }
        }]
    });
    initFaqsDataGrid();
    initFaqseList();
});

/**
 * 初使设备类型列表
 * user:wangzhiwen
 * date:2017/8/29 17:26
 **/
function initFaqseList(){
    $.post("/dict/findCacheDictItemListByCode?" + access_token,{"dictCode":"DEVICE_SYS_TYPE"},function(data){
        $.each(data,function(index,dict){
            $("#FaqseSel").append('<option value=' + dict.itemValue + '>' + dict.itemName + '</option>');
        });
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}

function assParameter(){
    parameter = {
        pinYinInitials:$("pinYinInitialsTxt").val(),
        FaqsName:$("#FaqsNameTxt").val()
    };
}

function findFaqsList(){
    initFaqsDataGrid();
}

/**
 * 查询数据表格
 */
function initFaqsDataGrid(){
    assParameter();
    $('#Faqs_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#FaqsToolbar",
        fit:true,
        fitColumns:true,
        collapsible:true,
        url:'/device/sys/findFaqsList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'FaqsName',title:'名称',width:100},
            {field:'pinYinInitials',title:'拼音首字母',width:100},
            {field:'createTime',title:'创建时间',width:100},
            {field:'id',title:'操作',width:100,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = FaqsEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delFaqs(' + value + ')>删除</a> | ' +
                        '<a href="javascript:void(0)" onclick = FaqsePage(' + value + ')>设备类型管理</a>';
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
function openFaqsSaveEdit() {
    $('#Faqs_dialog').dialog('open');
}

/**
 * 保存数据词典信息
 */
function saveFaqs() {
    //判断页面填写是否为空
    if(!$("#FaqsForm").form("validate")){
        return;
    }
    parameter = $('#FaqsForm').serialize();
    //获取主键值，根据主键值判断添加或修改
    var FaqsId = $('#FaqsId').val();
    //
    if(null == FaqsId || FaqsId == ""){
        $.post("/device/sys/saveFaqs",parameter,function(data){
            if(200 == data.status){
                $('#Faqs_table').datagrid('reload');
                $('#Faqs_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/device/sys/modifyFaqs",parameter,function(data){
            if(200 == data.status){
                $('#Faqs_table').datagrid('reload');
                $('#Faqs_dialog').dialog('close');
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
function FaqsEdit(id){
    $("#sysPinYinInitialsTxt").show();
    cleanDialog("Faqs_dialog");
    //设置弹出框信息
    $('#Faqs_table').datagrid('selectRecord',id);
    generateDialog($('#Faqs_table').datagrid('getSelected'),"Faqs_dialog");
    $('#Faqs_dialog').dialog('open');
}

/**
 * 删除词典信息
 */
function delFaqs(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/device/sys/delFaqs?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#Faqs_table').datagrid('reload');
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

var parameterItem;
function assParameterItem(){
    parameterItem = {
        parentId:$('#FaqsId2').val()
    };
}
/**
 * 词典项管理
 */
function FaqsePage(id){
    $('#FaqsId2').val(id);
    $("#Faqse_search").dialog('open');
    initFaqseDataGrid();
}

/**
 * 查询数据表格
 */
function initFaqseDataGrid(){
    assParameterItem();
    $('#Faqse_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        fit:true,
        toolbar: "#FaqseToolbarItem",
        fitColumns:true,
        collapsible:true,
        url:'/device/sys/findFaqseList?'　+　access_token,
        queryParams:parameterItem,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'FaqsName',title:'名称',width:100},
            {field:'pinYinInitials',title:'拼音首字母',width:100},
            {field:'pressureMonitor',title:'是否具有压力监测或液位监测',width:100,formatter:function(value){
                    return value == 1 ? "是":"否";
                }},
            {field:'sysPressure',title:'系统端压力',width:100,formatter:function(value){
                    return value == 1 ? "是":"否";
                }},
            {field:'FaqseDesc',title:'设备类型',width:100},
            {field:'id',title:'操作',width:100,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = FaqseEdit(' + value + ')>编辑</a> | ' +
                        '<a href="javascript:void(0)" onclick = delFaqse(' + value + ')>删除</a>';
                }
            }
        ]],
        pagination:true,
        rownumbers:true
    });
}

/**
 * 打开添加数据词典项窗口
 */
function openFaqseSaveEdit() {
    $('#FaqseForm')[0].reset();
    $("#FaqsId3").val($("#FaqsId2").val());
    $('#Faqse_dialog').dialog('open');
}

/**
 * 保存数据词典项信息
 */
function saveFaqse() {
    //判断页面填写是否为空
    if(!$("#FaqseForm").form("validate")){
        return;
    }
    parameterItem = $('#FaqseForm').serialize();

    //获取主键值，根据主键值判断添加或修改
    var FaqseId = $('#FaqseId').val();
    //
    if(null == FaqseId || FaqseId == ""){
        $.post("/device/sys/saveFaqse?" + access_token,parameterItem,function(data){
            if(200 == data.status){
                $('#Faqse_table').datagrid('reload');
                $('#Faqse_dialog').dialog('close');
                showMsg('保存成功！');
            }else{
                showMsg(data.msg);
            }
        },"json").error(function (error, status, info){
            showMsg(error.responseText);
        });
    }else{
        $.post("/device/sys/modifyFaqse?" + access_token,parameterItem,function(data){
            if(200 == data.status){
                $('#Faqse_table').datagrid('reload');
                $('#Faqse_dialog').dialog('close');
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
 * 修改词典项信息
 */
function FaqseEdit(id){
    $('#FaqseForm')[0].reset();
    //设置弹出框信息
    $('#Faqse_table').datagrid('selectRecord',id);
    generateDialog($('#Faqse_table').datagrid('getSelected'),"Faqse_dialog");
    $('#Faqse_dialog').dialog('open');
}

/**
 * 删除词典项信息
 */
function delFaqse(id){
    $.messager.confirm(titleInfo, '您确定删除吗？', function(r){
        if (r){
            $.post("/device/sys/delFaqse?" + access_token,{ "id":id,"status":99 },function(data){
                if(200 == data.status){
                    $('#Faqse_table').datagrid('reload');
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

