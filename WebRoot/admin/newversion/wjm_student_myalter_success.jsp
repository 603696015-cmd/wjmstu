
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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		
		<script type="text/javascript" src="js/message.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<!--<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="修改成功" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">修改设置</span>
			</li>
		</ul>-->
		<!-- 内容 -->
		<div style="margin-top: 60px;">
			<div style="margin-top: 40px; text-align: center;">
				<p style="font-size:30px;color:white;">个人信息修改成功</p>
				<p>&nbsp;</p>
				<p>				  <br>
			      <a href="wjm_student_myinfo.action"><span style="font-size:40px;color:red;font-weight:bold;">返回个人信息</span> </a>
			            </p>
			</div>

		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
