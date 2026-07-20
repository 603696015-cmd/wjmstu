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
				<span style="font-weight: bold;">我要选课</span>

			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; margin-left: 200px;">
			<div>
				<br>
				<br>
				<form action="listCanAppalyCourse.action" method="post"
					name="course_can_assignment_list" id="course_can_assignment_list">
					<input type="hidden" name="operate_search" value="ok">
					<label>
						课程来源
						<select name="course_sourse" id="course_sourse">
							<option value="1" id="course_sourse_0">
								上级分配的课程
							</option>
							<option value="2" selected="selected" id="course_sourse_0">
								本部门范围创建的课程
							</option>
						</select>
						课程名称
						<input type="text" name="course.name" id="course_name" value="">
						<input type="hidden" name="pN" value="0">
						<input type="hidden" name="pS" value="10">
						<input type="submit" value="搜 搜 看">
					</label>
				</form>
			</div>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
