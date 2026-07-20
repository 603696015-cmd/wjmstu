<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>">
		<TITLE>学习课程--<s:property value="course.name" /></TITLE>
	 	<STYLE type="text/css">
			body {
	font: 12px;
}
		
		</STYLE>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	<br>
		<br>
			<br>
		 <div style="width: 100%;text-align: center;font:11px;">笔记提交成功！<a href="javascript:closediv('noteadd')">关闭</a></div>
	
	</body>
</HTML>
