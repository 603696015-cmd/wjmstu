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
		<TITLE>五矿发展员工职业发展系统--管理端--下拉选项删除</TITLE>
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
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div style="margin-top: 0px;">
			<form action="deleteAnsweringTypeTree.action" method="post">
				<table border="0" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="120" height="30" align="center">
							确认要删除的问答类别
						</td>
						<td>
							<label>
								<s:property value="answeringTypeTree.name" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="150" height="30" align="center">
							所属用户及子问答类别
						</td>
						<td width="300">
							<input type="radio" name="sub_operate" checked="checked"
								value="0">
							<label>
								并入上级问答类别
							</label>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="sub_operate" value="1">
							<label>
								与本问答类别同时删除
							</label>
						</td>
					</tr>
					<tr>
						<td width="150" height="50" align="center">
							<input type="hidden" value="<s:property value="answeringTypeTree.id" />"
								name="answeringTypeTree.id">
						</td>
						<td width="300">
							<input name="submit" class="textbg4" style="width: 100px;"
								type="submit" value="确认删除" />
							<input type="button" class="textbg4" style="width: 50px;"
								onclick="document.location='viewAnsweringTypeTree.action?klTree.id=<s:property value="answeringTypeTree.id"/>'" value="取消" />
						</td>
				</table>
			</form>
		</div>
	</BODY>
</HTML>
