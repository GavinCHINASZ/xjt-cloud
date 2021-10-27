<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>设备系统管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/iot/waterList.js"></script>

</head>
<!-- 主页面 begin -->
<div  id="common_search" class="easyui-panel" fit="true">
    <div id="searchWaterDevice" style="padding:5px" >
        <label>项目名称：</label><input type="text" id="projectNameTxt" style='width:150px'/>
        <label>设备名称：</label><input type="text" id="waterDeviceNameTxt" style='width:150px'/>
        <label>拼音首写：</label><input type="text" id="pinYinInitialsTxt" style='width:150px'/>
        <label>设备ID：</label><input type="text" id="deviceQrNoTxt" style='width:150px'/>
        <label>传感器ID：</label><input type="text" id="sensorNoTxt" style='width:150px'/>
        <label>域名：</label><input type="text" id="urlTxt" style='width:150px'/>
        <label>端口：</label><input type="text" id="portTxt" style='width:150px'/>
        <label>类型：</label>
        <select id="deviceTypeSel" >
            <option value="" selected="selected">全部</option>
            <option value=2 >水压监测</option>
            <option value=3>智能水浸</option>
            <option value=13>水厢单位为M</option>
            <option value=14>两端压力设备</option>
            <option value=8>消火栓</option>
        </select> <span class="select-arrow"></span>
        <a href="javascript:void(0);" onclick="findWaterDeviceList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    </div>
    <table id="waterDevice_table" style="height: auto" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 新增修改页面 begin-->
<div id="waterDevice_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'水压设备信息',iconCls:'icon-save'" style="padding:5px;width:700px;height:700px;">
    <form action="" id="waterDeviceForm">
        <input type="hidden" name="id" id="waterDeviceId" />
        <input type="hidden" name="type" id="waterWaterDeviceHid" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>设备名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="deviceSysName" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
                <td class="tableTdRight"><span><label>设备ID：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="qrNo" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>巡检点：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="pointName" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
                <td class="tableTdRight"><span><label>设备位置：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="pointLocationDesc" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>传感器ID：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <input type="text" name="sensorNo" style="border: 0px;outline:none;cursor: pointer;" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>数据采样间隔：</label></span></td>
                <td class="tableTdLeft">
                    <select name="dataInterval" id="dataIntervalSel" class="form-control">

                    </select>
                </td>
                <td class="tableTdRight"><span><label>数据发送间隔：</label></span></td>
                <td class="tableTdLeft">
                    <select name="heartbeat" id="heartbeatSel" class="form-control">

                    </select>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>数据告警方式：</label></span></td>
                <td class="tableTdLeft">
                    <select name="alarmMode" class="form-control">
                        <option value="">请选择告警方式</option>
                        <option value="1">阈值告警</option>
                        <option value="2">波动值告警</option>
                        <option value="3">阈值和波动值告警</option>
                    </select>
                </td>
                <td class="tableTdRight"><span><label>波动告警值：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" id="waveAlarmValueFloatTxt" name="waveAlarmValueFloat" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'"><span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>上限告警值：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"id="maxValueFloatTxt"  name="maxValueFloat" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'"><span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>下限告警值：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" id="minValueFloatTxt" name="minValueFloat" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'"><span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>域名：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="url" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'"><span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>端口号：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="port" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'"><span class="star">*</span>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

</html>