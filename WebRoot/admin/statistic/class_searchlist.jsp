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

<%@page import="java.net.URLDecoder"%><HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">部门统计</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; margin-left: 40px;">
			<s:form action="dep_class_view" method="post" theme="simple"
				name="department_info" id="department_info">
				<table border="0" width="60%" align="left" cellpadding="1"
					cellspacing="1" bgcolor="#EBEBEB">
					<tr>
						<%--
						<td width="120" height="30" align="center" >
							选择培训班：
						</td>
						<td >
							<label>

								<select style="width: 300px" name="elclass.id" id="parentid">
									 <s:iterator value="classes">
									 	<option value="<s:property value="id"/>"><s:property value="name"/></option>
									 </s:iterator>
								</select>
							</label>
						</td>
						--%>
						<td width="120" height="30" align="center" >
							培训班：
						</td>
						<td >
							<label>
								<s:textfield size="25" name="elClassName"/>
							</label>
						</td>
					</tr>
					<!--<tr>
						<td width="120" height="30" align="center" >
							选择部门：
						</td>
						<td >
							<label>

								<select style="width: 300px" name="department.id" id="parentid">
									 wysLib:dep_select  
								</select>
							</label>
						</td>
					</tr>
					--><tr>
						<td width="120" height="50" align="center" >
							&nbsp;
						</td>
						<td >
							<input type="submit" value="搜索">
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
