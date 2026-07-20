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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="部门基本信息" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">基本信息</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="dep_course_list.action?department.id=<s:property value="department.id" />">选课情况
				</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="dep_add" method="post" theme="simple"
				name="department_info" id="department_info">
				<table border="0" width="80%" align="center" cellpadding="2"
					cellspacing="2" bgcolor="#EBEBEB">
					<tr>
						<td height="30" colspan="2" align="center" >
							部门基本信息
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							部门名称：
						</td>
						<td >
							<label>
								<s:property value="department.name" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							详细说明：
						</td>
						<td >
							<label>
								<s:property value="department.description" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							联系电话：
						</td>
						<td >
							<label>
								<s:property value="department.phone" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							管 理 员：
						</td>
						<td >
							<label>
								<s:if
									test="department.manager.realname==''||department.manager.realname==null">
																		无指定
																	</s:if>
								<s:else>
									<s:property value="department.manager.realname" />
								</s:else>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							地 址：
						</td>
						<td >
							<label>
								<s:property value="department.address" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							邮政编码：
						</td>
						<td >
							<label>
								<s:property value="department.postalcode" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							传 真：
						</td>
						<td >
							<label>
								<s:property value="department.fax" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							电子邮箱：
						</td>
						<td >
							<label>
								<s:property value="department.email" />
							</label>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
