
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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="邮件列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">学习提示</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div>
			<br>
			<TABLE width="90%" align="center" cellspacing="1" cellpadding="1">
				<caption>
						最新消息
			</caption>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						标题
					</TD>
					<TD height="30" align="center" >
						发送时间
					</TD>
					<TD height="30" align="center" >
						发件人
					</TD>
					<TD height="30" align="center" >&nbsp;
						
					</TD>
				</TR>
				<s:iterator value="newMessage">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							<a href="mess_info.action?mess.mess_id=24&deleteType=1"><s:property
									value="mess_title" /> </a>
						</TD>
						<TD height="30" align="center" >
							<s:date name="mess_time" format="yyyy-MM-dd HH:mm:sss" />
						</TD>
						<TD height="30" align="center" >
							<s:property value="mess_from.realname" />
						</TD>
						<TD height="30" align="center" >
							<a href="mess_info.action?mess.mess_id=24&deleteType=1">点击查看</a>
						</TD>
					</TR>
				</s:iterator>
			</TABLE>
			<!--<br>
			<TABLE width="90%" align="center" cellspacing="2">
				<TR>
					<TD colspan=4 height="30" align="center" >
						最近的考试
					</TD>
					<TD height="30" align="center" >
					</TD>
				</TR>
				<TR>
					<TD colspan=5 height="30" align="center" >
						最近没有考试。
					</TD>
				</TR>
			</TABLE>
		--></div>
		<!-- 内容 -->
	</BODY>
</HTML>
