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
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>险种管理</title>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>  
		<style type="text/css"> 
		td {font-size:12px;color:#333333;line-height:150%}
		tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style> 
	</HEAD>
	<body>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="增加险种成功" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">增加险种成功</span>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 30px">
		 	增加险种成功！
		 	<br><a href="PG_IC_U_columns_manageInit.action?IC.id=<s:property value="IC.id"/>">编辑险种列信息</a>
		 	<br><a href="PG_IC_ListInit.action">返回险种列表页</a>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
