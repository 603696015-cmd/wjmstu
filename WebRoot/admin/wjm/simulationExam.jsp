<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="newversionLib" uri="/WEB-INF/newversionLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>考试培训批次列表</title>
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />

		<style type="text/css">
<!--
.STYLE1 {
	font-size: 14px;
	font-weight: bold;
	color: #FF0000;
}
-->
</style>
	</head>

	<body>
		<table width="800" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td height="50">
					&nbsp;
				</td>
			</tr>
		</table>
		<table width="900" border="0" align="center" cellpadding="0"
			cellspacing="0" style="margin-top: 30px; margin-bottom: 0px;">
			<tr>
				<td valign="top" bgcolor="#F8FCFE">



					<s:if test="erbatchs.size() == 0">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="300" align="center" valign="middle">
									暂无模拟考试
								</td>
							</tr>
						</table>
					</s:if>
					<s:else>


						<table width="100%" border="0" cellpadding="5" cellspacing="1"
							bgcolor="#CFDBE2">
							<tr>
								<td align="left" background="images/bg002.jpg" bgcolor="#E9F5FC"
									style="padding-left: 25px;">
									<span class="STYLE1">模考类别</span>
								</td>
								<td width="150" height="30" align="center"
									background="images/bg002.jpg" bgcolor="#E9F5FC" class="STYLE1">
									进 度
								</td>
								<td width="60" align="center" background="images/bg002.jpg"
									bgcolor="#E9F5FC">
									&nbsp;
								</td>
							</tr>
							<s:iterator value="erbatchs">
								<tr>
									<td valign="middle" bgcolor="#F8FCFE">
										<s:property value="title" />
									</td>
									<td width="150" height="40" align="left" bgcolor="#F8FCFE">
										<div
											style="BORDER-BOTTOM: #ff6633 1px dotted; BORDER-LEFT: #ff6633 1px dotted; BORDER-TOP: #ff6633 1px dotted; BORDER-RIGHT: #ff6633 1px dotted">
											<img src="images/jd.gif"
												width="<s:property value="process" />%" height="14" />
										</div>
									</td>
									<td align="center" bgcolor="#F8FCFE">
										<table width="95" border="0" align="center" cellpadding="0"
											cellspacing="0">
											<tr>
												<td height="28" align="center" valign="middle"
													background="images/textbg.jpg">
													<a
														href="listEroomsByErbatchid.action?erbatch.id=<s:property value="id" />"><span
														style="font-size: 14px; font-weight: bold; color: white;">详
															情</span>
													</a>
												</td>
											</tr>
										</table>

									</td>
								</tr>
							</s:iterator>
						</table>
					</s:else>
				</td>
			</tr>
		</table>



		<p>
			&nbsp;

		</p>
		<p>
			&nbsp;

		</p>
		<p>
			&nbsp;

		</p>
		<p>
			&nbsp;

		</p>
		<p>
			&nbsp;

		</p>
		<p>
			&nbsp;

		</p>
		<p>
			&nbsp;

		</p>
		<p>
			&nbsp;

		</p>
	</body>
</html>


