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
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程基本信息" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">基本信息</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="course_user_list.action?course.id=<s:property value="course.id"/>">学员列表</a>

			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<table cellpadding="1" width="100%" cellspacing="1" >
				<tr>
					<td width="100" height="30" align="center" >
						课程名称：
					</td>
					<td style="padding-left:8px;">
						<label>
							<s:property value="course.name" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="100" height="30" align="center" >
						课程介绍：
					</td>
					<td style="padding-left:8px;">
						<label>
							<s:property value="course.description" />
						</label>
					</td>
				</tr>

				<tr>
					<td width="100" height="30" align="center" >
						所属类别：
					</td>
					<td style="padding-left:8px;">
						<s:property value="course.ctype.name" />
					</td>
				</tr>

				<tr>
					<td width="100" height="30" align="center" >
						推荐学分：
					</td>
					<td style="padding-left:8px;">
						<label>
							<s:property value="course.credit" />
						</label>
					</td>
				</tr><!--
				<tr>
					<td width="100" height="30" align="center" >
						通过成绩：
					</td>
					<td >
						<label>
							<s:property value="course.passgrade" />
							%
						</label>
					</td>
				</tr>

				--><tr>
					<td width="100" height="30" align="center" >
						课程状态：
					</td>
					<td style="padding-left:8px;">
						<s:property value="course.validName" />
					</td>
				</tr>
			</table>
			<a href="#" onClick="history.back(-1);return false;" class="textbg">返回</a>
	</div>
		<!-- 内容 -->
	</BODY>
</HTML>
