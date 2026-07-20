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
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="添加成功" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">添加试题</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<div style="margin-top: 40px; text-align: center;">
				添加试题成功
				<br>
				<a href="question_listInit.action">返回管理试题 </a>
				<br>
				<a href="question_addInit.action?question.qlib.id=<s:property value="question.qlib.id"/>&question.qtype=<s:property value="question.qtype"/>">返回继续添加  </a>
				<br>
				<br>
				<s:if test="question.parent.id!=0">
					<a
						href="question_view.action?question.id=<s:property value="question.parent.id"/>">返回当前材料题
					</a>				</s:if>
			</div>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
