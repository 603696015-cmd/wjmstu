<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0042)http://www.dnzs123.com/dazi/online_zh.html -->
<HTML xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>" />
		<title>中国食品安全培训网</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<meta content="MSHTML 6.00.2900.5921" name=GENERATOR />
		<script type="text/javascript" src="js/jquery.js"></script>
		<link media=all href="css/online.css" type=text/css rel=stylesheet>
		<SCRIPT type="text/javascript">
			function close_(){
				window.parent.close();
			}
		</SCRIPT>
		<link media=all href="css/online.css" type=text/css rel=stylesheet>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
			<div>已成功提交该邮件题，关闭该页面，做其他试题吧！<a href="" onClick="close_();return false">关闭</a></div>		 
	
	</body>
</html>
