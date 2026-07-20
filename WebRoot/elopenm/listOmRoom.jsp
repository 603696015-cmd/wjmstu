<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<base href="<%=basePath%>">
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>五矿发展员工职业发展系统--管理端--学员添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
	</HEAD>
	<BODY>
		<ul class="nav">
			<li>
				<s:if test="room.roomtype==1">会议</s:if>
				<s:if test="room.roomtype==2">课堂</s:if>列表
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 40px; text-align: center;">
		 <table align="center" cellpadding="1" cellspacing="1" width="100%" 
				bgcolor="#ECEDEB">
				<tr>
					<th>
						<s:if test="room.roomtype==1">会议名称</s:if>
						<s:if test="room.roomtype==2">课堂名称</s:if>
					</th>
					<th>
							<s:if test="room.roomtype==1">会议</s:if>
				<s:if test="room.roomtype==2">课堂</s:if>开始时间
					</th>
					 <th>
							<s:if test="room.roomtype==1">会议</s:if>
				<s:if test="room.roomtype==2">课堂</s:if>结束时间
					</th>
					 <th>
						 分配学员
					</th>
				</tr>
				<s:iterator value="rooms">
					<tr>
						<td height="20" align="center">
							<s:property value="name" />
						</td>
						<td height="20" align="center">
							<s:date name="starttime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						 <td height="20" align="center">
							<s:date name="updatetime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						 <td height="20" align="center">
							<a href="om_assign_list.action?room.id=<s:property value="id"/>&room.roomtype=<s:property value="room.roomtype"/>">分配</a>
						</td>
					</tr>
				</s:iterator>
			</table> 
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
