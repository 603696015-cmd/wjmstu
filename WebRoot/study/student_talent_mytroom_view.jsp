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
		<script type="text/javascript" src="js/message.js"></script>
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
				<span style="font-weight: bold;">我的测评</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="60%" cellpadding="1" cellspacing="1">
				<caption>
					考场信息
				</caption>
				<tr>
					<td>
						<strong>考场集标题</strong>
					</td>
					<td>
						<s:property value="troom.trcoll.title" />
					</td>
				</tr>
				<tr>
					<td>
						<strong>考场标题</strong>
					</td>
					<td align="left" >
						<label>
							<s:property value="troom.title" />
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<strong>开始结束时间</strong>
					</td>
					<td align="left" >
						<label>
							<s:date name="troom.begintime" format="yyyy-MM-dd" />
							到
							<s:date name="troom.endtime" format="yyyy-MM-dd" />
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<strong>试卷</strong>
					</td>
					<td align="left" >
						<label id="eptitle" style="width: 200px;">
							<s:property value="troom.exampaper.title" />
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<strong>测评指标</strong>
					</td>
					<td align="left" >
						<div id="trnorms" style="width: 100%;">
							<s:iterator status="normosst" value="troom.norms">
								<s:property /> ，
									</s:iterator>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<strong>测评</strong>
					</td>
					<td align="left" >
						<div id="trnorms" style="width: 100%;">
						
							<a href="student_talent_troom_evalInit.action?troom.id=<s:property value="troom.id"/>&mytroom.evaltype=1">我的打分</a> 
							<a href="student_talent_troom_evalInit.action?troom.id=<s:property value="troom.id"/>&mytroom.evaltype=2">我给同事打分</a>
							<a href="student_talent_troom_evalInit.action?troom.id=<s:property value="troom.id"/>&mytroom.evaltype=3">我给下级打分</a>
					</div>
					</td>
				</tr>
			</table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
