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
		<TITLE>中国食品安全培训网--管理端--自定义标签类别删除</TITLE>
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
			<form action="deleteLableTree.action" method="post">
				<table border="0" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="120" height="30" align="center">
							确认要删除的自定义标签类别
						</td>
						<td>
							<label>
								<s:property value="klTree.name" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="150" height="30" align="center">
							子知识类别
						</td>
						<td width="300">
							<input type="radio" name="sub_operate" checked="checked"
								value="0">
							<label>
								并入上级知识类别
							</label>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="sub_operate" value="1">
							<label>
								与本知识类别同时删除
							</label>
						</td>
					</tr>
					<tr>
						<td width="150" height="50" align="center">
							<input type="hidden" value="<s:property value="klTree.id" />"
								name="klTree.id">
						</td>
						<td width="300">
							<input name="submit" class="textbg4" style="width: 100px;"
								type="submit" value="确认删除" />
							<input type="button" class="textbg4" style="width: 50px;"
								onclick="document.location='viewLableTree.action?klTree.id=<s:property value="klTree.id"/>'" value="取消" />
						</td>
				</table>
			</form>
		</div>
	
	</body>
</HTML>
