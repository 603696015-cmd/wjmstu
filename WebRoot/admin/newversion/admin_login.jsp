<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>商务汉语学习系统</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link href="css/20140416/login.css?v=20260731-text2" type="text/css" rel="stylesheet">
		<script type="text/javascript">
			function load(){
				if(window.name=='rightFrame' && window.parent && window.parent !== window) {
					window.parent.location.href='admin/newversion/wjm_user_center_login.jsp';
				}
				var username = document.getElementById("username");
				if(username) {
					username.focus();
				}
			}
	</script>
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	
	
	
	<style type="text/css">
<!--
body {
	margin: 0;
	padding: 0;
	border: 0;
}
-->
</style></HEAD>
<body onLoad="load();">
	<div class="teacher-login-canvas">
		<form id="teacher-login-form" name="myform" method="post" action="wjm_admin_login.action">
			<input type="hidden" name="ipAddress" id="ipAddress"/>
			<input type="hidden" name="myLogin.ipAddr" id="ipAddr" value="${requestScope.myLogin.ipAddr }" />

			<a class="teacher-login-close" href="javascript:window.opener=null;window.open('','_self');window.close();" aria-label="关闭登录页">
				<img src="images/20140416/kongtu.gif" alt="" />
			</a>
			<label class="teacher-login-label teacher-login-user-label" for="username">用户名</label>
			<input id="username" class="teacher-login-input teacher-login-username" type="text" maxLength="30" name="elUser.username" value="${elUser.username}" autocomplete="off" />
			<label class="teacher-login-label teacher-login-password-label" for="password">密码</label>
			<input id="password" class="teacher-login-input teacher-login-password" type="password" maxLength="30" name="elUser.password" autocomplete="off" />
			<input class="teacher-login-submit" type="submit" value="登录" name="submit" />
		</form>
	</div>

	
	<SCRIPT type="text/javascript">
			if("${elmessage}"!='null'&&"${elmessage}"!='')
				 alert("${elmessage}!");
		</SCRIPT>
		
		
		
</body>
</html>
