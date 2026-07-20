<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>" target="_self">
		<TITLE>版主搜索</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<BODY style="height: 100%; width: 100%">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="搜索用户" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">搜索用户</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="forum_searchUserslist" method="post" theme="simple"
				name="department_info" id="department_info">
				<table border="0" width="100%" cellpadding="2" cellspacing="2"
					bgcolor="#EBEBEB">
					<tr>
						<td width="120" height="30" align="center" >
							所属部门：
						</td>
						<td >
							<label>
							<s:hidden name="pN" value="0" />
							<s:hidden name="pS" value="10" />
								<select style="width: 300px" name="department.id" id="parentid">
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
							<label>
								<input type="checkbox" name="sub_department" id="sub_department"
									value="1">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>用户名</strong>
						</td>
						<td >
							<label>
								<input type="text" name="elUser.username" id="username" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>姓名</strong>
						</td>
						<td >
							<label>
								<input type="text" name="elUser.realname" id="name" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>电子邮箱</strong>
						</td>
						<td >
							<label>
								<input type="text" name="elUser.email" id="email" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="50" align="center" >&nbsp;
							
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
