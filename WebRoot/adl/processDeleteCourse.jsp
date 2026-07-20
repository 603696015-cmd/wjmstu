<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.sql.*,java.util.*,org.adl.util.*"%>
<html>
<head>
<title>ADL Sample RTE V 1.2.2 Process Delete Course</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</HEAD>
<body bgcolor="#FFFFFF">

<jsp:include page="gotoMenu.jsp" flush="true" />
<%!Connection conn;
	PreparedStatement stmtUpdateCourse;

	/*********************************************************************
	* Method: jspInit()
	* Input: none
	* Output: 'conn' is given the value of the database connection and 
	*         'stmtUpdateCourse' is assigned the value of the SQL string.
	*
	* Description: This function sets the driverName and connectionURL
	*              variables and establishes the database connection.  The
	*              SQL string is also assigned and converted to a prepared
	*              statement.
	*********************************************************************/
	public void jspInit() {
		try {
			conn = JdbcUtils.getConnection();

			String sqlUpdateCourse = "UPDATE SC_COURSEINFO set Active = 0 where CourseID = ?";

			stmtUpdateCourse = conn.prepareStatement(sqlUpdateCourse);
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
			stmtUpdateCourse.close();
			conn.close();
		} catch (Exception e) {
		}
	}%>

<%
	String strCourses = "";
	String[] arrCourses;
	strCourses = request.getParameter("chkCourse");

	int loopCount = 0;
	int courseCount = 0;
	String courseID;

	// If 'strCourses' is not null, the values of the checked checkboxes
	// submitted from deleteCourse.jsp are assigned to 'arrCourses', creating
	// an array of course IDs.  The courses are then made inactive in the
	// function 'snychronized()'.  If 'strCourses' is null (meaning no courses
	// were checked on deleteCourse.jsp), an error message is displayed
	// instructing the user to select a course to delete, along with a link
	// which takes the user back to deleteCourse.jsp.
	if (strCourses != null) {
		arrCourses = request.getParameterValues("chkCourse");
		courseCount = arrCourses.length;

		/*********************************************************************
		* Method: synchronized()
		* Input: stmtUpdateCourse, courseCount
		* Output: none
		*
		* Description: Loops through 'arrCourses', sets 'courseID' to the
		*              value in the current position of the array (determined
		*              by 'loopCount'), inserts the value of 'courseID' into
		*              'stmtUpdateCourse' and executes stmt'UpdateCourse'.
		*              This changes every course selected by the admin as
		*              inactive.
		*********************************************************************/
		synchronized (stmtUpdateCourse) {
			for (loopCount = 0; loopCount < courseCount; loopCount++) {
				courseID = arrCourses[loopCount];
				stmtUpdateCourse.setString(1, courseID);
				stmtUpdateCourse.executeUpdate();
			}
		}
%>

<b>删除成功.</b>

<%
	} else {
%>
<b>请选择课程进行删除</b>
<br>
<a href="javascript:history.go(-1);">返回删除课程</a>
<%
	}
%>
</body>
</html>
