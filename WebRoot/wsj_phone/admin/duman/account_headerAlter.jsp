<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
	</HEAD>
	<BODY>
		<ul class="nav">
			<li>
				<span style="font-weight: bold;">头像照片</span>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 40px; margin-left: 300px;">
			<form action="account_headerAlter.action" enctype="multipart/form-data"
				method="post">
				<s:property value="elmessage" /><br>
				为<s:property value="elUser.realname" />上传的照片<br>
				照片上传：
				<input type="file" name="st">
				<br>
				<br>
				<input type="submit" value="照片上传">
				<br>
				<s:hidden name="elUser.id" />
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
