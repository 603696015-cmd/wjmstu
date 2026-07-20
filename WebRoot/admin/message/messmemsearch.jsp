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
		<title>学员列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="csses/member.css">
		<link rel="stylesheet" type="text/css" href="csses/common.css">
	</HEAD>

	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<div class="operateMenu">
			<div class="operate1">
				消息群发
			</div>
		</div>
	<div class="operateLine"></div>
		<s:form action="messMember_search.action" method="post" theme="simple">
		<table width="90%" align="center" cellpadding="2" cellspacing="2"
			>
			<tr>
				<td width="120" height="30" align="center" >
					<strong>所属部门</strong>
				</td>
				<td >
					<label>
						<select style="font-size:13px; width: 300px" name="dep.dep_id">
							<depSelect:depSelect></depSelect:depSelect>
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
						<input type="checkbox" name="sub_depart" value="1">
					</label>
				</td>
			</tr>
			<tr>
				<td width="120" height="30" align="center" >
					<strong>学号</strong>
				</td>
				<td >
					<label>
						<input type="text" name="mem.mem_un" value="">
					</label>
				</td>
			</tr>
			<tr>
				<td width="120" height="30" align="center" >
					<strong>姓名</strong>
				</td>
				<td >
					<label>
						<input type="text" name="mem.mem_name" value="">
					</label>
				</td>
			</tr>
			<tr>
				<td width="120" height="30" align="center" >
					&nbsp;
				</td>
				<td >
					<input type="submit" value="搜索">
				</td>
			</tr>
		</table>
		</s:form>
	</body>
</html>
