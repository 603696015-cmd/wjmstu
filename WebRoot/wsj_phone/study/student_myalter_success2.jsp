
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
				<span style="font-weight: bold;">修改设置</span>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 40px;">
			<div style="margin-top: 40px; text-align: center;">
				个人信息修改成功
				<br>
				<a href="student_myinfo2.action">返回个人信息 </a>
			</div>

		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
