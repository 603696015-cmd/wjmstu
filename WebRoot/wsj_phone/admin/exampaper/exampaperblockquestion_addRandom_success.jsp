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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<TITLE>课程类别管理</TITLE>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="添加成功" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">大题试题添加</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="exampaperblock_list.action?examPaper.id=<s:property value="epBlock.examPaper.id"/>">返回大题列表
				</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;text-align: center;">
			<label style="font-size: 16px; font-weight: bold;">
				当前试卷:
				<s:property value="epBlock.examPaper.title" />
				<br>
				当前大题:
				<s:property value="epBlock.title" />
				[
				<s:property value="epBlock.typeName" />
				]
			</label>
			<br>
			<br>
			大题添加设置成功
			<a href="exampaperblockquestion_list.action?epBlock.id=<s:property value="epBlock.id"/>" >返回大题设置列表</a>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
