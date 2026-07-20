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
	<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="试卷搜索" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">管理试卷</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<form action="exampaper_list.action" method="post" name="papers_info"
				id="papers_info">
				<table cellpadding="1"  cellspacing="1" border="1" width="500px;" align="center">
					<tr>
						<td>
							试卷名称关键字
						</td>
						<td>
							<input type="text" name="examPaper.title" id="name">
						</td>
					</tr>
					<tr>
						<td>
							所属试卷库
						</td>
						<td>
							<wysLib:elibtree iname="examPaper.epl.id" itype="ra" did="1"></wysLib:elibtree>
						</td>
					</tr>
					<tr>
						<td>
							包含子试卷库
						</td>
						<td>
							<input type="checkbox" name="sublibs" id="sublibs" value="1" checked="checked">
						</td>
					</tr>
					<tr>
						<td>&nbsp;
						</td>
						<td>
							<input type="hidden" name="pN" value="0">
							<input type="hidden" name="pS" value="10">
							<input class=textbg6 style="height:35px;" type="submit" value="搜 索">
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
