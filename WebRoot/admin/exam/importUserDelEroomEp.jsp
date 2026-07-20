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
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/message.js"></script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训班导入学员" /></div>
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
			<form action="eroomEpDeleteUser.action" enctype="multipart/form-data"
				method="post">
				<s:hidden name="examRoom.id" />
				<s:hidden name="examPaper.id" />
				选择需要导入的文档：
				<input type="file" name="st">
				<br>
				<span style="color:red;">注意：学员身份证请放在文档的第1列</span>
				<br><br>
				<input type="submit" value="导入">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="textbg6" style="width:100px;" href="download.jsp?filename=elstuffs/importUserToClassOrEroom.xls">导入格式下载</a>
				<a class="textbg4" href="examroom_assignSearchlist.action?sub_department=1&examPaper.id=<s:property value="examPaper.id" />&examRoom.id=<s:property value="examRoom.id" />&course.id=-1">返回</a>
				<div style="font-size:14px;font-weight:bold;color:red;line-height:50px;">${elmessage}</div>
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
