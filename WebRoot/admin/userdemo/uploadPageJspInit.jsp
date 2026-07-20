<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="zdyLib" uri="/WEB-INF/zdyLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<base target="_self">
		<base href="<%=basePath%>">
		<TITLE>上传模板</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<BODY>
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
		
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="middle" class="tablequiz">
					<ul class="nav">
						<li>
							<span style="font-weight: bold;">上传模板</span>
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
		<form action="uploadPageJsp.action" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="pageInfo.pageid" />
			<table>
				<tr>
					<td>
						<s:property value="pageInfo.pageName"/>
					</td>
					<td>
						<s:file name="st" theme="simple" />
					</td>

					<td>
						<input type="submit" value="上传" />
					</td>
				</tr>
			</table>
		</form>

	</BODY>
</HTML>
