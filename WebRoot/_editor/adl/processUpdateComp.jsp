<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.sql.*,java.util.*,org.adl.util.*,java.io.*"%>
<HTML>
<HEAD>
<TITLE>ADL Sample RTE Version 1.2.2 Process Update Competency</TITLE>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
<SCRIPT LANGUAGE="JavaScript">
   <!--
   function MM_reloadPage(init)
   { //reloads the window if Nav4 resized
       if (init==true) with (navigator)
       {
          if ((appName=="Netscape")&&(parseInt(appVersion)==4))
          {
             document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage;
          }
       }
       else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
   }
   MM_reloadPage(true);
     // -->
   </script>
</HEAD>

<BODY BGCOLOR="#FFFFFF">
<jsp:include page="gotoMenu.jsp" flush="true" />
<%!Connection conn;
	PreparedStatement stmtUpdateComp;

	/*********************************************************************
	* Method: jspInit()
	* Input: none
	* Output: conn and stmtUpdateComp are given new values
	*
	* Description: This function sets the driverName and connectionURL
	*              variables and establishes the database connection.  The
	*              SQL string is also assigned and converted to a prepared
	*              statement.
	*********************************************************************/
	public void jspInit() {
		try {
			conn = JdbcUtils.getConnection();

			String sqlUpdateComp = "UPDATE SC_COMPETENCY SET PassFail = ? WHERE CompID = ?";
			stmtUpdateComp = conn.prepareStatement(sqlUpdateComp);
		} catch (SQLException e) {
		} catch (Exception e) {
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
			stmtUpdateComp.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}%>
<%
	// The values of the variables passFail and compID are passed into
	// stmtUpdateComp and the statement is then executed, updating the
	// competency record.
	try {
		String compID = request.getParameter("ID");
		String passFail = request.getParameter("selPassFail");

		/*********************************************************************
		* Method: synchronized()
		* Input: stmtUpdateComp
		* Output: stmtUpdateComp
		*
		* Description: Inserts the value of the variables 'passFail' and
		* 'compID' into the prepared statement 'stmtDeleteComp'.
		*********************************************************************/
		synchronized (stmtUpdateComp) {
			stmtUpdateComp.setString(1, passFail);
			stmtUpdateComp.setString(2, compID);
		}

		stmtUpdateComp.executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
<CENTER><B>记录权限更新成功.</B></CENTER>
</BODY>
</HTML>
