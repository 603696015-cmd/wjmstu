<%@page import="com.sopia.common.MD5"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.sql.*,org.adl.util.JdbcUtils"%>

<%!
	Connection conn;
	PreparedStatement stmtSelectUser;

	public void jspInit() {
		try {
			String sqlSelectUser = "SELECT * FROM ELUSER Where USERNAME = ?";

			conn = JdbcUtils.getConnection();
			stmtSelectUser = conn.prepareStatement(sqlSelectUser);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void jspDestroy() {
		try {
			stmtSelectUser.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}%>

<%
	try {
		String UserName = new String("");
		String Password = new String("");
		String action = new String("");

		UserName = request.getParameter("uname");
		Password = request.getParameter("pwd");
		
		//启用MD5验证
		if(Password != null){
			Password = MD5.crypt(Password);
		}
		action = null;

		ResultSet userRS = null;

		synchronized (stmtSelectUser) {
			stmtSelectUser.setString(1, UserName);
			userRS = stmtSelectUser.executeQuery();
		}

		// Verifies that the username was found by checking to see if the result set
		// 'userRS' is empty.  If the username was found, it checks to see if the
		// entered password is correct.  If the username was not found, the variable
		// 'action' is changed to indicate this.
		if ((userRS != null) && (userRS.next())) {
			String passwd = userRS.getString("Password");
			boolean active = userRS.getBoolean("Active");

			if (!active) {
				action = "deactivated";
			}

			// Verifies that the password that was entered is not blank and that it
			// matches the password found to belong to the username.  If either of
			// these conditions is incorrect, the variable 'action' is changed
			// to indicate this.
			if ((Password != null) && (!Password.equals(passwd))) {
				action = "invalidpwd";
			}

		} else {
			action = "invaliduname";
		}

		// Verifies that no errors were found with the login by checking to see if
		// the action variable has been assigned anything.  If 'action' is null, no
		// errors were found and the session variables 'USERID' and 'RTEADMIN'
		// are set.  
		if (action == null) {
			session.putValue("userId", UserName);
			String admin = userRS.getString("Admin");

			// Checks to see if the user has admin rights and sets the 'RTEADMIN'
			// variable accordingly.
			if ((admin != null) && (admin.equals("1"))) {
				session.putValue("RTEADMIN", new String("true"));
			} else {
				session.putValue("RTEADMIN", new String("false"));
			}
		}

		// checks to see if the action variable is null.  If it is then no error
		// was found and the page is redirected to the menu page, otherwise it
		// is redirected to the second login page.
		if (action != null) {
			response.sendRedirect("LMSLogin2.htm");
		} else {
			response.sendRedirect("LMSMenu.jsp");
		}
	} catch (Exception e) {
%>
Caught exception
<%=e%>
<%
	}
%>
