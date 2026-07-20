<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.sql.*,java.util.*,org.adl.samplerte.util.*,org.adl.parsers.dom.*,java.io.*,org.adl.util.JdbcUtils"%>
<%!Connection conn;
	PreparedStatement stmtSelectCourse;
	PreparedStatement stmtSelectUserCourse;
	PreparedStatement stmtInsertUserCourse;
	PreparedStatement stmtDeleteUserCourse;
	PreparedStatement stmtDeleteUserSCO;

	/*********************************************************************
	* Method: jspInit()
	* Input: none
	* Output: 'conn' is given the value of the database connection and 
	*         'stmtSelectCourse', 'stmtSelectUserCourse',
	*         'stmtInsertUserCourse', 'stmtDeleteUserCourse',
	*         'stmtDeleteUserSco' are given the values of their respective
	*         SQL strings.
	*
	* Description: This function sets the driverName and connectionURL
	*              variables and establishes the database connection.  The
	*              SQL strings are also converted and assigned  to prepared
	*              statements.
	*********************************************************************/
	public void jspInit() {
		try {
			String sqlSelectUserCourse = "SELECT * FROM SC_USERCOURSEINFO WHERE UserID = ? AND CourseID = ?";

			String sqlSelectCourse = "SELECT * FROM SC_COURSEINFO";

			String sqlInsertUserCourse = "INSERT INTO SC_USERCOURSEINFO (UserID, CourseID) VALUES(?,?)";

			String sqlDeleteUserCourse = "DELETE FROM SC_USERCOURSEINFO WHERE UserID = ? AND CourseID = ?";

			String sqlDeleteUserSCO = "DELETE FROM SC_USERSCOINFO WHERE UserID = ? AND CourseID = ?";

			conn = JdbcUtils.getConnection();

			stmtSelectCourse = conn.prepareStatement(sqlSelectCourse);
			stmtSelectUserCourse = conn.prepareStatement(sqlSelectUserCourse);
			stmtInsertUserCourse = conn.prepareStatement(sqlInsertUserCourse);
			stmtDeleteUserCourse = conn.prepareStatement(sqlDeleteUserCourse);
			stmtDeleteUserSCO = conn.prepareStatement(sqlDeleteUserSCO);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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

			stmtSelectCourse.close();
			stmtSelectUserCourse.close();
			stmtInsertUserCourse.close();
			stmtDeleteUserCourse.close();
			stmtDeleteUserSCO.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}%>
<%
	try {
		String userID = String.valueOf(session.getAttribute("userId"));

		String selectedCourses = "|";

		RTEFileHandler fileHandler = new RTEFileHandler();

		Enumeration requestNames = request.getParameterNames();
		String testString = "Course-";
		while (requestNames.hasMoreElements()) {
			String paramName = (String) requestNames.nextElement();
			int locSkillId = paramName.indexOf(testString);

			if (locSkillId != -1) {
				//String courseID = paramName.substring( testString.length() );
				String courseID = paramName;
				selectedCourses += courseID + "|";
				ResultSet userCourseRS = null;

				/*********************************************************************
				* Method: synchronized()
				* Input: stmtSelectUserCourse
				* Output: userCourseRS
				*
				* Description: Inserts the value of the variables 'userID' and
				*              'compID' into the prepared statement
				*              'stmtSelectUserCourse'.  The statement is then executed
				*              and the result assigned to 'userCourseRS'.
				*********************************************************************/
				synchronized (stmtSelectUserCourse) {
					stmtSelectUserCourse.setString(1, userID);
					stmtSelectUserCourse.setString(2, courseID);
					userCourseRS = stmtSelectUserCourse.executeQuery();
				}

				if (userCourseRS.next() == false) {
					/******************************************************************
					* Method: synchronized()
					* Input: stmtInsertUserCourse
					* Output: stmtInsertUserCourse
					*
					* Description: Inserts the value of the variables 'userID' and
					*              'compID' into the prepared statement 
					*              'stmtInsertUserCourse'.  The statement is then
					*              executed, performing the insert.
					******************************************************************/
					synchronized (stmtInsertUserCourse) {
						stmtInsertUserCourse.setString(1, userID);
						stmtInsertUserCourse.setString(2, courseID);
						stmtInsertUserCourse.executeUpdate();
					}

					fileHandler.setUserID(userID);
					fileHandler.setCourseID(courseID);
					fileHandler.initializeCourseFiles();

					//////////////////////////////////////////////////////////////////
					// Create an ADLOrganizations object for the user and the course
					/////////////////////////////////////////////////////////////////
					//System.out.println("About to open the sequencing file");

					String theWebPath = getServletConfig().getServletContext().getRealPath("/");
					String courseSeqFile = theWebPath + "CourseImports\\" + courseID + "\\sequence.obj";
					FileInputStream istream = new FileInputStream(courseSeqFile);
					ObjectInputStream ois = new ObjectInputStream(istream);

					ADLOrganizations sequenceObj = (ADLOrganizations) ois.readObject();

					istream.close();

					//////////////////////////////////////////////////////////////////
					// Create a file for that user in this specific course
					/////////////////////////////////////////////////////////////////
					//  Write the Sequencing Object to a file
					String sequencingFileName = theWebPath + "CourseImports\\" + courseID + "\\sequence."
							+ userID;
					java.io.File userSequence = new java.io.File(sequencingFileName);
					FileOutputStream ostream = new FileOutputStream(userSequence);
					ObjectOutputStream oos = new ObjectOutputStream(ostream);
					oos.writeObject(sequenceObj);
					oos.flush();
					oos.close();

				}
			}
		}

		ResultSet courseRS = null;

		/*********************************************************************
		* Method: synchronized()
		* Input: stmtSelectCourse
		* Output: courseRS
		*
		* Description: 'stmtSelectCourse' is executed and this is assigned to
		*              the result set 'courseRS'.
		*********************************************************************/
		synchronized (stmtSelectCourse) {
			courseRS = stmtSelectCourse.executeQuery();
		}

		while (courseRS.next()) {
			String courseID = courseRS.getString("CourseID");

			// Look for courses that are not selected for the user
			if (selectedCourses.indexOf("|" + courseID + "|") == -1) {
				//Now check if the user was enrolled in the course and delete
				ResultSet userCourseRS = null;

				/*********************************************************************
				* Method: synchronized()
				* Input: stmtSelectUserCourse
				* Output: userCourseRS
				*
				* Description: Inserts the value of the variable 'compID' into the
				*              prepared statement 'stmtDeleteComp'.  This statement is
				*              then executed and the result is assigned to
				*             'userCourseRS'.
				*********************************************************************/
				synchronized (stmtSelectUserCourse) {
					stmtSelectUserCourse.setString(1, userID);
					stmtSelectUserCourse.setString(2, courseID);
					userCourseRS = stmtSelectUserCourse.executeQuery();
				}
				if (userCourseRS.next() == true) {
					synchronized (stmtDeleteUserCourse) {
						stmtDeleteUserCourse.setString(1, userID);
						stmtDeleteUserCourse.setString(2, courseID);
						stmtDeleteUserCourse.executeUpdate();

						fileHandler.setUserID(userID);
						fileHandler.setCourseID(courseID);
						fileHandler.deleteCourseFiles();
					}

					synchronized (stmtDeleteUserSCO) {
						stmtDeleteUserSCO.setString(1, userID);
						stmtDeleteUserSCO.setString(2, courseID);
						stmtDeleteUserSCO.executeUpdate();
					}
				}
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
<html>
<head>
<title>Process Course Registration</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META http-equiv="expires" CONTENT="Tue, 05 DEC 2000 01:00:00 GMT">
<META http-equiv="Pragma" CONTENT="no-cache">
</HEAD>
<body bgcolor="#FFFFFF">
<jsp:include page="gotoMenu.jsp" flush="true" />

<p><font size="4">课程注册成功</font></p>
</body>
</html>
