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
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<SCRIPT type="text/javascript">
				function setParent(dd,id){
				document.getElementById("parentid"+dd).value=id;
			}
		</SCRIPT>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程搜索" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">课程统计</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="course_searchlist" method="post" name="course_info"
				theme="simple" id="course_info">
				<table cellpadding="1" cellspacing="1" >
					<tr>
						<td width="160" height="30" align="center" >
							所属类别：
						</td>
						<td >
							<label>
								<wysLib:ctypeTree itype="ra" iname="course.ctype.id" did="0"  />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							课程名称：
						</td>
						<td >
							<label>
								<input name="course.name" type="text" id="name"
									style="width: 300px;">
							</label>
						</td>
					</tr>

					<tr>
						<td width="160" height="50" align="center" >
						</td>
						<td >
							<input name="submit" type="submit" value="搜索" />
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
