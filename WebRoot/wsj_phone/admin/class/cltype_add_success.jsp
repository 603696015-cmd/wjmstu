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
		<TITLE>中国食品安全培训网--管理端--</TITLE>
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
			<!--<div class="newpos2">
				<a href="cltype_addInit.action">培训班类别添加</a> 
				<span style="font-weight: bold;">培训班类别修改</span> 
			</div>-->
		</div>
		<div style="margin-left: 200px; font-size: 13px; margin-top: 65px;">
			培训班类别添加成功！<br><br>

			<a href="cltype_list.action" class=textbg>返 回</a>
		</div>
	
	</body>
</HTML>
