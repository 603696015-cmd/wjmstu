<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<title>会员中心-会员注册</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<SCRIPT type="text/javascript">
			function myload(){
				if("${elmessage}"!=""){
				alert( "${elmessage}!");
				}
			
			}
		
		</SCRIPT>	
		</HEAD>
	<BODY  onload="myload();" style="text-align: center;">
			<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="进行设置" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">注册设置</span>

			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="registerset" method="post" theme="simple"
				name="department_info" id="department_info">
				<table border="0" width="100%" cellpadding="0" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="220" height="30" align="center" bgcolor="#EBEBEB" >
							<strong>是否可注册</strong>
						</td>
						<td height="30" bgcolor="#EBEBEB">
							<s:radio list="#{1:'可以',0:'不可以'}" name="registerstatus"/>
						</td>
					</tr>
					<tr>
						<td width="220" height="30" align="center" bgcolor="#EBEBEB" >
							注册是否要审核
						</td>
						<td height="30" align="left" bgcolor="#EBEBEB" >
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="sysconf.register_need_sh"></s:radio>
						</td>
					</tr>
					<tr>
						<td width="220" height="30" align="center" bgcolor="#EBEBEB" >
							登录是否记录ip
						</td>
						<td height="30" align="left" bgcolor="#EBEBEB" >
							<s:radio list="#{'1':'记录','0':'不记录'}"
								name="sysconf.login_addip"></s:radio>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center" bgcolor="#EBEBEB" >
						  <label>
						    <input type="submit" value="保存设置"  class="textbg6">
						    </label>
					    </td>
					</tr>
				</table>
			</s:form>
		</div>
</HTML>
