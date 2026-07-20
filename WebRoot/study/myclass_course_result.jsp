
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="学习详情" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">培训班学习成绩 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="myelclass_view.action?elclass.id=<s:property value="elclass.id"/>">培训班详情</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<table align="center" width="90%" cellpadding="1" cellspacing="1">
				<caption>
					必修课
				</caption>
				<tr>
					<th>
						课程名称
					</th>
					<th>
						课程学分
					</th>
					<th>

						总时间/已学时间
					</th>
					<th>

						学习进度
					</th>
					<th>
						我的学时
					</th>
					<th>
						结业考试
					</th>
				</tr>
				<s:set name="btotalscore" value="0f"></s:set>
				<s:set name="btotalcredit" value="0.0f"></s:set>
				<s:iterator value="myClass.myCourseB">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							<s:property value="course.name" />
						</td>
						<td height="30" align="center" >
							<s:property value="course.credit" />
						</td>
						<td height="30" align="center" >
							<s:property value="course.during" />
							分钟 /
							<s:property value="passtime" />
							分钟
						</td>
						<td height="30" align="center" >
							<s:property value="processStr" />

							%
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
						</td>
					</tr>
				</s:iterator>
			</table>
			<table align="center" width="90%" cellpadding="1" cellspacing="1">
				<caption>
					选修课
				</caption>
				<tr>
					<th>
						课程名称
					</th>
					<th>
						课程学分
					</th>
					<th>

						总时间/已学时间
					</th>
					<th>

						学习进度
					</th>
					<th>
						我的学时
					</th>
					<th>
						结业考试
					</th>
				</tr>
				<s:set name="xtotalscore" value="0"></s:set>
				<s:set name="xtotalcredit" value="0"></s:set>
				<s:iterator value="myClass.myCourseX">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							<s:property value="course.name" />
						</td>
						<td height="30" align="center" >
							<s:property value="course.credit" />
						</td>
						<td height="30" align="center" >
							<s:property value="course.during" />
							分钟 /
							<s:property value="passtime" />
							分钟
						</td>
						<td height="30" align="center" >
							<s:property value="processStr" />

							%
						</td>
						<td height="30" align="center" >
							<s:property value="myCredit" />
						</td>
						<td height="30" align="center" >
							<s:if test="myExamPaper.status==3">
								<s:property value="myExamPaper.myScore" />
									<s:property value="myExamPaper.ispassed" />
							</s:if>
							<s:else>
								<s:property value="myExamPaper.statusName" />
							</s:else>
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
