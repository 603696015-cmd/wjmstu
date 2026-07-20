<%@ page language="java" pageEncoding="UTF-8"  contentType="text/html; charset=utf-8"%>
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
		<TITLE>图书类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="全部类别概览" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">课程类别管理</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="coursetype_addInit.action">添加课程新类别</a>
			</li>-->
			</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; margin-left: 10px;">
		
		<wysLib:testbooktypeTree itype="OP" did="0" rootAble="true" href="bookType_view.action?btype.id="></wysLib:testbooktypeTree>
			
		</div>
		
		<div style="margin-top: 25px; margin-left: 10px;">
			<a href="bookType_addInit.action" class="textbg">添加类别</a>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
