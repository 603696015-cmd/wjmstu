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
		<title>页面信息</title>
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
		
  		function upload(pageid){
  			document.getElementById("pageInfo.pageid").value = pageid;
  			jj.submit();
  		}
  </script>
	<body >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="middle" class="tablequiz">
					<ul class="nav">
						<li>
							<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
								<wysLib:Navigation ivalue="页面信息" />
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
		<div style="margin-top: 0px; text-align: center;">
			<table cellpadding='1' cellspacing='1' width='50%'>
				<tr><th>页面</th><th>JSP名称</th><th>是否上传</th><th>操作</th></tr>
				<s:iterator value="pageInfos">
					<tr>
						<td align=center><s:property value="pageName" /></td>
						<td align=center>
							<s:if test="upload == 1">
								<s:property value="jspName" />
							</s:if>
						</td>
						<td align=center>
							<s:if test="upload == 1">
								已上传
							</s:if>
							<s:else>
								未上传
							</s:else>
						</td>
						<td><input type="button" value="上传页面" onclick="upload('<s:property value="pageid" />');" /></td>
					</tr>
				</s:iterator>
			</table>
		</div>
		<form action="uploadPageJspInit.action" method="post" name="jj" >
			<input type="hidden" name="pageInfo.pageid" id="pageInfo.pageid" />
		</form>
	</body>
</html>