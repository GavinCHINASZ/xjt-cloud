<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>设备系统管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/device/deviceSysList.js"></script>

</head>
<!-- 主页面 begin -->
<div  id="deviceSys_search" class="easyui-panel" fit="true">
    <!--查询行 开始 -->
    <div id="deviceSysToolbar" fit="true">
        <form id="deviceSysSearch_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openDeviceSysSaveEdit();" >添加</a>
            <label>系统名称：</label><input type="text" id="deviceSysNameTxt" style='width:150px'/>
            <label>类型名称：</label><input type="text" id="deviceTypeNameTxt" style='width:150px'/>
            <label>项目名称：</label><input type="text" id="projectNameTxt" style='width:150px'/>
            <label>巡检项版本：</label><select id="vsTypeSel" class="form-control">
                <option value="">ALL</option>
                <option value="1">默认版</option>
                <option value="2">精简版</option>
                <option value="3">自定义版</option>
            </select>
            <label>设备类型：</label><select id="deviceTypeSel2" name="deviceType"></select><span class="select-arrow"></span>
            <a href="javascript:void(0);" onclick="findDeviceSysList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="deviceSys_table" style="height: auto" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 新增修改页面 begin-->
<div id="deviceSys_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'设备系统信息',iconCls:'icon-save'" style="padding:5px;width:500px;height:300px;">
    <form action="" id="deviceSysForm">
        <input type="hidden" name="id" id="deviceSysId" />
        <input type="hidden" name="type" value="1" id="deviceSysTypeHid" />
        <input type="hidden" name="parentId" value="0" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="deviceSysName" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'"
                        style="width: 200px">
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>拼音首写字母：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" id="sysPinYinInitialsTxt" name="pinYinInitials" style="width: 200px" readonly>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

<!-- 弹出词典项页面 开始-->
<div id="deviceType_search" class="easyui-dialog " data-options="closed:true,modal:true,title:'设备类型管理'" style="width:1000px;height:750px">
    <input type="hidden" id="deviceSysId2" name="id">
    <!--查询行 开始 -->
    <div id="deviceTypeToolbarItem" fit="true">
        <form id="deviceTypeSearch_item_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openDeviceTypeSaveEdit();" >添加</a>
            <label>类型名称：</label><input type="text" id="deviceTypeNameTxt1" style='width:150px'/>
            <label>项目名称：</label><input type="text" id="projectNameTxt1" style='width:150px'/>
            <label>巡检项版本：</label><select id="vsTypeSel1" class="form-control">
                <option value="1">默认版</option>
                <option value="2">精简版</option>
                <option value="3">自定义版</option>
            </select>
            <label>设备类型：</label><select id="deviceTypeSel1" name="deviceType"></select><span class="select-arrow"></span>
            <a href="javascript:void(0);" onclick="findDeviceTypeList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="deviceType_table" style="height:635px;"></table>
</div>
<!-- 弹出词典项页面 结束 -->

<!-- 新增修改页面 begin-->
<div id="deviceType_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'设备类型信息',iconCls:'icon-save'" style="padding:5px;width:1000px;height:800px;">
    <form action="" id="deviceTypeForm">
        <input type="hidden" name="id" id="deviceTypeId" />
        <input type="hidden" name="parentId" id="deviceSysId3" />
        <input type="hidden" name="type" value="9" id="deviceTypeTypeHid" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>系统名称：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <input type="text" id="deviceSysNameHid1" style="background-color:transparent;border:0;" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>设备名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="deviceSysName" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
                <td class="tableTdRight"><span><label>拼音首写字母 ：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text"  name="pinYinInitials" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>是否具有压力监测或液位监测：</label></span></td>
                <td class="tableTdLeft">
                    <label><input type="radio" name="pressureMonitor" value="1">是</label>
                    <label><input type="radio" name="pressureMonitor" value="0">否</label>
                </td>
                <td class="tableTdRight"><span><label>系统端压力 ：</label></span></td>
                <td class="tableTdLeft" >
                    <label><input type="radio" name="sysPressure" value="1">是</label>
                    <label><input type="radio" name="sysPressure" value="0">否</label>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>设备类型 ：</label></span></td>
                <td class="tableTdLeft">
                    <select id="deviceTypeSel" name="deviceType"></select><span class="select-arrow"></span>
                </td>
                <td class="tableTdRight"><span><label>设备限制：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="limitConfig" >
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>功能和使用方法  ：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea name="useMethod" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 330px;height: 100px"></textarea>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>描述  ：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea name="description" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 330px;height: 100px"></textarea>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>图片  ：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <img src="" style='height:80px;' id="imgUrl"/><br/>
                    <input type="hidden" name="imgUrl" id="imgUrl_hid" />
                    <input id="imgUrl_file_upload" type="file"/>
                    <a href="javascript:void(0);" onclick="uploadJs('imgUrl','deviceSys')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">上传</a>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

