<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>">
		<TITLE>在线培训系统--管理端--</TITLE>
		<META http-equiv=Pragma content=no-cache>
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<style>
body {
	overflow: hidden;
}
</style>
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	text-align: center;
}

.STYLE1 {
	font-size: 12px;
	color: #FFFFFF;
}

.STYLE3 {
	font-size: 12px;
	color: #033d61;
	width: 100%;
	height: 100%;
	padding-top: 3px;
}

a {
	text-decoration: none;
	color: red;
}
-->
</style>
		<style type="text/css">
.menu_title SPAN {
	FONT-WEIGHT: bold;
	LEFT: 3px;
	COLOR: #ffffff;
	POSITION: relative;
	TOP: 2px
}

.menu_title2 SPAN {
	FONT-WEIGHT: bold;
	LEFT: 3px;
	COLOR: #FFCC00;
	POSITION: relative;
	TOP: 2px
}
</style>
	</HEAD>
	<body>
		<table width="100%" height="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td height="58px">
					<%@include file="header.jsp" %>
				</td>
			</tr>
			<tr>
				<td height="100%">
					<script type="text/javascript">
					function switchLeft(obj){
						if(left_menu.style.display=="none"){
							left_menu.style.display="block";
							obj.src="images/leftmenu/main_55.gif";
						}
						else{
							left_menu.style.display="none";
							obj.src="images/leftmenu/main_55_1.gif";
						}
					}
				</script>
					<div style="width: 100%; height: 100%; margin: 0px;">
						<table width="100%" height="100%"
							style="margin: 0px; border: 0px;" cellpadding="0" cellspacing="0">
							<tr>
								<td width="165px" valign="top" height="100%" id="left_menu"
									align="center" bgcolor="#cfeeee">
									<div style="width: 165; text-align: center;padding: 0px;">
										<script type="text/javascript">
										//initMenu("<%=basePath%>",0 );
										//showMenu("common");
										</script>
									</div>
								</td>
								<td bgcolor="#1873aa" width=5>
									<img src="images/leftmenu/main_55.gif" style="cursor: hand"
										onclick="switchLeft(this)" />
								</td>
								<td valign="top" bgcolor="#defddf" style="font: 12px;text-align: center">
								<br>
								<br>
									${elmessage }<br><a href="javascript:window.history.back(-1)">返回</a>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>