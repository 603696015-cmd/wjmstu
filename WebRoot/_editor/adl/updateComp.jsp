<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.sql.*,java.util.*,org.adl.util.*,java.io.*"%>
<HTML>
<HEAD>
<TITLE>SCORM 1.2.2 LMS Update Competency</TITLE>
<META HTTP-EQUIV-equiv="Content-Type" CONTENT="text/html; charset=UTF-8">
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
<form method="post" action="processUpdateComp.jsp" name="frmUpdateComp">
<TABLE WIDTH="458" BORDER="0">
	<%!Connection conn;
	PreparedStatement stmtSelectComp;

	/*********************************************************************
	* Method: jspInit()
	* Input: none
	* Output: conn and stmtSelectComp are given new values
	*
	* Description: This function sets the driverName and connectionURL
	*              variables and establishes the database connection.  The
	*              SQL string is also assigned and converted to a prepared
	*              statement.
	*********************************************************************/
	public void jspInit() {
		try {
			conn = JdbcUtils.getConnection();

			String sqlSelectComp = "SELECT * FROM SC_COMPETENCY where CompID = ?";
			stmtSelectComp = conn.prepareStatement(sqlSelectComp);
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
			stmtSelectComp.close();
			conn.close();
		} catch (SQLException e) {
		}
	}%>
	<%
		// The competency ID is inserted into stmtSelectComp and that statement
		// is executed.
		try {
			ResultSet compRS;
			String compID = null;
			String passFail = null;

			/*********************************************************************
			* Method: synchronized()
			* Input: stmtSelectComp
			* Output: stmtSelectComp
			*
			* Description: Inserts the competency ID into the prepared statement
			*              'stmtDeleteComp'.
			*********************************************************************/
			synchronized (stmtSelectComp) {
				stmtSelectComp.setString(1, request.getParameter("ID"));
			}

			compRS = stmtSelectComp.executeQuery();

			// Displays the information from the competency record if one
			// with an ID equal to the one chosen by the user on the
			// previous page is found.
			if (compRS.next()) {
				compID = compRS.getString("CompID");
				passFail = compRS.getString("PassFail");
	%>
	<TR>
		<TD>Competency ID:</TD>
		<TD><%=compID%></TD>
	</TR>
	<TR>
		<TD>Results:</TD>
		<TD><select NAME="selPassFail">
			<OPTION value="pass">Pass</OPTION>
			<OPTION <%if (passFail.equals("fail")) {%> selected <%}%>
				VALUE="fail">Fail</OPTION>
		</SELECT> <INPUT TYPE="hidden" NAME="ID" VALUE="<%=compID%>"></TD>
	</TR>
	<%
		}
		} catch (Exception e) {
		}
	%>
	<TR>
		<TD><INPUT TYPE="submit" name="Submit" VALUE="Submit"></TD>
	</TR>
</TABLE>
</BODY>
</HTML>
