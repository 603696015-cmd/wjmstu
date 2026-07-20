
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
				<span style="font-weight: bold;">我的模考成绩</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:if test="myExamPapers.size==0">无必修课模考</s:if>
			<s:else>
				<table width="96%" align="center" cellspacing="1">
					<caption>
						必修课模拟考试
					</caption>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							试卷名称
						</td>
						<td height="30" align="center" >
							课程名称
						</td>
						<td height="30" align="center" >
							交卷时间
						</td>
						<td height="30" align="center" >
							考试成绩
						</td>
						<td height="30" align="center" >
							试卷状态
						</td>
						<td height="30" align="center" >
							是否及格
						</td>
						<td height="30" align="center" >
							查看答卷
						</td>
					</tr>
					<s:iterator value="myExamPapers">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
								<s:property value="examPaper.title" />
							</td>
							<td height="30" align="center" >
								<s:property value="course.name" />(<s:property value="myCourse.statusName" />)
							</td>
							<td height="30" align="center" >
								<s:date format="yyyy-MM-dd" name="endtime" />
							</td>
							<td height="30" align="center" >
								<s:property value="myScore" />
							</td>
							<td height="30" align="center" >
								<s:property value="statusName" />
							</td>
							<td align="center" >
								<s:if test="passed">及格</s:if>
								<s:else>不及格</s:else>
							</td>
							<td height="30" align="center" >
								<a
									href="mysimpaperview.action?course.id=<s:property value="course.id"/>&examPaper.id=<s:property value="examPaper.id"/>"
									target="_blank">查看试卷</a>
							</td>
						</tr>
					</s:iterator>
				</table>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
