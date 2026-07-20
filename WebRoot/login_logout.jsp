<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>">
		<title>用户登录注销- 五矿发展员工职业发展系统</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	 
	</HEAD>
	<body>
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0" >
			<tr>
				<td valign="top">
					<table width="1000" height="564" border="0" align="center"
						cellpadding="0" cellspacing="0" class="loginbg">
						<tr>
							<td valign="top">
								<table width="1000" height="564">
									<tr>
										<td width="225">
											&nbsp;

										</td>
										<td valign="top" align="left"> 
											<form name="myform" method="post" action="login_logout.action"
												style="padding: 0px; margin: 0px;">
												<input type="hidden" name="myLogin.ipAddr" id="ipAddr" value="${requestScope.myLogin.ipAddr }" />
												<input type="hidden" name="ispki" value="${requestScope.ispki }" />
												<h3>该账号由于未安全退出系统或同时在另一处登录，导致您不能登录，如需要登录请注销当前系统该账号信息！</h3>
												<table>
													<s:if test="#request.ispki=='yes'">
														<s:hidden name="elUser.username" />
														用户名：${elUser.username}
													</s:if>
													<s:else>
													<tr>
														<td style="width:80px">
															用户名：
														</td>
														<td>
															<input id=username type="text" style="width: 160px"
																name="elUser.username" value="${elUser.username}" />
														</td>
													</tr>
													<tr>
														<td style="width:80px">
															密码：
														</td>
														<td>
															<input type="password" style="width: 160px"
																name="elUser.password" />
														</td>
													</tr>
													</s:else>
													<%-- 
													<tr>
														<td>
															<INPUT type="text"
																style="width: 60px; margin-bottom: 4px;" name="yzCode">
															&nbsp;&nbsp;
															<IMG style="cursor: hand;" height="19" width="80"
																src="image.jsp"
																onClick="this.src='image.jsp?'+Math.random()"
																title="点击刷新验证码">
														</td>
													</tr>
													 --%>
													<tr>
														<td>
															<INPUT type="submit" value="注销" >
														</td>
													</tr>
													<tr>
														<td>
															<s:if test="myLogin.id>0&&myLogin.ipAddr!=null">
																<div style="font-size:13px;">
																当前正在使用该账号的客户端IP是：<s:property value="myLogin.ipAddr"/>
																</div>
															</s:if>
															<s:else>
																上次登录未记录ip
															</s:else>
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