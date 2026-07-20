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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>中国食品安全培训网--管理端--学员管理</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
	</HEAD>
	<BODY style="height: 100%; width: 100%">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">场次学员分配</span>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
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
					<td align="left" >
						<strong>创建时间</strong>
					</td>
					<td align="left" >
						<s:date name="troomcoll.createtime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td>
						<strong>场次标题</strong>
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
			</table>
			--><s:form action="talent_troom_assign_search" method="post" theme="simple"
				name="department_info" id="department_info">
				<table border="0" width="60%" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="120"  align="center" >
							所属部门：
						</td>
						<td >
							<s:hidden name="troomcoll.id" />
							<s:hidden name="troom.id" />
							<s:hidden name="pN" value="0" />
							<s:hidden name="pS" value="10" /><label>
								<select style="width: 300px" name="department.id" id="parentid">
									<wysLib:dep_select />
								</select>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120"  align="center" >
							<strong>包含下属部门</strong>
						</td>
						<td >
							<label>
								<input type="checkbox" name="sub_department" id="sub_department"
									value="1">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120"  align="center" >
							<strong>学号</strong>
						</td>
						<td >
							<label>
								<input type="text" name="elUser.username" id="username" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120"  align="center" >
							<strong>姓名</strong>
						</td>
						<td >
							<label>
								<input type="text" name="elUser.realname" id="name" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120"  align="center" >
							<strong>电子邮箱</strong>
						</td>
						<td >
							<label>
								<input type="text" name="elUser.email" id="email" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" align="center" >
							&nbsp;
						</td>
						<td >
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
	
	</body>
</HTML>
