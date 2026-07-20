<%@page contentType="text/html; charset=UTF-8" import="java.sql.*,java.util.*,java.io.*,org.adl.util.JdbcUtils"%>
<html>
<head>
<title>ADL Sample RTE Delete Course</title>
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
<form method="post" action="processDeleteCourse.jsp" name="deleteCourse" onsubmit="return checkData()">
<table width="458" border="0">
	<tr>
		<td bgcolor="#5E60BD" colspan="2"><font face="tahoma" size="2"
			color="#ffffff"><b> &nbsp;请选择你想要删除的课程 </b></font></td>
	</tr>
	<%!Connection conn;
	PreparedStatement stmtSelectCourses;
	String TempString;

	/******************************************************************
	* Method: jspInit()
	* Input: none
	* Output: conn and stmtSelectCourses are given new values
	*
	* Description: This function sets the driverName and connectionURL
	*              variables and establishes the database connection.
	*              The SQL string is also assigned and converted to a
	*              prepared statement.
	******************************************************************/
	public void jspInit() {
		try {
			conn = JdbcUtils.getConnection();

			//Query String to obtain Courses
			String sqlSelectCourses = "SELECT * FROM SC_COURSEINFO WHERE Active = 1 ORDER BY CourseTitle";
			stmtSelectCourses = conn.prepareStatement(sqlSelectCourses);
		} catch (SQLException e) {
			TempString = "SQL EXCEPTION";
		} catch (Exception e) {
			TempString = "Caught Genearl Exception";
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
			stmtSelectCourses.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}%>

	<%
		// The result set 'coursesRS' is assigned the results of the executed
		// statement 'stmtSelectCourses'.  This gives all of the courses.
		// 'courseRS' is then looped through and each course is outputted in
		// the table.  
		try {
			ResultSet coursesRS;
			String courseID = null;
			String courseTitle = null;

			coursesRS = stmtSelectCourses.executeQuery();

			// Loops through all of the courses and outputs them in the
			// table with a check box.
			while (coursesRS.next()) {
				courseID = coursesRS.getString("CourseID");
				courseTitle = coursesRS.getString("CourseTitle");
	%>
	<tr>
		<td width='10%'><input type='checkbox' name='chkCourse'
			value='<%=courseID%>' /></td>
		<td><%=courseTitle%></td>
	</tr>
	<%
		}
		} catch (Exception e) {
			System.out.println("IN EXCEPTION" + e + TempString);
		}
	%>

	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="2">
		<input type="submit" name="submit" value="提交" /></td>
	</tr>
</table>
<form>
</body>
</html>

