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
				<span style="font-weight: bold;">选课情况 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="dep_course_list.action?department.id=<s:property value="department.id" />">选课情况</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<table width="95%" cellpadding="2" cellspacing="2" bgcolor="#EBEBEB">
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						课程名称
					</td>
					<td height="30" align="center" >
						推荐学分
					</td>
					<td height="30" align="center" >
						创建者
					</td>
					<td height="30" align="center" >
						创建时间
					</td>
					<!--<td height="30" align="center" >
						及格分数(%)
					</td>
				--></tr>
				<s:iterator value="courses">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							<s:property value="name" />
						</td>
						<td height="30" align="center" >
							<s:property value="credit" />

						</td>
						<td height="30" align="center" >
							<s:property value="creater.realname" />
							<s:property value="creater.id" />
						</td>
						<td height="30" align="center" >
							<s:date name="createtime" format="yyyy年MM月dd日" />
						</td>
						<!--<td height="30" align="center" >
							<s:property value="passgrade" />
						</td>
					--></tr>
				</s:iterator>
			</table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