<!-- 弹出词典项页面 开始-->
<div id="checkItem_search" class="easyui-dialog " data-options="closed:true,modal:true,title:'设备巡检项管理'" style="width:1000px;height:750px">
    <input type="hidden" id="checkItemDeviceTypeHid" name="id">
    <!--查询行 开始 -->
    <div id="checkItemToolbarItem" fit="true">
        <form id="checkItemSearch_item_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openCheckItemSaveEdit();" >添加</a>
            <label>项目名称：</label><input type="text" id="projectNameTxt2" style='width:150px'/>
            <label>巡检项版本：</label><select id="vsTypeSel2" class="form-control">
                <option value="1">默认版</option>
                <option value="2">精简版</option>
                <option value="3">自定义版</option>
            </select>
            <a href="javascript:void(0);" onclick="findCheckItemList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="checkItem_table" style="height:635px;"></table>
</div>
<!-- 弹出词典项页面 结束 -->

<!-- 新增修改页面 begin-->
<div id="checkItem_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'设备巡检项信息',iconCls:'icon-save'" style="padding:5px;width:1000px;height:800px;">
    <form action="" id="checkItemForm">
        <input type="hidden" id="saveCheckItemDeviceTypeHid" name="deviceTypeId">
        <input type="hidden" name="id" id="checkItemIdHid" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>系统名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" id="deviceSysNameHid3" style="background-color:transparent;border:0;" readonly>
                </td>
                <td class="tableTdRight"><span><label>类型名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" id="deviceTypeNameHid3" style="background-color:transparent;border:0;" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>类型：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <select name="checkAction" class="form-control">
                        <option value="1">巡检</option>
                        <option value="2">检测</option>
                        <option value="3">保养</option>
                        <option value="4">抽检</option>
                    </select>
                </td>
                <td class="tableTdRight"><span><label>名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="checkName" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                    <span class="star">*</span>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>巡检方式：</label></span></td>
                <td class="tableTdLeft" >
                    <select name="checkType" id="checkTypeSel" class="form-control" onchange="onChangeCheckType()">
                        <option value="0">默认</option>
                        <option value="1">数值</option>
                        <option value="2">计数</option>
                        <option value="3">描述</option>
                        <option value="4">抽检</option>
                    </select>
                </td>
                <td class="tableTdRight"><span><label>建议巡检周期：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <select name="periodType" class="form-control">
                        <option value="">--请选择--</option>
                        <option value="9">每两年</option>
                        <option value="5">每年</option>
                        <option value="4">每半年</option>
                        <option value="3">每季度</option>
                        <option value="2">每两月</option>
                        <option value="1">每月</option>
                        <option value="10">每半月</option>
                        <option value="7">每周</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>检查标准：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea name="checkSpecification" data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 330px;height: 100px"></textarea>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>巡检合格结果：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <input type="text" name="itemAvailableResults" >
                </td>
            </tr>
            <tr id="checkItemTypeTr">
                <td class="tableTdRight"><span><label>巡检值类型：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <select name="type" class="form-control">
                        <option value="1">一般项</option>
                        <option value="2">供水端</option>
                        <option value="2">系统端</option>
                    </select>
                </td>
                <td class="tableTdRight"><span><label>单位：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <input type="text" name="unit" >
                </td>
            </tr>
            <tr id="checkItemValueTr">
                <td class="tableTdRight"><span><label>最大值：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <input type="text" name="maxValueFloat" >
                </td>
                <td class="tableTdRight"><span><label>最小值：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <input type="text" name="minValueFloat" >
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

