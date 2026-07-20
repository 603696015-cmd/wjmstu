<%@ page language="java" pageEncoding="UTF-8"   %>
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
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="添加考试试卷" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;"> 添加考试 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="quizpaper_list.action?course.id=<s:property value="course.id"/>">考试管理
				</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<form action="quizpaper_addInit.action" method="post"
				name="papers_info" id="papers_info">
				<input type="hidden" name="operate_search" value="ok">
				<label>
					试卷名称关键字
					<input type="text" name="examPaper.title" id="name">
				</label>
				<br>
				<br>
				<label>
					所属试卷库
							<wysLib:elibtree iname="examPaper.epl.id" itype="ra"></wysLib:elibtree>
				</label>
				<br>
				<br>
				<label>
					包含子试卷库
					<input type="checkbox" name="sublibs" id="sublibs" value="1">
				</label>
				<br>
				<s:hidden name="course.id"></s:hidden>
				<br>
				<s:hidden name="pN" value="0"/>
				<s:hidden name="pS" value="10"/>
				<input type="submit" value="搜索" />
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
