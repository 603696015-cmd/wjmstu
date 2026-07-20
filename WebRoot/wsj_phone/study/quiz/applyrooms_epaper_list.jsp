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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">申请考试 </span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<label style="font-size: 17px;">
				申请考场
				<b><s:property value="examRoom.title" /> </b> 
				<br/>
				该考场有多张试卷，请选择需要作答的试卷。
			</label>
			<table width="600px" align="center" cellpadding="1" cellspacing="1"
				>
				<caption>
					本考场使用的试卷如下
				</caption>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						试卷名称
					</td>
					<td height="30" align="center" >
					</td>
				</tr>
				<s:iterator value="examRoom.exampapers">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							<s:property value="title" />
						</td>
						<td height="30" align="center" >
						<s:if test="courseHasEp">已申请/<a onclick="return window.confirm('确定删除？');" href="applyrooms_epaper_delete.action?examPaper.id=<s:property value="id"/>&examRoom.id=<s:property value="examRoom.id"/>">删除</a></s:if>
						<s:else>
							<a onclick="return window.confirm('确定申请？');" href="applyrooms_epaper.action?examPaper.id=<s:property value="id"/>&examRoom.id=<s:property value="examRoom.id"/>">申请</a>
						</s:else>
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
