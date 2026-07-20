<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="expires" content="Tue, 20 Aug 1999 01:00:00 GMT">
		<meta http-equiv="Pragma" content="no-cache">
		<title>SCORM 1.2.2 LMS</title>
	</HEAD>
	<body>
	<%
		String userid = String.valueOf(session.getAttribute("userId"));
		String admin = (String) session.getAttribute("RTEADMIN");
	%>
	
	<p><a href="LMSMenu.jsp">返回主菜单</a></p>
	
	</body>
</html>
