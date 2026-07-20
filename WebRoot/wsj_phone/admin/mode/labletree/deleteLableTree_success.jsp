<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>中国食品安全培训网--管理端--删除提示信息</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<BODY style="height: 100%; width: 100%">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="删除成功" /></div>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			自定义标签类别删除成功
			<br>
			<br>
			<a href="list_lableTree.action">返回自定义标签类别管理</a>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
