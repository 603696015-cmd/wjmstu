<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<title>用户注册 -五矿发展员工职业发展系统</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="js/calendar.js"></script>
		<link href="css/login.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
<!--
.STYLE1 {
	font-family: "宋体"
}

.td_left {
	color: red;
	font-size: 12px;
}

input {
	border: 1px solid;
}
body {
	background-color: #F0F0F0;
}
-->
</style>
	</HEAD>
	<BODY>
		<table width="100%" height="564" border="0" cellpadding="0"
			cellspacing="0" background="images/regbgline.jpg">
			<tr>
				<td valign="top">
					<table width="1000" height="564" border="0" align="center"
						cellpadding="0" cellspacing="0" class="regbg">
						<tr>
							<td valign="top">
								<table width="1000" height="564" align="center">
									<tr>
										<td width="320">
										</td>
										<td valign="top" style="padding-top: 20px;">
											<s:form action="pkiregister.action" method="post" theme="simple">
												<span style="font-size: 13px; color: #FFFFFF"></span>
												<table width="500" cellpadding="0" cellspacing="2"
													bgcolor="#FFFFFF" id="info1" style="margin-top: 140px;">
													<!--
													<tr>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong>用户名</strong>
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<label>
																<s:textfield name="elUser.username" id="username" />
															</label>
														</td>
													</tr>
													-->
													<tr valign="middle">
														<td height="50" align="center"
															bgcolor="#A6E2FF" class="td_left" style="font-size: 18px; font-weight: bolder;" colspan="2">
														 	系统未找到该pki信息.请注册.
														 		<s:hidden name="elUser.username" />
															 		<s:hidden name="elUser.password" value="1111111"/>
															 		<s:hidden name="elUser.sex" value="1"/>
															 		<s:hidden name="elUser.jy" value="1"/>
														</td>
													</tr>
													<tr>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong>姓 名</strong>
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<label>
																<s:textfield readonly="readonly" name="elUser.realname" id="realname" />
															</label>
														</td>
													</tr>
													<tr>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong> 身份证号 </strong>
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<label>
																<s:textfield readonly="readonly" name="elUser.shenfenzheng" />
															</label>
														</td>
													</tr>
													<tr>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong>单位代码</strong>
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<label>
																<s:textfield readonly="readonly" name="elUser.danwei" />
															</label>
														</td>
													</tr>
													<tr>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<input type="submit" value="提交" />
														</td>
													</tr>
												</table>
												<s:hidden name="elUser.role.id" value="4" />
												<s:hidden name="elUser.department.id" value="1" />
											</s:form>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</BODY>
</HTML>
