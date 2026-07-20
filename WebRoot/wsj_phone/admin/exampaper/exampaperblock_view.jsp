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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
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
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="大题基本信息" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">查看大题信息</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="exampaperblock_alterInit.action?epBlock.id=<s:property value="epBlock.id" />">编辑大题信息</a>
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="exampaperblockquestion_list.action?epBlock.id=<s:property value="epBlock.id"/>">大题试题管理</a>
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="exampaperblock_list.action?examPaper.id=<s:property value="epBlock.examPaper.id"/>">返回大题列表</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<label style="font-size: 16px; font-weight: bold;">
				当前试卷:
				<s:property value="epBlock.examPaper.title" />
			</label>
			<br>
			<br>
			<table width="90%" align="center" cellpadding="2" cellspacing="2"
				bgcolor="#EBEBEB">
				<tr>
					<td width="160" height="30" align="center" >
						大题名称
					</td>
					<td >
						<label>
							<s:property value="epBlock.title" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						大题说明
					</td>
					<td >
						<label>
							<s:property value="epBlock.description" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" width="160" align="center" >
						题型
					</td>
					<td >
						<s:property value="epBlock.typeName" />
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						每题分数
					</td>
					<td >
						<label>
							<s:property value="epBlock.eachscore" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						试题总数
					</td>
					<td >
						<label>
							<s:property value="epBlock.questionamount" />
						</label>
					</td>
				</tr>
			</table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
