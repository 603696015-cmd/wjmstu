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
td {
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="学员列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;"> 综合学分</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<form action="usercredit_stat_search.action" method="post"
				name="acc_list">
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
				 		acc_list.action="usercredit_stat_search.action";
				 		document.getElementById("pageNow").value=i;
				 		acc_list.submit();
				 	}
			</script>
			<table width="1000px" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<caption>
					笔记学时排行
				</caption>
				<tr>
					<td align="center" >
						账号
					</td>
					<td align="center" >
						姓名
					</td>
					<td align="center" >
						部门
					</td>
					<td align="center" width="50" >
						笔记学分
					</td>
					<td align="center" width="50" >
						学习学分
					</td>
					<td align="center" width="150" >
						一般考试学分
					</td>
					<td align="center" width="150" >
						结业考试学分
					</td>
					<td align="center" width="150"  >
						线下培训学分
					</td>
					<td align="center" width="50"  >
						总分
					</td>
				</tr>
				<s:iterator value="elUsers" status="st">
					<tr>
						<td align="center" >
							<s:property value="username" />
							<s:set name="userid" value="id"></s:set>
						</td>
						<td align="center" >
							<s:property value="realname" />
						</td>
						<td align="center" >
							<s:property value="department.name" />
						</td>
						<td width="50" align="center" >
							<s:property value="ct_time" />
						</td>
						<td width="50" align="center" >
							<s:property value="xx_time" />
						</td>
						<td width="150" align="center" >
							<s:property value="ct_credit" />
						</td>
						<td width="150" align="center" >
							<s:property value="xfscore" />
						</td>
						<td width="150" align="center" >
							<s:property value="xx_credit" />
						</td>
						<td align="center" width="50"  >
							<s:property value="ct_time+xx_time+ct_credit+xx_credit" />
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
