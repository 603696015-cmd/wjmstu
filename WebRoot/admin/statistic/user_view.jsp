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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="用户基本信息" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">基本信息 </span>
			</li>
				<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					 href="user_course.action?elUser.id=<s:property value="elUser.id"/>">选课情况
				</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="account_add" method="post" theme="simple">

				<table width="500px" align="center" cellpadding="1" cellspacing="1"
					>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>所属单位/部门</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.department.name" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>学 号</strong>
						</td>
						<td height="30" align="left" >
							<label>

								<s:property value="elUser.username" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>姓 名</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.realname" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>编 号</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.userno" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>角色</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.role.name" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>联系电话</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.phone" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>地 址</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.address" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>电子邮箱</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.email" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>学分</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.xx_credit" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>排名</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.xfph" />
							</label>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
