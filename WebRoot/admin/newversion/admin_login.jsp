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
		<base href="<%=basePath%>">
		<title>商务汉语学习系统</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link href="http://www.fhse.net/wjm/css/20140416/login.css" type="text/css" rel=stylesheet>
		<script type="text/javascript">
			function load(){
				if(document.parentWindow.name=='rightFrame') 
				this.parent.location.href='admin/newversion/wjm_user_center_login.jsp';
				document.getElementById("username").focus();
			}
	</script>
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	
	
	
	<style type="text/css">
<!--
body {
	margin: 0;
	padding: 0;
	border: 0;
}
-->
</style></HEAD>
<body  style="overflow-x:hidden;overflow-y:hidden" onLoad="load();">

<form name="myform" method="post" action="wjm_admin_login.action" style="padding: 0px;margin: 0px;">
				<input type="hidden" name="ipAddress" id="ipAddress"/>
				<input type="hidden" name="myLogin.ipAddr" id="ipAddr" value="${requestScope.myLogin.ipAddr }" />

	

	<table width="1250" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="185">&nbsp;</td>
      </tr>
      <tr>
        <td height="65" align="right">
		  <a href="javascript:window.opener=null;window.open('','_self');window.close();" style="padding-right:15px;"><img src="images/20140416/kongtu.gif" width="60" height="65" border="0"></a>		</td>
      </tr>
      <tr>
        <td height="81" align="right">&nbsp;</td>
      </tr>
      <tr>
        <td height="34" align="right">
		  <INPUT id=username type="text" maxLength=30 name="elUser.username" value="${elUser.username}" style="width:223px;height:42px;border:none;background:none;padding-top:3px;padding-left:10px;padding-bottom:16px;font-size:25px;line-height:33px;">		</td>
      </tr>
      <tr>
        <td height="18" align="right">&nbsp;</td>
      </tr>
      <tr>
        <td height="35" align="right">
		  <INPUT type="password" maxLength=30 name="elUser.password" style="width:223px;height:42px;border:none;background:none;padding-top:8px;padding-left:10px;font-size:25px;line-height:20px;">		</td>
      </tr>
      <tr>
        <td height="36" align="right">&nbsp;</td>
      </tr>
      <tr>
        <td height="53" align="right">
		  <INPUT type=submit value="" name=submit style="width:299px;height:52px;background:none;border:none;">		</td>
      </tr>
    </table>
	
</form>

	
	<SCRIPT type="text/javascript">
			if("${elmessage}"!='null'&&"${elmessage}"!='')
				 alert("${elmessage}!");
		</SCRIPT>
		
		
		
</body>
</html>