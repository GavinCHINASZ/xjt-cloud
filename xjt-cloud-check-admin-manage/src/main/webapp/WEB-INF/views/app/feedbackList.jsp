<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>问题反馈管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/sys/feedbackList.js"></script>

</head>
<!-- 主页面 begin -->
<div  id="feedback_search" class="easyui-panel" fit="true">
    <!--查询行 开始 -->
    <div id="feedbackToolbar" fit="true">
        <form id="feedbackSearch_form">
            <label>项目名称：</label><input type="text" id="projectNameTxt" style='width:150px'/>
            <label>用户名：</label><input type="text" id="createUserNameTxt" style='width:150px'/>
            <label>联系方式：</label><input type="text" id="userPhoneTxt" style='width:150px'/>
            <label>状态：</label>
            <select id="statusSel" class="form-control">
                <option value="">所有</option>
                <option value="1">未处理</option>
                <option value="2">跟进中</option>
                <option value="3">已修复</option>
                <option value="4">无法修复</option>
                <option value="5">不存在</option>
                <option value="99">已删除</option>
            </select>
            <a href="javascript:void(0);" onclick="findFeedbackList()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </form>
    </div>
    <table id="feedback_table" style="height: auto" fit="true"></table>
</div>
<!-- 主页面 end -->

<!-- 新增修改页面 begin-->
<div id="feedback_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'问题反馈信息',iconCls:'icon-save'" style="padding:5px;width:600px;height:800px;">
    <form action="" id="feedbackForm">
        <input type="hidden" name="id" id="feedbackId" />
        <input type="hidden" name="type" value="1" id="feedbackTypeHid" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>用户名：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="createUserName" readonly>
                </td>
                <td class="tableTdRight"><span><label>用户电话：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="userPhone" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>提问时间：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="createTime" readonly>
                </td>
                <td class="tableTdRight"><span><label>内容：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="content" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>项目名称：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="projectName" readonly>
                </td>
                <td class="tableTdRight"><span><label>状态：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="statusDesc" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>处理人：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="handleUserName" readonly>
                </td>
                <td class="tableTdRight"><span><label>处理时间：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" name="handleTime" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>备注：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <input type="text" name="memo" readonly>
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>图片：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <img src="" name="imgUrl1" id="imgUrl1" style="height: 200px;width: 200px"/>
                    <img src="" name="imgUrl2" id="imgUrl2" style="height: 200px;width: 200px"/><br>
                    <img src="" name="imgUrl3" id="imgUrl3" style="height: 200px;width: 200px"/>
                    <img src="" name="imgUrl4" id="imgUrl4" style="height: 200px;width: 200px"/>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

<!-- 新增修改页面 begin-->
<div id="feedback_memo_dialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'备注',iconCls:'icon-save'" style="padding:5px;width:500px;height:300px;">
    <form action="" id="feedbackMemoForm">
        <input type="hidden" name="id" id="feedbackMemoId" />
        <input type="hidden" name="status" id="feedbackStatusId" />
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>备注：</label></span></td>
                <td class="tableTdLeft" colspan="3">
                    <textarea id="feedbackMemoTxt" name="memo"  data-options="required:true,validType:'length[0,100]'"  class="z_textarea" style="width: 330px;height: 100px"></textarea>
                </td>
            </tr>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 新增修改页面 end-->

</html>