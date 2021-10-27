<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用户管理</title>
    <!-- 页面相关JS -->
    <script type="text/javascript" src="/resource/views/sys/modifyPass.js"></script>
</head>
<!-- 主页面 end -->

<!-- 新增修改页面 begin-->
<div  id="common_search" class="easyui-panel" fit="true">
    <form action="" id="userForm">
        <table>
            <tbody>
            <tr>
                <td class="tableTdRight"><span><label>旧密码：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" id="oldPasswordTxt" name="oldPassword" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>新密码：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" id="passwordTxt" name="password" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                </td>
            </tr>
            <tr>
                <td class="tableTdRight"><span><label>确认密码：</label></span></td>
                <td class="tableTdLeft">
                    <input type="text" id="passwordTxt2" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'">
                </td>
            </tr>
            </tbody>
        </table>
        <a href="javascript:void(0);" onclick="modifyPass()" class="easyui-linkbutton" data-options="iconCls:'icon-anchor'">确 定</a>
    </form>
</div>
<!-- 新增修改页面 end-->

</html>