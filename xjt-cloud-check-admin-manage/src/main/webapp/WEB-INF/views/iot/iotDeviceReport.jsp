<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>物联设备报表</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/iot/iotDeviceReport.js"></script>
    <meta charset="utf-8">
    <!-- 引入 echarts.js -->
    <script src="/resource/echarts/echarts.min.js"></script>
</head>

<!-- 主页面 begin -->
<div  id="common_search" class="easyui-panel" fit="true">
    <div id="searchIotReport" style="padding:5px" >
        <a href="javascript:void(0);" onclick="downIotDeviceReportExcel()" class="easyui-linkbutton" data-options="iconCls:'icon-downward'">报表下载</a>
    </div>
    <table id="iotReport_table" style="height: auto" fit="true"></table>
</div>
</html>