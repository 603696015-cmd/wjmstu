<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<HEAD>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>">
		<TITLE>查看笔记</TITLE>
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
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
	</HEAD>
	<BODY style="">
		<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="修改笔记" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">编辑笔记 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="course_study_notelist.action?course.id=<s:property value="course.id"/>">查看笔记</a>
			</li>-->
		</ul>
		<form action="course_study_noteAlter.action" method="post">
			<table width="100%" align="center" cellpadding="1" cellspacing="1" bgcolor="#D1E4F5">
				<caption>
					课程:
					<s:property value="course.name" />
					的笔记
				</caption>
				<tr>
					<td align="center" bgcolor="#F8FCFE">
						标题：
					</td>
					<td align="center" bgcolor="#F8FCFE">
						<input type="text" value="<s:property value="cnote.title"/>"
							name="cnote.title" style="width: 440px;" />
					</td>
				</tr>
				<tr>
					<td align="center" bgcolor="#F8FCFE">
						内容：
					</td>
					<td align="center" bgcolor="#F8FCFE">
						<textarea id='notecontent' name="cnote.content"
							style="width: 440px; height: 145px;">${cnote.content }</textarea>
					</td>
				</tr>
				<tr>
					<td align="center" bgcolor="#F8FCFE">
					</td>
					<td align="center" bgcolor="#F8FCFE">
						<s:hidden name="course.id"></s:hidden>
						<s:hidden name="cnote.id"></s:hidden>
						<input type="hidden" name="cnote.course.id"
							value="<s:property value="course.id"/>">
						<input type="submit" onClick="" class="textbg4"
							style="width: 80px" value="保存">
						<a  class="textbg4"
							style="width: 80px" href="course_study_notelist.action?course.id=<s:property value="course.id"/>&elclass.id=<s:property value="elclass.id"/>&Return=<s:property value="Return"/>" >取消</a>
						<s:hidden name="elclass.id"></s:hidden>	
						<s:hidden name="Return"></s:hidden>	
					</td>
				</tr>
			</table>
		</form>
	</BODY>
</HTML>
