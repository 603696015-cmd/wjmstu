<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String patd = request.getContextPath();
	String basePatd = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ patd + "/";
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePatd%>">
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
			<li>
				<span style="font-weight: bold;">测评详情</span>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="65%" cellpadding="2" cellspacing="1" bgcolor="#EBEBEB">
				<caption>
					测评详情
				</caption>
				<tr>
					<td align="center" >
						场次名称
					</td>
					<td align="center" >
						<s:property value="myktroomc.troomcoll.title" />
					</td>
				</tr>
				<tr>
					<td align="center" >
						创建时间
					</td>
					<td align="center" >
						<s:date name="myktroomc.troomcoll.createtime"
							format="yyyy-MM-dd HH:mm:ss" />
					</td>

				</tr>
				<tr>
					<td align="center" >
						创建人
					</td>
					<td align="center" >
						<s:property value="myktroomc.troomcoll.creater.realname" />
					</td>
				</tr>
				<tr>
					<td align="center" >
						总分
					</td>
					<td align="center" >
						<s:property value="myktroomc.totalscore" />
						（共
						<s:property value="myktroomc.quizcount" />
						个考试）
					</td>
				</tr>
				<tr>
					<td align="center" >
						平均分
					</td>
					<td align="center" >
						<s:property value="myktroomc.avgscore" />
					</td>
				</tr>
				<tr>
					<th align="center" colspan="2" >
						得分详情
					</th>
				</tr>
				<tr>
					<td align="center" colspan="2" bgcolor="#FFFFFF" style="padding:0;">
						<table cellpadding="1" style="padding: 0;margin: 0;" cellspacing="1" width="100%" height="100%">
							<tr>
							<td>考试名次</td>
							<td>开始时间</td>
							<td>结束时间</td>
							<td>我的得分</td>
							</tr>
							<s:iterator value="myktroomc.myktrooms">
							<tr>
							<td><s:property value="troom.title"/></td>
							<td><s:date name="troom.begintime"
							format="yyyy-MM-dd HH:mm:ss" /></td>
							<td><s:date name="troom.endtime"
							format="yyyy-MM-dd HH:mm:ss" /></td>
							<td><s:property value="myScore"/></td>
							</tr></s:iterator>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
