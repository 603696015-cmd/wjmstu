<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.sql.*,java.util.*,org.adl.util.JdbcUtils"%>

<html>
<head>
<title>ADL Sample RTE Version 1.2.2 Process Delete User</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</HEAD>
<body bgcolor="#FFFFFF">

<jsp:include page="gotoMenu.jsp" flush="true" />
<%!Connection conn;
	PreparedStatement stmtUpdateUser;

	/*********************************************************************
	* Method: jspInit()
	* Input: none
	* Output: 'conn' is given the value of the database connection and
	*         'stmtUpdateUser' is given the value of 'sqlUpdateUser'
	*         converted to a prepared statement.
	*
	* Description: This function sets the driverName and connectionURL
	*              variables and establishes the database connection.  The
	*              SQL string is also created, converted to a prepared
	*              statement, and then assigned to 'stmtUpdateUser'.
	*********************************************************************/
	public void jspInit() {
		try {
			conn = JdbcUtils.getConnection();

			String sqlUpdateUser = "UPDATE ELUSER set Active = 1 where UserID = ?";

			stmtUpdateUser = conn.prepareStatement(sqlUpdateUser);
		} catch (SQLException e) {
			System.out.println("SQL EXCEPTION" + e);
		} catch (Exception e) {
			System.out.println("GENERAL EXCEPTION" + e);
		}
	}

	/*********************************************************************
	* Method: jspDestroy()
	* Input: none
	* Output: none
	*
	* Description: Closes statements and the database connection.    
	*********************************************************************/
	public void jspDestroy() {
		try {
			stmtUpdateUser.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQL EXCEPTION DESTROY" + e);
		}
	}%>

<%
	String strUsers = "";
	String[] arrUsers;
	strUsers = request.getParameter("chkUser");

	int loopCount = 0;
	int userCount = 0;
	String userID;

	// If 'strUsers' is not null, 'arrUsers' is assigned the value of all of the
	// checkboxes checked on the previous page.  The users are then made
	// in the 'synchronized()' function.
	if (strUsers != null) {
		arrUsers = request.getParameterValues("chkUser");
		userCount = arrUsers.length;

		synchronized (stmtUpdateUser) {
			for (loopCount = 0; loopCount < userCount; loopCount++) {
				userID = arrUsers[loopCount];
				stmtUpdateUser.setString(1, userID);
				stmtUpdateUser.executeUpdate();
			}
		}
%>
<b>Delete Successful.</b>
<%
	} else {
%>
<b>请选择一个用户删除</b>
<br>
<a href="javascript:history.go(-1);">返回删除用户</a>
<%
	}
%>

</body>
</html>
