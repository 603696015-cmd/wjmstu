<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.sql.*,java.util.*,org.adl.util.*"%>
<%!Connection conn;
	PreparedStatement stmtSelectUserCourse;

	/*********************************************************************
	* Method: jspInit()
	* Input: none
	* Output: 'conn' is assigned the value of the database connection and
	*         'driverName' is assigned the driver.  'sqlSelectUserCourse'
	*         is assigned the SQL string, converted to a prepared
	*         statement and assigned to 'stntSelectUserCourse'.
	*
	* Description: This function sets the 'driverName' and 'connectionURL'
	*              variables and establishes the database connection.  The
	*              SQL string is also assigned and converted to a prepared
	*              statement.
	*********************************************************************/
	public void jspInit() {
		try {
			String sqlSelectUserCourse = "SELECT SC_COURSEINFO.CourseID, "
				+ "SC_COURSEINFO.CourseTitle, SC_COURSEINFO.Control FROM SC_COURSEINFO, SC_USERCOURSEINFO "
				+ "WHERE SC_USERCOURSEINFO.UserID = ? AND "
				+ "SC_COURSEINFO.CourseID = SC_USERCOURSEINFO.CourseID AND SC_COURSEINFO.Active = 1 ORDER BY SC_COURSEINFO.CourseTitle";

			conn = JdbcUtils.getConnection();

			stmtSelectUserCourse = conn.prepareStatement(sqlSelectUserCourse);
		} catch (SQLException e) {
			System.out.println("浏览课程JSPINIT SQL异常："+e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("浏览课程JSPINIT 异常："+e.getMessage());
			e.printStackTrace();
		}
	}

	/*********************************************************************
	* Method: jspDestroy()
	* Input: none
	* Output: none
	*
	* Description: Closes the statement and the database connection.    
	*********************************************************************/
	public void jspDestroy() {
		try {
			stmtSelectUserCourse.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}%>

<html>
<head>
<title>SCORM 1.2.2 LMS View Courses</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="Tue, 05 DEC 2000 01:00:00 GMT">
<meta http-equiv="Pragma" content="no-cache">

<script language="javascript">

/****************************************************************************
**
** Function:  launchAutoWindow()
** Input:   courseID - String - The identifier of the course that is being
**                              launched in the new window.
** Output:  none
**
** Description:  Launches course content in a new window.
**
***************************************************************************/      
function launchAutoWindow(courseID){
   var theURL = "sequencingEngine.jsp?courseID=" + courseID;;
   window.document.location.href = "LMSMenu.jsp";
   window.top.contentWindow=window.open( theURL, 'displayWindow' ); 
}

/****************************************************************************
**
** Function:   init_menu()
** Input:   none
** Output:  none
**
** Description:  Refreshes the left menu page to build the current menu
**               for the currently launched course.
**
***************************************************************************/  
function init_menu(){
   window.parent.frames[1].document.location.href = "code.jsp";
}

/****************************************************************************
**
** Function:   newWindow()
** Input:   pageName - String - The page that will be launched in the new
**                              window.  At this time, only the help page.
** Output:  none
**
** Description:  Launches a new window with additional user help
**
***************************************************************************/  
function newWindow(pageName){
   window.open(pageName, 'Help', 
   "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=500,height=500");
}

</script>
</HEAD>

<body bgcolor="#FFFFFF" ONUNLOAD="init_menu()">
<jsp:include page="gotoMenu.jsp" flush="true" />

<p><font face="tahoma" size="3"><b> 可选课程 </b></font></p>

<table width="200">
	<tr>
		<td bgcolor="#5E60BD"><font face="tahoma" size="2"
			color="#ffffff"><b> &nbsp;请选择一个课程: </b></font></td>
	</tr>

	<%
		// Query for all courses the the logged in user is 
		try {
			String userID = String.valueOf(session.getAttribute("userId"));

			ResultSet userCourseRS = null;
			synchronized (stmtSelectUserCourse) {
				stmtSelectUserCourse.setString(1, userID);
				userCourseRS = stmtSelectUserCourse.executeQuery();
			}

			//Loops through the result set 'userCourseRS' outputting the name of
			//the course as a link to the sequencing engine with the courseID
			//so that the course can be launched by clicking on the link.
			while (userCourseRS.next()) {
				String courseID = userCourseRS.getString("CourseID");
				String courseTitle = userCourseRS.getString("CourseTitle");
				String control = userCourseRS.getString("Control");
	%>
	<tr>
		<td>
		<%
			// If its auto, launch in a new window.  If not, launch in the frameset
					if ((!(control == null)) && (control.equals("auto"))) {
		%>
		<p><a href="javascript:launchAutoWindow('<%=courseID%>')"> <%=courseTitle%>
		</a></p>
		<%
			} else {
		%>
		<p><a href="sequencingEngine.jsp?courseID=<%=courseID%>"><%=courseTitle%></a></p>
		<%
			}
		%>
		</td>
	</tr>
	<%
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	%>
	<tr>
		<td><br>
		<br>
		<a href="javascript:newWindow( 'launchCourseHelp.htm' );">帮助!</a></td>
	</tr>
</table>

</body>
</html>
