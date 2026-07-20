<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>"  />
		<title>预览同步章节--<s:property value="coursePage.title" />
		</title>
		<script type="text/javascript" src="js/_wys_menu.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<link href="css/_wys_menu.css" type=text/css rel=stylesheet />
		<style type="text/css">
			body {
				margin: 0px;
			}
		</style>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<iframe width="100%" style="width: 100%; height: 100%;" height="100%"
			frameborder="0" src="intoRoom.action?room.id=<s:property value="coursePage.room.id"/>">
		</iframe>
	</body>
</html>
