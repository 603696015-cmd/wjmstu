<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>">
		<title>用户登录 - 中国食品安全培训网</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/login.css" type="text/css" rel=stylesheet>
		<script type="text/javascript">
			function load(){
				if(document.parentWindow.name=='rightFrame') 
				this.parent.location.href='login.jsp';
				document.getElementById("username").focus();
			}
	</script>
	<style type="text/css">
		input {
	border: 1px solid buttonface;
}
	</style>
	</HEAD>
	<body onLoad="load();">
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0" bgcolor="#08AADB">
			<tr>
				<td valign="top">
					<table width="1000" height="564" border="0" align="center"
						cellpadding="0" cellspacing="0" class="loginbg">
						<tr>
							<td valign="top">
								<table width="1000" height="564">
									<tr>
										<td width="525" height="283">
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td width="525">
											&nbsp;
										</td>
										<td valign="top" align="left">
											<form name="myform" method="post" action="login.action" style="padding: 0px;margin: 0px;">
											<table>
												<tr>
													<td>
														<input id=username type="text" style="width: 160px"
															name="elUser.username" value="${elUser.username }" />
													</td>
												</tr>
												<tr>
													<td>
														<input type="password" style="width: 160px"
															name="elUser.password" />
													</td>
												</tr>
												<tr>
													<td>
														<INPUT type="text" style="width: 60px" name="yzCode">&nbsp;&nbsp;<IMG style="cursor: hand;" height="22" width="80" src="image.jsp" onClick="this.src='image.jsp?'+Math.random()" title="点击刷新验证码" >
													</td>
												</tr>
												<tr>
													<td>
														<INPUT type="image" src="images/tm.gif" style="border: none;" >&nbsp;&nbsp;&nbsp;<a href="registerInit.action"><img src="images/tm.gif"  border="0"/> </a>
													</td>
												</tr>
											</table>
											</form>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<SCRIPT type="text/javascript">
			if("${elmessage}"!='null'&&"${elmessage}"!='')
				 alert("${elmessage}!");
		</SCRIPT>
	
	</body>
</html>