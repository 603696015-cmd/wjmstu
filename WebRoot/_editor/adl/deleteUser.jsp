<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.sql.*,java.util.*,java.io.*,org.adl.util.JdbcUtils"%>

<html>
<head>
<title>Delete User</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript">
      <!--
      
      /****************************************************************************
      **
      ** Function:  MM_reloadPage()
      ** Input:   init - boolean
      ** Output:  boolean
      **
      ** Description:  This function reloads the window if Nav4 is resized
      **
      ** Issues:  This method is not in use in Version 1.2.2 due to the lack of
      **          Netscape support.
      **
      ***************************************************************************/
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
      function checkData()
      {
        return true;
      }
      // -->
   </script>
</HEAD>

<body bgcolor="#FFFFFF">

<jsp:include page="gotoMenu.jsp" flush="true" />
<form method="post" action="processDeleteUser.jsp" name="deleteUser"
	ONSUBMIT="return checkData()">
<table width="458" border="0">
	<tr>
		<td colspan="2" bgcolor="#5E60BD">
			<font face="tahoma" size="2"color="#ffffff">
				<b> &nbsp;请选择用户删除 </b>
			</font>
		</td>
	</tr>
	<%!Connection conn;
	PreparedStatement stmtSelectUsers;
	String TempString;

	/*********************************************************************
	* Method: jspInit()
	* Input: none
	* Output: conn and stmtSelectUsers are given new values
	*
	* Description: This function sets the driverName and connectionURL
	*              variables and establishes the database connection.  The
	*              SQL string is also assigned and converted to a prepared
	*              statement.
	*********************************************************************/
	public void jspInit() {
		try {
			conn = JdbcUtils.getConnection();

			//Query String to obtain Users
			String sqlSelectUsers = "SELECT * FROM ELUSER WHERE Active = 1 ORDER BY LastName";
			stmtSelectUsers = conn.prepareStatement(sqlSelectUsers);
		} catch (SQLException e) {
			TempString = "SQL Exception";
		}
		catch (Exception e) {
			TempString = "Caught General Exception";
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
			stmtSelectUsers.close();
			conn.close();
		} catch (SQLException e) {
		}
	}%>

	<%
		try {
			ResultSet usersRS;
			String userID = null;
			String realName = null;

			usersRS = stmtSelectUsers.executeQuery();

			// Loops through all of the users and outputs each one, along with
			// a checkbox, into the table.
			while (usersRS.next()) {
				userID = usersRS.getString("USERNAME");
				realName = usersRS.getString("REALNAME");
	%>
	<tr>
		<td width='10%'><input type='checkbox' name='chkUser'
			value='<%=userID%>' /></td>
		<td><%=realName%></td>
	</tr>
	<%
		}
		} catch (Exception e) {
		}
	%>

	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="2"><input type="submit" name="submit" value="提交" /></td>
	</tr>
</table>
</form>
</body>
</html>
