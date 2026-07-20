<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.sql.*,java.util.*,java.io.*,org.adl.util.JdbcUtils"%>
<html>
<head>
<title>ADL Sample RTE v 1.2.2 Process Add Competency</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript">
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
	PreparedStatement stmtInsertComp;

	/*********************************************************************
	* Method: jspInit()
	* Input: none
	* Output: conn and stmtInsertComp are given new values
	*
	* Description: This function sets the driverName and connectionURL
	*              variables and establishes the database connection.  The
	*              SQL string is also assigned and converted to a prepared
	*              statement.
	*********************************************************************/
	public void jspInit() {
		try {
			conn = JdbcUtils.getConnection();

			String sqlInsertComp = "INSERT INTO SC_COMPETENCY (CompID, PassFail) VALUES('?', '?')";
			stmtInsertComp = conn.prepareStatement(sqlInsertComp);
		}catch (SQLException e){
       	 e.printStackTrace();
        }catch (Exception e){
       	 e.printStackTrace();
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
			stmtInsertComp.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}%>

<%
	// The values of compID and passFail are inserted into stmtDeleteComp
	// and the statement is executed, inserting the competency record to
	// the database.
	try {
		String compID = (String) request.getParameter("txtCompID");
		String passFail = (String) request.getParameter("selPassFail");

		/*********************************************************************
		* Method: synchronized()
		* Input: stmtInsertComp
		* Output: stmtInsertComp
		*
		* Description: Inserts the values of the variables 'compID' and
		*              'passFail' into the prepared statement 'stmtDeleteComp'.
		*********************************************************************/
		synchronized (stmtInsertComp) {
			stmtInsertComp.setString(1, compID);
			stmtInsertComp.setString(2, passFail);
		}
		stmtInsertComp.executeUpdate();

	} catch (Exception e) {
		e.printStackTrace();
	}
%>

</BODY>
</HTML>
