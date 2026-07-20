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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="查看详情" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">模拟考试概况</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="90%" align="center" cellpadding="2" cellspacing="2"
				bgcolor="#EBEBEB">
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						考场名称
					</td>
					<td width="70%" align="center" >
						<s:property value="examPaper.title" />
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						所属科目
					</td>
					<td width="70%" align="center" >
						<s:property value="examPaper.course.name" />
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						任课老师
					</td>
					<td width="70%" align="center" >
						<s:property value="examPaper.course.creater.realname" />
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						当前查询部门
					</td>
					<td width="70%" align="center" >
						<s:property value="department.name" />
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						参考人数
					</td>
					<td width="70%" align="center" >
						<s:property value="examPaper.userCount" />
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						试卷及格分(百分制)
					</td>
					<!--<td width="70%" align="center" >
						<s:property value="examPaper.course.passgrade" />
					</td>
				--></tr>
			</table>
			<br>
			<br>

			<table width="90%" align="center" cellpadding="2" cellspacing="2"
				bgcolor="#EBEBEB">
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">&nbsp;
						
					</td>
					<td height="30" align="center" >
						姓名
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
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						最高分
					</td>
					<td height="30" align="center" >
						<s:property value="examPaper.maxMep.tester.realname" />
					</td>
					<td height="30" align="center" >
					</td>
					<td height="30" align="center" ></td>
					<td height="30" align="center" >
					</td>
					<td height="30" align="center" >
					</td>
					<td height="30" align="center" >
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						最低分
					</td>
					<td height="30" align="center" >
						<s:property value="examPaper.minMep.tester.realname" />
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
				</tr>

				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						平均分
					</td>
					<td height="30" colspan=7 align="center" >
						50
					</td>
				</tr>
			</table>
			<br>
			<br>

			<table width="90%" align="center" cellpadding="2" cellspacing="2"
				bgcolor="#EBEBEB">
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						分数段（百分制）
					</td>
					<td width="80%" align="center" >
						人数
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						0-9
					</td>
					<td width="80%" align="center" >
						0
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						10-19
					</td>
					<td width="80%" align="center" >
						0
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						20-29
					</td>
					<td width="80%" align="center" >
						0
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						30-39
					</td>
					<td width="80%" align="center" >
						0
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						40-49
					</td>
					<td width="80%" align="center" >
						0
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						50-59
					</td>
					<td width="80%" align="center" >
						1
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						60-69
					</td>
					<td width="80%" align="center" >
						0
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						70-79
					</td>
					<td width="80%" align="center" >
						0
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						80-89
					</td>
					<td width="80%" align="center" >
						0
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						90-100
					</td>
					<td width="80%" align="center" >
						0
					</td>
				</tr>
			</table>
			<br>
			<br>

		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
