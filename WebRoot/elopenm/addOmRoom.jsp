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
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/calendar.js"></script>
	</HEAD>
	<body>
	<ul class="nav">
			<li>
			<s:if test="room.roomtype==1"><span style="font-weight: bold;">创建会议</span></s:if>
			<s:if test="room.roomtype==2"><span style="font-weight: bold;">创建课堂</span></s:if>
			</li>
		</ul>
		<s:form action="addOmRoom" method="post" name="catalog_info"
			theme="simple">
			<table width="70%" cellpadding="2" cellspacing="1"
				bgcolor="#ECEDEB">
				<tr>
					<td height="30" align="center" bgcolor="#FFFFFF">
						<s:if test="room.roomtype==1">会议名称</s:if>
							<s:if test="room.roomtype==2">课堂名称</s:if>
					</td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:textfield name="room.name" id="name" size="60" />
							<s:hidden name="room.roomtype" id="name" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="center" bgcolor="#FFFFFF">
						<s:if test="room.roomtype==1">会议</s:if>
							<s:if test="room.roomtype==2">课堂</s:if>说明
					</td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:textarea name="room.comment" cols="60" rows="7" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="center" bgcolor="#FFFFFF">
						<s:if test="room.roomtype==1">会议</s:if>
							<s:if test="room.roomtype==2">课堂</s:if>开始时间
					</td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:textfield name="room.starttime"  onclick='setday(this)' />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="center" bgcolor="#FFFFFF">
						<s:if test="room.roomtype==1">会议</s:if>
							<s:if test="room.roomtype==2">课堂</s:if>结束时间
					</td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:textfield name="room.updatetime"  onclick='setday(this)' />
						</label>
					</td>
				</tr>
				<tr>
					<td height="50" align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
					<td bgcolor="#FFFFFF">
						<input type="submit" value="确认添加">
					</td>
				</tr>
			</table>
			<br>
		</s:form>
	</body>
</HTML>
