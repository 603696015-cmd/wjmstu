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
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>险种详情</title>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>    
		<SCRIPT type="text/javascript">
			function myload(){
				if("${elmessage}"!=""){
				alert( "${elmessage}!");
				}
			
			} 
		</SCRIPT>	
	</HEAD>
	<BODY onload="myload();">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="增加险种信息" /></div>
			</li> 
		</ul>
		<!-- 内容 -->
		<s:form action="IC_U_add" method="post" name="IC_U_addinfo" theme="simple" > 
		<s:hidden name="IC.id"></s:hidden>
		<s:hidden name="IC.tableName"></s:hidden> 
		<div align="center">
				<table cellpadding="1" cellspacing="1" bgcolor="#ECEDEB" width="700">
					<tr>
						<td width="100" height="30" align="center" bgcolor="#FFFFFF">
							${view['KS_DISHI']}
						</td>
						<td bgcolor="#FFFFFF">
							<wysLib:GET iname="KS_DISHI"></wysLib:GET>
						</td>
						<td bgcolor="#FFFFFF">${view['KS_RIQI']}
							<wysLib:GET iname="KS_RIQI"></wysLib:GET>
						</td>
					</tr> 
					<tr>
						<td>${view['KS_XIALAZHI']}<wysLib:GET iname="KS_XIALAZHI"></wysLib:GET></td>
						<td></td>
						<td></td>
					</tr>
					</table>  
						<br>
						<input class="textbg6" name="submit" type="submit" value="确认添加" /> 
		</div>
		</s:form>
		<!-- 内容 -->
	</BODY>
</HTML>
