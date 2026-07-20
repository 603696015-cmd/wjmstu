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
		<TITLE>五矿发展员工职业发展系统--管理端--学员管理</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<style type="text/css"> 
		td {font-size:12px;color:#333333;line-height:150%}
		tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="eltree/dtree.js"></script>
	</HEAD>
	<BODY style="height: 100%; width: 100%">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="搜索学员" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">练习分配人员</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="examprac_assign_list.action?examprac.id=<s:property value="examprac.id"/>">人员列表</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<h2>
				为练习【
				  <s:property value="examprac.title" />
				】添加考生
			</h2>
			<s:form action="examprac_assign_addlist" method="post" theme="simple"
				name="department_info" id="department_info">
				<table border="0" width="60%" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="120" align="center" >
							搜索范围：
						</td>
						<td >
							<label>
								<s:hidden name="group.id" />
								<s:hidden name="examprac.id" />
								<s:hidden name="pN" value="0" />
								<s:hidden name="pS" value="10" />
								<select style="width: 300px" name="department.id" id="parentid">
									<wysLib:dep_select />
								</select>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" align="center" >
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
						<td width="120" align="center" >
							<strong>学号</strong>
						</td>
						<td >
							<label>
								<input type="text" name="elUser.username" id="username" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" align="center" >
							<strong>姓名</strong>
						</td>
						<td >
							<label>
								<input type="text" name="elUser.realname" id="name" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" align="center" >&nbsp;
							
						</td>
						<td >
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="50">
										<input type="submit" value="搜索">
									</td>
									<td width="20">&nbsp;
										
									</td>
									<td>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:form>

		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
