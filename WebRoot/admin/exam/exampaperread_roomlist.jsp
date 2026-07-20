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
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考场列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">试卷评阅 </span>
			</li>
			<li class="sep">
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="96%" align="center" cellspacing="2">
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						考场标题
					</td>
					<td height="30" align="center" >
						考场地点
					</td>
					<td height="30" align="center" >
						考场开始时间
					</td>
					<td height="30" align="center" >
						考场结束时间
					</td>
					<td height="30" align="center" >&nbsp;
						
					</td>
				</tr>
				<s:iterator value="examRooms">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							<s:property value="title" />
						</td>
						<td height="30" align="center" >
							<s:property value="location" />
						</td>
						<td height="30" align="center" >
							<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td height="30" align="center" >
							<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td height="30" align="center" >
							<a
								href="exampaperreadlist.action?examRoom.id=<s:property value="id"/>&course.id=${ course.id}">试卷列表</a>
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
