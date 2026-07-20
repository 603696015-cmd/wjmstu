<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="userdemoLib" uri="/WEB-INF/userdemoLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>设置列范围</title>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
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
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<script type="text/javascript">
		
  		
  </script>
	<body >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="middle" class="tablequiz">
					<ul class="nav">
						<li>
							<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
								<wysLib:Navigation ivalue="设置列范围" />
							</div>
						</li>

					</ul>
				</td>
				<td width="120" valign="middle" class="tablequiz">
					<A id=quit href="javascript:window.parent.full_screen(false);"
						class="textbg6" style="display: none">退出全屏</A>
				</td>
			</tr>
		</table>
		<!-- 内容 -->
		<form action="setPageType.action" method="post" name="jj" onSubmit="return check();">
		<input type="hidden" name="elUser_page_type.column_name" value="<s:property value='elUserJs.column_name' />" />
		<div style="margin-top: 0px; text-align: center;">
			<table cellpadding='1' cellspacing='1' width='100%'>
				<tr>
					<td  align=center>列名：</td>
					<td align=center><s:property value="elUserJs.column_name" /></td>
				</tr>
				<tr>
					<td  align=center>范围：</td>
					<td align=center>
						<input type="text" name="elUser_page_type.range" /> 
					</td>
				</tr>
				<tr>
					<td  align=center>默认选择：</td>
					<td align=center>
						<input type="text" name="elUser_page_type.default_select" />
					</td>
				</tr>
				<tr>
					<td  align=center>是否可修改：</td>
					<td align=center>
						是：<input type="radio" name="elUser_page_type.modify" checked/>
						否：<input type="radio" name="elUser_page_type.modify" />
					</td>
				</tr>
			</table>
		</div>
		<div style="margin-top: 0px; text-align: center;">
			<input name="submit" type="submit" value="确认修改" class=textbg6 />
			<input type="hidden" name="elUserJs.check_js_type" id="elUserJs.check_js_type" />
			<s:hidden name="elUserJs.column_name"></s:hidden>
			<s:hidden name="elUserJs.show_type"></s:hidden>
		</div>
		</form>
	</body>
</html>