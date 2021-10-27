<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName()+":" + request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>消检通后台管理系统</title>
    <link rel="stylesheet" type="text/css" href="/resource/css/common.css" />
    <link rel="stylesheet" type="text/css" href="/resource/css/login.css">
    <link rel="stylesheet" href="/resource/js/jqueryeui-1.3.6/themes/default/easyui.css" />
    <script>
        var partnerFlag = true;
    </script>
</head>
<body>
<form id="loginForm" method="post" class="loginForm" action="<%=basePath%>login">
    <div class="container">
        <div class="logP">
            <div class="inn">
                <ul class="dlC">
                    <li class="dl01">
                        <div class="d_input">
                            <input type="text" class="inputC" title="请输账号" id="userMail" name="loginName" size="100" value=""/>
                            <label class="label">请输账号</label>
                        </div>
                    </li>
                    <li class="dl02">
                        <div class="d_input">
                            <input type="password" class="inputC" title="请输入密码" id="userPwd" name="password"  size="100" value=""/>
                            <label class="label">请输入密码</label>
                        </div>
                        <p class="l_ts">${loginInfo.msg}</p>
                    </li>
                    <li class="dl03"><a href="javascript:void(0)" class="an_dl" id="submit_login">登录</a></li>
                </ul>
            </div>
        </div>
        <div class="footer">
            <p>让消防更智慧 让社会更安全</p>
            <p>Let Fire Fighting Wisdom Make Society Safer</p>
        </div>
    </div>
</form>
</body>
</html>
<script type="text/javascript" src="/resource/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/resource/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resource/views/user_login.js?v=31"></script>
<script>
    $(function() {
        $(".dlC .inputC").val('');
        $(".dlC .inputC").focus(function() {
            var $len = $(this).length;
            if ($len > 0) {
                $(this).next(".label").css("display", "none");
            } else {
                $(this).next(".label").css("display", "block");
            }

        });
        $(".dlC .inputC").blur(function() {
            var value = $(this).val();
            if (value.length == 0) {
                $(this).next(".label").css("display", "block");
            }
        });
        $(".dlC .label").click(function() {
            $(this).prev(".dlC .inputC").focus();
        });
    })

    var jq = jQuery;
    function showForget(){
        jq.createWin({
            title:"忘记密码",
            url:'forget.html',
            height:280,
            width:540,
            buttons:[],
            onClose:function(targetjq){
            },
            onComplete:function(dailog,targetjq){
            }
        });
    }
</script>
<script>
    $(function(){
        locationCenter();
    });
    $(window).resize(function() {
        locationCenter();
    });

    function locationCenter(){
        if(self.frameElement != null && self.frameElement.tagName=="IFRAME"){
            window.parent.location = "sysLogin/login.html";
        }
        var windowobj = $(window);
        var browserheight = windowobj.height();
        var	scrollTop = windowobj.scrollTop();
        $("#container").height(browserheight);
        $("#content").css("top",(browserheight-270)/2-51);
    }
    /*
     *
     */

</script>