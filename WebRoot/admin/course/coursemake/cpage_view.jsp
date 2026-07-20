<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>章节</TITLE>
		<LINK href="css/bofang2.css" type=text/css rel=stylesheet>
	</HEAD>
	<BODY style="background: #dae9fe;">
			<h2 style="width: 100%; text-align: center;">
				<s:property value="coursePage.title" />
			</h2>
			<br>
			${coursePage.page }
	</BODY>
</HTML>
