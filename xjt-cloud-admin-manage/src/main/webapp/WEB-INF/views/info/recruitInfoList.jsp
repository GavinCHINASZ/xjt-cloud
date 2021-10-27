<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>招聘信息管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/info/recruitInfoList.js"></script>

</head>
<!-- 主页面 begin -->
<div  id="common_search" class="easyui-panel" fit="true">
    <!--查询行 开始 -->
    <div id="toolbar" fit="true">
        <form id="search_form">
            <label>用户名称：</label><input type="text" id="userNameTxt" >
            <label>电话：</label><input type="text" id="phoneTxt">
            <a href="javascript:void(0);" onclick="findRecruitInfoList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="recruitInfo_table" fit="true"></table>
</div>
<!-- 主页面 end -->



</html>