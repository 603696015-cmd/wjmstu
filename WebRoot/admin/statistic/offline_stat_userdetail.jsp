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
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="线下培训详情" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">活动详情</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="offline_stat_detail.action?offline.id=<s:property value="offline.id" />">活动概况</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px">
			<s:property value="elmessage" />
			<table cellspacing=1 cellpadding=2 width="700px;" align=center
				bgcolor=#ebebeb>
				<tbody>
					<tr>
						<td align=center bgcolor=#ffffff>
							活动名称
						</td>
						<td align=center bgcolor=#ffffff colspan="3">
							<s:property value="offline.name" />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							活动简介
						</td>
						<td align=center bgcolor=#ffffff colspan="3">
							<s:property value="offline.description" />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							开始时间
						</td>
						<td align=center bgcolor=#ffffff>
							<s:date name="offline.begintime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td align=center bgcolor=#ffffff>
							结束时间
						</td>
						<td align=center bgcolor=#ffffff>
							<s:date name="offline.endtime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							时长
						</td>
						<td align=center bgcolor=#ffffff colspan="3">
							<s:property value="offline.during" />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							学时
						</td>
						<td align=center bgcolor=#ffffff colspan="3">
							<s:property value="offline.xueshi" />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							学分
						</td>
						<td align=center bgcolor=#ffffff colspan="3">
							<s:property value="offline.score" />
						</td>
					</tr>
					<tr>
						<td align=center width="100px" bgcolor=#ffffff>
							参与人员
						</td>
						<td align=center bgcolor=#ffffff colspan="3">
							<s:property value="offline.usercount" />
						</td>
					</tr>
				</tbody>
			</table>
			<table cellspacing=1 cellpadding=2 width="700px;" align=center
				bgcolor=#ebebeb>
				<tbody>
					<tr>
						<td align=center bgcolor=#ffffff>
						</td>
						<td align=center bgcolor=#ffffff>
							学员姓名/账号
						</td>
						<td align=center bgcolor=#ffffff>
							部门
						</td>
						<td align=center bgcolor=#ffffff>
							性别
						</td>
						<td align=center bgcolor=#ffffff>
							学分
						</td>
					</tr>
					<s:iterator value="elUsers" status="xx">
						<tr>
							<td align=center bgcolor=#ffffff>
								<s:property value="#xx.index+1" /> 
							</td>
							<td align=center bgcolor=#ffffff>
								<s:property value="realname" />/<s:property value="username" />
							</td>
							<td align=center bgcolor=#ffffff>
								<s:property value="department.name" />
							</td>
							<td align=center bgcolor=#ffffff>
								<s:property value="sex" />
							</td>
							<td align=center bgcolor=#ffffff>
								<s:property value="offline.score" />
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
