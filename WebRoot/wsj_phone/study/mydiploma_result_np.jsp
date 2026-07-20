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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">未通过的证书</span>
			</li>
				<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="mydiploma_result_p.action"> 通过的证书 </a>
			</li>-->
		</ul> 
		<table width="76%" align="center" cellpadding="2" cellspacing="1"
			>
			<tr>
				<th align="center" >
					培训班名称
				</th>
				<th align="center" >
					证书名称
				</th>
				<th align="center" >
					学员姓名
				</th>
			</tr>
			<s:iterator value="myClasses">
			<tr>
				<td align="center" >
					<s:property value="elClass.name"/>
				</td>
				<td align="center" >
					<s:property value="elClass.certificatename"/>
				</td>
				<td align="center" >
					<s:property value="user.realname"/> 
				</td>
			</tr>
			</s:iterator>
		</table>
		<Br>
		<br>
	
	
	</body>
</HTML>
