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
		<TITLE>我的课</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
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
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="课程列表" />
				</div>
			</li>

		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<form action="courses_delete.action" name="myclistdel">
			<table width="100%" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<th width="200" align="center">
						课程名称
					</th>

					<th width="70" align="center">
						创建时间
					</th>
					<th width="110" align="center">
						课程类型
					</th>
					<th width="70" align="center">
						开始时间
					</th>
					<th width="70" align="center">
						结束时间
					</th>
					<th width="80" align="center">
						进入讲课
					</th>
				</tr>
				<s:iterator value="courses">
					<tr>
						<td>
							<s:property value="name" />
						</td>
						<td>
							<s:date format="yyyy-MM-dd HH:mm:ss" name="createtime" />
						</td>
						<td>
							<s:property value="islinkName" />
						</td>
						<td>
							<s:date format="yyyy-MM-dd HH:mm:ss" name="roomstart" />
						</td>
						<td>
							<s:date format="yyyy-MM-dd HH:mm:ss" name="roomend" />
						</td>
						<td>
							<a target="_blank" href="mytbcourse_Into.action?course.id=<s:property value="id" />" class="textbg6">开始讲课</a>
						</td>
					</tr>
				</s:iterator>
			</table>
		</form>
	</BODY>
</HTML>
