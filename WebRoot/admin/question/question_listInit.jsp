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
		<script type="text/javascript" src="js/message.js"></script>
	<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="搜索试题" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">管理试题 </span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;margin-left:60px;font-size:14px;color:#000099;">
			<br>
			<br>
			<form action="question_list.action" method="post"
				name="questions_info" id="questions_info">
				<label>
					题干
					<input type="text" name="question.title" value="" id="name">
				</label>
				<br>
				<br>
				<label>
					题型
					<select name="question.qtype" id="type">
						<option value="" id="type_0">
							所有试题
						</option>
						<option value="1" id="type_1">
							判断题
						</option>
						<option value="2" id="type_2">
							单项选择题
						</option>
						<%-- 
						<option value="3" id="type_3">
							不定项选择题
						</option>
						 --%>
						<option value="4" id="type_4">
							多项选择题
						</option>
						<option value="5" id="type_5">
							填空题
						</option>
						<option value="6" id="type_6">
							问答题
						</option>
						<%-- 
						<option value="7" id="type_7">
							材料题
						</option>
						<option value="8" id="type_8">
							打字题
						</option>
						<option value="9" id="type_9">
							邮件题
						</option>
						<option value="10" id="type_10">
							搜索题
						</option>
						<option value="11" id="type_11">
							OFFICE题
						</option>
						 --%>
					</select>
				</label>
				<br>
				<br>
				<label>
					所属知识点
					<wysLib:qlibtree did="1" itype="ra" iname="question.qlib.id" />
				</label>
				<br>
				<br>
				<label>
					包含子试题库
					<input type="checkbox" name="sublibs" checked="checked" id="sublibs" value="1">
					<input type="hidden" name="pN" value="0">
					<input type="hidden" name="pS" value="10">
				</label>
				<br>
				<br>
				<input class=textbg6 style="height:35px;" type="submit" value="搜索">
			</form>

		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
