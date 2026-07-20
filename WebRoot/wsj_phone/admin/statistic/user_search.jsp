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
		<script type="text/javascript" src="js/message.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="用户搜索" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">学员统计</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="user_searchlist" method="post" theme="simple"
				name="department_info" id="department_info">
				<table border="0" width="100%" align="left" cellpadding="1"
					cellspacing="1" bgcolor="#EBEBEB">
					<tr>
						<td width="120" height="30" align="center" >
							所属部门：
						</td>
						<td >
							<label>
								<s:hidden name="pN" value="0" />
								<s:hidden name="pS" value="10" />
								&nbsp;<select style="width: 300px" name="department.id" id="parentid">
									<wysLib:dep_select />
								</select>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>包含下属部门</strong>
						</td>
						<td >
							&nbsp;<label>
								<input type="checkbox" name="sub_department" id="sub_department"
									value="1">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>学号</strong>
						</td>
						<td >
							&nbsp;<label>
								<input type="text" name="elUser.username" id="username" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>姓 名</strong>
						</td>
						<td >
							&nbsp;<label>
								<input type="text" name="elUser.realname" id="name" value="">
							</label>
						</td>
					</tr>
					<!--<tr>
						<td width="120" height="30" align="center" >
							<strong>电子邮箱</strong>
						</td>
						<td >
							<label>
								<input type="text" name="elUser.email" id="email" value="">
							</label>
						</td>
					</tr>
					-->
					<tr>
						<td width="120" height="50" align="center" >&nbsp;
							
						</td>
						<td >
							&nbsp;&nbsp;<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="50">
										<input type="submit" value="搜索" class="textbg6">
									</td>
									<td width="20">&nbsp;
										
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
