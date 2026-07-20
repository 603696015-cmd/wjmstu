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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考场列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">最近考试</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;text-align: center;">
			<s:if test="myCourses.size==0">您最近没有考试</s:if>
			 	<table width="90%" align="center" cellpadding="2" cellspacing="2"
					bgcolor="#EBEBEB">
					<caption>
						最近考试
			</caption>
						<tr>
							<!--<th height="30" align="center" >
								课程名称
							</th>
							<th height="30" align="center" >
								课程类型
							</th>
							--><th height="30" align="center" >
								考场名
							</th>
							<!--<th height="30" align="center" >
								地点
							</th>
							<th height="30" align="center" >
							监考老师
							</th>
							--><th height="30" align="center" >
								考试开始时间
							</th>
							<th height="30" align="center" >
								考试结束时间
							</th>
							<th height="30" align="center" >
								进入
							</th><th height="30" align="center" >
								<a href="myrecentquiz_list.action?pN=0&pS=10"><b>更多</b></a>
							</th>
							 </tr>
						<s:iterator value="myCourses">
							<tr>
								<!--<td height="30" align="center" >
									<s:property value="course.name" />
								</td>
								<td height="30" align="center" >
									<s:property value="statusName" />
								</td>
								--><td height="30" align="center" >
									<s:property value="examRoom.title" />
								</td>
								<!--<td height="30" align="center" >
									<s:property value="examRoom.location" />
								</td>
								<td height="30" align="center" >
									<s:property value="examRoom.supervisor.realname" />
								</td>
								--><td height="30" align="center" >
										<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								 <td height="30" align="center" >
										<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm:ss" />
									
								</td>
								<td height="30" colspan="2" align="center" >
									<a
										href='javascript: void(window.open("quizpaper.action?examRoom.id=<s:property value="examRoom.id"/>","course_exam_5","toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no"))'>进入考试</a>
								</td>
								 </tr>
						</s:iterator>
					</table>
					<wysLib:page></wysLib:page>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
