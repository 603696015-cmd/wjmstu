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
				<script type="text/javascript">
		function page(i){
			document.getElementById("pageNow").value = i;
			classInfo.submit();
		}
		</script>
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
		<s:if test="loginInfos.size()==0">
					<table width="900" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> <td height="200" align="center" bgcolor="#F8FCFE"><span class="STYLE2">暂 无 数 据 </span></td>
  </tr>
</table>  <p>&nbsp;</p>
				    <p>&nbsp;</p>
				</s:if>
	<s:else>
					<table width="900" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:20px;">
  <tr>
    <td><form action="classInfo.action" name="classInfo" method="post">
					<s:hidden name="pN" id="pageNow"></s:hidden>
					<s:hidden name="pS"></s:hidden>
					<s:hidden name="elClass.id"></s:hidden>
					</form>
					<table width="900" border="0" align="center" cellpadding="1"
					cellspacing="1" bgcolor="#CFDBE2">
						<tr>
							<td align="center" valign="middle" background="images/bg002.jpg" bgcolor="#F8FCFE"
								>
								
									用户名
								
						  </td>
							<td height="38" align="center" valign="middle" background="images/bg002.jpg" bgcolor="#F8FCFE"
								>
								
									姓名								
						  </td>
							<td align="center" valign="middle" background="images/bg002.jpg" bgcolor="#F8FCFE"
								>
							
									部门
								
						  </td>
							<td width="120" align="center" valign="middle" background="images/bg002.jpg" bgcolor="#F8FCFE"
								>
								
									登录时间
								
						  </td>
							<td width="120" align="center" valign="middle" background="images/bg002.jpg" bgcolor="#F8FCFE"
								>
								
									退出时间
								
						  </td>
						</tr>
						<s:iterator value="loginInfos">
							<tr>
								<td align="center" valign="middle" bgcolor="#F8FCFE"
									>
									<p align="center">
										<s:property value="elUser.username"/>
									</p>
							  </td>
								<td height="40" align="center" valign="middle" bgcolor="#F8FCFE"
									>
									<p align="center">
										<s:property value="elUser.realname"/>
									</p>
							  </td>
								<td align="center" valign="middle" bgcolor="#F8FCFE"
									>
									<p align="center">
										<s:property value="elUser.department.name"/>
									</p>
							  </td>
								<td width="120" align="center" valign="middle" bgcolor="#F8FCFE"
									>
									<p align="center">
										<s:date name="begintime" format="yyyy/MM/dd HH:mm" />
									</p>
							  </td>
								<td width="120" align="center" valign="middle" bgcolor="#F8FCFE"
									>
									<p align="center">
										<s:date name="endtime" format="yyyy/MM/dd HH:mm" />
									</p>
							  </td>
							</tr>
						</s:iterator>
						<tr>
							<td align="center" valign="middle" bgcolor="#F8FCFE"
								>
								<p align="center">
									合计
								</p>
						  </td>
							<td colspan=2 align="center" valign="middle" bgcolor="#F8FCFE"
								>
								<p align="center">
									学习时长：<s:property value="hour" />小时
								</p>
						  </td>
							<td width="240" colspan=2 align="center" valign="middle" bgcolor="#F8FCFE"
								>
								<p align="center">
									<s:if test="intelligentTutoringPoints.scoreClass>10">
										得分：<s:property value="intelligentTutoringPoints.scoreClass" />分（超过10分按照10分计算）
									</s:if>
									<s:else>
										得分：<s:property value="intelligentTutoringPoints.scoreClass" />分
									</s:else>
								</p>
						  </td>
						</tr>
					</table></td>
  </tr>
  <tr>
    <td height="50" align="center" bgcolor="#F8FCFE"><wysLib:page></wysLib:page></td>
  </tr>
</table>

					
					
					
				   
				    <p>&nbsp;</p>
				    <p>&nbsp;</p>
	</s:else>
		
				
			

	</body>
</html>



