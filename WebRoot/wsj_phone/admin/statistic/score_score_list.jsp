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
		<style type="text/css">
td{
	font-size: 11px;
}
		</style>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="积分排榜" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">积分排行</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<form action="score_score_list.action" method="post" name="acc_list">
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
			<table width="90%" align="center" cellpadding="2" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
					<td align="center" >
						排名
					</td>
					<td align="center" >
						学号
					</td>
					<td align="center" >
						姓名
					</td>
					<td align="center" >
						积分
					</td>
					<td align="center" >
						单位
					</td>
					<td align="center" >
						部门
					</td>
					<td align="center" >
						基本信息
					</td>
				</tr>
				<s:iterator value="elUsers" status="st">
					<tr>
						<td align="center" >
							<s:property value="#st.index+1+(pN*pS)" />
						</td>
						<td align="center" >
							<s:property value="username" />
						</td>
						<td align="center" >
							<s:property value="realname" />
						</td>
							<td align="center" >
							<s:property value="score" />
						</td>
						<td align="center" >
							<s:property value="company.name" />
						</td>
						<td align="center" >
							<s:property value="department.name" />
						</td>
						<td align="center" >
							<a href="score_score_view.action?elUser.id=<s:property value="id"/>">查看详情</a>
						</td>
					</tr>
				</s:iterator>
			</table>
			<br>
			<wysLib:page></wysLib:page>
			
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
