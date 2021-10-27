$(function(){
    // UV统计
    findUvCountTableList();

    // UV列表
    findPageViewReportList();

    // 柱状图 拆线图
    // 基于准备好的dom，初始化 echarts 实例
    findUserActiveColumnarList();

    // UV报表统计饼图
    findUserActiveCountPie();
});

// UV统计
function findUvCountTableList() {
    $('#uv_count_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#pvToolbar",
        fit:false,
        fitColumns:true,
        collapsible:true,
        url:'/userActiveReport/findUvCountTableList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'dateName',title:' ',width:60, formatter:function(value){
                    return value == null ? "/" : value;
                }},
            {field:'userCount',title:'总用户数',width:60},
            {field:'activeUserCount',title:'活跃用户数',width:40},
            {field:'activeUserCountRate',title:'活跃百分比',width:40},
            {field:'quietUserCount',title:'沉寂用户数',width:20},
            {field:'quietUserCountRate',title:'沉寂百分比',width:20}
        ]],
        pagination:true,
        rownumbers:true
    });
}


// UV列表
function findPageViewReportList() {
    $('#fireAlarmDevice_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#pvToolbar",
        fit:false,
        fitColumns:true,
        collapsible:true,
        url:'/userActiveReport/findUserActiveReportList?' + access_token,
        queryParams:parameter,
        remoteSort: false,
        singleSelect:true,
        idField:'id',
        columns:[[
            {field:'pageTypeName',title:'页面名称',width:60, formatter:function(value){
                    return value == null ? "/" : value;
                }},
            {field:'pageUrl',title:'页面路径',width:60},
            {field:'uvTotalCount',title:'用户活跃数',width:40}
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

    findUserActiveColumnarList(projectName, clientTypeName, pageTypeName, startTime, endTime);
    findUserActiveCountPie(projectName, clientTypeName, pageTypeName, startTime, endTime);
}

/**
 * 查询柱状图 拆线图
 */
function findUserActiveColumnarList(projectName, clientTypeName, pageTypeName, startTime, endTime){
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
        url: '/userActiveReport/findUserActiveColumnarList?' + access_token,
        type : "post",
        data : {"projectName":projectName, "clientTypeName":clientTypeName, "pageTypeName":pageTypeName, "startTimeStr":startTime, "endTimeStr":endTime},
        dataType : "json",
        success: function(data){
            var list = data.listObj;

            var uvData = [];
            var dataArr = [];
            var yMax = 10;
            for(var i = 0; i< list.length; i++){
                uvData.push(list[i].userCount);
                dataArr.push(list[i].timeStr);
            }

            setChartData(yMax, uvData, dataArr)
        }
    });
}

function setChartData(yMax, uvData, dataArr){
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    var option;

    option = {
        title: {
            text: '用户活跃度拆线图'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['UV']
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
                name: 'UV',
                type: 'line',
                stack: '总量',
                data: uvData
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

// UV报表统计饼图
function findUserActiveCountPie(projectName, clientTypeName, pageTypeName, startTime, endTime) {
    $.ajax({
        url: '/userActiveReport/findUserActiveCountPie?' + access_token,
        type : "post",
        data : {"projectName":projectName, "clientTypeName":clientTypeName, "pageTypeName":pageTypeName, "startTimeStr":startTime, "endTimeStr":endTime},
        dataType : "json",
        success: function(data){
            var obj = data.obj;
            setPieData(obj.androidCount, obj.iosCount, obj.pcCount)
        }
    });
}

function setPieData(androidCount, iosCount, pcCount) {
    var myChart = echarts.init(document.getElementById('mainPie'));
    var option = {
        title: {
            text: '用户活跃度饼图',
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
                name: 'UV统计',
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
