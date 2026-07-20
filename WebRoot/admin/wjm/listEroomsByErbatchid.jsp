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
		<title>考试培训批次考场列表</title>

		<link href="css/style2013.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value = i;
				examrooms.submit();
			}
			
		</script>

		<style type="text/css">
<!--
.STYLE2 {
	font-weight: bold;
}

.STYLE5 {
	font-size: 14px;
	font-weight: bold;
	color: #FF0000;
}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
	</head>

	<body>
	<table width="1044" border="0" align="center" cellpadding="0" cellspacing="0" style="background-image: url(http://www.fhse.net/wjm/images/20140416/cent_bg3.png);background-repeat: no-repeat;background-position: center top;">
  <tr>
    <td height="550" valign="top" style="padding-top:40px;">


		<table width="800" border="0" align="center" cellpadding="0"
			cellspacing="0" style="margin-top: 8px; margin-bottom: 5px;">
			<tr>
				<td height="240" align="center" valign="top" bgcolor="#F8FCFE"
					style="padding-bottom: 10px;">
					<s:if test="examRooms.size() == 0">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="300" align="center" valign="middle">
									暂无模拟考试
								</td>
							</tr>
						</table>
					</s:if>
					<s:else>
						<form action="listEroomsByErbatchid.action" name="examrooms"
							method="post">
							<s:hidden name="pN" id="pageNow"></s:hidden>
							<s:hidden name="pS"></s:hidden>
							<s:hidden name="erbatch.id"></s:hidden>
							<table width="100%" border="0" cellpadding="5" cellspacing="1"
								bgcolor="#CFDBE2">
								<tr>
									<td align="center" background="images/bg002.jpg"
										bgcolor="#E9F5FC" style="padding-left: 25px;">
										<span class="STYLE5">标 题 </span>
									</td>
									<td width="150" height="30" align="center"
										background="images/bg002.jpg" bgcolor="#E9F5FC">
										<span class="STYLE5">成 绩 </span>
									</td>
									<td align="center" background="images/bg002.jpg"
										bgcolor="#E9F5FC">
										<span class="STYLE5">是否通过 </span>
									</td>
									<td width="120" align="center" background="images/bg002.jpg"
										bgcolor="#E9F5FC">&nbsp;
										
								  </td>
								</tr>
								<s:iterator value="examRooms">
									<tr>
										<td height="25" align="center" valign="middle" bgcolor="#F8FCFE">
											<s:property value="title" />
									  </td>
										<td width="150" height="25" align="center" bgcolor="#F8FCFE">
											<s:property value="score" />
									  </td>
										<td width="150" height="25" align="center" bgcolor="#F8FCFE">
											<s:if test="isPassed==1">
												<span class="STYLE2">通过</span>
											</s:if>
											<s:else>
												<span class="STYLE2">未通过</span>
											</s:else>
									  </td>
										<td width="120" height="25" align="center" bgcolor="#F8FCFE">
											<table width="95" border="0" align="center" cellpadding="0"
												cellspacing="0">
												<tr>
													<td height="28" align="center" valign="middle"
														background="images/textbg.jpg">
														<a
															href="quizpaperinit.action?myroom.examroom.id=<s:property value="id"/>"><span
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
						</form>
					</s:else>
					<wysLib:page></wysLib:page>
				</td>
			</tr>
	  </table>
		
		
		
		
	</td>
  </tr>
</table>


	</body>
</html>


