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
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="搜索试卷" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">模考统计</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<form action="sim_searchlist.action" method="post"
				name="department_info" id="department_info">
				<table width="90%" border="0" align="center" cellpadding="2"
					cellspacing="2" >
					<tr>
						<td width="160" height="50" align="center" >
							试卷关键字：
						</td>
						<td >
							<input type="hidden" name="operate_search" value="ok">
							<label>
								<input name="examRoom.title" type="text" value="" size="54">
							</label>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td width="160" height="50" align="center" >
							时间范围：
						</td>
						<td >
							<label>
								&nbsp;&nbsp;&nbsp;从
								<input class="Wdate" name="examRoom.begintime" type="text"
									onclick="setday(this)" />
								&nbsp;&nbsp;&nbsp;到
								<input class="Wdate" name="examRoom.endtime" type="text"
									onclick="setday(this)" />
							</label>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td height="50" align="center" >
							部门范围：
						</td>
						<td >
							<label>
								单位
								&nbsp;&nbsp;&nbsp;部门
								<select name="department.id" id="parentid">
									<wysLib:dep_select />
								</select>
							</label>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td height="50" align="center" >
							是否包含：
						</td>
						<td >
							<label>
								包含下属部门
								<input type="checkbox" name="sub_department" id="sub_department"
									value="1">
							</label>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td width="160" height="50" align="center" >&nbsp;
							
						</td>
						<td >
							<input type="submit" value="搜索">
							&nbsp;
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
