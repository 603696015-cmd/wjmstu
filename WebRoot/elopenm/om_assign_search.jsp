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
		<TITLE>五矿发展员工职业发展系统--管理端--学员管理</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
	</HEAD>
	<BODY style="height: 100%; width: 100%">
		<ul class="nav">
			<li>
				<span style="font-weight: bold;"><s:if test="room.roomtype==1">会议</s:if>
				<s:if test="room.roomtype==2">课堂</s:if>学员分配</span>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 10px;">
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
			--><s:form action="om_assign_search" method="post" theme="simple"
				name="department_info" id="department_info">
				<table border="0" width="60%" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="120"  align="center" bgcolor="#FFFFFF">
							所属部门：
						</td>
						<td bgcolor="#FFFFFF">
							<s:hidden name="ztroom.id" />
							<s:hidden name="pN" value="0" />
							<s:hidden name="pS" value="10" />
							<s:hidden name="room.id" /><label>
								<select style="width: 300px" name="department.id" id="parentid">
									<wysLib:dep_select />
								</select>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120"  align="center" bgcolor="#FFFFFF">
							<strong>包含下属部门</strong>
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<input type="checkbox" name="sub_department" id="sub_department"
									value="1">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120"  align="center" bgcolor="#FFFFFF">
							<strong>学号</strong>
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<input type="text" name="elUser.username" id="username" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120"  align="center" bgcolor="#FFFFFF">
							<strong>姓名</strong>
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<input type="text" name="elUser.realname" id="name" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120"  align="center" bgcolor="#FFFFFF">
							<strong>电子邮箱</strong>
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<input type="text" name="elUser.email" id="email" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" align="center" bgcolor="#FFFFFF">
							&nbsp;
						</td>
						<td bgcolor="#FFFFFF">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="50">
										<input type="submit" value="搜索">
									</td>
									<td width="20">
										&nbsp;
									</td>
									<td>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:form>

		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
