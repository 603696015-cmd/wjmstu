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
			<s:if test="myztrooms.size==0">没有符合条件的测评</s:if>
			<s:else>
				<table width="85%" cellpadding="2" cellspacing="1" bgcolor="#EBEBEB">
					<tr>
						<th align="center" >
							名称
						</th>
						<th align="center" >
							简介
						</th>
						<th align="center" >
							创建人
						</th>
						<th align="center" >
							开始时间
						</th>
						<th align="center" >
							结束时间
						</th>
						<th align="center" >
						</th>
					</tr>
					<s:iterator value="myztrooms">
						<tr>
							<td align="center" >
								<s:property value="ztroom.title" />
							</td>
							<td align="center" >
								<s:property value="ztroom.description" />
							</td>
							<td align="center" >
								<s:property value="ztroom.creater.realname" />
							</td>
							<td align="center" >
								<s:date name="ztroom.begintime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="center" >
								<s:date name="ztroom.endtime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="center" >
								<a
									href="student_talent_ztroom_view.action?troom.id=<s:property value="ztroom.id" />">进入</a>
							</td>
						</tr>
					</s:iterator>
				</table>
				<wysLib:page></wysLib:page>
			</s:else>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
