<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>五矿发展员工职业发展系统</title>
		<base href="<%=basePath%>" />
		<META content="MSHTML 6.00.2900.5897" name=GENERATOR>
		<LINK href="elfrontimages/style__.css" rel=stylesheet type="text/css">
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css"> 
		<style type="text/css">
body {
	background-color: #FFFFFF;
}

.STYLE2 {
	color: #FF0000
}

.STYLE5 {
	color: #ed7b0f;
	font-weight: bold;
}
.textbox{
	border: solid 1 #000000;
}
</style>
	</HEAD>
	<BODY>
		<%@include file="/elfrontman/frontheader.jsp"%>
		  
		  
		  
		 <%@include file="/elfrontman/frontbottom.jsp"%> 
	</BODY>
</HTML>

