<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
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
		<TITLE>定损员管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/system.css" />
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/manage.css" />
		<script type="text/javascript" src="<%=path %>/js/message.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">定损员批量导入</span>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 40px; margin-left: 300px;">
			注意 ，系统当前支持导入excel文档，请注意文档格式需要正确！
			<br>
			<form action="damageMemberBatchImport.action" enctype="multipart/form-data"	method="post"> 
				选择需要导入的文档：
				<input type="file" name="batchImport">
				<br>
				<br>
				<input type="submit" value="导 入" class="textbg4">
				<br>
				<a href="<%=path %>/pfms/ziyuan/import_damageMembers.xls" class="textbg">用户格式下载</a>
				<div style="font-size:14px;font-weight:bold;color:red;line-height:50px;">${elmessage}</div>
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
