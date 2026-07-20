<%@ page language="java" pageEncoding="UTF-8"%>
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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>操作提示</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<LINK href="elfrontimages/style.css" type=text/css rel=stylesheet>
		<script type="text/javascript" src="js/message.js"></script>
<script type="text/javascript">
	function winClose(){
		window.close();
	}
</script>
	</HEAD>
	<BODY >
		<input type="button" onclick="winClose();" value="关闭" />
	
	</body>
</HTML>