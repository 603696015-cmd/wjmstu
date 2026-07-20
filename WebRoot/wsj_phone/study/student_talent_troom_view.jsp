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
				<span style="font-weight: bold;">我要测评</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="80%" cellpadding="1" cellspacing="1">
				<caption>
					考场信息
				</caption>
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
						<strong>创建时间</strong>
					</td>
					<td align="left" >
						<label>
							<s:date name="troom.createtime" format="yyyy-MM-dd" />
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<strong>创建人</strong>
					</td>
					<td align="left" >
						<label id="eptitle" style="width: 200px;">
							<s:property value="troom.creater.realname" />
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<strong>考试</strong>
					</td>
					<td align="left" style="padding: 0px;padding:0px;" >
					<table cellpadding="1" style="margin: 0px;" cellspacing="1" width="100%" height="100%">
						<tr>
							<td>考试名次</td>
							<td>试卷名次</td>
							<td>开始时间</td>
							<td>结束时间</td>
							<td></td>
						</tr>
					<s:iterator value="troom.trooms">
						<tr>
							<td><s:property value="title" /> </td>
							<td><s:property value="exampaper.title"/></td>
							<td><s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" /></td>
							<td><s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" /></td>
						<td><a href="student_talent_quiz.action?qtroom.id=<s:property value="id"/>">进入考试</a></td>
						</tr>
					</s:iterator>
					</table>
							
					</td>
				</tr>
				<!--<tr>
					<td>
						<strong>测评</strong>
					</td>
					<td align="left" >
						<div id="trnorms" style="width: 100%;">
							<a href="student_talent_quiz.action?troom.id=<s:property value="troom.id"/>">作答试卷</a> 
							<a href="student_talent_troom_evalInit.action?troom.id=<s:property value="troom.id"/>&mytroom.evaltype=1">自我打分</a> 
							<a href="student_talent_troom_evalInit.action?troom.id=<s:property value="troom.id"/>&mytroom.evaltype=2">给同事打分</a>
							<a href="student_talent_troom_evalInit.action?troom.id=<s:property value="troom.id"/>&mytroom.evaltype=3">给下级打分</a>
							</div>
					</td>
				</tr>
			--></table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
