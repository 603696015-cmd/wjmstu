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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我的模考</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="myCourses.size==0">您没有模考</s:if>
			<s:else>
				<table width="90%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<caption>
						我的课程
					</caption>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							课程名称
						</td>
						<td height="30" align="center" >
							任课老师
						</td>
						<td height="30" align="center" >
							学习类型
						</td>
						<td height="30" align="center" >
							总时间/已学时间
						</td>
						<td height="30" align="center" >
							学习进度
						</td>
						<td height="30" align="center" >
							模考中心
						</td>
					</tr>
					<s:iterator value="myCourses">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
								<s:property value="course.name" />
							</td>
							<td height="30" align="center" >
								<s:property value="course.teacherName" />
							</td>
							<td height="30" align="center" >
								<s:property value="statusName" />
							</td>
							<td height="30" align="center" >
								<s:property value="course.during" />
								分钟 /
								<s:property value="passtime" />
								分钟
							</td>
							<td height="30" align="center" >
								<s:property value="processStr" />
								%
							</td>
							<td height="30" align="center" >
								<a target="_blank"
									href="simpaperlist.action?course.id=<s:property value="course.id"/>">模考</a>
							</td>
						</tr>
					</s:iterator>
				</table>
				<wysLib:page></wysLib:page>
			</s:else>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
