$(function(){
    // 基于准备好的dom，初始化echarts实例
    //initIotFireAlarmDeviceData();
    initIotFireAlarmDeviceList();
    //iotFireAlarmDeviceCountPie();
});


function initIotFireAlarmDeviceList() {
    $('#iotReport_table').datagrid({
        nowrap: true,
        autoRowHeight:true,
        striped: true,
        toolbar: "#searchIotReport",
        fit:false,
        fitColumns:true,
        collapsible:true,
        url:'/iot/deviceReport/findIotDeviceReportList?' + access_token,
        remoteSort: false,
        singleSelect:true,
        columns:[[
            {field:'projectName',title:'项目名称',width:180},
            {field:'totalCount',title:'设备总数',width:100,styler: function(value,row,index){
                    return 'background-color:#FFE4E1;';
                }},
            {field:'waterGageTotalCount',title:'水压总数',width:100,styler: function(value,row,index){
                    return 'background-color:#E1FFFF;color:#000000;';
                }},
            {field:'waterGageOnLineCount',title:'在线',width:60,styler: function(value,row,index){
                        return 'background-color:#E1FFFF;color:#000000;';
                    }},
            {field:'waterGageOffLineCount',title:'离线',width:60,styler: function(value,row,index){
                    return 'background-color:#E1FFFF;' + (value > 0 ? 'color:red;' : '');
                }},
            {field:'waterLeachingTotalCount',title:'水浸总数',width:100,styler: function(value,row,index){
                    return 'background-color:#FDF5E6;color:#000000;';
                }},
            {field:'waterLeachingOnLineCount',title:'在线',width:60,styler: function(value,row,index){
                    return 'background-color:#FDF5E6;color:#000000;';
                }},
            {field:'waterLeachingOffLineCount',title:'离线',width:60,styler: function(value,row,index){
                    return 'background-color:#FDF5E6;' + (value > 0 ? 'color:red;' : '');
                }},
            {field:'smokeTotalCount',title:'烟感总数',width:100,styler: function(value,row,index){
                    return 'background-color:#E1FFFF;color:#000000;';
                }},
            {field:'smokeOnLineCount',title:'在线',width:60,styler: function(value,row,index){
                    return 'background-color:#E1FFFF;color:#000000;';
                }},
            {field:'smokeOffLineCount',title:'离线',width:60,styler: function(value,row,index){
                    return 'background-color:#E1FFFF;' + (value > 0 ? 'color:red;' : '');
                }},
            {field:'fireAlarmTotalCount',title:'火警总数',width:100,styler: function(value,row,index){
                    return 'background-color:#FFEFD5;color:#000000;';
                }},
            {field:'fireAlarmOnLineCount',title:'在线',width:60,styler: function(value,row,index){
                    return 'background-color:#FFEFD5;color:#000000;';
                }},
            {field:'fireAlarmOffLineCount',title:'离线',width:60,styler: function(value,row,index){
                    return 'background-color:#FFEFD5;' + (value > 0 ? 'color:red;' : '');
                }},
            {field:'vesaTotalCount',title:'极早期总数',width:100,styler: function(value,row,index){
                    return 'background-color:#E1FFFF;color:#000000;';
                }},
            {field:'vesaOnLineCount',title:'在线',width:60,styler: function(value,row,index){
                    return 'background-color:#E1FFFF;color:#000000;';
                }},
            {field:'vesaOffLineCount',title:'离线',width:60,styler: function(value,row,index){
                    return 'background-color:#E1FFFF;' + (value > 0 ? 'color:red;' : '');
                }},
            {field:'fireHydrantTotalCount',title:'消火栓总数',width:100,styler: function(value,row,index){
                    return 'background-color:#FFEFD5;color:#000000;';
                }},
            {field:'fireHydrantOnLineCount',title:'在线',width:60,styler: function(value,row,index){
                    return 'background-color:#FFEFD5;color:#000000;';
                }},
            {field:'fireHydrantOffLineCount',title:'离线',width:60,styler: function(value,row,index){
                    return 'background-color:#FFEFD5;' + (value > 0 ? 'color:red;' : '');
                }},
            {field:'linkageTotalCount',title:'声光总数',width:100,styler: function(value,row,index){
                    return 'background-color:#E1FFFF;color:#000000;';
                }},
            {field:'linkageOnLineCount',title:'在线',width:60,styler: function(value,row,index){
                    return 'background-color:#E1FFFF;color:#000000;';
                }},
            {field:'linkageOffLineCount',title:'离线',width:60,styler: function(value,row,index){
                    return 'background-color:#E1FFFF;' + (value > 0 ? 'color:red;' : '');
                }}
        ]],
        pagination:true,
        pageSize : 30,
        rownumbers:true
    });
}

