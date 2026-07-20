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
		<TITLE>中国食品安全培训网--管理端--学员管理</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<BODY style="height: 100%; width: 100%">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="搜索用户" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">查找学员</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="course_notequerylist" method="post" theme="simple"
				name="department_info" id="department_info">
				<table border="0" width="100%" cellpadding="1" cellspacing="1"
					bgcolor="#D1E4F5">
							
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							所属部门：
						</td>
						<td bgcolor="#F8FCFE" >
							<label>
								<select style="width: 300px" name="department.id" id="parentid">
									<wysLib:dep_select />
								</select>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>包含下属部门</strong>
						</td>
						<td bgcolor="#F8FCFE" >
							<label>
							<s:property value="company.name" />
							<s:hidden name="pN" value="0" />
							<s:hidden name="pS" value="10" />
								<input type="checkbox" name="sub_department" id="sub_department"
									value="1">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>学号</strong>
						</td>
						<td bgcolor="#F8FCFE" >
							<label>
								<input type="text" name="elUser.username" id="username" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>姓名</strong>
						</td>
						<td bgcolor="#F8FCFE" >
							<label>
								<input type="text" name="elUser.realname" id="name" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>电子邮箱</strong>
						</td>
						<td bgcolor="#F8FCFE" >
							<label>
								<input type="text" name="elUser.email" id="email" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="50" align="center" bgcolor="#F8FCFE" >&nbsp;
							
						</td>
						<td bgcolor="#F8FCFE" >
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="50">
										<input type="submit" value="搜索">
									</td>
									<td width="20">&nbsp;
										
									</td>
									<td><!--
										<div class=ljbg>
											<a href="account_addInit.action">添加用户</a>
										</div>
									--></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
