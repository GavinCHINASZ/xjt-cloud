$(function(){
    $("#sysPinYinInitialsTxt").hide();
    $("#sysPinYinInitialsTxt").attr("disabled","disabled");
    $('#waterDevice_dialog').dialog({
        buttons:[{
            text:'确 定',
            handler:function(){
                saveWaterDevice();
            }
        },{
            text:'取消',
            handler:function(){
                $("#waterDevice_dialog").dialog('close');
                cleanDialog("waterDevice_dialog");
            }
        }]
    });
    initWaterDeviceDataGrid();
});

function assParameter(){
    parameter = {
        pinYinInitials:$("#pinYinInitialsTxt").val(),
        deviceSysName:$("#waterDeviceNameTxt").val(),
        qrNo:$("#deviceQrNoTxt").val(),
        sensorNo:$("#sensorNoTxt").val(),
        type:$("#deviceTypeSel").val(),
        projectName:$('#projectNameTxt').val(),
        url:$("#urlTxt").val(),
        port:$('#portTxt').val()
    };
}

function findWaterDeviceList(){
    assParameter();
    $('#waterDevice_table').datagrid('options').queryParams = parameter;
    $('#waterDevice_table').datagrid('options').pageNumber = 1;
    $('#waterDevice_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#waterDevice_table').datagrid('reload');
}

/**
 * 查询数据表格
 */
function initWaterDeviceDataGrid(){
    assParameter();
    $('#waterDevice_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#toolbar",
        fit:false,
        fitColumns:true,
        collapsible:true,
        url:'/iot/water/findWaterDeviceList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'projectName',title:'项目名称',width:100},
            {field:'deviceSysName',title:'设备名称',width:100},
            {field:'qrNo',title:'设备ID',width:100},
            {field:'sensorNo',title:'传感器ID',width:100},
            {field:'deviceStatus',title:'是否在线',width:60,formatter:function(value){
                    return value == 1 ? "正常" : "离线";
                }},
            {field:'dataIntervalDesc',title:'数据采样间隔',width:100},
            {field:'heartbeatDesc',title:'数据发送间隔',width:100},
            {field:'alarmModeDesc',title:'告警方式',width:100},
            {field:'waveAlarmValue',title:'波动告警值',width:100,formatter:function(value){
                    return value / 100;
                }},
            {field:'maxValue',title:'上限告警值',width:70,formatter:function(value){
                    return value / 100;
                }},
            {field:'minValue',title:'下限告警值',width:70,formatter:function(value){
                    return value / 100;
                }},
            {field:'url',title:'域名',width:150},
            {field:'port',title:'端口号',width:60},
            {field:'createTime',title:'创建时间',width:100},
            {field:'id',title:'操作',width:100,formatter:function(value){
                    return '<a href="javascript:void(0)" onclick = waterDeviceEdit(' + value + ')>编辑</a> ';
                }
            }
        ]],
        pagination:true,
        pageSize : 30,
        rownumbers:true
    });
}

/**
 * 保存数据词典信息
 */
function saveWaterDevice() {
    //判断页面填写是否为空
    if(!$("#waterDeviceForm").form("validate")){
        return;
    }
    parameter = $('#waterDeviceForm').serialize();
    $.post("/iot/water/modifyWaterDevice?" + access_token,parameter,function(data){
        if(200 == data.status){
            $('#waterDevice_table').datagrid('reload');
            $('#waterDevice_dialog').dialog('close');
            showMsg('保存成功！');
        }else{
            showMsg(data.msg);
        }
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}
/**
 * 修改词典信息
 */
function waterDeviceEdit(id){
    $("#sysPinYinInitialsTxt").show();
    cleanDialog("waterDevice_dialog");
    //设置弹出框信息
    $('#waterDevice_table').datagrid('selectRecord',id);
    var row = $('#waterDevice_table').datagrid('getSelected');
    waterSetInit(row.deviceType);
    $("#waveAlarmValueFloatTxt").val(row["waveAlarmValue"] / 100.00);
    $("#maxValueFloatTxt").val(row["maxValue"] / 100.00);
    $("#minValueFloatTxt").val(row["minValue"] / 100.00);
    generateDialog(row,"waterDevice_dialog");
    $('#waterDevice_dialog').dialog('open');
}

function waterSetInit(deviceType) {
    $("#dataIntervalSel").empty();
    $("#heartbeatSel").empty();
    if (deviceType != null && deviceType == 8){
        $("#dataIntervalSel").append('<option value="0">20秒</option>');
        $("#dataIntervalSel").append('<option value="1">1秒</option>');
        $("#dataIntervalSel").append('<option value="2">2秒</option>');
        $("#dataIntervalSel").append('<option value="3">3秒</option>');
        $("#dataIntervalSel").append('<option value="4">4秒</option>');
        $("#dataIntervalSel").append('<option value="5">5秒</option>');
        $("#dataIntervalSel").append('<option value="6">6秒</option>');
        $("#dataIntervalSel").append('<option value="7">7秒</option>');
        $("#dataIntervalSel").append('<option value="8">8秒</option>');
        $("#dataIntervalSel").append('<option value="9">9秒</option>');

        $("#heartbeatSel").append('<option value="1">24小时</option>');
        $("#heartbeatSel").append('<option value="2">12小时</option>');
        $("#heartbeatSel").append('<option value="3">6小时</option>');
        $("#heartbeatSel").append('<option value="4">3小时</option>');
        $("#heartbeatSel").append('<option value="5">2小时</option>');
        $("#heartbeatSel").append('<option value="6">1小时</option>');
        $("#heartbeatSel").append('<option value="7">半小时</option>');
    }else{
        $("#dataIntervalSel").append('<option value="3">请选择数据采样间隔</option>');
        $("#dataIntervalSel").append('<option value="3">30秒</option>');
        $("#dataIntervalSel").append('<option value="4">1分钟</option>');
        $("#dataIntervalSel").append('<option value="5">5分钟</option>');
        $("#dataIntervalSel").append('<option value="6">10分钟</option>');
        $("#dataIntervalSel").append('<option value="7">15分钟</option>');
        $("#dataIntervalSel").append('<option value="8">30分钟</option>');
        $("#dataIntervalSel").append('<option value="9">60分钟</option>');
        $("#dataIntervalSel").append('<option value="10">120分钟</option>');
        $("#dataIntervalSel").append('<option value="11">360分钟</option>');
        $("#dataIntervalSel").append('<option value="12">720分钟</option>');
        $("#dataIntervalSel").append('<option value="13">1440分钟</option>');

        $("#heartbeatSel").append('<option value="13">请选择数据发送间隔</option>');
        $("#heartbeatSel").append('<option value="9">60分钟</option>');
        $("#heartbeatSel").append('<option value="10">120分钟</option>');
        $("#heartbeatSel").append('<option value="11">360分钟</option>');
        $("#heartbeatSel").append('<option value="12">720分钟</option>');
        $("#heartbeatSel").append('<option value="13">1440分钟</option>');
    }
}

