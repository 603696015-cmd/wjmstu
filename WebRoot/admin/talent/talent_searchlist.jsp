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
		<TITLE>五矿发展员工职业发展系统--管理端--学员添加</TITLE>
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
				<span style="font-weight: bold;">分配学员</span>
			</li>
		</ul>
		<!-- 内容 -->
			<form action="talent_search.action" method="post" name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				<s:hidden name="sub_department" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.email" />
				<s:hidden name="elUser.realname" />
				<s:hidden name="troom.id" />
				<s:hidden name="troomcoll.id" />
			<table align="center" cellpadding="1" cellspacing="1" width="100%"
				>
				<tr>
					<th>
						场次
					</th>
					<th>
						学号
					</th>
					<th>
						姓名
					</th>
					 <th>
						单位
					</th>
					<th>
						部门
					</th>
					<th>
						角色
					</th>
					<th>
						客观得分（试卷）
					</th>
					<th>
						自我测评总分
					</th>
					<th>
						同事测评平均分
					</th>
					<th>
					上级评价平均分
					</th>
					<th>
					</th>
				</tr>
				<s:iterator value="mytrooms">
					<tr>
						<td height="20" align="center">
							<s:property value="troom.title" />
						</td>
						<td height="20" align="center">
							<s:property value="tester.username" />
						</td>
						<td height="20" align="center">
							<s:property value="tester.realname" />
						</td>
						<!--<td height="20" align="center">
							<s:property value="userno" />
						</td>
						--><td height="20" align="center">
							<s:property value="tester.company.name" />
						</td>
						<td height="20" align="center">
							<s:property value="tester.department.name" />
						</td>
						<td height="20" align="center">
							<s:property value="tester.role.name" />
						</td>
						<td height="20" align="center">
							 <s:property value="myScore" />
						</td>
						<td height="20" align="center">
							 <s:property value="zjScore" />
						</td>
						<td height="20" align="center">
							 <s:property value="tsScore" />
						</td>
						<td height="20" align="center">
							 <s:property value="sjscore" />
						</td>
						<td height="20" align="center">
							<a href="talent_troom_result_view.action?elUser.id=<s:property value="tester.id"/>&troom.id=<s:property value="troom.id"/>">查看</a>
						</td>
					</tr>
				</s:iterator>
			</table>
			</form>
			<div style="margin-bottom: 20px; text-align: center;">
			<script type="text/javascript">
			 	function page(i){
			 		acc_list.action=  "talent_search.action";
			 		document.getElementById("pageNow").value=i;
			 		acc_list.submit();
			 	}
			 	function backSearch(){
			 		acc_list.action=  "talent_searchInit.action";
			 		acc_list.submit();
			 	}
			</script>
				<wysLib:page></wysLib:page>
				<br>
				<input value="重新搜索" type="button" onclick="backSearch()">
			 <br>
				</div>
		<!-- 内容 -->
		
	</BODY>
</HTML>
