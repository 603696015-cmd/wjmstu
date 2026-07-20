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
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/message.js"></script>
	</HEAD>
	<body>
		<br/><br/>
		<div style="font-size: 13px;text-align:center; margin-top: 0px;">
			<table align="center" cellpadding="1" cellspacing="1" width="600">
				<tr>
					<th>
						类型名称
					</th>
					<th>	
						描述
					</th>
					<th width="200">
						功能
					</th>
				</tr>
				<tbody >
					<s:iterator value="lnss">
						<tr>
							<td height="30" style="padding-left: 8px; color: blue;"
								width="200" align="left">
								<s:property value="name" />
							</td>
							<td height="30" style="padding-left: 8px;" align="left">
								<s:property value="description" />
							</td>
							<td width="200" height="20" align="center">
								<a href="newsstyle_view.action?nstyle.id=<s:property value="id"/>" class="textbg4">查看</a>
								<a
									href="newsstyle_alterInit.action?nstyle.id=<s:property value="id"/>"
									class="textbg4">修 改</a>
								<a onclick="return window.confirm('确定删除？');"
											href="newsstyle_delete.action?nstyle.id=<s:property value="id"/>"
											class="textbg4">删 除</a>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<br/>
			<a href="newsstyle_addInit.action" class=textbg>添加新闻类型</a>
		</div>
	</BODY>
</HTML>
