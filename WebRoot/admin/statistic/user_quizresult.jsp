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

				<span style="font-weight: bold;">考试统计</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
		<s:if test="myCourses.size==0">该学员必修课还没安排考试</s:if>
			<s:else>
				<table width="96%" align="center" cellspacing="1">
					<caption>
						必修课考试
					</caption>
					<tr>
						<td align="center" >
							课程名称
						</td>
						<td align="center" >
							任课老师
						</td>
						<td align="center" >
							学分
						</td>
						<!--<td align="center" >
							考场名称
						</td>
						--><td align="center" >
							考试时间
						</td>
						<td align="center" >
							考试成绩（百分制）
						</td>
						<td align="center" >
							是否及格
						</td>
						<!-- <td align="center" >
							学分
						</td>-->
						<td align="center" >
							查看答卷/改分
						</td>
					</tr>
					<s:iterator value="myCourses">
						<tr>
							<td align="center" >
								<s:property value="course.name" />
							</td>
							<td align="center" >
								<s:property value="course.creater.realname" />
							</td>
							<td align="center" >
								<s:property value="course.credit" />
							</td>

							<s:if test="myEps.size==0">
								<td align="center" bgcolor="#ECEDEB" colspan="6">
									<b> 该课程未安排考试</b>
								</td>
							</s:if>
							<s:iterator value="myEps">
								<!--<td>
									<s:property value="examRoom.title" />
								</td>
								--><s:if test="status==3">
									<td>
										<s:date format="yyyy-MM-dd HH:mm:ss" name="endtime" />
									</td>
									<td align="center" >
										<s:property value="myScore" />
									</td>
									<td align="center" >
										<s:if test="passed">及格</s:if>
										<s:else>不及格</s:else>
									</td>
									<!--<td align="center" >
										<s:property value="course.credit" />
									</td>
									 -<td align="center" >
									查看试卷 
									</td>-->
									<td>				<a href="quizpaper_view.action?elUser.id=${elUser.id }&examRoom.id=<s:property value="examRoom.id" />">查看试卷</a>
									/<a target="_blank"
									href="exampaperread.action?myExamPaper.tester.id=<s:property value="elUser.id"/>&myExamPaper.examRoom.id=<s:property value="examRoom.id"/>">改分</a>
								</td>
								</s:if>
								<s:else>
									<td align="center" bgcolor="#ECEDEB" colspan="6"
										style="padding: 0px">
										<b> 试卷未提交或未批改，请等待...</b>
									</td>
								</s:else>
							</s:iterator>
						</tr>
					</s:iterator>
				</table>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
