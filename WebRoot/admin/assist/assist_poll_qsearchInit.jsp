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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>课程类别管理</TITLE>
		<base target="_self">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">管理试题 </span>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top:0px;text-align: center;">
			<br>
			<br>
			<form action="assist_poll_qsearchlist.action" method="post"
				name="questions_info" id="questions_info">
				<label>
					试题名称关键字
					<input type="text" name="question.title" value="" id="name">
				</label>
				<br>
				<br>
				<label>
					题型
					<select name="question.qtype" id="type">
						<option value="1" id="type_1">
							判断题
						</option>
						<option value="2" id="type_2">
							单项选择题
						</option>
						<option value="3" id="type_3">
							不定项选择题
						</option>
						<option value="4" id="type_4">
							多项选择题
						</option>
					</select>
				</label>
				<br>
				<br>
				<label>
					所属知识点
						<wysLib:qlibtree itype="ra" iname="question.qlib.id"></wysLib:qlibtree>
				</label>
				<br>
				<br>
				<label>
					包含子试题库
					<input type="checkbox" name="sublibs" id="sublibs" value="1">
					<input type="hidden" name="pN" value="0">
					<input type="hidden" name="pS" value="10">
				</label>
				<br>
				<br>
				<input type="submit" value="搜索">
			</form>

		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
