<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<div class="dh3">
			<div class="newpos"></div>
			<div class="newpos2">
				<a href="cltype_list.action">培训班类别管理</a>
				<a href="elclass_course.action">培训班类别添加</a>
				<span style="font-weight: bold;">管理培训班课程</span>
				<a
					href="elclass_course_credit.action?elclass.id=<s:property value="elclass.id"/>">培训班课程学分
				</a>
			</div>
		</div>
		<form action="elclass_applydelete.action" method="post">

			<input type="hidden" name="elclass.id" id="id" value="<s:property value="elclass.id"/>">
			<label>
				确认提交删除培训班 &nbsp;&nbsp;<s:property value="elclass.name"/>&nbsp;&nbsp;的申请，
				<br />
				<br />
				网站管理员在审核后将执行对该培训班的删除。
			</label>
			<br>
			<br>
			<br>
			<input type="submit" value="确认删除">
			&nbsp;&nbsp;&nbsp;
			<input type="button" value="返回" onclick="history.go(-1);">
		</form>
	</body>
</HTML>
