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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="试卷列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考试统计</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<br>
			<br>
			单位：
			<s:property value="company.name" />
			<br>
			部门：
			<s:property value="department.name" />
			（
			<s:if test="sub_department==1">包含子部门</s:if>
			<s:else>不包含子部门</s:else>
			）
			<br>
			<br>
			<table width="90%" align="center" cellpadding="2" cellspacing="2"
				bgcolor="#EBEBEB">
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						模拟试卷名称
					</td>
					<td height="30" align="center" >
						所属科目
					</td>
					<td height="30" align="center" >
						任课老师
					</td>
					<td height="30" align="center" >&nbsp;
						
					</td>
					<td height="30" align="center" >&nbsp;
						
					</td>
				</tr>
				<s:iterator value="examPapers">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							<s:property value="title" />
						</td>
						<td height="30" align="center" >
							<s:property value="course.name" />
						</td>
						<td height="30" align="center" >
							<s:property value="course.creater.realname" />
						</td>
						<td height="30" align="center" >
							<a
								href="sim_stat_view.action?examPaper.id=<s:property value="id"/>&examPaper.course.id=<s:property value="course.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">查看概况</a>
						</td>
						<td height="30" align="center" >
							<a
								href="sim_detail_view.action?examPaper.id=<s:property value="id"/>&examPaper.course.id=<s:property value="course.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">查看详情</a>
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
