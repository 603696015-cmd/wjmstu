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
				<span style="font-weight: bold;">客观评价场次添加</span>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		 	<form action="talent_roomcollect_add.action" method="post">
			<table width="60%" cellpadding="1" cellspacing="1">
						<tr>
							<td>
								<strong>场次标题</strong>
							</td>
							<td height="30" align="left" >
								<label>
									<input type="text" name="troomcoll.title" size="30"
										value="" />
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<strong>描述</strong>
							</td>
							<td height="30" align="left" >
								<label>
								<textarea name="troomcoll.description" cols="40" rows="7"></textarea>
								</label>
							</td>
						</tr>
						<tr>
							<td width="120" height="50" align="center" >
							</td>
							<td height="50" align="left" >
								<input type="submit" value="确认添加">
							</td>
						</tr>
					</table>
					</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