function downIotDeviceReportExcel(){
    window.location.href = "/iot/deviceReport/downIotDeviceReportExcel?" + access_token;
}

/**
 * 查询数据表格
 */
function initIotFireAlarmDeviceData(){
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
        url: '/iot/deviceReport/findIotFireAlarmDeviceReportList?' + access_token,
        type : "post",
        data : {},
        dataType : "json",
        success: function(data){
            var list = data.rows;
            var yData = [
                {
                    name: '在线',
                    type: 'bar',
                    barGap: 0,
                    label: labelOption,
                    data: []
                },
                {
                    name: '离线',
                    type: 'bar',
                    barGap: 0,
                    label: labelOption,
                    data: []
                },
                {
                    name: '总数',
                    type: 'bar',
                    barGap: 0,
                    label: labelOption,
                    data: []
                }
            ];
            var xData = [];
              for(var i = 0;i<list.length;i++){
                  xData.push(list[i].projectName);
                  yData[0].data.push(list[i].onLineCount);
                  yData[1].data.push(list[i].offLineCount);
                  yData[2].data.push(list[i].totalCount);

              }

            setChartData(xData,yData)
        }
    });
}

function setChartData(xData,yData){
    var myChart = echarts.init(document.getElementById('main'));
    /*var option = {
        color: ['#228B22', '#DAA520', '#D2691E'],
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['在线', '离线', '总数']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        xAxis: [
            {
                type: 'category',
                axisTick: {show: true},
                data: xData
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series:yData
    };*/
    var option = {
        tooltip : {
            show: true,
            trigger: 'item'
        },
        legend: {
            data:['邮件营销','联盟广告','直接访问','搜索引擎']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                data : ['周一','周二','周三','周四','周五','周六','周日']
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'邮件营销',
                type:'bar',
                itemStyle: {        // 系列级个性化样式，纵向渐变填充
                    normal: {
                        barBorderColor:'red',
                        barBorderWidth: 5,
                        color :'green'
                    },
                    emphasis: {
                        barBorderWidth: 5,
                        barBorderColor:'green',
                        color: 'red',
                        label : {
                            show : true,
                            position : 'top',
                            formatter : "{a} {b} {c}",
                            textStyle : {
                                color: 'blue'
                            }
                        }
                    }
                },
                data:[220, 232, 101, 234, 190, 330, 210]
            },
            {
                name:'联盟广告',
                type:'bar',
                stack: '总量',
                data:[120, '-', 451, 134, 190, 230, 110]
            },
            {
                name:'直接访问',
                type:'bar',
                stack: '总量',
                itemStyle: {                // 系列级个性化
                    normal: {
                        barBorderWidth: 6,
                        barBorderColor:'tomato',
                        color: 'red'
                    },
                    emphasis: {
                        barBorderColor:'red',
                        color: 'blue'
                    }
                },
                data:[
                    320, 332, 100, 334,
                    {
                        value: 390,
                        symbolSize : 10,   // 数据级个性化
                        itemStyle: {
                            normal: {
                                color :'lime'
                            },
                            emphasis: {
                                color: 'skyBlue'
                            }
                        }
                    },
                    330, 320
                ]
            },
            {
                name:'搜索引擎',
                type:'bar',
                barWidth: 40,                   // 系列级个性化，柱形宽度
                itemStyle: {
                    normal: {                   // 系列级个性化，横向渐变填充
                        borderRadius: 5,
                        color : 'rgba(30,144,255,0.8)',
                        label : {
                            show : true,
                            textStyle : {
                                fontSize : '20',
                                fontFamily : '微软雅黑',
                                fontWeight : 'bold'
                            }
                        }
                    }
                },
                data:[
                    620, 732,
                    {
                        value: 701,
                        itemStyle : { normal: {label : {position: 'inside'}}}
                    },
                    734, 890, 930, 820
                ]
            }
        ]
    };





    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

function iotFireAlarmDeviceCountPie() {
    $.ajax({
        url: '/iot/deviceReport/findIotFireAlarmDeviceCountPie?' + access_token,
        type : "post",
        data : {},
        dataType : "json",
        success: function(data){
            var obj = data.obj;
            setPieData(obj.onlineCount,obj.offLineCount)
        }
    });
}
function setPieData(onlineCount,offLineCount) {
    var myChart = echarts.init(document.getElementById('mainPie'));
    var option = {
        title: {
            text: '火警主机监测设备异常比例',
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
            data: ['上线数量', '离线数量']
        },
        series: [
            {
                name: '火警主机',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    {value: onlineCount, name: '上线数量'},
                    {value: offLineCount, name: '离线数量'}
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