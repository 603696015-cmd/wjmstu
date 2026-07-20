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
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript">
			function check(){
				var address = document.getElementById("macaddress").value;
				if(address==""){
					alert("请填写Mac地址");
				}else{
					macadd.submit();
				}
				
			}
		</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="Mac地址" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<form action="mac_add.action" method="post" name="macadd">
				<table width="60%" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<th width="81%" height="30" align="center" >
						Mac地址					</th>
					
					<th width="19%" height="30" align="center" >					</th>
				</tr>
					<tr>
						<td height="30" align="center">
							<input id="macaddress" name="mac.macaddres" type="text" style=" width : 217px; height : 31px;">
						</td>
						<td height="30" align="center" >
						</td>
					</tr>
		  </table>
				<div>
					<input type="button" value="确认添加" onclick="check()">
				</div>
			</form>
		</div>
			
		<!-- 内容 -->
	</BODY>
</HTML>