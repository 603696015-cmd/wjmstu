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
		<title>个人中心新首页</title>
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/index.css" rel="stylesheet" type="text/css" />
		<link type="text/css" href="css/base.css" rel="stylesheet" />
		<link type="text/css" href="css/qhIndex.css" rel="stylesheet" />
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />
		<style>
#ddd img {
	display: block;
}

.STYLE1 {
	font-size: 36px;
	font-weight: bold;
}
</style>

	</head>

	<body >
		<s:if test="proportion == null">
					<table width="900" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> <td height="200" align="center" bgcolor="#F8FCFE"><span class="STYLE2">暂 无 数 据 </span></td>
  </tr>
</table>
<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				</s:if>
				<s:else>
					<table width="900" border="0" align="center" cellpadding="1"
					cellspacing="1" bgcolor="#CFDBE2" style="margin-top:60px;">
						<tr>
							<td align="center" valign="middle" background="images/bg002.jpg"
								>
								
									复听题数
								
						  </td>
							<td height="38" align="center" valign="middle" background="images/bg002.jpg"
								>
								
									总题数						  </td>
							<td align="center" valign="middle" background="images/bg002.jpg"
								>
								
									复听比例
								
						  </td>
							<td width="120" align="center" valign="middle" background="images/bg002.jpg"
								>
								
									得分
								
						  </td>
						</tr>
						<tr>
							<td align="center" valign="middle" bgcolor="#F8FCFE"
								>
								<p align="center">
									<s:property value="proportion.tcount"/>
								</p>
						  </td>
							<td height="40" align="center" valign="middle" bgcolor="#F8FCFE"
								>
								<p align="center">
									<s:property value="proportion.totalcount"/>
								</p>
						  </td>
							<td align="center" valign="middle" bgcolor="#F8FCFE"
								>
								<p align="center">
									<s:property value="proportion.tprocess"/>%
								</p>
						  </td>
							<td width="120" align="center" valign="middle" bgcolor="#F8FCFE"
								>
								<p align="center">
									<s:property value="proportion.tscore"/>
								</p>
						  </td>
						</tr>
				  </table>
				  <p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p><p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				
				</s:else>
		
				
			

	</body>
</html>



