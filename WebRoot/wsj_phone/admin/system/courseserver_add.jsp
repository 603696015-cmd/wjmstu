<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="depSelect" uri="/WEB-INF/wysLib.tld"%>
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
		<base href="<%=basePath%>">
		<title>课件服务器列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课件服务器设置" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">课件服务器添加</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="course_server_list.action?pageNow=0&pageSize=10">课件服务器管理</a>
			</li>-->
		</ul>
		<div class="operateLine"></div>
		<form action="course_server_add.action" method="post">
		<table width="50%" align="center" cellpadding="2" cellspacing="2"
			>
			<tr>
				<td height="20" width="100px" align="center" >
					服务器名
				</td>
				<td height="20" align="left" >
					<input type="text" name="cserver.name">
				</td>
			</tr>
			<tr>
				<td height="20" align="center" >
					说明
				</td>
				<td height="20" align="left" >
					<input type="text" name="cserver.description">
				</td>
			</tr>
			<tr>
				<td height="20" align="center" >
					地址/域名
				</td>
				<td height="20" align="left" >
					<input type="text" name="cserver.url">请正确填写(如：www.baidu.com或者192.168.1.1)
				</td>
			</tr>
			<tr>
				<td height="20" align="center" >
					 
				</td>
				<td height="20" align="left" >
					 <input type="submit"  value="提交">
				</td>
			</tr>
		</table>
		</form>
	
	</body>
</html>
