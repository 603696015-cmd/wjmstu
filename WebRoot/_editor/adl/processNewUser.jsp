<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.sql.*,java.util.*,org.adl.util.*,java.io.*"%>

<html>
<head>
<title>ADL Sample RTE Version 1.2.2 Process New User</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	<!--
	function MM_reloadPage(init) { //reloads the window if Nav4 resized
		if (init == true)
			with (navigator) {
				if ((appName == "Netscape") && (parseInt(appVersion) == 4)) {
					document.MM_pgW = innerWidth;
					document.MM_pgH = innerHeight;
					onresize = MM_reloadPage;
				}
			}
		else if (innerWidth != document.MM_pgW
				|| innerHeight != document.MM_pgH)
			location.reload();
	}
	M
M_reloadPage(true);
      //-->
    </script>
</HEAD>
<%
	String userID = request.getParameter("userID");
	String firstName = request.getParameter("firstName");
	String lastName = request.getParameter("lastName");
	String password = request.getParameter("password");
	String cpassword = request.getParameter("cPassword");
	String admin = request.getParameter("admin");

	if (admin.equals("1")) {
		admin = "1";
	} else {
		admin = "0";
	}

	session.setAttribute("NEWUSERID", userID);
	session.setAttribute("NEWUSERFN", firstName);
	session.setAttribute("NEWUSERLN", lastName);

	

	Connection conn = null;
	PreparedStatement stmtSelectUserInfo = null;
	ResultSet rsSelectUserInfo = null;
	ResultSetMetaData rsmd = null;

	try {
		conn = JdbcUtils.getConnection();
		String sqlSelectUserInfo = "SELECT * FROM ELUSER WHERE UserID = '"
				+ userID + "'";

		stmtSelectUserInfo = conn.prepareStatement(sqlSelectUserInfo);
		rsSelectUserInfo = stmtSelectUserInfo.executeQuery();

		rsmd = rsSelectUserInfo.getMetaData();
	} catch (Exception e) {
		e.printStackTrace();
	}

	if (rsSelectUserInfo.next() == true) {
		session.setAttribute("NEWUSERERROR", "dupid");
		response.sendRedirect("newUser.jsp");
	}

	else {
		try {
			PreparedStatement stmtInsertUserInfo;
			String sqlInsertUserInfo = "INSERT INTO ELUSER VALUES ('"
					+ userID + "','" + lastName + "','" + firstName
					+ "','" + admin + "','" + password + "'," + "'1'"
					+ ")";
			stmtInsertUserInfo = conn
					.prepareStatement(sqlInsertUserInfo);
			stmtInsertUserInfo.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.removeAttribute("NEWUSERID");
		session.removeAttribute("NEWUSERFN");
		session.removeAttribute("NEWUSERLN");
%>

<body bgcolor="#FFFFFF">
<jsp:include page="gotoMenu.jsp" flush="true" />

<h2>New user has been processed:</h2>
</body>
</html>

<%
	}
%>
