$(function(){
    // PV UV统计
    findPageViewReportPvUvList();

    // PV列表
    findPageViewReportList();

    // 基于准备好的dom，初始化 echarts 实例
    findPageViewColumnarList();

    // PV报表统计饼图
    findPvPageViewCountPie();

    // uV报表统计饼图
    findUvPageViewCountPie();
});

// PV UV统计
function findPageViewReportPvUvList() {
    $('#table_pv_uv').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#pvToolbar",
        fit:false,
        fitColumns:true,
        collapsible:true,
        url:'/pageViewReport/findPageViewReportPvUvList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'dateName',title:' ',width:60, formatter:function(value){
                    return value == null ? " " : value;
                }},
            {field:'pvTotalCount',title:'总PV',width:40},
            {field:'uvTotalCount',title:'总UV',width:40},
            {field:'oneLevelPvCount',title:'1级PV',width:40},
            {field:'oneLevelUvCount',title:'1级UV',width:40},
            {field:'twoLevelPvCount',title:'2级PV',width:40},
            {field:'twoLevelUvCount',title:'2级UV',width:40},
            {field:'otherPvCount',title:'其它PV',width:40},
            {field:'otherUvCount',title:'其它UV',width:40}
        ]],
        pagination:true,
        rownumbers:true
    });
}

// PV列表
function findPageViewReportList() {
    $('#fireAlarmDevice_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#pvToolbar",
        fit:false,
        fitColumns:true,
        collapsible:true,
        url:'/pageViewReport/findPageViewReportList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'pageTypeName',title:'页面名称',width:60, formatter:function(value){
                    return value == null ? "/" : value;
                }},
            {field:'pageUrl',title:'页面路径',width:80, formatter:function(value){
                    return value == null ? "/" : value;
                }},
            {field:'num',title:'PV',width:40},
            {field:'uvTotalCount',title:'UV',width:40}
        ]],
        pagination:true,
        pageList:[20],
        rownumbers:true
    });
}

function assParameter(){
    parameter = {
        projectName:$("#projectNameTxt1").val(),
        clientTypeName:$("#clientTypeNameTxt1").val(),
        pageTypeName:$("#pageTypeNameTxt1").val()
    };
}

function findPvList1() {
    var projectName = $("#projectNameTxt1").val();
    var clientTypeName = $("#clientTypeNameTxt1").val();
    var pageTypeName = $("#pageTypeNameTxt1").val();
    var startTime = $("#startdate").datebox('getValue');
    var endTime = $("#enddate").datebox('getValue');

    parameter = {
        projectName:projectName,
        clientTypeName:clientTypeName,
        pageTypeName:pageTypeName,
        startTimeStr:startTime,
        endTimeStr:endTime
    };

    $('#fireAlarmDevice_table').datagrid('options').queryParams = parameter;
    $('#fireAlarmDevice_table').datagrid('options').pageNumber = 1;
    $('#fireAlarmDevice_table').datagrid('getPager').pagination({
        pageNumber : 1
    });
    $('#fireAlarmDevice_table').datagrid('reload');

    findPageViewColumnarList(projectName, clientTypeName, pageTypeName, startTime, endTime);
    findPvPageViewCountPie(projectName, clientTypeName, pageTypeName, startTime, endTime);
    findUvPageViewCountPie(projectName, clientTypeName, pageTypeName, startTime, endTime);
}

/**
 * 查询 拆线图
 */
function findPageViewColumnarList(projectName, clientTypeName, pageTypeName, startTime, endTime){
    var labelOption = {
            show: true,
            position: 'insideBottom',
            distance: 20,
            align: 'left',
            verticalAlign: 'middle',
            rotate: 90,
            formatter: '{c}  {name|{a}}',
            fontSize: 16,
            rich: {
                name: {
                    textBorderColor: '#fff'
                }
            }
    };

    $.ajax({
        url: '/pageViewReport/findPageViewColumnarList?' + access_token,
        type : "post",
        data : {"projectName":projectName, "clientTypeName":clientTypeName, "pageTypeName":pageTypeName, "startTimeStr":startTime, "endTimeStr":endTime},
        dataType : "json",
        success: function(data){
            var list = data.listObj;

            var pvData = [];
            var uvDate = [];
            var dataArr = [];
            var yMax = 10;
            for(var i = 0; i< list.length; i++){
                pvData.push(list[i].pvTotalCount);
                uvDate.push(list[i].uvTotalCount);
                dataArr.push(list[i].timeStr);
            }

            setChartData(yMax, pvData, uvDate, dataArr)
        }
    });
}

function setChartData(yMax, pvData, uvDate, dataArr){
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    var option;

    option = {
        title: {
            text: 'PVUV折线图'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['PV', 'UV']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: dataArr
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 'PV',
                type: 'line',
                stack: '总量',
                data: pvData
            },
            {
                name: 'UV',
                type: 'line',
                stack: '总量',
                data: uvDate
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

// PV报表统计饼图
function findPvPageViewCountPie(projectName, clientTypeName, pageTypeName, startTime, endTime) {
    $.ajax({
        url: '/pageViewReport/findPageViewCountPie?' + access_token,
        type : "post",
        data : {"projectName":projectName, "clientTypeName":clientTypeName, "pageTypeName":pageTypeName, "startTimeStr":startTime, "endTimeStr":endTime},
        dataType : "json",
        success: function(data){
            var obj = data.obj;
            setPvPieData(obj.androidCount, obj.iosCount, obj.pcCount)
        }
    });
}

function setPvPieData(androidCount, iosCount, pcCount) {
    var myChart = echarts.init(document.getElementById('pvPie'));
    var option = {
        title: {
            text: 'PV报表统计饼图',
            left: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
            //orient: 'vertical',
            //left: 'left',
            bottom: 10,
            left: 'center',
            data: ['Android数量', 'ios数量', 'PC数量']
        },
        series: [
            {
                name: 'PV统计',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    {value: androidCount, name: 'Android数量'},
                    {value: iosCount, name: 'ios数量'},
                    {value: pcCount, name: 'PC数量'}
                ],
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

// uv报表统计饼图
function findUvPageViewCountPie(projectName, clientTypeName, pageTypeName, startTime, endTime) {
    $.ajax({
        url: '/pageViewReport/findUvPageViewCountPie?' + access_token,
        type : "post",
        data : {"projectName":projectName, "clientTypeName":clientTypeName, "pageTypeName":pageTypeName, "startTimeStr":startTime, "endTimeStr":endTime},
        dataType : "json",
        success: function(data){
            var obj = data.obj;
            setUvPieData(obj.androidCount, obj.iosCount, obj.pcCount)
        }
    });
}

function setUvPieData(androidCount, iosCount, pcCount) {
    var myChart = echarts.init(document.getElementById('uvPie'));
    var option = {
        title: {
            text: 'UV报表统计饼图',
            left: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
            //orient: 'vertical',
            //left: 'left',
            bottom: 10,
            left: 'center',
            data: ['Android数量', 'ios数量', 'PC数量']
        },
        series: [
            {
                name: 'PV统计',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    {value: androidCount, name: 'Android数量'},
                    {value: iosCount, name: 'ios数量'},
                    {value: pcCount, name: 'PC数量'}
                ],
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}
