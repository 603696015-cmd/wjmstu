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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="上传文件" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">上传新资料</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: left; margin-left: 300px;">
			<s:form action="question_stuffadd.action" method="post"
				enctype="multipart/form-data" theme="simple">
				<input type="hidden" name="MAX_FILE_SIZE" value="10000000">
				<s:property value="elmessage" />
				<br>

				<input type="radio" name="st_type"
					onclick="document.getElementById('fileName').style.display='block';document.getElementById('filebox').style.display='none';"
					value="1" /> 已传到服务器
				<input type="radio" name="st_type" checked="checked"
					onclick="document.getElementById('fileName').style.display='none';document.getElementById('filebox').style.display='block';"
					value="0" />上传新的
					<br>
					<br>
				<label id="fileName" style="display: none;">
					文件名<s:textfield name="stfilename" />
				</label>
				<label id="filebox">
					选择上传文件
					<s:file name="st" />
					(文件大小小于10M)
				</label>
				<br>
				<label>
					类型
					<select name="qstuff.type">
						<option value="1">
							图片
						</option>
						<option value="2">
							音频
						</option>
						<option value="3">
							视频
						</option>
						<option value="4">
							文档
						</option>
						<option value="5">
							其他
						</option>
					</select>
				</label>
				<br>
				<label>
					填写资料说明
					<s:textarea name="qstuff.description" />
				</label>
				<br>
				<br>
				<input type="submit" value="确认上传">
														&nbsp;&nbsp;&nbsp;
													</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
