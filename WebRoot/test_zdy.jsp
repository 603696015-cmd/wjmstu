<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="zdyLib" uri="/WEB-INF/zdyLib.tld"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test_add.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery.js" ></script>
	<script type="text/javascript" src="js/zidingyipage.js" ></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <div>分页标签、搜索标签测试</div>
  	<zdyLib:zdypage lablename='新闻管理'   setnull='暂无数据'  switches='' include='true'  constraint='true'></zdyLib:zdypage>
  	<zdyLib:zidingyisearch lablename='新闻搜索' setnull='暂无数据' ></zdyLib:zidingyisearch>
  	<zdyLib:zdypage lablename='5555'   setnull='暂无数据'  switches='' include=''  constraint=''></zdyLib:zdypage>
  </body>
</html>
