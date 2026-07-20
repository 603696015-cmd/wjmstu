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
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="修改素材" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">修改资料</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: left; margin-left: 300px;">
			<s:form action="question_stuffalter" method="post"
				theme="simple">
				<input type="hidden" name="MAX_FILE_SIZE" value="10000000">
				<s:property value="elmessage" />
				<br>
				<br>
				<label>
					填写资料名称
					<s:textfield name="qstuff.title"></s:textfield>
				</label>
				<br>
				<select name="qstuff.type" >
						<option <s:if test="qstuff.type==1">selected='selected'</s:if> value="1">图片</option>
						<option <s:if test="qstuff.type==2">selected='selected'</s:if> value="2">音频</option>
						<option <s:if test="qstuff.type==3">selected='selected'</s:if> value="3">视频</option>
						<option <s:if test="qstuff.type==4">selected='selected'</s:if> value="4">文档</option>
						<option <s:if test="qstuff.type==5">selected='selected'</s:if> value="5">其他</option>
					</select>
				<label>
					填写资料说明
					<s:textarea name="qstuff.description" />
				</label>
				<br>
				<br>
				<s:hidden name="qstuff.id"></s:hidden>
				<input type="submit" value="确认上传">
														&nbsp;&nbsp;&nbsp;
													</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
