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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="模考成绩排行榜" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考试概况</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="90%" align="center" cellpadding="2" cellspacing="2"
				bgcolor="#EBEBEB">
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						排名
					</td>
					<td height="30" align="center" >
						姓名
					</td>
					<td height="30" align="center" >
						性别
					</td>
					<td height="30" align="center" >
						学号
					</td>
					<td height="30" align="center" >
						编号
					</td>
					<td height="30" align="center" >
						考试时间
					</td>
					<td height="30" align="center" >
						百分制成绩
					</td>
					<td height="30" align="center" >
						是否及格
					</td>
					<td height="30" align="center" >
						查看考卷
					</td>
				</tr>
				<s:iterator value="examPaper.meps">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							1
						</td>
						<td height="30" align="center" >
							<s:property value="tester.realname" />
						</td>
						<td height="30" align="center" >
						</td>
						<td height="30" align="center" >
						</td>
						<td height="30" align="center" ></td>
						<td height="30" align="center" >
						</td>
						<td height="30" align="center" >
						</td>
						<td height="30" align="center" >
							不及格
						</td>
						<td height="30" align="center" >
							<a href=" " target=_blank>查看</a>
						</td>
					</tr>
				</s:iterator>
			</table>

		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
