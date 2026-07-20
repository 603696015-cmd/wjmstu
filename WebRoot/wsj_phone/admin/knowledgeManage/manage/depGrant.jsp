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
		<TITLE>部门授权成功</TITLE>
		<base target="_self" href="<%=basePath%>">
		<script type="text/javascript">
			function init(){
				document.forms[0].submit();
			}
		</script>
	</HEAD>
	<body onload="init();">
		OK！！！
		<form action="updateKledgeInit.action" method="post">
			<s:hidden name="kledge.id"></s:hidden>
		</form>
	
	</body>
</HTML>
