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
		<script type="text/javascript" src="js/menu.js"></script>
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
				<span style="font-weight: bold;">添加新专家</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="talent_expert_list.action">专家列表</a>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		 
			<table width="60%">
						<tr>
							<td>
								<strong>所属单位/部门</strong>
							</td>
							<td height="30" align="left" >
								<label>

									<select style="width: 300px;" name="elUser.department.id">
										<wysLib:dep_select selectid="${elUser.department.id}" />
									</select>
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<strong>学号</strong>
							</td>
							<td height="30" align="left" >
								<label>
									<s:property value="elUser.username" />
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<strong>初始密码</strong>
							</td>
							<td height="30" align="left" >
								<label>
									<s:textfield name="elUser.password" id="password" />
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<strong>确认密码</strong>
							</td>
							<td height="30" align="left" >
								<label>
									<s:textfield name="repassword" id="repassword" />
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<strong>姓 名</strong>
							</td>
							<td height="30" align="left" >
								<label>
									<s:textfield name="elUser.realname" id="name" />
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<strong>编 号</strong>
							</td>
							<td height="30" align="left" >
								<label>
									<s:textfield name="elUser.userno" />
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<strong>教师权限</strong>
							</td>
							<td height="30" align="left" >
								<label>
									<input type="checkbox"
										<s:if test="elUser.role==3">checked='checked'</s:if>
										name="is_teacher" value="1" />
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<strong>联系电话</strong>
							</td>
							<td height="30" align="left" >
								<label>
									<s:textfield name="elUser.phone" />
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<strong>地 址</strong>
							</td>
							<td height="30" align="left" >
								<label>
									<s:textfield cssStyle="width:300px;" name="elUser.address"
										id="address" />
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<strong>电子邮箱</strong>
							</td>
							<td height="30" align="left" >
								<label>
									<s:textfield cssStyle="width:300px;" name="elUser.email"
										id="email" />
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<strong>开通状态</strong>
							</td>
							<td height="30" align="left" >
								<label>
									<s:radio list="#{true:'开通',false:'关闭'}" name="elUser.valid"/>
								</label>
							</td>
						</tr>
						<tr>
							<td width="120" height="50" align="center" >
								<s:hidden name="elUser.id"></s:hidden>
								<s:hidden name="elUser.role"></s:hidden>
							</td>
							<td height="50" align="left" >
								<input type="submit" value="确认修改">
							</td>
						</tr>
					</table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
