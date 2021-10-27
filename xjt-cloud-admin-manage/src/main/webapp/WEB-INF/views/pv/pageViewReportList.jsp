<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>PV统计报表</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/pv/pageViewReportList.js"></script>
    <meta charset="utf-8">
    <!-- 引入 echarts.js -->
    <script src="/resource/echarts/echarts.min.js"/>
</head>

<!-- 主页面 begin -->
<div id="common_search" class="easyui-panel" fit="true">
    <!-- pvuv统计 -->
    <table id="table_pv_uv" style="height: auto" fit="true"/>

    <!--查询行 开始 -->
    <div id="pvToolbar" fit="true">
        <form id="search_form">
            <label>项目名称：</label><input type="text" id="projectNameTxt1" style='width:150px'/>
            <label>客户端：</label><input type="text" id="clientTypeNameTxt1" style='width:150px'/>
            <label>模块类型名称：</label><input type="text" id="pageTypeNameTxt1" style='width:150px'/>
            <label>时间</label>
            <input id="startdate" class="easyui-datebox" name="startdate" data-options="prompt:'请选择开始日期',editable:false,onSelect:onSelect">
            <input id="enddate"	class="easyui-datebox" name="enddate"   data-options="prompt:'请选择结束日期',editable:false,required:'true',disabled:true,validType:'equaldDate[\'#startdate\']'">
            <a id="btn" href="javascript:void(0);" onclick="findPvList1()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>

    <!-- pvuv列表 -->
    <table id="fireAlarmDevice_table" style="height: auto" fit="true"/>

    <!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
    <div class="echart-wrap" style="display: flex;justify-content: space-between">
        <div id="main" style="width: 700px;height:360px;"/>
        <div id="pvPie" style="width: 400px;height:360px;"/>
        <div id="uvPie" style="width: 400px;height:360px;"/>
    </div>
</div>
<!-- 主页面 end -->

<script>
    // 开始日期选择时触发
    function onSelect(date){
        console.log(date);
        // 启用结束日期控件
        $('#enddate').datebox('enable');
        // 重置结束日期的值
        $('#enddate').datebox('reset')
    };

    $.extend($.fn.validatebox.defaults.rules, {
        equaldDate: {
            validator: function(value, param){
                // 获取开始时间
                var d1 = $(param[0]).datetimebox('getValue');
                if(value < d1){
                    // 失效
                    $("#btn").linkbutton("disable");
                }else {
                    // 重新启用
                    $('#btn').linkbutton('enable');
                }
                // 有效范围为大于开始时间的日期
                return d1 <= value;
            },
            message: '结束日期不能早于开始日期!'
        }
    })
</script>

</html>
