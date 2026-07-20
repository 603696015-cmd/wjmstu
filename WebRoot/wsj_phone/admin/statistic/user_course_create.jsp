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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">开课情况</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="course_user_list.action?course.id=<s:property value="course.id"/>">学员列表</a>

			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<table width="95%" align="center" cellpadding="2" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
					<td rowspan=2 align="center" >
						课程名称
					</td>
					<td rowspan=2 align="center" >
						课程类型
					</td>
					<td rowspan=2 align="center" >
						课程学分
					</td>
					<td rowspan=2 align="center" >
						创建时间
					</td>
					<td colspan=3 align="center" >
						学员人数
					</td>
				</tr>
				<tr>
					<td align="center" >
						在学
					</td>
					<td align="center" >
						完成
					</td>
					<td align="center" >
						总数
					</td>
				</tr>
				<s:iterator value="courses">
					<tr>
						<td align="center" >
							<s:property value="name" />
						</td>
						<td align="center" >
							<s:property value="ctype.name" />
						</td>
						<td align="center" >
							<s:property value="credit" />
						</td>
						<td align="center" >
							<s:property value="createtime" />
						</td>
						<td align="center" >
						<s:property value="userCount-userPassedCount"/>
						</td>
						<td align="center" >
						<s:property value="userPassedCount"/>
						</td>
						<td align="center" >
						<s:property value="userCount"/>
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
