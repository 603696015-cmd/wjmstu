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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="知识搜索" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">知识管理</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<form action="knowledge_list.action">
				部门：
				<select name="dep.id">
					<wysLib:dep_select></wysLib:dep_select>
				</select>
				<br>
				<br>
				包含下级部门
				<input type="checkbox" name="subdep" value="1">
				<br>
				<br>
				<s:hidden name="pN" value="0"></s:hidden>
				<s:hidden name="pS" value="10"></s:hidden>
				<input type="hidden" name="kltype.id" value="-1">
				<input type="submit" value="查看">
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
