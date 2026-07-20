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
			<li>
				<span style="font-weight: bold;">场次考试管理</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="talent_troom_addInit.action?troomcoll.id=<s:property value="troomcoll.id"/>">场次考试添加</a>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="60%" cellpadding="1" cellspacing="1">
				<caption>
					客观测评场次信息
				</caption>
				<tr>
					<td>
						<strong>标题</strong>
					</td>
					<td>
						<s:property value="troomcoll.title" />
					</td>
				</tr>
				<tr>
					<td height="30" align="left" >
						<strong>描述</strong>
					</td>
					<td>
						<s:property value="troomcoll.description" />
					</td>
				</tr>
				<tr>
					<td height="30" align="left" >
						<strong>创建时间</strong>
					</td>
					<td height="30" align="left" >
						<s:date name="troomcoll.createtime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td height="30" align="left" >
					</td>
					<td height="30" align="left" >
					<a href="talent_roomcollect_alterInit.action?troomcoll.id=<s:property value="troomcoll.id"/>">修改</a>
					</td>
				</tr>
			</table>
			<s:if test="troomcoll.trooms.size!=0">
			<table width="90%" cellpadding="1" cellspacing="1">
				<caption>
					考试管理
				</caption>
				<tr>
					<th>
						<strong>标题</strong>
					</th>
					<th height="30" align="left" >
						<strong>描述</strong>
					</th>
					<th height="30" align="left" >
						<strong>开始时间</strong>
					</th>
					<th height="30" align="left" >
						<strong>结束时间</strong>
					</th>
					<th height="30" align="left" >
						<strong>试卷</strong>
					</th>
					<th height="30" align="left" >
					</th>
				</tr>
				<s:iterator value="troomcoll.trooms">
					<tr>
						<td>
							<s:property value="title" />
						</td>
						<td height="30" align="left" >
							<s:property value="description" />
						</td>
						<td height="30" align="left" >
							<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td height="30" align="left" >
							<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td height="30" align="left" >
							<s:property value="exampaper.title"/>
						</td>
						<td height="30" align="left" >
							<a
								href="talent_troom_alterInit.action?troomcoll.id=<s:property value="troomcoll.id"/>&troom.id=<s:property value="id"/>">考试修改</a>
						</td>
					</tr>
				</s:iterator>
			</table>
			</s:if>
			<s:else>
				该场次集下没有场次..
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
