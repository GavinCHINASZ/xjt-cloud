<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="globalJspInclude.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>消检通后台管理系统</title>
	<%@ include file="globalCssLink.jsp"%>
	<script type="text/javascript">
		var jsonStr = ${userInfo};
		var userInfo = jsonStr.object;
		var access_token = "access_token="+ userInfo.access_token;
		var loginName = userInfo.loginName;
		$(document).ready(function(){
			$(this).keydown( function(e) {
			});

			$("#userListUl li ul li a").click(function(){
				if(document.getElementById('currentA'))
					document.getElementById('currentA').id = '';
				this.id = 'currentA';
			});

			//var main_height = document.body.clientHeight - 56 + 'px';
			//var content_width = document.body.clientWidth - 204 + 'px';

			//$("#body").css("min-height", document.body.clientHeight);
			//$("#body").css("min-width", document.body.clientWidth);
			//$("#main").css("height",main_height);
			//$("#content").css("width",content_width);
			//$("#menu").css("min-height", main_height);
		});

		//
		function loadListPage(url){
			$(".easyui-dialog").dialog('destroy');
			$.ajax({
				url:url + "?" + access_token,
				type:'post',
				cache:false,
				success:function(response) {
					$('#mainContent').panel({
						closable : true,
						content:response
						}
					);
				},
				error: function (err) {   //ajax请求失败时返回   返回的err是一个对象
					var data = $.parseJSON( err.responseText );
					if (err.responseText.indexOf("invalid_token)" != -1)){
						alert("登录过期，请重新登录！")
					}else{
						alert(data.msg);
					}
				}
			});
			//$('#mainContent').panel('refresh',url + "?" + access_token);
			return false;
		}

		$(document).ready(function(){
			document.onkeydown = check;
			function check(e) {
				var code;
				if (!e) var e = window.event;
				if (e.keyCode) code = e.keyCode;
				else if (e.which) code = e.which;

				if (((event.keyCode == 8) && ((event.srcElement.type != "text" && event.srcElement.type != "textarea" &&
						event.srcElement.type != "password") ||  event.srcElement.readOnly == true)))
				{
					event.keyCode = 0;
					event.returnValue = false;
				}

				return true;
			}
		});

		function unflod( obj ) {
			if(obj.parentNode.id == 'currentLi') {
				obj.parentNode.id = ''
			}else{
				if(document.getElementById('currentLi')) {
					var currentLi = document.getElementById('currentLi');
					currentLi.id = '';
				}

				obj.parentNode.id = 'currentLi';
			}

			if(event.stopPropagation) {
				event.stopPropagation();//火狐浏览器
			}else if(window.event) {
				window.event.cancelBubble = true;//IE浏览器
			}
		}

		function userLogout() {
			window.location.href = "/userLogout?loginName=" + loginName + "&" + access_token;
		}
	</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div  data-options="region:'north'" style="height:60px;">
			<input type="hidden" id="userInfoHid" value="${userInfo}">
			<div id="top">
				<table width="100%">
					<tr><td><h2>消检通后台管理系统</h2></td>
						<td align="right"><a href="#" onclick="loadListPage('<%=rootPath%>/user/toModifyPasswordPage')">修改密码</a>
							&nbsp;|&nbsp;
							<a  href="#" onclick="userLogout()">注销</a></td>
						<td width="20"></td>
					</tr>
				</table>
			</div>
		</div>

		<div data-options="region:'west',split:true" style="width:200px;top:70px;">
			<div id="menu" data-options="fit:true">
				<div id="menuTitle"><b>系&nbsp;统&nbsp;目&nbsp;录</b></div>

				<div id="userListDiv">
					<ul id="userListUl">
						<c:forEach items="${menuMap}" var="menu">
							<c:set var="permission" value='${menu.key}' />
							<c:set var="list" value='${menu.value}' />
							<li style="padding:2px;">
								<a onclick="unflod(this,event)"><span>${permission.pathName}</span></a>
								<ul>
									<c:forEach items="${list}" var="url">
										<li> <a href="javascript: void(0);" onclick="loadListPage('<%=rootPath%>${url.url}');">${url.pathName}</a> </li>
									</c:forEach>
								</ul>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
		<div id="mainContent" region="center"  style="overflow: hidden;" border="false" data-options="closable:true">
		</div>
		<!-- 下边版权区 -->
		<div region="south" style="height: 30px; text-align:center; line-height: 30px; color:#000000;" border="false">
			版权所有 © 深圳消检通有限公司
		</div>
	</div>
</body>
</html>