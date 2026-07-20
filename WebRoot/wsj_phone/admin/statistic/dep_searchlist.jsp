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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="部门学习情况" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">部门统计</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align:center; margin-left: 40px;">
		<table width="90%" align="center" cellpadding="2" cellspacing="1"
				bgcolor="#EBEBEB">
				<caption><s:property value="department.name"/>--部门信息</caption>
			<tr>
					<th height="30" >
						部门（单位）名称
					</th>
					<th height="30" >
						基本信息
					</th>
					<th height="30" >
						学员数量
					</th>
					<th height="30" colspan=2 >
						选课数量
					</th>
					<th height="30" colspan=2 >
						选班数量
					</th>
					<!--<th height="30" >
						考试统计
					</th>
				--></tr>
					<tr>
						<td height="30" >
							<s:property value="department.name" />
						</td>
						<td height="30" >
							<a
								href="dep_stat_view.action?department.id=<s:property value="department.id"/>">查看</a>
						</td>
						<td height="30" >
							<s:property value="department.userCount" />
						</td>
						<td height="30" >
							<s:property value="department.courseCount" />
						</td>
						<td height="30" >
							<a
								href="dep_course_list.action?department.id=<s:property value="department.id"/>">查看</a>
						</td>
						<td height="30" >
							<s:property value="department.classCount" />
						</td>
						<td height="30" >
							<a
								href="dep_class_list.action?department.id=<s:property value="department.id"/>">查看</a>
						</td><!--
						<td height="30" >
							<a
								href="dep_quiz_info.action?department.id=<s:property value="department.id"/>">查看</a>
						</td>
					--></tr>
			</table>
			<br><br>
			<s:if test="elUsers.size==0">没有符合条件下级部门的记录<br> <a href="dep_searchInit.action">返回重新搜索</a></s:if>
			<s:else>
			<table width="90%" align="center" cellpadding="2" cellspacing="1"
				bgcolor="#EBEBEB">
				<caption><s:property value="department.name"/>--部门下级的部门</caption>
				<tr>
					<th height="30" >
						姓名
					</th>
					<th height="30" >
						身份证号	 
					</th>
					<th height="30" >
					 	所属单位	 
					</th>
					<th height="30"  >
						 所属部门
					</th>
					<th height="30"   >
						学分
					</th>
				</tr>
				<s:iterator value="elUsers">
					<tr>
						<td height="30" >
							<s:property value="realname" />
						</td>
						<td height="30" >
							<s:property value="studentno" />
						</td>
						<td height="30" >
							<s:property value="company.name" />
						</td>
						<td height="30" >
							<s:property value="department.name" />
						</td>
						<td height="30" >
							<s:property value="xfscore" />
						</td>
						 
					</tr>
				</s:iterator>
			</table>
			<!--<table width="90%" align="center" cellpadding="2" cellspacing="1"
				bgcolor="#EBEBEB">
				<caption><s:property value="department.name"/>--部门下级的部门</caption>
				<tr>
					<th height="30" >
						部门（单位）名称
					</th>
					<th height="30" >
						基本信息
					</th>
					<th height="30" >
						用户数量
					</th>
					<th height="30" colspan=2 >
						选课数量
					</th>
					<th height="30" colspan=2 >
						选班数量
					</th>
					<th height="30" >
						考试统计
					</th>
				</tr>
				<s:iterator value="departments">
					<tr>
						<td height="30" >
							<s:property value="name" />
						</td>
						<td height="30" >
							<a
								href="dep_stat_view.action?department.id=<s:property value="id"/>">查看</a>
						</td>
						<td height="30" >
							<s:property value="userCount" />
						</td>
						<td height="30" >
							<s:property value="courseCount" />
						</td>
						<td height="30" >
							<a
								href="dep_course_list.action?department.id=<s:property value="id"/>">查看</a>
						</td>
						<td height="30" >
							<s:property value="classCount" />
						</td>
						<td height="30" >
							<a
								href="dep_class_list.action?department.id=<s:property value="id"/>">查看</a>
						</td>
						<td height="30" >
							<a
								href="dep_quiz_info.action?department.id=<s:property value="id"/>">查看</a>
						</td>
					</tr>
				</s:iterator>
			</table>
			-->
			<script>
				function page(i){
					document.location.href="dep_searchlist.action?department.id="+<s:property value="department.id"/>+
					"&pN="+i+"&pS=10";
				}
			</script>
			<wysLib:page></wysLib:page>
			</s:else>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
