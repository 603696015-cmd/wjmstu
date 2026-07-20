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
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训班列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">培训班通过率</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<table width="95%" cellpadding="2" cellspacing="2" bgcolor="#EBEBEB">
				<tr>
					<td align="center" >
						培训班名称
					</td>
					<td  align="center" >
						创建时间
					</td>
					<td  align="center" >
						学员人数
					</td>
					<td  align="center" >
						通过人数
					</td>
					<td align="center" >
						通过率
					</td>
					<td align="center" >
					</td>
				</tr>
				<s:iterator value="classes">
				<tr>
				<td align="center" >
						<s:property value="name"/>
					</td>
					<td align="center" >
						<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td align="center" >
								<s:property value="userCount"/>
					</td>
					<td align="center" >
						<s:property value="userPassedCount"/>
					</td>
					<td align="center" >
						<s:property value="passper"/>%
					</td>
					<td align="center" >
						<a href="gclass_dep_list.action?elclass.id=<s:property value="id"/>">查看部门排行</a>
					</td>
					</tr>
				</s:iterator>
			</table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
