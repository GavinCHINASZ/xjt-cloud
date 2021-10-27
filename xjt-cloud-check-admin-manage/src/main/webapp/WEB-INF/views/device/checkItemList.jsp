<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>设备巡检项管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/device/checkItemList.js"></script>

</head>
<!-- 主页面 begin -->
<div  id="deviceCheckItem_search" class="easyui-panel" fit="true">
    <!--查询行 开始 -->
    <div id="deviceCheckItemToolbar" fit="true">
        <form id="deviceSysSearch_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-config" plain="true" onclick="openProjectCheckItemPage();" >项目配置</a>
            <label>系统名称：</label><input type="text" id="deviceSysNameTxt" style='width:150px'/>
            <label>类型名称：</label><input type="text" id="deviceTypeNameTxt" style='width:150px'/>
            <label>项目：</label><select id="projectSel2" ></select>
            <label>巡检项版本：</label><select id="vsTypeSel" class="form-control">
                <option value="">ALL</option>
                <option value="1">默认版</option>
                <option value="2">精简版</option>
                <option value="3">自定义版</option>
            </select>
            <a href="javascript:void(0);" onclick="findCheckItemList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="deviceCheckItem_table" style="height: auto" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 弹出词典项页面 开始-->
<div id="deviceCheckItemRelation_search" class="easyui-dialog " data-options="closed:true,modal:true,title:'项目巡检项管理'" style="width:1200px;height:750px">
    <!--查询行 开始 -->
    <div id="deviceCheckItemRelationToolbarItem" fit="true">
        <form id="deviceCheckItemRelationSearch_form">
            <label>添加项目：</label><select id="projectSel" ></select><span class="select-arrow"></span><span class="star">*</span>
            <br/><span><hr/></span>
            <br/><span><font color="red">查询时，会排除'添加项目'中已添加的巡检项，选择条件'项目'只能与'版本自定义'一起使用</font> </span><br/>
            <label>系统名称：</label><input type="text" id="deviceSysNameTxt1" style='width:150px'/>
            <label>设备名称：</label><input type="text" id="deviceTypeNameTxt1" style='width:150px'/>
            <label>项目：</label><select id="projectSel1" ></select>
            <label>巡检项版本：</label><select id="vsTypeSel2" class="form-control">
                <option value="1">默认版</option>
                <option value="2">精简版</option>
                <option value="3">自定义版</option>
            </select>
            <a href="javascript:void(0);" onclick="findRelationCheckItemList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            <a href="javascript:void(0);" onclick="saveProjectCheckItem('')" class="easyui-linkbutton" data-options="iconCls:'icon-save'">添加</a>
            <a href="javascript:void(0);" onclick="saveProjectCheckItem('ALL')" class="easyui-linkbutton" data-options="iconCls:'icon-save'">全部添加</a>
        </form>
    </div>
    <table id="deviceCheckItemRelation_table" style="height:635px;"></table>
</div>
<!-- 弹出词典项页面 结束 -->
</html>