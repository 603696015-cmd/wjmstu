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
		<TITLE>五矿发展员工职业发展系统--管理端--部门删除</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="" />
				</div>
			</li>
			<!-- <li>
				<span style="font-weight: bold;">删除部门</span>

			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="dep_alterInit.action?department.id=<s:property value="department.id" />">编辑部门信息</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="dep_view.action?department.id=<s:property value="department.id" />">显示部门信息</a>
			</li> -->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div style="margin-top: 0px;">
			<form action="dep_delete.action" method="post">
				<table border="0" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="120" height="30" align="center">
							确认要删除的部门
						</td>
						<td>
							<label>
								<s:property value="department.name" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="150" height="30" align="center">
							所属用户及子部门
						</td>
						<td width="300">
							<input type="radio" name="sub_operate" checked="checked"
								value="0">
							<label>
								并入上级部门
							</label>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="sub_operate" value="1">
							<label>
								与本部门同时删除
							</label>
						</td>
					</tr>
					<tr>
						<td width="150" height="50" align="center">
							<input type="hidden" value="<s:property value="department.id" />"
								name="department.id">
						</td>
						<td width="300">
							<input name="submit" class="textbg4" style="width: 100px;"
								type="submit" value="确认删除" />
							<input type="button" class="textbg4" style="width: 50px;"
								onclick="document.location='dep_view.action?department.id=<s:property value="department.id"/>'" value="取消" />
						</td>
				</table>
			</form>
		</div>
	</BODY>
</HTML>
