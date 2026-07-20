<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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
		<script type="text/javascript" src="js/menu.js"></script>
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
				<span style="font-weight: bold;">可申请的考场</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="examRooms.size==0">
				<br>
						没有考场。
			</s:if>
			<s:else>
				<table width="96%" align="center" cellspacing="1" cellpadding="1">
					<tr>
						<td align="center" >
							考场标题
						</td>
						<td align="center" >
							考场地点
						</td>
						<td align="center" >
							类别库
						</td>
						<td align="center" >
							通过百分比
						</td>
						<td align="center" >
							考场开始时间
						</td>
						<td align="center" >
							考场结束时间
						</td>
						<td align="center" >
							类型
						</td>
						<td align="center" >
							试卷数
						</td>
						<td align="center" >
							&nbsp;
						</td>
					</tr>
					<s:iterator value="examRooms">
						<tr>
							<td align="center" >
								<s:property value="title" />
							</td>
							<td align="center" >
								<s:property value="location" />
							</td>
							<td align="center" >
								<s:property value="eroomLib.name" />
							</td>
							<td align="center" >
								<s:property value="passgrade" />
							</td>
							<td align="center" >
								<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="center" >
								<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="center" >
								<s:property value="typeName" />
							</td>
							<td align="center" >
								<s:property value="epsize" />
							</td>
							<td align="center" >
								<s:if test="hasuser">已经在该考场中/<a
										href="applyrooms_epaper_list.action?examRoom.id=<s:property value="id"/>">查看</a>
								</s:if>
								<s:else>
									<a onclick="return window.confirm('确定申请？');"
										href="applyroomsinit.action?examRoom.id=<s:property value="id"/>">申请</a>
								</s:else>
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
