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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="笔记列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">笔记查看</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; margin-left: 10px;">
			<table border="0" width="600px" align="left" cellpadding="1"
				cellspacing="1" bgcolor="#EBEBEB">
				<caption>
					<s:property value="elUser.realname" />(<s:property value="elUser.username" />
					)
					在 课程中
					<s:property value="cnote.course.name" />
					做的笔记
				</caption>
				<tr>
					<td width="120" height="30" align="center" >
						<strong>笔记标题</strong>
					</td>
					<td >
						<label>
							<s:property value="cnote.title" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" >
						<strong>得分</strong>
					</td>
					<td >
						<label>
							<s:property value="cnote.score" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" >
						<strong>笔记内容</strong>
					</td>
					<td >
						<label>
							${cnote.content }
						</label>
					</td>
				</tr>
			</table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
