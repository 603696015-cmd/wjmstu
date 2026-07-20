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
		<base href="<%=basePath%>">
		<TITLE>中国食品安全培训网--管理端--学员添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
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
			<li>
				<span style="font-weight: bold;">专家列表</span>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<form action="talent_expert_list.action" method="post" name="acc_list">
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
													</script>
			<wysLib:page></wysLib:page>
		 <table align="center" cellpadding="1" cellspacing="1" width="100%" 
				>
				<tr>
					<th>
						学号
					</th>
					<th>
						姓名
					</th>
					<!--<th>
						编号
					</th>
					--><th>
						单位
					</th>
					<th>
						部门
					</th>
					<th>
						电子邮箱
					</th>
					<th>
						&nbsp;
					</th>
				</tr>
				<s:iterator value="elUsers">
					<tr>
						<td height="20" align="center">
							<s:property value="username" />
						</td>
						<td height="20" align="center">
							<s:property value="realname" />
						</td>
						<!--<td height="20" align="center">
							<s:property value="userno" />
						</td>
						--><td height="20" align="center">
							<s:property value="company.name" />
						</td>
						<td height="20" align="center">
							<s:property value="department.name" />
						</td>
						<td height="20" align="center">
							<s:property value="email" />
						</td>
						<td height="20" align="center">
							<a target="_blank"
								href="forumListByBlockid.action?fblock.id=<s:property value="id"/>">咨询</a>
						</td>
					</tr>
				</s:iterator>
			</table> 
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
