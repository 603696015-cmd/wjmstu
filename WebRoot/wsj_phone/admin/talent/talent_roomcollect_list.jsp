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
				<span style="font-weight: bold;"> 我创建的客观评价场次 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="talent_roomcollect_addInit.action">客观评价场次添加</a>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		 
			<table width="60%" cellpadding="1" cellspacing="1">
						<tr>
							<th>
								<strong>标题</strong>
							</th>
							<th height="30" align="left" >
								<strong>描述</strong>
							</th>
							<th height="30" align="left" >
								<strong>创建时间</strong>
							</th>
							<th height="30" align="left" >
							</th>
							<th height="30" align="left" >
							</th>
						</tr>
						<s:iterator value="troomcolls"> 
						<tr>
							<td>
								<s:property value="title"/>
							</td>
							<td height="30" align="left" >
							<s:property value="description"/>
							</td>
							<td height="30" align="left" >
							<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td height="30" align="left" >
							<a href="talent_troom_list.action?troomcoll.id=<s:property value="id"/>">试卷管理</a>
							</td>
							<td height="30" align="left" >
							<a
								href="talent_troom_assign_list.action?troomcoll.id=<s:property value="id"/>">分配学员</a>
						</td>
						</tr>
						 </s:iterator>
			</table>
			<wysLib:page></wysLib:page>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
