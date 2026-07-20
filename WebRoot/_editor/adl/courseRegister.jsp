<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.sql.*,org.adl.util.JdbcUtils"%>
<%!Connection conn;
	PreparedStatement stmtSelectCourse;
	PreparedStatement stmtSelectUserCourse;

	/*********************************************************************
	* Method: jspInit()
	* Input: none
	* Output: conn, stmtSelectCourse, and stmtSelectUserCourse are given
	*         new values.
	*
	* Description: This function sets the driverName and connectionURL
	*              variables and establishes the database connection.  The
	*              SQL strings are also assigned and converted to prepared
	*              statements.
	*********************************************************************/
	public void jspInit() {
		try {
			String sqlSelectUserCourse = "SELECT * FROM SC_USERCOURSEINFO WHERE UserID = ?";
			String sqlSelectCourse = "SELECT * FROM SC_COURSEINFO WHERE Active = 1";

			conn = JdbcUtils.getConnection();

			stmtSelectCourse = conn.prepareStatement(sqlSelectCourse);
			stmtSelectUserCourse = conn.prepareStatement(sqlSelectUserCourse);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
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
			stmtSelectCourse.close();
			stmtSelectUserCourse.close();
			conn.close();
		} catch (SQLException e) {
		}
	}%>

<%
	String formBody = "";

	try {
		String userID = String.valueOf(session.getAttribute("userId"));

		ResultSet userCourseRS = null;
		/*********************************************************************
		* Method: synchronized()
		* Input: stmtSelectUserCourse
		* Output: stmtSelectUserCourse
		*
		* Description: Inserts the value of the variable 'userID' into the
		*              prepared statement 'stmtSelectUserCourse'.  The result
		*              set 'userCourseRS' is also assigned the results of the
		*              query.  This will give all of the courses associated
		*              with the particular user.
		*********************************************************************/
		synchronized (stmtSelectUserCourse) {
			stmtSelectUserCourse.setString(1, userID);
			userCourseRS = stmtSelectUserCourse.executeQuery();
		}

		// Loops through the result set 'userCourseRS' and adds each course ID to the
		// 'userCourses' string.
		String userCourses = "|";
		while (userCourseRS.next()) {
			userCourses += userCourseRS.getString("CourseID") + "|";
		}

		ResultSet courseRS = null;
		/*********************************************************************
		* Method: synchronized()
		* Input: stmtSelectCourse
		* Output: stmtSelectCourse
		*
		* Description: The result set 'courseRS' is assigned the results of
		*              the query.  This will give all of the courses.
		*********************************************************************/
		synchronized (stmtSelectCourse) {
			courseRS = stmtSelectCourse.executeQuery();
		}

		// Loops through the result set of all courses and if the current course is
		// in the string 'userCourses', the string 'checked' is assigned the value
		// "checked".  The body of the table is then formed by assigning it to the
		// string 'formBody'.  The 'checked' string is output in the checkbox tag.
		// If the course was in the 'userCourses' string, the checkbox will be
		// checked.
		while (courseRS.next()) {
			String courseID = courseRS.getString("CourseID");
			String courseTitle = courseRS.getString("CourseTitle");

			String checked = "";

			if (userCourses.indexOf("|" + courseID + "|") != -1) {
				checked = "checked";
			}

			formBody += "<tr><td width='10%'><input type='checkbox' name='" + courseID + "' value='1'"
					+ checked + "/></td><td>" + courseTitle + "</td></tr>";

		}

		formBody += "<tr><td colspan=2><hr></td></tr>";
	} catch (Exception e) {
	}
%>
<html>
<head>
<title>Sample Run-Time Environment Course Register</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META http-equiv="expires" CONTENT="Tue, 05 DEC 2000 01:00:00 GMT">
<META http-equiv="Pragma" CONTENT="no-cache">

<script language="javascript">
   <!--
   /*************************************************************************
   * Method: newWindow()
   * Input: pageName
   * Output: none
   *
   * Description: Opens the page input by 'pageName' in a new browser window.
   *************************************************************************/
   function newWindow(pageName){
      window.open(pageName, 'Help', "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=500,height=500");
   }
   // -->
</script>
</HEAD>
<body bgcolor="#FFFFFF">
<jsp:include page="gotoMenu.jsp" flush="true" />

<p><font face="tahoma" size="3"><b>课程注册</b></font></p>
<form name="courseRegForm" method="POST" action="processCourseReg.jsp">
<table width="548" border="0">
	<tr>
		<td colspan="3">
		<hr>
		</td>
	</tr>
	<tr>
		<td colspan="3" height="71"><font face="tahoma" size="2">
		请选择课程前面的复选框进行课程注册，取消选中的复选框删除课程课程的注册。请注意，注销一个课程将会删除该课程的所有相关数据。 </font></td>
	</tr>
	<tr>
		<td><BR>
		</td>
	</tr>
	<tr>
		<td colspan="6" bgcolor="#5E60BD"><font face="tahoma" size="2"
			color="#ffffff"><b> &nbsp;可选课程: </b></font></td>
	</tr>
	<%=formBody%>
</table>
<table width="547" border="0">
	<tr>
		<td width="45%">&nbsp;</td>
		<td width="55%"><input type="submit" name="submit" value="提交">
		</td>
	</tr>
	<TR>
		<td><A HREF="javascript:newWindow('courseRegisterHelp.htm');">帮助!</A>
		</td>
	</TR>
</table>
</form>
</body>
</html>
