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
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="新闻公告简介" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">新闻公告名称</span>
			</li>-->
		</ul>
			<table width="90%" cellpadding="2" align="center" cellspacing="1" >
				<tr>
					<td  align="center" >
						新闻公告名称
					</td>
					<td >
						<label>
							<s:property value="news.title"  />
						</label>
					</td>
				</tr>
				<tr>
					<td  align="center" >
						所属栏目
					</td>
					<td >
						<label>
							<s:property value="news.ntype.name"/>
						</label>
					</td>
				</tr>
				<tr>
					<td  align="center" >
						发布时间
					</td>
					<td >
						<label>
							<s:date  name="news.releasetime" format="yyyy-MM-dd HH:mm:ss"/>
						</label>
					</td>
				</tr>
				<tr height="500px" valign="top">
					<td align="center"
						>
						新闻公告内容
					</td>
					<td align="center"
						>
						${news.content }
					</td>
				</tr>
			</table>
	
	</body>
</HTML>
