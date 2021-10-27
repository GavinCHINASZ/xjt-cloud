<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>消检通后台管理系统</title>
    <link rel="stylesheet" type="text/css" href="/resource/css/common.css" />
    <link rel="stylesheet" type="text/css" href="/resource/css/login.css">
    <link rel="stylesheet" href="/resource/js/jqueryeui-1.3.6/themes/default/easyui.css" />
</head>
<body>
    <div class="container">
        <div class="logP">
            <div class="inn">
                <ul class="dlC">
                    <li class="dl01">
                        <div class="d_input">
                            <label class="label">请重新登录</label>
                        </div>
                    </li>
                    <li class="dl03"><a href="javascript:void(0)" onclick="backLogin()" class="an_dl" id="submit_login">登录</a></li>
                </ul>
            </div>
        </div>
    </div>
</body>
</html>
<script type="text/javascript" src="/resource/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/resource/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resource/views/user_login.js?v=31"></script>
<script>
    function backLogin(){
        window.parent.location = "/index.jsp";
    }
</script>