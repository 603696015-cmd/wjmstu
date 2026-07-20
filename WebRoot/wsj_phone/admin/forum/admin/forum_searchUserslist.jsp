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
		<base target="_self" href="<%=basePath%>">
		<TITLE>版主搜索</TITLE>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="用户列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">搜索用户</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
							<a href="forum_searchUsersInit.action">修改搜索条件</a>
			<form action="forum_searchUserslist.action" method="post" name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				<s:hidden name="sub_department" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.email" />
				<s:hidden name="elUser.realname" />

			</form>
			<script type="text/javascript">
													 	function page(i){
													 		document.getElementById("pageNow").value=i;
													 		acc_list.submit();
													 	}
													 	var idandtitle = new Array();
										function queding(){
											window.returnValue = idandtitle;
											window.close();
										}
										function selectTheUser(id,uname){
											idandtitle[0]= id;
											idandtitle[1]= uname;
										}
													</script>
			<wysLib:page></wysLib:page>
			<table width="90%" align="center" cellpadding="2" cellspacing="2"
				>
				<tr>
					<th>
					</th>
					<th>
						用户名
					</th>
					<th>
						姓名
					</th>
					<th>
						部门
					</th>
					<th>
						角色
					</th>
				</tr>
				<s:iterator value="elUsers">
					<tr>
						<td height="20" align="center">
						<input type="radio" name="ddd" onClick="selectTheUser(<s:property value="id"/>,'<s:property value="realname" />')">
						</td>
						<td height="20" align="center">
							<s:property value="username" />
						</td>
						<td height="20" align="center">
							<s:property value="realname" />
						</td>
						<td height="20" align="center">
							<s:property value="department.name" />
						</td>
						<td height="20" align="center">
							<s:property value="roleName" />
						</td>
					</tr>
				</s:iterator>
			</table>
			<input type="button" value="确定" onClick="queding();">
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
