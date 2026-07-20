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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训班列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">选班情况 </span>
			</li>
			<!--<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="dep_course_list.action?department.id=<s:property value="department.id" />">选课情况</a>
			</li>
		--></ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;text-align: center;">
			<s:if test="classes.size==0">没有培训班列表</s:if>
			<s:else>
			<table width="85%" cellpadding="2" cellspacing="1" bgcolor="#EBEBEB">
				<tr>
					<th align="center" >
						课程名称
					</th>
					<th align="center" >
						创建者
					</th>
					<th align="center" >
						创建时间
					</th>
					<th align="center" >
						必修课数
					</th>
					<th align="center" >
						选修课数/总学分/至少修学分
					</th>
					<th align="center" >
						查看获证排行榜
					</th>
				</tr>
				<s:iterator value="classes">
					<tr>
						<td align="center" >
							<s:property value="name" />
						</td>
						<td align="center" >
							<s:property value="creater.realname" />
						</td>
						<td align="center" >
							<s:date name="createtime" format="yyyy-MM-dd"/> 
						</td>
						<td align="center" >
							<s:property value="bxCount"/>
						</td>
						<td align="center" >
							<s:property value="xxCount"/>/<s:property value="xxCredit"/>/
							<s:property value="optionalcredit"/>
						</td>
						<td align="center" >
						<a href="dep_class_view.action?elclass.id=<s:property value="id"/>&department.id=<s:property value="department.id"/>">查看获证排行榜</a>
						</td>
					</tr>
				</s:iterator>
			</table>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