<!-- 弹出 故障类型页面 开始-->
<div id="fault_type_search" class="easyui-dialog " data-options="closed:true,modal:true,title:'设备故障类型管理'" style="width:1300px;height:750px">
    <input type="hidden" id="deviceTypeIdHidden" name="deviceTypeId">
    <input type="hidden" id="deviceSysId6" name="parentId">
    <input type="hidden" id="deviceTypeType" name="deviceTypeType">
    <!--查询行 开始 -->
    <div id="faultTypeToolbarItem" fit="true">
        <form id="faultTypeSearch_item_form">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openDeviceFaultTypeSaveEdit();" >添加</a>
            <label>故障类型：</label><input type="text" id="vsSelFaultType" style='width:150px'/>
            <label>原因分析：</label><input type="text" id="vsSelCauseAnalysis" style='width:150px'/>
            <label>维修方式：</label><input type="text" id="vsSelRepairMethod" style='width:150px'/>

            <label>维护建议：</label><input type="text" id="vsSelRepairProposal" style='width:150px'/>
            <a href="javascript:void(0);" onclick="findDeviceFaultTypeList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="deviceFaultType_table" style="height:635px;"></table>
</div>
<!-- 弹出 故障类型页面 结束 -->

<!-- 故障类型 新增 修改 页面 begin-->
<div id="faultType_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'设备故障类型信息',iconCls:'icon-save'" style="padding:5px;width:800px;height:600px;">
    <form action="" id="deviceFaultTypeForm">
        <input type="hidden" name="deviceTypeId" id="saveDeviceTypeIdHidden">
        <input type="hidden" name="parentId" id="deviceSysId5"/>
        <input type="hidden" name="deviceTypeType" id="deviceTypeType1"/>
        <input type="hidden" name="id" id="deviceFaultTypeIdHid"/>
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>故障类型：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea name="faultType" data-options="required:true,validType:'length[0,100]'" class="z_textarea" style="width: 330px;height: 80px"/>
                </td>
            </tr>

            <tr>
                <td class="tableTdRight"><span><label>事件类型：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <select name="faultEventType" class="form-control">
                        <option value="1">默认事件</option>
                        <option value="2">水压监测事件</option>
                        <option value="3">智能水浸事件</option>
                        <option value="4">火警主机事件</option>
                        <option value="5">极早期预警事件</option>
                        <option value="6">火眼报警事件</option>
                        <option value="7">声光报警事件</option>
                        <option value="8">智能消火栓事件</option>
                        <option value="9">可然气事件</option>
                        <option value="10">电气火灾事件</option>
                        <option value="11">智能烟感事件</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>排序：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <input name="sort" style="width: 100px"/>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>颜色：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <input name="faultColor" style="width: 330px"/>
                </td>
            </tr>

            <tr>
                <td class="tableTdRight"><span><label>维修方式：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea name="repairMethod" data-options="required:true,validType:'length[0,100]'" class="z_textarea" style="width: 330px;height: 80px"/>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>原因分析：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea name="causeAnalysis" data-options="required:true,validType:'length[0,100]'" class="z_textarea" style="width: 330px;height: 80px"/>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>维护建议：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea name="repairProposal" data-options="required:true,validType:'length[0,100]'" class="z_textarea" style="width: 330px;height: 80px"/>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 故障类型 新增 修改 页面 end-->

</html>