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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考生搜索" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考场人员分配</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="examroom_assignuserlist.action?examPaper.id=<s:property value="examPaper.id"/>&examRoom.id=<s:property value="examRoom.id"/>">考场人员</a>
			</li>
			<s:if test="optype!='valid'">
				<li class="sep">
				</li>
				<li>
					<a style="cursor: hand"
						onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
						onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
						href="examroomwithoutcourse_list.action">考试管理</a>
				</li>
			</s:if>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<h3>
				为 考场【
				<s:property value="examRoom.title" />
				】中的试卷【
				<s:property value="examPaper.title" />
				】添加考生
			</h3>
			<s:form action="examroom_assignSearchlist" method="post"
				theme="simple" name="department_info" id="department_info">
				<table border="0" width="100%" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="120" align="center" >
							搜索范围：
						</td>
						<td >
							<label>
								<s:hidden name="examPaper.id" />
								<s:hidden name="examRoom.id" />
								<s:hidden name="optype"></s:hidden>
								<s:hidden name="pN" value="0" />
								<s:hidden name="pS" value="10" />
								&nbsp;<select style="width: 300px" name="department.id" id="parentid">
									<wysLib:dep_select />
								</select>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" align="center" >
							<strong>包含下属部门</strong>
						</td>
						<td >
							&nbsp;<label>
								<input type="checkbox" name="sub_department" id="sub_department"
									value="1">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" align="center" >
							<strong>账号</strong>
						</td>
						<td >
							&nbsp;<label>
								<input type="text" name="elUser.username" id="username" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" align="center" >
							<strong>姓名</strong>
						</td>
						<td >
							&nbsp;<label>
								<input type="text" name="elUser.realname" id="name" value="">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" align="center" >&nbsp;
							
						</td>
						<td >
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="50">
										&nbsp;<input type="submit" value="搜索">
									</td>
									<td width="20">&nbsp;
										
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
