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
		<TITLE>用户批量导入</TITLE>
		<base href="<%=basePath%>" />
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
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
		<script type="text/javascript">
			function doSubmit(){
				document.impForm.action="accountImportCheck.action";
				document.impForm.submit();
			}
		</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="账号批量导入" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; margin-left: 300px;">
			注意 ，系统当前支持导入excel文档，请注意文档格式需要正确！
			<br>
			<form action="accountImportCheck.action" enctype="multipart/form-data"
				method="post" name="impForm">
				选择需要导入的文档：
				<input type="file" name="st">
				<br>
				<br>
				<input type="submit" value="导 入" style="border: none" class="textbg5">
				&nbsp;
				<a href="account_search.action" class="textbg5">用户列表</a>
				<a href="download.jsp?filename=elstuffs/import_user.xls"
					class="textbg5">用户格式下载</a>
				<div
					style="font-size: 13px; font-weight: bold; color: red; line-height: 30px;">
					${elmessage}
				</div>
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
<!--		 系统管理---用户与部门---用户批量导入(jsp页面)    	  -->
