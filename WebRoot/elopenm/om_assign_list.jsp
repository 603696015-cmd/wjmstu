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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
	</HEAD>
	<BODY>
		<ul class="nav">
			<li>
				<span style="font-weight: bold;">分配学员</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="om_assign_searchInit.action?room.id=<s:property value="room.id"/>">分配新学员</a>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 6px; text-align: center;">
			<!--<table width="60%" cellpadding="1" cellspacing="1">
				<caption>
					场次信息
				</caption>
				<tr>
					<td>
						<strong>场次集标题</strong>
					</td>
					<td>
						<s:property value="troomcoll.title" />
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#FFFFFF">
						<strong>创建时间</strong>
					</td>
					<td align="left" bgcolor="#FFFFFF">
						<s:date name="troomcoll.createtime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td>
						<strong>场次标题</strong>
					</td>
					<td align="left" bgcolor="#FFFFFF">
						<label>
							<s:property value="troom.title" />
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<strong>开始结束时间</strong>
					</td>
					<td align="left" bgcolor="#FFFFFF">
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
					<td align="left" bgcolor="#FFFFFF">
						<label id="eptitle" style="width: 200px;">
							<s:property value="troom.exampaper.title" />
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<strong>测评指标</strong>
					</td>
					<td align="left" bgcolor="#FFFFFF">
						<div id="trnorms" style="width: 100%;">
							<s:iterator status="normosst" value="troom.norms">
								<s:property /> ，
									</s:iterator>
						</div>
					</td>
				</tr>
			</table>
			--><s:if test="assignedUsers.size==0">暂无分配学员</s:if>
			<s:else>
			<table width="60%" cellpadding="1" cellspacing="1">
					<tr>
					<th>
						学号
					</th>
					<th>
						姓名
					</th>
					<th>
						部门
					</th>
					<th>
					</th>
					</tr>
					<s:iterator value="elUsers">
					<tr>
					<td height="20" align="center">
							<s:property value="username" />
						</td>
						<td height="20" align="center">
							<s:property value="realname" />
						</td>
						<td height="20" align="center">
							<s:property value="department.name" />
						</td>
						<td height="20" align="center">
							<a href="om_assign_delete.action?room.id=<s:property value="room.id"/>&elUser.id=<s:property value="id"/>">删除</a>
						</td>
					</tr>
					</s:iterator>
				</table>
				</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
