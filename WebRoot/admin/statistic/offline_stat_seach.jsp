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
		<script type="text/javascript" src="js/offline.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="搜索线下培训" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">活动统计分析</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px">
			<s:form action="offline_stat_seach" method="post"  theme="simple">
				<s:property value="elmessage" />
				<table cellspacing=1 cellpadding=2 width="70%" align=center
					bgcolor=#ebebeb>
					<tbody>
						<tr>
							<td align=center bgcolor=#ffffff>
								活动名称
							</td>
							<td align=center bgcolor=#ffffff colspan="3">
								<input type="text" id="off_name" name="offline.name" size="40" />
							</td>
						</tr>
						<tr>
							<td align=center bgcolor=#ffffff>
								开始时间
							</td>
							<td align=center bgcolor=#ffffff>
								<input id="off_begin" type="text" size="20"
									name="offline.begintime" onclick='setday(this)' />
							</td>
							<td align=center bgcolor=#ffffff>
								结束时间
							</td>
							<td align=center bgcolor=#ffffff>
								<input id="off_end" type="text" size="20" name="offline.endtime"
									onclick='setday(this)' />
							</td>
						</tr>
						<tr>
							<td align=center bgcolor=#ffffff colspan="4">
								<input type="submit" value="搜索">
								<br>
							</td>
						</tr>
					</tbody>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
