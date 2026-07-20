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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>产品所属栏目管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/system.css" />
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="<%=path %>/js/message.js"></script>
	</HEAD>
	<body>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="所属栏目树" /></div>
			</li>
			
		</ul>
 
		<!-- 内容 -->
		<div style="margin-top: 20px;margin-left: 100px;"> 
		 	<wysLib:policyTypeTree href="policyLib_view.action?ptype.id=" rootAble="true"></wysLib:policyTypeTree>
		</div>
		
		<div style="margin-top: 15px; margin-left: 80px;">
			<a href="policyLib_addInit.action" class=textbg>添加所属栏目</a>
		</div>
		<!-- 内容 -->

	</BODY>
</HTML>
