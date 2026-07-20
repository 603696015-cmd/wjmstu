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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="学分详情" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;"> 我的学分</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="myCourses.size==0">您没有学习完任何课</s:if>
			<s:else>
				<table width="90%" align="center" cellpadding="2" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<th height="30" align="center" >
						</th>
						<th height="30" align="center" >
							课程名称
						</th>
						<th height="30" align="center" >
							状态
						</th>
						<th height="30" align="center" >
							课程学分
						</th>
						<th height="30" align="center" >
							已获学分
						</th>
						<th height="30" align="center" >
							考试学分
						</th>
					</tr>
					<s:set name="totalscore" value="0.0f"></s:set>
					<s:set name="totalcredit" value="0.0f"></s:set>
					<s:set name="qtotalscore" value="0.0f"></s:set>
					<s:iterator value="myCourses" status="st">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
								<s:property value="#st.index+1" />
							</td>
							<td height="30" align="center" >
								<s:property value="course.name" />
							</td>
							<td height="30" align="center" >
								<s:property value="statusName" />
							</td>
							<td height="30" align="center" >
								<s:property value="course.credit" />
							</td>
							<td height="30" align="center" >
								<s:property value="myCredit" />
							</td>
							<td height="30" align="center" >
								<s:if test="myExamPaper.status==3">
									<s:property value="myExamPaper.myScore" />
								</s:if>
								<s:else>
									<s:property value="myExamPaper.statusName" />
								</s:else>
								<!--
							<s:set name="totalcredit" value="#totalcredit+course.credit"></s:set>
							<s:set name="totalscore" value="#totalscore+myCredit"></s:set>
							<s:set name="qtotalscore" value="#qtotalscore+quizScore"></s:set>
						-->
						</tr>
					</s:iterator>
					<tr>
						<td height="30" align="center" colspan="3" >
							合计
						</td>
						<td height="30" align="center" >
							<s:property value="#totalcredit" />
						</td>
						<td height="30" align="center" >
							<s:property value="#totalscore" />
						</td>
						<td height="30" align="center" >
						</td>
						<!--<td height="30" align="center" >
								 	<s:property value="#totalscore" />
							</td>
							 
							<td height="30" align="center" >
								 	<s:property value="#qtotalscore" />
							</td>
						-->
					</tr>
				</table>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
